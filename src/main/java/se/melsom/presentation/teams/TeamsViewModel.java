package se.melsom.presentation.teams;

import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import se.melsom.application.ApplicationController;
import se.melsom.presentation.ViewModel;

public class TeamsViewModel extends ViewModel {
	private static Logger logger = Logger.getLogger(TeamsViewModel.class);
	private ApplicationController controller;
	private TeamsView view;
	
	public TeamsViewModel(ApplicationController controller) {
		this.controller = controller;
		view = new TeamsView(this);
	}

	public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public JComponent getView() {
		return view;
	}

	public void showView() {
		logger.debug("showView()");
		if (view.isVisible()) {
			if (view.isIcon()) {
				try {
					view.setIcon(false);
				} catch (PropertyVetoException e) {
					logger.error("showView()", e);
				}
			} else {
				view.setVisible(false);
			}
		} else {
			view.setVisible(true);
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		controller.setTeamsChecked(false);
	}

}
