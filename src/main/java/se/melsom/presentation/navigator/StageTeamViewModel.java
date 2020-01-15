package se.melsom.presentation.navigator;

import javax.swing.tree.DefaultMutableTreeNode;

import se.melsom.model.program.StageTeam;

@SuppressWarnings("serial")
public class StageTeamViewModel extends DefaultMutableTreeNode {
	private StageTeam team;
	
	public StageTeamViewModel(StageTeam team) {
		this.team = team;
	}

	public void updateView(StageTeamView view) {
		view.clear();
	}
	
	@Override
	public String toString() {
		return team.getTitle();
	}

}
