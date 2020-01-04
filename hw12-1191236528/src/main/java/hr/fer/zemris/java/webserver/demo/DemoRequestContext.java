package hr.fer.zemris.java.webserver.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Demo class for task 1 in homework
 * @author Lucija Valentić
 *
 */
public class DemoRequestContext {

	/**
	 * Main method called in the beginning of the program
	 * @param args arguments of command line
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");

	}

	/**
	 * First sub-program, reads from given file with given encoding
	 * @param filePath String
	 * @param encoding String
	 * @throws IOException
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(), new ArrayList<RequestContext.RCCookie>());
		
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		
		rc.write("Čevapčići i Šiščevapčići.");
		
		os.close();
	}

	/**
	 * Second sub-program, reads from given file with given encoding
	 * @param filePath String
	 * @param encoding String
	 * @throws IOException
	 */	
	private static void demo2(String filePath, String encoding) throws IOException {
		
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(), new ArrayList<RequestContext.RCCookie>());
		
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie("korisnik", "perica", 3600, "127.0.0.1", "/");
		rc.addRCCookie("zgrada", "B4", null, null, "/");
		
		rc.write("Čevapčići i Šiščevapčići.");
		
		os.close();
	}
}
