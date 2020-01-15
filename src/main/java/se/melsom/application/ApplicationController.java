package se.melsom.application;

import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import se.melsom.command.EditMatchProgram;
import se.melsom.command.LoadMatchFile;
import se.melsom.command.PrintProtocol;
import se.melsom.command.PrintResult;
import se.melsom.command.RegisterCompetitor;
import se.melsom.command.SaveMatchFile;
import se.melsom.command.SaveMatchFileAs;
import se.melsom.command.ShowCompetitors;
import se.melsom.command.ShowLeaderboard;
import se.melsom.command.ShowTeams;
import se.melsom.event.ModelEventBroker;
import se.melsom.model.Competition;
import se.melsom.model.ShooterDatabase;
import se.melsom.presentation.MainView;
import se.melsom.presentation.MainViewModel;
import se.melsom.presentation.competitor.CompetitorListViewModel;
import se.melsom.presentation.competitor.RegisterShooterViewModel;
import se.melsom.presentation.leaderboard.LeaderboardViewModel;
import se.melsom.presentation.navigator.NavigatorViewModel;
import se.melsom.presentation.program.MatchProgramViewModel;
import se.melsom.presentation.teams.TeamsViewModel;
import se.melsom.settings.ApplicationSettings;
import se.melsom.settings.Property;

public class ApplicationController {
	private static Logger logger = Logger.getLogger(ApplicationController.class);
	private ModelEventBroker eventBroker = new ModelEventBroker();
	
	private ApplicationSettings settings = ApplicationSettings.singleton();
	private Competition competition = Competition.singleton();
	private ShooterDatabase database = ShooterDatabase.singleton();
	
	private MainViewModel mainViewModel = new MainViewModel();
	private MainView mainView = null;
	private NavigatorViewModel navigatorViewModel = null;
	private MatchProgramViewModel matchProgramViewModel = null;
	private RegisterShooterViewModel registerShooterViewModel = null;
	private CompetitorListViewModel competitorListViewModel = null;
	private TeamsViewModel teamsViewModel = null;
	private LeaderboardViewModel leaderboardViewModel = null;

	public ApplicationController() {
		try {
			settings.loadData("ApplicationSettings.xml");
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
		
		competition.setEventBroker(eventBroker);
	}
	
	public void applicationStart() {	
		logger.debug("Application start.");
		mainViewModel.createView(this);

		navigatorViewModel = new NavigatorViewModel(competition);
		mainViewModel.addInternalFrame(navigatorViewModel.getInternalFrame());
		eventBroker.addListener(navigatorViewModel);
		
		matchProgramViewModel = new MatchProgramViewModel(competition.getMatchProgram());
		eventBroker.addListener(matchProgramViewModel);
		navigatorViewModel.addViewModelToTree(matchProgramViewModel);
		
		teamsViewModel = new TeamsViewModel(this);
		mainViewModel.addInternalFrame(teamsViewModel.getInternalFrame());	
		
		registerShooterViewModel = new RegisterShooterViewModel(database, competition);
		navigatorViewModel.addViewModelToTree(registerShooterViewModel);

		competitorListViewModel = new CompetitorListViewModel(this);
		eventBroker.addListener(competitorListViewModel);
		mainViewModel.addInternalFrame(competitorListViewModel.getInternalFrame());	
		
		leaderboardViewModel = new LeaderboardViewModel(this);
		eventBroker.addListener(leaderboardViewModel);
		mainViewModel.addInternalFrame(leaderboardViewModel.getInternalFrame());	
		
		mainViewModel.addActionCommand("LoadMatchFile", new LoadMatchFile(this));
		mainViewModel.addActionCommand("SaveMatchFile", new SaveMatchFile(this));
		mainViewModel.addActionCommand("SaveMatchFileAs", new SaveMatchFileAs(this));
		
		mainViewModel.addActionCommand("PrintProtocol", new PrintProtocol(navigatorViewModel));
		mainViewModel.addActionCommand("PrintResult", new PrintResult(navigatorViewModel));
		
//		mainViewModel.addActionCommand("EditMatchProgram", new EditMatchProgram(matchProgramViewModel));

//		mainViewModel.addActionCommand("RegisterCompetitor", new RegisterCompetitor(registerShooterViewModel));
//		mainViewModel.addActionCommand("ShowCompetitors", new ShowCompetitors(competitorListViewModel));

		mainViewModel.addActionCommand("ShowTeams", new ShowTeams(teamsViewModel));

//		mainViewModel.addActionCommand("ShowLeaderboard", new ShowLeaderboard(leaderboardViewModel));		
	}
	
	public void loadMatchFileAction() {
		Property property = settings.getProperty("currentDirectory", ".");
		JFileChooser chooser = new JFileChooser(property.getValue());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");

		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(mainView);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			property.setValue(chooser.getSelectedFile().getParent());

			try {
				property = settings.getProperty("currentCompetitionPath", "");
				property.setValue(chooser.getSelectedFile().getPath());
				logger.debug("Load data from file=" + property.getValue());
				competition.loadData(property.getValue());
				
			} catch (FileNotFoundException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void saveMatchFileAction() {
		Property property = settings.getProperty("currentCompetitionPath", "");
		if (property.getValue().length() == 0) {
			saveMatchFileAsAction();
			return;
		}
		
		logger.debug("Save data to file=" + property.getValue());

		try {
			competition.saveData(property.getValue());
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveMatchFileAsAction() {
		Property property = settings.getProperty("currentDirectory", ".");
		JFileChooser chooser = new JFileChooser(property.getValue());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");

		chooser.setFileFilter(filter);

		int returnVal = chooser.showSaveDialog(mainView);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			property.setValue(chooser.getSelectedFile().getParent());

			try {
				property = settings.getProperty("currentCompetitionPath", "");
				property.setValue(chooser.getSelectedFile().getPath());
				logger.debug("Save data to file=" + property.getValue());

				competition.saveData(property.getValue());
			} catch (FileNotFoundException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setCompetitorListChecked(boolean isChecked) {
		mainViewModel.setCompetitorListChecked(isChecked);
	}

	public void setLeaderboardChecked(boolean isChecked) {
		mainViewModel.setLeaderboardChecked(isChecked);
	}

	public void setTeamsChecked(boolean isChecked) {
		mainViewModel.setTeamsChecked(isChecked);
	}

	public void applicationExit() {		
		logger.debug("applicationExit()");
		try {
			settings.saveData("ApplicationSettings.xml");
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
	}
	
}
