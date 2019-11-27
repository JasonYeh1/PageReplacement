import java.io.File;
import java.util.*;

/**
 * Class to represent the First-In-First-Out page replacement algorithm
 */
public class FIFO {

    //private variable declarations
    private int pageFaults = 0;
    private int pageSize = 0;
    private String referenceString = "";
    //HashSet is used to allow us to easily check to see what pages are in the frame
    private HashSet<Integer> set = new HashSet<>();
    //ArrayList to represent the page frame
    private ArrayList<Integer> memory = new ArrayList<>(pageSize);

    //Execution method to run the algorithm
    //params: file - Text file from which the algorithm can read from
    public int execute(File file) {

        //Try-catch block to read from the file and set the class variables
        try {
            Scanner input = new Scanner(file);
            while(input.hasNextLine()) {
                input.nextLine();
                pageSize = Integer.parseInt(input.nextLine());
//                System.out.println(pageSize);
                input.nextLine();
                referenceString = input.nextLine();
//                System.out.println(referenceString);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //int to store the oldest inserted index so that FIFO can be accomplished
        int oldestInsertedIndex = 0;
        //Iterate through entire string, choosing each character one by one
        for(int i = 0; i < referenceString.length(); i++) {
            int currentPage = Character.getNumericValue(referenceString.charAt(i));
            if(set.contains(currentPage)) {
                System.out.print(currentPage + " " + memory.toString());
                for(int s = 0; s < pageSize - memory.size(); s++) {
                    System.out.print("[ ]");
                }
                System.out.println(" PAGE FAULT");
            //If page faults then insert the page into the frame
            } else {
                pageFaults++;
                //If the memory is full, we need to choose the oldest inserted item to leave
                if(set.size() == pageSize) {
                    set.remove(memory.get(oldestInsertedIndex));                //remove the page from the set
                    set.add(currentPage);                                       //add the current page into the set
                    memory.set(oldestInsertedIndex, currentPage);               //Set the oldest inserted index to current page
                    if(oldestInsertedIndex == memory.size()-1) {                //If the oldest inserted index is equal to the page size - 1, set it back to 0
                        oldestInsertedIndex = 0;
                    } else {
                        oldestInsertedIndex++;                                  //Otherwise increment it
                    }
                //If the page has room, append the current page to the frame
                } else {
                    set.add(currentPage);
                    memory.add(currentPage);
                }
                //System.out.println("SET: " + set.toString());
                System.out.print(currentPage + " " + memory.toString());
                for(int s = 0; s < pageSize - memory.size(); s++) {
                    System.out.print("[ ]");
                }
                System.out.println("");
            }

        }
        System.out.println("Page faults: " + pageFaults);
        return pageFaults;
    }
}
