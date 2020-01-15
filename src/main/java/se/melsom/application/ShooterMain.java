package se.melsom.application;

import java.awt.EventQueue;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import se.melsom.logging.LogConfiguration;
import se.melsom.model.ShooterDatabase;
import se.melsom.settings.ApplicationSettings;

public class ShooterMain implements Runnable {
	private ApplicationController applicationController = null;

	public static void main(String[] args) {
		ShooterMain main = new ShooterMain();
		LogConfiguration.debug();
		main.start();	
		
		ApplicationSettings settings = ApplicationSettings.singleton();
		ShooterDatabase database = ShooterDatabase.singleton();
		
		try {
			settings.loadData("ApplicationSettings.xml");
			database.loadData("ShooterDatabase.xml");
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void start() {
		applicationController = new ApplicationController();

		EventQueue.invokeLater(this);
	}

	@Override
	public void run() {
		try {
			applicationController.applicationStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
