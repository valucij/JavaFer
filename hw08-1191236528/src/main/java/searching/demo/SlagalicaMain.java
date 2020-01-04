package searching.demo;

import java.util.*;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Class that shows with example how searhing algorithms work
 * @author Lucija Valentić
 *
 */
public class SlagalicaMain {
	
	/**
	 * Main method called in the beginning of a program
	 * @param args arguments of a command line
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Invalid number of arguments. Expected 1");
			return;
		}
		
		if(!isValid(args[0])) {
			System.out.println("Invalid arguments");
			return;
		}
		
		int[] polje = args[0].chars().map( (c) -> (c - '0')).toArray();
		
		Slagalica slagalica = new Slagalica(
		new KonfiguracijaSlagalice(polje)
		);
		
		Node<KonfiguracijaSlagalice> rješenje =	SearchUtil.bfs(slagalica, slagalica, slagalica);
		
		if(rješenje==null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println(
					"Imam rješenje. Broj poteza je: " + rješenje.getCost()
					);
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while(trenutni != null) {
					lista.add(trenutni.getState());
					trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			
			SlagalicaViewer.display(rješenje);
		}
	}

	/**
	 * Checks if given string is valid - if it consists of 9 digits,  
	 * and if consists of digits 0,1,2,3,4,5,6,7,8. All those digits don't 
	 * have to be in that order, but they have to be in given string, and
	 * only once.
	 * @param string
	 * @return boolean
	 */
	private static boolean isValid(String string) {
		
		int[] polje = string.chars().map( (c) -> (c - '0')).toArray();
		Arrays.sort(polje);
		
		int[] correct = {0,1,2,3,4,5,6,7,8};
		
		return Arrays.equals(correct, polje);
		
	}
}