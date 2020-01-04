package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDoumentModel.ButtonListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.*;

/**
 * Class holds all actions that can be performed in {@link JNotepadPP}.
 * @author Lucija Valentić
 *
 */
public class Actions {
	/**
	 * Reference to object, instance of class of actual program 
	 */
	private static JNotepadPP notepad;
	/**
	 * Reference to a model that holds all single documents
	 */
	private static DefaultMultipleDoumentModel model;
	/**
	 * Represents translator that will translate names of actions
	 */
	private static FormLocalizationProvider translator;
	/**
	 * First option in {@link JOptionPane} dialogs
	 */
	private static final int FIRST_OPTION = 0;
	/**
	 * Second option in {@link JOptionPane} dialogs
	 */
	private static final int SECOND_OPTION = 1;
	/**
	 * Third option in {@link JOptionPane} dialogs
	 */
	private static final int THIRD_OPTION = 2;
	/**
	 * Fourth option in {@link JOptionPane} dialogs
	 */
	private static final int FOURTH_OPTION = 3;
	/**
	 * Represents status bar of document
	 */
	private JPanel panel;
	
	/**
	 * Constructor
	 * @param notepad JNotepad
	 * @param model DefaultMultipleDocumentModel
	 * @param translator FormLocalizationProvider
	 * @param panel JPanel
	 */
	public Actions(JNotepadPP notepad, DefaultMultipleDoumentModel model, FormLocalizationProvider translator, JPanel panel) {
		Actions.notepad = notepad;
		Actions.model = model;
		Actions.translator = translator;
		this.panel = panel;
		
	}
	
