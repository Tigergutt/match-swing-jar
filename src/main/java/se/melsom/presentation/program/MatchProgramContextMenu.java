package se.melsom.presentation.program;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MatchProgramContextMenu extends JPopupMenu {
	private JMenuItem mntmAddStage = new JMenuItem("Lägg till omgång");;

	public MatchProgramContextMenu(MatchProgramViewModel viewModel) {
		super("Skjutprogram");
		mntmAddStage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewModel.createStage();
			}
		});
		
		add(mntmAddStage);
	}
}
