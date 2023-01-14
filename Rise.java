import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Rise extends Task {
    int N, M, P, K;
    ArrayList<ArrayList<String>> pachete;
    ArrayList<String> cartiDetinute;
    ArrayList<String> cartiDorite;
    boolean ok = false;

    public static void main(final String[] args) throws IOException, InterruptedException {
        Rise rise = new Rise();
        rise.readProblemData();
        rise.solve();
    }
    @Override
    public void solve() throws IOException, InterruptedException {
        for (String carte : this.cartiDetinute) {
            if (this.cartiDorite.contains(carte)) {
                this.cartiDorite.remove(carte);
            }
        }

        for (int i = 1; i <= this.P; i++) {
            this.K = i;
            this.formulateOracleQuestion();
            this.askOracle();
            this.decipherOracleAnswer();

            if (this.ok) {
                return;
            }
        }

        System.out.println("False");
    }
    @Override
    public void readProblemData() throws IOException {
        Scanner scanner = new Scanner(System.in);
        // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        this.N = scanner.nextInt();
        this.M = scanner.nextInt();
        this.P = scanner.nextInt();
        String caca = scanner.nextLine();

//        System.out.println(reader.readLine());
//        System.out.println(reader.readLine());
//        System.out.println(reader.readLine());
//        System.out.println(reader.readLine());

        // System.out.println(this.N + " " + this.M + " " + this.P);

        this.pachete = new ArrayList<>();
        this.cartiDetinute = new ArrayList<>();
        this.cartiDorite = new ArrayList<>();

        for (int i = 0; i < this.N; i++) {
            this.cartiDetinute.add(scanner.nextLine());
        }
        // System.out.println(this.cartiDetinute);

        for (int i = 0; i < this.M; i++) {
            this.cartiDorite.add(scanner.nextLine());
        }
        // System.out.println(this.cartiDorite);


        for (int i = 0; i < this.P; i++) {
            int size = scanner.nextInt();
            caca = scanner.nextLine();

            ArrayList<String> pachet = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                pachet.add(scanner.nextLine());
            }
            this.pachete.add(pachet);
        }

        // System.out.println(this.pachete);
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        FileWriter file = new FileWriter("sat.cnf");
        // nr elemente multime - caetiDorite.size()
        // nr submultimi - P
        // k - schimbam in for

        int difNr = (this.P * (this.P - 1)) / 2 + this.P;
        file.write("p cnf " + (this.K * this.P) + " " + (this.K + difNr + this.cartiDorite.size()) + "\n");

        int [][] mat = new int[this.K][this.P];

        for (int i = 0; i < this.K; i++) {
            for (int j = 1; j <= this.P; j++) {
                file.write((this.P * i + j) + " ");
                mat[i][j - 1] = this.P * i + j;
            }
            file.write("0\n");
        }

        for (int i = 0; i < this.K; i++) {
            for (int j = 0; j < this.P - 1; j++) {
                for (int k = j + 1; k < this.P; k++) {
                    file.write(-mat[i][j] + " " + -mat[i][k] + " 0\n");
                }
            }
        }

        for (int nr = 0; nr < this.cartiDorite.size(); nr++) {
            for (int subSetIdx = 0; subSetIdx < this.P; subSetIdx++) {
                if (this.pachete.get(subSetIdx).contains(this.cartiDorite.get(nr))) {
                    for (int i = subSetIdx + 1; i <= this.P * this.K; i += this.P) {
                        file.write(i + " ");
                    }
                }
            }
            file.write("0\n");
        }

        file.close();
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        FileReader file = new FileReader("sat.sol");
        Scanner scanner = new Scanner(file);
        String res = scanner.nextLine();

        // System.out.println(res);
        if (res.equals("True")) {

            // System.out.println(res);
            int size = scanner.nextInt();

            int nrElem = 0;
            ArrayList<Integer> subSets = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                int nr = scanner.nextInt();
                if (nr > 0) {
                    nrElem++;
                    if (nr % this.P == 0) {
                        subSets.add(this.P);
                    } else {
                        subSets.add(nr % this.P);
                    }
                }
            }

            System.out.println(nrElem);
            for (int i = 0; i < subSets.size(); i++) {
                System.out.print(subSets.get(i) + " ");
            }
            // System.out.print("\n");
            this.ok = true;
        }
    }

    @Override
    public void writeAnswer() throws IOException {

    }
}
