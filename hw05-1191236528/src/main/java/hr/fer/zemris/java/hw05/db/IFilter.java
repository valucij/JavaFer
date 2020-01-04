package hr.fer.zemris.java.hw05.db;

/**
 * Interface that can be implemented so it 
 * returns true or false, based on if the given instance
 * of StudentRecord satisfies implementation
 * @author Lucija Valentić
 *
 */
public interface IFilter {
	/**
	 * Abstract method, usually receives record,
	 * and based on how it is implemented, returns
	 * <code>true</code> or <code>false</code> if
	 * that record satisfies or not, if it is valid
	 * based on implemented criteria.
	 * 
	 * @param record
	 * @return <code>true<code> if given record is
	 * 			valid based on implemented criteria,
	 * 			<code>false</code> otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
