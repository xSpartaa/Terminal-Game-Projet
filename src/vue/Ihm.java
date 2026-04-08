package vue;

import modele.*;
import java.util.Scanner;

public class Ihm {
    private Scanner sc = new Scanner(System.in);
    private final String ROUGE = "\u001B[31m";
    private final String JAUNE = "\u001B[33m";
    private final String RESET = "\u001B[0m";

    public String demanderNom(int index) {
        String nom;
        do {
            System.out.print("Nom du joueur " + index + " : ");
            nom = sc.nextLine().trim();
        } while (nom.isEmpty());
        return nom;
    }

    public String demanderNomJ2(String nomJ1) {
        String nom;
        do {
            nom = demanderNom(2);
            if (nom.equalsIgnoreCase(nomJ1)) System.out.println("Erreur : Nom déjà pris.");
        } while (nom.equalsIgnoreCase(nomJ1));
        return nom;
    }

    public int choisirJeu() {
        while (true) {
            System.out.print("Quel jeu ? (1: Morpion, 2: Puissance 4) : ");
            String choix = sc.nextLine().trim();
            if (choix.equals("1")) return 1;
            if (choix.equals("2")) return 2;
            System.out.println("Erreur : Choisissez 1 ou 2.");
        }
    }

    public void afficherPlateau(Jeu jeu, String symJ1, String symJ2) {
        System.out.println();
        for (int i = 0; i < jeu.getNbLignes(); i++) {
            for (int j = 0; j < jeu.getNbColonnes(); j++) {
                String contenu = jeu.getPlateau()[i][j];
                System.out.print("| ");
                if (jeu instanceof Puissance4) {
                    if (contenu.equals(symJ1)) System.out.print(ROUGE + "●" + RESET);
                    else if (contenu.equals(symJ2)) System.out.print(JAUNE + "●" + RESET);
                    else System.out.print(" ");
                } else {
                    System.out.print(contenu);
                }
                System.out.print(" ");
            }
            System.out.println("|");
        }
    }

    public void afficherBilan(Joueur j1, Joueur j2) {
        System.out.println("\n--- SCORE FINAL ---");
        System.out.println(j1.getNom() + " : " + j1.getNombreVictoires() + " victoires");
        System.out.println(j2.getNom() + " : " + j2.getNombreVictoires() + " victoires");

        if (j1.getNombreVictoires() > j2.getNombreVictoires()) System.out.println("VAINQUEUR : " + j1.getNom());
        else if (j2.getNombreVictoires() > j1.getNombreVictoires()) System.out.println("VAINQUEUR : " + j2.getNom());
        else System.out.println("Résultat : ex aequo"); //
    }

    public boolean rejouer() {
        System.out.print("Rejouer ? (o/n) : ");
        return sc.nextLine().trim().toLowerCase().startsWith("o");
    }

    // Autres méthodes déjà fournies (demanderSiIA, saisirCoup, etc.)
    public boolean demanderSiIA() { System.out.print("Contre l'IA ? (o/n) : "); return sc.nextLine().trim().toLowerCase().startsWith("o"); }
    public Difficulte demanderDifficulte() { System.out.print("Niveau (1: Facile, 2: Difficile) : "); return sc.nextLine().equals("1") ? Difficulte.FACILE : Difficulte.DIFFICILE; }
    public String saisirCoup(String nom, String format) { System.out.print(nom + " (" + format + ") : "); return sc.nextLine().trim(); }
    public void messageErreurCoup() { System.out.println("Coup invalide !"); }
}