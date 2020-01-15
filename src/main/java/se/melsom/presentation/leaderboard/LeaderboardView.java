package se.melsom.presentation.leaderboard;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.log4j.Logger;

import se.melsom.io.html.HTMLRenderer;
import se.melsom.model.document.Leaderboard;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.score.Scoreboard;

@SuppressWarnings("serial")
public class LeaderboardView extends JInternalFrame {
	private static Logger logger = Logger.getLogger(LeaderboardView.class);
	private JEditorPane editorPane;

	public LeaderboardView(LeaderboardViewModel viewModel) {
		addComponentListener(viewModel);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Resultatlista");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		editorPane.setEditorKit(new HTMLEditorKit());
		scrollPane.setViewportView(editorPane);
	}

	public void setMatchStageData(ProgramStage stage) {
		setTitle("Resultatlista - " + stage.getTitle());
	}
	
	void updateContent(Scoreboard scoreboard) {
		logger.debug("Update content.");
		Leaderboard leaderboard = new Leaderboard(scoreboard);
		HTMLRenderer renderer = new HTMLRenderer();
		
		leaderboard.render(renderer);
		editorPane.setDocument(renderer.getHTML());		
	}

}
