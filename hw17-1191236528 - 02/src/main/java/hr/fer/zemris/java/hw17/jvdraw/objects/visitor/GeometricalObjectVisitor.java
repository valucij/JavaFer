package hr.fer.zemris.java.hw17.jvdraw.objects.visitor;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Interface that represents objects that, when implemented,
 * can perform different actions on geometric objects
 * @author Lucija Valentić
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Action performed when line is encountered
	 * @param line
	 */
	public abstract void visit(Line line);
	/**
	 * Action performed when circle is encountered
	 * @param circle
	 */
	public abstract void visit(Circle circle);
	/**
	 * Action performed when filled circle is encountered
	 * @param filledCircle
	 */
	public abstract void visit(FilledCircle filledCircle);
}
