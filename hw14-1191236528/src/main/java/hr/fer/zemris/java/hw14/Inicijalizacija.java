package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Class implements {@link ServletContextListener}. This is called in the
 * beginning and in the end of an application. In the beginning, connection
 * to some database is created - all the informations are read from alredy
 * prepared file.
 * 
 * @author Lucija Valentić
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		InputStream in;
		Properties p = new Properties();
		String fileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		try {
			in = new FileInputStream(fileName);
			p.load(in);
		} catch (FileNotFoundException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
		String host = p.getProperty("host");
		String port = p.getProperty("port");
		String name = p.getProperty("name");
		String user = p.getProperty("user");
		String pass = p.getProperty("password");
		
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + name + ";user=" + user + ";password=" + pass;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			throw new DAOException("Baza nije dostupna");
		}
		SQLConnectionProvider.setConnection(con);
		
		try {
			checkIfTableExists();
		} catch (SQLException | DAOException ignorable) {
			
		}finally {
			SQLConnectionProvider.setConnection(null);
			try { con.close(); } catch(SQLException ignorable) {}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method checks if tables -> Polls and PollOptions exist, and if
	 * they don't, then they are made and filled. Otherwise nothing happens.
	 * Also this method checks whether the Polls is empty table - if it
	 * is, then it is filled.
	 * 
	 * @throws SQLException
	 * @throws DAOException
	 */
	private void checkIfTableExists() throws SQLException, DAOException{
		
		DAOProvider.getDao().doesExistPollTableAndCreate();
		
		List<Poll> polls = DAOProvider.getDao().getAllPolls();
		
		if(polls == null || polls.size() == 0 || polls.size() < 2) {
			FillPollsUtil.fillPoll1();
			FillPollsUtil.fillPoll2();
		}
		
				
	}

}
