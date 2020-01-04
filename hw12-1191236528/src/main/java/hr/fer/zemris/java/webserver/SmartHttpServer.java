package hr.fer.zemris.java.webserver;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
/**
 * Class represent web server that can handle and
 * show some html documents and some smart scripts
 * @author Lucija Valentić
 *
 */
public class SmartHttpServer {

	/**
	 * Address
	 */
	@SuppressWarnings("unused")
	private String address;
	/**
	 * Host name
	 */
	private String domainName;
	/**
	 * Port
	 */
	private int port;
	/**
	 * How many threads are there
	 */
	private int workerThreads;
	/**
	 * How long one session lasts
	 */
	private int sessionTimeout;
	/**
	 * All supported mimeTypes
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Thread representing {@link #serverThread}
	 */
	private ServerThread serverThread;
	/**
	 * Thread representing {@link #demonicThread}
	 */
	private DemonicThread demonicThread;
	/**
	 * Thread pool
	 */
	private ExecutorService threadPool;
	/**
	 * Path of root document
	 */
	private Path documentRoot;
	/**
	 * Map of all supported workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	/**
	 * Map of all sessions
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Generator for random value
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Method called in the beginning of a program 
	 * @param args arguments of command line
	 */
	public static void main (String[] args) {
		new SmartHttpServer(args[0]);
	}
	
	/**
	 * New private class, represent one session, and all the information about the session
	 * @author Lucija Valentić
	 *
	 */
	private static class SessionMapEntry{

		/**
		 * Current session id
		 */
		private String sid;
		/**
		 * Host name
		 */
		private String host;
		/**
		 * For how long session is valid
		 */
		private long validUntil;
		/**
		 * Map of parameters
		 */
		Map<String, String> map;
		
		/**
		 * Constructor
		 * @param sid String
		 * @param host String
		 * @param validUntil long
		 * @param map Map<String, String>
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

	}
	
	/**
	 * Constructor
	 * @param configFileName String
	 */
	@SuppressWarnings("deprecation")
	public SmartHttpServer(String configFileName) {
		
		InputStream in;
		Properties p = new Properties();
		try {
			in = new FileInputStream(configFileName);
			p.load(in);
		} catch (FileNotFoundException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	
		this.address = p.getProperty("server.address");
		this.domainName = p.getProperty("server.domainName");
		this.port = Integer.parseInt(p.getProperty("server.port"));
		this.workerThreads = Integer.parseInt(p.getProperty("server.workerThreads"));
		this.documentRoot = Paths.get(p.getProperty("server.documentRoot"));
	
		String mimeFilePath = p.getProperty("server.mimeConfig");
		this.sessionTimeout = Integer.parseInt(p.getProperty("session.timeout"));
		
		String workerPath = p.getProperty("server.workers");
		
		try {
			in = new FileInputStream(mimeFilePath);
			p.load(in);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
		mimeTypes.put("html", p.getProperty("html"));
		mimeTypes.put("htm", p.getProperty("htm"));
		mimeTypes.put("txt", p.getProperty("txt"));
		mimeTypes.put("gif", p.getProperty("gif"));
		mimeTypes.put("png", p.getProperty("png"));
		mimeTypes.put("jpg", p.getProperty("jpg"));
		
		try {
			in = new FileInputStream(workerPath);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
		String lines = "";
		
		try {
			lines = new String(in.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			
		}
		
		String[] split = lines.split("\n");
		
		for(String s : split) {
			
			String[] temp = s.split("=");
			String path = temp[0];
			String fqcn = temp[1];
			
			Class<?> referenceToClass;
			Object newObject = null;
			
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn.trim());
				newObject = referenceToClass.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
			IWebWorker iww = (IWebWorker)newObject;
			
			workersMap.put(path.trim(), iww);
			
		}
		try {
			in.close();
		} catch (IOException ignorable) {
			
		}
		
		start();
		
		
	}
	/**
	 * Called when server is started. It starts all threads, and initializes thread pool
	 */
	protected synchronized void start() {
		
		if(threadPool == null) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
		
		if(serverThread == null) {
			serverThread = new ServerThread();
			serverThread.start();
			
		}
		
		if(demonicThread == null) {
			demonicThread = new DemonicThread();
			demonicThread.setDaemon(true);
			demonicThread.start();
		}
	}
	
	/**
	 * Demonic thread, it removes, every 5 minutes, all sessions that are
	 * not longer alive. Everything resets.
	 * @author Lucija Valentić
	 *
	 */
	protected class DemonicThread extends Thread{
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			
			while(true) {

				Map<String, SessionMapEntry> copy = Map.copyOf(sessions);
				
				for(String s : copy.keySet()) {
					
					SessionMapEntry entry = sessions.get(s);
					if(entry.validUntil < System.currentTimeMillis()) {
						sessions.remove(s);
					}
				}
				
				try {
					sleep(300000);
				} catch (InterruptedException ignorable) {
					return;
				}	
			}
		}
	}
	/**
	 * Called in the end of server, it shutsdown thread pools, and stops the threads
	 */
	protected synchronized void stop() {
		
		try {
			serverThread.serverSocket.close();
			threadPool.shutdown();
		} catch ( NullPointerException | IOException e) {
			
		}finally{
			serverThread = null;
			threadPool = null;
		}
		
	}
	/**
	 * Class implements {@link Thread}. It represents server thread, the
	 * one the handles client request
	 * @author Lucija Valentić
	 *
	 */
	protected class ServerThread extends Thread{
		
		ServerSocket serverSocket;
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {

			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress)null, port));
			} catch (IOException e) {
				throw new RuntimeException();
			}
			
