package hr.fer.zemris.java.fractals;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.*;
import hr.fer.zemris.math.*;

/**
 * Class is used for fractal viewer. Fractal viewer that is used
 * is Newton-Rapshson iteration-based. In this class, some
 * other classes are used, such as {@link ComplexPolynomial} and
 * {@link ComplexRootedPolynomial}. When program is started, user
 * has to write two or more roots for polynom, and than
 * after he signals he is done, fractal is shown with fractal-viewer.
 * Of course, calculation of fractal would be very time-consuming, 
 * but threads are used, so time for computing data which dictate how
 * fractal is shown is shortened. Said data are actually roots of polynom,
 * but calculated in special way. For every pixel on screen, it becomes
 * one point in complex plane, and than with newton based iterations other
 * points are calculated. After some time, when those points become very close
 * to each other, root that is closest to said points is returned and stored into data.
 * @author Lucija Valentić
 *
 */
public class Newton {

	/**
	 * Represents convergence treshold, shows when points are very close
	 * in iterations
	 */
	public static final double convergenceTreshold = 0.001;
	/**
	 * Represents max of iterations
	 */
	public static final int maxIter = Integer.MAX_VALUE;
	/**
	 * Represents root treshold, for computing
	 * indexOfClosestRoot in {@link ComplexRootedPolynomial}
	 */
	public static final double rootTreshold = 0.002;
	/**
	 * List of roots, but in string form
	 */
	private static List<String> roots;
	/**
	 * Polynom, but derived one
	 */
	private static ComplexPolynomial derived;
	/**
	 * Polynom, but in form z0*(z-z1)*(z-z2)*...
	 */
	private static ComplexRootedPolynomial rootedPolynomial;
	
	/**
	 * Main method that is called in the beginning of the program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Rapshson iterration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Scanner sc = new Scanner(System.in);
		
		roots = new LinkedList<String>();
		int i = 1;
		while(true) {
			
			System.out.print("Root " + i + " > ");
			i++;
			String string = null;
			
			if(sc.hasNext()) {
				string = sc.nextLine();
			}
			
			if(string.equals("done")) {
				
				if(roots.size() < 2) {
					System.out.println("You entered " + roots.size() + ", expected at least 2. Enter more.");
				}else {
					break;	
				}
				
			}
			
			roots.add(string);
		}
		
		Complex[] croots = toRoots(roots);
		sc.close();
		if(croots == null) {
			throw new IllegalArgumentException("You entered invalid roots.\n"
					+ "Valid form of one root is: a+ib or a-ib, parts with zeros can be dropped, but not "
					+ "both.\n If there is 'i' given, but no b, then b = 1 by default.");
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		Complex constant = Complex.ONE;
		rootedPolynomial = new ComplexRootedPolynomial(constant, croots);
		derived = rootedPolynomial.toComplexPolynom().derive();
		
		FractalViewer.show(new NewtonProducer());
		
	}
	
	/**
	 * From given list of strings, for every string in list,
	 * it extracts one complex number. If some string doesn't 
	 * represent valid complex number, null is returned. Valid complex
	 * numbers are: a + ib, and numbers with either parts zero can be written 
	 * and parts can be left out, but only one. Also, if there is only 'i', b is
	 * then = 1
	 * @param list
	 * @return roots
	 */
	private static Complex[] toRoots(List<String> list) {
		
		Complex[] roots = new Complex[list.size()];
		int i = 0;
		
		for(String string : list) {
		
		
			if( !string.isBlank() && !string.matches("(\\s+)?-?(\\d+)?(\\s+)?[+-]?(\\s+)?i?(\\d+)?(\\s+)?")) {
				return null;
			}else {
				roots[i] = parseComplex(string);
				if(roots[i] == null) {
					return null;
				}
				i++;
			}	
		}
		
		return roots;
	}

	/**
	 * From given string, it parses complex number. This
	 * method is called when we know that said string 
	 * has complex number in it. However, if string is
	 * 'weird' aka if it is null or blank, null is returned 
	 * @param string
	 * @return complex number
	 */
	private static Complex parseComplex(String string) {
		
		
		if(string == null || string.isBlank()) {
			return null;
		}
		
		double re = 0;
		double im = 0;
		
		String[] parts = string.split("[+-]");
		
		if((parts[0].isBlank() && parts.length == 2)|| parts.length < 2) {
			
			int indexSign = string.indexOf("-");
			String sign = null;
			if(indexSign != -1) {
				sign = "-";
			}else {
				sign = "+";
			}
			
			int i = string.indexOf("i");
			
			if(i == -1) {
				
				re = Double.parseDouble(string);
			}else {
				
				if(i + 1 == string.length() || string.substring(i + 1).isBlank()) {
			
					im = Double.parseDouble(sign+"1");
				}else {
					im = Double.parseDouble(sign+string.substring( i + 1));	
				}				
			}
			
		}else {
			
			int indexSign = -1;
			String sign = null;
			if(parts.length > 2) {
				
				indexSign = string.indexOf("-");
				
				if(indexSign == -1 || indexSign >= parts[1].length() ) {
					sign = "+";
				}else {
					sign = "-";
				}
				
				re = Double.parseDouble(sign+parts[1]);
			}else {
				indexSign = string.indexOf("-");
				
				if(indexSign == -1 || indexSign >= parts[0].length()) {
					sign = "+";
				}else {
					sign = "-";
				}
				
				re = Double.parseDouble(sign+parts[0]);	
			}
			
			int i = -1;
			String imag = null;
			
			if(parts.length > 2) {
				
				indexSign = string.lastIndexOf("-");
				
				if(indexSign == -1 || indexSign < string.indexOf(parts[1])) {
					sign = "+";
				}else {
					sign = "-";
				}
				
				i = parts[2].indexOf("i");
				imag = parts[2];
			}else {
				
				indexSign = string.lastIndexOf("-");
				
				if(indexSign == -1 || indexSign < string.indexOf(parts[0])) {
					sign = "+";
				}else {
					sign = "-";
				}
				
				imag = parts[1];
				i = parts[1].indexOf("i");
			}
			
			if(i == -1) {
				return null;
			}
			
			if(i + 1 == imag.length() || imag.substring(i + 1).isBlank()) {
				im = Double.parseDouble(sign+"1");
				
			}else {
				im = Double.parseDouble(sign+imag.substring(i + 1));
			}
			
		}
		
		return new Complex(re,im);
		
	}
	
