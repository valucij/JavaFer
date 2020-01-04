package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class extends {@link HttpServlet}. It's job is to
 * update voting results in voting app.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="gl", urlPatterns = {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		
		// Zabiljezi glas...
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		List<String> lines;
		
		try {
			lines = Files.readAllLines(Paths.get(fileName),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			return;
		}
		
		
		String newLine = "";
		int index = 0;
		List<String> copy = List.copyOf(lines);
		for(String s : copy) {
			
			String[] temp = s.split("\t");
			
			if(id.equals(temp[0])) {
				int votes = Integer.parseInt(temp[1]);
				votes++;
				newLine = id +"\t" + votes;
				lines.remove(index);
				break;
			}
			index++;
			
		}
		
		lines.add(index, newLine);
		
		Path out = Paths.get(fileName);
		
		Files.write(out, lines, Charset.defaultCharset());
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
