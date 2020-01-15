package se.melsom.presentation.program;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import se.melsom.presentation.ViewModel;

@SuppressWarnings("serial")
public class MatchProgramView extends JPanel {
	private Logger logger = Logger.getLogger(this.getClass());
	private JPanel panelMatch = new JPanel();
	private MatchProgramContextMenu contextMenu;

	private JTextField txtMatchTitle = new JTextField();
	private JTextField txtMatchDate = new JTextField();
	private JPanel stagesPanel = new JPanel();

	public MatchProgramView(ViewModel viewModel) {
		setBorder(new LineBorder(Color.MAGENTA));
//		setClosable(true);
//		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//		setIconifiable(true);
		
//		setResizable(true);
//		setTitle("Matchprogram");
//		setBounds(10, 10, 604, 300);
		/*getContentPane().*/setLayout(new BorderLayout(0, 0));
		
		int gridY = 0;
				
//		panelMatch = new JPanel();
//		panelMatch.setMinimumSize(new Dimension(400, 80));
//		panelMatch.setMaximumSize(new Dimension(400, 80));
//		panelMatch.setSize(new Dimension(400, 80));

		/*getContentPane().*/add(panelMatch, BorderLayout.NORTH);
		panelMatch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.debug("Mouse click " + e.getX() + "," + e.getY());
				if (contextMenu == null) {
					logger.warn("Context menu is null.");
					return;
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					contextMenu.show(panelMatch, e.getX(), e.getY());
				}
			}
		});
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		panelMatch.setLayout(gbl_panel);
		
		JLabel lblName = new JLabel("Namn");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = gridY;
		panelMatch.add(lblName, gbc_lblName);
		
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.anchor = GridBagConstraints.WEST;
		gbc_txtName.gridwidth = 4;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.gridx = 2;
		gbc_txtName.gridy = gridY;
		txtMatchTitle.setName("MatchTitle");
		txtMatchTitle.addActionListener(viewModel);
		txtMatchTitle.addFocusListener(viewModel);

		panelMatch.add(txtMatchTitle, gbc_txtName);
		txtMatchTitle.setColumns(15);
				
		JLabel lblDate = new JLabel("Datum");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(0, 0, 0, 5);
		gbc_lblDate.gridx = 1;
		gbc_lblDate.gridy = gridY + 1;
		panelMatch.add(lblDate, gbc_lblDate);
		
		GridBagConstraints gbc_txtDate = new GridBagConstraints();
		gbc_txtDate.anchor = GridBagConstraints.WEST;
		gbc_txtDate.gridwidth = 2;
		gbc_txtDate.insets = new Insets(0, 0, 0, 5);
		gbc_txtDate.gridx = 2;
		gbc_txtDate.gridy = gridY + 1;
		txtMatchDate.setName("MatchDate");
		txtMatchDate.addActionListener(viewModel);
		txtMatchDate.addFocusListener(viewModel);
		panelMatch.add(txtMatchDate, gbc_txtDate);
		txtMatchDate.setColumns(8);

		stagesPanel.setBorder(new LineBorder(Color.RED));
		stagesPanel.setLayout(new BoxLayout(stagesPanel, BoxLayout.Y_AXIS));
		stagesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		stagesPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.debug("Mouse click " + e.getX() + "," + e.getY());
				if (contextMenu == null) {
					logger.warn("Context menu is null.");
					return;
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					contextMenu.show(stagesPanel, e.getX(), e.getY());
				}
			}
		});

//		/*getContentPane().*/add(new JScrollPane(stagesPanel), BorderLayout.CENTER);
		add(stagesPanel, BorderLayout.CENTER);
	}

	public void setMatchTitle(String text) {
		txtMatchTitle.setText(text);
	}
	
	public void setMatchDate(String text) {
		txtMatchDate.setText(text);
	}
	
	public void clear() {
		setMatchTitle("");
		setMatchDate("");
		stagesPanel.removeAll();
	}
	
	public void addPanel(JPanel panel) {
		stagesPanel.add(panel);
		revalidate();
	}
	
	public void setContextMenu(MatchProgramContextMenu contextMenu) {
		this.contextMenu = contextMenu;
	}

}
