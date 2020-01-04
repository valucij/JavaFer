package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.text.BadLocationException;
/**
 * Class implements {@link MultipleDocumentModel} and extends {@link JTabbedPane}.
 * It tracks all documents that are open, and all changes that might happen
 * in them. When changes occur, listeners are notified, and appropriate actions
 * are made.
 * @author Lucija Valentić
 *
 */
public class DefaultMultipleDoumentModel extends JTabbedPane implements MultipleDocumentModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List of all open documents in program
	 */
	private List<SingleDocumentModel> collection;
	/**
	 * Represents current document that user sees
	 */
	private SingleDocumentModel current;
	/**
	 * List of listeners
	 */
	private List<MultipleDocumentListener> listeners;
	/**
	 * String that represents internal clipboard, not actual clipboard
	 */
	private String clipboard;
	
	/**
	 * Represents component which text is set to
	 * current time and date
	 */
	private JComponent currentClock;

	/**
	 * Red icon, when document is modified
	 */
	private static ImageIcon RED = null;
	/**
	 * Green icon, when document is not modified
	 */
	private static ImageIcon GREEN = null;
	

	/**
	 * Returns this.clock
	 * @return JComponent 
	 */
	public JComponent getClock(){
		return this.currentClock;
	}
	
	/**
	 * Constructor
	 */
	public DefaultMultipleDoumentModel() {
	
		
		InputStream red = this.getClass().getResourceAsStream("icons/reddisk.png");
		if(red == null) {
	
			return;
		}
		byte[] bytesRed;
		
		InputStream green = this.getClass().getResourceAsStream("icons/greendisk.png");
		if(green == null) {
	
			return;
		}
		byte[] bytesGreen;
		
		try {
			bytesRed = red.readAllBytes();
			red.close();
			
			Image image = new ImageIcon(bytesRed).getImage();
			Image newImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			
			RED = new ImageIcon(newImage);
			
			bytesGreen = green.readAllBytes();
			green.close();
			
			image = new ImageIcon(bytesGreen).getImage();
			newImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			
			GREEN = new ImageIcon(newImage);
		} catch (IOException e) {
			
			return;
		}
		
		
		collection = new ArrayList<SingleDocumentModel>();
		currentClock = new Clock();
		
		listeners = new ArrayList<MultipleDocumentListener>();
		createNewDocument();

		listeners.add(new MultipleDocumentListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				
				int index = collection.indexOf(model);
				
				if(current.equals(model) && collection.size() != 1) {
					current = collection.get(collection.size() - 1);
					
				}
				
				collection.remove(index);
				DefaultMultipleDoumentModel.this.removeTabAt(index);
				
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void documentAdded(SingleDocumentModel model) {
				
				collection.add(model);
				
				Path path = model.getFilePath();
				String text = "unamed";
				String name = "unamed";
				if(path != null) {

					text = path.toString();
					name = model.getTextComponent().getName();
				}
				
				
				DefaultMultipleDoumentModel.this.addTab(name, null, new JScrollPane(model.getTextComponent()),text);
				current = model;
				
				int index = DefaultMultipleDoumentModel.this.getNumberOfDocuments() - 1;
				JPanel tab = createCrossTabName(name, index);
				DefaultMultipleDoumentModel.this.setTabComponentAt(index, tab);
				
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				
				current = currentModel;
				
			}
		});
	}
	
	/**
	 * Creates new {@link JPanel}, that represents one tab.
	 * It consists of one {@link JLabel} - title of document, 
	 * and {@link JButton} - button cross, that when clicked, 
	 * it triggers closing of a document
	 * @param name String
	 * @param number int
	 * @return JPanel
	 */
	private JPanel createCrossTabName(String name, int number) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		JButton cross = new JButton("x");
		ActionListener l = new ButtonListener(number);
		cross.addActionListener(l);
		cross.setPreferredSize(new Dimension(0,5));;
		JLabel title = new JLabel(name);
		title.setOpaque(true);
	
		title.setIcon(RED);
	
		if(current.getFilePath() != null) {
			title.setIcon(GREEN);
		}
		
		panel.add(title);
		panel.add(cross);
		
		return panel;
		
		
	}
	/**
	 * Special {@link ActionListener}, made for buttons in 
	 * tabs. When pressed, it triggers closing of document
	 * where it has been pressed 
	 * @author Lucija Valentić
	 *
	 */
	public class ButtonListener implements ActionListener{

		/**
		 * Represents index of a tab
		 */
		private int index;
		/**
		 * Constructor
		 * @param index
		 */
		public ButtonListener(int index) {
			this.index = index;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Actions.closingOneDocument(index);
			
		}
		/**
		 * Sets new index of a tab. Usually called when 
		 * document has been remove, so all internal indexed
		 * of listeners has to be updated, so later the right
		 * tab can be closed
		 * @param index
		 */
		public void setIndex(int index) {
			this.index = index;
		}
		
	}

	/**
	 * Method is called when current document that user sees has been 
	 * changed, this method than notifies all listeners.
	 */
	public void change() {
		
		for(MultipleDocumentListener l : listeners) {
			
			if(DefaultMultipleDoumentModel.this.getSelectedIndex() == -1) {
				return;
			}
			
			l.currentDocumentChanged(getCurrentDocument(), getDocument(DefaultMultipleDoumentModel.this.getSelectedIndex()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new SingleDocumentIterator();
	}

	/**
	 * Class implements {@link Iterator}
	 * @author Lucija Valentić
	 *
	 */
	public class SingleDocumentIterator implements Iterator<SingleDocumentModel>{

		/**
		 * Represents index of current document, recently returned
		 */
		private int index;
		/**
		 * Constructor
		 */
		public SingleDocumentIterator() {
			this.index = -1;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return index + 1 < collection.size();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SingleDocumentModel next() {
			
			index++;
			return collection.get(index);
			
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(null, "");
		
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(newDoc);
		}
		
		current = newDoc;
		return newDoc;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
	
		String text = "";
		
		try {
			text = Files.readString(path);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this.getCurrentDocument().getTextComponent(), "Datoteku " + path + " nije moguće pročitati, došlo je do pogreške",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		for(SingleDocumentModel document : collection) {
			if(document.getFilePath() != null  && document.getFilePath().equals(path)) {
				
				for(MultipleDocumentListener l : listeners) {
					l.currentDocumentChanged(current, document);
				}
				current = document;
				
				return current;
			}
		}
		
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(path, text);
		
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(newDoc);
			
		}
		
		return newDoc;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
		model.setFilePath(newPath);
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		
		for(MultipleDocumentListener l : listeners){
			l.documentRemoved(model);
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return collection.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		
		return collection.get(index);
	}
	
	/**
	 * Returns text from internal clipboard
	 * @return String
	 */
	public String getClipboard() {
		return clipboard;
	}

	/**
	 * Puts given <code>string</code> into internal clipboard
	 * @param string String
	 */
	public void setClipboard(String string) {
		clipboard = string;
	}
	
	/**
	 * Sets tab name of current document. It is called any time that document has been changed, 
	 * and/or saved. Given <code>flagStar</code> is either empty string, or 
	 * string "*". That is determined whether document has been changed or not. This
	 * given string is put in the beginning of a tab name
	 * @param flagStar String
	 */
	public void setTabName(String flagStar) {
		
		String name = "unamed";
		String tooltip = "unamed";
		
		Path path = current.getFilePath();
		
		if(path != null) {
		
			name = path.getFileName().toString();
			tooltip = path.toString();
		}
		
		name = flagStar + name;
		
		if(this.getSelectedIndex() == -1) {
			return;
		}
		
		
		int index = this.getSelectedIndex();
		((JLabel)((JPanel)this.getTabComponentAt(index)).getComponent(0)).setText(name);
		
		ImageIcon icon = RED;
		
		if(!flagStar.equals("*")) {
			icon = GREEN;
		}
		
		((JLabel)((JPanel)this.getTabComponentAt(index)).getComponent(0)).setIcon(icon);
		
		
		this.setToolTipTextAt(this.getSelectedIndex(), tooltip);
		this.setTitleAt(this.getSelectedIndex(), name);
		this.getCurrentDocument().getTextComponent().setName(name);
		
	}
	/**
	 * Returns number of characters in current document
	 * @return String
	 */
	public String getCurrentNumberOfCharacters() {
		
		String text = current.getTextComponent().getText();
		return String.valueOf(text.length());
		
	}

	/**
	 * Returns number of non-blank characters in current document
	 * @return String
	 */
	public String getCurrentNumberNonBlankCharacters() {
		
		char[] chars = current.getTextComponent().getText().toCharArray();
		int sum = 0;
		
		for(int i = 0, n = chars.length; i < n; i++) {
			
			if(!Character.isWhitespace(chars[i])) {
				sum++;
			}
		}
		
		return String.valueOf(sum);
	}

	/**
	 * Returns current number of rows in current document
	 * @return String
	 */
	public String getNumberOfRows() {
		
		char[] chars = current.getTextComponent().getText().toCharArray();
		int sum = 0;
		
		for(int i = 0, n = chars.length; i < n; i++) {
			
			if( chars[i] == '\n') {
				sum++;
			}
		}
		
		return String.valueOf(sum + 1);
		
	}

	/**
	 * Internal class representing clock. This class is made, so
	 * it is easier to track current date and time. It extends JLabel,
	 * but this JLabel has been set been seen, and its text is always
	 * current date and time in special format - "yyyy/MM/dd  HH:mm:ss" 
	 * @author Lucija Valentić
	 *
	 */
	private static class Clock extends JLabel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * String representing current time
		 */
		private volatile String time;
		/**
		 * String representing current date
		 */
		private volatile String date;
		/**
		 * Formatter that formats time as described above
		 */
		private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
		/**
		 * Formatter that formats date as described above
		 */
		private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				
		/**
		 * Constructor
		 */
		public Clock() {
			setVisible(true);
			updateTime();
			
			Thread t = new Thread(()->{
				while(true) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		/**
		 * Method that updates time and date
		 */
		private void updateTime() {
			time = formatterTime.format(LocalTime.now());
			date = formatterDate.format(LocalDate.now());
			setText(date + "  " + time);
			repaint();
			
		}
		
	}
	
	/**
	 * Method updated status bar, if method is called
	 * first time, it adds new components in given {@link JPanel},
	 * but all other times when called, it repaints them with new informations.
	 * @param statusBar Jpanel
	 * @return Jpanel
	 */
	public JPanel updateStatusBar(JPanel statusBar) {
		
		if(statusBar == null) {
				return null;
		}
		
		
		JLabel first = new JLabel();
		JLabel second = new JLabel();
		JLabel third = (JLabel) currentClock;
		
		first.setOpaque(true);
		second.setOpaque(true);
		third.setOpaque(true);
		
		String forFirst = "Lenght:" + getCurrentNumberOfCharacters();
		String forSecond = "Ln:" + getCurrentLine() +" Col:" + getCurrentColumn() + " Sel:" + getSelectedCaret();
		String forThird = third.getText();
		
		
		first.setText(forFirst);
		second.setText(forSecond);
		
		first.setHorizontalAlignment(SwingConstants.LEFT);
		second.setHorizontalAlignment(SwingConstants.LEFT);
		third.setHorizontalAlignment(SwingConstants.RIGHT);
		
		if(statusBar.getComponentCount() == 0) {

			statusBar.add(first);
			statusBar.add(second);
			statusBar.add(third);
				
		}else {
			((JLabel)statusBar.getComponent(0)).setText(forFirst);
			((JLabel)statusBar.getComponent(1)).setText(forSecond);
			((JLabel)statusBar.getComponent(2)).setText(forThird);
		}
		
		return statusBar;
		
	}

	/**
	 * Method returns length of selected text with caret.
	 * If nothing is selected, zero is returned
	 * @return String
	 */
	private String getSelectedCaret() {
		
		int len = Math.abs(current.getTextComponent().getCaret().getDot() - current.getTextComponent().getCaret().getMark() );
		
		if(len < 1) {
			return "0";
		}
		
		return String.valueOf(len);
	}

	/**
	 * Method returns current column, more specifically
	 * column in which caret is found
	 * @return String
	 */
	private String getCurrentColumn() {
		
		int line;
		int end;
		int col = 1;
		try {
			line = current.getTextComponent().getLineOfOffset(current.getTextComponent().getCaret().getDot());
			end = current.getTextComponent().getLineStartOffset(line);
			
		} catch (BadLocationException e) {
			return "";
		}
		
		col += current.getTextComponent().getCaret().getDot() - end;
		
		return String.valueOf(col);
		
	}

	/**
	 * Method returns current line in current document,
	 * more specifically line in which caret is found
	 * @return String
	 */
	private String getCurrentLine() {
		
		int line;
		
		try {
			line = current.getTextComponent().getLineOfOffset(current.getTextComponent().getCaret().getDot());	
		} catch (BadLocationException e) {
			return "";
		}
		
		
		return String.valueOf((line + 1));
		
	}
	
	/**
	 * Returns string that represents name in tab of 
	 * a main program
	 * @return String
	 */
	public String setTitle() {
		
		String string = "unamed";
	
		Path path = current.getFilePath();
		
		if(path != null) {
			string = path.toString();
		}
		
		return string + " - JNotepad++";
	}
	
}




