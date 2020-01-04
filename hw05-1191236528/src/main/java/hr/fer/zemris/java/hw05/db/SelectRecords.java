package hr.fer.zemris.java.hw05.db;

import java.util.*;

/**
 * Class that extracts StudentRecords from 
 * database based on query that has been given in constructor.
 * It uses QueryFilter for filtering valid records. QueryFilter
 * is made with list of conditional expressions parsed from
 * given query
 * 
 * @author Lucija Valentić
 *
 */
public class SelectRecords {

	/**
	 * Private list that represent students records
	 * after passing through filter.
	 */
	private List<StudentRecord> records;
	
	/**
	 * Constructor in which we also go through
	 * database, and filter appropriate student records
	 * 
	 * @param query
	 * @param database
	 */
	public SelectRecords(String query, StudentDatabase database) {
		QueryParser parser = new QueryParser(query);
		LinkedList<ConditionalExpression> list = new LinkedList<ConditionalExpression>();
		
		
		if(parser.isDirectQuery()) {
			list.add(new ConditionalExpression(FieldValueGetters.JMBAG, parser.getQueriedJMBAG(), ComparisonOperators.EQUALS));
			records = database.filter(new QueryFilter(list));
			if(records.size() != 0) {
				System.out.println("Using index for record retrieval.");
			}
		}else {

			List<ConditionalExpression> list2 = parser.getQuery();
			
			try {
				records = database.filter(new QueryFilter(list2));
			}catch(IllegalArgumentException ex) {
				System.err.println("Too many '*' characters. Try writing query again: ");
			}
		}
		
	}

	/**
	 * Return list of student records from this object
	 * @return this.records
	 */
	public List<StudentRecord> getRecords(){
		return records;
	}
	
}
