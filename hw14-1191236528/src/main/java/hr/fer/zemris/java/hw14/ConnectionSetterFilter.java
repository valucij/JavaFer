package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

/**
 * This class implements {@link Filter}. Through
 * this class passes every servlet once it is called before doing
 * its job. 
 * 
 * @author markocupic
 *
 */
@WebFilter(filterName="f1",urlPatterns={"/servleti/*"})
public class ConnectionSetterFilter implements Filter {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		DataSource ds = (DataSource)request.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Baza podataka nije dostupna.", e);
		}
		SQLConnectionProvider.setConnection(con);
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try { con.close(); } catch(SQLException ignorable) {}
		}
	}
	
}
