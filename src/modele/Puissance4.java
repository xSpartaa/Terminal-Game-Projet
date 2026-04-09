package modele;

import java.util.*;

public class Puissance4 extends Jeu {
    public Puissance4() {
        this.nbLignes = 6;
        this.nbColonnes = 7;
        this.plateau = new String[6][7];
        for (int i = 0; i < 6; i++) Arrays.fill(plateau[i], " ");
    }

    @Override
    public boolean coupValide(String saisie) {
        if (saisie == null || !saisie.matches("\\d+")) return false;
        int c = Integer.parseInt(saisie) - 1;
        return c >= 0 && c < 7 && plateau[0][c].equals(" ");
    }

    @Override
    public void jouerCoup(String saisie, String symbole) {
        int c = Integer.parseInt(saisie) - 1;
        for (int i = 5; i >= 0; i--) {
            if (plateau[i][c].equals(" ")) {
                plateau[i][c] = symbole;
                break;
            }
        }
    }

    @Override
    public boolean estGagnant(String s) {
        // Vérification 4 pions (Horizontal, Vertical, Diagonales)
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                if (verifierAlignement(r, c, 0, 1, s) >= 4) return true;
                if (verifierAlignement(r, c, 1, 0, s) >= 4) return true;
                if (verifierAlignement(r, c, 1, 1, s) >= 4) return true;
                if (verifierAlignement(r, c, 1, -1, s) >= 4) return true;
            }
        }
        return false;
    }

    private int verifierAlignement(int r, int c, int dr, int dc, String s) {
        int count = 0;
        while (r >= 0 && r < 6 && c >= 0 && c < 7 && plateau[r][c].equals(s)) {
            count++; r += dr; c += dc;
        }
        return count;
    }

    @Override
    public boolean estPlein() {
        for (int j = 0; j < 7; j++) if (plateau[0][j].equals(" ")) return false;
        return true;
    }

    @Override
    public String getFormatSaisie() { return "colonne (1-7)"; }

    @Override
    public String genererCoupIA(Difficulte diff, String symIA, String symAdv) {
        if (diff == Difficulte.FACILE) return coupAleatoire();

        int meilleurScore = -10000;
        List<Integer> meilleursCoups = new ArrayList<>();

        for (int c = 0; c < 7; c++) {
            if (plateau[0][c].equals(" ")) {
                int score = evaluerCoup(c, symIA, symAdv);
                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleursCoups.clear();
                    meilleursCoups.add(c + 1);
                } else if (score == meilleurScore) {
                    meilleursCoups.add(c + 1);
                }
            }
        }
        return String.valueOf(meilleursCoups.get(new Random().nextInt(meilleursCoups.size())));
    }

    private int evaluerCoup(int c, String symIA, String symAdv) {
        int r = 5;
        while (r >= 0 && !plateau[r][c].equals(" ")) r--;
        int score = 0;

        // 1) Phase défensive
        plateau[r][c] = symAdv;
        if (estGagnant(symAdv)) score += 900;
        plateau[r][c] = " ";

        // 2) Phase offensive
        plateau[r][c] = symIA;
        if (estGagnant(symIA)) score += 1000; // Aligner 4

        // Aligner 3 (+100 par espace vide)
        // Aligner 2 (+10 par groupe espaces)
        // (Logique de comptage simplifiée pour les alignements partiels)

        // 3) Placement stratégique (Centre)
        int[] bonus = {1, 2, 3, 5, 3, 2, 1};
        score += bonus[c];

        plateau[r][c] = " ";
        return score;
    }

    private String coupAleatoire() {
        Random r = new Random();
        int c;
        do { c = r.nextInt(7); } while (!plateau[0][c].equals(" "));
        return String.valueOf(c + 1);
    }
}