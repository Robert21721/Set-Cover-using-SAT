import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Redemption {
    HashMap<String, HashMap<Integer, ArrayList<String>>> map = new HashMap<>();
    int N, M, P, K;
    ArrayList<ArrayList<String>> pachete;
    ArrayList<String> cartiDetinute;
    ArrayList<String> cartiDorite;

    public static void main(final String[] args) throws IOException, InterruptedException {
        Redemption redemption = new Redemption();
        redemption.readProblemData();
        redemption.createMap();

        ArrayList<Integer> decksToBuy = new ArrayList<>();
        while (!redemption.map.isEmpty()) {
            decksToBuy.add(redemption.getNextDeck());
        }

        System.out.println(decksToBuy.size());
        for (int i : decksToBuy) {
            System.out.print(i + " ");
        }
    }

    public int getNextDeck() {
        int[] freq = new int[this.P];
        int subsetNr = 0;
        int maxFreq = 0;

        for (Map.Entry<String, HashMap<Integer, ArrayList<String>>> set : this.map.entrySet()) {
            // System.out.println(set.getKey() + " = " + set.getValue());
            // String cardName = set.getKey();
            Set<Integer> subsetIdx = set.getValue().keySet();

            // System.out.println(keys);
            for (int val : subsetIdx) {
                freq[val]++;

                if (freq[val] > maxFreq) {
                    maxFreq = freq[val];
                    subsetNr = val;
                }
            }
        }

        // delete + save
        Iterator<Map.Entry<String, HashMap<Integer, ArrayList<String>>>> it = map.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<String, HashMap<Integer, ArrayList<String>>> entry = it.next();
            if (entry.getValue().containsKey(subsetNr)) {
                it.remove();
            }
        }
        // System.out.println(this.map);
        return subsetNr + 1;
    }

    public void createMap() {
//        for (ArrayList<String> pachet : this.pachete) {
        for (int i = 0; i < this.pachete.size(); i++) {
            ArrayList<String> pachet = this.pachete.get(i);

            for (String carte : pachet) {
                if (! this.map.containsKey(carte)) {
                    this.map.put(carte, new HashMap<>());
                }

                this.map.get(carte).put(i, pachet);
            }
        }

        // System.out.println(this.map);

        for (String carte : this.cartiDetinute) {
            this.cartiDorite.remove(carte);
        }

        // System.out.println(this.cartiDorite);

        for (String carte : this.cartiDorite) {
            if (!this.map.containsKey(carte)) {
                this.map.remove(carte);
            }
        }
        // System.out.println(this.map);
    }

    public void readProblemData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = reader.readLine();
        ArrayList<Integer> info = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(firstLine, " ");

        while (st.hasMoreTokens()) {
            info.add(Integer.valueOf(st.nextToken()));
        }

        this.N = info.get(0);
        this.M = info.get(1);
        this.P = info.get(2);

        this.pachete = new ArrayList<>();
        this.cartiDetinute = new ArrayList<>();
        this.cartiDorite = new ArrayList<>();

        for (int i = 0; i < this.N; i++) {
            this.cartiDetinute.add(reader.readLine());
        }

        for (int i = 0; i < this.M; i++) {
            this.cartiDorite.add(reader.readLine());
        }

        for (int i = 0; i < this.P; i++) {
            int size = Integer.valueOf(reader.readLine());

            ArrayList<String> pachet = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                pachet.add(reader.readLine());
            }
            this.pachete.add(pachet);
        }
    }


    public void writeAnswer() throws IOException {

    }
}
