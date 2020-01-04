package hr.fer.zemris.java.custom.collections;

/**
 * Class <code>Dictionary<K,V></code> is another type of collection 
 * where we can keep objects, more specifically, pairs of objects, 
 * something like a map. For every key (type of K) there is one
 * value (type of V). So in Dictionary, we can have multiple
 * same values, but we can only have different keys.
 * Each value has it's own key. Next we can show an example
 * of a dictionary.
 * 
 * (Ana, 5)
 * (Jasna, 5)
 * (Petra, 3)
 * and so on...
 * 
 * So as we see, all keys (now type String) are different
 * and some of the values (now type Integer) are the same.
 * Next is example how to use class <code>Dictionary<K,V></code>
 * 
 * <pre>
 * Dictionary<int,double> Dict = new Dictionary<int,double>();
 * Dict.put(7,4.2); -> COLLECTION consists of( (7,4.2) )
 * Dict.put(8,4.2); -> COLLECTION consists of( (7,4.2), (8,4.2) )
 * Dict.isEmpty(); -> OUTPUT: false
 * Dict.put("test",4); -> ERROR
 * Dict.put(7,5); -> -> COLLECTION consists of( (7,5.0), (8,4.2) )
 * Dict.size(); -> OUTPUT: 2
 * Dict.get(7); ->OUTPUT: 5.0
 * </pre>
 * 
 * @author Lucija Valentić
 *
 * @param <K> type of key
 * @param <V> type of value
 */
public class Dictionary<K,V> {
	
	/**
	 * private collection that keeps pairs of keys and values
	 */
	private ArrayIndexedCollection< Pair<K,V> > col;

	/**
	 * Constructor.
	 */
	public Dictionary(){
		col = new ArrayIndexedCollection< Pair<K,V> >();
	}
	
	/**
	 * Checks if the collection, dictionary, is empty.
	 * @return <code>true</code> if it's empty,
	 * 			<code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return col.size() == 0;
	}
	/**
	 * Returns size of collection, dictionary.
	 * @return int
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Puts pair (key, value) in the collection. Key
	 * cannot be <code>null</code>, value can. If collection
	 * already has a pair with the same key, then it rewrites
	 * the pair with new value; otherwise, it adds new pair
	 * with new key in the collection. If key is <code>null</code>
	 * it throws exception.
	 * 
	 * @param key
	 * @param value
	 * @throws NullPointerException()
	 */
	public void put(K key, V value) {
		 
		
		if(key == null) {
			throw new NullPointerException("Key cannot be null");
		}
		
		if(containsKey(key)) {
			
			int index = indexOfKey(key);
			
			if(index == -1) {
				return;
			}
			
			col.remove(index);
			col.insert(new Pair<K,V>(key,value), index);
						
		}else {
			col.add(new Pair<K,V>(key,value));
		}
		return;
		
	}
	
	/**
	 * Returns value that is paired with a given key. If
	 * that kind of key doesn't exist, or the given key is
	 * different type from keys from collection, this method
	 * returns null
	 * 
	 * @param key
	 * @return value mapped to given key, or <code>null</code>
	 * 		if the given key is not found in collection
	 */
	public V get(Object key) {
		
		if(containsKey(key)) {
			int index = indexOfKey(key);
			return col.get(index).getValue();
		}
		
		return null;
	}
	
	/**
	 * Gives an index of a pair in collection with a given key.
	 * If that kind of pair is not found, it returns -1.
	 * 
	 * @param key
	 * @return index of a pair with given key, or -1 otherwise
	 */
	private int indexOfKey(Object key) {
		
		if(!containsKey(key)) {
			return -1;
		}
		
		for(int i = 0, n = col.size(); i < n; i++) {
			Pair<K,V> helpPair = col.get(i);
			if(helpPair.getKey().equals(key)) {
				return i;
			}
		}
		return -1;
		
	}
	
	/**
	 * Method checks if there is a pair in collection
	 * with given key. Returns <code>true</code> if there is
	 * <code>false</code> otherwise.
	 * 
	 * @param key
	 * @return <code>true</code> if there is a pair with a given
	 * 		  key, <code>false</code> otherwise
	 */
	private boolean containsKey(Object key) {
		
		for(int i = 0, n = col.size(); i < n; i++) {
			Pair<K,V> helpPair = col.get(i);
			if(helpPair.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Method clears dictionary from any elements.
	 */
	public void clear() {
		
		col.clear();
	}
	/**
	 * Private class that represents pair of two objects,
	 * objects can be of any type.
	 *  
	 * @author Lucija Valentić
	 *
	 * @param <T>
	 * @param <S>
	 */
	private static class Pair<T,S>{
		/**
		 * key represents object of any type
		 */
		private T key;
		/**
		 * value represents object of any type
		 */
		private S value;
		/**
		 * Constructor 
		 * 
		 * @param newKey
		 * @param newValue
		 */
		Pair(T newKey, S newValue){
			key = newKey;
			value = newValue;
		}
		
		/**
		 * Returns key of a pair on which has been called
		 * @return key
		 */
		public T getKey() {
			return key;
		}
		/**
		 * Returns value of a pair on which has been called
		 * @return value
		 */
		public S getValue() {
			return value;
		}
	}
	
}
