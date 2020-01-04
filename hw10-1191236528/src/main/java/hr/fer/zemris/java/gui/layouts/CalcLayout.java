package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * Class implements interface {@link LayoutManager2}. This
 * class is made so we can make classes that represents calculators.
 * This class manages all components in some container and arranges
 * them, and then that container, along side with components in it, 
 * can actually look like calculator. 
 * 
 * @author Lucija Valentić
 */
public class CalcLayout implements LayoutManager2{

	/**
	 * Represents components in some container
	 */
	private Component components[][];
	/**
	 * Represents gap between rows and columns
	 */
	private int gap;
	/**
	 * Represents preferred height for row
	 */
	private double rowHeightPref;
	/**
	 * Represents preferred width for column
	 */
	private double columnWidthPref;
	/**
	 * Represents minimum height for row
	 */
	private double rowHeightMin;
	/**
	 * Represents minimum width for column
	 */
	private double columnWidthMin;
	
	/**
	 * Constructor
	 * @param i
	 */
	public CalcLayout(int i) {
		gap = i;
		components = new Component[5][7];
		rowHeightPref = 0;
		columnWidthPref = 0;
		rowHeightMin = 0;
		columnWidthMin = 0;
	}

	/**
	 * Constructor
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		
		for(Component[] comp1 : components) {
			for(Component c : comp1) {
				if(c != null && c == comp) {
					c = null;
				}
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return preferedOrMinimal(parent, "pre");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferedOrMinimal(parent, "min");
		
	}

	/**
	 * This method actually does work for methods {@link #preferredLayoutSize(Container)}
	 * and {@link #minimumLayoutSize(Container)}. Those two method are almost similar code wise,
	 * so code is relocated in new method
	 * @param parent
	 * @param string
	 * @return dimension
	 */
	private Dimension preferedOrMinimal(Container parent, String string) {
		
		Integer w = 0;
		Integer h = 0;
		
		switch(string) {
		
		case "min":
			
			w = (int) Math.floor(7 * columnWidthMin) ; 
			h = (int) Math.floor(5 * rowHeightMin);
			
		case "pre":
		
			w = (int) Math.floor(7 * columnWidthPref);
			h = (int) Math.floor(5 * rowHeightPref);
		}
		
		
		Insets in = parent.getInsets();
		
		w += 6 * gap + in.left + in.right;
		h += 4 * gap + in.bottom + in.top;

				
		return new Dimension(w, h);


	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {

		Insets in = parent.getInsets();
		int width = parent.getWidth() - in.left - in.right;
		int height = parent.getHeight() - in.bottom - in.top;
		
		double rowHeight = (double)(height - 4 * gap) /5;
		double columnWidth = (double)(width - 6 * gap) / 7;
		
		int i = (int) ((Math.ceil(rowHeight) * 5 + 4 *gap) - height);
		int j = (int) ((Math.ceil(columnWidth) * 7 + 6 *gap) - width);
		
		int x;
		int y = in.top;
		int w;
		int h;
		
		//public void setBounds(int x, int y, int width, int height)
		
		for(int r = 0; r < 5; r++) {
			
			x = in.left;
			for(int c = 0; c < 7; c++) {
				
				w = (r == 0 && c == 0 )? width11(columnWidth, j, r, c) : width(columnWidth, j, r, c);
				h = height(rowHeight, i, r, c);
				
				if(components[r][c] != null) {

					components[r][c].setBounds(x, y, w, h);		
					
					if(r == 0 && c == 0) {
						c += 4;
					}
				}
				
				x += w + gap;		
			}
			y += height(rowHeight, i, r, 0) + gap;
			
		}
		
	}
	
	/**
	 * This method calculates how wide component(1, 1) has
	 * to be. It takes in to account between columns. We can observe
	 * component(1,1) as 5 "regular" components and 4 gaps. Those
	 * regular components can be different width, based on in which
	 * column they are, and which elements in which column needs to be
	 * width Math.ceil(rowHeight) and which Math.floor(rowHeight). 
	 * See {@link #width(double, int, int, int)} for more informations how
	 * that is calculated. 
	 * @param w
	 * @param i
	 * @param row
	 * @param column
	 * @return int (width)
	 */
	private int width11(double w, int i, int row, int column) {
		
		int width  = 4 * gap;
		
		for(int c = 0; c < 5; c ++) {
			
			width += width(w, i, 0, c);
		}
		
		return width;
		
	}

	/**
	 * Method calculates width of component in place (row, column).
	 * Given width is double, so it doesn't have to be number that 
	 * is "whole". That's way, we have to make sure that elements
	 * in special columns are ceil(width) or floor(width). What 
	 * will be chosen is determined by given <code>i</code> and 
	 * of course given <code>column</code>. I is number that is
	 * difference between actual width of container (minus insets)
	 * and width that comes from ceil(width) times 7. This method
	 * is here because that difference is not always 0. 
	 * @param w
	 * @param i
	 * @param row
	 * @param column
	 * @return int (width)
	 */
	private int width(double w, int i, int row, int column) {
		
		int up = (int)Math.ceil(w);
		int down = (int) Math.floor(w);
		
		if(i <= 3 ) {//na dolje manjinu (stupci)
			
			if( (i == 1 && column == 3) || 
				( i == 2 && (column == 1 || column == 5)) ||
				(i == 3 && (column == 5 || column == 1 || column == 3)) ) {
				return down;
			}else {
				return up;
			}
			
		}else {//na gore manjinu (stupci)
			
			i = 7 - i;
			
			if( (i == 1 && column == 3) || 
				( i == 2 && (column == 1 || column == 5)) ||
				(i == 3 && (column == 5 || column == 1 || column == 3)) ) {
				return up;
			}else {
				return down;
			}	
		}
	}
	

	/**
	 * Method calculates height of component in place (row, column).
	 * Given height is double, so it doesn't have to be number that 
	 * is "whole". That's way, we have to make sure that elements
	 * in special rows are ceil(height) or floor(height). What 
	 * will be chosen is determined by given <code>i</code> and 
	 * of course given <code>row</code>. I is number that is
	 * difference between actual height of container (minus insets)
	 * and height that comes from ceil(height) times 5. This method
	 * is here because that difference is not always 0. 
	 * @param h
	 * @param i
	 * @param row
	 * @param column
	 * @return int (height)
	 */
	private int height(double h, int i, int row, int column) {
		
		int up = (int) Math.ceil(h);
		int down = (int) Math.floor(h);
		
		if(i <=2 ) {
			
			if( (i == 1 && row == 2) || ( i == 2 && (row == 1 || row == 2)) ) {
				return down;
			}
			return up;
			
		}else {
			
			i = 5 - i;
			
			if( (i == 1 && row == 2) || ( i == 2 && (row == 1 || row == 2)) ) {
				return up;
			}
			return down;
			
		}
		
	}
	/**
	 * {@inheritDoc}
	 * @throws CalcLayoutException
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		
		if(comp == null || constraints == null) {
			throw new CalcLayoutException();
		}
		
		RCPosition rc = null;
		
		if(constraints instanceof String) {
			
			rc = parse((String) constraints);
			
		}else if(constraints instanceof RCPosition) {
			
			valid((RCPosition)constraints);
			rc = (RCPosition) constraints;
		}else {
			throw new CalcLayoutException();
		}
		
		components[rc.getRow() - 1][ rc.getColumn() - 1] = comp;
		
		Dimension dimPref = comp.getPreferredSize();
		Dimension dimMin = comp.getMinimumSize();

		if(rc.getRow() == 1 && rc.getColumn() == 1) {
			
			double w;
			
			
			w = (dimPref.getWidth() - (4 * gap) ) / 5;
			
			if(w > columnWidthPref) {
				columnWidthPref = w;
			}
			
			
			w = (dimMin.getWidth() - (4 * gap) ) / 5;
			
			if(w > columnWidthMin) {
				columnWidthMin = w;
			}
			
		}else {
		
			if(dimPref.getWidth() > columnWidthPref) {
				columnWidthPref = dimPref.getWidth();
			}
		
			if(dimMin.getWidth() > columnWidthMin) {
				columnWidthMin = dimMin.getWidth();
			}
			
		}
		
		if(dimPref.getHeight() > rowHeightPref) {
			rowHeightPref = dimPref.getHeight();
		}
		
		
		if(dimMin.getHeight() > rowHeightMin) {
			rowHeightMin = dimMin.getHeight();
		}
		
	}
	
	/**
	 * Method takes string, and extracts two numbers from 
	 * that string, then saves them in object that is instance of
	 * {@link RCPosition}. String has to be in special form:
	 * "a,b", where 'a' and 'b' represents numbers. There can be spaces
	 * between any of the given characters, but there cannot be 
	 * character any other then two numbers and ',' that seperates them. Throws exception
	 * if that is not the case 
	 * @param string
	 * @return RCPosition
	 * @throws CalcLayoutException
	 */
	private RCPosition parse(String string) {
		
		if(!string.matches("(\\s+)?\\d(\\s+)?,(\\\\s+)?\\d(\\\\s+)?")) {
			throw new CalcLayoutException();
		}
		
		String[] split = string.split(",");
		RCPosition rc;
		
		try {	
			rc = new RCPosition(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
		}catch(NumberFormatException ex) {
			throw new CalcLayoutException();
		}
		
		valid(rc);
		
		return rc;
		
		
	}
	/** 
	 * Checks if given <code>rc</code> is valid
	 * for this class. Valid RCPosition, in this case,
	 * can mean different things:
	 * 1. first number is '1', but then second number has to be 1, or
	 * 6 or 7.
	 * 2. first number is any number less then 6, (6 not included), and
	 * second number is any number less then 8, (8 not included)
	 * @param rc
	 */
	private void valid(RCPosition rc) {
		
		int r = rc.getRow() - 1;
		int c = rc.getColumn() - 1;
		
		if(r < 0 || c > 6 || r > 4 || c < 0 || components[r][c] != null || ( components[0][0] != null && r == 0 && c <= 4) || (r == 0 && c >0 && c <=4 )) {
			throw new CalcLayoutException();
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		
		Integer w = Integer.MAX_VALUE * 7 + 6 * gap;
		Integer h = Integer.MAX_VALUE * 5 + 4 * gap;
		
		return new Dimension(w, h);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {
		
	}


}
