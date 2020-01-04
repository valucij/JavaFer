package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements interface {@link IWebWorker}. This class generates
 * html document that writes out, in table, all arguments that were sent in.
 * If there is no arguments, nothing is written out.
 * @author Lucija Valentić
 *
 */
public class EchoParams implements IWebWorker{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		Map<String, String> params = context.getParameters();
		context.setMimeType("text/html");
		
		
		try {
			context.write("<table border=7 bordercolor=BLACK>");
			
			for(String key : params.keySet()) {
				context.write("<tr> <td>" + key + "</td> <td>" + params.get(key) + "</td></tr>");
			}
			
			context.write("</table>");
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}

}
