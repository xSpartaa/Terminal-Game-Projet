package modele;

public abstract class Jeu {
    protected String[][] plateau;
    protected int nbLignes;
    protected int nbColonnes;

    public abstract boolean coupValide(String saisie);
    public abstract void jouerCoup(String saisie, String symbole);
    public abstract boolean estGagnant(String symbole);
    public abstract boolean estPlein();
    public abstract String genererCoupIA(Difficulte diff, String symIA, String symAdv);
    public abstract String getFormatSaisie();

    public String[][] getPlateau() { return plateau; }
    public int getNbLignes() { return nbLignes; }
    public int getNbColonnes() { return nbColonnes; }
}