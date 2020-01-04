package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
/**
 * Class implements interface {@link LSystemBuilder}.
 * It is used for configurating information and creating
 * context for creating LSystem, and then drawing that
 * LSystem.
 * 
 * @author Lucija Valentić
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{

	/**
	 * Represents current moving shift of a turtle
	 */
	private double unitLength;
	/**
	 * Represents by how much moving shift
	 * has to be scaled
	 */
	private double unitLengthDegreeScaler;
	/**
	 * Represent origin point of a turtle
	 */
	private Vector2D origin;
	/**
	 * Represent direction of a turtle
	 */
	private double angle;
	/**
	 * Represents axiom from which
	 * it can be generated some strings
	 * based on given rules
	 */
	private String axiom;
	/**
	 * Dictionary that keeps
	 * rules for productions.
	 * Those rules tell what characters need
	 * to be replaced with what string
	 */
	private Dictionary<Character, String> productions;
	/**
	 * Dictionary that keeps actions/command
	 * that are linked with some character
	 */
	private Dictionary<Character, Command> actions;
	
	/**
	 * Constructor
	 */
	public LSystemBuilderImpl() {
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
		productions = new Dictionary<>();
		actions = new Dictionary<>();
		
	}
	
	/**
	 * Returns new instance of a LSystemImpl
	 * 
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Private class that is implementation of
	 * a LSystem.
	 * It has method draw() and generate(),
	 * that are used for showing/drawing some
	 * LSystem (like fractals)
	 * 
	 * @author Lucija Valentić
	 *
	 */
	private class LSystemImpl implements LSystem{
		
		/**
		 * Method creates new context,
		 * creates new state of a turtle, generates
		 * string (using method String generate(int arg)).
		 * When string is generated, it is passed 
		 * through that string and for each character,
		 * it is searched if there exists some command/action
		 * that is linked with that character. If it does,
		 * then command is executed, and otherwise,
		 * that character is skipped.
		 * 
		 * @param arg0
		 * @param arg1
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context ctx = new Context();
			
			TurtleState state = new TurtleState( origin, new Vector2D(1,0).rotated(angle), Color.BLACK, unitLength);
		
			ctx.pushState(state);
			String string = generate(arg0);
			
			char[] whatToDo = string.toCharArray();
			
			double scale = Math.pow(unitLengthDegreeScaler, arg0);
						
			ScaleCommand scaling = new ScaleCommand(scale);
			scaling.execute(ctx, arg1);
			
			for(int i = 0, n = string.length(); i < n; i++) {
				
				char action = whatToDo[i];
				Command command = actions.get(action);
				
				if(command != null) {
					command.execute(ctx, arg1);
				}
			}
		}

		/**
		 * Method generates string based on given
		 * level and axiom. If given level is 0,
		 * returned string is an axiom.
		 * If level is 1 or above, then 
		 * string is generated in the way that
		 * on each level, it is passed
		 * through string that was generated in the
		 * previous level, and then each character is 
		 * exchanged for some string, if
		 * production that says that exists, and
		 * otherwise, that character is just copied and
		 * essentially skipped
		 * 
		 * @param arg0
		 * @return Stringg
		 */
		@Override
		public String generate(int arg0) {
			
			String string = axiom;
			
			if(arg0 == 0) {
				return string;
			}
			
			for(int i = 0; i < arg0; i++) {
			
				char[] charString = string.toCharArray();
				string = "";
				
				for(int j = 0, n = charString.length; j < n; j++) {
					
					String adding = productions.get(charString[j]);
					
					if(adding != null) {
						string = string + adding;
					}else {
						string = string + charString[j];
					}
				}
			}
			
			return string;
		}
	
		
	}
	
	/**
	 * Method configures LSystemBuilder
	 * with given array of strings.
	 * Each string is empty, or inside
	 * it is written what and how needs to be 
	 * set. This method is actually some sort
	 * of simple lexer, so little mistakes are check,
	 * but nothint more
	 * 
	 * @param arg0
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		
		for(String string : arg0) {
			
			if(!string.isBlank()) {
				
				int firstIndex = nextNotBlank(string);
				int lastIndex = nextBlank(string,firstIndex);
				int flag = 0;
				
				String action = string.substring(firstIndex, lastIndex);
				String ending = string.substring(lastIndex);
				
				switch(action) {
				
				case "origin":
					

					firstIndex = nextNotBlank(ending);
								
					
					int first = firstIndex;
					int second = nextBlank(ending,first);
					
					double x = Double.parseDouble(ending.substring(first, second));
					
					ending = ending.substring(second);
					first = nextNotBlank(ending);
					
					double y = Double.parseDouble(ending.substring(first));
					
					setOrigin(x, y);
					flag=1;
					break;
				case "angle":
					double angle = Double.parseDouble(ending);
					setAngle(angle);
					flag = 1;
					break;
				case "unitLength":
					double length = Double.parseDouble(ending);
					setUnitLength(length);
					flag = 1;
					break;
				case "unitLengthDegreeScaler":
					
					double scale;
					
					try {
						scale = Double.parseDouble(ending);
						
						setUnitLengthDegreeScaler(scale);
						flag = 1;
						break;
					}catch(NumberFormatException ex) {
						
						
						first = nextNotBlank(ending);
						second = nextBlank(ending, first);
						
						x = Double.parseDouble(ending.substring(first, second));
						
						ending = ending.substring(second);
						
						first = nextNotBlank(ending);
						
						String operator = ending.substring(first, first + 1);
						
						ending = ending.substring(first + 1);
						
						y = Double.parseDouble(ending);
						
						setUnitLengthDegreeScaler(doOperation(x,y,operator));
						flag = 1;
						break;
					}
					
				case "command":
					
					first = nextNotBlank(ending);
					
					String char1 = ending.substring(first, first + 1);
					
					char[] char2 = char1.toCharArray();
					
					ending = ending.substring(first + 1);
					
					first = nextNotBlank(ending);
					ending = ending.substring(first);
					
					registerCommand(char2[0], ending);
					flag = 1;
					break;
				case "axiom":
					
					firstIndex = nextNotBlank(ending);
					ending = ending.substring(firstIndex);
					setAxiom(ending);
					flag = 1;
					break;
					
				case "production":
					
					firstIndex = nextNotBlank(ending);
					char1 = ending.substring(firstIndex,firstIndex + 1);
					
					ending = ending.substring(firstIndex + 1);
					firstIndex = nextNotBlank(ending);
				
					ending = ending.substring(firstIndex);
					
					char2 = char1.toCharArray();
					registerProduction(char2[0], ending);
					flag = 1;
					break;
					
				}				
				
				if(flag == 0) {
					throw new IllegalArgumentException("Invalid string");
				}
			}
		}
		
		return this;
	}

	
	private double doOperation(double x, double y, String oper) {
		
		switch(oper) {
		
		case "+": 
			
			return x + y;
			
		case "*":
			
			return x * y;
			
		case "/":
			
			return x / y;
			
		case "-":
			
			return x - y;
		}
		
		throw new IllegalArgumentException();
		
	}
	/**
	 * Method that returns int that represents
	 * position of next character that is a blank
     * in given string. Checking starts from given
     * index.
     * 
	 * @param arg0
	 * @param index
	 * @return
	 */
	private int nextBlank(String arg0, int index) {
		
		char[] string = arg0.toCharArray();
		int i = index;
		
		while(!Character.isWhitespace(string[i])) {
			i++;
		}
		return i;
		
	}
	/**
	 * Method sets this.actions with given 
	 * parameters
	 * 
	 * @param arg0
	 * @param arg1
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		
		actions.put(arg0, parseCommand(arg1));
		return this;
	}

	/**
	 * Private method that helps in extracting
	 * command from the string. This is some
	 * sort of very simple parser, and 
	 * it is assumed that given string has
	 * nicely written command that are valid, so
	 * little to non checks are performed
	 * It returns parsed command, or
	 * throws exception if something goes wrong
	 * 
	 * @param arg1
	 * @return Command
	 * @throws IllegalArgumentException()
	 */
	private Command parseCommand(String arg1) {
				
		
		int i = nextNotBlank(arg1);
		String action = arg1.substring(i, i + 2);
		
		switch(action) {
		
			case "dr":
				
				double step = Double.parseDouble(arg1.substring(i + 4));
				DrawCommand command1 = new DrawCommand(step);
				return command1;
			
			case "sk":
			
				double skip = Double.parseDouble(arg1.substring(i + 4));
				SkipCommand command2 = new SkipCommand(skip);
				return command2;
				
			case "sc":
				
				double scale = Double.parseDouble(arg1.substring(i + 5));
				ScaleCommand command3 = new ScaleCommand(scale);
				return command3;
		
			case "ro":
		
				double angle = Math.toRadians(Double.parseDouble(arg1.substring(i + 6)));
				RotateCommand command4 = new RotateCommand(angle);
				return command4;
				
				
			case "pu":
			
				PushCommand command5 = new PushCommand();
				return command5;
				
			case "po":
			
				PopCommand command6 = new PopCommand();
				return command6;
				
			case "co":
								
				String stringOfColor ="#" + arg1.substring( i + 6);
				
				Color color = Color.decode(stringOfColor);
				
				ColorCommand command7 = new ColorCommand(color);

				return command7;
		}
		
		throw new IllegalArgumentException("Invalid action");
	}
	
	/**
	 * Private method that from given
	 * string finds first index
	 * of a whitespace
	 * 
	 * @param arg
	 * @return index
	 */
	private int nextNotBlank (String arg) {
		
				
		char[] string = arg.toCharArray();
		
		int i = 0;
		
		while(Character.isWhitespace(string[i]) && string[i] != 0) {
		
			i++;
		}
		
		return i;
	}
	

	/**
	 * Method sets this.productions with given 
	 * parameters
	 * 
	 * @param arg0
	 * @param arg1
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		
		productions.put(arg0, arg1);
		return this;
	}


	/**
	 * Method sets this.angle with given 
	 * parameter
	 * 
	 * @param arg0
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = Math.toRadians(arg0);
		return this;
	}


	/**
	 * Method sets this.axiom with given 
	 * parameter
	 * 
	 * @param arg0
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}


	/**
	 * Method sets this.origin with given 
	 * parameters
	 * 
	 * @param arg0
	 * @param arg1
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		Vector2D newOrigin = new Vector2D(arg0, arg1);
		origin = newOrigin;
		return this;
	}


	/**
	 * Method sets this.unitLength with given 
	 * parameter
	 * 
	 * @param arg0
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	/**
	 * Method sets this.unitLengthDegreeScaler with given 
	 * parameter
	 * 
	 * @param arg0
	 * @return LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
