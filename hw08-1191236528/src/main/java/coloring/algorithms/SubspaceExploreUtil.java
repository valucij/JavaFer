package coloring.algorithms;

import java.util.*;
import java.util.function.*;

/**
 * Class that represents "collection" of algorithms. More specifically,
 * this class has three methods, each one of the method is one
 * algorithm for searching. First one: Breadth-first search (BFS) is an algorithm for
 * traversing or searching tree or graph data structures. It starts at the tree root 
 * (or some arbitrary node of a graph, sometimes referred to as a 'search key'), 
 * and explores all of the neighbor nodes at the present depth prior to moving
 * on to the nodes at the next depth level. 
 * (source: https://en.wikipedia.org/wiki/Breadth-first_search)
 * Second one: Depth-first search (DFS) is an algorithm for traversing or searching 
 * tree or graph data structures. The algorithm starts at the root node (selecting 
 * some arbitrary node as the root node in the case of a graph) and explores as 
 * far as possible along each branch before backtracking.
 * (source https://en.wikipedia.org/wiki/Depth-first_search)
 * Third one: same as bfs, but it remembers states that were checked/visited.
 * @author Lucija Valentić
 *
 */
public class SubspaceExploreUtil {

	/**
	 * BFS algorithm, explained above in details.
	 * @param <S>
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ, Predicate<S> acceptable) {
		
		List<S> zaIstraziti = new LinkedList<S>();
		zaIstraziti.add(s0.get());
		
		while(!zaIstraziti.isEmpty()) {
			
			S si = zaIstraziti.get(0);
			zaIstraziti.remove(si);
			
			if(!acceptable.test(si)) {
				continue;
			}
			
			process.accept(si);
			
			zaIstraziti.addAll(succ.apply(si));
		}
		
	}
	
	/**
	 * DFS algorithm, explained above in details.
	 * @param <S>
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ, Predicate<S> acceptable) {
		
		List<S> zaIstraziti = new LinkedList<S>();
		zaIstraziti.add(s0.get());
		
		while(!zaIstraziti.isEmpty()) {
			S si = zaIstraziti.get(0);
			zaIstraziti.remove(si);
			
			if(!acceptable.test(si)) {
				continue;
			}
			
			process.accept(si);
			
			zaIstraziti.addAll(0, succ.apply(si));
		}
		
	}
	
	/**
	 * BFSV algorithm, explained above in details.
	 * @param <S>
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ, Predicate<S> acceptable) {
		
		List<S> zaIstraziti = new LinkedList<S>();
		Set<S> posjeceno = new HashSet<S>();
		
		zaIstraziti.add(s0.get());
		posjeceno.add(s0.get());
		
		
		
		while(!zaIstraziti.isEmpty()) {
			
			S si = zaIstraziti.get(0);
			zaIstraziti.remove(si);
			
			if(!acceptable.test(si)) {
				continue;
			}
			
			process.accept(si);
			
			List<S> djeca = succ.apply(si);
			djeca.removeIf((s)-> posjeceno.contains(s));
			
			zaIstraziti.addAll(zaIstraziti.size(), djeca);
			posjeceno.addAll(djeca);
		
		}
		
	}
	
	
}
