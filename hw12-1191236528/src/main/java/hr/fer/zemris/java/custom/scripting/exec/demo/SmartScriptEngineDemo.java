package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.demo.TreeWriter;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class similar to {@link TreeWriter}, but this class
 * reads from smart scripts, and actully performs action written in them 
 * @author Lucija Valentić
 *
 */
public class SmartScriptEngineDemo {
	
	/**
	 * Main method called in the beginning of a program
	 * @param args arguments of command line
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if(args.length != 1) {
			throw new RuntimeException();
		}
		
		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		Map<String , String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		
		//ZADATAK 1: osnovni.smscr		
//		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
//				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

		
		//ZADATAK 2: zbrajanje.smscr
//		parameters.put("a", "4");
//		parameters.put("b", "2");
//		
//		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
//				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();		
		
		
		//ZADATAK 3: brojPoziva.smscr
//		persistentParameters.put("brojPoziva", "3");
//		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
//		
//		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), rc).execute();
//		
//		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
		
		
		//ZADATAK 4: fibonacci.smscr
//		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
//				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
		
		//ZADATAK 5: fibonaccih.smscr
		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
		
	}

}
