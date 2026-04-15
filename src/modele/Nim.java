package modele;

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
        for (int nbAllumettes : lesTas) {
            for (int i = 1; i <= nbAllumettes; i++) {
                s+="| ";
            }
           s+="\n";
        }
        return s;
    }

    @Override
    public boolean coupValide(String saisie) {
        String[] saisis = saisie.split(" ");
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
        return "";
    }

    @Override
    public String getFormatSaisie() {
        return "";
    }
}
