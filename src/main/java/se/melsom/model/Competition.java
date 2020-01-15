package se.melsom.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import se.melsom.competition.jaxb.DAOCompetition;
import se.melsom.competition.jaxb.DAOCompetitor;
import se.melsom.competition.jaxb.DAOScoreboard;
import se.melsom.competition.jaxb.DAOScorecard;
import se.melsom.competition.jaxb.DAOShotScore;
import se.melsom.competition.jaxb.DAOStage;
import se.melsom.competition.jaxb.DAOStageScore;
import se.melsom.competition.jaxb.DAOString;
import se.melsom.competition.jaxb.DAOStringScore;
import se.melsom.competition.jaxb.DAOTeam;
import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.program.MatchProgram;
import se.melsom.model.program.MatchType;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;
import se.melsom.model.program.StageTeam;
import se.melsom.model.score.Scoreboard;
import se.melsom.model.score.Scorecard;
import se.melsom.model.score.ShotScore;
import se.melsom.model.score.StageScore;
import se.melsom.model.score.StringScore;
import se.melsom.model.shooter.Shooter;

public class Competition implements ModelElement {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static Competition singleton = new Competition();
	
	private ModelEventBroker eventBroker = null;
	private ShooterDatabase database = ShooterDatabase.singleton();
	private MatchProgram program = new MatchProgram(MatchType.RANGE);
	private Scoreboard scoreboard = new Scoreboard();
	
	private boolean isDirty = false;

	private Competition() {
		program.setParent(this);
		scoreboard.setParent(this);
	}
	
	public static Competition singleton() {
		return singleton;
	}

	public MatchProgram getMatchProgram() {
		return program;
	}
	

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void loadData(String path) throws JAXBException, FileNotFoundException {
		logger.debug("Load competition data from XML: " + path);		
		JAXBContext jxb = JAXBContext.newInstance(DAOCompetition.class.getPackage().getName());			
		Unmarshaller unmarshaller = jxb.createUnmarshaller();			
		DAOCompetition competitionData = (DAOCompetition) unmarshaller.unmarshal(new FileInputStream(path));
		
		program.clear();
		scoreboard.clear();
		
		Map<Integer, Competitor> competitors = new HashMap<>();
		
		if (competitionData.getProgram() != null) {
			program.setTitle(competitionData.getProgram().getTitle());
			program.setDate(competitionData.getProgram().getDate());
			competitionData.getProgram().getType();
			
			for (DAOStage stageData : competitionData.getProgram().getStages()) {
				ProgramStage stage = new ProgramStage(stageData.getIndex(), stageData.getName());
				
				stage.setTeamSize(stageData.getShooterCount());
				stage.setFirstCall(stageData.getFirstCall());
				stage.setNextCallInterval(stageData.getNextCallInterval());
				
				program.addStage(stage);
				
				if (stageData.getCompetitorList() != null) {
					for (DAOCompetitor shooterData : stageData.getCompetitorList().getCompetitors()) {
						Shooter shooter = database.getShooter(shooterData.getShooterID());
						Competitor competitor = new Competitor(shooter);
						
						stage.addCompetitor(competitor);
						
						if (!competitors.containsKey(shooterData.getShooterID())) {
							competitors.put(shooterData.getShooterID(), competitor);
						}
					}
				}
				
				if (stageData.getStringList() != null) {
					for (DAOString stringData : stageData.getStringList().getStrings()) {
						int index = stringData.getIndex();
						String title = stringData.getName();
						int shotCount = stringData.getShotCount();
						ProgramString string = new ProgramString(index, title, shotCount);
						
						stage.addString(string);
					}
				}
			}
			
			if (competitionData.getScoreboard() != null) {
				for (DAOScorecard scorecardData : competitionData.getScoreboard().getScores()) {
					Scorecard scorecard = new Scorecard(scorecardData.getShooterID());
					
					for (DAOStageScore stageScoreData : scorecardData.getScores()) {
						StageScore stageScore = new StageScore(stageScoreData.getStageIndex());
						
						for (DAOStringScore stringScoreData : stageScoreData.getScores()) {
							StringScore stringScore = new StringScore(stringScoreData.getStringIndex());
							
							for (DAOShotScore shotScoreData : stringScoreData.getScores()) {
								int index = shotScoreData.getShotIndex();
								int score = shotScoreData.getScore();
								boolean isBullsEye = shotScoreData.isBullsEye();
								ShotScore shotScore = new ShotScore(index, score, isBullsEye);
								
								stringScore.addShotScore(shotScore);
							}
							
							stageScore.addStringScore(stringScore);
						}
						
						scorecard.addStageScore(stageScore);						
					}
					
					scoreboard.addScorecard(scorecard);
				}				
			}
		}
		
		for (DAOStage stageData : competitionData.getProgram().getStages()) {
			ProgramStage stage = program.getStage(stageData.getIndex());
			
			if (stageData.getTeamList() != null) {
				for (DAOTeam teamData : stageData.getTeamList().getTeams()) {
					StageTeam team = new StageTeam(teamData.getId());
					
					team.setTitle(teamData.getTitle());
					team.setCallTime(teamData.getCallTime());
					team.setEventBroker(eventBroker);
					
					for (DAOCompetitor shooterData : teamData.getCompetitor()) {
						Competitor competitor = competitors.get(shooterData.getShooterID());
						
						competitor.setTeamID(shooterData.getTeamIndex());
						competitor.setLaneIndex(shooterData.getLaneIndex());
													
						team.addCompetitor(competitor);
					}
					
					stage.addTeam(team);					
				}				
			}
		}
		
		isDirty = false;
		matchModified();
	}
	
