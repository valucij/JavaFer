package hr.fer.zemris.gallery.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet}. It is used for displaying
 * picture on index.html. Pictures can be displayed in form of thumbnails, 
 * and in form of a actual picture. What will be displayed is decided
 * based on parameter "type". Send parameter "title" is a name
 * of a picture that needs to be displayed.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet("/picture")
public class PictureDisplayServlet extends HttpServlet {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		OutputStream out = resp.getOutputStream();
		
		String realPath = req.getServletContext().getRealPath("/WEB-INF/" + req.getParameter("type") + "/" + req.getParameter("title"));
		
		resp.setContentType("image/jpeg");
		
		try {
			BufferedImage image = ImageIO.read(Paths.get(realPath).toFile());
			ImageIO.write(image, "jpg", out);
		}catch(IIOException ignorable) {
		}
		
		
	}
}
