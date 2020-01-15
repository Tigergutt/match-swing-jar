package se.melsom.presentation.navigator;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import se.melsom.model.competitor.Competitor;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;
import se.melsom.model.program.StageTeam;
import se.melsom.model.score.Scoreboard;
import se.melsom.model.score.Scorecard;
import se.melsom.model.score.StageScore;
import se.melsom.model.score.StringScore;
import se.melsom.presentation.scores.StringScoreEditorView;
import se.melsom.presentation.scores.StringScoreViewModel;

@SuppressWarnings("serial")
public class StringViewModel extends DefaultMutableTreeNode {
	private static Logger logger = Logger.getLogger(NavigatorViewModel.class);
	
	private ProgramString string;
	private StringScoreEditorView view;
	
	public StringViewModel(ProgramString string, StageTeam team, Scoreboard scoreboard) {
		this.string = string;
		
		ProgramStage stage = (ProgramStage) string.getParent();
		Vector<StringScoreViewModel> viewModels = new Vector<>();
		
		logger.debug("Team=" + team.getTitle() + ",size=" + team.getSize());
		
		for (int laneIndex : team.getLanes()) {
			logger.debug("Lane=" + laneIndex);
			
			Competitor competitor = team.getCompetitor(laneIndex);	
			logger.debug("Shooter id=" + competitor.getShooterID());
			Scorecard scorecard = scoreboard.getScorecard(competitor.getShooterID());
			StageScore stageScore = scorecard.getStageScore(stage.getIndex());			
			StringScore stringScore = stageScore.getStringScore(string.getIndex());
			
			viewModels.addElement(new StringScoreViewModel(laneIndex, competitor, stringScore));
		}
		
		view = new StringScoreEditorView(viewModels);
	}

	public void updateView(StringView stringView) {
		stringView.clear();
		stringView.addSubView(view);
	}

	@Override
	public String toString() {
		return string.getTitle();
	}
}
