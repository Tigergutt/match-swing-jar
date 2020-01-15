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

import se.melsom.model.program.ProgramString;
import se.melsom.presentation.ViewModel;

@SuppressWarnings("serial")
public class ProgramStringView extends JPanel {
	private static Logger logger = Logger.getLogger(ProgramStringView.class);
	private ProgramStringContextMenu contextMenu;
	private JTextField txtShotCount;
	private JTextField txtStringTitle;
	private JLabel lblStringNumber;

	public ProgramStringView(ViewModel viewModel, ProgramString string, ProgramStringContextMenu contextMenu) {
		this.contextMenu = contextMenu;
		
		setMaximumSize(new Dimension(32767, 30));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		gridBagLayout.columnWidths = new int[] {5, 20, 200, 30, 40, 0, 0};
		setLayout(gridBagLayout);

		txtStringTitle = new JTextField();
		txtStringTitle.setName("StringTitle");
		txtStringTitle.addFocusListener(viewModel);
		txtStringTitle.addActionListener(viewModel);
//		txtStringTitle.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusLost(FocusEvent e) {
//				logger.debug("String title:" + txtStringTitle.getText());
//				string.setTitle(txtStringTitle.getText());
//			}
//		});
//		txtStringTitle.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				logger.debug("String title:" + txtStringTitle.getText());
//				string.setTitle(txtStringTitle.getText());
//			}
//		});
		
		lblStringNumber = new JLabel("N");
		GridBagConstraints gbc_lblStringNumber = new GridBagConstraints();
		gbc_lblStringNumber.anchor = GridBagConstraints.EAST;
		gbc_lblStringNumber.insets = new Insets(0, 0, 0, 5);
		gbc_lblStringNumber.gridx = 1;
		gbc_lblStringNumber.gridy = 0;
		add(lblStringNumber, gbc_lblStringNumber);
		txtStringTitle.setColumns(10);

		GridBagConstraints gbc_txtStringTitle = new GridBagConstraints();
		gbc_txtStringTitle.insets = new Insets(0, 0, 0, 5);
		gbc_txtStringTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStringTitle.gridx = 2;
		gbc_txtStringTitle.gridy = 0;
		add(txtStringTitle, gbc_txtStringTitle);

		txtShotCount = new JTextField();
		txtShotCount.setName("ShotCount");
		txtShotCount.addFocusListener(viewModel);
		txtShotCount.addActionListener(viewModel);
		txtShotCount.addKeyListener(viewModel);
//		txtShotCount.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusLost(FocusEvent event) {
//				logger.debug("Shot count:" + txtShotCount.getText());
//				try {
//					string.setShotCount(Integer.parseInt(txtShotCount.getText()));
//				} catch (Exception e) {
//				}
//			}
//		});
//		txtShotCount.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent event) {
//				logger.debug("Shot count:" + txtShotCount.getText());
//				try {
//					string.setShotCount(Integer.parseInt(txtShotCount.getText()));
//				} catch (Exception e) {
//				}
//			}
//		});
//		txtShotCount.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent event) {
//				try {
//					Integer.parseInt("" + event.getKeyChar());
//				} catch (Exception e) {
//					event.consume();
//				}
//			}
//		});
		txtShotCount.setColumns(2);
		txtShotCount.setHorizontalAlignment(SwingConstants.RIGHT);

		GridBagConstraints gbc_txtShotCount = new GridBagConstraints();
		gbc_txtShotCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtShotCount.insets = new Insets(0, 0, 0, 5);
		gbc_txtShotCount.gridx = 4;
		gbc_txtShotCount.gridy = 0;
		add(txtShotCount, gbc_txtShotCount);

		JLabel lblShotCount = new JLabel("skott");
		GridBagConstraints gbc_lblAntalSkott = new GridBagConstraints();
		gbc_lblAntalSkott.insets = new Insets(0, 0, 0, 5);
		gbc_lblAntalSkott.anchor = GridBagConstraints.WEST;
		gbc_lblAntalSkott.gridx = 5;
		gbc_lblAntalSkott.gridy = 0;
		add(lblShotCount, gbc_lblAntalSkott);

		lblStringNumber.setText("" + (string.getIndex() + 1));
		txtStringTitle.setText(string.getTitle());
		txtShotCount.setText("" + string.getShotCount());

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
}
