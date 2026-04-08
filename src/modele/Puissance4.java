package modele;

import java.util.Arrays;
import java.util.Random;

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
        try {
            int c = Integer.parseInt(saisie) - 1;
            return c >= 0 && c < 7 && plateau[0][c].equals(" ");
        } catch (NumberFormatException e) { return false; }
    }

    @Override
    public void jouerCoup(String saisie, String symbole) {
        int c = Integer.parseInt(saisie) - 1;
        for (int i = 5; i >= 0; i--) {
            if (plateau[i][c].equals(" ")) {
                plateau[i][c] = symbole; // On stocke le symbole du joueur (X ou O)
                break;
            }
        }
    }

    @Override
    public boolean estGagnant(String s) {
        for (int l=0; l< plateau.length; l++) {
            for (int c = 0; c < plateau[l].length; c++) {
                if (plateau[l][c] != " ") {
                    // Horizontal
                    if (c + 3 < plateau[l].length && plateau[l][c] == plateau[l][c + 1] && plateau[l][c] == plateau[l][c + 2] && plateau[l][c] == plateau[l][c + 3])
                        return true;
                    // Vertical
                    if (l + 3 < plateau.length && plateau[l][c] == plateau[l + 1][c] && plateau[l][c] == plateau[l + 2][c] && plateau[l][c] == plateau[l + 3][c])
                        return true;
                    // Diagonales
                    if (l - 3 >= 0 && c + 3 < plateau[l].length && plateau[l][c] == plateau[l - 1][c + 1] && plateau[l][c] == plateau[l - 2][c + 2] && plateau[l][c] == plateau[l - 3][c + 3])
                        return true;
                    if (l + 3 < plateau.length && c + 3 < plateau[l].length && plateau[l][c] == plateau[l + 1][c + 1] && plateau[l][c] == plateau[l + 2][c + 2] && plateau[l][c] == plateau[l + 3][c + 3])
                        return true;
                }
            }
        }
        return false;
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
        // IA Facile = Aléatoire
        // IA Difficile = Évaluation offensive/défensive
        Random r = new Random();
        int c;
        do { c = r.nextInt(7); } while (!plateau[0][c].equals(" "));
        return String.valueOf(c + 1);
    } try
}