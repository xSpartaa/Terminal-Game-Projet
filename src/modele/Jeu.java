package modele;

public abstract class Jeu {
    public int[][]grid;
    public int playerTurn = 1;


    public void lancer(int row,int col) {
        //Initialisation et affichage de la grille + lancement de la boucle de jeu
        grid = new int[row][col];
    }

    public void setGrid(int row,int col) {
        // Case choisis par le joueur est assigné à celui-ci + on affiche la grille mise à jour
        grid[row][col] = playerTurn;
    }

    public String draw() {
        String symbole;
        //On initialise le message
        StringBuilder message = new StringBuilder();
        //Pour chaque ligne dans la grille
        for (int[] ints : grid) {
            // on créer la premiere colonne
            message.append("|");
            //Pour chaque colonne dans la ligne
            for (int anInt : ints) {
                //on met la valeur + un séparateur ("|")
                symbole = anInt == 1 ? "o" : "x";
                if (anInt != 0) message.append(symbole).append("|");
                else message.append(" |");
            }
            //A la fin de la ligne , on saute la ligne
            message.append("\n");
        }
        //On renvoie le message
        return message.toString();
    }

}
