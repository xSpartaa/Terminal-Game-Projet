package modele;
import controleur.Controleur;
import vue.Ihm;

public class Morpion extends Jeu {
   Ihm ihm;
   Controleur controleur;

    public Morpion(Ihm ihm, Controleur controleur) {
        this.ihm = ihm;
        this.controleur = controleur;
    }

    public int checkWin() {
        //Verification de toutes les lignes
        for (int[] row : grid) {
            if (row[0] == row[1] && row[1] == row[2] && row[0] != 0) return row[0];

        //Verification de toutes les colonnes
        } for (int col = 0;col <grid[0].length;col++) {
            if (grid[0][col] == grid[1][col] && grid[1][col] == grid[2][col] && grid[0][col] != 0) return grid[0][col];
        }

        //Verification de la diagonale de haut à gauche de bas à droite
        if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[0][0] != 0) return grid[0][0];

        //Verification de la diagonale de haut à droite de bas à gauche
        else if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[0][2] != 0) return grid[0][2];

        //Verification si une case est encore disponible
        for (int[] row : grid) {
            for (int col = 0;col <grid[0].length;col++) {
                if (row[col] == 0) return 0;
            }
        }return 3;
    }
}
