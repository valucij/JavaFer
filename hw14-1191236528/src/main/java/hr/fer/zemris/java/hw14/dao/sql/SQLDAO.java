package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw14.FillPollsUtil;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author Lucija Valentić
 */
public class SQLDAO implements DAO {

	/**
	 * SQL that creates table Polls in database
	 */
	private static final String CREATE_TABLE_POLLS = "CREATE TABLE Polls("
			+ "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+ "title VARCHAR(150) NOT NULL,"
			+ "message CLOB(2048) NOT NULL)";
	
	/**
	 * SQL that creates table PollOptions in database
	 */
	private static final String CREATE_TABLE_POLL_OPTIONS = "CREATE TABLE PollOptions("
			+ "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+ "optionTitle VARCHAR(100) NOT NULL,"
			+ "optionLink VARCHAR(150) NOT NULL,"
			+ "pollId BIGINT,"
			+ "votesCount BIGINT,"
			+ "FOREIGN KEY (pollId) REFERENCES Polls(id))";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean createTablePolls() throws DAOException {
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			
			pst = con.prepareStatement(CREATE_TABLE_POLLS);
			pst.execute();
			
		}catch(Exception ex) {
			return false;
		}
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean createTablePollOptions() throws DAOException {
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			
			pst = con.prepareStatement(CREATE_TABLE_POLL_OPTIONS);
			pst.execute();
			
		}catch(Exception ex) {
			return false;
		}
		
		return true;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long insertIntoTablePolls(Poll poll) throws DAOException {
		
		long noviId = -1;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(
				"INSERT INTO Polls (title, message) values (?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, poll.getName());
			pst.setString(2, poll.getMessage());
			
			pst.executeUpdate();
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					noviId = rset.getLong(1);
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					throw new DAOException();
				}
			}
		} catch(SQLException ex) {
			throw new DAOException();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				throw new DAOException();
			}
		}
		
		return noviId;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override//vraća id pod kojim je insertano to
	public long insertIntoTablePollOptions(PollOption pollOption) throws DAOException {
		
		long noviId = -1;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(
				"INSERT INTO PollOptions (optionTitle, optionLink, pollId, votesCount) values (?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, pollOption.getOptionTitle());
			pst.setString(2, pollOption.getOptionLink());
			pst.setLong(3, pollOption.getPollId());
			pst.setLong(4, pollOption.getVotes());

			pst.executeUpdate();
			ResultSet rset = pst.getGeneratedKeys();
			
			try {
				if(rset != null && rset.next()) {
					noviId = rset.getLong(1);
				}
			} finally {
				try { rset.close(); } catch(SQLException ex) {
					throw new DAOException();
				}
			}
		} catch(SQLException ex) {
			throw new DAOException();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				throw new DAOException();
			}
		}
		
		return noviId;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Poll getPollById(long id) throws DAOException {
		Poll poll= null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						poll = new Poll();
						
						poll.setId(rs.getLong(1));
						poll.setName(rs.getString(2));
						poll.setMessage(rs.getString(3));
						
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata ankete.", ex);
		}
		
		return poll;
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PollOption getPollOptionById(long id) throws DAOException {
		
		PollOption option = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollId, votesCount from PollOptions where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						option = new PollOption();
						option.setId(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setPollId(rs.getLong(4));
						option.setVotes(rs.getLong(5));
					}
					
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata opcije za anketu.", ex);
		}
		
		return option;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PollOption> getPollOptionsByIdPoll(long pollId) throws DAOException {
		
		List<PollOption> list = new ArrayList<PollOption>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollId, votesCount from PollOptions where pollId=?");
			pst.setLong(1, Long.valueOf(pollId));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						
						PollOption option = new PollOption();
						option.setId(rs.getLong(1));
						option.setOptionLink(rs.getString(3));
						option.setOptionTitle(rs.getString(2));
						option.setPollId(rs.getLong(4));
						option.setVotes(rs.getLong(5));
						list.add(option);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			return null;
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Poll> getAllPolls() throws DAOException {
		
		List<Poll> list = new ArrayList<Poll>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, title, message from polls order by id");
			
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setName(rs.getString(2));
						poll.setMessage(rs.getString(3));
						list.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			return null;
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PollOption> getAllPollOptions() {
		List<PollOption> list = new ArrayList<PollOption>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollId, votesCount from PollOptions order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						
						PollOption option = new PollOption();
						option.setId(rs.getLong(1));
						option.setOptionLink(rs.getString(3));
						option.setOptionTitle(rs.getString(2));
						option.setPollId(rs.getLong(4));
						option.setVotes(rs.getLong(5));
						list.add(option);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			return null;
		}
		
		return list;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doesExistPollTableAndCreate() throws DAOException {
		
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			con.prepareStatement("select id, title, message from Polls order by id");
			
		} catch (SQLException e) {
			createTablePolls();	
			doesExistPollOptionsTableAndCreate();
			FillPollsUtil.fillPoll1();
			FillPollsUtil.fillPoll2();
		}
		
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doesExistPollOptionsTableAndCreate() throws DAOException {
		
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			con.prepareStatement("select id, optionTitle, optionLink, pollId, votesCount from PollOptions order by id");
		} catch (SQLException e) {
			createTablePollOptions();
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addVoteToPollOptionById(long id) throws DAOException {
		
		PollOption option = getPollOptionById(id);
		long vote = option.getVotes();
		vote++;
		
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("UPDATE PollOptions set votesCount=? WHERE id=?"); 
			
			pst.setLong(1, vote); 
			pst.setLong(2, option.getId());
			
			pst.executeUpdate();
			
		} catch(SQLException ex) {
			throw new DAOException();
		} finally {
			try { pst.close(); } catch(SQLException ex) {
				throw new DAOException();
			}
		}
		
	}


	
}
