package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.jupiter.api.Test;

/**
 * Class tests if class {@link CalcLayout} works how it should
 * @author Lucija Valentić
 *
 */
class CaclLayoutTest {

	@Test
	void testInvalidRowLessZero() {
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		try{
			
			p.add(new JLabel("K"), new RCPosition(0, 5));
			
		}catch(CalcLayoutException ex) {
			return;
		}

		fail();
	}

	@Test
	void testInvalidColumnMore7() {
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		try{
			p.add(new JLabel("K"), new RCPosition(0, 8));
		}catch(CalcLayoutException ex) {
			return;
		}

		fail();
	}
	
	@Test
	void testInvalidRowMore5() {
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		try{
			
			p.add(new JLabel("K"), new RCPosition(6, 5));
			
		}catch(CalcLayoutException ex) {
			return;
		}

		fail();
	}
	
	@Test
	void testInvalidColumnLessOne() {
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		try{
			
			p.add(new JLabel("K"), new RCPosition(2, 0));
			
		}catch(CalcLayoutException ex) {
			return;
		}

		fail();
	}
	
	@Test
	void testInvalidRowAndColumnForFirstRow() {
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		try{
			
			p.add(new JLabel("K"), new RCPosition(1, 3));
			
		}catch(CalcLayoutException ex) {
			return;
		}

		fail();
	}
	
	@Test
	void testMoreComponentsSamePosition() {
		
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("L"), new RCPosition(2,3));
		
		try{
			
			p.add(new JLabel("K"), new RCPosition(2, 3));
			
		}catch(CalcLayoutException ex) {
			return;
		}

		fail();
	}
	
	@Test
	void testMoreComponentsDifferentPosition() {
		
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("L"), new RCPosition(2,3));
		p.add(new JLabel("U"), new RCPosition(3,3));
		
	}
	
	@Test
	void testPrefferedSizeNormalElements() {
		
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(158, dim.height);
		assertEquals(152, dim.width);
		
	}
	
	
	@Test
	void testPrefferedSizeElementsOneOne() {
		
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); 
		l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(158, dim.height);
		assertEquals(152, dim.width);
		
	}
	
	
}
