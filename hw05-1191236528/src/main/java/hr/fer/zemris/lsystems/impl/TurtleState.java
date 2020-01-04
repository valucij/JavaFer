package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
/**
 * Class represent a point with which
 * LSystems can be drawn. It is easier
 * to think of it as of turtle, so hence 
 * the name. So 'turle' has everything
 * we need when drawing something.
 * It has direction, coordinates of origin,
 * color, and moving shift (by how much the turtle
 * should move when drawing a line).
 * 
 * @author Lucija Valentić
 *
 */
public class TurtleState {

	/**
	 * Represents current position of a turtle
	 */
	private Vector2D currentPosition;
	/**
	 * Represent an angle of a turtle
	 */
	private Vector2D currentDirection;
	/**
	 * Represent color with which
	 * will be drawn some line
	 */
	private Color currentColor;
	/**
	 * Represent by how much is turtle
	 * moved, when some command to
	 * move the turtle is called
	 */
	private double currentShift;
	
	/**
	 * Constructor
	 * 
	 * @param position
	 * @param direction
	 * @param color
	 * @param shift
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double shift){
		currentPosition  = position;
		currentDirection = direction;
		currentColor = color;
		currentShift = shift;
	}
	
	/**
	 * Returns copy of a currentState of
	 * a turtle, this method doesn't change
	 * this state of a turtle
	 * 
	 * @return new TurtleState
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(),currentDirection.copy(),currentColor,currentShift);
	}

	/**
	 * Returns currentPosition of a turtle
	 * @return this.currentPosition
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Returns currentDirection of a turtle
	 * @return this.currentDirection
	 */
	public Vector2D getCurrentDirection() {
		return currentDirection;
	}
	
	/**
	 * Returns currentColor of a turtle
	 * @return this.currentColor
	 */
	public Color getCurrentColor() {
		return currentColor;
	}

	/**
	 * Returns currentShift of a turtle
	 * @return this.currentShift
	 */
	public double getCurrentShift() {
		return currentShift;
	}

}
