package se.melsom.presentation.program;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventListener;
import se.melsom.model.program.MatchProgram;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;
import se.melsom.presentation.ViewModel;

public class MatchProgramViewModel extends ViewModel implements ModelEventListener  {
	private static Logger logger = Logger.getLogger(MatchProgramViewModel.class);
	
	private MatchProgram matchProgram;
	private MatchProgramView view;
	private MatchProgramContextMenu matchContextMenu;
	private Vector<ProgramStageViewModel> stages = new Vector<>();

	public MatchProgramViewModel(MatchProgram matchProgram) {
		this.matchProgram = matchProgram;
		
		view = new MatchProgramView(this);
		matchContextMenu = new MatchProgramContextMenu(this);
		
		view.setContextMenu(matchContextMenu);
		update(matchProgram);
	}

	@Override
	public JComponent getView() {
		return view;
	}
	
	@Override
	public String toString() {
		return "Program";
	}	

	@Override
	public void handleEvent(ModelEvent event) {
		if (event.getSource() instanceof MatchProgram) {
			logger.debug("handleEvent(" + event + ")");
			matchProgram = (MatchProgram) event.getSource();

			update(matchProgram);
		} else if (event.getSource() instanceof ProgramStage) {
			logger.debug("handleEvent(" + event + ")");
			matchProgram = (MatchProgram) event.getSource().getParent();

			update(matchProgram);
		} else if (event.getSource() instanceof ProgramString) {
			logger.debug("handleEvent(" + event + ")");
			matchProgram = (MatchProgram) event.getSource().getParent().getParent();

			update(matchProgram);
		}
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	public void update(MatchProgram program) {
		view.clear();
		view.setMatchTitle(program.getTitle());
		view.setMatchDate(program.getDate());
		
		for (ProgramStage stage : program.getStages()) {
			ProgramStageViewModel stageViewModel = new ProgramStageViewModel(stage, view, this);
			
			stages.addElement(stageViewModel);
		}
	}

	public void showView() {
		logger.warn("showView()");
//		if (view.isVisible()) {
//			if (view.isIcon()) {
//				try {
//					view.setIcon(false);
//				} catch (PropertyVetoException e) {
//					logger.error("showView()", e);
//				}
//			}
//		} else {
//			view.setVisible(true);
//		}
	}

	void setMatchTitle(String text) {
		matchProgram.setTitle(text);
	}
	
	void setMatchDate(String text) {
		matchProgram.setDate(text);
	}
	
	public void createStage() {
		int stageIndex = matchProgram.getStageCount();
		String stageTitle = "Omgång " + (stageIndex + 1);
		ProgramStage stage = new ProgramStage(stageIndex, stageTitle);
		matchProgram.addStage(stage);
	}
	
	public void removeStage(ProgramStage stage) {
		matchProgram.removeStage(stage);
	}

//	public void removeStage(int stageIndex) {
//		matchProgram.removeStage(stageIndex);
//	}

//	public void createString(int stageIndex) {
//		ProgramStage stage = matchProgram.getStage(stageIndex);
//		
//		if (stage == null) {
//			logger.warn("Stage index is out of bounds.");
//			return;
//		}
//		
//		int stringIndex = stage.getStringCount();
//		String stringTitle = "Serie " + (stringIndex + 1);
//		
//		stage.addString(new ProgramString(stringIndex, stringTitle, 5));
//	}

//	public void removeString(int stageIndex, int stringIndex) {
//		ProgramStage stage = matchProgram.getStage(stageIndex);
//		
//		if (stage == null) {
//			logger.warn("Stage index is out of bounds.");
//			return;
//		}
//		
//		stage.removeString(stringIndex);
//	}
	
//	public void createTeamsAction(int stageIndex) {
//		logger.debug("createTeamsAction(" + stageIndex + ")");
//		matchProgram.createTeams(stageIndex);
//	}

	boolean handleTextFieldEvent(Component component, String newText) {
		switch (component.getName()) {
		case "MatchTitle":
			matchProgram.setTitle(newText);
			break;
			
		case "MatchDate":
			matchProgram.setDate(newText);
			break;
			
		default:
			return false;
		}
		
		return true;
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


//	@Override
//	public void programModified(MatchProgram matchProgram) {
//		this.matchProgram = matchProgram;
//		
//		update(matchProgram);
//	}
//
//	@Override
//	public void stageAdded(MatchProgram matchProgram, ProgramStage stage) {
//		this.matchProgram = matchProgram;
//		
//		update(matchProgram);
//	}
//
//	@Override
//	public void stageRemoved(MatchProgram matchProgram, ProgramStage stage) {
//		this.matchProgram = matchProgram;
//		
//		update(matchProgram);
//	}

}
