package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableString;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Class represent program that is similar to what we know
 * as notepad. This program, however, has couple of functions
 * that regular notepad doesn't have. With this program, user can
 * edit documents, save documents, load documents, create new documents, 
 * copy, cut, paste, change cases, sort lines, and also user 
 * can change language of program. There are two currently available
 * languages - croatian and english.
 * @author Lucija Valentić
 *
 */
public class JNotepadPP extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Represents one model of a {@link DefaultMultipleDoumentModel}, 
	 * with that model we track all the changes that happen in
	 * program while working with documents
	 */
	private DefaultMultipleDoumentModel model;
	/**
	 * Reference to object, instance of {@link Actions}.
	 * This object helps in performing some actions. 
	 * Usually, those actions are put in {@link JButton}, so
	 * when clicked, they can perform actions which has
	 * been implemented
	 */
	private Actions actions;
	/**
	 * Reference to object that is used for localization
	 */
	private FormLocalizationProvider translator;
	/**
	 * Reference to component that represent status bar
	 */
	private JPanel statusBar;
	
	/**
	 * Method updates status bar
	 * @param currentBar
	 */
	public void setStatusBar(JPanel currentBar) {
		statusBar = currentBar;
	}
	/**
	 * Updates title for program
	 * @param string String
	 */
	public void setTitleForProgram(String string) {
		setTitle(string);
	}
	
	/**
	 * Constructor
	 */
	public JNotepadPP() {
		
		translator = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(100,100);
		setSize(600,500);
		setTitle("unamed - JNotepad++");
		
		model = new DefaultMultipleDoumentModel();
		statusBar = new JPanel();
		
		actions = new Actions(this, model, translator, statusBar);
		this.addWindowListener(new WindowListener() {
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				actions.create();
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowDeactivated(WindowEvent e) {	
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				actions.exiting();
				
			}
			
			/**
			 * {@inheritDoc}
			 */		
			@Override
			public void windowClosed(WindowEvent e) {
				JNotepadPP.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
		
		
		initGUI();
		
	}
	
	/**
	 * Initializes the whole gui, and how program
	 * should look like, and adds all components into
	 * some container
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		
		cp.add(model, BorderLayout.CENTER);
		

		model.addChangeListener(new ChangeListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void stateChanged(ChangeEvent e) {
					
				model.change();
				actions.setsEnabledActions();
				model.updateStatusBar(statusBar);
				setTitle(model.setTitle());
				
			}
		});
		
		createAction();
		createMenus();
		cp.add(createToolbar(), BorderLayout.PAGE_START);
		
		actions.saveDocument.setEnabled(false);
		model.setTabName("");
		actions.cut.setEnabled(false);
		actions.copy.setEnabled(false);
		actions.changeCase.setEnabled(false);
		actions.sort.setEnabled(false);
		actions.unique.setEnabled(false);
		actions.tools.setEnabled(false);
		
		statusBar.setLayout(new GridLayout(1,3));
		statusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		model.updateStatusBar(statusBar);
		
		cp.add(statusBar, BorderLayout.PAGE_END);
		
		
	}
	/**
	 * Method creates actions
	 */
	private void createAction() {
		
		actions.creating();
				
	}
	/**
	 * Method creates menus with sub menus and menu
	 * items for program
	 */
	private void createMenus() {
		
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu(new LocalizableString("file", translator));
		mb.add(file);
		
		file.add(new JMenuItem(actions.openDocument));
		file.add(new JMenuItem(actions.saveAsDocument));
		file.addSeparator();
		file.add(new JMenuItem(actions.exitApp));
		
		JMenu edit = new JMenu(new LocalizableString("edit", translator));
		mb.add(edit);
		edit.add(new JMenuItem(actions.copy));
		edit.add(new JMenuItem(actions.cut));
		edit.add(new JMenuItem(actions.paste));
		
		JMenu info = new JMenu(new LocalizableString("info", translator));
		mb.add(info);
		info.add(new JMenuItem(actions.statistics));
		
		JMenu jezik = new JMenu(new LocalizableString("jezici", translator));
		mb.add(jezik);
		jezik.add(new JMenuItem(actions.langHr));
		jezik.add(new JMenuItem(actions.langEn));
		jezik.add(new JMenuItem(actions.langFr));
		
		JMenu tools = new JMenu(actions.tools);
		mb.add(tools);
		
		JMenu cases = new JMenu(actions.changeCase);
		tools.add(cases);
		cases.add(new JMenuItem(actions.upperCaseAction));
		cases.add(new JMenuItem(actions.lowerCaseAction));
		cases.add(new JMenuItem(actions.inverseCaseAction));
		
		JMenu sort = new JMenu(actions.sort);
		tools.add(sort);
		sort.add(actions.ascending);
		sort.add(actions.descending);
		
		mb.add(new JMenuItem(actions.unique));
		
		setJMenuBar(mb);
		
	}
	/**
	 * Method makes new toolbar, with some buttons
	 * for program 
	 * @return JToolBar
	 */
	private JToolBar createToolbar() {
		
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		
		tb.add(new JButton(actions.openDocument));
		tb.add(new JButton(actions.saveAsDocument));
		tb.add(new JButton(actions.createDocument));
		tb.add(new JButton(actions.saveDocument));
		tb.add(new JButton(actions.copy));
		tb.add(new JButton(actions.cut));
		tb.add(new JButton(actions.paste));
		tb.add(new JButton(actions.closeCurrentTab));
		
		return tb; 
	}
				
	/**
	 * Main method called in the beginning of a program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new JNotepadPP().setVisible(true);
			});

	}

}
