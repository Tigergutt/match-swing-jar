package se.melsom.presentation.navigator;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class StageTeamView extends JPanel {
	private static Logger logger = Logger.getLogger(StageTeamView.class);

	public StageTeamView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	public void setViewModel(StageTeamViewModel viewModel) {
	}
	
	public void clear() {
	}

	public void addSubView(JComponent component) {
		logger.warn("Add sub view is not yet implemented!");
	}

}
