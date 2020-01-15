package se.melsom.presentation.scores;

import java.util.Vector;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventListener;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.score.ShotScore;
import se.melsom.model.score.StringScore;

public class StringScoreViewModel implements ModelEventListener {
	private static Logger logger = Logger.getLogger(StringScoreViewModel.class);	

	private StringScore stringScore;
	private int laneIndex;
	private Competitor competitor;
	private Vector<ShotScoreViewModel> shotScores = new Vector<>();
	private JLabel totalScore = null;

	public StringScoreViewModel(int laneIndex, Competitor competitor, StringScore stringScore) {
		this.stringScore = stringScore;
		this.laneIndex = laneIndex;
		this.competitor = competitor;
		
		for (ShotScore shotScore : stringScore.getShotScores()) {
			shotScores.addElement(new ShotScoreViewModel(shotScore));
		}
		
		stringScore.getEventBroker().addListener(this);
	}
	
	public String getLaneNumber() {
		return "" + (laneIndex + 1);
	}
	
	public String getShooterName() {
		return competitor.getName();
	}
	
	public Vector<ShotScoreViewModel> getShotScoreModels() {
		return shotScores;
	}
	
	public void setTotalScore(JLabel totalScore) {
		this.totalScore = totalScore;
		
		totalScore.setText("" + stringScore.getTotalScore());
	}

	@Override
	public void handleEvent(ModelEvent event) {
		if (event.getSource() instanceof StringScore) {
			if (stringScore.equals(event.getSource())) {
				String totalScoreText = "" + stringScore.getTotalScore();
				
				logger.debug("Total string score for " + competitor.getName() + " is " + totalScoreText);
				if (totalScore != null) {
					totalScore.setText(totalScoreText);
				}
			}
		}
	}
	
	@Override
	public void finalize() throws Throwable {
		stringScore.getEventBroker().removeListener(this);
		
		super.finalize();
	}
}
