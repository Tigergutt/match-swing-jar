package se.melsom.presentation.program;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import se.melsom.model.program.ProgramStage;
import se.melsom.presentation.ViewModel;

@SuppressWarnings("serial")
public class ProgramStageView extends JPanel {
	private static Logger logger = Logger.getLogger(ProgramStageView.class);
	private ProgramStageContextMenu contextMenu;
	private JTextField txtStageTitle;
	private JTextField txtTeamSize;
	private JTextField txtFirstCall;
	private JTextField txtNextCallInterval;

	public ProgramStageView(ViewModel programStageViewModel, ProgramStage stage, ProgramStageContextMenu contextMenu) {
		this.contextMenu = contextMenu;

		setMaximumSize(new Dimension(32767, 50));
		setAlignmentY(Component.TOP_ALIGNMENT);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowHeights = new int[] {10, 20};
		gridBagLayout.columnWidths = new int[] { 5, 200, 5, 50, 0, 30, 0, 0, 30, 0, 0, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		setLayout(gridBagLayout);

		JLabel lblStage = new JLabel("Omgång");
		GridBagConstraints gbc_lblOmgng = new GridBagConstraints();
		gbc_lblOmgng.anchor = GridBagConstraints.NORTH;
		gbc_lblOmgng.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOmgng.insets = new Insets(0, 0, 5, 5);
		gbc_lblOmgng.gridx = 1;
		gbc_lblOmgng.gridy = 0;
		add(lblStage, gbc_lblOmgng);

		JLabel lblTeamSize = new JLabel("Skjutlagsstorlek");
		GridBagConstraints gbc_lblTeamSize = new GridBagConstraints();
		gbc_lblTeamSize.anchor = GridBagConstraints.NORTH;
		gbc_lblTeamSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTeamSize.gridwidth = 2;
		gbc_lblTeamSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeamSize.gridx = 3;
		gbc_lblTeamSize.gridy = 0;
		add(lblTeamSize, gbc_lblTeamSize);

		JLabel lblFirstCall = new JLabel("Första upprop");
		GridBagConstraints gbc_lblFirstCall = new GridBagConstraints();
		gbc_lblFirstCall.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFirstCall.gridwidth = 2;
		gbc_lblFirstCall.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstCall.gridx = 6;
		gbc_lblFirstCall.gridy = 0;
		add(lblFirstCall, gbc_lblFirstCall);

		JLabel lblInterval = new JLabel("Intervall");
		GridBagConstraints gbc_lblInterval = new GridBagConstraints();
		gbc_lblInterval.anchor = GridBagConstraints.NORTH;
		gbc_lblInterval.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInterval.insets = new Insets(0, 0, 5, 5);
		gbc_lblInterval.gridx = 9;
		gbc_lblInterval.gridy = 0;
		add(lblInterval, gbc_lblInterval);

		GridBagConstraints gbc_txtStageTitle = new GridBagConstraints();
		gbc_txtStageTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStageTitle.insets = new Insets(0, 0, 0, 5);
		gbc_txtStageTitle.gridx = 1;
		gbc_txtStageTitle.gridy = 1;

		txtStageTitle = new JTextField();
		txtStageTitle.setColumns(2);
		txtStageTitle.setName("StageTitle");
		txtStageTitle.addActionListener(programStageViewModel);
		txtStageTitle.addFocusListener(programStageViewModel);
		add(txtStageTitle, gbc_txtStageTitle);

		txtTeamSize = new JTextField();
		txtTeamSize.setColumns(2);
		txtTeamSize.setName("TeamSize");
		txtTeamSize.addActionListener(programStageViewModel);
		txtTeamSize.addFocusListener(programStageViewModel);
		txtTeamSize.addKeyListener(programStageViewModel);
		txtTeamSize.setHorizontalAlignment(SwingConstants.RIGHT);

		GridBagConstraints gbc_txtTeamSize = new GridBagConstraints();
		gbc_txtTeamSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTeamSize.insets = new Insets(0, 0, 0, 5);
		gbc_txtTeamSize.gridx = 3;
		gbc_txtTeamSize.gridy = 1;

		add(txtTeamSize, gbc_txtTeamSize);

		JLabel lblShooters = new JLabel("skyttar");
		GridBagConstraints gbc_lblSkyttar = new GridBagConstraints();
		gbc_lblSkyttar.insets = new Insets(0, 0, 0, 5);
		gbc_lblSkyttar.anchor = GridBagConstraints.WEST;
		gbc_lblSkyttar.gridx = 4;
		gbc_lblSkyttar.gridy = 1;
		add(lblShooters, gbc_lblSkyttar);

		JLabel lblNewLabel = new JLabel("Kl.");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 6;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		txtFirstCall = new JTextField();
		txtFirstCall.setName("FirstCall");
		txtFirstCall.addActionListener(programStageViewModel);
		txtFirstCall.addFocusListener(programStageViewModel);
		txtFirstCall.addKeyListener(programStageViewModel);
		txtFirstCall.setHorizontalAlignment(SwingConstants.RIGHT);

		GridBagConstraints gbc_txtFirstCall = new GridBagConstraints();
		gbc_txtFirstCall.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFirstCall.insets = new Insets(0, 0, 0, 5);
		gbc_txtFirstCall.gridx = 7;
		gbc_txtFirstCall.gridy = 1;

		add(txtFirstCall, gbc_txtFirstCall);

		txtNextCallInterval = new JTextField();
		txtNextCallInterval.setColumns(2);
		txtNextCallInterval.setName("NextCallInterval");
		txtNextCallInterval.addActionListener(programStageViewModel);
		txtNextCallInterval.addFocusListener(programStageViewModel);
		txtNextCallInterval.addKeyListener(programStageViewModel);
		txtNextCallInterval.setHorizontalAlignment(SwingConstants.RIGHT);

		GridBagConstraints gbc_txtNexttCallInterval = new GridBagConstraints();
		gbc_txtNexttCallInterval.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNexttCallInterval.insets = new Insets(0, 0, 0, 5);
		gbc_txtNexttCallInterval.gridx = 9;
		gbc_txtNexttCallInterval.gridy = 1;

		add(txtNextCallInterval, gbc_txtNexttCallInterval);

		JLabel lblMin = new JLabel("min");
		GridBagConstraints gbc_lblMin = new GridBagConstraints();
		gbc_lblMin.insets = new Insets(0, 0, 0, 5);
		gbc_lblMin.gridx = 10;
		gbc_lblMin.gridy = 1;
		add(lblMin, gbc_lblMin);
		
		txtStageTitle.setText(stage.getTitle());
		txtTeamSize.setText("" + stage.getTeamSize());
		txtFirstCall.setText(stage.getFirstCall());
		txtNextCallInterval.setText("" + stage.getNextCallInterval());
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.debug("Mouse click " + e.getX() + "," + e.getY());
				if (SwingUtilities.isRightMouseButton(e)) {
					handleMouseClicked(e.getX(), e.getY());
				}
			}
		});
	}

	void handleMouseClicked(int x, int y) {
		if (contextMenu == null) {
			logger.warn("Context menu is null.");
			return;
		}
		
		contextMenu.show(this, x, y);
	}
	
	public void addPanel(JPanel panel) {
		add(panel);
		revalidate();
	}
	
}
