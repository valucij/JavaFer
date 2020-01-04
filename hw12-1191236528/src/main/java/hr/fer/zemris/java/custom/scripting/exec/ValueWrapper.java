package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class stores one value that can be of any type. It also
 * has some methods that receives some other values, and then performs
 * actions on stored value. Of course, for that
 * actions to be performed, and for exception not to be thrown, stored
 * value and received value have to be compatible. Function that checks
 * compatibility is in class {@link ValueWrapperUtil}, and in javadoc
 * of that function it is explained when two values are compatible.
 * Actions that can be performed are: adding, subtracting, dividing, multiplying
 * and int comparing.
 * Next example shows how this class can be used.
 * 
 * <pre>
 * 
 * ValueWrapper v1 = new ValueWrapper(null);
 * ValueWrapper v2 = new ValueWrapper(null);
 * v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
 * 
 * ValueWrapper v3 = new ValueWrapper("1.2E1");
 * ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
 * v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
 * 
 * ValueWrapper v5 = new ValueWrapper("12");
 * ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
 * v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
 * 
 * ValueWrapper v7 = new ValueWrapper("Ankica");
 * ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
 * v7.add(v8.getValue()); // throws RuntimeException
 *
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */
public class ValueWrapper {

	/**
	 * Represents stored value
	 */
	private Object value;
	/**
	 * Help object whose methods check compatibility
	 * of values, and whose methods actually perform actions
	 * on values, and return the result
	 */
	private ValueWrapperUtil util;
	
	/**
	 * Constructor
	 * @param initialValue
	 */
	public ValueWrapper(Object initialValue) {
		this.value = initialValue;
		this.util = new ValueWrapperUtil();
	}
	
	/**
	 * Returns stored value
	 * @return this.value
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Sets stored value to given value
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * If incValue is compatible with stored value, then 
	 * it adds them together; otherwise it throws exception.
	 * 
	 * @param incValue
	 * @throws RuntimeException()
	 */
	public void add(Object incValue) {
		
		if(util.isCompatible(value, incValue, "+")) {
			this.value = util.doOperation(value, incValue, "+");
		}else {
			throw new RuntimeException("Can't add numbers, incompatible types");
		}
		
	}
	
	/**
	 * If decValue is compatible with stored value, then 
	 * it subtracts decValue from stored value; otherwise it throws exception.
	 * 
	 * @param decValue
	 * @throws RuntimeException()
	 */
	public void subtract(Object decValue) {
		
		if(util.isCompatible(value, decValue, "-")) {
			this.value = util.doOperation(value, decValue, "-");
		}else {
			throw new RuntimeException("Can't subtract numbers, incompatible types");
		}
		
	}
	
	/**
	 * If mulValue is compatible with stored value, then 
	 * it multiply them together; otherwise it throws exception.
	 * 
	 * @param mulValue
	 * @throws RuntimeException()
	 */
	public void multiply(Object mulValue) {
		
		if(util.isCompatible(value, mulValue, "*")) {

			
			this.value = util.doOperation(value, mulValue, "*");
		}else {
			throw new RuntimeException("Can't multiply numbers, incompatible types");
		}
		
	}
	
	/**
	 * If divValue is compatible with stored value, then 
	 * it divides stored value and divValue; otherwise it throws exception.
	 * Also, if divValue is 0, exception is thrown. 
	 * 
	 * @param divValue
	 * @throws RuntimeException()
	 */
	public void divide(Object divValue) {
		
		if(util.isCompatible(value, divValue, "/")) {
			this.value = util.doOperation(value, divValue, "/");
		}else {
			throw new RuntimeException("Can't divide numbers, incompatible types");
		}
		
	}
	
	/**
	 * If withValue is compatible with stored value, then 
	 * it compares them; otherwise it throws exception.
	 * If stored value and withValue are strings that cannot
	 * be parsed to double or integer (strings in traditional way),
	 * then it compares those two strings in a way that string are usually
	 * compared. 
	 * 
	 * @param withValue
	 * @throws RuntimeException()
	 */
	public int numCompare(Object withValue) {
		
		if(util.isCompatible(value, withValue, "compare")) {
			return (int)util.doOperation(value, withValue, "compare");
		}else {
			throw new RuntimeException("Can't comapare numbers, incompatible types");
		}
		
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
