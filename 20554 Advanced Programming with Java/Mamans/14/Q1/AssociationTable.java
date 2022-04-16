/*Generic Association table class*/
import java.util.Iterator;
import java.util.TreeMap;

public class AssociationTable<T extends Comparable<T>, V> {
    private TreeMap<T, V> associationTable;

    public AssociationTable() {
        associationTable = new TreeMap<T, V>();
    }

    //fills the association table with keys and values of the given arrays
    //throws IllegalArgumentException if the amount of keys and values are not equal
    public AssociationTable(T[] keys, V[] values) throws IllegalArgumentException {

        if (!isEqualNumOfKeysAndValues(keys, values))
            throw new IllegalArgumentException("Number of Keys and Values are not equal");

        associationTable = new TreeMap<T, V>();
        for (int i = 0; i < keys.length; i++)
            associationTable.put(keys[i], values[i]);
    }

    //returns if the amount of keys and values are equal
    private boolean isEqualNumOfKeysAndValues(T[] keys, V[] values) {
        return keys.length == values.length;
    }

    //adds an entry to the association table
    public void add(T key, V value) {
        associationTable.put(key, value);
    }

    //returns the value associated with the key, or null if the key is not in the table
    public V get(T key) {
        return associationTable.get(key);
    }

    //returns if the association table contains the key
    public boolean contains(T key) {
        return associationTable.containsKey(key);
    }

    //remove a key from the association table, if the key was in the table returns true
    //otherwise returns false
    public boolean remove(T key) {
        V removed = associationTable.remove(key);
        return removed != null;
    }

    //returns the number of pair entries (key and value) in the association table
    public int size() {
        return associationTable.size();
    }

    //returns an iterator over the keys in the table
    public Iterator<T> keyIterator() {
        return associationTable.keySet().iterator();
    }

    @Override
    public String toString() {
        String result = "";
        for (Iterator<T> it = keyIterator(); it.hasNext(); ) {
            T key = it.next();
            result += "Key: " + key + " Value: " + this.get(key) + "\n";
        }
        return result;
    }

    //updates value of key if key exists in table,else does nothing
    public void updateKeyValue(T key, V value) {
        if (this.contains(key))
            this.associationTable.put(key, value);
    }

}
