package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that creates header, and can write out some text, or
 * bytes sent to its method {@link #write(byte[])}, {@link #write(String)}, 
 * {@link #write(byte[], int, int)}. This class is used with {@link SmartHttpServer}. 
 * 
 * @author Lucija Valentić
 *
 */
public class RequestContext {

	/**
	 * Output stream
	 */
	private OutputStream outputStream;
	/**
	 * Some {@link StandardCharsets}
	 */
	private Charset charset;
	/**
	 * Represents encoding
	 */
	public String encoding;
	/**
	 * Status code
	 */
	public int statusCode;
	/**
	 * Status text
	 */
	public String statusText;
	/**
	 * What type of text some document shows
	 */
	public String mimeType;
	/**
	 * How long is the document
	 */
	public Long contentLength;
	/**
	 * Parameters from document
	 */
	private Map<String, String> parameters;
	/**
	 * Temporary parameters from document
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * Persistent parameters from document
	 */
	private Map<String, String> persistentParameters;
	/**
	 * Cookies from document
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Flag that tell whether the header was generated or not
	 */
	private boolean headerGenerated;
	/**
	 * Internal dispatcher
	 */
	private IDispatcher dispatcher;
	/**
	 * Session id
	 */
	private String sid;
	
	/**
	 * Sets this.encoding. Throws exception if
	 * header was already generated 
	 * @param encoding String
	 * @throws RuntimeException
	 */
	public void setEncoding(String encoding) {
		
		if(headerGenerated) {
			throw new RuntimeException();
		}
		
		this.encoding = encoding;
		this.charset = Charset.forName(encoding);
	}

	/**
	 * Sets this.statusCode. Throws exception if
	 * header was already generated 
	 * @param encoding String
	 * @throws RuntimeException
	 */
	public void setStatusCode(int statusCode) {

		if(headerGenerated) {
			throw new RuntimeException();
		}
		
		this.statusCode = statusCode;
	}

	/**
	 * Sets this.statusText. Throws exception if
	 * header was already generated 
	 * @param encoding String
	 * @throws RuntimeException
	 */
	public void setStatusText(String statusText) {
		
		if(headerGenerated) {
			throw new RuntimeException();
		}
		
		this.statusText = statusText;
	}

	/**
	 * Sets this.mimeType. Throws exception if
	 * header was already generated 
	 * @param encoding String
	 * @throws RuntimeException
	 */
	public void setMimeType(String mimeType) {
		
		if(mimeType.startsWith("\"")) {
			mimeType = mimeType.substring(1);
		}
		if(mimeType.endsWith("\"")) {
			mimeType = mimeType.substring(0, mimeType.length()-1);
		}
		
		if(headerGenerated) {
			throw new RuntimeException();
		}
		
		this.mimeType = mimeType;
	}

	/**
	 * Sets this.contentLenght. Throws exception if
	 * header was already generated 
	 * @param encoding String
	 * @throws RuntimeException
	 */
	public void setContentLength(Long contentLength) {
		
		if(headerGenerated) {
			throw new RuntimeException();
		}
		
		this.contentLength = contentLength;
	}

	/**
	 * Constructor
	 * @param outputStream OutputStream
	 * @param parameters Map<String, String>
	 * @param persistentParameters Map<String, String>
	 * @param outputCookies List<RCCookie>
	 * @param temporaryParameters Map<String, String>
	 * @param dispatcher IDispatcher
	 * @param sid String
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies, 
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sid) {
		
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.sid = sid;
	}


	/**
	 * Constructor
	 * @param outputStream OutputStream
	 * @param parameters Map<String, String>
	 * @param persistentParameters Map<String, String>
	 * @param outputCookies List<RCCookie>
	 * 
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		
		super();
		if(outputStream == null) {
			throw new RuntimeException();
		}
		
		this.outputStream = outputStream;
		this.parameters = (parameters == null)? new HashMap<String, String>() : parameters;
		this.persistentParameters = (persistentParameters == null)? new HashMap<String, String>() : persistentParameters;
		this.outputCookies = (outputCookies == null)? new ArrayList<RequestContext.RCCookie>() : outputCookies;
		
		
		this.temporaryParameters = new HashMap<String, String>();
		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.contentLength = null;
		this.headerGenerated = false;
		this.charset = Charset.forName(encoding);
		
	}
	
	/**
	 * Adds new cookie to map of cookies
	 * @param name String
	 * @param value String
	 * @param maxAge Integer
	 * @param domain String
	 * @param path String
	 */
	public void addRCCookie(String name, String value, Integer maxAge, String domain, String path) {
		
		outputCookies.add(new RCCookie(name, value, maxAge, domain, path));
	}
	
	/**
	 * Private class, represents cookies used with {@link SmartHttpServer}
	 * @author Lucija Valentić
	 *
	 */
	public static class RCCookie{
		/**
		 * Name of a cookie
		 */
		private String name = null;
		/**
		 * Value of a cookie
		 */
		private String value = null;
		/**
		 * Host of session
		 */
		private String domain = null;
		/**
		 * Path
		 */
		private String path = null;
		/**
		 * How long will the cookie last
		 */
		private Integer maxAge = null;
		/**
		 * Represents boolean that tells if its httpOnly
		 */
		private boolean httpOnly = false;
		
		/**
		 * Constructor
		 * @param name String 
		 * @param value String 
		 * @param maxAge Integer
		 * @param domain String
		 * @param path String
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = false;
		}
		/**
		 * Returns this.name
		 * @return String
		 */
		public String getName() {
			return name;
		}
		/**
		 * Changes state of this.httpOnly variable
		 */
		public void changeHttpOnly() {
			this.httpOnly = true;
		}
		/**
		 * Returns this.value
		 * @return String
		 */
		public String getValue() {
			return value;
		}
		/**
		 * Returns this.domain
		 * @return String
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * Returns this.path
		 * @return String
		 */
		public String getPath() {
			return path;
		}
		/**
		 * Returns this.maxAge
		 * @return Integer
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		/**
		 * Returns this.httpOnly
		 * @return boolean
		 */
		public boolean getHttpOnly() {
			return httpOnly;
		}
		
		
	}


	/**
	 * Returns this.parameters
	 * @return Map<String, String>
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Returns this.temporaryParameters
	 * @return Map<String, String>
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Sets this.temporaryParameters with given one
	 * @param temporaryParameters Map<String, String>
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Returns this.persistentParameters
	 * @return Map<String, String>
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Sets this.persistentParameters with given one
	 * @param persistentParameters Map<String, String>
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}
	/**
	 * Returns this.dispatcher
	 * @return IDispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Returns parameter with mapped to given name, or <code>null</code> if
	 * that parameter doesn't exist
	 * @param name String
	 * @return String, or <code>null</code>
	 */
	public String getParameter(String name) {
		
		return parameters.get(name);
	}
	/**
	 * Returns all keys from this.parameters
	 * @return Set<String>
	 */
	public Set<String> getParameterNames(){
		
		Set<String> set = parameters.keySet();
		return Set.copyOf(set);
		
	}

	/**
	 * Returns persistent parameter with mapped to given name, or <code>null</code> if
	 * that parameter doesn't exist
	 * @param name String
	 * @return String, or <code>null</code>
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	/**
	 * Returns all keys from this.persistentParameters
	 * @return Set<String>
	 */	
	public Set<String> getPersistentParameterNames(){
		
		Set<String> set= persistentParameters.keySet();
		
		return Set.copyOf(set);
	}
	/**
	 * Maps given value to given name, and puts that into
	 * this.persistentParameter map
	 * @param name String
	 * @param value String
	 */
	public void setPersistentParameter(String name, String value) {
		
		persistentParameters.put(name, value);
	}
	/**
	 * Removes object from persistentParameter that is mapped to 
	 * given name. If that object doesn't exits, then this method
	 * does nothing
	 * @param name String
	 */
	public void removePersistentParameter(String name) {
		
		persistentParameters.remove(name);
	}
	/**
	 * Returns temporary parameter with mapped to given name, or <code>null</code> if
	 * that parameter doesn't exist
	 * @param name String
	 * @return String, or <code>null</code>
	 */	
	public String getTemporaryParameter(String name) {
		
		return temporaryParameters.get(name);
	}
	/**
	 * Returns all keys from this.temporaryParameters
	 * @return Set<String>
	 */	
	public Set<String> getTemporaryParameterNames(){
		
		Set<String> set = temporaryParameters.keySet();
		
		return Set.copyOf(set);
	}
	/**
	 * Returns this.sid
	 * @return String
	 */
	public String getSessionID() {
		return sid;
	}
	/**
	 * Maps given value to given name, and puts that into
	 * this.temporaryParameter map
	 * @param name String
	 * @param value String
	 */
	public void setTemporaryParameter(String name, String value) {
		
		temporaryParameters.put(name, value);
	}
	/**
	 * Removes object from temporaryParameter that is mapped to 
	 * given name. If that object doesn't exits, then this method
	 * does nothing
	 * @param name String
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	/**
	 * Writes in internal outputstream given array of bytes, encoded
	 * with set encoding
	 * @param data byte[]
	 * @return RequestContext
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException{
		
		return this.write(data, 0, data.length);
	}
	/**
	 * Writes in internal outputstream given array of bytes, encoded
	 * with set encoding
	 * @param data byte[]
	 * @return RequestContext
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException{
		
		if(headerGenerated != true) {
			writeHeader();
		}
		
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}
	/**
	 * Writes in internal outputstream given array of bytes, encoded
	 * with set encoding
	 * @param data byte[]
	 * @return RequestContext
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException{
		
		return this.write(text.getBytes(charset));
		
	}
	/**
	 * Method writes out header to internal output stream
	 * @throws IOException
	 */
	public void writeHeader() throws IOException {
		
		Charset chars = Charset.forName("ISO_8859_1");
		String linesSeparation = "\r\n";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + linesSeparation);
		sb.append("Content-Type: " + mimeType);
		
		if(mimeType.startsWith("text/")) {
			
			sb.append("; charset=" + encoding);
		}
		
		sb.append(linesSeparation);
		
		if(contentLength != null) {
			sb.append("Content-Length: " + contentLength + linesSeparation);
		}
		
		
		for(RCCookie cookie : outputCookies) {
			String name = cookie.getName();
			String value = cookie.getValue();
			String domain = cookie.getDomain();
			String path = cookie.getPath();
			Integer maxAge = cookie.getMaxAge();
			boolean httpOnly = cookie.getHttpOnly();
			
			if(name != null && value != null) {
				sb.append("Set-Cookie: " + name + "=\"" + value + "\"");
			}
			if(domain != null) {
				sb.append("; Domain=" + domain);
			}
			if(path != null) {
				sb.append("; Path=" + path);
			}
			if(maxAge != null) {
				sb.append("; Max-Age=" + maxAge);
			}
			if(httpOnly == true) {
				sb.append("; HttpOnly");
			}
			sb.append(linesSeparation);	
		}
		
		sb.append(linesSeparation);
		
		outputStream.write(sb.toString().getBytes(chars));
		outputStream.flush();
		
		headerGenerated = true;
		
	}
	
}
