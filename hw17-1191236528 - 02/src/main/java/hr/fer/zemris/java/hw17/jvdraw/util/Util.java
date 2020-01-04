package hr.fer.zemris.java.hw17.jvdraw.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.JVDrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.JVPoint;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.objects.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.objects.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.tools.AbstractTool;

/**
 * Class that has methods used for saving, opening, and exporting files
 * @author Lucija Valentić
 *
 */
public class Util {
	
	/**
	 * Canvas
	 */
	private static JDrawingCanvas canvas;
	/**
	 * Main frame
	 */
	private static JVDraw frame;
	
	/**
	 * Method called when user wants to save file. Extension of saved file is ".jvd".
	 * If something goes wrong, appropriate message is written and 
	 * empty string (representing path) is returned 
	 * @param frame
	 * @param model
	 * @return String
	 */ 
	public static String saveAs(JVDraw frame, DrawingModel model) {
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save Document");
		
		FileNameExtensionFilter exFilter = new FileNameExtensionFilter("JVDraw files (*.jvd)", "*.jvd");
		jfc.setFileFilter(exFilter);
		
		if(jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			
			showMessage(frame, "File not saved!", "message", "Message");
			return "";
		}
		
		
		Path path = jfc.getSelectedFile().toPath();
		
		if(path.toFile().exists() &&  JOptionPane.showConfirmDialog(frame, 
				"File will be overwritten. Proceed?", "Message", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
			
			showMessage(frame, "File wasn't saved", "message", "Message");
			return "";
		}
		//ako ne postoji ili će se overwritteat
		boolean flag = save(frame, path.toString(), model);
		
		if(!flag) {
			return "";
		}
		
		model.clearModifiedFlag();
		frame.setSaveStatus();
		return path.toString();
	}
	
	/**
	 * Method called when user wants to save file to
	 * existing path
	 * @param frame
	 * @param path
	 * @param model
	 * @return
	 */
	public static boolean save(JVDraw frame, String path, DrawingModel model) {
		
		String forFile = prepareForSave(((JVDrawingModel) model).getObjects());
		
		if(!path.endsWith(".jvd")) {
			path += ".jvd";
		}
		
		try {
			Files.writeString(Paths.get(path), forFile);
		} catch (IOException e) {
			
			showMessage(frame, "Error while saving", "error", "Error");
			return false;
		}
		
		model.clearModifiedFlag();
		frame.setSaveStatus();
		return true;
	}
	/**
	 * Method called when user wants to open file 
	 * @param frame
	 * @param model
	 */
	public static void open(JVDraw frame, DrawingModel model) {
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open file");
		
		if(jfc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
		
			showMessage(frame, "File not opened", "message", "Message");
			return;
		}
		
		Path filePath = jfc.getSelectedFile().toPath();
		
		if(!filePath.toString().endsWith(".jvd")) {
			showMessage(frame, "File not opened", "error", "Error");
			return;
		}
		
		List<String> fileContent;
		
		try {
			fileContent = Files.readAllLines(filePath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			showMessage(frame, "File not opened, error occurred", "error", "Error");
			return;
		} 
		
		model.clear();
		canvas.paintComponents(canvas.getGraphics());
		
		for(String s : fileContent) {
		
			String[] info = s.split("\\s+");
			
			if(info.length == 0) {
				showMessage(frame, "Not able to read file, wrong format", "error", "Error");
				return;
			}
			
			if(info[0].equals("LINE")) {
				
				Line line = createLine(frame, info);
				if(line == null) {
					return;
				} 
				model.add(line);
				
			}else if(info[0].equals("CIRCLE")) {
				
				Circle circle = (Circle) createCircle(frame, info, "circle", 7);
				
				if(circle == null) {
					return;
				}
				model.add(circle);
				
			}else if(info[0].equals("FCIRCLE")) {
				FilledCircle fCircle = (FilledCircle) createCircle(frame, info, "fcircle", 10);
				
				if(fCircle == null) {
					return;
				}
				model.add(fCircle);
				
			}else {
				showMessage(frame, "Not able to read file, wrong format", "error", "Error");
				return;
			}
			
		}
		
		AbstractTool.setFirstClick();
		canvas.paintComponents(canvas.getGraphics());
		model.clearModifiedFlag();
		frame.setSaveStatus();
		frame.setPathToFile(filePath);
	}
	
	/**
	 * Method called when user wants to export some file as an image, or
	 * odf file
	 * @param frame
	 * @param model
	 */
	public static void export(JVDraw frame, DrawingModel model) {

		GeometricalObjectBBCalculator bbCalc = new GeometricalObjectBBCalculator();
		
		for(int i = 0; i < model.getSize(); i++) {
			GeometricalObject o = model.getObject(i);
			o.accept(bbCalc);
		}
		
		Rectangle box = bbCalc.getBoundingBox();
		
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g = image.createGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box.width, box.height);
		
		g.translate(- box.x, -box.y);
		
		for(int i = 0 ; i < model.getSize(); i++) {
			
			GeometricalObject o = model.getObject(i);
			o.accept(new GeometricalObjectPainter(g));
			
		}
		
		g.dispose();
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Export document");
		
		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG image(*.png)", "*.png");
		jfc.setFileFilter(pngFilter);
		FileNameExtensionFilter gifFilter = new FileNameExtensionFilter("GIF image(*.gif)", "*.gif");
		jfc.setFileFilter(gifFilter);
		FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG image(*.jpg)", "*.jpg");
		jfc.setFileFilter(jpgFilter);
		
		if(jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			
			showMessage(frame, "File not exported!", "message", "Message");
			return;
		}
		
		
		Path path = jfc.getSelectedFile().toPath();
		
		if(path.toFile().exists() &&  JOptionPane.showConfirmDialog(frame, 
				"File will be overwritten. Proceed?", "Message", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
			
			showMessage(frame, "File wasn't exported", "message", "Message");
			return;
		}
		
		String typeOfImage = jfc.getFileFilter().getDescription().substring(12, 15);
		
		File file = new File(path.toString() + "." +typeOfImage);
		
		try {
			ImageIO.write(image, typeOfImage, file);
		} catch (IOException e) {
			showMessage(frame, "File wasn't exported, error occurred", "error", "Error");
			return;
		}
		
		showMessage(frame, "File successfully exported", "message", "Message");
		
	}
	
	/**
	 * Method that goes through list of objects and prepares 
	 * file content that will be written in file for saving
	 * @param list
	 * @return
	 */
	private static String prepareForSave(List<GeometricalObject> list) {
		
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		
		for(GeometricalObject o : list) {
			
			if(flag) {
				flag = false;
			}else {
				sb.append("\n");
			}
			sb.append(o.saveToString());
			
		}
		return sb.toString();
		
	}
	
	/**
	 * Method that is used for displaying messages
	 * @param frame
	 * @param message
	 * @param type
	 * @param title
	 */
	public static void showMessage(JVDraw frame, String message, String type, String title) {
		
		int option = JOptionPane.ERROR_MESSAGE;
		
		if(type.equals("message")) {
			option = JOptionPane.INFORMATION_MESSAGE;
		}
		
		JOptionPane.showMessageDialog(frame, message, title, option);
	}
	
	/**
	 * Method that creates {@link Line} from array of strings,
	 * used when opening file. Message is shown if something
	 * goes wrong and null is returned. Otherwise, newly created line is
	 * returned
	 * @param frame
	 * @param info
	 * @return Line
	 */
	private static Line createLine(JVDraw frame, String[] info) {
		
		if(info.length != 8) {
			showMessage(frame, "Not able to read file, wrong format", "error", "Error");
			return null;	
		}
		
		double x1;
		double y1;
		double x2;
		double y2;
		double red;
		double green;
		double blue;
		
		try {
			x1 = Double.parseDouble(info[1]);
			y1 = Double.parseDouble(info[2]);
			x2 = Double.parseDouble(info[3]);
			y2 = Double.parseDouble(info[4]);
			red = Double.parseDouble(info[5]);
			green = Double.parseDouble(info[6]);
			blue = Double.parseDouble(info[7]);
			
			Line line = new Line();
			line.setAll(x1, y1, x2, y2, new Color((int)red, (int)green, (int)blue));
			return line;
			
		}catch(NumberFormatException ex){
			showMessage(frame, "Not able to read file, wrong format", "error", "Error");
			return null;
		}

	}

	/**
	 * Method that creates {@link Circle} or {@link FilledCircle} from array of strings,
	 * used when opening file. Message is shown if something
	 * goes wrong and null is returned. Otherwise, newly created circle/filledCircle is
	 * returned
	 * @param frame
	 * @param info
	 * @return GeometricalObject
	 */
	private static GeometricalObject createCircle(JVDraw frame, String[] info, String type, int len) {
		
		if(info.length != len) {
			showMessage(frame, "Not able to read file, wrong format", "error", "Error");
			return null;	
		}
		
		double x;
		double y;
		double radius;
		double fRed;
		double fGreen;
		double fBlue;
		double bRed;
		double bGreen;
		double bBlue;
		
		try {
			x = Double.parseDouble(info[1]);
			y = Double.parseDouble(info[2]);
			radius = Double.parseDouble(info[3]);
			fRed = Double.parseDouble(info[4]);
			fGreen = Double.parseDouble(info[5]);
			fBlue = Double.parseDouble(info[6]);
			
			if(type.equals("fcircle")) {
			
				bRed = Double.parseDouble(info[7]);
				bGreen = Double.parseDouble(info[8]);
				bBlue = Double.parseDouble(info[9]);
				
				FilledCircle fCircle = new FilledCircle();
				fCircle.setAll(new JVPoint(x, y), radius, new Color((int)fRed, (int)fGreen, (int)fBlue), 
						new Color((int)bRed, (int)bGreen, (int)bBlue));
				return fCircle;
			}
			
			Circle circle = new Circle();
			circle.setAll(new JVPoint(x, y), radius, new Color((int)fRed, (int)fGreen, (int)fBlue));
			return circle;
			
		}catch(NumberFormatException ex) {
			showMessage(frame, "Not able to read file, wrong format", "error", "Error");
			return null;	
		}
	}
	
	/**
	 * Sets canvas
	 * @param c
	 */
	public static void setCanvas(JDrawingCanvas c) {
		canvas = c;
	}
	
	/**
	 * Method called when it is needed for canvas to all function repaint()
	 */
	public static void canvasRepaint() {
		canvas.repaint();
	}
	
	/**
	 * Method that enables/disables save buttons
	 */
	public static void setSaveStatus() {
		frame.setSaveStatus();
	}
	/**
	 * Sets this.frame to given frame f
	 * @param f JVDraw
	 */
	public static void setFrame(JVDraw f) {
		frame = f;
	}
}

