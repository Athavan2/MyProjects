import java.util.*;
import java.util.Map;

//Tp2 2015: Athavan Pathmanathan (20181966) et Vyncent Larose (20189960)
//Our FileMap follows a ChainHashMap structure, also adding a functionality to quickly access the bigram.
public class FileMap<K,V> implements Map<K,V> {

    //attributes
    protected int n = 0; // number of entries in the map
    protected int capacity; // size of the table
    private int prime; // prime factor
    private long scale, shift; // shift and scale factors
    private ArrayList<String> voisins = new ArrayList<>(); // Contains all the neighbours of this word.
    private String prochainMotPlusReccurent= ""; // Contains the best bigram candidate for this word
    private int nombreDeRepetitions =0 ; // Contains the number of times the best bigram is present.
    private UnsortedTableMap<K,V>[] table;


    // Constructeurs (AbstractHashMap)
    public FileMap( int cap, int p ) {
        this.prime = p;
        this.capacity = cap;
        Random rand = new Random();
        this.scale = rand.nextInt( prime - 1 ) + 1;
        this.shift = rand.nextInt( prime );
        this.createTable();
    }
    public FileMap( int cap ) { this( cap, 109345121 ); }
    public FileMap() { this( 11 ); }

    //////////////////////////////////////Partie propre a nous//////////////////////////////////////////////////////////

    public void setNombreDeRepetitions(int nombreDeRepetitions) {
        this.nombreDeRepetitions = nombreDeRepetitions;
    }

    public String getProchainMotPlusReccurent() {
        return prochainMotPlusReccurent;
    }

    public void setProchainMotPlusReccurent(String prochainMotPlusReccurent) {
        this.prochainMotPlusReccurent = prochainMotPlusReccurent;
    }

    public UnsortedTableMap<K, V>[] getTable() {
        return table;
    }

    public void setTable(UnsortedTableMap<K, V>[] table) {
        this.table = table;
    }

    //This method adds a new neighbour also updating the best candidate for bigram
    public void addVoisin(String voisin) {
        voisins.add(voisin);
        if(prochainMotPlusReccurent.equals("")){ //premier voisin
            setProchainMotPlusReccurent(voisin);setNombreDeRepetitions(1);
        }
        else{
            int compteur = 0;
            for (String mot : voisins) {
                if (mot.equals(voisin)) {
                    compteur++;
                }
            }
            if (compteur > nombreDeRepetitions ||
                    (compteur == nombreDeRepetitions && (prochainMotPlusReccurent.compareTo(voisin) > 0))) {
                setProchainMotPlusReccurent(voisin);
                setNombreDeRepetitions(compteur);
            }
        }
    }


    //Fin partie propre a nous/////////////////////////////////////////////////////////////////////////////////////////

    // Fonctions Overwritten/////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int size() {return n; }
    @Override
    public boolean isEmpty() { return this.size() == 0;} // abstract map
    @Override
    public boolean containsKey(Object key) {return bucketGet( hashValue((K) key ), (K) key ) != null;  }
    //public boolean containsKey2(K key) {return bucketGet( hashValue( key ), key ) != null; }
    @Override
    public boolean containsValue(Object value) { return values().contains((V) value); } //Unused
    @Override
    public V get(Object key){return bucketGet( hashValue( (K) key ), (K) key );}
    //public V get2(K key) {return bucketGet( hashValue( key ), key );}   // AbstractHashMap
    @Override
    public V put( K key, V value ) {
        V retV = bucketPut( hashValue( key ), key, value );
        if( n > capacity / 2 ) // keep load factor <= 0.5
            resize( 2 * capacity - 1 ); // (or find a nearby prime)
        return retV;
    }

    @Override
    public V remove(Object key) {
        return bucketRemove( hashValue((K) key ), (K) key );
    }
    @Override
    public void putAll(Map m) {
        for (Object entry: m.entrySet()){
            Entry<K,V> entryTmp = (Entry<K,V>)entry;
            put(entryTmp.getKey(),entryTmp.getValue());
        }
    }
    @Override
    public void clear() {n=0;createTable();}
    @Override
    public Set<K> keySet() {return (Set<K>) new KeyIterable();}
    @Override
    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>();
        Iterator<V> valueSet = (new FileMap.ValueIterable()).iterator();
        while(valueSet.hasNext()){
            values.add(valueSet.next());
        }
        return values;
    }// unused
    @Override  // return an iterable collection of all key-value entries of the map
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> buffer = new HashSet<>();

        for(int h = 0; h < this.capacity; h++ )
            if( table[h] != null ) {
                Iterator<NewEntry<K, V>> entree = table[h].entrySet().iterator();
                while(entree.hasNext()){
                    buffer.add(entree.next());
                }
            }
        return buffer;
    }

    ///////////////////////////////////Fin fonctions a override////////////////////////////////////////////////////////

    ////////////////////////////Tout ce qui permet d'iterer sur nos entrees////////////////////////////////////////////
    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator(); // reuse entrySet (in interface Map)
        public boolean hasNext() { return entries.hasNext(); }
        public K next() { return entries.next().getKey(); } // return key
        public void remove() { throw new UnsupportedOperationException(); }
    }
    private class KeyIterable implements Iterable<K> {
        public Iterator<K> iterator() { return (Iterator<K>) new KeyIterator(); }
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

    // Fonctions de AbstractHashMap

    private int hashValue( K key ) { //Fonction de Hachage
        return (int)( ( Math.abs( key.hashCode() * scale + shift ) % prime ) % capacity );
    }

    private void resize( int newCap ) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for( Entry<K,V> e : this.entrySet() )
            buffer.add( e );
        this.capacity = newCap;
        this.createTable(); // based on updated capacity
        this.n = 0; // wil be recomputed while reinserting entries
        for( Entry<K,V> e : buffer )
            put( e.getKey(), e.getValue() );
    }

    // Fonctions de ChainHashMap
    protected void createTable() {
        table = (UnsortedTableMap<K,V>[]) new UnsortedTableMap[this.capacity];
        //table = (ProbeHashMap<K,V>[]) new ProbeHashMap[this.capacity];
    }
    // return value associated with key k in bucket with hash value h, or else null
    protected V bucketGet( int h, K k ) {
        UnsortedTableMap<K,V> bucket = table[h];
        //ProbeHashMap<K,V> bucket = table[h];
        if( bucket == null ) return null;
        return bucket.get( k );
    }
    // associate key k with value v in bucket with hash value h; returns old value
    protected V bucketPut( int h, K k, V v ) {
        UnsortedTableMap<K,V> bucket = table[h];
        //ProbeHashMap<K,V> bucket = table[h];
        if( bucket == null )
            bucket = table[h] = new UnsortedTableMap<>();
        //bucket = table[h] = new ProbeHashMap<>();
        int oldSize = bucket.size();
        V old = bucket.put( k, v );
        this.n += ( bucket.size() - oldSize ); // size may have increased
        return old;
    }
    // remove entry having key k from bucket with hash value h, if any
    protected V bucketRemove( int h, K k ) {
        UnsortedTableMap<K,V> bucket = table[h];
        //ProbeHashMap<K,V> bucket = table[h];
        if( bucket == null ) return null;
        int oldSize = bucket.size();
        V retV = bucket.remove( k );
        n -= ( oldSize - bucket.size() ); // size may have decreased
        return retV;
    }
}