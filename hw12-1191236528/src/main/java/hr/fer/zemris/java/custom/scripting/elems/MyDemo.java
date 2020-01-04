package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Stack;

public class MyDemo {

	public static void main(String[] args) {
		
		Stack<Integer> stack = new Stack<Integer>();
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		for(Integer i : stack) {
			System.out.println(i);
		}

	}

}
