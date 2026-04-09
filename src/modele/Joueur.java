package modele;


public class Joueur {
    private final String nom;
    private final String symbole;
    private int nbVictoires; // Compteur pour le bilan final de fin de session


    public Joueur(String nom, String symbole) {
        this.nom = nom;
        this.symbole = symbole;
        this.nbVictoires = 0; // Initialisation à zéro au début de la session
    }

    public String getNom() {
        return nom;
    }

    public String getSymbole() {
        return symbole;
    }

    /*
      Retourne le nombre de parties gagnées par ce joueur au cours de la session.
     */
    public int getVictoires() {
        return nbVictoires;
    }

    /*
      Incrémente le compteur de victoires du joueur lorsqu'il gagne une partie.
     */
    public void ajouterVictoire() {
        this.nbVictoires++;
    }
}