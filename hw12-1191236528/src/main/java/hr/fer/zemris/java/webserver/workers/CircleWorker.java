package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements intraface {@link IWebWorker}. This class, when called
 * show pink circle. Pink circle is drawn inside a rectangle using {@link Graphics2D}
 * @author Lucija Valentić
 *
 */
public class CircleWorker implements IWebWorker{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 200, 200);
		g2d.setColor(Color.PINK);
		g2d.fillArc(0, 0, 200, 200, 0, 360);
		g2d.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		context.setMimeType("image/png");
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		}catch(IOException ex) {
			ex.printStackTrace();
			
		}
	}

}
