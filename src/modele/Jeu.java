package modele;

public abstract class Jeu {
    public int[][] grille;
    public int tourJoueur = 1;

    public void lancer(int ligne, int col) {
        grille = new int[ligne][col];
    }

    public void setGrille(int ligne, int col) {
        grille[ligne][col] = tourJoueur;
    }

    public String dessiner() {
        String symbole;
        StringBuilder message = new StringBuilder();
        for (int[] ligne : grille) {
            message.append("|");
            for (int caseGrille : ligne) {
                symbole = caseGrille == 1 ? "o" : "x";
                if (caseGrille != 0) message.append(symbole).append("|");
                else message.append(" |");
            }
            message.append("\n");
        }
        return message.toString();
    }

    // Méthode pour compter les cases vides (utile pour MinMax)
    public int compterCasesVides() {
        int vides = 0;
        for (int[] ligne : grille) {
            for (int c : ligne) if (c == 0) vides++;
        }
        return vides;
    }
}