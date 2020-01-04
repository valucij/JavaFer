package hr.fer.zemris.gallery.bean;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that represents picture objects. Every picture
 * has title, descriptions, and list of tags/
 * 
 * @author Lucija Valentić
 *
 */
public class Picture {

	/**
	 * Title of picture
	 */
	private String title;
	/**
	 * Description of a picture
	 */
	private String description;
	/**
	 * List of tags
	 */
	private List<String> tags;
	
	/**
	 * Constructor
	 */
	public Picture() {
		tags = new ArrayList<String>();
	}
	/**
	 * Sets title of this picture
	 * @param title String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Sets description of this picture
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Sets tag of this picture
	 * @param allTags String
	 */
	public void setTags(String allTags){
		
		String[] t = allTags.split(",");
		
		for(String s : t) {
			tags.add(s.trim());
		}
	}

	/**
	 * Returns this.title
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns this.description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns this.tags
	 * @return List<String>
	 */
	public List<String> getTags() {
		return tags;
	}
	
	

}
