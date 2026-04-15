package controleur;

import modele.*;
import vue.Ihm;

public class Controleur {
    private final Ihm ihm;
    private Joueur j1, j2;
    private boolean modeIA;

    public Controleur(Ihm ihm) { this.ihm = ihm; }

    public void lancerJeu() {
        String n1 = ihm.demanderNom(1);
        j1 = new Joueur(n1, "X");

        modeIA = ihm.contreIA();
        if (modeIA) j2 = new Joueur("IA", "O");
        else j2 = new Joueur(ihm.demanderNomJ2(n1), "O");

        do {
            jouerPartie();
        } while (ihm.rejouer());

        ihm.bilan(j1, j2);
    }

    private void jouerPartie() {
        Jeu jeu = (ihm.choisirJeu() == 1) ? new Morpion() : (ihm.choisirJeu() == 2) ? new Puissance4() : new Nim() ;
        Difficulte diff = modeIA ? ihm.demanderDifficulte() : Difficulte.FACILE;

        boolean tourJ1 = true;
        while (!jeu.estPlein()) {
            ihm.afficherPlateau(jeu, j1.getSymbole(), j2.getSymbole());
            Joueur courant = tourJ1 ? j1 : j2;
            String coup;

            if (modeIA && !tourJ1) {
                coup = jeu.genererCoupIA(diff, j2.getSymbole(), j1.getSymbole());
                System.out.println("L'IA joue en : " + coup);
            } else {
                coup = ihm.saisirCoup(courant.getNom(), jeu.getFormatSaisie());
                while (!jeu.coupValide(coup)) {
                    ihm.erreurCoup();
                    coup = ihm.saisirCoup(courant.getNom(), jeu.getFormatSaisie());
                }
            }

            jeu.jouerCoup(coup, courant.getSymbole());
            if (jeu.estGagnant(courant.getSymbole())) {
                ihm.afficherPlateau(jeu, j1.getSymbole(), j2.getSymbole());
                System.out.println(courant.getNom() + " GAGNE !");
                courant.ajouterVictoire();
                return;
            }
            tourJ1 = !tourJ1;
        }
        System.out.println("MATCH NUL !");
    }
}