package se.melsom.presentation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	private static Logger logger = Logger.getLogger(MainView.class);

	private JMenuItem mntmNewRangeShooting;
	private JMenuItem mntmNewFieldShooting = null;
	private JMenuItem mntmNewTournament = null;
	private JMenuItem mntmOpen = null;
	private JMenuItem mntmSettings = null;
	private JMenuItem mntmSave = null;
	private JMenuItem mntmSaveAs = null;
	private JMenuItem mntmPrintProtocol = null;
	private JMenuItem mntmPrintResult = null;
	private JMenu mnImport = null;
	private JMenuItem mntmImportShooters = null;
	private JMenuItem mntmImportScoreboard = null;
	private JMenuItem mntmExport = null;
	private JMenu mnCompetition = null;
	private JMenuItem mntmMatchProgram = null;
	private JMenuItem mntmRegisterShooter = null;
	private JMenuItem mntmRegisterScores = null;
	private JMenu mnTournament = null;

	private JCheckBoxMenuItem chckbxmntmShowCompetitors = null;
	private JCheckBoxMenuItem chckbxmntmShowLeaderboard = null;
	private JCheckBoxMenuItem chckbxmntmShowTeams = null;
	
	private JDesktopPane desktopPane;
	private MainViewModel presenter;
	
	public MainView(MainViewModel presenter) {
		this.presenter = presenter;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				quit(false);
			}
		});
		
		setTitle("Shooter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Arkiv");
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("Ny");
		mnFile.add(mnNew);
		
		mntmNewRangeShooting = new JMenuItem("Banskyttetävling ...");
		mntmNewRangeShooting.addActionListener(presenter);		
		mnNew.add(mntmNewRangeShooting);
		
		mntmNewFieldShooting = new JMenuItem("Fältskyttetävling ...");
		mntmNewFieldShooting.addActionListener(presenter);
		mnNew.add(mntmNewFieldShooting);
		
		mntmNewTournament = new JMenuItem("Serie ...");
		mntmNewTournament.setEnabled(false);
		mntmNewTournament.addActionListener(presenter);		
		mnNew.add(mntmNewTournament);
		
		mntmOpen = new JMenuItem("Öppna ...");
		mntmOpen.setActionCommand("LoadMatchFile");
		mntmOpen.addActionListener(presenter);
		mnFile.add(mntmOpen);
		
		mnFile.add(new JSeparator());
		
		JMenu mnPrint = new JMenu("Skriv ut");
		mnFile.add(mnPrint);
		
		mntmPrintProtocol = new JMenuItem("Protokoll ...");
		mntmPrintProtocol.addActionListener(presenter);
		mntmPrintProtocol.setActionCommand("PrintProtocol");
		mnPrint.add(mntmPrintProtocol);
		
		mntmPrintResult = new JMenuItem("Resultat ...");
		mntmPrintResult.addActionListener(presenter);
		mntmPrintResult.setActionCommand("PrintResult");
		mnPrint.add(mntmPrintResult);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		mntmSettings = new JMenuItem("Inställningar ...");
		mntmSettings.setEnabled(false);
		mntmSettings.addActionListener(presenter);
		mnFile.add(mntmSettings);
		
		mnFile.add(new JSeparator());
		
		mntmSave = new JMenuItem("Spara");
		mntmSave.addActionListener(presenter);
		mntmSave.setActionCommand("SaveMatchFile");
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuItem("Spara som ...");
		mntmSaveAs.addActionListener(presenter);
		mntmSaveAs.setActionCommand("SaveMatchFileAs");
		mnFile.add(mntmSaveAs);
		
		mnFile.add(new JSeparator());
		
		mnImport = new JMenu("Importera");
		mnFile.add(mnImport);
		
		mntmImportShooters = new JMenuItem("Skyttar ...");
		mntmImportShooters.addActionListener(presenter);
		mnImport.add(mntmImportShooters);
		
		mntmImportScoreboard = new JMenuItem("Resultat ...");
		mntmImportScoreboard.addActionListener(presenter);
		mnImport.add(mntmImportScoreboard);
		
		mntmExport = new JMenuItem("Exportera ...");
		mntmExport.addActionListener(presenter);		
		mnFile.add(mntmExport);
		
		mnFile.add(new JSeparator());
		
		JMenuItem mntmQuit = new JMenuItem("Avsluta");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quit(true);
			}
		});
		mnFile.add(mntmQuit);
		
		mnCompetition = new JMenu("Tävling");
		mnCompetition.setEnabled(false);
		menuBar.add(mnCompetition);
		
		mntmMatchProgram = new JMenuItem("Skjutprogram ...");
		mntmMatchProgram.setEnabled(false);
		mntmMatchProgram.setActionCommand("EditMatchProgram");
		mntmMatchProgram.addActionListener(presenter);
		mnCompetition.add(mntmMatchProgram);
		
		mnCompetition.add(new JSeparator());
		
		mntmRegisterShooter = new JMenuItem("Registrera skytt ...");
		mntmRegisterShooter.setEnabled(false);
