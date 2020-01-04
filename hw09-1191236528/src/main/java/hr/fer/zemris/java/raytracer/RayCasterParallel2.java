package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class is used for simple ray caster. When main program
 * is open, picture of two spheres will be shown. Those spheres
 * will be illuminated with two source of light, and it
 * will be colored based on Phong - model; it will also be reflective.
 * Well, job of this class is to prepare everything so right picture is shown. 
 * Class computes color components for each pixel on the screen, as well as 
 * how that color need to be shown, and based on some diffusion and 
 * reflective components, colors are calculated. If you are intereseted more
 * in theory, read http://java.zemris.fer.hr/nastava/irg/,
 * chapters 9.2, and 10.2.
 * 
 *  However, this class is different from {@link RayCaster}.
 *  This class uses thread pool for computing data, and also
 *  picture that is shown rotates in time.
 *  
 * @author Lucija Valentić
 *
 */

public class RayCasterParallel2 {


	/**
	 * Main method that is called in the beginning of the program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
		}
	
	/**
	 * This method returns one implementation of interface
	 * {@link IRayTracerAnimator}. This implementation is animation
	 * support for raytracer. Using methods, class can inform 
	 * GUI how to 'animate' picture, where is 'up', where is 
	 * 'eye' in the picture etc...
	 * @return IRayTracerAnimator
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			/**
			 * Represents time
			 */
			long time;
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void update(long deltaTime) {
				time += deltaTime;
				
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getViewUp() {
				return new Point3D(0, 0, 10);
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getView() {
				return new Point3D( -2, 0, -0.5);
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public long getTargetTimeFrameDuration() {
				return 150;
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getEye() {
				double t = (double) time/ 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 3 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}
	
	/**
	 * Class extends class {@link RecursiveAction}. This class represents
	 * object that will be 'jobs' for fork thread pool. Method compute of this class
	 * compute for every pixel, how that pixel will be shown in the picture,
	 * and essentially it 'paints' that way the whole picutre
	 * @author Lucija Valentić
	 *
	 */
	@SuppressWarnings("unused")
	public static class RayCasterJob extends RecursiveAction{

		/**
		 * Serial number
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Represents position of an eye in space
		 */
		private Point3D eye;
		/**
		 * Represents point on the screen
		 */
		private Point3D view;
		/**
		 * Represent viewUp, so vector j can be calculated
		 */
		private Point3D viewUp;
		/**
		 * Represents horizontal, 'direction' of vector j
		 */
		private double horizontal;
		/**
		 * Represents vertical, 'direction' of vector i
		 */
		private double vertical;
		/**
		 * Represents width
		 */
		private int width;
		/**
		 * Represent height
		 */
		private int height;
		/**
		 * Represents requestNo
		 */
		private long requestNo;
		/**
		 * Represents data for red color
		 */
		private short[] red;
		/**
		 * Represents data for blue color
		 */
		private short[] blue;
		/**
		 * Represents data for green color
		 */
		private short[] green;
		/**
		 * Represents cancel
		 */
		private AtomicBoolean cancel;
		/**
		 * Maximum y
		 */
		private int yMax;
		/**
		 * Minimum y
		 */
		private int yMin;
		/**
		 * Max number of direct computing
		 */
		private final int MAX_NUMBER_COMPUTING = 50;
		/**
		 * Constructor
		 * @param eye
		 * @param view
		 * @param viewUp
		 * @param horizontal
		 * @param vertical
		 * @param width
		 * @param height
		 * @param requestNo
		 * @param red
		 * @param blue
		 * @param green
		 * @param cancel
		 * @param yMax 
		 * @param yMin 
		 */
		public RayCasterJob(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
				int height, long requestNo, short[] red, short[] blue, short[] green, AtomicBoolean cancel, int yMin, int yMax) {
			
			this.eye = eye;
			this.view = view;
			this.viewUp = viewUp;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.requestNo = requestNo;
			this.blue = blue;
			this.red = red;
			this.green = green;
			this.cancel = cancel;
			this.yMax = yMax;
			this.yMin = yMin;
		}

		/**
		 * {@inheritDoc} 
		 */
		@Override
		protected void compute() {

			if(yMax - yMin < MAX_NUMBER_COMPUTING) {
				directCalculation();
				return;
			}else {
				invokeAll(new RayCasterJob(eye, view, viewUp, horizontal, vertical, width, height, requestNo, red, blue, green, cancel, yMin, (yMax - yMin)/2 + yMin),
						new RayCasterJob(eye, view, viewUp, horizontal, vertical, width, height, requestNo, red, blue, green, cancel, (yMax - yMin)/2 + yMin, yMax));
						
			}
			
		}
		
		/**
		* 
		* Method calculates all the data, aka how each object
		* in scene will be colored, and how every color
	 	* will be shown, and than it sends those informations to observer,
	 	* object type IRayTracerResultObserver, so that he can actually
	 	* 'paint' the picture
	 	* @return IRayTracerProducer
	 	*/
		private void directCalculation() {
			
			Point3D OG = view.difference(view, eye).modifyNormalize();
			Point3D zAxis = OG;
			Point3D yAxis = viewUp.normalize().sub(OG.scalarMultiply(OG.scalarProduct(viewUp.normalize()))).normalize();
			Point3D xAxis = OG.vectorProduct(yAxis).modifyNormalize();
			
			Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
			Scene scene = RayTracerViewer.createPredefinedScene2();
			
			short[] rgb = new short[3];
			int offset = yMin*width;
			
			for(int y = yMin; y < yMax; y++) {
				for(int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((double)1/(width-1)*horizontal*x))
							.modifySub(yAxis.scalarMultiply((double)1/(height-1)*vertical*y));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					
					tracer(scene, ray, rgb);
					
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					
					offset++;
				}
			}
	
		}
		
		/**
		 * Method calculates how every object in scene will be colored.
		 * It calculates color component of final picture
		 * 
		 * @param scene
		 * @param ray
		 * @param rgb
		 */
		private void tracer(Scene scene, Ray ray, short[] rgb) {
			
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			
			RayIntersection closest = findClosestIntersection(scene, ray);
			
			if(closest != null) {
				determineColorFor(scene, closest, rgb, ray);
			}

		}
		
		
		
	}
	
	/**
	 * Class returns one implementation of interface {@link IRayTracerProducer}.
	 * This class uses thread pool for computing how every object in the 
	 * scene will be shown. Thread pool that is used is ForkJoinPool.
	 * @return IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		
		return new IRayTracerProducer() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, 
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new RayCasterJob(eye, view, viewUp, horizontal, vertical, width, height, requestNo, red, blue, green, cancel, 0, height));
				
				pool.shutdown();
								
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			
			
		};
		
	}

	/**
	 * Method determinates color and how some object will be colored for
	 * every light source. Usually, in space, object can reflect light not 
	 * only from light source, but light reflected from other objects
	 * and etc. So how some object is colored and shown in space is determined
	 * by its position, and by how much light hits it. So this method
	 * calculates exactly that
	 * @param scene
	 * @param ray
	 * @param rgb
	 * @param rayFromEye
	 */

	private static void determineColorFor(Scene scene, RayIntersection ray, short[] rgb, Ray rayFromEye) {
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		List<LightSource> lights = scene.getLights();
		
		for(LightSource light : lights) {
			
			Ray r = Ray.fromPoints(light.getPoint(), ray.getPoint());
			RayIntersection closest = findClosestIntersection(scene, r);
			
			if(closest != null && (light.getPoint().sub(ray.getPoint()).norm() > light.getPoint().sub(closest.getPoint()).norm() + 0.001)) {
				continue;
			}else {
				diffuseAndReflectiveComponent(light, closest, rgb, rayFromEye);
			}
		}		
	}

	/**
	 * Method sets color components for some point in space. How that point
	 * in space will be colored is determined by the light source that
	 * hits that point, and by ray that hits that point, but the 
	 * one that exits the eye.
	 * @param light
	 * @param ray
	 * @param rgb
	 * @param rayFromEye
	 */
	private static void diffuseAndReflectiveComponent(LightSource light, RayIntersection ray, short[] rgb, Ray rayFromEye) {
		
		if(ray == null) {
			return;
		}
		//difuzni dio
		Point3D n = ray.getNormal().normalize();
		Point3D l = light.getPoint().sub(ray.getPoint()).normalize();
		//Point3D l = ray.getPoint().sub(light.getPoint()).normalize();
		
		double nl = n.scalarProduct(l);
		
		rgb[0] += light.getR() * ray.getKdr() * Math.max(nl, 0);
		rgb[1] += light.getG() * ray.getKdg() * Math.max(nl, 0);
		rgb[2] += light.getB() * ray.getKdb() * Math.max(nl, 0);
		
		//zrcalni dio
		Point3D v = rayFromEye.start.sub(ray.getPoint()).normalize();
		n = ray.getNormal().normalize();
		l = light.getPoint().sub(ray.getPoint()).normalize();
		double krn = ray.getKrn();
		double cos = l.scalarProduct(n)/(l.norm() * n.norm());
		l = light.getPoint().sub(ray.getPoint()).normalize();
		
		Point3D r = n.scalarMultiply(2 * cos).sub(l).normalize();
		double cosA = r.scalarProduct(v);
			
		rgb[0] += light.getR() * ray.getKrr() * Math.pow(cosA, krn);
		rgb[1] += light.getG() * ray.getKrg() * Math.pow(cosA, krn);
		rgb[2] += light.getB() * ray.getKrb() * Math.pow(cosA, krn);
		
	}
	/**
	 * Method finds closest intersection between given ray, and
	 * any object in the scene. If given ray
	 * doesn't hit any object in space, then null is returned
	 * @param scene
	 * @param ray
	 * @return RayIntersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		List<GraphicalObject> objects = scene.getObjects();
		RayIntersection min = null;
		
		for(GraphicalObject object : objects) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			
			if(intersection != null && (min == null || min.getDistance() > intersection.getDistance())) {
				min = intersection;
			}
		}
		
		return min;
	}
	
	

}
