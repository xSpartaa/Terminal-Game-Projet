package modele;

import java.util.Random;

public class Morpion extends Jeu {
    public Morpion() {
        this.nbLignes = 3;
        this.nbColonnes = 3;
        this.plateau = new String[3][3];
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) plateau[i][j] = " ";
    }

    @Override
    public boolean coupValide(String saisie) {
        if (saisie == null || !saisie.matches("\\d+\\s+\\d+")) return false;
        String[] pts = saisie.split("\\s+");
        int l = Integer.parseInt(pts[0]) - 1, c = Integer.parseInt(pts[1]) - 1;
        return l >= 0 && l < 3 && c >= 0 && c < 3 && plateau[l][c].equals(" ");
    }

    @Override
    public void jouerCoup(String saisie, String symbole) {
        String[] pts = saisie.split("\\s+");
        plateau[Integer.parseInt(pts[0]) - 1][Integer.parseInt(pts[1]) - 1] = symbole;
    }

    @Override
    public boolean estGagnant(String s) {
        for (int i = 0; i < 3; i++) {
            if (plateau[i][0].equals(s) && plateau[i][1].equals(s) && plateau[i][2].equals(s)) return true;
            if (plateau[0][i].equals(s) && plateau[1][i].equals(s) && plateau[2][i].equals(s)) return true;
        }
        return (plateau[0][0].equals(s) && plateau[1][1].equals(s) && plateau[2][2].equals(s)) ||
                (plateau[0][2].equals(s) && plateau[1][1].equals(s) && plateau[2][0].equals(s));
    }

    @Override
    public boolean estPlein() {
        for (String[] ligne : plateau) for (String c : ligne) if (c.equals(" ")) return false;
        return true;
    }

    @Override
    public String getFormatSaisie() { return "ligne colonne (ex: 2 2)"; }

    @Override
    public String genererCoupIA(Difficulte diff, String symIA, String symAdv) {
        int vides = 0;
        for (String[] l : plateau) for (String c : l) if (c.equals(" ")) vides++;

        if (diff == Difficulte.DIFFICILE) {
            if (vides <= 4) return executerMinMax(symIA, symAdv);
            return appliquerHeuristiques(symIA, symAdv);
        }
        return coupAleatoire();
    }

    private String appliquerHeuristiques(String symIA, String symAdv) {
        // 1. Gagner ou 2. Bloquer
        String coup = verifierMenaceOuOpportunite(symIA);
        if (coup != null) return coup;
        coup = verifierMenaceOuOpportunite(symAdv);
        if (coup != null) return coup;

        // 3. Centre
        if (plateau[1][1].equals(" ")) return "2 2";

        // 4. Coins
        int[][] coins = {{1,1}, {1,3}, {3,1}, {3,3}};
        for (int[] c : coins) if (plateau[c[0]-1][c[1]-1].equals(" ")) return c[0] + " " + c[1];

        return coupAleatoire();
    }

    private String verifierMenaceOuOpportunite(String s) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plateau[i][j].equals(" ")) {
                    plateau[i][j] = s;
                    boolean gagne = estGagnant(s);
                    plateau[i][j] = " ";
                    if (gagne) return (i + 1) + " " + (j + 1);
                }
            }
        }
        return null;
    }

    private String executerMinMax(String symIA, String symAdv) {
        int meilleurScore = Integer.MIN_VALUE;
        String meilleurCoup = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plateau[i][j].equals(" ")) {
                    plateau[i][j] = symIA;
                    int score = minmax(false, symIA, symAdv);
                    plateau[i][j] = " ";
                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = (i + 1) + " " + (j + 1);
                    }
                }
            }
        }
        return meilleurCoup;
    }

    private int minmax(boolean estMax, String symIA, String symAdv) {
        if (estGagnant(symIA)) return 1;
        if (estGagnant(symAdv)) return -1;
        if (estPlein()) return 0;

        int scoreRef = estMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plateau[i][j].equals(" ")) {
                    plateau[i][j] = estMax ? symIA : symAdv;
                    int score = minmax(!estMax, symIA, symAdv);
                    plateau[i][j] = " ";
                    scoreRef = estMax ? Math.max(score, scoreRef) : Math.min(score, scoreRef);
                }
            }
        }
        return scoreRef;
    }
    private String coupAleatoire() {
        Random r = new Random();
        int l, c;
        do { l = r.nextInt(3); c = r.nextInt(3); } while (!plateau[l][c].equals(" "));
        return (l + 1) + " " + (c + 1);
    }
}