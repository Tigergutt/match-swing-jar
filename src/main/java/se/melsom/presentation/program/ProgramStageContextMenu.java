package se.melsom.presentation.program;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ProgramStageContextMenu extends JPopupMenu {
	private JMenuItem mntmAddString;
	private JMenuItem mntmRemoveStage;

	public ProgramStageContextMenu(ProgramStageViewModel viewModel) {
		mntmAddString = new JMenuItem("Lägg till serie");
		mntmAddString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewModel.createString();
			}
		});
		add(mntmAddString);
		
		add(new JSeparator());
		
		mntmRemoveStage = new JMenuItem("Ta bort omgång");
		mntmRemoveStage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewModel.removeStage();
			}
		});
		
		add(mntmRemoveStage);
	}

}
