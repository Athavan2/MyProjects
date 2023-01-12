import java.lang.Iterable;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

//Tp2 2015: Athavan Pathmanathan (20181966) et Vyncent Larose (20189960)
public class UnsortedTableMap<K,V>  {
    // physical storage for the map entries
    private ArrayList<NewEntry<K,V>> table = new ArrayList<>();

    // construct an initially empty map
    public UnsortedTableMap() {}

    // private utilities
    // return the index of the entry with key k, or -1 if not found
    private int findIndex( K k ) {
	int n = this.table.size();
	for( int j = 0; j < n; j++ )
	    if( this.table.get( j ).getKey().equals( k ) ) // found it
		return j;
	// none found
	return -1; // special value denoting not such entry
    }

    // number of entries in the map
    public int size() { return this.table.size(); }
    // return the value associated with the specified key, or null if not found
    public V get( K key ) {
	int j = this.findIndex( key );
	if( j == -1 ) return null; // not found
	return this.table.get( j ).getValue();
    }
    // check if an entry with given key exists
    public boolean containsKey( K key ) { return this.findIndex( key ) != -1; } 
    // associate the pair key-value, replacing existing value if any
    public V put( K key, V value ) {
	int j = findIndex( key );
	if( j == -1 ) { // not found, so insert the new pair
	    this.table.add( new NewEntry<>( key, value ) );
	    return null;
	} else // key exists
	    return this.table.get( j ).setValue( value ); // return old value
    }
    // remove the entry with specified key, if any, return its value
    public V remove( K key ) {
	int j = findIndex( key );
	if( j == -1 ) return null; // no such entry found
	int n = this.size();
	V retV = this.table.get( j ).getValue();
	// remove in O(1)
	if( j != n-1 ) // move last entry of the array to index j
	    this.table.set( j, this.table.get( n-1 ) );
	this.table.remove( n-1 ); // remove the last entry
	// remove in O(n)
	//this.table.remove( j );
	return retV;
    }
    // support for entrySet public method
    private class EntryIterator implements Iterator<NewEntry<K,V>> {
	private int j = 0;
	public boolean hasNext() { return j < table.size(); }
	public NewEntry<K,V> next() {
	    if( j == table.size() ) throw new NoSuchElementException();
	    return table.get( j++ );
	}
	public void remove() { throw new UnsupportedOperationException(); }
    }
    private class EntryIterable implements Iterable<NewEntry<K,V>> {
		public Iterator<NewEntry<K,V>> iterator() { return new EntryIterator(); }
    }
    public Iterable<NewEntry<K,V>> entrySet() { return new EntryIterable(); }

	public boolean isEmpty() { return this.size() == 0; }
	// support for public keySet method
	private class KeyIterator implements Iterator<K> {
		private Iterator<NewEntry<K,V>> entries = entrySet().iterator(); // reuse entrySet (in interface Map)
		public boolean hasNext() { return entries.hasNext(); }
		public K next() { return entries.next().getKey(); } // return key
		public void remove() { throw new UnsupportedOperationException(); }
	}
	private class KeyIterable implements Iterable<K> {
		public Iterator<K> iterator() { return new KeyIterator(); }
	}
	public Iterable<K> keySet() { return new KeyIterable(); }

	// support for public values
	private class ValueIterator implements Iterator<V> {
		private Iterator<NewEntry<K,V>> entries = entrySet().iterator(); // reuse entrySet (in interface Map)
		public boolean hasNext() { return entries.hasNext(); }
		public V next() { return entries.next().getValue(); } // return value
		public void remove() { throw new UnsupportedOperationException(); }
	}
	private class ValueIterable implements Iterable<V> {
		public Iterator<V> iterator() { return new ValueIterator(); }
	}
	public Iterable<V> values() { return new ValueIterable(); }
}