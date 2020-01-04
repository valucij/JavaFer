package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements interface {@link IWebWorker}. When called,
 * it generates new html document that writes out current date and time, 
 * and number of letters in given name. If no name was given, then
 * it writes out appropriate message
 * @author Lucija Valentić
 *
 */
public class HelloWorker implements IWebWorker{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		
		context.setMimeType("text/html");
		
		String name = context.getParameter("name");
		try {
			
			context.write("<html><body>");
			context.write("<h1>Hello!!!</h1>");
			context.write("<p>Now is: " + sdf.format(now) + "</p>");
			
			if(name == null || name.trim().isEmpty()) {
				context.write("<p>You didn't send your name</p>");
			}else {
				context.write("<p>Your name has " + name.trim().length() + " letters.</p>");
			}
			context.write("</body></html>");
			
		}catch(IOException ex) {
			
			ex.printStackTrace();
		}
	}

}
