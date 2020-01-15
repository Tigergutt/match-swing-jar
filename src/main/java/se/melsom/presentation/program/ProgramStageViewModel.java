package se.melsom.presentation.program;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;
import se.melsom.presentation.ViewModel;

public class ProgramStageViewModel extends ViewModel {
	private static Logger logger = Logger.getLogger(ProgramStageViewModel.class);
	private static final String STAGE_TITLE = "StageTitle";
	private static final String TEAM_SIZE = "TeamSize";
	private static final String FIRST_CALL = "FirstCall";
	private static final String NEXT_CALL_INTERVAL = "NextCallInterval";
	private static final String CREATE_TEAMS = "CreateTeams";
	
	private ProgramStage stage;
	private MatchProgramViewModel matchProgramViewModel;
	private ProgramStageView view;
	private ProgramStageContextMenu stageContextMenu;
	private Vector<ProgramStringViewModel> strings = new Vector<>();
	
	private JPanel stringsPanel;

	
	public ProgramStageViewModel(ProgramStage stage, MatchProgramView matchProgramView, MatchProgramViewModel matchProgramViewModel) {
		this.stage = stage;
		this.matchProgramViewModel = matchProgramViewModel;
		
		stageContextMenu = new ProgramStageContextMenu(this);
		view = new ProgramStageView(this, stage, stageContextMenu);
		
		matchProgramView.addPanel(view);
		
		stringsPanel = new JPanel();
		stringsPanel.setLayout(new BoxLayout(stringsPanel, BoxLayout.Y_AXIS));
		stringsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		matchProgramView.addPanel(stringsPanel);

		for (ProgramString string : stage.getStrings()) {
			strings.addElement(new ProgramStringViewModel(string, this));
		}
	}

	@Override
	public JComponent getView() {
		return view;
	}

	public void removeStage() {
		matchProgramViewModel.removeStage(stage);
	}

	public void createString() {
		int stringIndex = stage.getStringCount();
		String stringTitle = "Serie " + (stringIndex + 1);
		
		stage.addString(new ProgramString(stringIndex, stringTitle, 5));
	}
	
	public void removeString(ProgramString string) {
		stage.removeString(string);
	}
	
	public void createTeamsAction() {
		logger.warn("createTeamsAction(" + stage + ") not yet implemented!");
		// TODO
//		matchProgram.createTeams(stageIndex);
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
		} else if (event.getSource() instanceof JButton) {
			JButton source = (JButton) event.getSource();

			switch (source.getName()) {
			case CREATE_TEAMS:
				createTeamsAction();
				break;
				
			default:
				logger.warn("Unhandled button action event " + event);
				break;
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
		case TEAM_SIZE:
			if (!isValidNumber(source.getText(), ch, source.getColumns())) {
				event.consume();
			}
			break;
			
		case FIRST_CALL:
			if (!isValidTime(source.getText(), ch)) {
				event.consume();
			}
			break;
			
		case NEXT_CALL_INTERVAL:
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
		case STAGE_TITLE:
			stage.setTitle(newText);
			break;
			
		case TEAM_SIZE:
			stage.setTeamSize(Integer.parseInt(newText));
			break;
			
		case FIRST_CALL:
			stage.setFirstCall(newText);
			break;
			
		case NEXT_CALL_INTERVAL:
			stage.setNextCallInterval(Integer.parseInt(newText));
			break;
			
		default:
			return false;
		}
		
		return true;
	}


	// TODO: subscribe for event
	public void stringRemoved(ProgramStage stage, ProgramString s) {
		strings.clear();
		stringsPanel.removeAll();
		view.revalidate();
		
		for (ProgramString string : stage.getStrings()) {
			strings.addElement(new ProgramStringViewModel(string, this));
		}
	}

	public void addPanel(ProgramStringView stringView) {
		stringsPanel.add(stringView);
		view.revalidate();
	}
}
