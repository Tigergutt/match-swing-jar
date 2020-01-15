package se.melsom.command;

import se.melsom.application.ApplicationController;

public class SaveMatchFile implements Command {
	private ApplicationController controller;
	
	public SaveMatchFile(ApplicationController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.saveMatchFileAction();
	}

}
