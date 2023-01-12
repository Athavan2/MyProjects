import java.util.Map;

//Tp2 2015: Athavan Pathmanathan (20181966) et Vyncent Larose (20189960)
// This class is made so that both structures would hold entries implemented from the
// Map.Entry interface and hold the required functionnalities.
public class NewEntry<K,V>  implements Map.Entry<K,V> {
    private K k; // for the key
    private V v; // for the value
    public NewEntry(K key, V value ) {
        this.k = key;
        this.v = value;
    }
    public K getKey(){return this.k;} // return the key stored in the Entry
    public V getValue() { return this.v; } // return the value stored in the Entry
    public V setValue(V value) {
        V old = v;
        this.v = value;
        return old;
    }
    public String toString() { return "<" + this.getKey() + ":" + this.getValue() + ">"; }
}
