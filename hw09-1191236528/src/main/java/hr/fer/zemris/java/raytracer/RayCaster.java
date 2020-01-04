package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
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
 * @author Lucija Valentić
 *
 */
public class RayCaster {

	/**
	 * Main method that is called in the beginning of the program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
						new Point3D(10,0,0),
						new Point3D(0,0,0),
						new Point3D(0,0,10),
						20, 20);
		}
	
	/**
	 * Method calculates all the data, aka how each object
	 * in scene will be colored, and how every color
	 * will be shown, and than it sends those informations to observer,
	 * object type IRayTracerResultObserver, so that he can actually
	 * 'paint' the picture
	 * @return IRayTracerProducer
	 */
	@SuppressWarnings("unused")
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
				
				Point3D OG = view.difference(view, eye).modifyNormalize();
				Point3D zAxis = OG;
				Point3D yAxis = viewUp.normalize().sub(OG.scalarMultiply(OG.scalarProduct(viewUp.normalize()))).normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).modifyNormalize();
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				
				for(int y = 0; y < height; y++) {
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
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
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
			
			Ray r = Ray.fromPoints(light.getPoint(),ray.getPoint());
			RayIntersection closest = findClosestIntersection(scene, r);
			
			if(closest != null && (light.getPoint().sub(ray.getPoint()).norm() > light.getPoint().sub(closest.getPoint()).norm() + 0.0001  )) {
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
