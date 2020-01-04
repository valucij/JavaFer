package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Razred <code>Factorial</code> korisnicima umogućava 
 * upis brojeva između 3 i 20, i potom za uneseni cijeli
 * broj izračunava faktorijelu. Pritom se pazi da se 
 * za krivi unos ispiše odgovarajuća poruka, ali
 * program nastavlja svoj rad nakon toga. Program
 * završava s radom nakon što se unese riječ 'kraj'.
 * U sljedećem primjeru je ilustriran način rada.
 * 
 * <pre>
 *  Unesite broj > 2
 *  '2' nije u dozvoljenom rasponu.
 *  Unesite broj > 5
 *  5! = 120
 *  Unesite broj > kraj
 *  Doviđenja. 
 * </pre>
 *
 * @author Lucija Valentić
 *
 */
public class Factorial{

/**
 * Metoda koja se poziva pri pokretanju programa.
 * 	
 * @param args argumenti komandne linije
 */	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		
		while(true){

			System.out.print("Unesite broj > ");

			if(sc.hasNextInt()){

				int x = sc.nextInt();
				
				if(x < 3 || x > 20){

					System.out.printf("'%d' nije u dozvoljenom rasponu.%n",x);
					
				}
				else{
					
					long f = fact(x);
					System.out.printf("%d! = %s%n", x, f);
				}

			}
			else{

				String st = sc.next();

				if(st.equals("kraj")){

					System.out.println("Doviđenja.");

					break;
				}
				else{

					System.out.printf("'%s' nije cijeli broj.%n", st);
				}
			}
		}
		sc.close();
	}
	
/**
 *  Metoda za primljeni cijeli broj izračunava faktorijelu,
 *  ako je to moguće (cijeli broj mora biti jednak ili 
 *  veći od 0). 
 *  
 *  @param n cijeli broj kojeg metoda prima
 *  @return f broj koji označava izračunatu faktorijelu
 *  @throws IllegalArgumentException() ako metoda primi
 *  		cijeli broj manji od 0
 */	
	public static long fact(int n){
		
		if(n < 0) {
			  throw new IllegalArgumentException("Faktorijelu nemoguće izračunati.");
			}
		
		long f = 1;
				
		while(n > 1){
			
			f*=n;
			
			n-= 1;
		}
				
		return f;
	}
}
