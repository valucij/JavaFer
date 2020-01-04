package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred <code>Rectangle</code> omogućava korisniku
 * unos visine i širine pravoknutnika, te za dobar unos
 * ispisuje opseg i površinu. Unos se može obaviti
 * preko komandne linije. Ako je broj argumenata krivi, 
 * ispisuje se odgovarajuća poruka, i prekida se
 * s radom. Isto tako se pazi da je unos
 * valjan, stringovi i 'decimalni' brojevi sa zarezom se
 * ne prihvaćaju, ali se nastavlja s radom, sve
 * dok se ne unese neki prihvatljiv broj - cijeli broj ili 
 * 'pravi' decimalni broj s točkom.
 *  U sljedećem primjeru je prikazan način rada.
 * 
 * <pre>
 * 	Unesite širinu > 2,3
 * 	'2,3' se ne može protumačiti kao broj.
 * 	Unesite širinu > 5
 * 	Unesite visinu > -5
 * 	Unijeli ste negativnu vrijednost.
 * 	Unesite visinu > 6.2
 * 	Pravokutnik širine 5.0 i visine 6.2 ima površinu
 *  31 te opseg 22.4. 			
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */

public class Rectangle{

/**
 * Metoda koja se poziva pri pokretanju programa.
 * 	
 * @param args argumenti komandne linije
 */
	public static void main(String[] args){
		
		if(args.length == 2){
			
			double a = 1.0;
			double b = 1.0;
			try{
				 a = Double.parseDouble(args[0]);
				 b = Double.parseDouble(args[1]);
				
			}catch(NumberFormatException ex){
				
				System.out.println("Niste unijeli dobre argumente");
				return;
			}
			
			if(a > 0.0 && b > 0.0) {
				System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.%n",a,b,povrsina(a,b),opseg(a,b));
				return;
			}
			else {
				System.out.println("Upisali ste negativne argumente.");
				return;
			}
		}
		
		if(args.length == 0){
						
			Scanner sc = new Scanner(System.in);
			
			double a = upisSirine(sc);
			
			double b = upisVisine(sc);
			
			System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.%n",a,b,povrsina(a,b),opseg(a,b));
			
			
		}
		else{
			
			System.out.println("Upisali ste netočan broj argumenata.");
			return;
		}
	}
/**
 * Metoda za dane decimalne brojeve izračunava opseg. 
 * Primljeni argumenti moraju biti veći od 0.0, inače
 * nema smisla.
 * 
 * @param a visina pravokutnika
 * @param b širina pravokutnika
 * @throws IllegalArgumentException() ako su primljeni
 * 		   brojevi manji od 0
 * @return o opseg pravokutnika	
 */
	public static double opseg(double a,double b){
		
		if(a < 0.0 || b < 0.0) {
			throw new IllegalArgumentException("Nemoguće izračunati opseg.");
		}
		
		double o = 2*a + 2*b;
		
		return o;
	}
/**
 * Metoda prima decimalne brojeve, veće od 0.0,
 * koji označavaju visinu i širinu pravokutnika
 * i vraća izračunatu površinu.
 * 
 * @param a visina pravokutnika
 * @param b širina pravokutnika
 * @throws IllegalArgumentException() ako su primljeni
 * 		   brojevi manji od 0
 * @return p površina trokuta
 */
	public static double povrsina(double a,double b){
		
		if(a < 0.0 || b < 0.0) {
			throw new IllegalArgumentException("Nemoguće izračunati površinu.");
		}
		
		double p = a*b;
		
		return p;
	}
/**
 * Metoda obavlja upis širine, kako se ne bi sve događalo
 * u main dijelu. Pazi na točan unos, znači ako se unese
 * string ili 'decimalni' broj sa zarezom, ispisuje se 
 * odgovarajuća poruka i čeka se unos prave vrijednosti.
 * Unos cijelog broja je dopušten. Broj ne smije biti
 * negativan, inače se isto tako ispisuje odgovarajuća 
 * poruka.
 *  
 * @param sc
 * @return a širina pravokutnika
 */
	private static double upisSirine(Scanner sc){
		
				
		while(true){
			
			System.out.print("Unesite širinu > ");
			
			if(sc.hasNextDouble()) {
				
				double a = sc.nextDouble();
				if(a < 0.0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}
				else {
					
					return a;
				}
			}
			else {
				
				String pom = sc.next();
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", pom);
			}
					
		}
		
	}
/**
* Metoda obavlja upis visine, kako se ne bi sve događalo
* u main dijelu. Pazi na točan unos, znači ako se unese
* string ili 'decimalni' broj sa zarezom, ispisuje se 
* odgovarajuća poruka i čeka se unos prave vrijednosti.
* Unos cijelog broja je dopušten. Broj ne smije biti
* negativan, inače se isto tako ispisuje odgovarajuća 
* poruka.
* 	
* @param sc
* @return b visina pravokutnika
*/
	private static double upisVisine(Scanner sc){
		
		
		while(true){
			
			System.out.print("Unesite visinu > ");
			
			if(sc.hasNextDouble()) {
				
				double a = sc.nextDouble();
				if(a < 0.0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}
				else {
					
					return a;
				}
			}
			else {
				
				String pom = sc.next();
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", pom);
			}
				
			
		}
		
	}
}
