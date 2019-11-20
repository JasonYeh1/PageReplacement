import java.io.File;
import java.util.*;

public class LRU {
    public int execute(File file) {
        int pageFaults = 0;
        int pageSize = 0;
        String referenceString = "";
        LinkedList<Integer> memory = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();
        LinkedList<Integer> state = new LinkedList<>();

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
            System.out.println("-------------------------------------------- insert: " + currentPage);
            if(set.contains(currentPage)) {
                pageFaults++;
                //Update LinkedList position by appending the currentPage to the end and removing it from the LinkedList
                memory.addLast(currentPage);
                Integer intObj = Integer.valueOf(currentPage);
                memory.remove(intObj);
                System.out.println("PAGE FAULT");
                //System.out.println("SET: " + set.toString());
                //System.out.println("LRU: " + memory.toString());
                System.out.println("State: " + state.toString());
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
                System.out.println("State: " + state.toString());
            }

        }
        return pageFaults;
    }
}
