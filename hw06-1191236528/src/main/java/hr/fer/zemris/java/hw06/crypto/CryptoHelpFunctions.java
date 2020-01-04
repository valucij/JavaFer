package hr.fer.zemris.java.hw06.crypto;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * Main function of this class is to help {@link Crypto} class
 * in making cipher objects, and this function actually does
 * encrypting and decrypting with that cipher; methods in Crypto just 
 * call this functions. This was made to avoid redundant code
 * in class {@link Crypto};
 * 
 * @author Lucija Valentić
 *
 */
public class CryptoHelpFunctions {

	/**
	 * Method decrypt or encrypt given in file, and
	 * makes a new one, file out, based on the output of
	 * decryption, or encryption. This method doesn't read
	 * the whole file, and then, using cypher decrypts, or 
	 * encrypts, but reads file in chunks.
	 * 
	 * @param in
	 * @param out
	 * @param string
	 * @throws IOException
	 */
	public static void makeFile(Path in, Path out, String string) throws IOException {
		
		Cipher cipher = getCipher(string);
		
		byte[] reading = new byte[1024];
		
		InputStream inFile = Files.newInputStream(in);
		OutputStream outFile = Files.newOutputStream(out);
		
		byte[] code = null;
		
		while( inFile.read(reading) != -1) {
			code = cipher.update(reading);
			outFile.write(code);
		}
		
		inFile.close();
		outFile.close();
	}
	/**
	 * Private method that creates cipher object, based on
	 * given string. Given string should be "encrypt" or
	 * "decrypt"; otherwise, method throws exception. This method
	 * just uses cipher algorithms already implemented in java.
	 * Also, method asks user for password and initialization vector;
	 * because cipher objects are made using couple of parameters.
	 * If, in process of making cipher, something goes wrong, exception 
	 * is thrown.
	 * 
	 * @param string
	 * @return cipher object
	 * @throws CryptoException()
	 */
	private static Cipher getCipher(String string){
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		Scanner sc = new Scanner(System.in);
		String keyText = "", ivText = "";
		
		if(sc.hasNext()) {
			keyText = sc.nextLine();
		}
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		
		if(sc.hasNext()) {
			ivText = sc.nextLine();
		}
		
		sc.close();
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText),"AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		Cipher cipher;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCs5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			throw new CryptoException("Error when creating cipher");
		}
		
		
		switch(string) {
			case "encrypt":
				try {
					cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
				} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
					throw new CryptoException("Error when initializating cipher");
				}
				break;
			case "decrypt":
				try {
					cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
				} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
					throw new CryptoException("Error when initializating cipher");
				}
				break;
			case "*":
				throw new CryptoException("Invalid cipher mode");
		}
		
		return cipher;
		
	}
	
}
