package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Lucija Valentić
 *
 */
public interface DAO {

	/**
	 * Creates table Polls
	 * @return boolean
	 * @throws DAOException
	 */
	public boolean createTablePolls() throws DAOException;
	
	/**
	 * Creates table PollOptions
	 * @return boolean
	 * @throws DAOException
	 */
	public boolean createTablePollOptions() throws DAOException;
	
	/**
	 * Inserts given poll into table Poll. Returns id of
	 * newly made poll
	 * 
	 * @param poll
	 * @return long id
	 * @throws DAOException
	 */
	public long insertIntoTablePolls(Poll poll) throws DAOException;
	
	/**
	 * Inserts given pollOption into table PollOptions. Returns id of newly
	 * created option
	 * @param pollOption
	 * @return long id
	 * @throws DAOException
	 */
	public long insertIntoTablePollOptions(PollOption pollOption) throws DAOException;
	
	/**
	 * Returns poll from database that has the same id as given one
	 * @param id
	 * @return poll
	 * @throws DAOException
	 */
	public Poll getPollById(long id) throws DAOException;
	
	/**
	 * Returns pollOption from database that has the same id as given one
	 * @param id
	 * @return pollOption
	 * @throws DAOException
	 */
	public PollOption getPollOptionById(long id) throws DAOException;
	
	/**
	 * Returns list of poll options from specific poll. Poll is determined
	 * by given pollId
	 * @param pollId
	 * @return List<PollOption>
	 * @throws DAOException
	 */
	public List<PollOption> getPollOptionsByIdPoll(long pollId) throws DAOException;
	
	/**
	 * Returns list of of polls from database
	 * @return List<Poll>
	 * @throws DAOException
	 */
	public List<Poll> getAllPolls() throws DAOException;
	
	/**
	 * Returns all poll options from database
	 * @return List<PollOption>
	 */
	public List<PollOption> getAllPollOptions();
	
	/**
	 * Checks whether table Poll exists in database, and creates
	 * it if it doesn't
	 * @throws DAOException
	 */
	public void doesExistPollTableAndCreate() throws DAOException;
	
	/**
	 * Checks whether table PollOptions exists in database, and creates it
	 * if it doesn't
	 * @throws DAOException
	 */
	public void doesExistPollOptionsTableAndCreate() throws DAOException;
	
	/**
	 * Increments one vote for some poll option. Poll option
	 * is determined by given id
	 * @param id
	 * @throws DAOException
	 */
	public void addVoteToPollOptionById(long id) throws DAOException;
	
}
