package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;
/**
 * Class that implements 4 interfaces - {@link Consumer}, {@link Function}, {@link Predicate},
 * and {@link Supplier}. This class represents object that is used for {@link FillApp} and
 * coloring some pictures with appropriate algorithm. It keeps references to position of a
 * mouse when clicked, or more specifically, to the pixel that we call referent pixel. It
 * also keeps reference to color with which should surrounding area be filled/colored.
 * @author Lucija Valentić
 *
 */

public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel>{

	/**
	 * Reference to mouse position
	 */
	private Pixel reference;
	/**
	 * Reference to the picture that needs to be colored
	 */
	private Picture picture;
	/**
	 * Reference to the color with which part
	 * of a picture needs to be colored
	 */
	private int fillColor;
	/**
	 * Reference to the color in mouse position
	 */
	private int refColor;
	
	/**
	 * Constructor
	 * @param reference
	 * @param picture
	 * @param fillColor
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.picture = picture;
		this.reference = reference;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override//consumer - boja pixel u neku boju
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override//function - vraća sljedbenike
	public List<Pixel> apply(Pixel t) {
		
		List<Pixel> list = new LinkedList<Pixel>();
		
		int width = picture.getWidth();
		int height = picture.getHeight();
		
		if( (t.x + 1) < width ) {
			list.add(new Pixel(t.x + 1, t.y));
		}
		
		if( (t.y + 1) < height) {
			list.add(new Pixel(t.x, t.y + 1));
		}
		
		if( (t.x - 1) > 0) {
			list.add(new Pixel(t.x - 1, t.y));
		}
		
		if( (t.y - 1) > 0) {
			list.add(new Pixel(t.x, t.y - 1));
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override//predicate - je li iste boje
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.x, t.y) == refColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override//supplier - vraća početno stanje jel
	public Pixel get() {
		return reference;
	}
	
	
	
}
