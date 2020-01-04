package hr.fer.zemris.java.raytracer.model;

/**
 * This class extends  class {@link GraphicalObject}.
 * Class represents sphere in space
 * @author Lucija Valentić
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of sphere
	 */
	private Point3D center;
	/**
	 * Radius of sphere
	 */
	private double radius;
	/**
	 * Red diffuse component
	 */
	private double kdr;
	/**
	 * Green diffuse component
	 */
	private double kdg;
	/**
	 * Blue diffuse component
	 */
	private double kdb;
	/**
	 * Red reflective component
	 */
	private double krr;
	/**
	 * Green reflective component
	 */
	private double krg;
	/**
	 * Blue reflective component
	 */
	private double krb;
	/**
	 * Coefficient n for reflective components
	 */
	private double krn;
	
	/**
	 * Constructor
	 * @param center
	 * @param radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * {@inheritDoc}
	 * This method calculates intersecton between ray and sphere in
	 * geometrical way, and Pythagorean theorem is used.
	 * Picture given in the link 
	 * https://www.scratchapixel.com/images/upload/ray-simple-shapes/raysphereisect1.png?
	 * shows how exactly intersection is calculated. Same labels are used, so it is
	 * easier to follow the picture and the code.
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D rayStart = ray.start;
		Point3D rayDirection = ray.direction;
		
		Point3D L = center.sub(rayStart);
		
		double tca = L.scalarProduct(rayDirection);
		
		if(tca < 0) {
			return null;
		}
		
		double d = Math.sqrt( L.scalarProduct(L) - tca * tca);
		
		if(d > radius) {
			return null;
		}
		
		double thc = Math.sqrt( radius * radius - d * d);
		
		double t0 = tca - thc;
		
		Point3D pointIntersection = rayStart.add(rayDirection.scalarMultiply(t0));
		
		double distance = pointIntersection.sub(rayStart).norm();
		
		boolean outer = true;
		
		return new RayIntersection(pointIntersection, distance, outer) {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getNormal() {
				return this.getPoint().sub(center);
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrr() {
				return krr;
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrn() {
				return  krn;
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrg() {
				return krg;
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrb() {
				return krb;
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdr() {
				return kdr;
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdg() {
				return kdg;
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

	/**
	 * Returns center of the sphere
	 * @return this.center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Returns radius of the sphere
	 * @return this.radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	* Returns coefficient for diffuse component for red color; used in
	* lightning model to calculate point color.
	*
	* @return red diffuse component
	*/
	public double getKdr() {
		return kdr;
	}

	/**
	* Returns coefficient for diffuse component for green color; used in
	* lightning model to calculate point color. 
	*
	* @return green diffuse component
	*/
	public double getKdg() {
		return kdg;
	}

	/**
	* Returns coefficient for diffuse component for blue blue; used in
	* lightning model to calculate point color. 
	*
	* @return blue diffuse component
	*/
	public double getKdb() {
		return kdb;
	}

	/**
	* Returns coefficient for reflective component for red color; used in
	* lightning model to calculate point color. 
	* 
	* @return red reflective component
	*/
	public double getKrr() {
		return krr;
	}

	/**
	* Returns coefficient for reflective component for green color; used in
	* lightning model to calculate point color.
	*
	* @return green reflective component
	*/
	public double getKrg() {
		return krg;
	}

	/**
	 * Returns coefficient for reflective component for blue color; used in
	 * lightning model to calculate point color.
	 * @return blue reflective component
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * Returns coefficient <code>n</code> for reflective component.
	 * It is used in lighting model to calculate point color.
	 * @return n
	 */
	public double getKrn() {
		return krn;
	}
	
	

}
