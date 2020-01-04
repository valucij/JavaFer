package hr.fer.zemris.java.hw06.crypto;

/**
 * Class is used when we want to convert
 * array of bytes into string of hex-numbers,
 * or if we want to convert string of hex-numbers
 * into an array of bytes. Next example shows how to
 * use this class.
 * 
 * <pre>
 * 
 * input > "01aE22";
 * 
 * using > Util.hextobyte("01aE22");
 * 
 * output > {1, -82, 34};
 * 
 * 
 *	OR
 *
 * input > {1, -82, 34};
 * 
 * using > Util.bytetohex({1, -82, 34});
 * 
 * output > "01aE22";
 *
 * 
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */
public class Util {

	/**
	 * Public method that receives string
	 * of hex-numbers, there can be multiple hex-numbers
	 * in the string, and return array of bytes representing
	 * each number
	 * 
	 * @param string
	 * @return array of bytes
	 */
	public static byte[] hextobyte(String string) {
		
		if(string == null) {
			return null;
		}
		
		if(string.length() % 2 != 0) {
			string = "0" + string;
		}
		
		byte[] bytes = new byte[string.length()/2];
		
		char[] forConvert = string.toCharArray();
		
		for(int i = 0, j = 0; i < string.length(); i += 2, j++) {
			String number = String.copyValueOf(forConvert, i, 2);
			bytes[j] = hex(number);
		}
		
		return bytes;
		
	}
	/**
	 * Private method that calculates one byte for each
	 * given hex-number
	 * @param number
	 * @return
	 */
	private static byte hex(String number) {
		
		String firstDigit = number.substring(0, 1).toLowerCase();
		String secondDigit = number.substring(1).toLowerCase();
		
		int b = 0;
		
		b += 16 * multiplier(firstDigit);
		b += multiplier(secondDigit);
		
		return (byte) b;
		
	}
	/**
	 * Private method that helps convert hex-digit into
	 * a decimal number.
	 * If digit was letter, valid one (a-f), then 
	 * it return proper value, and if it was a digit
	 * it just return that digit in integer form
	 * 
	 * @param string
	 * @return int
	 */
	private static int multiplier(String string) {
		
		if(Character.isLetter(string.toCharArray()[0])) {
			return returnProperValueFromHex(string);
		}else {
			return Integer.parseInt(string);
		}	
	}
	/**
	 * Checks if given digit is valid. Valid
	 * digits in hex-numbers are a,b,c,d,e and f.
	 * If given digit is not valid, exception is 
	 * thrown.
	 * @param digit
	 * @return int
	 * @throws NumberFormatException
	 */
	private static int returnProperValueFromHex(String digit) {
			
		switch(digit) {
		
		case "a":
			return 10;
		case "b":
			return 11;
		case "c":
			return 12;
		case "d":
			return 13;
		case "e":
			return 14;
		case "f":		
			return 15;
		}
		
		throw new NumberFormatException();
	}
	/**
	 * Public method that returns string of hex-numbers
	 * that are corresponding to given array of
	 * bytes.
	 * 
	 * @param bytes
	 * @return string
	 */
	public static String bytetohex(byte[] bytes) {
		
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
