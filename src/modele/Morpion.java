package modele;

import java.util.ArrayList;
import java.util.Random;

public class Morpion extends Jeu {


    public Morpion() {

    }

    public int verifierGagnant() {
        // Vérification des lignes
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] == grille[i][1] && grille[i][1] == grille[i][2] && grille[i][0] != 0) return grille[i][0];
        }
        // Vérification des colonnes
        for (int i = 0; i < 3; i++) {
            if (grille[0][i] == grille[1][i] && grille[1][i] == grille[2][i] && grille[0][i] != 0) return grille[0][i];
        }
        // Diagonales
        if (grille[0][0] == grille[1][1] && grille[1][1] == grille[2][2] && grille[0][0] != 0) return grille[0][0];
        if (grille[0][2] == grille[1][1] && grille[1][1] == grille[2][0] && grille[0][2] != 0) return grille[0][2];

        return compterCasesVides() == 0 ? 3 : 0; // 3 = Match nul
    }

    public int[] calculerCoupIA() {
        // Si 4 cases vides ou moins, on utilise MinMax
        if (compterCasesVides() <= 4) {
            return algorithmeMinMax();
        }
        // Sinon, on applique les règles heuristiques
        return reglesHeuristiques();
    }

    private int[] reglesHeuristiques() {
        // Ordre : Gagner > Bloquer > Centre > Coin > Côté > Première vide

        // 1 & 2 : Gagner ou Bloquer (Simulation)
        int[] coup = simulationCoup(2); // Gagner (IA = Joueur 2)
        if (coup != null) return coup;
        coup = simulationCoup(1); // Bloquer (Joueur 1)
        if (coup != null) return coup;

        // 3 : Centre
        if (grille[1][1] == 0) return new int[]{1, 1};

        // 4 : Coin
        int[][] coins = {{0,0}, {0,2}, {2,0}, {2,2}};
        for (int[] c : coins) if (grille[c[0]][c[1]] == 0) return c;

        // 5 : Côté
        int[][] cotes = {{0,1}, {1,0}, {1,2}, {2,1}};
        for (int[] c : cotes) if (grille[c[0]][c[1]] == 0) return c;

        return null;
    }

    private int[] simulationCoup(int j) {
        for(int i=0; i<3; i++) {
            for(int k=0; k<3; k++) {
                if(grille[i][k] == 0) {
                    grille[i][k] = j;
                    if(verifierGagnant() == j) { grille[i][k] = 0; return new int[]{i, k}; }
                    grille[i][k] = 0;
                }
            }
        }
        return null;
    }

    private int[] algorithmeMinMax() {
        int meilleurScore = Integer.MIN_VALUE;
        int[] meilleurCoup = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == 0) {
                    grille[i][j] = 2; // IA joue
                    int score = minMax(false);
                    grille[i][j] = 0;
                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = new int[]{i, j};
                    }
                }
            }
        }
        return meilleurCoup;
    }

    private int minMax(boolean estMax) {
        int resultat = verifierGagnant();
        if (resultat == 2) return 1;  // IA gagne
        if (resultat == 1) return -1; // IA perd
        if (resultat == 3) return 0;  // Nul

        int scoreS;
        if (estMax) {
            scoreS = Integer.MIN_VALUE;
            for(int i=0; i<3; i++)
                for(int j=0; j<3; j++)
                    if(grille[i][j] == 0) { grille[i][j] = 2; scoreS = Math.max(scoreS, minMax(false)); grille[i][j] = 0; }
        } else {
            scoreS = Integer.MAX_VALUE;
            for(int i=0; i<3; i++)
                for(int j=0; j<3; j++)
                    if(grille[i][j] == 0) { grille[i][j] = 1; scoreS = Math.min(scoreS, minMax(true)); grille[i][j] = 0; }
        }
        return scoreS;
    }

    public int[] calculerCoupAleatoireMorpion() {
        ArrayList<int[]> casesVides = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == 0) {
                    casesVides.add(new int[]{i, j});
                }
            }
        }
        return casesVides.get(new Random().nextInt(casesVides.size()));
    }
}