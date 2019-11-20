import java.io.*;

public class Main {
    public static FIFO fifo = new FIFO();
    public static LRU lru = new LRU();
    public static OA oa = new OA();
    public static void main(String args[]) {
        final int TRIALS = 1;
        for(int k = 0; k < TRIALS; k++) {
            //Generate reference string of size 30
            String referenceString = "";
            for(int i = 0; i < 30; i++) {
                int rng = (int)(Math.random()*8);
                referenceString += Integer.toString(rng);
            }

            //Generate 4 text files for 3, 4, 5, and 6 number of page frame using the same reference String
            for(int j = 3; j < 7; j++) {
                try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test" + j + ".txt"), "utf-8"))) {
                    writer.write("NumberOfPageFrame value:\n" + j + "\nReference String:\n");
                    writer.write(referenceString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mainExecute();
        }
    }

    //Apply the 3 algorithms on each of the newly created text files (3 * 4 * 50) = 600 iterations
    public static void mainExecute() {
        for(int i = 3; i < 7; i++) {
            File file = new File("test" + i + ".txt");
            fifo.execute(file);
            lru.execute(file);
            oa.execute(file);
        }
    }
}
