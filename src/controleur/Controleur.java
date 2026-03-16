package controleur;
import modele.*;
import vue.Ihm;

public class Controleur {
    Ihm ihm;
    public Joueur joueur1;
    public Joueur joueur2;
    public Joueur joueurActuelle;
    String[] jeuxListe = {"Morpion","Puissance4"};
    public Jeu jeu;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void lancerJeu() {
        //Création des joueurs + affichage des jeux + choix du jeu
        creerJoueurs();
        afficherListeJeux();
        choixJeu();
        joueurActuelle = joueur1;
        update();
    }

    private void update() {
        //Choix du coup du joueur
        ihm.print(String.format("%s :",joueurActuelle.nom));
        if (jeu.getClass().getSimpleName().equals("Morpion")) {
            ihm.print("Choisissez votre coup (Ligne + Colonne):");
            int[] coups = ihm.choixCoup(jeu.getClass().getSimpleName());
            int row = coups[0]-1;
            int col = coups[1]-1;

            //Tant que la valeur est plus grande que le nombre de case ou que la case choisis est déjà prise
            while (row >= jeu.grid.length || row < 0 || col >= jeu.grid[row].length || col < 0  || jeu.grid[row][col] != 0) {

                //Si la valeur est égale à 999 (valeur pour quitter le jeu), quitte le jeu en égalité
                if (row == 998 && col == 998) finJeu(3);

                    //Si valeur est égale à 404, valeur pas dans la grille
                else if (row == 403 || col == 403) ihm.print("ERREUR : Valeur non comprise dans la grille");

                    //Si la valeur est comprise dans la grille → case occupée
                else if (row < jeu.grid.length && row >= 0 || col < jeu.grid.length && col >= 0) ihm.print("ERREUR : Case déjà occupée");

                else if (row < 0 || col < 0) ihm.print("ERREUR : Valeur trop petite");
                    //Sinon → Valeur trop grande
                else ihm.print("ERREUR : Valeur trop Grande!");

                //Affichage de la grille + nom du joueur et nouveau choix
                ihm.print(String.format("%s :",joueurActuelle.nom));
                ihm.print("Choisissez votre coup (Ligne + Colonne):");

                coups = ihm.choixCoup(jeu.getClass().getSimpleName());
                row = coups[0]-1;
                col = coups[1]-1;
            }
            jeu.setGrid(row,col);
        }if (jeu.getClass().getSimpleName().equals("Puissance4")) {
            int value = ihm.choixCoup("Puissance4")[0];
            //Tant que la valeur est plus grande que le nombre de case ou que la case choisis est déjà prise
            while (value >= jeu.grid.length || value < 0 || (jeu.grid[0][value] != 0)) {
                //Si la valeur est égale à 999 (valeur pour quitter le jeu), quitte le jeu en égalité
                if (value == 998) finJeu(3);

                    //Si valeur est égale à 404, valeur pas dans la grille
                else if (value == 403) ihm.print("ERREUR : Valeur non comprise dans la grille");

                    //Si la valeur est inférieure à 0 (valeur - 1 < 0) → valeur trop petite
                else if (value < 0) ihm.print("ERREUR: Valeur trop petite");

                    //Si la valeur est comprise dans la grille → case occupée
                else if (jeu.grid[0][value] != 0) ihm.print("ERREUR : Case déjà occupée");

                    //Sinon → Valeur trop grande
                else ihm.print("ERREUR : Valeur trop Grande!");

                //Affichage de la grille + nom du joueur et nouveau choix
                int row = 0;
                while (jeu.grid[row][value] == 0 && row < jeu.grid.length-1) {
                    row++;
                }
                jeu.setGrid(row,value);

                ihm.print(String.format("%s :",joueurActuelle.nom));
                ihm.print("Choisissez votre coup :");
                value = ihm.choixCoup("Puissance4")[0];
                jeu.setGrid(row,value);

            }

        }

        ihm.print(jeu.draw());
        //On regarde si un joueur à gagner la partie (ZÉRO si personne a gagné).
        int winner;
        if (jeu.getClass().getSimpleName().equals("Morpion")) {
            Morpion morpion = (Morpion) jeu;
            winner = morpion.checkWin();
        } else {
            Puissance4 puissance4 = (Puissance4) jeu;
            winner = puissance4.checkWin();
        }

        //Si personne a gagné, on passe le tour au prochain joueur et on refait une mise à jour du jeu
        if (winner == 0) {
            joueurActuelle = jeu.playerTurn == 1 ? joueur2 : joueur1;
            jeu.playerTurn = jeu.playerTurn == 1 ? 2 : 1;
            update();

            //Sinon, on termine la partie en indiquant le gagnant
        } else finJeu(winner);
    }

