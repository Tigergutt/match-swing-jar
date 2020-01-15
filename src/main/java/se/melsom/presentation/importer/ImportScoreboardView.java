package se.melsom.presentation.importer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ImportScoreboardView extends JFrame {
//	private Logger logger = Logger.getLogger(this.getClass());

	private JPanel contentPane;
	private JTable table;
	private JTextField textPath;
	private JTextField textFrom;
	private JTextField textName;
	private JTextField textUnit;
	private JTextField textScore;
	private JTextField textTo;
	private JButton btnOpen;
	private JButton btnOk;
	private JButton btnCancel;
	private JButton btnUpdate;

	public ImportScoreboardView(ImportScoreboardViewModel presenter) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 654, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelControl = new JPanel();
		contentPane.add(panelControl, BorderLayout.NORTH);
		panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.PAGE_AXIS));
		
		JPanel panelFile = new JPanel();
		panelControl.add(panelFile);
		
		JLabel lblNewLabel = new JLabel("Importera resultat från:");
		panelFile.add(lblNewLabel);
		
		textPath = new JTextField();
		panelFile.add(textPath);
		textPath.setColumns(30);
		
		btnOpen = new JButton("...");
		panelFile.add(btnOpen);
		
		JPanel panelRowsAndColumns = new JPanel();
		panelRowsAndColumns.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Importera rader och kolumner", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelControl.add(panelRowsAndColumns);
		GridBagLayout gbl_panelRowsAndColumns = new GridBagLayout();
		gbl_panelRowsAndColumns.columnWidths = new int[] {0, 0, 0, 0};
		gbl_panelRowsAndColumns.rowHeights = new int[]{28, 28, 28, 0};
		gbl_panelRowsAndColumns.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelRowsAndColumns.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelRowsAndColumns.setLayout(gbl_panelRowsAndColumns);
		
		JLabel lblFrom = new JLabel("Från och med:");
		GridBagConstraints gbc_lblFrom = new GridBagConstraints();
		gbc_lblFrom.insets = new Insets(0, 10, 5, 5);
		gbc_lblFrom.anchor = GridBagConstraints.EAST;
		gbc_lblFrom.gridx = 0;
		gbc_lblFrom.gridy = 0;
		panelRowsAndColumns.add(lblFrom, gbc_lblFrom);
		
		textFrom = new JTextField();
		textFrom.setText("1");
		GridBagConstraints gbc_textFrom = new GridBagConstraints();
		gbc_textFrom.insets = new Insets(0, 0, 5, 5);
		gbc_textFrom.anchor = GridBagConstraints.WEST;
		gbc_textFrom.gridx = 1;
		gbc_textFrom.gridy = 0;
		panelRowsAndColumns.add(textFrom, gbc_textFrom);
		textFrom.setColumns(3);
		
		JLabel lblTo = new JLabel("Till och med:");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.anchor = GridBagConstraints.EAST;
		gbc_lblTo.insets = new Insets(0, 10, 0, 5);
		gbc_lblTo.gridx = 0;
		gbc_lblTo.gridy = 1;
		panelRowsAndColumns.add(lblTo, gbc_lblTo);
		
		textTo = new JTextField();
		textTo.setText("1");
		textTo.setColumns(3);
		GridBagConstraints gbc_textTo = new GridBagConstraints();
		gbc_textTo.anchor = GridBagConstraints.WEST;
		gbc_textTo.insets = new Insets(0, 0, 5, 0);
		gbc_textTo.gridx = 1;
		gbc_textTo.gridy = 1;
		panelRowsAndColumns.add(textTo, gbc_textTo);
		
		JLabel lblName = new JLabel("Namn:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 20, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 2;
		gbc_lblName.gridy = 0;
		panelRowsAndColumns.add(lblName, gbc_lblName);
		
		textName = new JTextField();
		textName.setText("1");
		textName.setColumns(3);
		GridBagConstraints gbc_textName = new GridBagConstraints();
		gbc_textName.insets = new Insets(0, 0, 5, 0);
		gbc_textName.anchor = GridBagConstraints.WEST;
		gbc_textName.gridx = 3;
		gbc_textName.gridy = 0;
		panelRowsAndColumns.add(textName, gbc_textName);
		
		JLabel lblUnit = new JLabel("Förband:");
		GridBagConstraints gbc_lblUnit = new GridBagConstraints();
		gbc_lblUnit.insets = new Insets(0, 20, 5, 5);
		gbc_lblUnit.anchor = GridBagConstraints.EAST;
		gbc_lblUnit.gridx = 2;
		gbc_lblUnit.gridy = 1;
		panelRowsAndColumns.add(lblUnit, gbc_lblUnit);
		
		textUnit = new JTextField();
		textUnit.setText("2");
		textUnit.setColumns(3);
		GridBagConstraints gbc_textUnit = new GridBagConstraints();
		gbc_textUnit.insets = new Insets(0, 0, 5, 0);
		gbc_textUnit.anchor = GridBagConstraints.WEST;
		gbc_textUnit.gridx = 3;
		gbc_textUnit.gridy = 1;
		panelRowsAndColumns.add(textUnit, gbc_textUnit);
		
		JLabel lblScore = new JLabel("Poäng:");
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.anchor = GridBagConstraints.EAST;
		gbc_lblScore.insets = new Insets(0, 20, 5, 5);
		gbc_lblScore.gridx = 2;
		gbc_lblScore.gridy = 2;
		panelRowsAndColumns.add(lblScore, gbc_lblScore);
		
		textScore = new JTextField();
		textScore.setText("3");
		textScore.setColumns(3);
		GridBagConstraints gbc_textScore = new GridBagConstraints();
		gbc_textScore.insets = new Insets(0, 0, 5, 0);
		gbc_textScore.anchor = GridBagConstraints.WEST;
		gbc_textScore.gridx = 3;
		gbc_textScore.gridy = 2;
		panelRowsAndColumns.add(textScore, gbc_textScore);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(presenter.getTableModel());
		scrollPane.setViewportView(table);
		
		JPanel panelButtons = new JPanel();
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panelButtons, BorderLayout.SOUTH);
		
		btnOk = new JButton("Ok");
		btnCancel = new JButton("Avbryt");
		
		panelButtons.add(btnCancel);
		
		btnUpdate = new JButton("Uppdatera");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presenter.update();
			}
		});
		panelButtons.add(btnUpdate);
		panelButtons.add(btnOk);
	}
	
	public void setPath(String path) {
		textPath.setText(path);
	}
	
	public int getFirstRowNumber() {
		return Integer.parseInt(textFrom.getText());
	}

	public int getLastRowNumber() {
		return Integer.parseInt(textTo.getText());
	}

	public void setLastRowNumber(int value) {
		textTo.setText("" + value);
	}

	public int getNameColumnNumber() {
		return Integer.parseInt(textName.getText());
	}

	public int getUnitColumnNumber() {
		return Integer.parseInt(textUnit.getText());
	}

	public int getScoreColumnNumber() {
		return Integer.parseInt(textScore.getText());
	}
	
//  TODO: FIX THIS!
//	@Override
//	public void setInteractor(String name, ButtonInteractor interactor) {
//		switch (name) {
//		case "Open":
//			btnOpen.addActionListener(interactor);
//			break;
//			
//		case "Ok":
//			btnOk.addActionListener(interactor);
//			break;
//
//		case "Cancel":
//			btnCancel.addActionListener(interactor);
//			break;
//
//		default:
//			logger.warn("Unknown button " + name);
//			break;
//		}
//	}
//
}
