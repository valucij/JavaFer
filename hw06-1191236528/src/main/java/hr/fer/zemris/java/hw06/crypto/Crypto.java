package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.*;
import java.util.Scanner;
/**
 * Class is made for checking sha-256 message digest from
 * given file, or for encrypting and decrypting given file.
 * Class has method main, and when starting this program,
 * user should given two or three arguments. One arguments should
 * be special word "checksha", "encrypt", "decrypt" followed by
 * path of a file. In case of encrypt and decrypt, another
 * argument is expected, path of a file that will contain
 * data that has been decrypted or encrypted in this methods.
 * 
 * @author Lucija Valentić
 *
 */
public class Crypto {
	
	/**
	 * Main method called in the beginning of a program. 
	 * This method just decides which part of this class
	 * will be run.
	 * 
	 * @param args arguments of command line
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		switch(args[0]) {
		
		case "checksha":
			checksha(Paths.get(args[1]));
			break;
		case "decrypt":
			decrypt(Paths.get(args[1]), Paths.get(args[2]));
			break;
		case "encrypt":
			encrypt(Paths.get(args[1]), Paths.get(args[2]));
			break;
		}
		
	}
	
	/**
	 * Method calculates and checks sha-256 of a given 
	 * file. It asks user for sha-256, and then, if calculated
	 * sha-256 of a given file is not equal with the one
	 * user gave, then appropriate message is written. Also,
	 * if they are the same, appropriate message is written.
	 * 
	 * @param path
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void checksha(Path path) throws NoSuchAlgorithmException, IOException {
		
		System.out.println("Please provide expected sha-256 digest for hw06test.bin");
		Scanner sc = new Scanner(System.in);
		String expected = "";
		
		
		if(sc.hasNext()) {
			expected = sc.nextLine();
		}
		sc.close();
		
		byte[] calculated = null;
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		InputStream file = Files.newInputStream(path);
		
		calculated = sha.digest(file.readAllBytes());
		
		
		if(!expected.equals(Util.bytetohex(calculated))) {
			System.err.printf("Digest of %s does not match the expected digest. Digest was %s%n", path.getFileName(), Util.bytetohex(calculated));
		}else {
			System.out.printf("Digest of %s matches expected digest.", path.getFileName());
		}
		file.close();
		
	}
	/**
	 * Method decrypts given file, and writes resulting data
	 * of decryption in given out file. This method actually
	 * calls method <code>CryptoHelpFunctions.makeFile</code>
	 * to do all the work, and just writes ending message.
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void decrypt(Path in, Path out) throws IOException {
		
		CryptoHelpFunctions.makeFile(in, out, "decrypt");
		System.out.printf("Decryption completed. Generated file %s based on file %s.", out.getFileName(), in.getFileName());
	}
	
	/**
	 * Method encrypts given file, and writes resulting data
	 * of encryption in given out file. This method actually
	 * calls method <code>CryptoHelpFunctions.makeFile</code>
	 * to do all the work, and just writes ending message.
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void encrypt(Path in, Path out) throws IOException {
		
		CryptoHelpFunctions.makeFile(in, out, "encrypt");
		System.out.printf("Encryption completed. Generated file %s based on file %s.", out.getFileName(), in.getFileName());
	}
	
}
