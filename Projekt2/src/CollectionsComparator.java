import java.util.Collection;
import java.util.Comparator;

public class CollectionsComparator implements Comparator<Collection<?>> {

    @Override
    //-1 wiekszy od prawego, 1 prawy wiekszy od lewego, 0 rowny
    public int compare(Collection<?> c1, Collection<?> c2) {
        return Integer.compare(c1.size(),c2.size());
        //return o1.size()-o2.size();
    }
}
