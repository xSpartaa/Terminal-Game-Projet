package modele;

/**
 * Classe représentant un joueur participant aux jeux.
 * Respecte l'architecture MVC et utilise des identifiants en français[cite: 5, 219, 306, 254].
 */
public class Joueur {
    private final String nom;
    private final String symbole;
    private int nbVictoires; // Compteur pour le bilan final de fin de session

    /**
     * Constructeur pour créer un nouveau joueur.
     * @param nom Le nom saisi par l'utilisateur [cite: 10, 238]
     * @param symbole Le symbole utilisé sur le plateau (X, O ou ●) [cite: 225, 231]
     */
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