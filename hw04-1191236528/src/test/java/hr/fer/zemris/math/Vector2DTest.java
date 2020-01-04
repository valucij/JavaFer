package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	final double DELTA = 0.01;
	
	@Test
	void testConstructor() {
		Vector2D vector = new Vector2D(7,42);
		
		assertNotNull(vector);
	}
	
	@Test
	void testGetX() {
		Vector2D vector = new Vector2D(7,42);
		
		assertEquals(7,vector.getX());
	}
	@Test
	void testGetY() {
		Vector2D vector = new Vector2D(7,42);
		
		assertEquals(42,vector.getY());
	}
	@Test
	void testTranslate() {
		Vector2D vector = new Vector2D(7,42);
		vector.translate(new Vector2D(1.0,1.0));
		
		assertEquals(8.0, vector.getX());
		assertEquals(43.0, vector.getY());
		
	}
	@Test
	void testTranslated() {
		Vector2D vector = new Vector2D(7,42);
		Vector2D test = vector.translated(new Vector2D(1.0,1.0));
		
		assertNotNull(test);
		assertEquals(8.0, test.getX());
		assertEquals(43.0, test.getY());
	}
	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(1.0,0.0);
		vector.rotate(Math.PI);
		
		assertEquals(-1.0, vector.getX(), DELTA);
		assertEquals(0.0, vector.getY(), DELTA);
		
	}
	@Test
	void testRotated() {
		Vector2D vector = new Vector2D(1.0,0.0);
		Vector2D test = vector.rotated(Math.PI);
		
		assertNotNull(test);
		assertEquals(-1.0, test.getX(), DELTA);
		assertEquals(0.0, test.getY(), DELTA);
	}
	@Test
	void testScale() {
		Vector2D vector = new Vector2D(2.0,3.0);
		vector.scale(7);
		
		assertEquals(14.0, vector.getX());
		assertEquals(21.0, vector.getY());
	}
	@Test
	void testScaled() {
		Vector2D vector = new Vector2D(2.0,3.0);
		Vector2D test = vector.scaled(7);
		
		assertNotNull(test);
		assertEquals(14.0, test.getX());
		assertEquals(21.0, test.getY());
	}
	@Test
	void testCopy() {
		Vector2D vector = new Vector2D(7.0,42.0);
		Vector2D test = vector.copy();
		
		assertNotNull(test);
		assertEquals(7.0, test.getX());
		assertEquals(42.0, test.getY());
	}

}
