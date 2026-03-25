package vue;
import java.util.Scanner;

public class Ihm {
    public String getStringInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (input.isEmpty()) {
            print("Aucune valeur entrée , Recommencez votre choix : ");
            input = sc.nextLine();
        }
        return input;
    }

    public int getIntInput() {
        try {
            return Integer.parseInt(getStringInput());
        } catch (Exception e) {
            return 404;
        }
    }

    public void print(String texte) {
        System.out.println(texte);
    }

    public int[] choixCoup(String jeuNom) {
        if (jeuNom.equals("Morpion")) {
            String[] saisie = getStringInput().split(" ");
            if (saisie[0].equals("999")) return new int[]{999, 999};
            if (saisie.length == 2) {
                try {
                    int ligne = Integer.parseInt(saisie[0]);
                    int col = Integer.parseInt(saisie[1]);
                    return new int[]{ligne, col};
                } catch (NumberFormatException e) {
                    print("Saisie invalide, entrez deux chiffres séparés par un espace (ex: 1 2)");
                    return choixCoup(jeuNom); // Redemande à l'utilisateur
                }
            } else {
                print("Format invalide, entrez deux chiffres séparés par un espace (ex: 1 2)");
                return choixCoup(jeuNom);
            }
        } else {
            return new int[]{getIntInput() - 1};
        }
    }
}