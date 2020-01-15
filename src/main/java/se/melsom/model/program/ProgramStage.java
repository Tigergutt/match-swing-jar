package se.melsom.model.program;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.competitor.CompetitorList;
import se.melsom.model.score.Scoreboard;
import se.melsom.model.score.Scorecard;
import se.melsom.model.score.StageScore;

public class ProgramStage implements ModelElement {
	private static Logger logger = Logger.getLogger(ProgramStage.class);

	private ModelEventBroker eventBroker;
	private ModelElement parent;
	private int index;
	private String title;
	private int teamSize = 3;
	private String firstCall = "";
	private int nextCallInterval = 0;
	private Vector<ProgramString> strings = new Vector<>();
	private Vector<StageTeam> teams = new Vector<>();
	private CompetitorList competitorList = new CompetitorList();
	
	public ProgramStage(int index, String title) {
		this.index = index;
		this.title = title;
	}
	
	@Override
	public ModelEventBroker getEventBroker() {
		return eventBroker;
	}

	@Override
	public void setEventBroker(ModelEventBroker eventBroker) {
		this.eventBroker = eventBroker;
	}
	
	@Override
	public ModelElement getParent() {
		return parent;
	}

	@Override
	public void setParent(ModelElement parent) {
		this.parent = parent;
	}

	@Override
	public void childUpdated(ModelElement child) {
	}
	
	public int getIndex() {
		return index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		if (title.equals(value)) {
			return;
		}
		
		title = value;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int value) {
		if (teamSize == value) {
			return;
		}
		
		teamSize = value;
	}

	public String getFirstCall() {
		return firstCall;
	}
	
	public void setFirstCall(String text) {
		if (firstCall.equals(text)) {
			return;
		}
		
		firstCall = text;
		notifyStageModified();
	}
	
	public void setNextCallInterval(int i) {
		if (nextCallInterval == i) {
			return;
		}
		
		nextCallInterval = i;
		notifyStageModified();
	}
	
	public int getNextCallInterval() {
		return nextCallInterval;
	}
	
	public int getStringCount() {
		return strings.size();
	}

	public List<ProgramString> getStrings() {
		return strings;
	}

	public ProgramString getString(int index) {
		return strings.get(index);
	}

	public ProgramString removeString(int index) {
		ProgramString string = strings.remove(index);
		
		notifyStringRemoved(string);
		return string;
	}

	public void removeString(ProgramString string) {
		strings.remove(string);
		string.setParent(null);
		notifyStringRemoved(string);
	}

	public void addString(ProgramString string) {
		strings.add(string);
		string.setParent(this);
		string.setEventBroker(getEventBroker());
		notifyStringAdded(string);
	}
	
	public int getTeamCount() {
		return teams.size();
	}
	
	public StageTeam getTeam(int teamIndex) {
		return teams.get(teamIndex);
	}
	
	public void addTeam(StageTeam team) {
		teams.addElement(team);
		team.setParent(this);
		team.setEventBroker(getEventBroker());
		notifyTeamAdded(team);
	}

	public Collection<StageTeam> getTeams() {
		return teams;
	}
	
	public int getCompetitorCount() {
		return competitorList.size();
	}
	
	public boolean isCompeting(int shooterID) {
		return competitorList.containsShooter(shooterID);
	}

	public CompetitorList getCompetitorList() {
		return competitorList;
	}
	
	public void addCompetitor(Competitor competitor) {
		logger.debug("addCompetitor");
		competitorList.add(competitor);
		notifyCompetitorAdded(competitor);
	}
	
	public void createTeams(Scoreboard scoreboard) {
		if (!getTeams().isEmpty()) {
			logger.warn("Team container is not empty!");
			return;
		}

		Collection<Competitor> competitors = getCompetitorList().getCompetitors();
		
		if (competitors.isEmpty()) {
			logger.warn("Competitor list is empty!");
			return;
		}
				
		int laneIndex = 0;
		int teamIndex = -1;
		Vector<StageTeam> teams = new Vector<>();
		StageTeam currentTeam = null;

		for (Competitor competitor : competitors) {
			logger.debug(laneIndex + ": " + competitor);
			if (laneIndex == 0) {
				currentTeam = new StageTeam(++teamIndex);
				teams.addElement(currentTeam);
			}
			
			competitor.setTeamID(teamIndex);
			competitor.setLaneIndex(laneIndex);

			currentTeam.addCompetitor(competitor);

			Scorecard scorecard = scoreboard.getScorecard(competitor.getShooterID());
			
			if (scorecard == null) {
				scorecard = new Scorecard(competitor.getShooterID());
				
				scoreboard.addScorecard(scorecard);
			}
						
			scorecard.addStageScore(new StageScore(this));

			if (++laneIndex % getTeamSize() == 0) {
				laneIndex = 0;
			}
		}
		
		for (StageTeam team : teams) {
			addTeam(team);
		}
	}

	private void notifyStageModified() {
		logger.debug("notifyStageModified");
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
	}

	private void notifyStringAdded(ProgramString string) {
		logger.debug("notifyStringAdded");
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_ADDED, string));
	}

	private void notifyStringRemoved(ProgramString string) {
		logger.debug("notifyStringRemoved");
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_REMOVED, string));
	}

	private void notifyCompetitorAdded(Competitor competitor) {
		logger.debug("notifyCompetitorAdded");
	}

	private void notifyTeamAdded(StageTeam team) {
		logger.debug("notifyTeamAdded");
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_ADDED, team));
	}
	
	@Override
	public String toString() {
		return "Stage #" + getIndex() + " " + getTitle();
	}
}
