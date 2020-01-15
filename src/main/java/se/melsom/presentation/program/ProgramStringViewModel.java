package se.melsom.presentation.program;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import se.melsom.model.program.ProgramString;
import se.melsom.presentation.ViewModel;

public class ProgramStringViewModel extends ViewModel {
	private static Logger logger = Logger.getLogger(ProgramStringViewModel.class);
	
	private static final String STRING_TITLE = "StringTitle";
	private static final String SHOT_COUNT = "ShotCount";
	
	private ProgramString string;
	private ProgramStageViewModel programStageViewModel;
	private ProgramStringContextMenu stringContextMenu;
	private ProgramStringView view;
	
	public ProgramStringViewModel(ProgramString string, ProgramStageViewModel programStageViewModel) {
		this.string = string;
		this.programStageViewModel = programStageViewModel;
		
		stringContextMenu = new ProgramStringContextMenu(this);
		view = new ProgramStringView(this, string, stringContextMenu);
		
		programStageViewModel.addPanel(view);
	}

	@Override
	public JComponent getView() {
		return view;
	}

	public void removeString() {
		programStageViewModel.removeString(string);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		logger.debug("Action command '" + action + "'");
		
		if (event.getSource() instanceof JTextField) {
			JTextField source = (JTextField) event.getSource();
			String newText = source.getText();
			
			if (!handleTextFieldEvent(source, newText)) {
				logger.warn("Unhandled text field action event " + event);
			}
		} 
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField == false) {
			return;
		}

		JTextField source = (JTextField) e.getSource();
		String newText = source.getText();
		
		if (!handleTextFieldEvent(source, newText)) {
			logger.warn("Unhandled text field focus lost event " + e);
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		if (event.getSource() instanceof JTextField == false) {
			return;
		}

		if (event.getKeyChar() == KeyEvent.CHAR_UNDEFINED) {
			return;
		}
		
		char ch = event.getKeyChar();
		
		JTextField source = (JTextField) event.getSource();
		
		switch (source.getName()) {
		case SHOT_COUNT:
			if (!isValidMinutes(source.getText(), ch)) {
				event.consume();
			}
			break;
			
		default:
			break;
		}		
	}

	boolean handleTextFieldEvent(Component component, String newText) {
		switch (component.getName()) {
		case STRING_TITLE:
			string.setTitle(newText);
			break;
			
		case SHOT_COUNT:
			string.setShotCount(Integer.parseInt(newText));
			break;
			
		default:
			return false;
		}
		
		return true;
	}
}
