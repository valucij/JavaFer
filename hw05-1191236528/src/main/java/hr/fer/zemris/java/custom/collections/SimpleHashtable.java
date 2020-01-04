package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that represents a little bit more complicated 
 * implementation of map. Pairs (key, value), they can be any type,
 * are stored in special array, every slot in an array keeps a list,
 * so we can have more elements in this map then there are slots in array.
 * Next example shows how this class can be used.
 * 
 * <pre>
 * SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
 *		
 * examMarks.put("Ivana", 2);
 * examMarks.put("Ante", 2);
 * examMarks.put("Jasna", 2); 
 * examMarks.put("Kristina", 5);
 * examMarks.put("Ivana", 5); //overwrites old grade for Ivana
 *		
 * Integer kristinaGrade = examMarks.get("Kristina");
 *	
 * System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
 *	
 * System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
 * </pre>
 * 
 * @author Lucija Valentić
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V> >{
	/**
	 * Constant, default capacity of a SimpleHashtable
	 */
	private final static int DEFAULT_CAPACITY = 16;
	/**
	 * Array that keeps pairs of elements, more specifically lists
	 * of elements
	 */
	private TableEntry<K,V>[] table;
	/**
	 * Number of elements in SimpleHashTable
	 */
	private int size;
	/**
	 * Keeps track on how much has map changed
	 */
	private long modificationCount;
	/**
	 * Constructor, makes new array with capacity set to 
	 * <code>DEFAULT_CAPACITY</code>
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = new TableEntry[DEFAULT_CAPACITY];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Constructor, makes new array with capacity set to the power of 2,
	 * the one that is the same or the first bigger as given
	 * <code>capacity</code>. Throw an exception if given
	 * capacity is below 1
	 * 
	 * @param capacity
	 * @throws IllegalArgumentException() if given capacity is below 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		
		if(capacity < 1) {
			throw new IllegalArgumentException("Wrong Initial Capacity.");
		}
		table = new TableEntry[nextCapacity(capacity)];
		
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Calculates power of 2, that is same or first bigger as given number.
	 * 
	 * @param n
	 * @return power of 2
	 */
	private int nextCapacity(int n) {
		
		if( n == 1 || n % 2 == 0 ) {
			return n;
		}
		
		return (int)Math.ceil((double) Math.log(n) / Math.log(2));
	}
	
	/**
	 * Inserts pair (key, value), they can be of a type K and V respectively,
	 * the ones that SimpleHashTable was made. Key cannot be null,
	 * value can. If the pair with a given key
	 * already exists in the map, then it overwrites the value with this given
	 * one. If it doesn't, then it just inserts it in appropriate slot.
	 * Throws exception if given key is <code>null</code>
	 * 
	 * @param key
	 * @param value
	 * @throws NullPointerException() if given key is <code>null</code>
	 */
	public void put(K key, V value) {
		
		if(key == null) {
			throw new NullPointerException("Key cannot be null.");
		}
		
		if(containsKey(key)) {
			
			TableEntry<K,V> help = table[correspondingIndex(key)];
			while(!help.key.equals(key)) {
				help = help.next;
			}
			help.value = value;
			
			return;
			
		}else {
			
			int counterOccupation = 1;
			modificationCount++;
			
			TableEntry<K,V> help = table[correspondingIndex(key)];
			
			if(help == null) {
				table[correspondingIndex(key)] = new TableEntry<K,V>(key, value);
				table[correspondingIndex(key)].next = null;
				size++;
				return;
			}
			
			while(help.next != null) {
				help = help.next;
				counterOccupation++;
			}
			
			help.next = new TableEntry<K,V>(key,value);
			help = help.next;
			help.next = null;
			size++;

			if(needResize(counterOccupation)) {
				
				resize();
			}
			
			return;
			
		}
	}
	
	/**
	 * Checks if given slotSize is equal or larger than 75% of table capacity, 
	 * which means there is to much spilling in table.
	 * 
	 * @param slotSize
	 * @return <code>true</code> if there is too much elements in one slot,
	 * 			<code>false</code> otherwise
	 */
	private boolean needResize(int slotSize) {
				
		if(slotSize >= table.length * (double)75/100) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method resizes this hashtable, then it calls another
	 * function to fix elements that are in the wrong spot.
	 * It doesn't create new table, then it adds elements in it,
	 * in the right place of course, then it just 'steals' it.
	 * In the end, before fixing element's places, table is 
	 * essentially the same, elements are in the same spot as in the
	 * beginning, just capacity is doubled. 
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		
		TableEntry<K,V>[] newTable = new TableEntry[table.length * 2];
		
		for(int i = 0, n = table.length; i < n; i++) {
			newTable[i] = table[i];
		}
		
		table = newTable;
		searchIndexToDoSomething("fixhashtable", table.length / 2);
	}
	
	/**
	 * Returns index of a slot where element key
	 * should be. It is calculated with the help
	 * of a hashcode of a key
	 * 
	 * @param key
	 * @return index
	 */
	private int correspondingIndex(Object key) {	
		
		return Math.abs(key.hashCode())%table.length;
	}
	
	/**
	 * Returns value associated with a given key. If there is no pair
	 * in a map with this key, then it returns <code>null</code>. 
	 * Of course, if the key exists, and the value corresponding is
	 * <code>null</code>, there is no way of knowing if the that pair
	 * with given key exists, or it doesn't. For that, we have other methods.
	 * 
	 * @param key
	 * @return value associated to given key
	 */
	public V get(Object key) {
		
		if(!containsKey(key)) {
			return null;
		}
		
		TableEntry<K,V> help = table[correspondingIndex(key)];
		while(!help.key.equals(key)) {
			help = help.next;
		}
		
		return help.value;
	}
	
	/**
	 * Returns how many pairs there are in map.
	 * 
	 * @return this.size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if a pair with given key exists in a map.
	 * Given key can be <code>null</code>, but then
	 * <code>false</code> is returned.
	 * 
	 * @param key
	 * @return <code>true</code> if there is a pair
	 * 		  with the given key, <code>false</code>
	 * 			otherwise
	 */
	public boolean containsKey(Object key) {
		
		if(key == null) {
			return false;
		}
		
		TableEntry<K,V> help = table[correspondingIndex(key)];
		
		while(help!=null) {
			if(help.key.equals(key)) {
				return true;
			}
	
			help = help.next;
		}
		return false;
		
	}
	
	/**
	 * Checks if at least one pair with given value exists in a map.
	 *  
	 * @param value
	 * @return <code>true</code> if there is at least one pair
	 * 		  with the given value, <code>false</code>
	 * 			otherwise
	 */
	public boolean containsValue(Object value) {
		
		for(int i = 0, n = table.length; i < n; i++) {
			
			if(checkSlot(table[i], value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if there is at least one pair with given value in
	 * one slot of a array that represents hashtable.
	 * 
	 * @param slot
	 * @param value
	 * @return <code>true</code> if there is at least one pair
	 * 		  with a given value, <code>false</code> otherwise
	 */
	private boolean checkSlot(TableEntry<K,V> slot, Object value) {
		
		while(slot != null) {
			
			if(slot.value.equals(value)) {
				return true;
			}
			slot = slot.next;
		}
		return false;
	}
	/**
	 * Removes pair with a given value form the map.
	 * If the key doesn't exists, or if it's <code>null</code>,
	 * it does nothing.
	 * 
	 * @param key
	 */
	public void remove(Object key) {
		
		if(!containsKey(key)) {
			return;
		}
		
		modificationCount ++;
		TableEntry<K,V> help = table[correspondingIndex(key)];
		
		if(!help.key.equals(key)) {
			
			while(!help.next.key.equals(key)) {
				help = help.next;
			}
			help.next = help.next.next;
		}else {
			table[correspondingIndex(key)] = table[correspondingIndex(key)].next;
		}
		
		size--;
		return;
		
	}
	/**
	 * Checks if the map is empty.
	 * @return <code>true</code> if the map is empty,
	 * 		  <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Returns map in String form.
	 * @return string
	 */
	@Override
	public String toString() {
		
		String sb = "[";
		
		for(int i = 0, n = table.length; i < n; i++) {
			
			if(table[i] != null) {
				sb = sb + table[i].toString() +", ";
			}
			
		}
		
		try {
			sb = sb.substring(0, sb.length()-2);
		}catch(IndexOutOfBoundsException ex) {}
		
		return sb + "]";

	}
	/**
	 * Method removes all elements from the table. it calls another
	 * function to do this. We can go from each slot and remove
	 * elements, but this way the only slots that are visited will be
	 * the ones that actually have some elements in them.
	 */
	public void clear() {
		modificationCount++;
		searchIndexToDoSomething("clear", table.length);
	}
	
	/**
	 * Method helps when we want to visit every slot in table, 
	 * and do something (like resizing or removing elements) only on
	 * the ones that have some elements.
	 *  
	 * @param n represents size of a table. We have to use this n, because we
	 * 		 use this function while resizing table so length can change
	 * @param string it tells us what we want to do with elements when found
	 */
	private void searchIndexToDoSomething(String string, int n) {
		
		
		for(int i = 0; i < n; i++) {
			if(table[i] != null) {
				doSomething(i,string);
			}
		}
	
	}
	
	/**
	 * Method performs removing of elements, or fixing elements's place in table
	 * based on the given string. If the string is "clear", then it removes all the 
	 * elements from table on the position of given index.
	 * If string is "fixhashtable", then all the elements that are not supposed
	 * to be on position index of hashtable are moved to its right place.
	 * This method was called from method 
	 * <code>void searchIndexToDoSomething(int left, int right, String string)</code>
	 * so everything that it is done in this method, it is done only on one slot,
	 * that has at least one element.
	 * 
	 * @param index
	 * @param string
	 */
	private void doSomething(int index, String string) {
		
		if(table[index] == null) {
			return;
		}
		
		switch(string){
			
			case "clear":
				
				TableEntry<K,V> help = table[index];
				
				while(help.next != null) {
					
					size--;
					help = help.next;	
				
				}
				table[index].next = null;
				remove(table[index].key);
				break;
			
			case "fixhashtable":
				
				TableEntry<K,V> help2 = table[index];
				
				while(correspondingIndex(table[index].key) != index) {
					
					put(help2.key,help2.value);
					size--;
					try {
					
						table[index].key = table[index].next.key;
						table[index].value = table[index].next.value;
						table[index].next = table[index].next.next;
					
					}catch(NullPointerException ex) {table[index] = null; return;}
					
				}
				
				while(help2.next != null) {
					
					if(correspondingIndex(help2.next.key) != index) {

						put(help2.next.key, help2.next.value);
						size--;
						
						help2.next.key = null;
						help2.next.value = null;
						help2.next = help2.next.next;
						
					
					}else {
						
						help2 = help2.next;
					}
	
				}
				
				break;
			
		}
			
	}
	/**
	 * Method creates one iterator that passes through this map.
	 */
	public Iterator<SimpleHashtable.TableEntry<K,V> > iterator(){
		return this.new IteratorImpl();
	}
	/**
	 * Private class represents iterator that can pass through the map.
	 * It implements interface <code>Iterator<SimpleHashtable.TableEntry<K,V>></code>.
	 * 
	 * @author Lucija Valentić
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>>{
		/**
		 * Represents current slot where we are looking for next elements.
		 */
		private int currentSlot;
		/**
		 * One pair in a map, current one to be exact.
		 */
		private TableEntry<K,V> currentElement;
		/**
		 * Int that remembers how many changes there was before this iterator was made
		 */
		private long savedModificationCount;
		/**
		 * Int that keeps track whether some element has been removed or not,
		 * with the help of iterator of course.
		 */
		private int flagRemoved;
		/**
		 * Constructor
		 */	
		public IteratorImpl() {
			currentSlot = -1;
			flagRemoved = 0;
			currentElement = null;
			savedModificationCount = modificationCount;
		}
		/**
		 * Checks if there is more elements form the map that has not been returned.
		 * 
		 * @return <code>true</code> if there is more element to be returned,
		 * 			<code>false</code> otherwise
		 */
		public boolean hasNext() {
			
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Something was changed in a map");
			}
			
			if(flagRemoved == 1 && currentElement!= null) {
				return true;
			}
			
			int help = currentSlot;
			
			if(currentElement == null || currentElement.next == null) {
				help = nextNotEmptySlot(currentSlot);
			}
			
			if(help >= table.length) {
				return false;
			}
			
			return true;
			
		}
		/**
		 * Method searches for next slot of a hashtable that has at least one element
		 * 
		 * @param index
		 * @return index of a slot where there is some elements
		 */
		private int nextNotEmptySlot(int index) {
			
			for(int i = index + 1, n = table.length; i < n; i++) {
				if(table[i] !=null) {
					return i;
				}
			}
			
			return table.length;
		}
		
		/**
		 * Returns an element of a map, but it keeps track of which elements have been 
		 * already returned, so it returns next one
		 * 
		 * @return element of a hashtable, next one to be precise
		 */
		@SuppressWarnings("rawtypes")
		public SimpleHashtable.TableEntry next(){
			
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Something was changed in a map");
			}	
			/*If element was removed from hashtable using its method remove
			 * then we just have to return current element that is technically
			 * next element.*/
			if(flagRemoved == 1 && currentElement != null) {
				flagRemoved = 0;
				return currentElement;
			}
			
			if(currentElement == null || currentElement.next == null) {
				
				currentSlot = nextNotEmptySlot(currentSlot);
				if(currentSlot >= table.length) {
					throw new NoSuchElementException("There is no more elements in the list");
				}
				flagRemoved = 0;
				currentElement = table[currentSlot];
				return currentElement;
			}
			
			flagRemoved = 0;
			currentElement = currentElement.next;
			return currentElement;
			
		}
		/**
		 * This method <code>void remove()</code> when called with iterator,
		 * it removes element that has just been returned in previous iterator call.
		 */
		@Override
		public void remove() {
			
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Can't remove elements, modifications have been made");
			}
			/*If something was removed two times in the row using this function, exception is thrown*/
			if(flagRemoved == 1) {
				throw new IllegalStateException("Can't remove two elements in the row from map.");
			}
			
			TableEntry<K,V> help = currentElement.next;
		
			SimpleHashtable.this.remove(currentElement.key);
			currentElement = help;
			flagRemoved = 1;
			savedModificationCount++;
		}
		
	}
	/**
	 * Public class that represents one slot in hashtable.
	 * Every TableEntry<T,S> keeps one pair, and reference to another
	 * node, because that is actually a list, so we can have multiple
	 * pairs in the same slot of a hashtable. Key and value can be of any type
	 * 
	 * @author Lucija Valentić
	 *
	 * @param <T>
	 * @param <S>
	 */
	public static class TableEntry<T,S> {
		/**
		 * Key of a pair
		 */
		private T key;
		/**
		 * Value of a pair
		 */
		private S value;
		/**
		 * Referece to next node in list
		 */
		private TableEntry<T,S> next;
		
		/**
		 * Constructor
		 * @param key
		 * @param value
		 */
		public TableEntry(T key, S value) {
			this.key = key;
			this.value = value;
		}
		/**
		 * Returns key of this TableEntry
		 * @return this.key
		 */
		public T getKey() {
			return key;
		}
		/**
		 * Sets value of this TableEntry
		 * @param value
		 */
		
		public void setValue(S value) {
			this.value = value;
		}
		/**
		 * Returns value of this TableEntry
		 * @return this.value
		 */
		
		public S getValue() {
			return value;
		}
		
		/**
		 * Returns each list (more specifically each slot of a hashtable)
		 * in form of a string.
		 */
		@Override
		public String toString() {
			
			String sb = "";
			
			TableEntry<T,S> help = this;
			
			while(help != null) {
				
				sb = sb + help.key + "=" + help.value;
				if(help.next != null) {
					sb = sb + ", ";
				}
				help = help.next;
			}
			
			return sb;
		}
	}
}
