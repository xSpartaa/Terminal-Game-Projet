package modele;

public abstract class Jeu {
    public int[][] grille;
    public int tourJoueur = 1;

    public void lancer(int ligne, int col) {
        // Initialisation de la grille
        grille = new int[ligne][col];
    }

    public void setGrille(int ligne, int col) {
        // La case choisie est assignée au joueur actuel
        grille[ligne][col] = tourJoueur;
    }

    public String dessiner() {
        String symbole;
        StringBuilder message = new StringBuilder();
        for (int[] ligne : grille) {
            message.append("|");
            for (int cellule : ligne) {
                symbole = cellule == 1 ? "o" : "x";
                if (cellule != 0) message.append(symbole).append("|");
                else message.append(" |");
            }
            message.append("\n");
        }
        return message.toString();
    }

    // Compte le nombre de cases encore vides dans la grille
    public int compterCasesVides() {
        int vides = 0;
        for (int[] ligne : grille) {
            for (int cellule : ligne) {
                if (cellule == 0) vides++;
            }
        }
        return vides;
    }
}