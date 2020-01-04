package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is something similar as the map, but special kind of map.
 * Usually, map can have multiple keys, but for each key, only one value
 * attached to it. With this special class, there can also be multiply keys,
 * but this time, with one key there can be more than one value attached to it.
 * (Those values attached to one key has to be different). So here, there is a map
 * where keys are instances of strings, and values are actually stacks with different
 * values for that key. Next example shows how this class can be used.
 * 
 * <pre>
 * 
 * ObjectMultistack multistack = new ObjectMultistack();
 * ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
 * multistack.push("year", year);
 * ValueWrapper price = new ValueWrapper(200.51);
 * multistack.push("price", price);
 * 
 * System.out.println("Current value for year: "
 * + multistack.peek("year").getValue());
 * 
 * System.out.println("Current value for price: "
 * + multistack.peek("price").getValue());
 * multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
 * 
 * System.out.println("Current value for year: "
 * + multistack.peek("year").getValue());
 * multistack.peek("year").setValue(
 * ((Integer)multistack.peek("year").getValue()).intValue() + 50
 * );
 * 
 * System.out.println("Current value for year: "
 * + multistack.peek("year").getValue());
 * multistack.pop("year");
 * 
 * System.out.println("Current value for year: "
 * + multistack.peek("year").getValue());
 * multistack.peek("year").add("5");
 * 
 * System.out.println("Current value for year: "
 * + multistack.peek("year").getValue());
 * multistack.peek("year").add(5);
 * 
 * System.out.println("Current value for year: "
 * + multistack.peek("year").getValue());
 * multistack.peek("year").add(5.0);
 * 
 * System.out.println("Current value for year: "
 * + multistack.peek("year").getValue());
 *
 * OUTPUT: 
 *
 * Current value for year: 2000
 * Current value for price: 200.51
 * Current value for year: 1900
 * Current value for year: 1950
 * Current value for year: 2000
 * Current value for year: 2005
 * Current value for year: 2010
 * Current value for year: 2015.0
 * 
 * 
 * </pre>
 * 
 * 
 * @author Lucija Valentić
 *
 */
public class ObjectMultistack {

	/**
	 * Internal map, it is special kind of map explained in javadoc
	 * of a class
	 */
	Map<String, MultistackEntry> internalMap;//drži samo glavu stoga 
	
	/**
	 * Constructor
	 */
	public ObjectMultistack() {
		internalMap = new HashMap<String, MultistackEntry>();
	}
	
	/**
	 * Pushes new value in stack that is attached to given keyValue in internalMap.
	 * If map doesn't contain key already, then it adds that new key to the map, and
	 * stack with just new value attached to it.
	 * @param keyName
	 * @param valueWrapper
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		
		if(!internalMap.containsKey(keyName)) {
			MultistackEntry entry = new MultistackEntry();
			entry.value = valueWrapper;
			entry.next = null;
			internalMap.put(keyName, entry);
		}else if( !containsValue(internalMap.get(keyName), valueWrapper) ){
			addToStack(internalMap.get(keyName), valueWrapper);
		}
		
	}
	
	/**
	 * It removes value on top of the stack that is attached to keyName. If it's stack
	 * is empty, exception is thrown. Also, after it removes said value, that value is 
	 * returned.
	 * 
	 * @param keyName
	 * @return ValueWrapper
	 * @throws ObjectStackException()
	 */
	public ValueWrapper pop(String keyName) {
		
		
		if( isEmpty(keyName)) {
			throw new ObjectStackException();
		}
		
		ValueWrapper value = peek(keyName);
		
		MultistackEntry help = internalMap.get(keyName);
		
		int newSize = sizeOfStack(help);
		
		MultistackEntry head = buildStack(help, newSize);
		
		internalMap.replace(keyName, head);
		
		return value;
	}
	
	/**
	 * Private function that build copy of a stack that is represented
	 * with given MultistackEntry help, and new stack will be of a size indicated
	 * with given int newSize. Usually, newSize is smaller that actually size of
	 * a stack represented by help. It returns newly made stack
	 * @param help
	 * @param newSize
	 * @return MultistackEntry
	 */
	private MultistackEntry buildStack(MultistackEntry help, int newSize) {

		if(newSize == 1) {
			return null;
		}
		
		MultistackEntry head = new MultistackEntry();
		head.value = help.value;
		head.next = null;
		
		for(int i = 0; i < newSize - 1; i++, help = help.next) {
			addToStack(head, help.value);
		}
		
		return head;
		
	}

	/**
	 * Method returns value on top of the stack that is mapped to keyName.
	 * If its stack is empty, exception is thrown
	 * @param keyName
	 * @return ValueWrapper
	 * @throws ObjectStackException()
	 */
	public ValueWrapper peek(String keyName) {
		
		if( !isEmpty(keyName)) {
			MultistackEntry help = internalMap.get(keyName);

			while(help.next != null) {
				help = help.next;
			}
			
			return help.value;
			
		}else {
			throw new ObjectStackException(); 
		}
	}
	
	/**
	 * Method check if stack mapped to keyName is empty
	 * or not. It returns <code>true</code> if it is, <code>false</code>
	 * otherwise
	 * 
	 * @param keyName
	 * @return <code>true</code> if stack si empty,
	 * 		<code>false</code> otherwise
	 */
	public boolean isEmpty (String keyName) {
		
		if( !internalMap.containsKey(keyName)) {
			return true;
		}else {
			return sizeOfStack(internalMap.get(keyName)) == 0;
		}
	}
	/**
	 * Represent one node in a stack. Stack is build like a linked
	 * list, so that's why one node has two variables, one is ValueWrapper
	 * value, and other is reference to next node in that stack (linked list)
	 * @author Lucija Valentić
	 *
	 */
	private static class MultistackEntry{
		private ValueWrapper value;
		private MultistackEntry next;
	}
	
	/**
	 * Method checks if given stack represented with head contains 
	 * given valueWrapper. It returns <code>true</code> if it does,
	 * <code>false</code> otherwise. Also, if received stack doesn't 
	 * exist (it is null), <code>false</code> is returned.
	 * @param head
	 * @param valueWrapper
	 * @return <code>true</code> if it contains value, <code>false</code>
	 * 		otherwise
	 */
	private boolean containsValue(MultistackEntry head, ValueWrapper valueWrapper) {
		
		if(head == null) {
			return false;
		}
		
		MultistackEntry help = head;
		
		while(help != null) {
			
			if(help.value.equals(valueWrapper)) {
				return true;
			}
			help = help.next;
		}
		return false;
	}
	
	/**
	 * It adds new value given as valueWrapper to stack given as MultistackEntry head. 
	 * This method is called "add" and not "push" because stack here is actually linked list,
	 * so we literally add value in the end of the list
	 * @param head
	 * @param valueWrapper
	 */
	private void addToStack(MultistackEntry head, ValueWrapper valueWrapper) {
		
		MultistackEntry newEntry = new MultistackEntry();
		newEntry.value = valueWrapper;
		newEntry.next = null;
		
		if(head == null) {
			head = newEntry;
			return;
		}
		
		MultistackEntry help = head;
		
		while(help.next != null) {
			help = help.next;
		}
		help.next = newEntry;
		
		
	}
	
	/**
	 * Returns size of a stack represented with given MultistackEntry head.
	 * Zero is returned if there is no elements, or if given stack is non
	 * existent
	 * @param head
	 * @return int
	 */
	private int sizeOfStack(MultistackEntry head) {
		
		int count = 0;
		MultistackEntry help = head;
		
		while(help != null) {
			count++;
			help = help.next;
		}
		
		return count;
		
	}
}