	/**
	 * Class is used as job for thread pool. This class
	 * has methods that compute data for fractals, briefly
	 * explained above.
	 * @author Lucija Valentić
	 *
	 */
	private static class NewtonJobCalculate implements Callable<Void>{

		/**
		 * Represents min of real part
		 */
		private double reMin;
		/**
		 * Represents max of real part
		 */
		private double reMax;
		/**
		 * Represent min of imaginary part
		 */
		private double imMin;
		/**
		 * Represents max of imaginary part
		 */
		private double imMax;
		/**
		 * Represents widht
		 */
		private int width;
		/**
		 * Represents height
		 */
		private int height;
		/**
		 * Represent min of y value
		 */
		private int yMin;
		/**
		 * Represent max of y value
		 */
		private int yMax;
		/**
		 * Represents data
		 */
		private short[] data;
		/**
		 * Represents offset
		 */
		private int offset;
		
		/**
		 * Constructor
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param data
		 */
		public NewtonJobCalculate(double reMin, double reMax, double imMin, double imMax, int width, int height,
				int yMin, int yMax, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Void call() throws Exception {
			
			offset = yMin * width;
			
			for(int y = yMin; y <= yMax; y++) {
				for(int x = 0; x < width; x++) {
					
					Complex zn = mapToComplexPlain(x, y, yMin, yMax, 0, width, reMin, reMax, imMin, imMax, height);
					int iter = 0;
					double module = 0;
					
					do {
						
						Complex numerator = rootedPolynomial.apply(zn);
						Complex demoninator = derived.apply(zn);
						Complex znOld = zn;
						Complex fraction = numerator.divide(demoninator);
						zn = zn.sub(fraction);
						module = znOld.sub(zn).module();
						iter++;
					
					}while( module > convergenceTreshold && iter < maxIter);

					int index = rootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
					
					data[offset++] = (short) (index + 1);
				}
			}
		
			return null;
		}

		/**
		 * Method is given a lot of arguments, the main ones
		 * are x and y that represent pixel on the screen, and some max and min
		 * values of y values and x values, width and length. Main job
		 * of this method is to 'map' that pixel to complex plain, so that
		 * it can be shown properly in picture one rendered
		 * @param x
		 * @param y
		 * @param yMin
		 * @param yMax
		 * @param xMin
		 * @param xMax
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param height
		 * @return
		 */
		private Complex mapToComplexPlain(int x, int y, int yMin, int yMax, int xMin, int xMax, double reMin,
				double reMax, double imMin, double imMax, double height) {
			
			double cRe = x / (xMax-1.0) * (reMax - reMin) + reMin;
			double cIm = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
			return new Complex(cRe, cIm);
			
		}		
	}
	
	/**
	 * Class implements {@link IFractalProducer}. It's job is to
	 * create thread pool, and to push all jobs to that thread. Jobs are
	 * actually computing data which will be in the end indication
	 * how fractal should be shown. Thread pool is made in constructor,
	 * so we need one daemonic thread to shut down everything once the program is closed.
	 * @author Lucija Valentić
	 *
	 */
	private static class NewtonProducer implements IFractalProducer{

		/**
		 * Represents thread pool
		 */
		private static ExecutorService pool;
		
		/**
		 * Constructor
		 */
		public NewtonProducer() {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DaemonicThreadFactory());
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean arg07) {
			
			int m = derived.order() + 2;
			short[] data = new short[height * width];
			
			final int brojTraka = height/8*Runtime.getRuntime().availableProcessors();
			int brojYPoTraci = height / brojTraka;

			List<Future<Void>> result = new ArrayList<>();
			
			for(int i = 0; i < brojTraka; i++) {
				
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci - 1;
				if(i == brojTraka - 1) {
					yMax = height - 1;
				}
				
				NewtonJobCalculate job = new NewtonJobCalculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
				result.add(pool.submit(job));	
			}
			
			for(Future<Void> job : result) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {	
				}
			}
			
			System.out.println("Computing finished.");
			observer.acceptResult(data, (short)m, requestNo);
		}
		
	}
	
	/**
	 * Class that implements {@link ThreadFactory}. Job
	 * of this class is to make daemon thread, which will close
	 * everything once we close the program. 
	 * @author Lucija Valentić
	 *
	 */
	private static class DaemonicThreadFactory implements ThreadFactory{

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = Executors.defaultThreadFactory().newThread(r);
			thread.setDaemon(true);
			return thread;
		}
		
	}

}
