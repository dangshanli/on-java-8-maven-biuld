package container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class HelperUse {
    public static void main(String[] args) {
        Collection<Integer> collection= new ArrayList<>(Arrays.asList(1,2,3));
        Collections.addAll(collection,5,6,7);
        Integer[] moreInt={9,10,11};
        Collections.addAll(collection,moreInt);
        System.out.println(collection);





    }
}
