package hr.fer.zemris.java.hw06.shell.util;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellIOException;

/**
 * Class represent one object that can create {@link NameBuilder} for
 * creating new file name. All it needs is some pattern in special form,
 * and of course valid to create 'skeleton' that will, when used, generate appropriate 
 * file name. Form of a patter don't necessarily needs to be special, but if it is,
 * the form should have this parts in it, and they have to be written exactly like this:
 * '${1}' or '${1,02}' or '${1,4}', numbers can be different of course. If
 * patter is written any differently, exception is thrown.
 * @author Lucija Valentić
 *
 */
public class NameBuilderParser {

	/**
	 * Internal string that is actually some pattern
	 * that dictates how new file names will be made
	 */
	private  String izraz;
	
	/**
	 * Constructor
	 * @param izraz
	 */
	public NameBuilderParser(String izraz) {
		this.izraz = izraz;
	}
	
	/**
	 * Returns NameBuilder. Before it returns it,
	 * it has to create it
	 * @return LastNameBuilder
	 */
	public NameBuilder getNameBuilder() {
		
		LastNameBuilder lnb = getLastNameBuilder();
		return lnb;
	}
	
	/**
	 * Special method that pushes
	 * into StringBuilder whole given text
	 * @param index
	 * @param padding
	 * @param minWitdh
	 * @return NameBuilder
	 */
	static NameBuilder text(String t) {
		return (result, sb)->{ 
			sb.append(t); 
		}; 
	}
	/**
	 * Special method that pushes
	 * into StringBuilder just part of the information from result.
	 * It pushes part of a file name, part is determined with given index.
	 * @param index
	 * @param padding
	 * @param minWitdh
	 * @return NameBuilder
	 */	
	static NameBuilder group(int index) {
		return (result, sb) ->{
			sb.append(result.group(index));
		};
	}
	
	/**
	 * Special method that pushes
	 * into StringBuilder just part of the information from result.
	 * It pushes part of a file name, part is determined with given index,
	 * but it dictates how long that string should be, and 
	 * with what it should be padded.
	 * @param index
	 * @param padding
	 * @param minWitdh
	 * @return NameBuilder
	 */
	static NameBuilder group(int index, char padding, int minWitdh) {
		return (result, sb) ->{
			
			String string = result.group(index);
			
			if(string.length() < minWitdh) {
				
				for(int i = string.length(); i < minWitdh; i++ ) {
					sb.append(padding);	
				}
				
			}
			sb.append(string);
			
		};
	}
	
	/**
	 * Returns newly made LastNameBuilder
	 * @return LastNameBuilder
	 */
	private LastNameBuilder getLastNameBuilder() {
		return this.new LastNameBuilder();
	}
	
	/**
	 * Class that implements interface {@link NameBuilder}
	 * This special class has list of different {@link NameBuilder}, and
	 * in method execute calls execution on every NameBuilder
	 * in the list
	 * @author Lucija Valentić
	 *
	 */
	private class LastNameBuilder implements NameBuilder{

		/**
		 * Represents list of different {@link NameBuilder}.
		 */
		List<NameBuilder> list;
		
		/**
		 * Constructor, creates list of different {@link NameBuilder},
		 * so when instance of this class is executed, it 
		 * class execution on every {@link NameBuilder} in list. It
		 * can also throw an exception if something goes wrong. 
		 * @throws ShellIOException
		 */
		public LastNameBuilder() {
			
			NameBuilderLexer lexer = new NameBuilderLexer(izraz);
			list = new LinkedList<NameBuilder>();
			
			NameBuilderToken token = lexer.nextToken();
			
			while(lexer.hasNext()) {
		
				if(token.getType().equals(NameBuilderTokenType.TEXT)) {
				
					list.add(text(token.getValue()));
				}else if(token.getType().equals(NameBuilderTokenType.GROUP1)) {
					
					list.add(group( Integer.parseInt(token.getValue()) ));
				}else if(token.getType().equals(NameBuilderTokenType.GROUP2)) {
					
					StringBuilder group = new StringBuilder();
					StringBuilder padding = new StringBuilder();
					StringBuilder minWidth = new StringBuilder();
					
			        String[] string = token.getValue().split(",");
			        group.append(string[0].trim());
			        
			        if( string[1].trim().length() != 1) {
			        	
			        	padding.append( string[1].trim().toCharArray()[0]);
			        	minWidth.append( string[1].trim().toCharArray()[1]);
			        	
			        }else {
			        	padding.append(" ");
			        	minWidth.append(string[1].trim());
			        }
					
					list.add(group( Integer.parseInt(group.toString()) , padding.toString().toCharArray()[0], Integer.parseInt(minWidth.toString()) ));
				}else {
					throw new ShellIOException();
				}
				
				token = lexer.nextToken();
				
			}
			
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			
			for(NameBuilder builder : list){
				
				builder.execute(result, sb);
			}
			
		}
		
	}
}