	/**
	 * Sets some variables to actions
	 */
	public  void creating() {
		
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt o"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Open document from disc");
		
		
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt s"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save document to disc");
		
		
		createDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt r"));
		createDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		createDocument.putValue(Action.SHORT_DESCRIPTION, "Creates new documents");
		
		
		exitApp.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitApp.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitApp.putValue(Action.SHORT_DESCRIPTION, "Exits the app");
		
		
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt f"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save document to disc");
		
		
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control c"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		copy.putValue(Action.SHORT_DESCRIPTION, "Copy part of text to clipboard");
		
		
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control x"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		cut.putValue(Action.SHORT_DESCRIPTION, "Cuts part of text to clipboard");
		
		
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control v"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		paste.putValue(Action.SHORT_DESCRIPTION, "Pastes part of text from clipboard to text area");
		
		statistics.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt t"));
		statistics.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		statistics.putValue(Action.SHORT_DESCRIPTION, "Returns number of characters, rows, and non blank characters");

		closeCurrentTab.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt b"));
		closeCurrentTab.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		closeCurrentTab.putValue(Action.SHORT_DESCRIPTION, "Closes current tab");

		
		updateNames();
	}

	/**
	 * Method handles exiting the program, and handles
	 * closing all tabs, asking user to choose whether he wants to 
	 * save them or not
	 */
	public final void exiting() {
		
		int size = model.getNumberOfDocuments();
		
		for(int i = 0; i < size; i++) {
			
			//pitati za svaki prozor da li se treba spremiti ili ne
			
			if(!closeOne(0)) {
				break;
			}
			
		}
		
		if(model.getNumberOfDocuments() == 0) {
			notepad.dispose();
		}
	}
	
	/**
	 * Method that closes one document. This method handles saving and 
	 * closing one document with appropriate message dialogs. Document that 
	 * is closed is in the position of a given <code>index</code>. This
	 * method return <code>true</code> if document is saved, and <code>false</code>
	 * otherwise
	 * @param index int
	 * @return boolean
	 */
	private static boolean closeOne(int index) {
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Closing");
		
		String name = model.getDocument(index).getTextComponent().getName();
		
		if(!model.getDocument(index).isModified() && model.getDocument(index).getFilePath() != null) {
			model.closeDocument(model.getDocument(index));
			return true;
		}
		
		
		String op1 = translator.getString("saveAs");
		String op2 = translator.getString("save");
		String op3 = translator.getString("notSave");
		String op4 = translator.getString("cancel");
		
		String[] options = {op1, op2, op3, op4};
		String poruka1 = translator.getString("poruka1");
		String title = translator.getString("closing");
		
		int choice = JOptionPane.showOptionDialog(notepad, poruka1 + " \"" + name +"\" ?", title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		
		if(choice == FIRST_OPTION) {//saveAs je izabran, sprema se i zatvara se
			
			saveAs();
				
			model.closeDocument(model.getDocument(index));
			
		}else if(choice == SECOND_OPTION) {//save izabran
			
			Path path = model.getDocument(index).getFilePath();
			
			if(path != null) {
				model.saveDocument(model.getDocument(index), path);	
			}else {
				if(!saveAs()) {
					//pokazati samo poruku da je došlo do greške
				}
			}
			
			model.closeDocument(model.getDocument(index));
			return true;
		}else if(choice == THIRD_OPTION) {
			
			String poruka3 = translator.getString("poruka3");
			String title3 = translator.getString("obavijest");
			
			JOptionPane.showMessageDialog(notepad, poruka3,
					title3, JOptionPane.INFORMATION_MESSAGE);
			
			model.closeDocument(model.getDocument(index));
			return true;
		}else if(choice == FOURTH_OPTION) {
			
			String poruka4 = translator.getString("poruka4");
			String title4 = translator.getString("obavijest");
			
			JOptionPane.showMessageDialog(notepad, poruka4,
					title4, JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
		
	} 
	
	/**
	 * Action "exitApp" represents action that, when performed,
	 * exits from program
	 */
	@SuppressWarnings("serial")
	public  final Action exitApp = new LocalizableAction("exit", translator) {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			exiting();
			
		}
	};
	
	/**
	 * Method creates new document and adds appropriate listeners
	 * to it
	 */
	public final void create() {
		
		model.createNewDocument();

		setsDocumentListener();
		setsCaretListener();

		model.change();
		
	}
	
	/**
	 * Method sets {@link ChangeListener} on caret of current document. This method is
	 * called only once on each document, in the moment of creating the or
	 * opening
	 */	
	private final void setsCaretListener() {
		
		model.getCurrentDocument().getTextComponent().getCaret().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
		
				setsEnabledActions();
				
				notepad.setStatusBar(model.updateStatusBar(panel));
				
			}
		});
	}
	
	public void setsEnabledActions() {
		
		boolean selection = model.getCurrentDocument().getTextComponent().getCaret().getDot() != model.getCurrentDocument().getTextComponent().getCaret().getMark();
		copy.setEnabled(selection);
		cut.setEnabled(selection);
		changeCase.setEnabled(selection);
		sort.setEnabled(selection);
		unique.setEnabled(selection);
		tools.setEnabled(selection);
		
		Path path = model.getCurrentDocument().getFilePath();
		boolean modification = model.getCurrentDocument().isModified();
		
		saveDocument.setEnabled(path == null || modification == true);
		
	}
	
	/**
	 * Method sets {@link DocumentListener} on current document. This method is
	 * called only once on each document, in the moment of creating the or
	 * opening
	 */
	private final void setsDocumentListener() {
		
		
		model.getCurrentDocument().getTextComponent().getDocument().addDocumentListener(new DocumentListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void removeUpdate(DocumentEvent e) {
				model.getCurrentDocument().setModified(true);
				saveDocument.setEnabled(true);
				
				model.setTabName("*");
				notepad.setStatusBar(model.updateStatusBar(panel));
				
			}
			/**
			 * {@inheritDoc}
			 */			
			@Override
			public void insertUpdate(DocumentEvent e) {
				model.getCurrentDocument().setModified(true);
				saveDocument.setEnabled(true);
				
				model.setTabName("*");
				notepad.setStatusBar(model.updateStatusBar(panel));
				
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void changedUpdate(DocumentEvent e) {
				model.getCurrentDocument().setModified(true);
				saveDocument.setEnabled(true);
				
				model.setTabName("*");
				notepad.setStatusBar(model.updateStatusBar(panel));
				
			}
		});

	}
	
	/**
	 * Action "createDocument" represents action that, when performed,
	 * creates new document, file path is set to <code>null</code>
	 */
	@SuppressWarnings("serial")
	public  final Action createDocument = new LocalizableAction("create", translator) {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			create();
		}
	};
	
	/**
	 * Action "saveAsDocument" represents action that, when performed,
	 * saves documents into new path, not the one that document already has
	 */
	@SuppressWarnings("serial")
	public  final Action saveAsDocument = new LocalizableAction("saveAs", translator) {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = saveAs();
			if(flag) {
				saveDocument.setEnabled(false);	
				notepad.setTitleForProgram(model.setTitle());
				model.setTabName("");
				
				String poruka = translator.getString("spremljeno");
				String title = translator.getString("obavijest");
				
				JOptionPane.showMessageDialog(notepad, poruka,
						title, JOptionPane.INFORMATION_MESSAGE);
				
			}
		}

	};

	/**
	 * Method that helps action {@link #saveAsDocument}, when 
	 * called, it triggers {@link JFileChooser} for saving documents.
	 * User can chose to save into some file that already exists. If that happens, 
	 * user is warned. Otherwise, document is saved
	 * @return
	 */
	private  final static boolean saveAs() {
		
		String op1 = translator.getString("da");
		String op2 = translator.getString("ne");
		
		String[] options = {op1, op2};
		String title = translator.getString("obavijest");
		String poruka = translator.getString("poruka6");
		
		String poruka5 = translator.getString("poruka5");
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if(jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
		
			JOptionPane.showMessageDialog(notepad, poruka5,
					title, JOptionPane.INFORMATION_MESSAGE);
			
			return false;
		}
			
		Path path = jfc.getSelectedFile().toPath();
		
		if(path.toFile().exists() && JOptionPane.showOptionDialog(notepad, poruka, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null) != FIRST_OPTION) {
			
			JOptionPane.showMessageDialog(notepad, poruka5,
					title, JOptionPane.INFORMATION_MESSAGE);
			
			return false;
		}
		
		model.saveDocument(model.getCurrentDocument(), path);
		return true;
		
	}
	/**
	 * Action "save" represents action that, when performed,
	 * saves document. If document is not already saved, then action 
	 * equal to action save As is performed. Otherwise, document is saved in
	 * "his normal" path
	 */
	@SuppressWarnings("serial")
	public  final Action saveDocument = new LocalizableAction("save", translator) {
		

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Path path = model.getCurrentDocument().getFilePath();
			boolean flag = false;
			if(path != null) {
				model.saveDocument(model.getCurrentDocument(), path);	
				this.setEnabled(false);
				notepad.setTitleForProgram(model.setTitle());
				model.setTabName("");
				
				String poruka = translator.getString("spremljeno");
				String title = translator.getString("obavijest");
				
				JOptionPane.showMessageDialog(notepad, poruka,
						title, JOptionPane.INFORMATION_MESSAGE);
				
			}else {
				flag = saveAs();
			}
			
			if(flag) {
				this.setEnabled(false);	
				notepad.setTitleForProgram(model.setTitle());
				model.setTabName("");
				String poruka = translator.getString("spremljeno");
				String title = translator.getString("obavijest");
				
				JOptionPane.showMessageDialog(notepad, poruka,
						title, JOptionPane.INFORMATION_MESSAGE);
				
			}
			
		}
	};
	/**
	 * Action "copy" represents action that, when performed,
	 * copies selected text from document into internal clipboarf.
	 * This action is disabled if no text is selected
	 */
	@SuppressWarnings("serial")
	public  final Action copy = new LocalizableAction("copy", translator) {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			copyToClipboardMaybeCutIfOne(0);
			
		}
	};
	
	/**
	 * Method help actions {@link #copy} and {@link #cut}. This method
	 * accepts one flag. For copy and cut actions, selected text is 
	 * copied from current document into internal clipboard. If the given
	 * flag is 1, then action cut is performed, and text is also removed from
	 * document
	 * @param oneToCut int
	 */
	private  final void copyToClipboardMaybeCutIfOne(int oneToCut) {
		
		int start = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getMark() , model.getCurrentDocument().getTextComponent().getCaret().getDot());
		int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot() - model.getCurrentDocument().getTextComponent().getCaret().getMark() );
		
		if(len < 1) {
			return;
		}
		
		String text = "";
		
		try {
			 text = model.getCurrentDocument().getTextComponent().getDocument().getText(start, len);
			 model.setClipboard(text);
			 
		} catch (BadLocationException e1) {
			return;
		}
		
		if(oneToCut == 1) {
		
			String document = model.getCurrentDocument().getTextComponent().getText();
			
			String firstHalf  = "";
			String secondHalf = "";
			
			if(start != 0) {
				firstHalf = document.substring(0, start);	
			}
				
			if(len + start < document.length()) {
				secondHalf = document.substring(len + start);	
			}
			
			document = firstHalf + secondHalf;
			
			model.getCurrentDocument().getTextComponent().setText(document);
			
		}
		
		return;		
	}

	/**
	 * Action "cut" represents action that, when performed,
	 * cuts selected text from document. This action is disabled if no text
	 * is selected
	 */
	@SuppressWarnings("serial")
	public  final Action cut = new AbstractAction() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			copyToClipboardMaybeCutIfOne(1);
			
		}
	};
	

	/**
	 * Action "paste" represents action that, when performed,
	 * inserts text from clipboard, but internal one, not actual one
	 * into current place of caret
	 */
	@SuppressWarnings("serial")
	public  final Action paste = new LocalizableAction("paste", translator) {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			performPaste();
		}
	};

	/**
	 * Method that inserts text from internal clipboard (not actual clipboard) to
	 * document in current place of caret
	 */
	private void performPaste () {
		
		String clip = model.getClipboard();
		
		if(clip == null || clip.isEmpty()) {
			return;
		}
		
		int start = model.getCurrentDocument().getTextComponent().getCaret().getDot();
		
		String document = model.getCurrentDocument().getTextComponent().getText();
		
		String firstHalf  = "";
		String secondHalf = "";
		
		if(start != 0) {
			firstHalf = document.substring(0, start);	
		}
		
		secondHalf = document.substring(start);	
		
		document = firstHalf + clip + secondHalf;
		
		model.getCurrentDocument().getTextComponent().setText(document);
		
	}

	/**
	 * Action "statistics" represents action that, when performed,
	 * gives statistic of current document - number of characters, number
	 * of non blank characters and number of rows
	 */
	@SuppressWarnings("serial")
	public final Action statistics = new LocalizableAction("statistics", translator) {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
		
			String p7 = translator.getString("poruka7");
			String p8 = translator.getString("poruka8");
			String p9 = translator.getString("poruka9");
			String title = translator.getString("obavijest");
			
			JOptionPane.showMessageDialog(notepad, p7 + " " + model.getCurrentNumberOfCharacters() + " "
					+ "\n" + p8 + " " + model.getCurrentNumberNonBlankCharacters() + " \n"
							+ p9 + " "+ model.getNumberOfRows(),
					title, JOptionPane.INFORMATION_MESSAGE);
			
			
		}
	};
	

	/**
	 * Action "openDocument" represents action that, when performed,
	 * opens new document from some path
	 */
	@SuppressWarnings("serial")
	public final  Action openDocument = new LocalizableAction("open", translator) {
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			
			if(jfc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path filePath = jfc.getSelectedFile().toPath();
			
			model.loadDocument(filePath);
			
			setsDocumentListener();
			setsCaretListener();
			
			model.change();
			
		}

	};
	
	/**
	 * Method closes just one document, that is on position of a given 
	 * <code>index</code>, and updated index of tabs buttons
	 * @param index int
	 */
	public static final void closingOneDocument(int index) {
		
		//pitanje za zatvaranje
		closeOne(index);
		
		//updatiranje indeksa gumba
		
		for(int i = 0, n = model.getNumberOfDocuments(); i < n; i++) {
			
			ButtonListener listener = (ButtonListener) ((JButton)((JPanel)model.getTabComponentAt(i)).getComponent(1)).getActionListeners()[0];
			listener.setIndex(i);	
		}
		
		if(model.getNumberOfDocuments() == 0) {
			notepad.dispose();
		}
		
	}

	/**
	 * Action "closeCurrentTab" represents action that, when performed,
	 * closes current tab that user sees
	 */
	public final Action closeCurrentTab = new LocalizableAction("closeCurrentTab", translator) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			closingOneDocument(model.getSelectedIndex());
		}
	}; 
	
	
	/**
	 * Action "langHr" represents action that, when performed,
	 * changes current language to croatian
	 */
	public final Action langHr = new LocalizableAction("hr", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
			updateNames();
		}
	};

	/**
	 * Action "langFr" represents action that, when performed,
	 * changes current language to french
	 */
	public final Action langFr = new LocalizableAction("fr", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("fr");
			updateNames();
		}
	};
	/**
	 * Action "langEn" represents action that, when performed,
	 * changes current language to english
	 */
	public final Action langEn = new LocalizableAction("en", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
			updateNames();
			
		}
	};

	/**
	 * Action "upperCaseAction" represents action that, when performed,
	 * changes case of selected text so lower letters are upper
	 */
	public final Action upperCaseAction = new LocalizableAction("upper", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			toogle("upper");
		}
	};

	/**
	 * Action "lowerCaseAction" represents action that, when performed,
	 * changes case of selected text so upper letters are lower
	 */
	public final Action lowerCaseAction = new LocalizableAction("lower", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			toogle("lower");
		}

	};

	/**
	 * Action "inverseCaseAction" represents action that, when performed,
	 * changes case of selected text so lower letters are upper, and upper letters
	 * are lower
	 */
	public final Action inverseCaseAction = new LocalizableAction("inverse", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			toogle("inverse");
		}

	};

	/**
	 * Method changes case of selected part of document
	 * based on given string, and appropriate methods are called. Method 
	 * that can be called are {@link #lowerCase(String)}, {@link #upperCase(String)}, 
	 * {@link #inverseCase(String)} 
	 * @param string
	 */
	private void toogle(String string) {

		int start = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getMark() , model.getCurrentDocument().getTextComponent().getCaret().getDot());
		int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot() - model.getCurrentDocument().getTextComponent().getCaret().getMark() );
		
		if(len < 1) {
			return;
		}
		
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		
		try {
		
		String text = doc.getText(start, len);
		
		switch(string) {
		case "upper":
			text = upperCase(text);
			break;	
		case "lower":
			text = lowerCase(text);
			break;
		case "inverse":
			text = inverseCase(text);
			break;
		}
		
		doc.remove(start, len);
		doc.insertString(start, text, null);
		}catch(BadLocationException ignorable){}
		
	}

	/**
	 * Method accepts one string <code>string</code> that has
	 * word "ascending", "descending", or "unique". Based on that word
	 * this method chooses a comparator. For "unique", comparator is not
	 * needed. This method extracts from current document selected lines, and
	 * then is sorts them based on two given word. If second word is "list", then
	 * all lines are sorted, no duplicates are removed; otherwise, 
	 * lines are not sorted, but just duplicates are removed.
	 * @param string String
	 * @param whatCollection String
	 */
	private void sortText(String string, String whatCollection) {

		Locale local = Locale.forLanguageTag(translator.getCurrentLanguage());
		Collator collator = Collator.getInstance(local);
		Comparator<String> comp = null;
		
		if(string.equals("ascending")) {
			comp = (z1, z2) -> collator.compare(z1, z2);	
		}else if(string.equals("descending")){
			comp = (z1, z2) -> -collator.compare(z1, z2);
		}
		
		
		Collection<String> collection = new LinkedList<String>();;
		
		int firstOffset;
		int secondOffset;
		
		try {
			firstOffset = model.getCurrentDocument().getTextComponent().getLineOfOffset(model.getCurrentDocument().getTextComponent().getCaret().getDot());
			secondOffset = model.getCurrentDocument().getTextComponent().getLineOfOffset(model.getCurrentDocument().getTextComponent().getCaret().getMark());
		} catch (BadLocationException e) {
			return;
		}
		
		if(secondOffset < firstOffset) {
			int temp = firstOffset;
			firstOffset = secondOffset;
			secondOffset = temp;
		}
		
		int firstLine;
		int lastLine;
		
		try {
			firstLine = model.getCurrentDocument().getTextComponent().getLineStartOffset(firstOffset);
			lastLine = model.getCurrentDocument().getTextComponent().getLineEndOffset(secondOffset);
		} catch (BadLocationException e) {
			return;
		}
		
		int len = lastLine - firstLine;
		
		if(len < 1) {
			return;
		}
		
		Document doc = model.getCurrentDocument().getTextComponent().getDocument();
		String text = null;
		
		try {
			text = doc.getText(firstLine, len);
		} catch (BadLocationException e) {
			return;
		}
		
		String[] lines = text.split("\n");
		
		for(String line : lines) {
			collection.add(line);
		}
		
		List<String> newList;
		
		if(whatCollection.equals("list")) {
			((List<String>) collection).sort(comp);	
			newList = (LinkedList<String>) collection;
		}else {
			newList = collection.stream().distinct().collect(Collectors.toList());
		}
		 
		
		StringBuilder sb = new StringBuilder();
		
		for(String line : newList) {
			sb.append(line + "\n");
		}
		
		try {
			doc.remove(firstLine, len);
			doc.insertString(firstLine, sb.toString(), null);
		} catch (BadLocationException e) {
			return;
		}
		
		
	}

	/**
	 * Method converts given <code>text</code> into
	 * string, but in special way. All capital letters become
	 * lower ones, and lower ones become capital
	 * @param text String
	 * @return string
	 */
	private String inverseCase(String text) {
		
		char[] chars = text.toCharArray();
		
		for(int i = 0, n = chars.length; i < n; i++) {
			
			if(Character.isUpperCase(chars[i])) {
				chars[i] = Character.toLowerCase(chars[i]);
			}else if(Character.isLowerCase(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
			}
		}
		
		return new String(chars);
	}

	/**
	 * Method converts given <code>text</code> into
	 * string with all lower letters
	 * @param text String
	 * @return string
	 */
	private String lowerCase(String text) {
		
		char[] chars = text.toCharArray();
		
		for(int i = 0, n = chars.length; i < n; i++) {
			
			if(Character.isUpperCase(chars[i])) {
				chars[i] = Character.toLowerCase(chars[i]);
			}
		}
		
		return new String(chars);
	}

	/**
	 * Method converts given <code>text</code> into
	 * string with all capital letters
	 * @param text String
	 * @return string
	 */
	private String upperCase(String text) {
		
		char[] chars = text.toCharArray();
		
		for(int i = 0, n = chars.length; i < n; i++) {
			
			if(Character.isLowerCase(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
			}
		}
		
		return new String(chars);
	}


	/**
	 * "changeCase" is an action, but it is instance of {@link LocalizableString},
	 * so it behaves like a dynamic string that can change its text based on
	 * current language. This 'string' is just string that represents
	 * word "change case" in different languages
	 */
	public final Action changeCase = new LocalizableString("changeCase", translator);
	

	/**
	 * "sort" is an action, but it is instance of {@link LocalizableString},
	 * so it behaves like a dynamic string that can change its text based on
	 * current language. This 'string' is just string that represents
	 * word "sort" in different languages
	 */
	public final Action sort = new LocalizableString("sort", translator);
	
	/**
	 * "tools" is an action, but it is instance of {@link LocalizableString},
	 * so it behaves like a dynamic string that can change its text based on
	 * current language. This 'string' is just string that represents
	 * word "tool" in different languages
	 */
	public final Action tools = new LocalizableString("tools", translator);
	

	/**
	 * Action "ascending" represents action that, when performed, sorts all
	 * selected lines in ascending order. No duplicates are removed
	 */
	public final Action ascending = new LocalizableAction("ascending", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			sortText("ascending", "list");
			
			
		}
	};
	
	/**
	 * Action "descending" represents action that, when performed, sorts all
	 * selected lines in descending order. No duplicates are removed
	 */
	public final Action descending = new LocalizableAction("descending", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			sortText("descending", "list");
			
			
		}
	};
	
	/**
	 * Action "unique" represents action that, when performed, 
	 * removes all selected lines from document that are duplicates.
	 * Only first occurrence of the line stays.
	 */
	public final Action unique = new LocalizableAction("unique", translator) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			sortText("unique", "set");
		}
		
	};
	
	/**
	 * Method updates name of labels, menus, menu items in 
	 * current language
	 */
	private void updateNames() {
		
		openDocument.putValue(Action.NAME, translator.getString("open"));
		saveAsDocument.putValue(Action.NAME, translator.getString("saveAs"));
		saveDocument.putValue(Action.NAME, translator.getString("save"));
		exitApp.putValue(Action.NAME, translator.getString("exit"));
		langHr.putValue(Action.NAME, translator.getString("hr"));
		langFr.putValue(Action.NAME, translator.getString("fr"));
		langEn.putValue(Action.NAME, translator.getString("en"));
		copy.putValue(Action.NAME, translator.getString("copy"));
		paste.putValue(Action.NAME, translator.getString("paste"));
		cut.putValue(Action.NAME, translator.getString("cut"));
		statistics.putValue(Action.NAME, translator.getString("statistics"));
		createDocument.putValue(Action.NAME, translator.getString("create"));
		lowerCaseAction.putValue(Action.NAME, translator.getString("lower"));
		upperCaseAction.putValue(Action.NAME, translator.getString("upper"));
		inverseCaseAction.putValue(Action.NAME, translator.getString("inverse"));
		changeCase.putValue(Action.NAME, translator.getString("changeCase"));
		ascending.putValue(Action.NAME, translator.getString("ascending"));
		descending.putValue(Action.NAME, translator.getString("descending"));
		sort.putValue(Action.NAME, translator.getString("sort"));
		unique.putValue(Action.NAME, translator.getString("unique"));
		tools.putValue(Action.NAME, translator.getString("tools"));
		closeCurrentTab.putValue(Action.NAME, translator.getString("closeCurrentTab"));
		
		
	}

}
