package hr.fer.zemris.java.hw05.db;
/**
 * Class represents one conditional expression.
 * That means that each object of this class
 * has some kind of getter -> what kind of
 * information it must retrieve from some object
 * of StudentRecord; StringLiteral -> information
 * that needs to be checked in what relation it is
 * with appropriate information from StudentRecord,
 * gotten with getter; and operator -> decides what is 
 * appropriate relation between information from
 * StuentRecord and StringLiteral. Used while making
 * some kind of filter.
 * 
 * @author Lucija Valentić
 *
 */
public class ConditionalExpression {

	/**
	 * Represents some kind of getter that gets appropriate information
	 * from StudentRecord
	 */
	private IfieldValueGetter getter;
	/**
	 * StringLiteral, represents some information that in the future
	 * will be compared with some information from StudentRecord
	 */
	private String string;
	/**
	 * Represents what kind of relation we want to check
	 * between some information from StudentRecord and
	 * StringLiteral
	 */
	private IComparisonOperator operator;
	
	/**
	 * Constructor 
	 * 
	 * @param getter
	 * @param string
	 * @param operator
	 */
	public ConditionalExpression(IfieldValueGetter getter, String string, IComparisonOperator operator) {
		this.getter = getter;
		this.string = string;
		this.operator = operator;
	}
	/**
	 * Return getter from this object.
	 * @return this.getter
	 */
	public IfieldValueGetter getFieldGetter() {
		return getter;
	}
	/**
	 * Returns string literal from this object
	 * @return this.string
	 */
	public String getStringLiteral() {
		return string;
	}
	/**
	 * Returns operator from this object
	 * @return this.operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return operator;
	}
	
	
}
