package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred <code>UniqueNumbers</code>  omogućava korisniku
 * stvaranje binarnog stabla, vrijednost čvora je neki
 * cijeli broj. Čvorovi s manjim vrijednostima postaju 
 * djeca u lijevom podstablu, a s većim vrijednostima 
 * u desnom. Isto tako je omogućeno provjeravanje da li
 * se čvor s određenom vrijednošću nalazi, odnosno ne nalazi
 * u stablu. MOže se provjeravati i veličina, tj broj
 * čvorova u stablu. Sve što se može provjeravati u vezi
 * binarnog stabla se treba obaviti prije nego što se
 * unese string 'kraj'. Ako se unese nešto što nije cijeli
 * broj, to se javlja porukom i nastavlja se s radom , dok
 * se ne unese string 'kraj', tad program prekida s radom.
 *  Ako se želi dodati čvor koji 
 * već postoji, onda se ništa ne događa, nastavlja se s 
 * radom. U sljedećem primjeru je prikazan rad progama.
 * 
 * <pre>
 * Unesite broj > 58
 * Dodano.
 * Unesite broj > lucija
 * 'lucija' nije cijeli broj.
 * Unesite broj > 58
 * Broj već postoji. Preskačem.
 * Unesite broj > kraj
 * </pre>
 * 
 *  @author Lucija Valentić
 *
 */
