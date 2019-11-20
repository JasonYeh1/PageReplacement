import java.io.File;
import java.util.*;

public class OA {
    public int execute(File file) {
        int pageFaults = 0;
        int pageSize = 0;
        String referenceString = "";
        LinkedList<Integer> memory = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();

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
                //System.out.println("PAGE FAULT");
            } else {
                if(set.size() == pageSize) {
                    //OA algorithm to choose correct memory to replace
                } else {
                    set.add(currentPage);
                    memory.add(currentPage);
                }
                //System.out.println("SET: " + set.toString());
                //System.out.println("ArrayList: " + memory.toString());
            }

        }
        return pageFaults;
    }
        
}
