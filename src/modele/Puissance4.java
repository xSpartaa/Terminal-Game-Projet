package modele;

import java.util.Arrays;
import java.util.Random;

// Classe Puissance4 qui hérite de la classe abstraite Jeu
public class Puissance4 extends Jeu {

    // Constructeur : initialise la taille du plateau et le remplit de cases vides
    public Puissance4() {
        this.nbLignes = 6;      // 6 lignes
        this.nbColonnes = 7;    // 7 colonnes
        this.plateau = new String[6][7];  // Création du tableau 2D

        // Initialisation de toutes les cases avec un espace vide
        for (int i = 0; i < 6; i++)
            Arrays.fill(plateau[i], " ");
    }

    // Vérifie si la saisie du joueur est valide
    @Override
    public boolean coupValide(String saisie) {
        // Vérifie que la saisie n’est pas nulle et contient uniquement des chiffres
        if (saisie == null || !saisie.matches("\\d+")) return false;

        try {
            int c = Integer.parseInt(saisie) - 1; // Conversion en index (0 à 6)

            // Vérifie que la colonne existe et que la case du haut est vide
            return c >= 0 && c < 7 && plateau[0][c].equals(" ");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Place le symbole dans la colonne choisie
    @Override
    public void jouerCoup(String saisie, String symbole) {
        int c = Integer.parseInt(saisie) - 1;

        // On part du bas du plateau vers le haut (gravité)
        for (int i = 5; i >= 0; i--) {
            if (plateau[i][c].equals(" ")) {
                plateau[i][c] = symbole; // On place le symbole (X ou O)
                break;
            }
        }
    }

    // Vérifie si un joueur est gagnant
    @Override
    public boolean estGagnant(String s) {

        // Parcours de toutes les cases du plateau
        for (int l = 0; l < plateau.length; l++) {
            for (int c = 0; c < plateau[l].length; c++) {

                if (plateau[l][c].equals(s)) {

                    // Vérification horizontale (4 symboles alignés)
                    if (c + 3 < plateau[l].length &&
                            plateau[l][c + 1].equals(s) &&
                            plateau[l][c + 2].equals(s) &&
                            plateau[l][c + 3].equals(s))
                        return true;

                    // Vérification verticale
                    if (l + 3 < plateau.length &&
                            plateau[l + 1][c].equals(s) &&
                            plateau[l + 2][c].equals(s) &&
                            plateau[l + 3][c].equals(s))
                        return true;

                    // Vérification diagonale montante (/)
                    if (l - 3 >= 0 && c + 3 < plateau[l].length &&
                            plateau[l - 1][c + 1].equals(s) &&
                            plateau[l - 2][c + 2].equals(s) &&
                            plateau[l - 3][c + 3].equals(s))
                        return true;

                    // Vérification diagonale descendante (\)
                    if (l + 3 < plateau.length && c + 3 < plateau[l].length &&
                            plateau[l + 1][c + 1].equals(s) &&
                            plateau[l + 2][c + 2].equals(s) &&
                            plateau[l + 3][c + 3].equals(s))
                        return true;
                }
            }
        }
        return false; // Aucun alignement trouvé
    }

    // Vérifie si le plateau est plein
    @Override
    public boolean estPlein() {
        // Si une case en haut est vide, alors le plateau n’est pas plein
        for (int j = 0; j < 7; j++)
            if (plateau[0][j].equals(" ")) return false;

        return true;
    }

    // Indique le format attendu pour la saisie utilisateur
    @Override
    public String getFormatSaisie() {
        return "colonne (1-7)";
    }

    // Génère un coup pour l’IA selon la difficulté
    @Override
    public String genererCoupIA(Difficulte diff, String symIA, String symAdv) {

        // Mode FACILE : colonne aléatoire valide
        if (diff == Difficulte.FACILE) {
            Random r = new Random();
            int c;
            do {
                c = r.nextInt(7);
            } while (!plateau[0][c].equals(" "));

            return String.valueOf(c + 1);
        }

        // Mode DIFFICILE : IA intelligente
        else {
            return choisirColonneIntelligente(symIA);
        }
    }

    // Choisit la meilleure colonne selon une évaluation stratégique
    private String choisirColonneIntelligente(String symboleIA) {

        String symboleAdverse = symboleIA.equals("X") ? "O" : "X";
        int maxScore = Integer.MIN_VALUE;

        // Liste des meilleures colonnes possibles
        java.util.List<Integer> meilleuresColonnes = new java.util.ArrayList<>();

        // Évaluation de chaque colonne disponible
        for (int c = 0; c < 7; c++) {

            if (plateau[0][c].equals(" ")) {

                int score = evaluerColonne(c, symboleIA, symboleAdverse);

                if (score > maxScore) {
                    maxScore = score;
                    meilleuresColonnes.clear();
                    meilleuresColonnes.add(c);
                }
                else if (score == maxScore) {
                    meilleuresColonnes.add(c);
                }
            }
        }

        // Choix aléatoire parmi les meilleures colonnes
        Random r = new Random();
        int bestCol = meilleuresColonnes.get(r.nextInt(meilleuresColonnes.size()));
        return String.valueOf(bestCol + 1);
    }

    // Évalue le score stratégique d’une colonne
    private int evaluerColonne(int colonne, String symboleIA, String symboleAdverse) {

        // Recherche de la ligne disponible la plus basse
        int ligne = -1;
        for (int i = 5; i >= 0; i--) {
            if (plateau[i][colonne].equals(" ")) {
                ligne = i;
                break;
            }
        }

        if (ligne == -1) return Integer.MIN_VALUE;

        int score = 0;

        // PHASE DÉFENSIVE : simuler un coup adverse
        plateau[ligne][colonne] = symboleAdverse;
        if (estGagnant(symboleAdverse)) {
            score += 900; // Priorité au blocage
        }
        plateau[ligne][colonne] = " ";

        // PHASE OFFENSIVE : simuler coup IA
        plateau[ligne][colonne] = symboleIA;

        if (estGagnant(symboleIA)) {
            score += 1000; // Priorité à la victoire
        }
        else {
            score += evaluerAlignements(ligne, colonne, symboleIA);
        }

        plateau[ligne][colonne] = " ";

        // Bonus positionnel favorise le centre
        int[] bonusPos = {1, 2, 3, 5, 3, 2, 1};
        score += bonusPos[colonne];

        return score;
    }

    // Évalue les alignements autour d’une position
    private int evaluerAlignements(int l, int c, String symbole) {

        int score = 0;

        // Horizontal
        for (int colStart = Math.max(0, c - 3); colStart <= Math.min(3, c); colStart++)
            score += evaluerFenetre(l, colStart, 0, 1, symbole);

        // Vertical
        for (int ligStart = Math.max(0, l - 3); ligStart <= Math.min(2, l); ligStart++)
            score += evaluerFenetre(ligStart, c, 1, 0, symbole);

        // Diagonale descendante \
        for (int dec = 3; dec >= 0; dec--) {
            int ligStart = l - dec, colStart = c - dec;
            if (ligStart >= 0 && ligStart <= 2 && colStart >= 0 && colStart <= 3)
                score += evaluerFenetre(ligStart, colStart, 1, 1, symbole);
        }

        // Diago montante /
        for (int dec = 3; dec >= 0; dec--) {
            int ligStart = l + dec, colStart = c - dec;
            if (ligStart >= 3 && ligStart <= 5 && colStart >= 0 && colStart <= 3)
                score += evaluerFenetre(ligStart, colStart, -1, 1, symbole);
        }

        return score;
    }

    // evalue une fenêtre de 4 cases consécutives
    private int evaluerFenetre(int lStart, int cStart, int dLig, int dCol, String symbole) {

        int nbSymbole = 0, nbVide = 0;

        for (int i = 0; i < 4; i++) {
            String val = plateau[lStart + i * dLig][cStart + i * dCol];

            if (val.equals(symbole)) nbSymbole++;
            else if (val.equals(" ")) nbVide++;
        }

        // Attribution des scores selon le potentiel
        if (nbSymbole == 3 && nbVide == 1) return 100;
        if (nbSymbole == 2 && nbVide == 2) return 10;

        return 0;
    }
}