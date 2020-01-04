package hr.fer.zemris.java.hw15.crypto;

import java.io.IOException;
import java.security.*;

/**
 * Class that helps in calculating password hash for passwords.
 * @author Lucija Valentić
 *
 */
public class Crypto {
	
	/**
	 * Receives string that represents password,
	 * and returns password hash.
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String calculatePassword(String password) throws NoSuchAlgorithmException, IOException {
		
		byte[] calculated = null;
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		
		calculated = sha.digest(password.getBytes());
		
		return bytetohex(calculated);
		
	}
	
	/**
	 * Private method that returns string of hex-numbers
	 * that are corresponding to given array of
	 * bytes.
	 * 
	 * @param bytes
	 * @return string
	 */
	private static String bytetohex(byte[] bytes) {
		
		if(bytes == null) {
			return null;
		}
		
		String s =""; 
		for(byte b : bytes) {
			s = s + String.format("%02x", b);
		}
		
		return s;
		
	}
	
}
