import java.io.File;
import java.util.*;

/**
 * Class to represent the Optimal-Algorithm page replacement
 */
public class OA {

    //private variable declarations
    private int pageFaults = 0;
    private int pageSize = 0;
    private String referenceString = "";
    //HashMap to hold the page value as its key, and its index within the ArrayList as its value
    private HashMap<Integer, Integer> map = new HashMap<>();
    //ArrayList to represent the current state of the page frame
    private ArrayList<Integer> memory = new ArrayList<>();
    //HashSet is used to allow us to easily check to see what pages are in the frame
    private HashSet<Integer> set = new HashSet<>();

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

        //Iterate through entire string, choosing each character one by one
        for(int i = 0; i < referenceString.length(); i++) {
            int currentPage = Character.getNumericValue(referenceString.charAt(i));
            //Checks for page faults
            if(set.contains(currentPage)) {
                pageFaults++;
                System.out.print(currentPage + " " + memory.toString());
                for(int s = 0; s < pageSize - memory.size(); s++) {
                    System.out.print("[ ]");
                }
                System.out.println(" PAGE FAULT");
            //If no page faults then insert the page into the frame
            } else {
                //If the memory is full, we need to choose the oldest inserted item to leave
                if(set.size() == pageSize) {
                    int pageToReplace = forsee(i, referenceString);                 //Call the forsee method to get the page to replace
                    set.remove(pageToReplace);                                      //Remove the page to replace from the set
                    set.add(currentPage);                                           //Add the current page into the set
                    int indexToReplace = map.get(pageToReplace);                    //Get the index of the page to replace by retreiving the value from the HashMap
                    map.remove(pageToReplace);                                      //Remove the pageToReplace from the hashmap
                    memory.set(indexToReplace, currentPage);                        //Set the index in the ArrayList to the new incoming page
                    map.put(currentPage, indexToReplace);                           //Add an entity into the map with the current page value as its key, and its index as its value
                //If the page has room, append the current page to the frame
                } else {
                    memory.add(currentPage);
                    set.add(currentPage);
                    map.put(currentPage, memory.indexOf(currentPage));
                }
                System.out.print(currentPage + " " + memory.toString());
                for(int s = 0; s < pageSize - memory.size(); s++) {
                    System.out.print("[ ]");
                }
                System.out.println("");
            }
            //System.out.println("Map: " + map.toString());
            //System.out.println("Set: " + set.toString());

        }
        System.out.println("Page faults: " + pageFaults);
        return pageFaults;
    }

    //Method that will calculate the closest distance-to-repeat of the current page being inserted into memory
    //and store it as a key-value pair in a HashMap
    private int forsee(int i, String referenceString) {
        int currentPageValue;
        int maxDistance = Integer.MIN_VALUE;
        int pageToReplace = -1;
        boolean found;

        //Loop to go through each value currently inside the page frame
        for(int k = 0; k < memory.size(); k++) {
            found = false;
            currentPageValue = memory.get(k);
            //Loop to go through each incoming page in the reference string from the current position
            for(int j = i+1; j < referenceString.length(); j++) {
                //If a match is found, calculate its difference
                if(currentPageValue == Character.getNumericValue(referenceString.charAt(j))) {
                    found = true;
                    int distance = j-i;
                    //If the difference is now the larges current value, replace maxDistance with the new distance
                    //and update the value of the pageToReplace
                    if(distance > maxDistance) {
                        maxDistance = distance;
                        pageToReplace = currentPageValue;
                    }
                    //Exit loop immediately
                    j = 1000;
                }
            }
            //If at the time a value does not have a future incoming page, return this value immediately
            if(!found) {
                return currentPageValue;
            }
        }
        return pageToReplace;
    }
}
