package hr.fer.zemris.java.hw05.db;

import java.util.*;
/**
 * Class represents one student. It has variable
 * such as firstName, lastName, jmbag, finalGrade. It
 * also implements its own method hashCode() and equals(),
 * so we can compare two students in the way we want.
 * Two stuents are the same if their jmbags are the same. Same goes
 * with lesser or greater students.
 * 
 * @author Lucija Valentić
 *
 */
public class StudentRecord {

	/**
	 * Represents jmbag of a student.
	 */
	private String jmbag;
	/**
	 * Represents name of a student.
	 */
	private String firstName;
	/**
	 * Represents last name of a student
	 */
	private String lastName;
	/**
	 * Represents final grade in class of a student
	 */
	private String finalGrade;
	
	/**
	 * Constructor 
	 * 
	 * @param jmbag
	 * @param firstName
	 * @param lastName
	 * @param grade
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, String grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = grade;
		
	}	
	/**
	 * Returns jmbag from this student.
	 * @return this.jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns name from this student.
	 * @return this.firstNane
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Returns last name from this student.
	 * @return this.lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Returns final grade from this student.
	 * @return this.finalGrade
	 */
	public String getFinalGrade() {
		return finalGrade;
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
		return  jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}
}
