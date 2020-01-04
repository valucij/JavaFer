package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;

/**
 * This method extends {@link HttpServlet}. It's job
 * is to create image of a piechart.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="im", urlPatterns = {"/reportImage"})
public class ReportImageServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		resp.setContentType("image/png");
		
		OutputStream out = resp.getOutputStream();
		
		Map<String, Integer> dataset = new HashMap<>();
		
		dataset.put("Linux", 29);
		dataset.put("Mac", 20);
		dataset.put("Windows", 51);
		
		PieChart chart = new PieChart("Chart", dataset);
		
		ChartUtilities.writeChartAsPNG(out, chart.getChart(), 500, 270);
		out.flush();
	}
	
    
}
