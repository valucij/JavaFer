package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents actual getters for
 * retrieving information from StudentRecord.
 * We can retrieve first name, last name, or jmbag.
 * 
 * @author Lucija Valentić
 *
 */
public class FieldValueGetters {

	/**
	 * Getter that returns first name from StudentRecord
	 */
	public static final IfieldValueGetter FIRST_NAME = 
			(record) -> {
				return record.getFirstName();
			};
	/**
	 * Getter that returns last name from StudentRecord
	 */
	public static final IfieldValueGetter LAST_NAME = 
			(record) -> {
				return record.getLastName();
			};
	/**
	 * Getter that returns jmbag from StudentRecord
	 */
	public static final IfieldValueGetter JMBAG = 
			(record) -> {
				return record.getJmbag();
			};
}
