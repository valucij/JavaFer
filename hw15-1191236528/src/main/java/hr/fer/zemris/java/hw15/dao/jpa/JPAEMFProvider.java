package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;
/**
 * Class has methods that provide {@link EntityManagerFactory}
 * 
 * @author Lucija Valentić
 *
 */
public class JPAEMFProvider {

	/**
	 * Internal {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Returns this.emf
	 * @return EntityManagerFactory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets this.emf
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}