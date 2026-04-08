package modele;

import java.util.Random;

public class Morpion extends Jeu {

    // Constructeur : initialise un plateau 3x3 vide
    public Morpion() {
        this.nbLignes = 3;
        this.nbColonnes = 3;
        this.plateau = new String[3][3];

        // Remplissage du plateau avec des cases vides
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                plateau[i][j] = " ";
    }

    // Vérifie si la saisie correspond à "ligne colonne" et si la case est libre
    @Override
    public boolean coupValide(String saisie) {
        if (saisie == null || !saisie.matches("\\d+\\s+\\d+")) return false;

        String[] parts = saisie.split("\\s+");
        try {
            int l = Integer.parseInt(parts[0]) - 1;
            int c = Integer.parseInt(parts[1]) - 1;

            // Vérifie que la position est dans la grille et vide
            return l >= 0 && l < 3 && c >= 0 && c < 3 && plateau[l][c].equals(" ");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Place le symbole du joueur dans la case choisie
    @Override
    public void jouerCoup(String saisie, String symbole) {
        String[] parts = saisie.split("\\s+");
        int l = Integer.parseInt(parts[0]) - 1;
        int c = Integer.parseInt(parts[1]) - 1;

        plateau[l][c] = symbole;
    }

    // Vérifie si le symbole passé en paramètre a gagné
    @Override
    public boolean estGagnant(String s) {

        // Vérification des lignes et colonnes
        for (int i = 0; i < 3; i++) {
            if (plateau[i][0].equals(s) && plateau[i][1].equals(s) && plateau[i][2].equals(s))
                return true;

            if (plateau[0][i].equals(s) && plateau[1][i].equals(s) && plateau[2][i].equals(s))
                return true;
        }

        // Vérification des deux diagonales
        return (plateau[0][0].equals(s) && plateau[1][1].equals(s) && plateau[2][2].equals(s)) ||
                (plateau[0][2].equals(s) && plateau[1][1].equals(s) && plateau[2][0].equals(s));
    }

    // Vérifie si toutes les cases sont remplies
    @Override
    public boolean estPlein() {
        for (String[] ligne : plateau)
            for (String caseP : ligne)
                if (caseP.equals(" "))
                    return false;

        return true;
    }

    // Format saisie utilisateur
    @Override
    public String getFormatSaisie() {
        return "ligne colonne (ex: 2 1)";
    }

    // Génération du coup IA selon la difficulté
    @Override
    public String genererCoupIA(Difficulte diff, String symIA, String symAdv) {

        // Mode facile : coup totalement aléatoire
        if (diff == Difficulte.FACILE) {
            Random r = new Random();
            int l, c;

            // On cherche une case vide au hasard
            do {
                l = r.nextInt(3);
                c = r.nextInt(3);
            } while (!plateau[l][c].equals(" "));

            return (l + 1) + " " + (c + 1);
        }

        // Mode difficile : heuristique puis MinMax en fin de partie
        else {
            if (compterCasesVides() > 4) {
                // Phase heuristique (début/milieu de partie)
                return jouerPhaseHeuristique(symIA, symAdv);
            } else {
                // Phase critique : utilisation de MinMax
                int[] bestMove = minimaxMorpion(symIA, symAdv, symIA);
                return (bestMove[1] + 1) + " " + (bestMove[2] + 1);
            }
        }
    }

    // Compte le nombre de cases encore vides sur le plateau
    private int compterCasesVides() {
        int count = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (plateau[i][j].equals(" "))
                    count++;

        return count;
    }

    // phase heuristique : règles simples appliquées dans l'ordre demandé
    private String jouerPhaseHeuristique(String symIA, String symAdv) {

        // Jouer un coup gagnant si possible
        String coup = chercherCoupGagnant(symIA);
        if (coup != null) return coup;

        // bloquer l’adversaire s’il peut gagner
        coup = chercherCoupGagnant(symAdv);
        if (coup != null) return coup;

        // prendre le centre si liibre
        if (plateau[1][1].equals(" ")) return "2 2";

        // Prendre un coin libre
        int[][] coins = {{0,0}, {0,2}, {2,0}, {2,2}};
        for (int[] coin : coins)
            if (plateau[coin[0]][coin[1]].equals(" "))
                return (coin[0] + 1) + " " + (coin[1] + 1);

        // Prendre un côté libre
        int[][] cotes = {{0,1}, {1,0}, {1,2}, {2,1}};
        for (int[] cote : cotes)
            if (plateau[cote[0]][cote[1]].equals(" "))
                return (cote[0] + 1) + " " + (cote[1] + 1);

        // Sinon prendre la première case vide
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (plateau[i][j].equals(" "))
                    return (i + 1) + " " + (j + 1);

        return "";
    }

    // Cherche s’il exste un coup gagnant pour le symbole donné
    private String chercherCoupGagnant(String symbole) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (plateau[i][j].equals(" ")) {

                    // On simule le coup
                    plateau[i][j] = symbole;

                    boolean gagne = estGagnant(symbole);

                    // annule la simulation
                    plateau[i][j] = " ";

                    if (gagne)
                        return (i + 1) + " " + (j + 1);
                }
            }
        }
        return null;
    }

    //  algo MinMax
    private int[] minimaxMorpion(String symIA, String symAdv, String joueurActuel) {

        //  victoire, défaite ou nul
        if (estGagnant(symIA)) return new int[]{1, -1, -1};
        if (estGagnant(symAdv)) return new int[]{-1, -1, -1};
        if (estPlein()) return new int[]{0, -1, -1};

        int bestScore = joueurActuel.equals(symIA) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestL = -1, bestC = -1;

        // teste toutes les cases libres
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (plateau[i][j].equals(" ")) {

                    // Simulation du coup
                    plateau[i][j] = joueurActuel;

                    int score = minimaxMorpion(
                            symIA,
                            symAdv,
                            joueurActuel.equals(symIA) ? symAdv : symIA
                    )[0];

                    // annulation de la simulation
                    plateau[i][j] = " ";

                    // Maximisation pour l’IA, minimisation pour l’adversaire
                    if (joueurActuel.equals(symIA)) {
                        if (score > bestScore) {
                            bestScore = score;
                            bestL = i;
                            bestC = j;
                        }
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestL = i;
                            bestC = j;
                        }
                    }
                }
            }
        }

        return new int[]{bestScore, bestL, bestC};
    }
}