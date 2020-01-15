package se.melsom.presentation.program;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class ProgramStringContextMenu extends JPopupMenu {
	private JMenuItem mntmRemoveString;

	public ProgramStringContextMenu(ProgramStringViewModel viewModel) {
		mntmRemoveString = new JMenuItem("Ta bort serie");
		mntmRemoveString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewModel.removeString();
			}
		});
		
		add(mntmRemoveString);
	}

}
