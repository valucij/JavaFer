package hr.fer.zemris.java.custom.collections.demo;


import hr.fer.zemris.java.custom.collections.*;
/**
 * Class <code>StackDemo</code> works with stack, and is used for calculating
 * postfix expressions. Here is given example of use:
 * 
 * <pre>
 * 
 * Input > "-1 8 2 / +"
 * Output > "Expression evaluates to 3."
 * 
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */
public class StackDemo {

	/**
	 * Main method that is called in the begining of program.
	 * 	
	 * @param args arguments command line. Number of them should be one.
	 */
	public static void main (String[] args) {
		
		if(args.length != 1) {
			
			System.out.println("Too many arguments");
			return;
		}
		
		
		String string = args[0].substring(0,args[0].length());
		
				
		evaluate(string);
				
	}
/**
 * Method <code>evaluate</code> from given strings extracts postfix expression,
 * and with use of stack, it calculates the expression. If something is wrong
 * with the string, or if the expression is not postfix, it throws
 * IllegalArgumentException().
 * 	
 * @param str given string from which we must calulate postfix expression
 */
	public static void evaluate(String str) {
		
		ObjectStack S1 = new ObjectStack();
		
		String parts[] = str.split(" ");
		int t = 0;
						
		for(int i = 0, n = parts.length; i < n; i++) {
				
			try {
				int x = Integer.parseInt(parts[i]);
				S1.push(x);
				t = 1;
			}catch(IllegalArgumentException ex) { t = 0;}
			
			if(t == 0) {
				
					int second = 1;
					int first = 1;
					int flag = 0;
					try {
						
						second = (int) S1.pop();
						first = (int) S1.pop();
						
					}catch(EmptyStackException ex) {
						System.out.println("Wrong input.");
						return;
					}
					
					int number = 0;
					
				if(parts[i].equals("/")){
									
					if(second == 0) {
						System.out.println("Can't divide with zero.");
						return;
					}
					number = first/second;
					flag = 1;
					
				}else if(parts[i].equals("*")) {
					
					number = first * second;
					flag = 1;
									
				}else if(parts[i].equals("+")) {
					
					number = first + second;
					flag = 1;
										
				}else if(parts[i].equals("-")) {
						
					number = first - second;
					flag = 1;
					
				}
				
				if(flag == 0) {
					System.out.println("Wrong input.");
					return;
				}
				
				S1.push(number);
				t = 1;
			}
									
		}
		
		if(S1.size() != 1) {
			
			System.err.println("Error happend.");
			return;
		}
		
		int y =(int)S1.peek();
		S1.pop();
		
		System.out.printf("Expression evaluates to %d.%n", y);
	}
}
