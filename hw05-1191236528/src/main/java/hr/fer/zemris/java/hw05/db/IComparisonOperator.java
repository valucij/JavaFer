package hr.fer.zemris.java.hw05.db;

/**
 * Interface with one method - satisfied.
 * Can be implemented in a way s owe can check
 * relation between two strings.
 * 
 * @author Lucija Valentić
 *
 */
public interface IComparisonOperator {
	/**
	 * Abstract method, usually receives two strings, and
	 * check in what kind of relation are they
	 * @param value1 string
	 * @param value2 string
	 * @return <code>true</code> if relation between value1
	 * 		and value2 is satisfied, <code>false</code> otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
