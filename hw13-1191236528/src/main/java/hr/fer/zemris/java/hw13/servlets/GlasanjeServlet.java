package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class extends {@link HttpServlet}. It it called 
 * "in the beginning" of a voting webapp. This class prepares list
 * of all bands user can vote on, and also prepare results, meaning,
 * in text file where results are kept, in the beginning of app ( but
 * only first time when app is started on user's computer, 
 * this doesn't happen if server is (re)started!), results are initialized, every band
 * gets 0 votes.
 * Then everything is sent to "/WEB-INF/pages/glasanjeIndex.jsp", and user can vote.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="g", urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// Učitaj raspoložive bendove
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<String> lines;
		
		try {
			lines = Files.readAllLines(Paths.get(fileName),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			return;
		}
		
		List<Integer> id = new ArrayList<Integer>();
		List<String> bandNames = new ArrayList<String>();
		
		for(String s : lines) {
			String[] temp = s.split("\t");
			
			id.add(Integer.parseInt(temp[0]));
			bandNames.add(temp[1]);
			
		}
		
		req.setAttribute("id", id);
		req.setAttribute("bandNames", bandNames);
		
		//podešavanje rezultata prvi put ako je potrebno
		fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		try {
			lines = Files.readAllLines(Paths.get(fileName),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			return;
		}
		
		if(lines.size() == 0 || lines.size() == 1) {
		
			
			List<String> forWriting = new ArrayList<String>();
			
			for(Integer i : id) {
				forWriting.add(String.valueOf(i) + "\t" + "0");
			}
			
			Path out = Paths.get(fileName);
			
			Files.write(out, forWriting, Charset.defaultCharset());
		}
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
