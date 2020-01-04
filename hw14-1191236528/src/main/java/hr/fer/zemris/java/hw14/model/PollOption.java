package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Class that represents objects that function like some poll
 * options
 * 
 * @author Lucija Valentić
 *
 */
public class PollOption {

	/**
	 * Id of a poll option
	 */
	private long id;
	/**
	 * Title of a option
	 */
	private String optionTitle;
	/**
	 * Link to a song of a option
	 */
	private String optionLink;
	/**
	 * Id of a poll which this option belongs to
	 */
	private long pollId;
	/**
	 * How many votes poll option has
	 */
	private long votes;
	
	/**
	 * Constructor
	 */
	public PollOption() {
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
	 * Returns this.optionTitle
	 * @return String
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets this.optionTitle
	 * @param optionTitle
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Returns this.optionLink
	 * @return String
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets this.optionLink
	 * @param optionLink
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Returns this.pollId
	 * @return long
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * Sets this.pollId
	 * @param pollId
	 */
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

	/**
	 * Returns this.votes
	 * @return long
	 */
	public long getVotes() {
		return votes;
	}

	/**
	 * Sets this.votes
	 * @param votes
	 */
	public void setVotes(long votes) {
		this.votes = votes;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return id + optionTitle + optionLink + votes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(votes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PollOption))
			return false;
		PollOption other = (PollOption) obj;
		return votes == other.votes;
	}
	
	
	
}
