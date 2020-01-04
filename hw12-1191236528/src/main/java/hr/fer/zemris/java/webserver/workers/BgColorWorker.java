package hr.fer.zemris.java.webserver.workers;

import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class implements interface {@link IWebWorker}. This worker has to check
 * if some arguments were sent to him via html form. If that was the case, then it has to check if those arguments
 * are valid color names. If that is the case, then it changes the color of index2.html page to that color. 
 * Otherwise it generates new html document with message that color is not changed, and a button to return 
 * to index2.html page 
 * @author Lucija Valentić
 *
 */
public class BgColorWorker implements IWebWorker{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		Map<String, String> params = context.getParameters();
		
		String color = params.get("bgcolor");
		
		if(color != null && isValid(color.toUpperCase())) {
			color = "#"+color.toUpperCase();
			context.setPersistentParameter("bgcolor", color);
			
			context.write("<html><body>");
			context.write("<p>Boja jest promjenjena</p><br>");
			context.write("<form method=\"GET\" action=\"/index2.html\">");
			context.write("<input type=\"submit\" value=\"Vrati se na početnu stranicu!\"/>");
			context.write("</form>");
			context.write("</html></body>");
			
		}else {
		
			context.write("<html><body>");
			context.write("<p>Boja nije promjenjena</p><br>");
			context.write("<form method=\"GET\" action=\"/index2.html\">");
			context.write("<input type=\"submit\" value=\"Vrati se na početnu stranicu!\"/>");
			context.write("</form>");
			context.write("</html></body>");
		}
		
	}

	/**
	 * Method checks if given string is valid color name; more specifically
	 * given string has to be 6 digits long hex-number. It returns <code>true</code>
	 * if that is the case, and <code>false</code> if not.
	 * @param color String
	 * @return <code>true</code> if color is valid, <code>false</code> otherwise
	 */
	private boolean isValid(String color) {
		
		if(color.length() != 6) {
			return false;
		}
		
		char[] chars = color.toCharArray();
		
		for(int i = 0; i < 6; i++) {
			if(!Character.isDigit(chars[i]) && chars[i] !='A' && chars[i] != 'B' 
					&& chars[i] != 'C' && chars[i] != 'D' && chars[i] != 'E' && chars[i] != 'F') {
				return false;
			}
		}
		
		return true;
	}

}
