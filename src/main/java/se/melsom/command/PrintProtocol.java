package se.melsom.command;

import se.melsom.presentation.navigator.NavigatorViewModel;

public class PrintProtocol implements Command {
	private NavigatorViewModel viewModel;

	public PrintProtocol(NavigatorViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void execute() {
		viewModel.printProtocol();
	}

}
