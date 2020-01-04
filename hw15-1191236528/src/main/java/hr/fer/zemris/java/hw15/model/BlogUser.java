package hr.fer.zemris.java.hw15.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Class that models one blog user
 * 
 * @author Lucija Valentić
 *
 */
@Entity
@Table(name="blog_user")
public class BlogUser {
	
	/**
	 * Id of a user
	 */
	private Long id;
	/**
	 * First name of user
	 */
	private String firstName;
	/**
	 * Last name of a user
	 */
	private String lastName;
	/**
	 * Username of a user
	 */
	private String nick;
	/**
	 * E-mail of a user
	 */
	private String email;
	/**
	 * passwordHash of a user
	 */
	private String passwordHash;
	/**
	 * List of all blog entries of a user
	 */
	private List<BlogEntry> blogEntries;
	
	/**
	 * Returns this.id
	 * @return Long
	 */
	@Id @GeneratedValue
	public Long getId() {
		return this.id;
	}

	/**
	 * Returns this.firstName
	 * @return String
	 */
	@Column(length = 20, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets this.firstName
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns this.lastName
	 * @return String
	 */
	@Column(length = 30, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets this.lastName
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns this.nick
	 * @return String
	 */
	@Column(length = 20, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Sets this.nick
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Returns this.email
	 * @return String
	 */
	@Column(length = 50, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Sets this.email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns this.passwordHash
	 * @return String
	 */
	@Column(length = 100, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets this.passwordHash
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Sets this.id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Return this.blogEntries
	 * @return List<BlogEntry>
	 */
	@OneToMany(mappedBy="blogUser", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Sets this.blgoEntries
	 * @param blogEntries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
