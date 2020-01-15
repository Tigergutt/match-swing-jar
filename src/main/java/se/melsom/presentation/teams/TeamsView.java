package se.melsom.presentation.teams;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import se.melsom.model.Competition;
import se.melsom.model.program.ProgramStage;
import se.melsom.presentation.ViewModel;

@SuppressWarnings("serial")
public class TeamsView extends JInternalFrame {
	private JEditorPane editorPane;

	public TeamsView(ViewModel viewModel) {
		addComponentListener(viewModel);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setBounds(500, 10, 469, 400);
		setTitle("Skjutlagsindelning");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		editorPane.setEditorKit(new HTMLEditorKit());
		scrollPane.setViewportView(editorPane);
	}

	public void setMatchStageData(ProgramStage stage) {
		setTitle("Skjutlagsindelning - " + stage.getTitle());
	}

	void updateContent(Competition match) {
//		ShooterTeams teams = new ShooterTeams(match, match.getCurrentStage());
//		HTMLRenderer renderer = new HTMLRenderer();
//		
//		teams.render(renderer);
//		editorPane.setDocument(renderer.getHTML());		
	}
}
