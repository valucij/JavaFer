package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Checks if class {@link ConditionalExpression} works
 * @author Lucija Valentić
 *
 */
class ConditionalExpressionTest {

	@Test
	void testConditionalExpression() {
		StudentRecord record = new StudentRecord("244235353","Lucija","Valentić","5");
		
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Zagreb", ComparisonOperators.LESS);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral());
		
		assertFalse(recordSatisfies);
	}

}
