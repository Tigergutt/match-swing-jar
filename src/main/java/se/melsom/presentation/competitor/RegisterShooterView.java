package se.melsom.presentation.competitor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.melsom.model.shooter.Shooter;
import se.melsom.model.shooter.ShooterField;
import se.melsom.presentation.competitor.selector.ShooterSelector;
import se.melsom.presentation.competitor.selector.ShooterState;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class RegisterShooterView extends JPanel  {
//	private static Logger logger = Logger.getLogger(RegisterShooterView.class);
	
//	private RegisterShooterViewModel viewModel;
	private ShooterSelector cmbForename;
	private ShooterSelector cmbSurname;
	private JTextField txtUnit;
	private JButton btnControl;
	private JButton btnClear;
	private ShooterState state = ShooterState.EMPTY;
	private JCheckBox cbxLefty;
//	private Shooter currentShooter = null;
	
	public RegisterShooterView(RegisterShooterViewModel viewModel) {
		setBorder(new LineBorder(Color.MAGENTA));
//		this.viewModel = viewModel;
		
//		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//		setClosable(true);
		
//		setBounds(10, 10, 380, 210);
		
		JPanel panel = new JPanel();
		/*getContentPane().*/add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0};
		gbl_panel.rowHeights = new int[] {30, 30, 30, 0, 30, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Förnamn");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 20, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		cmbForename = new ShooterSelector(ShooterField.FORENAME, viewModel);
		GridBagConstraints gbc_cmbForename = new GridBagConstraints();
		gbc_cmbForename.insets = new Insets(0, 0, 5, 10);
		gbc_cmbForename.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbForename.gridx = 1;
		gbc_cmbForename.gridy = 0;
		panel.add(cmbForename, gbc_cmbForename);
		
		JLabel lblNewLabel_1 = new JLabel("Efternamn");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		cmbSurname = new ShooterSelector(ShooterField.SURNAME, viewModel);
		GridBagConstraints gbc_cmbSurname = new GridBagConstraints();
		gbc_cmbSurname.insets = new Insets(0, 0, 5, 10);
		gbc_cmbSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbSurname.gridx = 1;
		gbc_cmbSurname.gridy = 1;
		panel.add(cmbSurname, gbc_cmbSurname);
		
		JLabel lblNewLabel_2 = new JLabel("Förband");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtUnit = new JTextField();
		txtUnit.setActionCommand("UnitName");
		txtUnit.addKeyListener(viewModel);
//		txtUnit.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				fieldEdited();
//			}
//		});
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 10);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		panel.add(txtUnit, gbc_textField_2);
		txtUnit.setColumns(10);
		
		cbxLefty = new JCheckBox("Vänsterskytt");
		cbxLefty.setActionCommand("IsLefty");
		cbxLefty.addActionListener(viewModel);
		GridBagConstraints gbc_cbxLefty = new GridBagConstraints();
		gbc_cbxLefty.anchor = GridBagConstraints.WEST;
		gbc_cbxLefty.insets = new Insets(0, 0, 5, 0);
		gbc_cbxLefty.gridx = 1;
		gbc_cbxLefty.gridy = 3;
		panel.add(cbxLefty, gbc_cbxLefty);
		
		JPanel panelControl = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelControl.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panelControl = new GridBagConstraints();
		gbc_panelControl.fill = GridBagConstraints.BOTH;
		gbc_panelControl.gridx = 1;
		gbc_panelControl.gridy = 4;
		panel.add(panelControl, gbc_panelControl);
		
		btnClear = new JButton("Rensa");
		btnClear.setActionCommand("ClearShooter");
		btnClear.addActionListener(viewModel);
//		btnClear.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				clearAction();
//			}
//		});
		btnClear.setEnabled(false);
		panelControl.add(btnClear);
		
		btnControl = new JButton("Lägg till");
		btnControl.setActionCommand("AddShooter");
		panelControl.add(btnControl);
		btnControl.addActionListener(viewModel);
//		btnControl.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				controlAction();
//			}
//		});
		btnControl.setEnabled(false);
		
		setState(ShooterState.EMPTY);
	}
	
	public String getUnit() {
		return txtUnit.getText();
	}

	public void setUnit(String unit) {
		txtUnit.setText(unit);
	}

	public String getSurname() {
		return cmbSurname.getText();
	}
	
	public void setSurname(String surname) {
		cmbSurname.setText(surname);
	}

	public String getForename() {
		return cmbForename.getText();
	}
	
	public void setForename(String forename) {
		cmbForename.setText(forename);
	}

	public ShooterState getState() {
		return state;
	}
	
	public void setShooterData(Shooter shooter) {
		cmbForename.setText(shooter.getForename());
		cmbSurname.setText(shooter.getSurname());
		String organization = "";
		
		if (shooter.getUnit() != null) {
			organization = shooter.getUnit().getName();
		}
		
		if (shooter.getGroup() != null && organization.length() == 0) {
			organization = shooter.getGroup().getShortName();
		}
		
		txtUnit.setText(organization);
		cbxLefty.setSelected(shooter.isLefthanded());
	}

//	@Override
//	public void shooterSelected(Shooter shooter) {
//		cmbForename.setText(shooter.getForename());
//		cmbSurname.setText(shooter.getSurname());
//		txtUnit.setText(shooter.getUnit());
//		cbxLefty.setSelected(shooter.isLefthanded());
//		
//		currentShooter = shooter;
//		
//		setState(ShooterState.SELECTED);
//	}

//	@Override
//	public void fieldEdited() {
//		setState(ShooterState.EDIT);
//
//		if (txtUnit.getText().length() == 0) {
//			return;
//		}
//		
//		if (cmbSurname.getText().length() == 0) {
//			return;
//		}
//		
//		if (cmbForename.getText().length() == 0) {
//			return;
//		}
//		
//		setState(ShooterState.NEW);
//	}

	public void setState(ShooterState state) {
		switch (state) {
		case EMPTY:
			cmbForename.setText("");
			cmbSurname.setText("");
			txtUnit.setText("");
			cbxLefty.setSelected(false);
			btnControl.setText("Lägg till");
			btnControl.setEnabled(false);
			btnClear.setEnabled(false);
			break;
			
		case EDIT:
			btnClear.setEnabled(true);
			break;
			
		case NEW:
			btnControl.setText("Lägg till");
			btnControl.setEnabled(true);
			break;
			
		case SELECTED:
			btnControl.setText("Anmäl");
			btnControl.setEnabled(true);
			break;
			
		case REGISTERED:
			btnControl.setText("Avanmäl");
			btnControl.setEnabled(true);
			break;
		}
		
		this.state = state;
	}
	
//	void clearAction() {
//		setState(ShooterState.EMPTY);
//	}

//	void controlAction() {
//		switch (state) {
//		case NEW:
//			// TODO: Implement create new Shooter
//			logger.debug("New Shooter.");
//			viewModel.addCompetitor(currentShooter);
//			break;
//			
//		case SELECTED:
//			// TODO: Implement register Shooter
//			logger.debug("Register Shooter " + cmbForename.getText() + "," + cmbSurname.getText());
//			viewModel.addCompetitor(currentShooter);
//			break;
//			
//		case REGISTERED:
//			// TODO: Implement un-register Shooter
//			logger.warn("Un-register Shooter not yet implemented!");
//			break;
//			
//		default:
//			break;
//		}
//		
//		setState(ShooterState.EMPTY);
//	}

}
