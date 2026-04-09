package vue;

import modele.*;
import java.util.Scanner;

public class Ihm {
    private final Scanner sc = new Scanner(System.in);

    public String demanderNom(int id) {
        String n;
        do {
            System.out.print("Joueur " + id + ", nom : ");
            n = sc.nextLine().trim();
        } while (n.isEmpty());
        return n;
    }

    public String demanderNomJ2(String nomJ1) {
        String n;
        do {
            n = demanderNom(2);
            if (n.equalsIgnoreCase(nomJ1)) System.out.println("Erreur : Nom identique au Joueur 1 !");
        } while (n.equalsIgnoreCase(nomJ1));
        return n;
    }

    public int choisirJeu() {
        while (true) {
            System.out.print("Jeu (1: Morpion, 2: Puissance 4) : ");
            String c = sc.nextLine();
            if (c.equals("1") || c.equals("2")) return Integer.parseInt(c);
            System.out.println("Choix invalide !");
        }
    }

    public void afficherPlateau(Jeu jeu, String s1, String s2) {
        System.out.println();
        for (int i = 0; i < jeu.getNbLignes(); i++) {
            for (int j = 0; j < jeu.getNbColonnes(); j++) {
                String p = jeu.getPlateau()[i][j];
                System.out.print("| ");
                if (jeu instanceof Puissance4) {
                    String ROUGE = "\u001B[31m";
                    String JAUNE = "\u001B[33m";
                    String RESET = "\u001B[0m";
                    if (p.equals(s1)) System.out.print(ROUGE + "●" + RESET);
                    else if (p.equals(s2)) System.out.print(JAUNE + "●" + RESET);
                    else System.out.print(" ");
                } else {
                    System.out.print(p);
                }
                System.out.print(" ");
            }
            System.out.println("|");
        }
    }

    public void bilan(Joueur j1, Joueur j2) {
        System.out.println("\n--- RÉSULTATS FINAUX ---");
        System.out.println(j1.getNom() + " : " + j1.getVictoires() + " victoires");
        System.out.println(j2.getNom() + " : " + j2.getVictoires() + " victoires");
        if (j1.getVictoires() > j2.getVictoires()) System.out.println("Vainqueur : " + j1.getNom());
        else if (j2.getVictoires() > j1.getVictoires()) System.out.println("Vainqueur : " + j2.getNom());
        else System.out.println("Ex aequo !");
    }

    public boolean rejouer() {
        while (true) {
            System.out.print("Rejouer ? (o/n) : ");
            String rep = sc.nextLine().trim().toLowerCase();
            if (rep.equals("o")) return true;
            if (rep.equals("n")) return false;
            System.out.println("Erreur : Veuillez répondre par 'o' pour oui ou 'n' pour non.");}

    }

    public boolean contreIA() {
        while (true) {
        System.out.print("Contre l'IA ? (o/n) : ");
        String rep = sc.nextLine().trim().toLowerCase();
        if (rep.equals("o")) return true;
        if (rep.equals("n")) return false;
        System.out.println("Erreur : Veuillez répondre par 'o' pour oui ou 'n' pour non.");
        }
    }
    public Difficulte demanderDifficulte() { System.out.print("Difficulté (1: Facile, 2: Difficile) : "); return sc.nextLine().equals("2") ? Difficulte.DIFFICILE : Difficulte.FACILE; }
    public String saisirCoup(String n, String f) { System.out.print(n + " (" + f + ") : "); return sc.nextLine().trim(); }
    public void erreurCoup() { System.out.println("Coup invalide !"); }
}