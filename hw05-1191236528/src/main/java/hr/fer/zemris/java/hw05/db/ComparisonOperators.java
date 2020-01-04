package hr.fer.zemris.java.hw05.db;

/**
 * This class keeps variables that are instances of 
 * {@link IComparisonOperator}. We can call method satisfied
 * of this variables( they have that method because they are
 * instances of {@link IComparisonOperator})
 * on two string (named value1, and value2),
 * to checked desired relationship. We usually check if 
 * string value1 is LESS/LESS_OR_EQUAL/... than the string
 * value2. Next example shows how this class can be used.
 * 
 * <pre>
 * 
 * ComparisonOperators.LESS.satisfied("A", "B") -> returns true;
 * ComparisonOperators.LESS_OR_EQUAL.satisfied("A", "B") -> returns true;
 * ComparisonOperators.GREATER.satisfied("A", "B") -> returns false;
 * ComparisonOperators.GREATER_OR_EQUAL.satisfied("A", "A") -> returns true;
 * ComparisonOperators.EQUAL.satisfied("A", "A") -> returns true;
 * ComparisonOperators.NOT_EQUAL.satisfied("A", "A") -> returns false;
 * ComparisonOperators.LIKE.satisfied("AAA", "A*") -> returns true;
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */
public class ComparisonOperators{

	/**
	 * This variable has method satisfied, that
	 * receives two strings, value1 and value2
	 * and returns <code>true</code> if string
	 * value1 is lesser than string value2.
	 * Otherwise, it returns <code>false</code>
	 */
	public static final IComparisonOperator LESS = 
			(value1, value2) -> {
				if(value1.compareTo(value2) == -1){
					return true;
				}else{
					return false;
				}
			};
		

	/**
	 * This variable has method satisfied, that
	 * receives two strings, value1 and value2
	 * and returns <code>true</code> if string
	 * value1 is lesser than string value2, or equal
	 * as value2.
	 * Otherwise, it returns <code>false</code>
	 */		
	public static final IComparisonOperator LESS_OR_EQUALS = 
			(value1, value2) -> {
				if(value1.compareTo(value2) == -1 || value1.compareTo(value2) == 0){
					return true;
				}else{
					return false;
				}
			};


	/**
	 * This variable has method satisfied, that
	 * receives two strings, value1 and value2
	 * and returns <code>true</code> if string
	 * value1 is greater than string value2.
	 * Otherwise, it returns <code>false</code>
	 */
	public static final IComparisonOperator GREATER = 
			(value1, value2) -> {
				if(value1.compareTo(value2) > 1){
					return true;
				}else{
					return false;
				}
			};


	/**
	 * This variable has method satisfied, that
	 * receives two strings, value1 and value2
	 * and returns <code>true</code> if string
	 * value1 is greater than string value2, or equal
	 * as value2.
	 * Otherwise, it returns <code>false</code>
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = 
			(value1, value2) -> {
				if(value1.compareTo(value2) > 1 || value1.compareTo(value2) == 0){
					return true;
				}else{
					return false;
				}
			};
	/**
	 * This variable has method satisfied, that
	 * receives two strings, value1 and value2
	 * and returns <code>true</code> if strings
	 * value1 and value2 are equal.
	 * Otherwise, it returns <code>false</code>
	 */
	public static final IComparisonOperator EQUALS = 
			(value1, value2) -> {
				if(value1.compareTo(value2) == 0){
					return true;
				}else{
					return false;
				}
			};

	/**
	 * This variable has method satisfied, that
	 * receives two strings, value1 and value2
	 * and returns <code>true</code> if strings
	 * value1 and value2 are not equal.
	 * Otherwise, it returns <code>false</code>
	 */
	public static final IComparisonOperator NOT_EQUALS = 
			(value1, value2) -> {
				if(value1.compareTo(value2) != 0){
					return true;
				}else{
					return false;
				}
			};

	/**
	 * This variable has method satisfied, that
	 * receives two strings, value1 and value2
	 * and returns <code>true</code>  there is 
	 * a substring value2 in string value1, but
	 * in special way. Value2 has special sign '*',
	 * which represents any character. So beginning
	 * of value1 has to be the same as substring of value2
	 * before the '*', and ending of value1 has to be the same
	 * as substring of value2 after the '*'. If there is 
	 * no '*' in value2, then value2 and value1 have to be
	 * equal. 
	 * Otherwise, it returns <code>false</code>
	 */
	public static final IComparisonOperator LIKE = 
			(value1, value2) -> {
				return isLike(value1, value2);
			};

	/**
	 * Method that helps LIKE variable. This method
	 * actually does everything described for variable
	 * LIKE, but this method was written so variable
	 * LIKE can be initialized in more simple fashion.
	 * There can not be two '*' characters, so exception
	 * is thrown if that happens		
	 * @param value1 string
	 * @param value2 string
	 * @return <code>true</code> if value1 is like value2,
	 * 			<code>false</false> otherwise
	 * @throws IllegalArgumentException 
	 */
	private static boolean isLike(String value1, String value2) {
		
		if(value2.indexOf('*') == -1) {
			return EQUALS.satisfied(value1, value2);
		}
		
		if(value2.indexOf('*') != value2.lastIndexOf('*')) {
			throw new IllegalArgumentException();
		}
		
		return beforeStar(value1, value2) && afterStar(value1, value2);		
				
	}		
	/**
	 * Helps method isLike, and check if
	 * everything is the same in string v1, and 
	 * string v2 before the character '*'
	 * Returns <code>true</code> if it is, <code>false</code>
	 * if it's not.
	 * 
	 * @param v1
	 * @param v2
	 * @return <code>true</code> if it's the same
	 * 			<code>false</code> otherwise
	 */
	private static boolean beforeStar(String v1, String v2) {
		
		if(v1.length() < (v2.length() - 1) ) {
			return false;
		}
		
		char[] value1 = v1.toCharArray();
		char[] value2 = v2.toCharArray();
		int i = 0;
		int j = 0;
		int n = v1.length();
		
		while(true) {
			
			if( (value1[i] != value2[j]) && value2[j] != '*') {
				return false;
			}else if(value2[j] == '*') {
				break;
			}
			
			i++;
			j++;
			
			if(i == n) {
				break;
			}
		}
		
		return true;
	}
	/**
	 * Helps method isLike, and check if
	 * everything is the same in string v1, and 
	 * string v2 after the character '*'
	 * Returns <code>true</code> if it is, <code>false</code>
	 * if it's not.
	 * 
	 * @param v1
	 * @param v2
	 * @return <code>true</code> if it's the same
	 * 			<code>false</code> otherwise
	 */
	private static boolean afterStar(String v1, String v2) {
		
		if(v1.length() < (v2.length() - 1) ) {
			return false;
		}
		
		char[] value1 = v1.toCharArray();
		char[] value2 = v2.toCharArray();
		int i = v1.length() - 1;
		int j = v2.length() - 1;
		
		while(true) {
			
			if( (value1[i] != value2[j]) && value2[j] != '*') {
				return false;
			}else if(value2[j] == '*') {
				break;
			}
			
			i--;
			j--;
			
		}
		
		return true;
	}
	
	
}
