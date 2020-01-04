package searching.slagalica;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;
/**
 * CLass implements three interfaces : {@link Supplier}, 
 * {@link Function} and {@link Predicate}. It is used in example
 * of showing how searching algortihms work. This one class
 * represetns one configuration of a 9 space puzzle, but more specifically, this
 * class is a whole object that also has methods that test next configuration, 
 * test if current configuration is correct, and so on.
 * @author Lucija Valentić
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>, Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	/**
	 * represents configuration of a puzzle
	 */
	private KonfiguracijaSlagalice configuration;
	
	/**
	 * Constructor
	 * @param conf
	 */
	public Slagalica(KonfiguracijaSlagalice conf) {
		configuration = conf;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override//predikat, gleda je li ta konfiguracija završna ili ne
	public boolean test(KonfiguracijaSlagalice t) {
		KonfiguracijaSlagalice correct = new KonfiguracijaSlagalice(new int[]{1,2,3,4,5,6,7,8,0});
		return t.equals(correct);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override//function - vraća sljedeća stanja, kao djecu i to
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();
		
		
		int zero = t.indexOfSpace();
		int[] polje = t.getPolje();
		
		if(zero - 3 >= 0) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(polje, zero, zero - 3)), 1));
		}
		if(zero + 3 < polje.length) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(polje, zero, zero + 3)), 1));
		}
		
		if(zero - 1 >= 0 && ((zero - 1) % 3) < (zero % 3)  ) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(polje, zero, zero - 1)), 1));
		}
		
		if(zero + 1 < polje.length && ((zero + 1) % 3) > (zero % 3)  ) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(swap(polje, zero, zero + 1)), 1));
		}
		
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override //vraća sadašnje početno stanje
	public KonfiguracijaSlagalice get() {
		return configuration;
	}

	/**
	 * Swaps two elements in given array. Elements that
	 * are swaped, position of those elements, are
	 * given as arguments of a method
	 * @param polje
	 * @param i
	 * @param j
	 * @return copy of a given array, but changed
	 */
	private int[] swap(int[] polje, int i, int j) {
		
		int[] copy = polje.clone();
		
		int temp = copy[i];
		copy[i] = copy[j];
		copy[j] = temp;
		
		return copy;
	}

}
