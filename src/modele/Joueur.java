package modele;

public class Joueur {
    public String nom;
    private int partiesGagnees = 0;
    public boolean estIA; // Pour savoir si c'est l'ordinateur qui joue

    public Joueur(String nom, boolean estIA) {
        this.nom = nom;
        this.estIA = estIA;
    }

    public int getPartiesGagnees() {
        return partiesGagnees;
    }

    public void setPartiesGagnees(int partiesGagnees) {
        this.partiesGagnees = partiesGagnees;
    }
}