package modele;

import java.util.BitSet;
import java.util.Random;
import java.util.function.BinaryOperator;

/**
 * <p>La classe Nim représente l'état courant de la partie
 * Elle est caractérisée par un tableau. Chaque case du tableau enregistre le nombre d'allumettes de chaque tas.
 * Le nombre d'allumettes du premier tas étant dans la case 0.
 * </p>
 *
 * @author Kahlem
 */
public class Nim extends Jeu{

    /**
     * représente l'état courant de la partie
     */
    private int[] lesTas=new int[3];

    public Nim() {
        initialiser();
    }


    /**
     * ajoute les allumettes dans chacun des tas de la manière suivante :
     * dans le ième tas, on place 2*i - 1 allumettes.
     */
    public void initialiser(){
        for (int i = 0; i < lesTas.length; i++) {
            lesTas[i] = 2 * i + 1;
        }
    }

    /**
     * Retourne vrai si la partie est terminée et faux sinon
     * @return
     */

    @Override
    public boolean estGagnant(String symbole) {
        return nbAllumette() == 0;
    }

    /**
     * Nombre d'allumettes par tas
     */
    public int nbAllumettes(int numeroTas) {
        return lesTas[numeroTas - 1];
    }

    /**
     * Retourne le nombre total d'allumettes de la partie
     */
    public int nbAllumette(){

    int total = 0;
    for (int nb : lesTas)
        total+=nb;
    return total;
    }

    /**
     * retourne l'état de la partie sous forme d'une chaîne de caractères constituées des batons correspondant au nombre d'allumettes pour chaque tas.
     *
     */
    @Override
    public String toString() {
        String s="";
        int k=0;
        for (int nbAllumettes : lesTas) {
            s += ++k + " ";
            for (int i = 1; i <= nbAllumettes; i++) {
                s+="| ";
            }
           s+="\n";
        }
        return s;
    }

    @Override
    public boolean coupValide(String saisie) {
        if (saisie == null || !saisie.matches("\\d+\\s+\\d+")) return false;
        String[] saisis = saisie.split("\\s+");
        int numeroTas = Integer.parseInt(saisis[0]);
        int nbAllumettes = Integer.parseInt(saisis[1]);
        if (numeroTas >= 1 && numeroTas <= lesTas.length && nbAllumettes >= 1 && nbAllumettes <= nbAllumettes(numeroTas)) return true;
        return false;
    }

    @Override
    public void jouerCoup(String saisie, String symbole) {
        String[] saisis = saisie.split(" ");
        int numeroTas = Integer.parseInt(saisis[0]);
        int nbAllumettes = Integer.parseInt(saisis[1]);
        System.out.println(numeroTas + " " + nbAllumettes);
        if (numeroTas >= 1 && numeroTas <= lesTas.length && nbAllumettes >= 1 && nbAllumettes <= nbAllumettes(numeroTas)) {
            lesTas[numeroTas - 1] -= nbAllumettes;

        }
    }


    @Override
    public String genererCoupIA(Difficulte diff, String symIA, String symAdv) {
        if (diff == Difficulte.FACILE) {
            Random r = new Random();
            int l = r.nextInt(3-1)+1;
            while (lesTas[l-1] == 0) {l = r.nextInt(3-1)+1;             System.out.println(l); }

            int nb = 1;
            if ( lesTas[l-1] > l) {nb = r.nextInt(lesTas[l-1]-1)+1;}
            return l + " " + nb;


        }else if (diff == Difficulte.DIFFICILE) {
            int xor=0;
            for (int n = 1;n <= lesTas.length;n++) {
                if (lesTas[n-1] > 0) {
                    int m = lesTas[n-1];
                    xor ^=m;
                    System.out.println(xor);
                }
            }
            System.out.println("--------------------------");
            if (xor == 0) { for (int n = 1 ;n <= lesTas.length;n++) { if (lesTas[n] > 0) return n + " " + 1;}
            } else {
                for (int n = 0 ;n < lesTas.length;n++) {
                    if (lesTas[n] > 0) {
                        int tempXor = xor ^ lesTas[n];
                        System.out.println(tempXor);
                        if (tempXor < lesTas[n]) return (n + 1 + " " + (lesTas[n] - tempXor));
                    }
                }
            }
        }
        System.out.println("null");
        return "1 1";
    }

    @Override
    public String getFormatSaisie() {
        return "Numéros de tas | nombre d'allumettes (ex : 3 2 )";
    }
}
