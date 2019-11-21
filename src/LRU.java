import java.io.File;
import java.util.*;

public class LRU {

    private int pageFaults = 0;
    private int pageSize = 0;
    private String referenceString = "";
    private LinkedList<Integer> memory = new LinkedList<>();
    private HashSet<Integer> set = new HashSet<>();
    private LinkedList<Integer> state = new LinkedList<>();

    public int execute(File file) {


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

        for(int i = 0; i < referenceString.length(); i++) {
            int currentPage = Character.getNumericValue(referenceString.charAt(i));
            if(set.contains(currentPage)) {
                pageFaults++;
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
            } else {
                if(set.size() == pageSize) {
                    //LRU algorithm to choose correct memory to replace
                    set.remove(memory.getFirst());
                    set.add(currentPage);
                    state.set(state.indexOf(memory.getFirst()), currentPage);
                    memory.addLast(currentPage);
                    memory.removeFirst();
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
        return pageFaults;
    }
}
