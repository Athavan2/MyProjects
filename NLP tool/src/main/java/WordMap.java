import java.util.*;

//Tp2 2015: Athavan Pathmanathan (20181966) et Vyncent Larose (20189960)
//Our wordMap class follows a ProbeHashMap structure.

public class WordMap<K,V> implements Map<K,V> {
    // attributes
    protected int n = 0; // number of entries in the map
    protected int capacity; // size of the table
    private int prime; // prime factor
    private long scale, shift; // shift and scale factors
    private Entry<K,V>[] table; // using a fixed array of entries
    private Entry<K,V> DEFUNCT = new NewEntry<>( null, null ); // sentinel

    /////////////////////////////////////Fonctions Overwritten//////////////////////////////////////////////////////////
    @Override
    public int size() { return n;}
    @Override
    public boolean isEmpty() {return this.size() == 0; } // AbstractMap
    @Override
    public boolean containsKey(Object key) {return bucketGet( hashValue((K) key ),(K) key ) != null; }
    @Override
    public boolean containsValue(Object value) {return values().contains((V)value); }
    @Override
    public V get(Object key) {return bucketGet( hashValue( (K) key ),(K) key );}
    @Override
    public V put(Object key, Object value) {
        V retV = bucketPut( hashValue( (K) key ), (K) key, (V) value );
        if( n > (Math.floor(capacity * 0.75)))    // keep load factor <= 0.5
            resize( (2 * capacity) + 1 ); // (or find a nearby prime)
        return retV;
    }
    @Override
    public V remove(Object key) {return bucketRemove( hashValue((K) key ),(K) key );}
    @Override
    public void putAll(Map m) {
        for (Object entry: m.entrySet()){
            Entry<K,V> entryTmp = (Entry<K,V>)entry;
            put(entryTmp.getKey(),entryTmp.getValue());
        }
    }
    @Override
    public void clear() {n=0; createTable();}
    @Override
    public Set<K> keySet() {return (Set<K>) new KeyIterable();}
    @Override
    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>();
        Iterator<V> valueSet = (new ValueIterable()).iterator();
        while(valueSet.hasNext()){
            values.add(valueSet.next());
        }
        return values;
    }// unused
    @Override
    public Set<Entry<K,V>> entrySet() {
        Set<Entry<K,V>> buffer = new HashSet<>();
        for( int h = 0; h < this.capacity; h++ )
            if( !isAvailable( h ) ) buffer.add( table[h] );
        return buffer;
    }

    //////////////////////////////////////Fin Fonctions Overwritten/////////////////////////////////////////////////////

    //////////////////////////////////////Fonctions ProbeHashMap////////////////////////////////////////////////////////

    public WordMap( int cap, int p ) {
        this.prime = p;
        this.capacity = cap;
        Random rand = new Random();
        this.scale = rand.nextInt( prime - 1 ) + 1;
        this.shift = rand.nextInt( prime );
        this.createTable();
    }
    public WordMap() { this( 11 ); }
    public WordMap(int cap ) { this( cap, 109345121 ); }

    // create an empty table of current capacity
    protected void createTable() {
        table = (Entry<K,V>[]) new Entry[this.capacity];
    }
    // return true if location is either empty or the 'defunct' sentinel
    private boolean isAvailable( int j ) { return ( table[j] == null || table[j] == DEFUNCT ); }
    // return index with key k, or -(a+1) so that k could be added at index a
    private int findSlot( int h, K k ) {
        int avail = -1; // no slot available (thus far)
        int j = h; // index for scanning the table
        do {
            if( isAvailable( j ) ) { // may be either empty or defunct
                if( avail == -1 ) avail = j; // first available slot
                if( table[j] == null ) break; // if empty, search fails immediately
            } else if( table[j].getKey().equals( k ) )
                return j; // successful search
            j = (j + 1) % this.capacity; // keep looking (cyclically)
        } while( j != h ); // stop if we return to the start
        return -(avail + 1); // search has failed
    }
    // return value associated with key k in bucket with hash value h, or else null
    protected V bucketGet( int h, K k ) {
        int j = findSlot( h, k );
        if( j < 0 ) return null; // no match found
        return table[j].getValue();
    }
    // associate key k with value v in bucket with hash value h; returns old value
    protected V bucketPut( int h, K k, V v ) {
        int j = findSlot( h, k );
        if( j >= 0 ) // this key has an existing entry
            return table[j].setValue( v );
        table[-(j+1)] = new NewEntry<>( k, v ); // convert to proper index
        this.n++;
        return null;
    }
    // remove entry having key k from bucket with hash value h, if any
    protected V bucketRemove( int h, K k ) {
        int j = findSlot( h, k );
        if( j < 0 ) return null; // nothing to remove
        V retV = table[j].getValue();
        table[j] = DEFUNCT; // mark this slot as deactivated
        this.n--;
        return retV;
    }

    ///////////////////////////////////////////Fin Fonctions ProbeHashMap///////////////////////////////////////////////

    ///////////////////////////////////////////Fonctions AbstractMap////////////////////////////////////////////////////
    // support for public keySet method
    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator(); // reuse entrySet (in interface Map)
        public boolean hasNext() { return entries.hasNext(); }
        public K next() { return entries.next().getKey(); } // return key
        public void remove() { throw new UnsupportedOperationException(); }
    }
    private class KeyIterable implements Iterable<K> {
        public Iterator<K> iterator() { return new KeyIterator(); }
    }

    // support for public values
    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator(); // reuse entrySet (in interface Map)
        public boolean hasNext() { return entries.hasNext(); }
        public V next() { return entries.next().getValue(); } // return value
        public void remove() { throw new UnsupportedOperationException(); }
    }
    private class ValueIterable implements Iterable<V> {
        public Iterator<V> iterator() { return new ValueIterator(); }
    }
    /////////////////////////////////////////Fin Fonctions AbstractMap//////////////////////////////////////////////////

    /////////////////////////////////////////Fonctions AbstractHashMap//////////////////////////////////////////////////
    // developer's utilities
    private int hashValue( K key ) {
        return (int)( ( Math.abs( key.hashCode() * scale + shift ) % prime ) % capacity );
    }

    private void resize( int newCap ) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for( Entry<K,V> e : this.entrySet() ) {
            buffer.add(e);
        }
        this.capacity = newCap;
        this.createTable(); // based on updated capacity
        this.n = 0; // wil be recomputed while reinserting entries
        for( Entry<K,V> e : buffer )
            put( e.getKey(), e.getValue() );
    }
    ////////////////////////////////////////Fin Fonctions AbstractHashMap///////////////////////////////////////////////
}
