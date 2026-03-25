package controleur;
import modele.*;
import vue.Ihm;
import java.util.Random;

public class Controleur {
    Ihm ihm;
    public Joueur joueur1;
    public Joueur joueur2;
    public Joueur joueurActuel;
    String[] jeuxListe = {"Morpion", "Puissance4"};
    public Jeu jeu;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void lancerJeu() {
        creerJoueurs();
        afficherListeJeux();
        choixJeu();
        joueurActuel = joueur1;
        miseAJour();
    }

    private void miseAJour() {
        ihm.print(String.format("%s :", joueurActuel.nom));
        int ligne = -1, col = -1;

        if (joueurActuel.estIA) {
            // Logique IA selon le jeu [cite: 14, 38]
            if (jeu instanceof Morpion) {
                int[] coup = ((Morpion) jeu).calculerMeilleurCoup();
                ligne = coup[0]; col = coup[1];
            } else {
                int[] coup = ((Puissance4) jeu).calculerCoupAleatoire();
                ligne = coup[0]; col = coup[1];
            }
            ihm.print("L'IA a joué en : " + (ligne + 1) + " " + (col + 1));
        } else {
            // Logique Humain (ton code original traduit)
            if (jeu instanceof Morpion) {
                ihm.print("Choisissez votre coup (Ligne + Colonne):");
                int[] coups = ihm.choixCoup("Morpion");
                ligne = coups[0] - 1; col = coups[1] - 1;

                while (ligne >= jeu.grille.length || ligne < 0 || col >= jeu.grille[0].length || col < 0 || jeu.grille[ligne][col] != 0) {
                    if (ligne == 998 && col == 998) finJeu(3);
                    else ihm.print("ERREUR : Case invalide ou occupée");
                    coups = ihm.choixCoup("Morpion");
                    ligne = coups[0] - 1; col = coups[1] - 1;
                }
            } else if (jeu instanceof Puissance4) {
                col = ihm.choixCoup("Puissance4")[0];
                while (col >= jeu.grille[0].length || col < 0 || jeu.grille[0][col] != 0) {
                    if (col == 998) finJeu(3);
                    else ihm.print("ERREUR : Colonne invalide ou pleine");
                    col = ihm.choixCoup("Puissance4")[0];
                }
                // Trouver la ligne pour Puissance 4
                ligne = jeu.grille.length - 1;
                while (ligne >= 0 && jeu.grille[ligne][col] != 0) ligne--;
            }
        }

        jeu.setGrille(ligne, col);
        ihm.print(jeu.dessiner());

        int gagnant = (jeu instanceof Morpion) ? ((Morpion) jeu).verifierGagnant() : ((Puissance4) jeu).verifierGagnant();

        if (gagnant == 0) {
            joueurActuel = (jeu.tourJoueur == 1) ? joueur2 : joueur1;
            jeu.tourJoueur = (jeu.tourJoueur == 1) ? 2 : 1;
            miseAJour();
        } else finJeu(gagnant);
    }

    private void choixJeu() {
        ihm.print("Veuillez choisir un jeu : ");
        int choix = ihm.getIntInput();
        while (choix < 0 || choix >= jeuxListe.length) {
            choix = ihm.getIntInput();
        }
        if (choix == 0) { jeu = new Morpion(); jeu.lancer(3, 3); }
        else { jeu = new Puissance4(); jeu.lancer(6, 7); }
        ihm.print(jeu.dessiner());
    }

    private void afficherListeJeux() {
        for (int k = 0; k < jeuxListe.length; k++) ihm.print("[" + k + "] | " + jeuxListe[k]);
    }

    public void creerJoueurs() {
        ihm.print("Nom du Joueur 1 :");
        joueur1 = new Joueur(ihm.getStringInput(), false);

        ihm.print("Jouer contre [0] Humain ou [1] IA ?");
        int mode = ihm.getIntInput();
        if (mode == 1) {
            joueur2 = new Joueur("IA", true);
        } else {
            ihm.print("Nom du Joueur 2 :");
            joueur2 = new Joueur(ihm.getStringInput(), false);
        }
    }

    public void finJeu(int gagnantId) {
        Joueur gagnant = gagnantId == 1 ? joueur1 : joueur2;
        if (gagnantId == 3) ihm.print("Match nul !");
        else {
            ihm.print(gagnant.nom + " a gagné !");
            gagnant.setPartiesGagnees(gagnant.getPartiesGagnees() + 1);
        }
        // ... (Reste de ta logique de fin de jeu inchangée mais traduite)
    }
}