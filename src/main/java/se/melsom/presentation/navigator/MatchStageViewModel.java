package se.melsom.presentation.navigator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventListener;
import se.melsom.model.Competition;
import se.melsom.model.ShooterDatabase;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.score.Scoreboard;
import se.melsom.model.score.Scorecard;
import se.melsom.model.shooter.Shooter;

@SuppressWarnings("serial")
public class MatchStageViewModel extends DefaultMutableTreeNode implements ActionListener, ModelEventListener  {
	private static Logger logger = Logger.getLogger(MatchStageViewModel.class);

	private static final String CREATE_TEAMS = "CreateTeams";
	private static final String SHUFFLE_LIST = "ShuffleList";

	private ProgramStage stage;
	private MatchStageView view;
	
	public MatchStageViewModel(ProgramStage stage) {
		this.stage = stage;
		
		stage.getEventBroker().addListener(this);
	}
	
	public ProgramStage getStage() {
		return stage;
	}

	public void updateView(MatchStageView view) {
		this.view = view;
		
		view.setTeamSize(stage.getTeamSize());
		view.setCreateTeamsEnabled(stage.getTeamCount() == 0 && stage.getCompetitorCount() > 0);
		view.setScrambleListEnabled(stage.getTeamCount() == 0 && stage.getCompetitorCount() > stage.getTeamSize());
		view.getCompetitorTableModel().setCompetitors(stage.getCompetitorList());
	}

	@Override
	public void handleEvent(ModelEvent event) {
		ModelElement source = event.getSource();
		
		if (source instanceof Scoreboard && stage.getIndex() > 0) {
			logger.debug("handle scoreboard event(" + event + ")");
			Scoreboard scoreboard = (Scoreboard) source;
			int max = Math.min(scoreboard.getScorecards().size(), stage.getTeamSize());	
			stage.getCompetitorList().clear();
			
			for (int scorecardIndex = 0; scorecardIndex < max; scorecardIndex++) {
				Scorecard scorecard = scoreboard.getScorecards().get(scorecardIndex);
				Shooter shooter = ShooterDatabase.singleton().getShooter(scorecard.getShooterID());
				Competitor competitor = new Competitor(shooter);
				
				stage.getCompetitorList().add(competitor);
			}
		}
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		logger.debug("Action command '" + action + "'");
		
		if (event.getSource() instanceof JButton) {
			JButton source = (JButton) event.getSource();

			switch (source.getName()) {
			case CREATE_TEAMS:
				stage.createTeams(Competition.singleton().getScoreboard());
				break;
				
			case SHUFFLE_LIST:
				view.getCompetitorTableModel().shuffle();
				break;

			default:
				logger.warn("Unhandled button action event " + event);
				break;
			}
		}
		
	}

	@Override
	public String toString() {
		return stage.getTitle();
	}
}
