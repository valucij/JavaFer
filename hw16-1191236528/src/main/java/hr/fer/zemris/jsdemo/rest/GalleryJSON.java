package hr.fer.zemris.jsdemo.rest;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.gallery.bean.Picture;


/**
 * Class that uses org.json for working with JSON format
 * This class has methods that, once required, prepare tags, or pictures for
 * displaying
 * 
 * @author Lucija Valentić
 */
@Path("/tagovi")
public class GalleryJSON {

	/**
	 * Internal list that keeps all pictures in "database"
	 */
	List<Picture> pictures= new ArrayList<Picture>();
	/**
	 * Servlet context, use can use it when he needs
	 * some real paths of files
	 */
	@Context ServletContext context;
	
	
	/**
	 * This method gathers all tags from file "opisnik.txt", and 
	 * "remembers" all picture in "database"
	 * @return Response
	 */
	@GET
	@Produces("application/json")
	@Context
	public Response getTagsList() {
		
		String fileName = context.getRealPath("/WEB-INF/opisnik.txt");
		
		List<String> informations;
		
		try {
			informations = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			
			JSONObject message = new JSONObject();
			message.put("errorMessage", "Datoteka u kojoj su upisane informacije o slici nije pronađena");
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();
		}		
		
		for(int i = 0; i < informations.size(); i+=3) {
		
			Picture p = new Picture();
			p.setTitle(informations.get(i).trim()); 
			p.setDescription(informations.get(i+1).trim());
			p.setTags(informations.get(i+2).trim());
			
			pictures.add(p);
		}
		
		
		JSONArray result = new JSONArray();
		Set<String> duplicates = new HashSet<String>();
		
		for(Picture p : pictures) {
			
			List<String> tags = p.getTags();
			
			for(String t : tags) {
				
				if(!duplicates.contains(t)) {
					duplicates.add(t);
					result.put(t);
				}
				
			}
			
		}
		
		duplicates.clear();
		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * This method prepares names of all pictures that
	 * has the same tag, tag sent in parameters. This method
	 * also creates new folder (if that folder doesn't exist; if
	 * it does, nothing happened). It also makes thumbnails (smaller)
	 * pictures in it, "copies" of original pictures. If those thumbnails
	 * already exist, nothing happens.
	 * @param tag
	 * @return
	 */
	@Path("{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags(@PathParam("tag") String tag) {
		
		JSONArray result = new JSONArray();
		
		String thumbnailsDir = context.getRealPath("/WEB-INF/thumbnails");
		
		if(!Paths.get(thumbnailsDir).toFile().exists()) {
			new File(thumbnailsDir).mkdir();
		}
		
		for(Picture p : pictures) {
			
			if(hasTag(p, tag)) {
				
				if(!Paths.get(thumbnailsDir + "/" + p.getTitle()).toFile().exists()) {
					
					String originalImage = context.getRealPath("/WEB-INF/slike/"+p.getTitle());
					try {
						resizeImage(originalImage ,Paths.get(thumbnailsDir + "/" + p.getTitle()).toFile(), 150, 150, BufferedImage.TYPE_INT_ARGB);
						
					} catch (IOException ignorable) {
					}
					
				}
				
				result.put(p.getTitle());
			}
		}
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * This method prepares one picture for displaying. It puts
	 * into JSONArray pictures name-title, description and tags.
	 * @param pictureName
	 * @return
	 */
	@Path("/jedna/{pictureName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPicture(@PathParam("pictureName") String pictureName) {
		
		String name = pictureName.substring(5);
		JSONArray result = new JSONArray();
		
		for(Picture p : pictures) {
			
			if(p.getTitle().equals(name)) {
				result.put(p.getTitle());
				result.put(p.getDescription());
				result.put(tagsToString(p.getTags()));
			}
		}
		return Response.status(Status.OK).entity(result.toString()).build();
		
	}

	/**
	 * Method resizes given original picture, and saves it in new directory, 
	 * with new name - that is given with parameter file
	 * @param originalImage String 
	 * @param file File
	 * @param height int
	 * @param width int
	 * @param type int
	 * @throws IOException
	 */
	private void resizeImage(String originalImage, File file, int height, int width, int type) throws IOException {
		
		Image og = ImageIO.read(new File(originalImage)).getScaledInstance(150, 150, BufferedImage.SCALE_SMOOTH);
		
		BufferedImage img = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		img.createGraphics().drawImage(og, 0, 0, null);
		ImageIO.write(img, "jpg", new File(file.toString()));
		
	}

	/**
	 * Method checks if given picture p has tag
	 * that is the same as given tag. Returns <code>true</code>
	 * if it has, <code>false</code> otherwise.
	 * @param p Picture
	 * @param tag String
	 * @return boolean
	 */
	private boolean hasTag(Picture p, String tag) {
		
		List<String> tags = p.getTags();
		
		for(String t : tags) {
			if(t.equals(tag)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * "Converts" tags to String. Tags in pictures are usually in
	 * lists, so this method extracts them, and puts them in one string, 
	 * separated with commas, and blanks
	 * @param tags
	 * @return
	 */
	private String tagsToString(List<String> tags) {
		
		StringBuilder sb = new StringBuilder();
		boolean flagFirst = true;
		
		for(String t : tags) {
			
			if(!flagFirst) {
				sb.append(", ");
			}
			flagFirst = false;
			sb.append(t);
			
		}
		
		return sb.toString();
		
	}
	
	
	
}
