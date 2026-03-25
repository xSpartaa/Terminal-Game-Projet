package controleur;
import modele.*;
import vue.Ihm;

public class Controleur {
    Ihm ihm;
    public Joueur joueur1;
    public Joueur joueur2;
    public Joueur joueurActuel;
    String[] listeJeux = {"Morpion", "Puissance 4"};
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
            int[] coup;
            if (jeu instanceof Morpion) {
                coup = ((Morpion) jeu).calculerCoupIA();
            } else {
                coup = ((Puissance4) jeu).calculerCoupAleatoire();
            }
            ligne = coup[0];
            col = coup[1];
        } else {
            // Saisi Humain (Morpion)
            if (jeu instanceof Morpion) {
                ihm.print("Choisissez votre coup (Ligne + Colonne):");
                int[] coups = ihm.choixCoup("Morpion");
                ligne = coups[0]-1; col = coups[1]-1;
                while (ligne >= 3 || ligne < 0 || col >= 3 || col < 0 || jeu.grille[ligne][col] != 0) {
                    if (ligne == 998) finJeu(3);
                    ihm.print("ERREUR : Case occupée ou invalide.");
                    coups = ihm.choixCoup("Morpion");
                    ligne = coups[0]-1; col = coups[1]-1;
                }
            }
            // Saisi Humain (Puissance 4)
            else {
                ihm.print("Choisissez votre colonne :");
                col = ihm.choixCoup("Puissance4")[0];
                while (col >= 7 || col < 0 || jeu.grille[0][col] != 0) {
                    if (col == 998) finJeu(3);
                    ihm.print("ERREUR : Colonne pleine ou invalide.");
                    col = ihm.choixCoup("Puissance4")[0];
                }
                ligne = jeu.grille.length-1;
                while (jeu.grille[ligne][col] != 0) ligne--;
            }
        }

        jeu.setGrille(ligne, col);
        ihm.print(jeu.dessiner());

        int gagnantId = (jeu instanceof Morpion) ? ((Morpion)jeu).verifierGagnant() : ((Puissance4)jeu).verifierGagnant();

        if (gagnantId == 0) {
            joueurActuel = (jeu.tourJoueur == 1) ? joueur2 : joueur1;
            jeu.tourJoueur = (jeu.tourJoueur == 1) ? 2 : 1;
            miseAJour();
        } else finJeu(gagnantId);
    }

    private void choixJeu() {
        ihm.print("Veuillez choisir un jeu : ");
        int choix = ihm.getIntInput();

        while (choix < 0 || choix > 1) {
            ihm.print("ERREUR : Veuillez choisir un jeu : ");
            choix = ihm.getIntInput();
        }

        if (choix == 0) { jeu = new Morpion(); jeu.lancer(3, 3); }
        else { jeu = new Puissance4(); jeu.lancer(6, 7); }
        ihm.print(jeu.dessiner());
    }

    private void afficherListeJeux() {
        for(int k = 0; k < listeJeux.length; k++) ihm.print("["+k+"] | "+listeJeux[k]);
    }

    public void creerJoueurs() {
        // Joueur 1 [cite: 9]
        ihm.print("Nom du Joueur 1 :");
        joueur1 = new Joueur(ihm.getStringInput(), false);

        // Mode de jeu
        ihm.print("Mode : [0] Deux joueurs, [1] Contre l'ordinateur (IA)");
        int mode = ihm.getIntInput();

        while (mode != 0  && mode != 1) {
            ihm.print("Mauvais valeur entrée");
            ihm.print("Choisissez votre mode :");
            mode = ihm.getIntInput();
        }

        if (mode == 1) {
            joueur2 = new Joueur("IA", true); // IA nommée "IA"
        } else {
            ihm.print("Nom du Joueur 2 :"); //
            String nom = ihm.getStringInput();
            while (nom.equals(joueur1.nom)) {
                ihm.print("Nom égale à celui du joueur 1");
                ihm.print("Nom du Joueur 2 :");
                nom = ihm.getStringInput();
            }
            joueur2 = new Joueur(nom, false);
        }
    }

    public void finJeu(int gagnantId) {
        if (gagnantId == 3) ihm.print("Match nul !");
        else {
            Joueur victorieux = (gagnantId == 1) ? joueur1 : joueur2;
            ihm.print(victorieux.nom + " a gagné !");
            victorieux.setPartiesGagnees(victorieux.getPartiesGagnees() + 1);
        }
        ihm.print("Relancer ? [0] Oui [1] Quitter");
        if (ihm.getIntInput() == 0) {
            jeu.lancer(jeu.grille.length, jeu.grille[0].length );
            ihm.print(jeu.dessiner());
            miseAJour();
        }
        else {
            ihm.print("Classements des parties gagnées : ");
            ihm.print(String.format("- %s : %s parties gagnées",joueur1.nom,joueur1.getPartiesGagnees()));
            ihm.print(String.format("- %s : %s parties gagnées \n",joueur2.nom,joueur2.getPartiesGagnees()));
            Joueur gagnantFinal = joueur1.getPartiesGagnees() > joueur2.getPartiesGagnees() ? joueur1 : joueur2;

            //On désigne le gagnant selon les parties gagnées des joueurs
            if (joueur1.getPartiesGagnees() == joueur2.getPartiesGagnees()) gagnantFinal = null;

            //Si gagnant final est non nul, on affiche le gagnant final, Sinon, on donne l'égalité
            if (gagnantFinal != null) ihm.print("Le gagnant final est : "+ gagnantFinal.nom);
            else ihm.print("Egalité , vous avez tout les deux gagné autant de partie l'un que l'autre");
            System.exit(0);
        }
    }
}