public class UniqueNumbers{
/**
 * Pomoćna struktura <code>TreeNode</code> pomoću koje 
 * imamo/stvaramo svoja binarna stabla.
 *  	
 * @author Lucija Valentić
 *
 */
	static class TreeNode{
		TreeNode left;
		TreeNode right;
		int value;
	}
/**
 * Metoda koja se poziva pri pokretanju programa.
 * 	
 * @param args argumenti komandne linije
 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;
		
		while(true) {
			
			System.out.print("Unesite broj > ");
			
			if(sc.hasNextInt()) {
				
				int a = sc.nextInt();
				
				if(glava == null) {
					glava = addNode(glava,a);
					System.out.println("Dodano.");
					continue;
				}
				
				if(containsValue(glava,a)) {
					
					System.out.println("Broj već postoji. Preskačem.");
				}
				else {
					glava = addNode(glava,a);
					System.out.println("Dodano.");
				}
				
			}
			else {
				
				String st = sc.next();
				
				if(st.equals("kraj")) {
					break;
				}
				
				System.out.printf("'%s' nije cijeli broj.%n", st);
			}
			
		}
		
		printajOdNajmanjeg(glava);
		printajOdNajvećeg(glava);
		
		sc.close();
	}
/**
 * Metoda dodaje novi čvor sa vrijednošću 'x' u stablo
 * s korijenom node. Metoda pazi na slučajeve da je stablo
 * prazno ili slučaj da se takav čvor već nalazi u stablu.
 * Ako se takav čvor već nalazi u stablu, ne dodaje ga
 * opet. Stablo se slaže tako da manji idu lijevo, veći 
 * desno.
 * 
 * @param node korijen stabla u kojeg treba staviti novi čvor
 * @param x vrijednost koju treba staviti u novi čvor
 * @return korijen stabla sa novim čvorom ako on ne postoji
 * 		   u stablu, inače vraća korijen nepromjenjenog 
 * 		   stabla
 */
	public static TreeNode addNode(TreeNode node,int x){
		
		if(node == null) {
			TreeNode pom = new TreeNode();
			pom.value = x;
			pom.left = null;
			pom.right = null;	
			return pom;
		}
		
		TreeNode temp = node;
		
		TreeNode pom = new TreeNode();
		pom.value = x;
		pom.left = null;
		pom.right = null;
		
		while(node != null){
			
			
			
			if(x == node.value) {
				return temp;
			}
			else if(x < node.value) {
				
				if(node.left != null) {
					node = node.left;
				}
				else {
					node.left = pom;
					break;
				}
			}
			else if(x > node.value) {
				
				if(node.right != null) {
					node = node.right;
				}
				else {
					node.right = pom;
					break;
				}
			}
		}
				
		return temp;
	}

/**
 * Metoda je rekurzivna, i izračunava broj čvorova u nekom
 * stablu. Kad dođe u neki čvor, zbroji njega, i broj 
 * čvorova iz desnog i lijevog stabla. Nešto slično
 * preorderu, samo se zbrajaju stvari kad se vraćaju.
 * 
 * @param node korijen stabla za kojeg se želi izračunati
 * 		  veličina (broj čvorova).
 * @return broj čvorova u trenutnom stablu za kojeg je 
 * 		   pozvana metoda
 */
	public static int treeSize(TreeNode node) {
		
		if(node == null) {
			return 0;
		}
		else {
			
			int trenutni = 1;
			
			return treeSize(node.left) + treeSize(node.right) + trenutni;
		}
		
	}
/**
 * Metoda za dani čvor node i vrijednost x provjerava 
 * nalazi li se takav čvor u tom stablu kojem je korijen 
 * taj čvor node. Ako se pošalje prazno stablo, automatski
 * se vraća <false> jer čvor ni ne može postojati u takvom
 * stablu.
 *  	
 * @param node početni čvor od stabla za kojeg
 * 		  želimo provjeriti nalazi li se u njemu čvor 
 * 		  vrijednosti 'x'
 * @param x vrijednost za koju želimo provjeriti nalazi 
 * 		  li se u stablu
 * @return <code>true</code> ako se čvor vrijednosti 'x' 
 * 		   nalazi u stablu, inače <code>false</code>
 */
	public static boolean containsValue(TreeNode node, int x) {
		
		TreeNode temp = node;
		
		while(temp != null && x != temp.value) {
			
			if(temp.value < x) {
				temp = temp.right;
			}
			else {
				temp = temp.left;
			}				
		}
		
		if(temp != null) {
			return true;
		}
		
		return false;
		
	}
/**
* Metoda prima čvor, i on u ovom slučaju označava korijen
* stabla, i želimo ispisati sve elemente njega, ali od 
* najmanjeg prema najvećem. Poziva pomoćnu metodu za ispis
* vrijednosti čvorova. Ako je poslano stablo prazno
* ispisuje se odgovarajuća poruka.
* 
* @param node čvor koji označava korijen ili cijelog
* 		  stabla, ili podstabla, ali od njega i njegove 
* 	      djece želimo ispis
*/	
	public static void printajOdNajmanjeg(TreeNode node) {
		
		if(node == null) {
			System.out.println("Poslano stablo je prazno.");
			return;
		}
		
		System.out.print("Ispis od najmanjeg: ");
		inOrder(node);
		System.out.println("");
	}
/**
 * Metoda prima čvor, i on u ovom slučaju označava korijen
 * stabla, i želimo ispisati sve elemente njega, ali od 
 * najvećeg prema najmanjem. Poziva pomoćnu metodu za ispis
 * vrijednosti čvorova. Ako je poslano stablo prazno
 * ispisuje se odgovarajuća poruka.
 * 
 * @param node čvor koji označava korijen ili cijelog
 * 		  stabla, ili podstabla, ali od njega i njegove 
 * 	      djece želimo ispis
 */
	public static void printajOdNajvećeg(TreeNode node) {
		
		if(node == null) {
			System.out.println("Poslano stablo je prazno.");
			return;
		}
		
		System.out.print("Ispis od najvećeg: ");
		obrnutiInOrder(node);
		System.out.println("");
	}
/**
 * Metoda prolazi stablom u inorderu, ali prvo počinje od
 * desnog stabla, pritom ispisuje vrijednost čvorova u 
 * kojima se nalazi. Budući da je ovo obrnuti inorder
 * dobit ćemo ispis čvorova u inorderu koji je obrnuti.	
 * 
 * @param node čvor od kojeg počinje obrnuti inorder. 
 * 		  Ako je <code>null</code>, ništa se ne događa.
 */
	public static void obrnutiInOrder(TreeNode node) {
		
		if(node == null) {
			return;
		}
		
		obrnutiInOrder(node.right);
		System.out.printf("%d ", node.value);
		obrnutiInOrder(node.left);
		
		
		return;
		
	}
/**
 * Metoda prolazi po stablu u inorderu, i ispisuje pritom
 * vrijednost čvorova (naravno u inorderu).
 * 	
 * @param node čvor od kojeg se počinje inorder. Ako je
 * 		  <code>null</code> onda metoda ne radi ništa.
 */
	public static void inOrder(TreeNode node) {
		
		if(node == null) {
			return;
		}
		
		inOrder(node.left);
		System.out.printf("%d ", node.value);
		inOrder(node.right);
		
		return;
	}
}
