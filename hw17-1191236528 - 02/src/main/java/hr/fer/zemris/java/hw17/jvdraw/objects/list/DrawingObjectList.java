package hr.fer.zemris.java.hw17.jvdraw.objects.list;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import hr.fer.zemris.java.hw17.jvdraw.JVDrawException;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

public class DrawingObjectList{

	private boolean pressed;
	private int index;
	private DrawingModel model;
	private JList<GeometricalObject> list;
	private double windowHeight;
	private double windowWidth;
	
	public DrawingObjectList(DrawingModel model, JList<GeometricalObject> list) {
		this.model = model;
		pressed = false;
		this.list = list;
		
		list.setFocusable(true);
		
		addMouseListener();
		addKeyListener();
	}
	
	
	
	public void setWindowHeight(double windowHeight) {
		this.windowHeight = windowHeight;
	}



	public void setWindowWidth(double windowWidth) {
		this.windowWidth = windowWidth;
	}



	public void setPressed() {
		pressed = false;
	}
	
	public void addMouseListener() {

		list.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(!pressed) {
					pressed = true;
					index = list.locationToIndex(e.getPoint());
				}else {
					int newIndex = list.locationToIndex(e.getPoint());
					if(newIndex != index) {
						index = newIndex;
						return;
					}
					
					GeometricalObject object = model.getObject(index);
					
					if(object == null) {
						return;
					}
					
					GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
					
					editor.setHeight(windowHeight);
					editor.setWidth(windowWidth);
					
					while(JOptionPane.showConfirmDialog(null, editor, "Options", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						
						try {
							editor.checkEditing();
							editor.acceptEditing();
							break;
						}catch(JVDrawException ex) {
							Util.showMessage(null, "Editing not accepted, try again", "message", "Message");
						}
						
					}
					
					setPressed();
				}
			}
		});
		
	}
	
	public void addKeyListener() {
		
		list.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				/*
				if(!pressed) {
					return;
				}*/
				
				if(e.getKeyChar() == '+' && e.getComponent().equals(list)) {
					
					GeometricalObject object = model.getObject(index);
					if(object == null) {
						return;
					}
					
					object.moveUp();
					
				}else if(e.getKeyChar() == '-' && e.getComponent().equals(list)) {
					GeometricalObject object = model.getObject(index);
					if(object == null) {
						return;
					}
					
					object.moveDown(windowHeight);
					
				}else if(e.getKeyCode() == KeyEvent.VK_DELETE && e.getComponent().equals(list)) {
					
					GeometricalObject object = model.getObject(index);
					if(object == null) {
						return;
					}
					model.remove(object);
				}
			}
		});
		
	}
	
}
