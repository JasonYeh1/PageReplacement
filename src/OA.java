import java.io.File;
import java.util.*;

public class OA {

    private int pageFaults = 0;
    private int pageSize = 0;
    private String referenceString = "";
    private HashMap<Integer, Integer> map = new HashMap<>();
    private ArrayList<Integer> memory = new ArrayList<>();
    private HashSet<Integer> set = new HashSet<>();

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
            System.out.println("-------------------------------------------- insert: " + currentPage);
            if(set.contains(currentPage)) {
                pageFaults++;
                System.out.println("PAGE FAULT");
            } else {
                if(set.size() == pageSize) {
                    int pageToReplace = forsee(i, referenceString);
                    set.remove(pageToReplace);
                    set.add(currentPage);
                    System.out.println("pageToReplace: " + pageToReplace);
                    int indexToReplace = map.get(pageToReplace);
                    map.remove(pageToReplace);
                    memory.set(indexToReplace, currentPage);
                    map.put(currentPage, indexToReplace);

                } else {
                    memory.add(currentPage);
                    set.add(currentPage);
                    map.put(currentPage, memory.indexOf(currentPage));
                }
            }
            System.out.println("Map: " + map.toString());
            System.out.println("Set: " + set.toString());
            System.out.println("ArrayList: " + memory.toString());
        }
        return pageFaults;
    }

    //Method that will calculate the closest distance-to-repeat of the current page being inserted into memory
    //and store it as a key-value pair in a HashMap
    private int forsee(int i, String referenceString) {
        int currentPageValue;
        int maxDistance = Integer.MIN_VALUE;
        int pageToReplace = -1;
        boolean found;

        for(int k = 0; k < memory.size(); k++) {
            found = false;
            currentPageValue = memory.get(k);
            for(int j = i+1; j < referenceString.length(); j++) {
                if(currentPageValue == Character.getNumericValue(referenceString.charAt(j))) {
                    found = true;
                    int distance = j-i;
                    if(distance > maxDistance) {
                        maxDistance = distance;
                        pageToReplace = currentPageValue;
                    }
                    j = 1000;
                }
            }
            if(!found) {
                return currentPageValue;
            }
        }
        return pageToReplace;
    }
        
}
