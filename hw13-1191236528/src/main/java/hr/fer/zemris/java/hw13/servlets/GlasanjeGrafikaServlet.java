package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;

/**
 * This class extends {@link HttpServlet}. It's job is to
 * make image of a chart out of voting results in voting app.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="glgraf", urlPatterns = {"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {

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
		
		String fileNameResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String fileNameInformations = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		Map<String, Integer> dataset = GlasanjeUtil.returnMapNamesAndPercVotes(fileNameResults, fileNameInformations);
		
		PieChart chart = new PieChart("Chart", dataset);
		
		ChartUtilities.writeChartAsPNG(out, chart.getChart(), 400, 400);
		out.flush();
		
	}
}
