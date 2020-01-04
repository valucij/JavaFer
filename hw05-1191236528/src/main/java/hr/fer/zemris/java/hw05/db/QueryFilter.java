package hr.fer.zemris.java.hw05.db;

import java.util.*;

/**
 * Class implements interface {@link IFilter}.
 * In constructor, it accepts list of 
 * conditional expressions, and based on that
 * when given a StudentRecord to check if is valid
 * or not, it goes through that list, and if that record
 * satisfy all of the conditional expressions, then
 * it is valid.
 * @author Lucija Valentić
 *
 */
public class QueryFilter implements IFilter{

	/**
	 * Private variable that keeps list
	 * of conditional expressions given in
	 * constructor
	 */
	private List<ConditionalExpression> list;
	
	/**
	 * Constructor.
	 * 
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		
		
		for(ConditionalExpression expr : list) {
			
			if( !expr.getComparisonOperator()
					.satisfied(expr.getFieldGetter().get(record),
							expr.getStringLiteral()) ) {
				return false;
			}
		}
		
		return true;
		
	}

}
