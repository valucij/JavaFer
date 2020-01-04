package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class is class that helps class {@link ValueWrapper} in
 * performing actions with its value. Some class in this method
 * are written in the way that sometimes, some things are assumed. For
 * example, method returnInteger assumes that received object is string that
 * can be parsed into integer, or integer, or int. Of course, those methods can
 * assume those kind of things because they are called only in times when everything
 * about received object is checked and all important informations are know. Also
 * most of this methods are private, so user cannot use them, and break the program with them, 
 *  with possible exceptions. Methods that are public, user can use, but they are exception
 * safe, because they are either called in special times when everything that
 * can go wrong is checked, or they themselves make sure that they perform desired actions
 * without causing exception. 
 *  
 * @author Lucija Valentić
 *
 */
public class ValueWrapperUtil {

	/**
	 * Method checks if two received values are compatible. They are compatible
	 * only in these cases:
	 * 	1. value1 is integer/double (or string that can be parsed into that) and
	 * 		value2 is integer/double (or string that can be parsed into that)
	 * 	2. both value1 and value2 are null
	 * 	3. value1 or value2 are null, and other one is integer/double 
	 * 		(or string that can be parsed into that)
	 * 	4. value1 and value2 are both strings (and they cannot be parsed into
	 * 		integer or double, they are strings in traditional way), but in that
	 * 		case, received string has to be equal to string "compare"
	 * 
	 * If they are compatible, method return <code>true</code>,
	 * <code>false</code> otherwise
	 * @param value1
	 * @param value2
	 * @param string
	 * @return <code>true</code> if they are comaptible
	 * 		<code>false</code> otherwise
	 */
	public boolean isCompatible(Object value1, Object value2, String string) {
		
		if( !isIntegerOrDoubleOrNull(value1) || !isIntegerOrDoubleOrNull(value2)) {
			
			//ali ovo podrazumjeva da value1, value2 nisu ni stringovi koji bi mogli
			//postati int ili double. jer su tako funcije koje se pozivaju napravljene
			
			return( string.equals("compare") && isString(value1) && isString(value2));
			
		}else {
			return ( isIntegerOrDoubleOrNull(value1) && isIntegerOrDoubleOrNull(value2)); 
		}
		
	}
	
	/**
	 * Method performs action on two received values. Received string says
	 * what action should be performed. There are 4 actions: adding, subtracting, 
	 * multiplying, dividing and comparing. This method is called when it is made
	 * sure that action sent in the form of a string can be performed on given values.
	 * So this method is exception proof, and action will be performed and 
	 * some new value will be returned.
	 * @param value1
	 * @param value2
	 * @param string
	 * @return object
	 */
	public Object doOperation(Object value1, Object value2, String string) {
	
		double firstNumberD = 0.0;
		double secondNumberD = 0.0;
		int firstNumberI = 0;
		int secondNumberI = 0;
		
		if(isDouble(value1) || isDouble(value2)) {
			firstNumberD = returnDouble(value1);
			secondNumberD = returnDouble(value2);
		}else if( isIntegerOrDoubleOrNull(value1) || isIntegerOrDoubleOrNull(value2)){
			firstNumberI = returnInteger(value1);
			secondNumberI = returnInteger(value2);		
		}
		
		switch(string) {
		
		case "+":
			
			if(isDouble(value1) || isDouble(value2)) {
				return firstNumberD + secondNumberD;
			}else {
				return firstNumberI + secondNumberI;
			}	
				
			
		case "-":
		
			if(isDouble(value1) || isDouble(value2)) {
				return firstNumberD - secondNumberD;
				
			}else {
				return firstNumberI - secondNumberI;
			}	
				
		case "*":
			
			if(isDouble(value1) || isDouble(value2)) {
				return firstNumberD * secondNumberD;
				
			}else {
				return firstNumberI * secondNumberI;
			}	
				
		case "/":
			
			if(isDouble(value1) || isDouble(value2)) {

				if(secondNumberD == 0) {
					throw new RuntimeException("Can't divide with zero.");
				}
				
				return firstNumberD / secondNumberD;
				
			}else {
				
				if(secondNumberI == 0) {
					throw new RuntimeException("Can't divide with zero.");
				}
				
				return firstNumberI / secondNumberI;
			}	
			
			
		case "compare":
		
			if(!isIntegerOrDoubleOrNull(value1) && !isIntegerOrDoubleOrNull(value2)) {
				String first = (String) value1;
				String second = (String) value2;
				return first.compareTo(second);
				
			}else if( isDouble(value1) || isDouble(value2)) {
				
				if(firstNumberD < secondNumberD) {
					return -1;
				}else if(firstNumberD > secondNumberD) {
					return 1;
				}else {
					return 0;
				}				
			}else {
				
				if(firstNumberI < secondNumberI) {
					return -1;
				}else if(firstNumberI > secondNumberI) {
					return 1;
				}else {
					return 0;
				}	
			}
		}
		return 0;
	}
	
