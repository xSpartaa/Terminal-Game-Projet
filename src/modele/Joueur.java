package modele;

public class Joueur {
    public String nom;
    private int partiGagner=0;

    public Joueur(String nom) {
        this.nom = nom;
    }

    public int getPartiGagner() {
        return partiGagner;
    }

    public void setPartiGagner(int partiGagner) {
        this.partiGagner = partiGagner;
    }
}
