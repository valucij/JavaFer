package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.function.Supplier;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.JVDrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.list.DrawingObjectList;
import hr.fer.zemris.java.hw17.jvdraw.objects.list.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.AbstractTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * Class extends {@link JFrame}. It represents
 * app for drawing
 * @author Lucija Valentić
 *
 */
public class JVDraw extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Current used tool
	 */
	private Tool currentState;
	/**
	 * Drawing model that keeps all geometrical objects that
	 * were made
	 */
	private DrawingModel model;
	/**
	 * Canvas, where user can draw
	 */
	private JDrawingCanvas canvas;
	/**
	 * Label that displays colors
	 */
	private JLabel bottom;
	/**
	 * Color provider, for foreground color
	 */
	private JColorArea fgColor;
	/**
	 * Color provider, for background color
	 */
	private JColorArea bgColor;
	/**
	 * Path for saving
	 */
	private String path;
	/**
	 * Model for list
	 */
	private DrawingObjectListModel modelList;
	
	/**
	 * Constructor
	 */
	public JVDraw() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(100,100);
		setSize(600,500);
		initGUI();
		
	}
	
	/**
	 * Initializes everything in JFrame
	 */
	public void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		
		fgColor = new JColorArea(Color.BLUE);
		bgColor = new JColorArea(Color.RED);
		
		bottom = new JVDrawBottom(fgColor, bgColor);
		
		createActions();
		createMenu();
		
		model = new JVDrawingModel();
		modelList = new DrawingObjectListModel(model);
		
		JList<GeometricalObject> list = new JList<GeometricalObject>(modelList);
		DrawingObjectList listenList = new DrawingObjectList(model, list);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		
		currentState = new LineTool(model, fgColor);
		
		canvas = new JDrawingCanvas(model, new Supplier<Tool>() {
			@Override
			public Tool get() {
				return currentState;
			}
		});
		
		listenList.setWindowHeight(canvas.getHeight());
		listenList.setWindowWidth(canvas.getWidth());
		
		this.addComponentListener(new ComponentAdapter() {	
			@Override
			public void componentResized(ComponentEvent e) {
				listenList.setWindowHeight(canvas.getHeight());
				listenList.setWindowWidth(canvas.getWidth());
			}
		});
		
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentState.mouseClicked(e);
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				currentState.mouseMoved(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				currentState.mousePressed(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				currentState.mousePressed(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				currentState.mouseDragged(e);
			}
		};
		
		canvas.addMouseListener(adapter);
		canvas.addMouseMotionListener(adapter);
		
		model.addDrawingModelListener(canvas);
		AbstractTool.setCanvas(canvas);
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				JVDraw.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
			
		});
		
		Util.setCanvas(canvas);
		Util.setFrame(this);
		panel.add(canvas);
		panel.add(new JScrollPane(list));
		cp.add(panel, BorderLayout.CENTER);
		
		cp.add(createToolbar(), BorderLayout.PAGE_START);
		cp.add(bottom, BorderLayout.PAGE_END);
		
		saveAsDocument.setEnabled(true);
		saveDocument.setEnabled(true);
		
		model.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				saveDocument.setEnabled(true);
				saveAsDocument.setEnabled(true);
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				saveDocument.setEnabled(true);
				saveAsDocument.setEnabled(true);
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				saveDocument.setEnabled(true);
				saveAsDocument.setEnabled(true);
			}
		});
		
	}
	
	/**
	 * Function that enables/disables save buttons
	 */
	public void setSaveStatus() {
		
		if(model.isModified()) {
			saveDocument.setEnabled(true);
			saveAsDocument.setEnabled(true);
		}else {
			saveDocument.setEnabled(false);
			saveAsDocument.setEnabled(false);
		}
		
	}
	
	/**
	 * Creates menu, and its items
	 */
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		mb.add(file);
		
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(export));
		file.add(new JMenuItem(exit));
		file.add(new JMenuItem(open));
		
		setJMenuBar(mb);
	}

	/**
	 * Creates toolbar and its menus
	 * @return Component
	 */
	private Component createToolbar() {
		
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		
		JPanel p = new JPanel();
		
		p.add(fgColor);
		p.add(bgColor);
		
		tb.add(p);
		
		tb.add(saveDocument);
		tb.add(saveAsDocument);
		tb.add(export);
		tb.add(exit);
		tb.add(open);
		tb.add(line);
		tb.add(circle);
		tb.add(filledCircle);
		
		return tb;
		
	}

	/**
	 * Creates actions
	 */
	private void createActions() {
		saveDocument.putValue(Action.NAME, "Save");
		saveAsDocument.putValue(Action.NAME, "Save As");
		open.putValue(Action.NAME, "Open");
		export.putValue(Action.NAME, "Export");
		exit.putValue(Action.NAME, "Exit");
		line.putValue(Action.NAME, "Line");
		circle.putValue(Action.NAME, "Circle");
		filledCircle.putValue(Action.NAME, "FilledCircle");
	}

	/**
	 * Action that, when selected, changes current used
	 * tool into line one
	 */
	public Action line = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentState = new LineTool(model, fgColor);
			canvas.setTool(currentState);
		}
	};


	/**
	 * Action that, when selected, changes current used
	 * tool into circle one
	 */
	public Action circle = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentState = new CircleTool(model, fgColor);
			canvas.setTool(currentState);
		}
	};


	/**
	 * Action that, when selected, changes current used
	 * tool into filled circle one
	 */
	public Action filledCircle = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentState = new FilledCircleTool(model, fgColor, bgColor);
			canvas.setTool(currentState);
		}
	};

	/**
	 * Action that save progress/document
	 */
	public Action saveDocument = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(path == null || path.isBlank()) {
				path = Util.saveAs(JVDraw.this, model);
			}else {
				Util.save(JVDraw.this, path, model);
			}
		}
	};
		

	/**
	 * Action that save progress/document
	 */
	public Action saveAsDocument = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			path = Util.saveAs(JVDraw.this, model);
		}
	};
	

	/**
	 * Action that exports progress/document, as some image file
	 */
	public Action export = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Util.export(JVDraw.this, model);
		}
	};


	/**
	 * Action that exits from program
	 */
	public Action exit = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
		
			exitAction();
		}
	};

	/**
	 * Method that is called when user want's to exit program
	 */
	private void exitAction() {
		
		if(model.isModified()) {
			
			int option = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save file?", "Closing", JOptionPane.YES_NO_CANCEL_OPTION) ; 
			
			if(option == JOptionPane.YES_OPTION) {
				
				if(path == null || path.isBlank()) {
					path = Util.saveAs(JVDraw.this, model);
					
					if(path == null || path.isBlank()) {
						return;
					}
					
				}else {
					int option2 = JOptionPane.showConfirmDialog(JVDraw.this, "Same path?", "Closing", JOptionPane.YES_NO_OPTION);
					
					if(option2 == JOptionPane.NO_OPTION) {
						Util.saveAs(JVDraw.this, model);
						
					}else {
						boolean flag = Util.save(JVDraw.this, path, model);	
						if(!flag) {
							return;
						}	
					}
				}
				
			}else if(option == JOptionPane.CANCEL_OPTION) {
				return;
			}
			
		}else {
			int option = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to close program?", "Closing", JOptionPane.YES_NO_OPTION) ;	
			if(option == JOptionPane.NO_OPTION) {
				return;
			}
		}
		
		dispose();
	}
	
	/**
	 * Action that opens some new document
	 */
	public Action open = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Util.open(JVDraw.this, model);
			
		}
	};
	
	/**
	 * Main method called in the beginning of program 
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
		new JVDraw().setVisible(true);});
	}

	public void setPathToFile(Path filePath) {
		this.path = filePath.toString();
	}

}
