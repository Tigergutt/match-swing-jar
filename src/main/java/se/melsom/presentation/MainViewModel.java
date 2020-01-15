package se.melsom.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import se.melsom.application.ApplicationController;
import se.melsom.command.Command;
import se.melsom.settings.ApplicationSettings;
import se.melsom.settings.WindowSettings;

public class MainViewModel implements ActionListener, ComponentListener {
	private static Logger logger = Logger.getLogger(MainViewModel.class);

	private MainView mainView;
	private Map<String, Command> actionCommands = new HashMap<>();
	private ApplicationController applicationController;

	public void createView(ApplicationController applicationController) {
		this.applicationController = applicationController;
		
		WindowSettings settings = ApplicationSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 100, 100, 1024, 520, true);
			
			ApplicationSettings.singleton().addWindowSettings(settings);
		}
		
		mainView = new MainView(this);	
		mainView.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		mainView.setVisible(true);
		mainView.addComponentListener(this);
	}
	
	public void addInternalFrame(JInternalFrame internalFrame) {
		mainView.getDesktop().add(internalFrame);
	}
	
	public void addActionCommand(String action, Command command) {
		actionCommands.put(action, command);
	}

	public void quitApplication() {
		applicationController.applicationExit();
	}

	public void setCompetitorListChecked(boolean isChecked) {
		mainView.setCompetitorListChecked(isChecked);
	}

	public void setLeaderboardChecked(boolean isChecked) {
		mainView.setLeaderboardChecked(isChecked);
	}

	public void setTeamsChecked(boolean isChecked) {
		mainView.setTeamsChecked(isChecked);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		Command command = actionCommands.get(action);
		
		if (command == null) {
			logger.warn("Unhandled action command " + action);
			return;
		}
		
		command.execute();
	}

	public void setRegisterShooterEnabled(boolean enableRegisterShooter) {
		mainView.setRegisterShooterEnabled(enableRegisterShooter);
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JFrame == false) {
			return;
		}
		
		JFrame frame = (JFrame) event.getSource();

		ApplicationSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JFrame == false) {
			return;
		}
		
		JFrame frame = (JFrame) event.getSource();

		ApplicationSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	String getWindowName() {
		return MainView.class.getSimpleName();
	}

}
