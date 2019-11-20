import java.io.File;
import java.util.*;

public class FIFO {

    public FIFO() {

    }

    public int execute(File file) {
        int pageFaults = 0;
        int pageSize = 0;
        String referenceString = "";
        HashSet<Integer> set = new HashSet<>();
        ArrayList<Integer> memory = new ArrayList<>(pageSize);
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
        int oldestInsertedIndex = 0;
        for(int i = 0; i < referenceString.length(); i++) {
            int currentPage = Character.getNumericValue(referenceString.charAt(i));
            System.out.println("-------------------------------------------- insert: " + currentPage);
            if(set.contains(currentPage)) {
                pageFaults++;
                System.out.println("PAGE FAULT");
                System.out.println("State: " + memory.toString());
            } else {
                if(set.size() == pageSize) {

                    //FIFO algorithm
                    //If the memory is full, we need to choose the oldest inserted item to leave
                    //should be at the 0 index
                    set.remove(memory.get(oldestInsertedIndex));
                    set.add(currentPage);
                    memory.set(oldestInsertedIndex, currentPage);
                    if(oldestInsertedIndex == memory.size()-1) {
                        oldestInsertedIndex = 0;
                    } else {
                        oldestInsertedIndex++;
                    }
                } else {
                    set.add(currentPage);
                    memory.add(currentPage);
                }
                //System.out.println("SET: " + set.toString());
                System.out.println("State: " + memory.toString());
            }

        }
        //System.out.println("Page faults: " + pageFaults);
        return pageFaults;
    }
}
