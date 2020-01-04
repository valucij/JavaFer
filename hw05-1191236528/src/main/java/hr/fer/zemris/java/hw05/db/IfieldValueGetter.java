package hr.fer.zemris.java.hw05.db;

/**
 * Interface that can be implemented to
 * catch/ get any information from one instance of
 * {@link StudentRecord}
 * 
 * @author Lucija Valentić
 *
 */
public interface IfieldValueGetter {
	/**
	 * Abstract method, usually gets some information from
	 * record.
	 * 
	 * @param record
	 * @return information from instance of StudentRecord
	 */
	public String get(StudentRecord record);
}
