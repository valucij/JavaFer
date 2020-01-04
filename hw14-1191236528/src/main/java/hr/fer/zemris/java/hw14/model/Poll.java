package hr.fer.zemris.java.hw14.model;

/**
 * Class that represents objects that function
 * like a poll
 * 
 * @author Lucija Valentić
 *
 */
public class Poll {

	/**
	 * Id of a poll
	 */
	private long id;
	/**
	 * Title/name of a poll
	 */
	private String name;
	/**
	 * Message of a poll
	 */
	private String message;
	
	/**
	 * Constructor
	 */
	public Poll() {
		
	}

	/**
	 * Returns this.id
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets this.id
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns this.name
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets this.name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns this.message
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets this.message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return id + name + message;
	}
	
}
