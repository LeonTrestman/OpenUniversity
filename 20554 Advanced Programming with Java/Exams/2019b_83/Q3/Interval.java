import java.util.ArrayList;
import java.util.List;

public class Interval<T extends Comparable<T>> {
    T maxValue;
    T minValue;
    List<T> collectionOfT;

    public Interval(T minValue, T maxValue) throws Exception {
        if ( maxValue.compareTo(minValue) < 0 ) {
            throw new Exception("Out of bounds");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        collectionOfT = new ArrayList<>();
    }

    public void add (T element) throws Exception {
        if ( element.compareTo(minValue) < 0 || element.compareTo(maxValue) > 0 ) {
            throw new Exception("Out of bounds");
        }else
            collectionOfT.add(element);
    }

    public Interval<T> subInterval(T min, T max) throws Exception {
        if ( min.compareTo(max) >0 || min.compareTo(maxValue) > 0 || max.compareTo(minValue) < 0 ) {
            throw new Exception("Out of bounds");
        }
        Interval<T> subInterval = new Interval<>(min, max);
        for (T element : collectionOfT) {
            if ( element.compareTo(min) >= 0 && element.compareTo(max) <= 0 ) {
                subInterval.add(element);
            }
        }
        return subInterval;
    }
}


