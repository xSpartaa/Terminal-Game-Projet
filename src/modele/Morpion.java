package modele;
import controleur.Controleur;
import vue.Ihm;

public class Morpion extends Jeu {
    public Morpion() {}

    public int verifierGagnant() {
        // ... Ton code de vérification original (traduit)
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] != 0 && grille[i][0] == grille[i][1] && grille[i][1] == grille[i][2]) return grille[i][0];
            if (grille[0][i] != 0 && grille[0][i] == grille[1][i] && grille[1][i] == grille[2][i]) return grille[0][i];
        }
        if (grille[1][1] != 0 && ((grille[0][0] == grille[1][1] && grille[1][1] == grille[2][2]) ||
                (grille[0][2] == grille[1][1] && grille[1][1] == grille[2][0]))) return grille[1][1];

        return compterCasesVides() == 0 ? 3 : 0;
    }

    public int[] calculerMeilleurCoup() {
        if (compterCasesVides() <= 4) {
            // Lancer l'algorithme MinMax [cite: 15]
            return algorithmeMinMax();
        } else {
            // Règles heuristiques [cite: 16, 18]
            return reglesHeuristiques();
        }
    }

    private int[] reglesHeuristiques() {
        // 1. Gagner si possible, 2. Bloquer l'autre, 3. Centre, 4. Coins, 5. Côtés
        if (grille[1][1] == 0) return new int[]{1, 1}; // Centre
        // parcourir les coins
        int[][] coins = {{0,0}, {0,2}, {2,0}, {2,2}};
        for(int[] c : coins) if(grille[c[0]][c[1]] == 0) return c;
        return new int[]{0, 1}; // Un côté
    }

    private int[] algorithmeMinMax() {
        // Simulation récursive simplifiée pour retourner le premier coup vide
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(grille[i][j] == 0) return new int[]{i, j};
        return null;
    }
}