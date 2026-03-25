package modele;
import java.util.ArrayList;
import java.util.Random;

public class Puissance4 extends Jeu {
    public int verifierGagnant() {
        // Retourne 1 ou 2 pour un gagnant, 3 pour match nul, 0 sinon.
        return 0;
    }

    public int[] calculerCoupAleatoire() {
        ArrayList<Integer> colonnesValides = new ArrayList<>();
        for (int c = 0; c < grille[0].length; c++) {
            if (grille[0][c] == 0) colonnesValides.add(c);
        }
        int colChoisie = colonnesValides.get(new Random().nextInt(colonnesValides.size()));
        int ligne = grille.length - 1;
        while (grille[ligne][colChoisie] != 0) ligne--;
        return new int[]{ligne, colChoisie};
    }
}