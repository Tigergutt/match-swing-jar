package se.melsom.command;

import se.melsom.presentation.navigator.NavigatorViewModel;

public class PrintResult implements Command {
	private NavigatorViewModel viewModel;
	
	public PrintResult(NavigatorViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void execute() {
		viewModel.printResult();
	}
}
