import java.io.File;
import java.util.*;

/**
 * Class to represent the Least-Recently-Used page replacement algorithm
 */
public class LRU {

    //private variable declarations
    private int pageFaults = 0;
    private int pageSize = 0;
    private String referenceString = "";
    //LinkedList to keep track of which page is the least recently used, the least recently used value will be at the front of the list
    private LinkedList<Integer> memory = new LinkedList<>();
    //HashSet is used to allow us to easily check to see what pages are in the frame
    private HashSet<Integer> set = new HashSet<>();
    //LinkedList to represent the actual page frame
    private LinkedList<Integer> state = new LinkedList<>();

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

        //System.out.println(pageSize);
        //System.out.println(referenceString);

        //Iterate through entire string, choosing each character one by one
        for(int i = 0; i < referenceString.length(); i++) {
            int currentPage = Character.getNumericValue(referenceString.charAt(i));
            if(set.contains(currentPage)) {

                //Update LinkedList position by appending the currentPage to the end and removing it from the LinkedList
                memory.addLast(currentPage);
                Integer intObj = Integer.valueOf(currentPage);
                memory.remove(intObj);
                //System.out.println("SET: " + set.toString());
                //System.out.println("LRU: " + memory.toString());
                System.out.print(currentPage + " " + state.toString());
                for(int s = 0; s < pageSize - state.size(); s++) {
                    System.out.print("[ ]");
                }
                System.out.println(" PAGE FAULT");
            //If page faults then insert the page into the frame
            } else {
                pageFaults++;
                //If the memory is full, we need to choose the oldest inserted item to leave
                if(set.size() == pageSize) {
                    //LRU algorithm to choose correct memory to replace
                    set.remove(memory.getFirst());                                  //Remove the least recently used page
                    set.add(currentPage);                                           //Add the current page into the set=
                    state.set(state.indexOf(memory.getFirst()), currentPage);       //Set the index of the least recently used page to the incoming page
                    memory.addLast(currentPage);                                    //Update the list so that the most currently inserted page is at the end
                    memory.removeFirst();                                           //Remove the oldest page from the list since it has been replace in the frame
                //If the page has room, append the current page to the frame
                } else {
                    set.add(currentPage);
                    state.add(currentPage);
                    memory.add(currentPage);
                }
                //System.out.println("SET: " + set.toString());
                //System.out.println("LRU: " + memory.toString());
                System.out.print(currentPage + " "  + state.toString());
                for(int s = 0; s < pageSize - state.size(); s++) {
                    System.out.print("[ ]");
                }
                System.out.println("");
            }
        }
        System.out.println("Page faults: " + pageFaults);
        return pageFaults;
    }
}


