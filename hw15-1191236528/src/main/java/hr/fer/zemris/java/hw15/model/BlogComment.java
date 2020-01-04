package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class models blog comment
 * 
 * @author Lucija Valentić
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Id of a comment
	 */
	private Long id;
	/**
	 * Blog entry where comment was left
	 */
	private BlogEntry blogEntry;
	/**
	 * E-mail of a user who left a comment
	 */
	private String usersEMail;
	/**
	 * Message of a comment
	 */
	private String message;
	/**
	 * Time when comment was posted
	 */
	private Date postedOn;
	
	/**
	 * Returns this.id
	 * @return Long
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets this.id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns this.blogEntry
	 * @return BlogEntry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets this.blogEntry
	 * @param blogEntry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns this.userEmail
	 * @return String
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets this.userEmail
	 * @param usersEMail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns this.message
	 * @return String
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets this.message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns this.postedOn
	 * @return Date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets this.postedOn
	 * @param postedOn
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}