	/**
	 * Checks if received object is instance of String.
	 * Returns <code>true</code> if it is, <code>false</code>
	 * otherwise. 
	 * @param string
	 * @return <code>true</code> if object is string,
	 * 		<code>false</code> otherwise
	 */
	private boolean isString(Object object) {
		
//		System.out.println("object " + object);
//		
//		if(object instanceof String) {
//			System.out.println("da");
//		}else {
//			System.out.println("ne");
//		}
//		
//		if(object instanceof ValueWrapper) {
//			System.out.println("value wrapper da");
//		}else {
//			System.out.println("ne valuewrapper");
//		}
//		
//		if(object instanceof Integer) {
//			System.out.println("int da");
//		}else {
//			System.out.println("int ne");
//		}
//		
		return object instanceof String;
	}
	
	/**
	 * Method is called when it is made sure that given value is
	 * integer, or null, or string that can be parsed into integer.
	 * Transforms given value into integer and returns said integer.
	 * If value is equal to null, it returns 0.
	 * 
	 * @param value
	 * @return integer
	 */
	private int returnInteger(Object value) {
		
		if(value == null) {
			return 0;
		}
		
		if( isString(value)) {
			return Integer.parseInt((String)value);
		}else {
			return Integer.valueOf(String.valueOf(value));
		}
		
		
	}
	/**
	 * Method is called when it is made sure that given value is
	 * double, or null, or string that can be parsed into double.
	 * Transforms given value into double and returns said double.
	 * If value is equal to null, it returns 0.0 .
	 * 
	 * @param value
	 * @return double
	 */
	private double returnDouble(Object value) {
		
		if(value == null) {
			return 0.0;
		}
		
		if( isString(value)) {
			return Double.parseDouble((String)value);		
		}else {
			
			return Double.valueOf(String.valueOf(value));	
		}
	}
	
	/**
	 * Method checks if given object is integer, or 
	 * string that can be parsed into integer. Return <code>true</code>
	 * if it is, or <code>false</code> otherwise.
	 * @param integer
	 * @return <code>true</code> if given object is
	 * 		integer, <code>false</code> otherwise
	 */
	private boolean isInteger(Object integer) {
		
		if(isString(integer)) {
			try {
				Integer.parseInt((String)integer);
				return true;
			}catch(NumberFormatException ex) { 
				
				return false;
			}
		}else {
			return integer instanceof Integer;	
		}
	}
	
	/**
	 * Method checks if given object is double, or 
	 * string that can be parsed into double. Return <code>true</code>
	 * if it is, or <code>false</code> otherwise.
	 * @param double
	 * @return <code>true</code> if given object is
	 * 		double, <code>false</code> otherwise
	 */
	private boolean isDouble(Object doubble) {
		 
		if( isString(doubble)) {
			
			try {//ovo samo provjerava da slučajno string poslani
				//nije int, jer ako je int, uspjet će se parsat u double, i 
				//neće biti dobro
				Integer.parseInt((String) doubble);
				
				return false;
			}catch(NumberFormatException ex) { }
			
			try {
				Double.parseDouble((String)doubble);
				return true;
			}catch(NumberFormatException ex) { 
				
				return false;
			}
		}else {
			return doubble instanceof Double;		
		}
	}
	
	/**
	 * Method checks if given value is integer(or string that can be parsed
	 * into one), double (or string that can be parsed into one), or null.
	 * Method returns <code>true</code> if one of the above is true, <code>false</code>
	 * otherwise
	 * @param value
	 * @return <code>boolean</code>
	 */
	private boolean isIntegerOrDoubleOrNull(Object value) {
		
		return( isInteger(value) || isDouble(value) || value == null);
	}
}
