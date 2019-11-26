import java.io.*;
import java.util.ArrayList;

/**
 * Main class to allow entry into the program
 */
public class Main {

    //Class ArrayLists to hold the trial averages for each algorithm
    static ArrayList<Integer> fifoTrials = new ArrayList<>();
    static ArrayList<Integer> lruTrials = new ArrayList<>();
    static ArrayList<Integer> oaTrials = new ArrayList<>();

    //Double to represent the number of trials to be held
    final static double TRIALS = 1;
    public static void main(String args[]) {

        //Loop to run through trials
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
        printResults();
    }

    //Apply the 3 algorithms on each of the newly created text files (3 * 4 * 50) = 600 iterations
    private static void mainExecute() {
        for(int i = 3; i < 7; i++) {
            FIFO fifo = new FIFO();
            LRU lru = new LRU();
            OA oa = new OA();
            File file = new File("test" + i + ".txt");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Page frame size: " + (i) + " First-In-First-Out");
            int fifoPageFaults = fifo.execute(file);
            fifoTrials.add(fifoPageFaults);

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Page frame size: " + (i) + " of Least-Recently-Used");
            int lruPageFaults = lru.execute(file);
            lruTrials.add(lruPageFaults);

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Page frame size: " + (i) + " of Optimal Algorithm");
            int oaPageFaults = oa.execute(file);
            oaTrials.add(oaPageFaults);
        }
    }

    //Print the results of each algorithm
    private static void printResults() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        int fifoSum3 = 0;
        int fifoSum4 = 0;
        int fifoSum5 = 0;
        int fifoSum6 = 0;
        for(int i = 0; i < fifoTrials.size(); i+=4) {
            fifoSum3 += fifoTrials.get(i);
            fifoSum4 += fifoTrials.get(i+1);
            fifoSum5 += fifoTrials.get(i+2);
            fifoSum6 += fifoTrials.get(i+3);
        }
        System.out.println("FIFO " + TRIALS + " page fault average for page frame size 3: " + fifoSum3/TRIALS);
        System.out.println("FIFO " + TRIALS + " page fault average for page frame size 4: " + fifoSum4/TRIALS);
        System.out.println("FIFO " + TRIALS + " page fault average for page frame size 5: " + fifoSum5/TRIALS);
        System.out.println("FIFO " + TRIALS + " page fault average for page frame size 6: " + fifoSum6/TRIALS);

        int lruSum3 = 0;
        int lruSum4 = 0;
        int lruSum5 = 0;
        int lruSum6 = 0;
        for(int i = 0; i < lruTrials.size(); i+=4) {
            lruSum3 += lruTrials.get(i);
            lruSum4 += lruTrials.get(i+1);
            lruSum5 += lruTrials.get(i+2);
            lruSum6 += lruTrials.get(i+3);
        }
        System.out.println("LRU " + TRIALS + " page fault average for page frame size 3: " + lruSum3/TRIALS);
        System.out.println("LRU " + TRIALS + " page fault average for page frame size 4: " + lruSum4/TRIALS);
        System.out.println("LRU " + TRIALS + " page fault average for page frame size 5: " + lruSum5/TRIALS);
        System.out.println("LRU " + TRIALS + " page fault average for page frame size 6: " + lruSum6/TRIALS);

        int oaSum3 = 0;
        int oaSum4 = 0;
        int oaSum5 = 0;
        int oaSum6 = 0;
        for(int i = 0; i < oaTrials.size(); i+=4) {
            oaSum3 += oaTrials.get(i);
            oaSum4 += oaTrials.get(i+1);
            oaSum5 += oaTrials.get(i+2);
            oaSum6 += oaTrials.get(i+3);
        }
        System.out.println("OA " + TRIALS + " page fault average for page frame size 3: " + oaSum3/TRIALS);
        System.out.println("OA " + TRIALS + " page fault average for page frame size 4: " + oaSum4/TRIALS);
        System.out.println("OA " + TRIALS + " page fault average for page frame size 5: " + oaSum5/TRIALS);
        System.out.println("OA " + TRIALS + " page fault average for page frame size 6: " + oaSum6/TRIALS);
    }




}
