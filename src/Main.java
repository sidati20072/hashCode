
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static int computeIngrediants(List<Set<String>> pizzas) {
        Set<String> union = new HashSet<String>();
        for (Set<String> pizza : pizzas) {
            union.addAll(pizza);
        }
        return union.size();
    }
    public static int run(Map<String, String> pizzas, List<Integer> teams, List<List<Integer>> results) {
        int i = 0;
        int k = 0;
        int score = 0;
        results.clear();
        while (i < pizzas.size()) {
            if (k == teams.size())
                return score;
            int team = teams.get(k);
            List<Integer> result = new ArrayList<Integer>();
            if (pizzas.size() >= i + team) {
                int q = computeIngrediants(pizzas.subList(i, i + team));
                score += q * q;
                for (int l = 0; l < team; l++) {
                    result.add(i + l);
                }
                results.add(result);
            }
            i = i + team;
            k++;
        }
        return score;
    }
    public static void main(String[] args) {
        try {
            int K = 5000;
            File myObj = new File("C:\\Users\\Admin\\Downloads\\pizza.txt");
            Scanner sc = new Scanner(myObj);
            int M = sc.nextInt();
            int T2 = sc.nextInt();
            int T3 = sc.nextInt();
            int T4 = sc.nextInt();
            Date d1 = new Date();

            Map<String, String> pizzas = new HashMap<>();
            //List<Set<String>> pizzas = new ArrayList<Set<String>>();
            for (int p = 0; p < M; p++) {
                int nbIngredient = sc.nextInt();
                String ingrediants;
                List<String> listIngredients = new ArrayList<>() ;
                for (int j = 0; j < nbIngredient; j++) {
                    listIngredients.add(sc.next());
                }

                ingrediants = listIngredients.stream().sorted().collect(Collectors.joining(" "));
                pizzas.put(ingrediants, String.valueOf(p));
            }

            int maxscore = 0;
            List<List<Integer>> results = new ArrayList<List<Integer>>();
            List<Integer> teams = new ArrayList<Integer>();
            int t = 0;
            while (t < T2) {
                teams.add(2);
                t++;
            }
            t = 0;
            while (t < T3) {
                teams.add(3);
                t++;
            }
            t = 0;
            while (t < T4) {
                teams.add(4);
                t++;
            }

            pizzas.keySet().stream().sorted(Main::compareByDisimilariry).collect(Collectors.toList());

            for (int k = 0; k < K; k++) {
                List<List<Integer>> temp = new ArrayList<List<Integer>>();
                int score = run(pizzas, teams, temp);
                if (score > maxscore) {
                    maxscore = score;
                    results = temp;
                }
            }

            System.out.println(results.size());
            for (int j = 0; j < results.size(); j++) {
                List<Integer> result = results.get(j);
                System.out.print(result.size());
                for (int l = 0; l < result.size(); l++) {
                    System.out.print(" " + result.get(l));
                }
                System.out.println();
            }
            long d2 = new Date().getTime() - d1.getTime();
            System.out.println("************" + maxscore + "*************** in *****: " + d2);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static int compareByDisimilariry(String key1, String key2) {
        similarity.SimilarityStrategy strategy = new similarity.JaroWinklerStrategy();

        String target = key1;
        String source = key2;
        similarity.StringSimilarityService service = new similarity.StringSimilarityServiceImpl(strategy);
        double score = service.score(source, target); // Score is 0.90
            if (score <= 0.5) return 1;
            else return -1;
    }


}
