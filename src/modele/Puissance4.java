package modele;
import java.util.ArrayList;
import java.util.Random;

public class Puissance4 extends Jeu {

    public Puissance4() {}

    public int verifierGagnant() {
        int casesLibres = 0;
        for (int l = 0; l < grille.length; l++) {
            for (int c = 0; c < grille[l].length; c++) {
                if (grille[l][c] != 0) {
                    // Vérifications (Lignes, Colonnes, Diagonales)
                    if (c + 3 < grille[l].length && grille[l][c] == grille[l][c+1] && grille[l][c] == grille[l][c+2] && grille[l][c] == grille[l][c+3]) return grille[l][c];
                    if (l + 3 < grille.length && grille[l][c] == grille[l+1][c] && grille[l][c] == grille[l+2][c] && grille[l][c] == grille[l+3][c]) return grille[l][c];
                    if (l + 3 < grille.length && c + 3 < grille[l].length && grille[l][c] == grille[l+1][c+1] && grille[l][c] == grille[l+2][c+2] && grille[l][c] == grille[l+3][c+3]) return grille[l][c];
                    if (l - 3 >= 0 && c + 3 < grille[l].length && grille[l][c] == grille[l-1][c+1] && grille[l][c] == grille[l-2][c+2] && grille[l][c] == grille[l-3][c+3]) return grille[l][c];
                } else casesLibres++;
            }
        }
        return casesLibres == 0 ? 3 : 0;
    }

    public int[] calculerCoupAleatoire() {
        ArrayList<Integer> colonnesValides = new ArrayList<>();
        for (int c = 0; c < grille[0].length; c++) {
            if (grille[0][c] == 0) colonnesValides.add(c);
        }
        int col = colonnesValides.get(new Random().nextInt(colonnesValides.size()));
        int ligne = grille.length - 1;
        while (grille[ligne][col] != 0) ligne--;
        return new int[]{ligne, col};
    }
}