			while(true) {
				
				Socket toClient;
				try {
					toClient = serverSocket.accept();
				} catch (IOException e) {
					break;
				}
				
				ClientWorker cw = new ClientWorker(toClient);
				threadPool.execute(cw);	
			}	
			
		}
	}
	
	/**
	 * Implements {@link Runnable} and {@link IDispatcher}.  
	 * Handles client request
	 * @author Lucija Valentić
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher{
		
		/**
		 * Client socket
		 */
		private Socket csocket;
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream
		 */
		private OutputStream ostream;
		/**
		 * Version
		 */
		private String version;
		/**
		 * Method
		 */
		private String method;
		/**
		 * Host name
		 */
		private String host;
		/**
		 * Parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Persistent parameters
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		/**
		 * List of cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Current session id
		 */
		private String SID;
		/**
		 * Internal {@link RequestContext}
		 */
		private RequestContext context = null;
		
		/**
		 * Constructor
		 * @param csocket Socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
		
			try {
				
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
			} catch (IOException e) {
				
				throw new RuntimeException();
			}
			
			byte[] requestRead;
			String requestStr = null;
			try {
				requestRead = readRequest(istream);
				if(requestRead==null) {
					sendError(ostream, 400, "Bad request", version);
					return;
				}
				requestStr = new String(
					requestRead, 
					StandardCharsets.ISO_8859_1
				);
			} catch (IOException e2) {
				return;
				//throw new RuntimeException();
			}
			
			List<String> request = extractHeaders(requestStr);
			
			if(request == null || request.size() < 1) {
				try {
					sendError(ostream, 400, "Invalid header", version);
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
			
			String firstLine = request.get(0);
			String requestedPath;
			try {
				requestedPath = extract(firstLine);
			} catch (IOException ignorable) {
				return;
			}
			
			if(requestedPath == null || requestedPath.isBlank()) {
				return;
			}
			
			assignHost(request);
			
			checkSession(request);
			
			//split requestedPath
			String path = null;
			String paramString = null;
			
			if(requestedPath.indexOf('?') != -1) {
				
				String[] split = requestedPath.split("\\?");
				path = split[0];
				if(split.length > 1) {
					paramString = split[1];	
				}
				
				
			}else {
				path = requestedPath;
			}
			
			parseParameters(paramString);
			
			if(Paths.get(path.substring(1)).toFile().exists() && !path.substring(1).contains(domainName.toString().substring(1))) {
				try {
					sendError(ostream, 403, "Forbidden", path.substring(1));
					return;
				} catch (IOException e) {
					return;
				}
			}
			
			Path rPath = documentRoot.resolve(Paths.get(path.substring(1)));
			
			try {
				internalDispatchRequest(rPath.toString(), true);
			} catch (Exception e1) {
				
			}
			
			try {
				csocket.close();
			} catch (IOException e) {
				
			}
		
		}

		/**
		 * Method checks current session. It checks if sent request in its header has
		 * some cookies, and if that cookies have been saved. Cookies we are interested in
		 * are cookies with a name "sid" (session id). Those cookies can help user to differentiate
		 * between two different clients and sessions. If cookie with a name session is not found, 
		 * new cookie with that name is created. Session id, a number, is randomly generated number, 
		 * lenght of that number is 20 letters. 
		 * @param header List<String>
		 */
		private void checkSession(List<String> header) {
			
			String cookiesAll = null;
			String sidCandidate = null;
			for(String s : header) {
				
				if(s.startsWith("Cookie:")) {
					cookiesAll = s.substring(8).trim();
				}
			}
			
			if(cookiesAll != null) {
				
				String[] cookie = cookiesAll.split(";");
				
				for(String c : cookie) {
					if(c.startsWith("sid")) {
						
						sidCandidate = c.substring(4).trim();
						sidCandidate = sidCandidate.substring(1);
						sidCandidate = sidCandidate.substring(0, sidCandidate.length()-1);
						
					}
				}	
			
				if(sidCandidate != null && !sidCandidate.isBlank()) {
					SessionMapEntry entry = sessions.get(sidCandidate);
					
			
					if(entry != null) {
						if(!entry.host.equals(host) && (entry.validUntil < System.currentTimeMillis())) {
							
							sessions.remove(sidCandidate);//umjesto entry sidCandidate
							makeUniqueSid();
						}else {
							entry.validUntil = sessionTimeout + System.currentTimeMillis();
							SID = entry.sid;
						}
						
					}else if(entry == null && sidCandidate != null){
						SID = sidCandidate;
						
						SessionMapEntry sessionEntry = new SessionMapEntry(SID, host, System.currentTimeMillis() + sessionTimeout, new ConcurrentHashMap<String, String>());
						
						sessions.put(SID, sessionEntry);
						
						
					}else {
						makeUniqueSid();
					}
					
				}else {//sid cookie ne postoji
					
					makeUniqueSid();
				}
			}else {
				
				makeUniqueSid();
			}
			
			
			SessionMapEntry entry = sessions.get(SID);
			
			permParams = entry.map;
			
		}

		/**
		 * This function makes unique cookie with a name "sid" and 
		 * value that is some 20 letter random string.
		 */
		private void makeUniqueSid() {
			
			byte[] b = {1};
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 20; i++) {
				
				while(b[0] < 32 || b[0]>123) {
					sessionRandom.nextBytes(b);	
				}
				
				String string = new String(b, StandardCharsets.UTF_8);
				sb.append(string);
				sessionRandom.nextBytes(b);
			}
			
			SID = sb.toString().toUpperCase();
			
			SessionMapEntry sessionEntry = new SessionMapEntry(SID, host, System.currentTimeMillis() + sessionTimeout, new ConcurrentHashMap<String, String>());
			
			sessions.put(SID, sessionEntry);
			
			RCCookie cookie = new RCCookie("sid", SID, null, host, "/");
			cookie.changeHttpOnly();
			outputCookies.add(cookie);
			
		}

		/**
		 * Method from given string extract header. Each line in
		 * header is separated with character "\n", so this method
		 * splits given string with that character. 
		 * @param requestHeader String
		 * @return List<String>
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Method creates document and writes out, to user in browser, 
		 * given message and code of error
		 * @param os OutputStream
		 * @param code int
		 * @param string String
		 * @param version String
		 * @throws IOException exception
		 */
		private void sendError(OutputStream os, int code, String string, String version) throws IOException {
			
			RequestContext rc = new RequestContext(os, params, permParams,outputCookies);
			rc.setStatusCode(code);
			rc.setStatusText(string);
			
			if(version == null || version.isBlank()) {
				version = "HTTP/?";
			}
			
			rc.write((version + " " + code + " "+ string +"\r\n"+
					"Server: Smart Http Server\r\n").getBytes(StandardCharsets.US_ASCII)
				);
			
		}

		/**
		 * Method from given string extracts parameters and puts
		 * them into map of all parameters. 
		 * @param paramString String
		 */
		private void parseParameters(String paramString) {
		
			if(paramString == null || paramString.isBlank()) {
				return;
			}
			
		
			String[] string = paramString.split("&");
			
			for(String s : string) {
				
				String[] temp = s.split("=");
				params.put(temp[0], temp[1]);
			}
			
		}

		/**
		 * Method goes through header and searches for line that starts with
		 * "Host:". If that line exists, host name is added (only name, not port). If 
		 * that kind of line doesn't exist, then domainName, found in config/server.properties
		 * becomes the host
		 * @param request List<String>
		 */
		private void assignHost(List<String> request) {
			
			for(String string : request) {
				
				if(string.contains("Host:")) {
					
					int index = string.indexOf("Host:");
					char[] chars = string.toCharArray();
					StringBuilder sb = new StringBuilder();
					
					for(int i = index + 5; chars[i] != ':' && chars[i] != ';'; i++) {
						sb.append(chars[i]);
					}
					
					host = (sb.toString()).trim();
					
					return;
				}
			}
			host = domainName.trim();
			return;
			
		}
		/**
		 * This method goes through header and extracts method, version, and 
		 * requestedPath. If method is not "GET", and version is not either "HTTP/1.1" or
		 * "HTTP/1.0", exception is thrown. requestedPath is returned
		 * @param firstLine String
		 * @return String
		 * @throws IOException exception
		 */
		private String extract(String firstLine) throws IOException {
			
			String[] string = firstLine.split(" ");
			method = string[0];
			version = string[2];
			
			if(!method.equals("GET") && (!version.equals("HTTP/1.1") || !version.equals("HTTP/1.0"))) { 
				sendError(ostream, 400, "Invalid first line in header", version);
				return null;
			}
			
			return string[1];
				
		}

		/**
		 * It creates String from given input stream. This is actually some kind
		 * of automat that extracts header from clients request.
		 * @param is InputStream
		 * @return byte[]
		 * @throws IOException
		 */
		private byte[] readRequest(InputStream is) throws IOException {
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = is.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Method is given some path do a document. Job of this method is to
		 * decide what to do with that document. If document is smart script, then {@link SmartScriptEngine}
		 * is used. If document is normal file, then content of that document is shown to user in browser.
		 * If given path is actually path that is mapped to some worker, that worker is called and 
		 * document in browser is generated with a help of a worker
		 * @param urlPath String
		 * @param directCall boolean
		 * @throws Exception exception
		 */
		@SuppressWarnings("deprecation")
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception{

			
			
			if(context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			}
			
			Path path = Paths.get(urlPath);
			
			
			if(path.toString().contains("/private") && directCall) {
				
				sendError(ostream, 404, "Can't call private, invalid path", path.toString());
				return;
			}else if(path.toString().contains("/private")) {
				path = documentRoot.resolve(path.toString().substring(1).trim());
			}
			
			if(path.toString().lastIndexOf("/ext/") != -1) {
				String worker = path.toString().substring(path.toString().lastIndexOf("/") + 1);
				String fqcn = "hr.fer.zemris.java.webserver.workers." + worker.trim();
				
				Class<?> referenceToClass;
				Object newObject = null;
				
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(fqcn.trim());
					newObject = referenceToClass.newInstance();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					return;
				}
				
				IWebWorker iww = (IWebWorker)newObject;
				
				iww.processRequest(context);
				
				return;
			}
			
			synchronized (workersMap) {
				
				IWebWorker webW = workersMap.get(path.toString().substring(path.toString().lastIndexOf("/")).trim());
				
				if(webW != null) {
					
					webW.processRequest(context);
					return;
				}
				
			}
			
			if(!path.toFile().exists() || !path.toFile().canRead()) {
				try {
					
					sendError(ostream, 404, "File not readable or doesn't exist", version);
				} catch (IOException e) {
					
					throw new RuntimeException();
				}
			}
				
			
			int indexLastPoint = path.toString().lastIndexOf(".");
			String extension = path.toString().substring(indexLastPoint + 1);
			
			
			if(extension.equals("smscr")) {
				writeScripts(path);
				return;
			}
			
			String mimeType = mimeTypes.get(extension).trim();
			
			if(mimeType == null || mimeType.isBlank()) {
				mimeType = "application/octet-stream";
			}
			
			writeNormalFileInWebRoot(path, mimeType);
			
		}

		/**
		 * Method reads file in given path and writes it out in browser
		 * @param path Path
		 * @param mimeType String
		 */
		private void writeNormalFileInWebRoot(Path path, String mimeType) {
			
			context.setMimeType(mimeType);
			context.setStatusCode(200);
			
			try(InputStream in = new FileInputStream(path.toString())){
				
				byte[] bytes = in.readAllBytes();
				context.setContentLength((long) new String(bytes, StandardCharsets.UTF_8).length());
				context.write(bytes);
				
			}catch(IOException ex) {
				
			}
			
		}

		/**
		 * Method creates {@link SmartScriptEngine} and executes smart script on given path
		 * with it
		 * @param path Path
		 * @throws IOException exception
		 */
		private void writeScripts(Path path) throws IOException {
			
			String docBody = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			
			new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
					context).execute();

			
		}
		
	}
}
