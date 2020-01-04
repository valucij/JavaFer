package searching.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class represents 'collection' of a searching algorithms. More specifically, 
 * this class has two methods, that represent different searching algorithms. Those
 * algorithms are commonly used in AI. They are as follows:
 * first one -  Breadth-first search (BFS) is an algorithm for
 * traversing or searching tree or graph data structures. It starts at the tree root 
 * (or some arbitrary node of a graph, sometimes referred to as a 'search key'), 
 * and explores all of the neighbor nodes at the present depth prior to moving
 * on to the nodes at the next depth level. 
 * (source: https://en.wikipedia.org/wiki/Breadth-first_search)
 * and second one - algorithm BFSV, same as bfs, but it remembers states that were checked/visited.
 * @author Lucija Valentić
 *
 */
public class SearchUtil {

	/**
	 * Represents BFS algorithm, explained better above
	 * @param <S>
	 * @param s0
	 * @param succ
	 * @param goal
	 * @return Node<S>
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal){
		
		List<Node<S>> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));

		while(!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.get(0);
			
			zaIstraziti.remove(0);
			
			if( goal.test(ni.getState())) {
				return ni;
			}
			
			List<Transition<S>> pairs = succ.apply(ni.getState());
			
			for(Transition<S> pair : pairs) {
				zaIstraziti.add(zaIstraziti.size(),new Node<S>(ni,pair.getState(), ni.getCost() + pair.getCost()));
				
			}
		}
		return null;		
	}

	/**
	 * Represents BFSV algorithm, explained better above
	 * @param <S>
	 * @param s0
	 * @param succ
	 * @param goal
	 * @return Node<S>
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal){
		
		System.out.println("OPREZ: ako je unesena konfiguracija kao u zadaći, ona neriješiva, petlja će se izvrtiti 181 439 puta prije nego vrati null");
		
		List<S> prodeni = new LinkedList<>();
		List<Node<S>> zaIstraziti = new LinkedList<>();
		zaIstraziti.add(new Node<S>(null, s0.get(), 0));
		prodeni.add(s0.get());
		
		while(!zaIstraziti.isEmpty()) {
			Node<S> ni = zaIstraziti.get(0);
			zaIstraziti.remove(0);
			
			if( goal.test(ni.getState())) {
				return ni;
			}
			
			List<Transition<S>> pairs = succ.apply(ni.getState());
			
			pairs.removeIf( (s) -> prodeni.contains(s.getState()));
			
			
			for(Transition<S> pair : pairs) {
				
				zaIstraziti.add(zaIstraziti.size(),new Node<S>(ni,pair.getState(), ni.getCost() + pair.getCost()));
				prodeni.add(prodeni.size(),pair.getState());	
					
			}
			
			
		}
		return null;
		
	}

}
