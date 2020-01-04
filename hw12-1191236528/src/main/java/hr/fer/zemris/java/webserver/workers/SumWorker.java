package hr.fer.zemris.java.webserver.workers;

import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements interface {@link IWebWorker}. This class, when called, 
 * calculates sum of two arguments sent (if arguments are not integer, or if
 * they are not sent, then default values are 1 for a, and 2 for b), saves that
 * sum to temporary parameters, and saves name of one of two pictures. iF sum is even number,
 * then name of the first picture is saved, and if sum is not even , name of second picutre
 * is saved
 * @author Lucija Valentić
 *
 */
public class SumWorker implements IWebWorker{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		Map<String, String> params = context.getParameters();
		
		int a = 1;
		int b = 2;
		
		try {
			a = Integer.parseInt(params.get("a"));
		}catch(NullPointerException | NumberFormatException ignorable ) {
			
		}
	
		try {
			b = Integer.parseInt(params.get("b"));
		}catch(NullPointerException | NumberFormatException ignorable ) {
			
		}
		
		int sum = a + b;
		String result = String.valueOf(sum);
		
		context.setTemporaryParameter("zbroj", result);
		
		
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		
		String img = "";
		if(sum % 2 == 0) {
			img = "/images/prva.jpg";
		}else {
			img = "/images/druga.jpg";
		}
		
		context.setTemporaryParameter("imgName", img);
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		
	}

}
