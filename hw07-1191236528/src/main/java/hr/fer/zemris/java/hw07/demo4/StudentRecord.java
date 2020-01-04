package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * Class represents one record for some students. This class
 * can keep students jmbag, name, surname, grades and points.
 * 
 * @author Lucija Valentić
 *
 */
public class StudentRecord {

	/**
	 * Students jmbag
	 */
	private int jmbag;
	/**
	 * Students name
	 */
	private String name;
	/**
	 * Students surname
	 */
	private String surname;
	/**
	 * Students points from middle exam
	 */
	private String mi;
	/**
	 * Students points from last exam
	 */
	private String zi;
	/**
	 * Students points from lab practice
	 */
	private String lab;
	/**
	 * Students grade for some course
	 */
	private int grade;
	
	/**
	 * Constructor
	 * @param jmbag
	 * @param surname
	 * @param name
	 * @param mi
	 * @param zi
	 * @param lab
	 * @param grade
	 */
	public StudentRecord(int jmbag, String surname, String name, String mi, String zi, String lab, int grade) {
		super();
		this.jmbag = jmbag;
		this.name = name;
		this.surname = surname;
		this.mi = mi;
		this.zi = zi;
		this.lab = lab;
		this.grade = grade;
	}

	/**
	 * Returns students jmbag
	 * @return this.jmbag
	 */
	public int getJmbag() {
		return jmbag;
	}

	/**
	 * Returns students name
	 * @return this.name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns students surname
	 * @return this.surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Returns students points from middle exam
	 * @return this.mi
	 */
	public String getMi() {
		return mi;
	}

	/**
	 * Returns students points from last exam
	 * @return this.zi
	 */
	public String getZi() {
		return zi;
	}

	/**
	 * Returns students points from lab practice
	 * @return this.lab
	 */
	public String getLab() {
		return lab;
	}

	/**
	 * Returns students grade
	 * @return this.grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		
		return properFormat(jmbag) + "\t" + surname + "\t" + name + "\t" + mi + "\t" + zi + "\t" + lab + "\t" + grade;
	}
	
	/**
	 * Method receives int that represents student jmbag,
	 * and returns string of proper format. Proper format means
	 * that number of integers in jmbag is 10, with leading numbers 0.
	 * @param n
	 * @return string
	 */
	public String properFormat(int n) {
		
		if(n < 10) {
			return "000000000" + String.valueOf(n);
		}else if(n < 100) {
			return "00000000" + String.valueOf(n);
		}else {
			return "0000000" + String.valueOf(n);
		}
		
	}
	
}
