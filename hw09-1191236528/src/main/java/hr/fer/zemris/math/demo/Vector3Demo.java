package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Vector3;

/**
 * Class that checks if class {@link Vector3} works.
 * 
 * @author Lucija Valentić
 *
 */
public class Vector3Demo {

	/**
	 * Main method called in the beginning of the program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		
		Vector3 l = k.add(j).scale(5);
		
		Vector3 m = l.normalized();
		
		System.out.println("i: " + i);
		System.out.println("j: " + j);
		System.out.println("k: " + k);
		System.out.println("l: " + l);
		System.out.println("l.norm(): " + l.norm());
		System.out.println("m: " + m);
		System.out.println("l.dot(): " + l.dot(j));
		System.out.println("i.add(new Vector3(0.1.0)).cosAngle(l):" + i.add(new Vector3(0,1,0)).cosAngle(l));
		
		
		
	}
}
