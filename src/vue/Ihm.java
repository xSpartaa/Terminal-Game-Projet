package vue;
import java.util.Scanner;

public class Ihm {

    public String getStringInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int getIntInput() {
        try {
            Scanner sc = new Scanner(System.in);
            return Integer.parseInt(sc.nextLine());
        } catch (Exception _) {
            //Si type de valeur mauvaise renvoie le code d'erreur 404.
            print("Votre valeur choisis n'est pas un numéro");
            return 404;
        }
    }

    public void print(String text) {
        System.out.println(text);
    }

    public int[] choixCoup(String jeu) {
        if (jeu.equals("Morpion")) {
            String values = getStringInput();
            String[] valuesArray = values.split(" ");
            int row;
            int col;
            int[] coup = new int[2];
            if (valuesArray.length == 2) {
                row = Integer.parseInt(valuesArray[0]);
                col = Integer.parseInt(valuesArray[1]);
                coup[0] = row;
                coup[1] = col;
                return coup;
            } else return coup;
        }
        if (jeu.equals("Puissance4")) {
            int[] val = new int[1];
            val[0] = getIntInput() - 1;
            return val;
        }
        return null;
    }
}
