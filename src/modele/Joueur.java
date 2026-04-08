package modele;

public class Joueur {
    private String nom;
    private String symbole;
    private int nombreVictoires; // Pour le bilan final

    public Joueur(String nom, String symbole) {
        this.nom = nom;
        this.symbole = symbole;
        this.nombreVictoires = 0;
    }

    public String getNom() { return nom; }
    public String getSymbole() { return symbole; }
    public int getNombreVictoires() { return nombreVictoires; }
    public void incrementerVictoires() { this.nombreVictoires++; }
}