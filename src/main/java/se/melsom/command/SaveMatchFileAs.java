package se.melsom.command;

import se.melsom.application.ApplicationController;

public class SaveMatchFileAs implements Command {
	private ApplicationController controller;
	
	public SaveMatchFileAs(ApplicationController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.saveMatchFileAsAction();
	}

}
