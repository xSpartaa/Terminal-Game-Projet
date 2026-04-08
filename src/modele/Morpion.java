package modele;

import java.util.Random;

public class Morpion extends Jeu {
    public Morpion() {
        this.nbLignes = 3;
        this.nbColonnes = 3;
        this.plateau = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) plateau[i][j] = " ";
    }

    @Override
    public boolean coupValide(String saisie) {
        if (saisie == null || !saisie.matches("\\d+\\s+\\d+")) return false;
        String[] parts = saisie.split("\\s+");
        try {
            int l = Integer.parseInt(parts[0]) - 1;
            int c = Integer.parseInt(parts[1]) - 1;
            return l >= 0 && l < 3 && c >= 0 && c < 3 && plateau[l][c].equals(" ");
        } catch (NumberFormatException e) { return false; }
    }

    @Override
    public void jouerCoup(String saisie, String symbole) {
        String[] parts = saisie.split("\\s+");
        int l = Integer.parseInt(parts[0]) - 1;
        int c = Integer.parseInt(parts[1]) - 1;
        plateau[l][c] = symbole;
    }

    @Override
    public boolean estGagnant(String s) {
        for (int i = 0; i < 3; i++) {
            if (plateau[i][0].equals(s) && plateau[i][1].equals(s) && plateau[i][2].equals(s)) return true;
            if (plateau[0][i].equals(s) && plateau[1][i].equals(s) && plateau[2][i].equals(s)) return true;
        }
        return (plateau[0][0].equals(s) && plateau[1][1].equals(s) && plateau[2][2].equals(s)) ||
                (plateau[0][2].equals(s) && plateau[1][1].equals(s) && plateau[2][0].equals(s));
    }

    @Override
    public boolean estPlein() {
        for (String[] ligne : plateau)
            for (String caseP : ligne) if (caseP.equals(" ")) return false;
        return true;
    }

    @Override
    public String getFormatSaisie() { return "ligne colonne (ex: 2 1)"; }

    @Override
    public String genererCoupIA(Difficulte diff, String symIA, String symAdv) {

        Random r = new Random();
        int l, c;
        do { l = r.nextInt(3); c = r.nextInt(3); } while (!plateau[l][c].equals(" "));
        return (l + 1) + " " + (c + 1);

    }
}