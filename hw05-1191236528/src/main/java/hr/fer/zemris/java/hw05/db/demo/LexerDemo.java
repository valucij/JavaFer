package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.QueryLexer;
import hr.fer.zemris.java.hw05.db.QueryToken;
import hr.fer.zemris.java.hw05.db.QueryTokenType;

/**
 * Class for testing is QueryLexer works.
 * @author Lucija Valentić
 *
 */
public class LexerDemo {

	public static void main(String[] args) {
		QueryLexer lexer1 = new QueryLexer(" jmbag >= \"883182397\" ");
		QueryToken token = lexer1.nextToken();
		
		while(!token.getType().equals(QueryTokenType.EOF)) {
			System.out.println(token);
			token = lexer1.nextToken();	
		}
		System.out.println(token);
	}
	
	
}
