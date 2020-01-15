package se.melsom.command;

import se.melsom.presentation.program.MatchProgramViewModel;

public class EditMatchProgram implements Command {
	private MatchProgramViewModel matchProgramViewModel;
	
	public EditMatchProgram(MatchProgramViewModel matchProgramViewModel) {
		this.matchProgramViewModel = matchProgramViewModel;
	}

	@Override
	public void execute() {
		matchProgramViewModel.showView();;
	}

}
