package modele;

import controleur.Controleur;
import vue.Ihm;

public class Puissance4 extends Jeu{
    Ihm ihm;
    Controleur controleur;

    public Puissance4(Ihm ihm, Controleur controleur) {
        this.ihm = ihm;
        this.controleur = controleur;
    }

    public int checkWin() {
        int spaceAvailable = 0;
        //Pour chaque élément de la grille
        for (int row = 0;row < grid.length;row++) {
            for (int col = 0; col < grid[row].length; col++) {

                //Si la case est occupé par un joueur
                if (grid[row][col] != 0) {

                    //Verification ligne gagnante
                    if (col - 3 >=0) {
                        if (grid[row][col - 3] == grid[row][col - 2] && grid[row][col - 2] == grid[row][col - 1] && grid[row][col - 1] == grid[row][col]) return grid[row][col];

                    //Verification colonne gagnante
                    }if (row -3 >= 0) {
                        if (grid[row-3][col] == grid[row-2][col] && grid[row-2][col] == grid[row-1][col] && grid[row-1][col] == grid[row][col]) return grid[row][col];

                    //Verification diagonale gauche gagnante
                    }if (row - 3 >= 0 && col-3>= 0) {
                        if (grid[row-3][col-3] == grid[row-2][col-2] && grid[row-2][col-2] == grid[row-1][col-1] && grid[row-1][col-1] == grid[row][col]) return grid[row][col];

                    //Verification diagonale droite gagnante
                    }if (row -3 >= 0 && col+3<= grid[row].length-1) {
                        if (grid[row-3][col+3] == grid[row-2][col+2] && grid[row-2][col+2] == grid[row-1][col+1] && grid[row-1][col+1] == grid[row][col]) return grid[row][col];
                    }
                }else spaceAvailable++;
            }
        }
        if (spaceAvailable>0) return 0;
        else return 3;
    }

}
