
import java.io.*;
import java.util.*;

// J’ai réalisé cette solution pour le challenge Advent of Code 2024 - Day 2
// L’objectif est d’analyser une série de rapports de niveaux et de savoir
// combien sont “sûrs” selon certaines règles.
public class Solution {

    // Vérifie si un rapport est “safe” (partie 1 du puzzle)
    // Un rapport est sûr s’il est soit entièrement croissant, soit entièrement décroissant,
    // et si la différence entre deux valeurs consécutives est comprise entre 1 et 3.
    public static boolean isSafe(int[] levels) {
        if (levels.length < 2) return false;

        boolean allIncreasing = true;
        boolean allDecreasing = true;

        // On compare chaque paire de valeurs consécutives
        for (int i = 0; i < levels.length - 1; i++) {
            int diff = levels[i + 1] - levels[i];

            // Si la différence est négative, ce n’est pas croissant
            if (diff <= 0) allIncreasing = false;
            // Si la différence est positive, ce n’est pas décroissant
            if (diff >= 0) allDecreasing = false;

            // Vérifie que l’écart reste dans la plage [1,3]
            int distance = Math.abs(diff);
            if (distance < 1 || distance > 3) return false;
        }

        // Le rapport est safe s’il est totalement croissant ou décroissant
        return allIncreasing || allDecreasing;
    }

    // Partie 1 : compter le nombre de rapports déjà “safe”
    public static int part1(String[] lines) {
        int safeCount = 0;

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Découpe la ligne en nombres
            String[] numbers = line.trim().split(" ");
            int[] levels = new int[numbers.length];

            // Convertit les valeurs texte → int
            for (int i = 0; i < numbers.length; i++) {
                levels[i] = Integer.parseInt(numbers[i]);
            }

            // Vérifie si le rapport respecte les règles
            if (isSafe(levels)) {
                safeCount++;
            }
        }
        return safeCount;
    }

    // Partie 2 : compter les rapports qui peuvent devenir “safe”
    // si on retire exactement un seul niveau
    public static int part2(String[] lines) {
        int fixableCount = 0;

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] numbers = line.trim().split(" ");
            int[] original = new int[numbers.length];

            for (int i = 0; i < numbers.length; i++) {
                original[i] = Integer.parseInt(numbers[i]);
            }

            boolean canBeFixed = false;

            // On essaie d’enlever chaque valeur une par une
            for (int removeIndex = 0; removeIndex < original.length; removeIndex++) {
                int[] modified = new int[original.length - 1];
                int newIndex = 0;

                for (int i = 0; i < original.length; i++) {
                    if (i != removeIndex) {
                        modified[newIndex++] = original[i];
                    }
                }

                // Si le rapport devient safe après avoir retiré un élément, on le compte
                if (isSafe(modified)) {
                    canBeFixed = true;
                    break;
                }
            }

            if (canBeFixed) fixableCount++;
        }
        return fixableCount;
    }

    // Lecture du fichier input.txt (situé à la racine du projet)
    public static String[] readInputFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();
            return lines.toArray(new String[0]);

        } catch (IOException e) {
            System.out.println("Erreur : impossible de lire le fichier input.txt");
            return new String[0];
        }
    }

    // Fonction principale
    public static void main(String[] args) {
        String[] reports = readInputFile("input.txt");

        if (reports.length == 0) {
            System.out.println("Aucune donnée trouvée, arrêt du programme.");
            return;
        }

        int resultPart1 = part1(reports);
        int resultPart2 = part2(reports);

        System.out.println("Partie 1 : " + resultPart1 + " rapports sûrs");
        System.out.println("Partie 2 : " + resultPart2 + " rapports réparables");
    }
}
