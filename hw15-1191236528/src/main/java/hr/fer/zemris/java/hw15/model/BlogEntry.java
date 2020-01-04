package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that models one blog entry
 * 
 * @author Lucija Valentić
 *
 */
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Id of a blog entry
	 */
	private Long id;
	/**
	 * List of blog comments
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Date when this blog entry was created
	 */
	private Date createdAt;
	/**
	 * Date when this blog entry was last modified
	 */
	private Date lastModifiedAt;
	/**
	 * Title of a blog entry
	 */
	private String title;
	/**
	 * Text of a blog entry
	 */
	private String text;
	/**
	 * Author of a blog entry
	 */
	private BlogUser blogUser;
	
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
	 * Returns this.comments
	 * @return List<BlogComment>
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Sets this.comments
	 * @param comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns this.createdAt
	 * @return Date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets this.createdAt
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns this.lastModifiedAt
	 * @return Date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets this.lastModifiedAt
	 * @param lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns this.title
	 * @return String
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets this.title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns this.text
	 * @return String
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * Sets this.text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Returns this.blogUser 
	 * @return BlogUser
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getBlogUser() {
		return blogUser;
	}

	/**
	 * Sets this.blogUser
	 * @param blogUser
	 */
	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}