	public boolean isDirty() {
		return isDirty;
	}
	
	public void setDirty(boolean dirty) {
		this.isDirty = dirty;
	}

	public void saveData(String path) throws JAXBException, FileNotFoundException {
		JAXBContext jxb = JAXBContext.newInstance(DAOCompetition.class.getPackage().getName());			
		Marshaller marshaller = jxb.createMarshaller();
		DAOCompetition competitionData = new DAOCompetition();
		DAOCompetition.Program programData = new DAOCompetition.Program();
		
		programData.setTitle(program.getTitle());
		programData.setDate(program.getDate());
		programData.setType("" + program.getCompetitionType());
		
		competitionData.setProgram(programData);
		competitionData.setScoreboard(new DAOScoreboard());
		
		for (ProgramStage stage : program.getStages()) {
			DAOStage stageData = new DAOStage();
			
			stageData.setIndex(stage.getIndex());
			stageData.setName(stage.getTitle());
			stageData.setFirstCall(stage.getFirstCall());
			stageData.setNextCallInterval(stage.getNextCallInterval());
			stageData.setShooterCount(stage.getTeamSize());
			
			stageData.setStringList(new DAOStage.Strings());
			stageData.setTeamList(new DAOStage.Teams());
			stageData.setCompetitorList(new DAOStage.Competitors());
			
			competitionData.getProgram().getStages().add(stageData);
			
			for (ProgramString string : stage.getStrings()) {
				DAOString stringData = new DAOString();
				
				stringData.setIndex(string.getIndex());
				stringData.setName(string.getTitle());
				stringData.setShotCount(string.getShotCount());
				
				stageData.getStringList().getStrings().add(stringData);
			}
			
			for (StageTeam team : stage.getTeams()) {
				DAOTeam teamData = new DAOTeam();
				
				teamData.setId(team.getIndex());
				teamData.setTitle(team.getTitle());
				teamData.setCallTime(team.getCallTime());
				
				for (Competitor competitor : team.getCompetitors()) {
					DAOCompetitor shooterData = new DAOCompetitor();
					
					shooterData.setShooterID(competitor.getShooterID());
					shooterData.setTeamIndex(competitor.getTeamID());
					shooterData.setLaneIndex(competitor.getLaneIndex());
					
					teamData.getCompetitor().add(shooterData);
				}
				
				stageData.getTeamList().getTeams().add(teamData);
			}
			
			for (Competitor competitor : stage.getCompetitorList().getCompetitors()) {
				DAOCompetitor shooterData = new DAOCompetitor();
				
				shooterData.setShooterID(competitor.getShooterID());
				
				stageData.getCompetitorList().getCompetitors().add(shooterData);
			}
		}
		
		for (Scorecard scorecard : scoreboard.getScorecards()) {
			DAOScorecard scorecardData = new DAOScorecard();
			
			scorecardData.setShooterID(scorecard.getShooterID());
			
			competitionData.getScoreboard().getScores().add(scorecardData);
			
			for (StageScore stageScore : scorecard.getStageScores()) {
				DAOStageScore stageScoreData = new DAOStageScore();
				
				stageScoreData.setStageIndex(stageScore.getStageIndex());
				
				scorecardData.getScores().add(stageScoreData);
				
				for (StringScore stringScore : stageScore.getStringScores()) {
					DAOStringScore stringScoreData = new DAOStringScore();
					
					stringScoreData.setStringIndex(stringScore.getStringIndex());
					
					stageScoreData.getScores().add(stringScoreData);
					
					for (ShotScore shotScore : stringScore.getShotScores()) {
						DAOShotScore shotScoreData = new DAOShotScore();
						
						shotScoreData.setShotIndex(shotScore.getIndex());
						shotScoreData.setScore(shotScore.getScore());
						shotScoreData.setBullsEye(shotScore.isBullsEye());
						
						stringScoreData.getScores().add(shotScoreData);
					}
				}
			}
		}
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(competitionData, new FileOutputStream(path));
		isDirty = false;
	}

	@Override
	public ModelEventBroker getEventBroker() {
		return eventBroker;
	}

	@Override
	public void setEventBroker(ModelEventBroker eventBroker) {
		this.eventBroker = eventBroker;
		
		program.setEventBroker(eventBroker);
		scoreboard.setEventBroker(eventBroker);
	}

	@Override
	public ModelElement getParent() {
		return null;
	}

	@Override
	public void setParent(ModelElement parent) {
	}
	
	@Override
	public void childUpdated(ModelElement child) {
	}
	
	void matchModified() {
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
	}
}
