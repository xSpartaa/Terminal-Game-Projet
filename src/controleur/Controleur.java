package controleur;

import modele.*;
import vue.Ihm;

public class Controleur {
    private Ihm ihm;
    private Joueur j1, j2;
    private boolean contreIA;

    public Controleur(Ihm ihm) { this.ihm = ihm; }

    public void lancerJeu() {
        String n1 = ihm.demanderNom(1);
        j1 = new Joueur(n1, "X");

        contreIA = ihm.demanderSiIA();
        if (contreIA) j2 = new Joueur("IA", "O");
        else j2 = new Joueur(ihm.demanderNomJ2(n1), "O");

        do {
            jouerUnePartie();
        } while (ihm.rejouer());

        ihm.afficherBilan(j1, j2); // Affiche les scores et le vainqueur final
    }

    private void jouerUnePartie() {
        int choix = ihm.choisirJeu();
        Jeu jeu = (choix == 1) ? new Morpion() : new Puissance4();
        Difficulte diff = contreIA ? ihm.demanderDifficulte() : Difficulte.DIFFICILE ;

        boolean tourJ1 = true;
        while (!jeu.estPlein()) {
            ihm.afficherPlateau(jeu, j1.getSymbole(), j2.getSymbole());
            Joueur courant = tourJ1 ? j1 : j2;
            String coup;

            if (contreIA && !tourJ1) {
                coup = jeu.genererCoupIA(diff, j2.getSymbole(), j1.getSymbole());
            } else {
                coup = ihm.saisirCoup(courant.getNom(), jeu.getFormatSaisie());
                while (!jeu.coupValide(coup)) {
                    ihm.messageErreurCoup();
                    coup = ihm.saisirCoup(courant.getNom(), jeu.getFormatSaisie());
                }
            }

            jeu.jouerCoup(coup, courant.getSymbole());
            if (jeu.estGagnant(courant.getSymbole())) {
                ihm.afficherPlateau(jeu, j1.getSymbole(), j2.getSymbole());
                System.out.println(courant.getNom() + " gagne la partie !");
                courant.incrementerVictoires();
                return;
            }
            tourJ1 = !tourJ1;
        }
        System.out.println("Match nul !");
    }
}