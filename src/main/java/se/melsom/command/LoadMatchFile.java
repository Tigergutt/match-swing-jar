package se.melsom.command;

import se.melsom.application.ApplicationController;

public class LoadMatchFile implements Command {
	private ApplicationController controller;
	
	public LoadMatchFile(ApplicationController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.loadMatchFileAction();
	}

}