    private void choixJeu() {
        ihm.print("Veuillez choisir un jeu : ");
        int choice = (ihm.getIntInput());

        //Tant que le choix est égale à 404 (ERREUR pour mauvais type de valeur) ou que le choix n'est pas dans la liste des choix possible.
        while (choice == 404 || choice < 0 || choice > jeuxListe.length ) {
            ihm.print("Veuillez choisir un jeu : ");
            choice = ihm.getIntInput();}

        //Si choix est égale à | 0 : Morpion, 1 : Puissance4 | Puis lancement du jeu
        switch(choice) {
            case(0) -> {
                jeu = new Morpion(ihm,this);
                jeu.lancer(3,3);
            }
            case(1) -> {
                jeu = new Puissance4(ihm,this);
                jeu.lancer(6,7);
            }
        }
        ihm.print(jeu.draw());

    }

    private void afficherListeJeux() {
        //Pour toute la liste des jeux, afficher chaque jeu
        for(int k = 0; k < jeuxListe.length;k++) {
            ihm.print("["+k+"] | "+jeuxListe[k]);
        }
    }

    public void creerJoueurs() {
        //Pour de 1 à 3, demander le nom de chaque joueur et le donner en fonction du numéro du joueur
        for(int k = 1; k < 3 ; k++) {
            String username = creerNom(k);
            switch(k) {
                case(1) -> joueur1 = new Joueur(username);
                case(2) -> joueur2 = new Joueur(username);
            }
        }
    }

    public String creerNom(int id) {
        //Demander le nom du joueur
        ihm.print(String.format("Joueur %s \n Entrez votre nom : \n",id));
        String username = ihm.getStringInput();

        //Tant que le nom donné est vide ou que le nom du joueur est égale à l'autre, afficher une erreur et redemander le nom du joueur.
        while (username.isEmpty() || joueur1 != null && joueur1.nom.equals(username) ) {
            if (joueur1 != null && joueur1.nom.equals(username)) ihm.print("ERREUR : Nom égal au premier joueur!");
            else ihm.print("ERREUR : Nom incorrect!");
            ihm.print(String.format("Joueur %s \n Entrez votre nom : \n", id));
            username = ihm.getStringInput();
        }return username;
    }

    public void finJeu(int winner) {
        //On initialise le gagnant en fonction de la valeur winner donné
        Joueur gagnant = winner == 1 ? joueur1 : joueur2 ;
        //Si winner est égale à 3, cela veut dire aucun gagnant
        if (winner == 3) ihm.print("Aucun gagnant pour la partie");
            //S-il y a un gagnant, on affiche le gagnant et on lui ajoute 1 à ses parties gagnées
        else {
            ihm.print(String.format("%s à gagné la partie !",gagnant.nom));
            gagnant.setPartiGagner(gagnant.getPartiGagner()+1);
        }

        ihm.print("Voulez vous relancez une partie ?");
        ihm.print("[0] Relancer une partie ");
        ihm.print("[1] Quitter le jeu");
        int choice = ihm.getIntInput();

        //tant que le choix est different des possibilités (0 ou 1), on affiche une erreur et redonne les possibilités
        while (choice != 0 && choice != 1) {
            ihm.print("ERREUR : Valeur incorrect ! \n");
            ihm.print("Voulez vous relancez une partie ?");
            ihm.print("[0] Relancer une partie ");
            ihm.print("[1] Quitter le jeu");
            choice = ihm.getIntInput();
        }

        //Si le choix est égale à 0, relance la partie, Sinon (1), on affiche le classement des joueurs.
        if (choice == 0) jeu.lancer(jeu.grid.length,jeu.grid[0].length);
        else {
            ihm.print("Classements des parties gagnées : ");
            ihm.print(String.format("- %s : %s parties gagnées",joueur1.nom,joueur1.getPartiGagner()));
            ihm.print(String.format("- %s : %s parties gagnées \n",joueur2.nom,joueur2.getPartiGagner()));
            Joueur gagnantFinal = joueur1.getPartiGagner() > joueur2.getPartiGagner() ? joueur1 : joueur2;

            //On désigne le gagnant selon les parties gagnées des joueurs
            if (joueur1.getPartiGagner() == joueur2.getPartiGagner()) gagnantFinal = null;

            //Si gagnant final est non nul, on affiche le gagnant final, Sinon, on donne l'égalité
            if (gagnantFinal != null) ihm.print("Le gagnant final est : "+ gagnantFinal.nom);
            else ihm.print("Egalité , vous avez tout les deux gagné autant de partie l'un que l'autre");
        }
    }
}
