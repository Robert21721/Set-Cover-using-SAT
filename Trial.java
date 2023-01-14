import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Trial extends Task {
    int N, M, K;
    ArrayList<ArrayList<Integer>> data;

    public static void main(final String[] args) throws IOException, InterruptedException {
        Trial trial = new Trial();
        trial.readProblemData();
        trial.formulateOracleQuestion();
        trial.askOracle();
        trial.decipherOracleAnswer();
    }
    @Override
    public void solve() throws IOException, InterruptedException {

    }
    @Override
    public void readProblemData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> info = new ArrayList<>();
        String firstLine = reader.readLine();
        StringTokenizer st = new StringTokenizer(firstLine, " ");

        while (st.hasMoreTokens()) {
            info.add(Integer.valueOf(st.nextToken()));
        }

        this.N = info.get(0);
        this.M = info.get(1);
        this.K = info.get(2);

        this.data = new ArrayList<>();

        for (int i = 0; i < this.M; i++) {
            String line = reader.readLine();
            ArrayList<Integer> numbers = new ArrayList<>();

            st = new StringTokenizer(line, " ");
            while (st.hasMoreTokens()) {
                numbers.add(Integer.valueOf(st.nextToken()));
            }

            numbers.remove(0);
            this.data.add(numbers);
        }
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        FileWriter file = new FileWriter("sat.cnf");

        int difNr = (this.M * (this.M - 1)) / 2 + this.M;

        file.write("p cnf " + (this.K * this.M) + " " + (this.K + difNr + this.N) + "\n");

        int [][] mat = new int[this.K][this.M];

        for (int i = 0; i < this.K; i++) {
            for (int j = 1; j <= this.M; j++) {
                file.write((this.M * i + j) + " ");
                mat[i][j - 1] = this.M * i + j;
            }
            file.write("0\n");
        }

        for (int i = 0; i < this.K; i++) {
            for (int j = 0; j < this.M - 1; j++) {
                for (int k = j + 1; k < this.M; k++) {
                    file.write(-mat[i][j] + " " + -mat[i][k] + " 0\n");
                }
            }
        }

        for (int nr = 1; nr <= this.N; nr++) {
            for (int subSetIdx = 0; subSetIdx < this.data.size(); subSetIdx++) {
                if (this.data.get(subSetIdx).contains(Integer.valueOf(nr))) {
                    for (int i = subSetIdx + 1; i <= this.M * this.K; i += this.M) {
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
        BufferedReader reader = new BufferedReader(file);
        String res = reader.readLine();

        System.out.println(res);
        if (res.equals("False")) {
            return;
        }

        int size = Integer.valueOf(reader.readLine());
        String data = reader.readLine();
        StringTokenizer st = new StringTokenizer(data, " ");

        int nrElem = 0;
        ArrayList<Integer> subSets = new ArrayList<>();
        while (st.hasMoreTokens()) {
            int nr = Integer.valueOf(st.nextToken());
            if (nr > 0) {
                nrElem++;
                if (nr % this.M == 0) {
                    subSets.add(this.M);
                } else {
                    subSets.add(nr % this.M);
                }
            }

        }

        System.out.println(nrElem);
        for (int i = 0; i < subSets.size(); i++) {
            System.out.print(subSets.get(i) + " ");
        }
    }

    @Override
    public void writeAnswer() throws IOException {
        System.out.print("False");
    }
}


