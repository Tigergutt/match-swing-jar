package se.melsom.model.program;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventBroker;

public class MatchProgram implements ModelElement {
	private static Logger logger = Logger.getLogger(MatchProgram.class);

	private ModelEventBroker eventBroker = null;
	private ModelElement parent;
	private MatchType type;
	private String title = "Ny tävling";
	private String date = "";
	private Vector<ProgramStage> stages = new Vector<>();
	
	public MatchProgram(MatchType type) {
		clear();
		
		this.type = type;		
	}

	public void clear() {
		title = "Ny tävling";
		date = "";
		stages.clear();
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
	
	public MatchType getCompetitionType() {
		return type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		if (!this.title.equals(title)) {
			this.title = title;
			logger.debug("program modified");
			notifyProgramModified();
		}
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		if (!this.date.equals(date)) {
			logger.debug("program modified");
			this.date = date;
			notifyProgramModified();
		}
	}

	public int getStageCount() {
		return stages.size();
	}
	
	public Vector<ProgramStage> getStages() {
		return stages;
	}

	public void addStage(ProgramStage stage) {
		logger.debug("Stage added");
		stages.add(stage);
		stage.setParent(this);
		stage.setEventBroker(getEventBroker());
		notifyStageAdded(stage);
	}

	public void removeStage(ProgramStage stage) {
		notifyStageRemoved(stage);
		stages.remove(stage);
		stage.setParent(null);
	}
	
	public ProgramStage getStage(int stageIndex) {
		if (stageIndex > -1 && stages.size() > stageIndex) {
			return stages.get(stageIndex);
		}
		
		return null;
	}
	
//	public ProgramStage removeStage(int stageIndex) {
//		ProgramStage stage = stages.remove(stageIndex);
//
//		logger.debug("stage removed");
//		notifyStageRemoved(stage);
//		
//		return stage;
//	}

//	public void createTeams(int stageIndex) {
//		parent.createTeams(stageIndex);
//	}
	
	private void notifyProgramModified() {
		logger.debug("notifyProgramModified");
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_MODIFIED, this));
	}

	private void notifyStageAdded(ProgramStage stage) {
		logger.debug("notifyStageAdded");
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_ADDED, stage));
	}
	
	private void notifyStageRemoved(ProgramStage stage) {
		logger.debug("notifyStageRemoved");
		eventBroker.send(new ModelEvent(EventType.MODEL_ELEMENT_REMOVED, stage));
	}

}
