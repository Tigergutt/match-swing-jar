package se.melsom.presentation.leaderboard;

import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import se.melsom.application.ApplicationController;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventListener;
import se.melsom.model.score.Scoreboard;
import se.melsom.presentation.ViewModel;
import se.melsom.settings.ApplicationSettings;
import se.melsom.settings.WindowSettings;

public class LeaderboardViewModel extends ViewModel implements ModelEventListener {
	private static Logger logger = Logger.getLogger(LeaderboardViewModel.class);
	private ApplicationController controller;
	private LeaderboardView view;
	
	public LeaderboardViewModel(ApplicationController controller) {
		this.controller = controller;

		WindowSettings settings = ApplicationSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 500, 10, 300, 400, false);
			
			ApplicationSettings.singleton().addWindowSettings(settings);
		}
		
		view = new LeaderboardView(this);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		controller.setLeaderboardChecked(settings.isVisible());
		view.addComponentListener(this);
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
	public void handleEvent(ModelEvent event) {
		if (event.getSource() instanceof Scoreboard) {
			logger.debug("handle scoreboard event: " + event);
			
			view.updateContent((Scoreboard) event.getSource()); 
			WindowSettings settings = ApplicationSettings.singleton().getWindowSettings(getWindowName());
			
			if (settings != null) {
				view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
			}
		}
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}


	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		ApplicationSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		ApplicationSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		ApplicationSettings.singleton().setWindowVisible(getWindowName(), true);	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		controller.setLeaderboardChecked(false);
		ApplicationSettings.singleton().setWindowVisible(getWindowName(), false);	
	}

	String getWindowName() {
		return LeaderboardView.class.getSimpleName();
	}
}