//		mntmRegisterShooter.setEnabled(false);
		mntmRegisterShooter.setActionCommand("RegisterCompetitor");
		mntmRegisterShooter.addActionListener(presenter);
		mnCompetition.add(mntmRegisterShooter);
		
		mntmRegisterScores = new JMenuItem("Registrera resultat ...");
		mntmRegisterScores.setEnabled(false);
//		mntmRegisterScores.setEnabled(false);
		mntmRegisterScores.setActionCommand("RegisterScore");
		mntmRegisterScores.addActionListener(presenter);
		mnCompetition.add(mntmRegisterScores);
		
		mnTournament = new JMenu("Serie");
		mnTournament.setEnabled(false);
		menuBar.add(mnTournament);
		
		JMenu mnShow = new JMenu("Visa");
		menuBar.add(mnShow);
		
		chckbxmntmShowCompetitors = new JCheckBoxMenuItem("Deltagarlista");
		chckbxmntmShowCompetitors.setActionCommand("ShowCompetitors");
		chckbxmntmShowCompetitors.addActionListener(presenter);
		mnShow.add(chckbxmntmShowCompetitors);
		
		chckbxmntmShowLeaderboard = new JCheckBoxMenuItem("Resultatlista");
		chckbxmntmShowLeaderboard.setActionCommand("ShowLeaderboard");
		chckbxmntmShowLeaderboard.addActionListener(presenter);
		mnShow.add(chckbxmntmShowLeaderboard);
		
		chckbxmntmShowTeams = new JCheckBoxMenuItem("Skjutlag");
		chckbxmntmShowTeams.setActionCommand("ShowTeams");
		chckbxmntmShowTeams.addActionListener(presenter);
		mnShow.add(chckbxmntmShowTeams);
		
		desktopPane = new JDesktopPane();
		getContentPane().add(desktopPane, BorderLayout.CENTER);
	}

	public JDesktopPane getDesktop() {
		return desktopPane;
	}
	
	public void setRegisterShooterEnabled(boolean isEnabled) {
		mntmRegisterShooter.setEnabled(isEnabled);
	}
	
	public void setRegisterScoresEnabled(boolean isEnabled) {
		mntmRegisterScores.setEnabled(isEnabled);
	}
	
	public void setCompetitorListChecked(boolean isChecked) {
		chckbxmntmShowCompetitors.setSelected(isChecked);		
	}
	
	public void setLeaderboardChecked(boolean isChecked) {
		chckbxmntmShowLeaderboard.setSelected(isChecked);		
	}
	
	public void setTeamsChecked(boolean isChecked) {
		chckbxmntmShowTeams.setSelected(isChecked);		
	}
	
	void quit(boolean shouldDispose) {
		logger.debug("quit(" + shouldDispose + ")");
		presenter.quitApplication();
		
		if (shouldDispose) {
			dispose();
		}
	}
}
