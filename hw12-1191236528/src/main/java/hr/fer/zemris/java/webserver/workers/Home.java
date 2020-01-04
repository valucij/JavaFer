package hr.fer.zemris.java.webserver.workers;

import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements interface {@link IWebWorker}. This worker sets background color
 * parameter to temporary parameters, (if there is one), and opens up smart script
 * home.smscr. User, in open browser can then see page showing three links, and two forms, 
 * one for adding numbers, and one for changing colors. 
 * @author Lucija Valentić
 *
 */
public class Home implements IWebWorker{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String background = context.getPersistentParameter("bgcolor");
		
		Map<String, String> params = context.getParameters();
		
		if(background != null && !background.isBlank()) {
			
			context.setTemporaryParameter("background",background);
		}else {
			
			context.setTemporaryParameter("background", "#7F7F7F");
		}
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

	
}
