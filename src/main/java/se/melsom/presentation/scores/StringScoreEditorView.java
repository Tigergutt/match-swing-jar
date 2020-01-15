package se.melsom.presentation.scores;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class StringScoreEditorView extends JPanel {
	private static Logger logger = Logger.getLogger(StringScoreEditorView.class);	
	
	public StringScoreEditorView(Vector<StringScoreViewModel> stringScoreViewModels) {
		int teamSize = stringScoreViewModels.size();
		if (teamSize == 0) {
			logger.warn("Team size=0");
			return;
		}
		int shotCount = stringScoreViewModels.get(0).getShotScoreModels().size();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[shotCount + 3];
		gridBagLayout.rowHeights = new int[teamSize + 1];
		gridBagLayout.columnWeights = new double[shotCount + 3];
		gridBagLayout.rowWeights = new double[teamSize + 1];
		
		int columnIndex = 0;
		
		gridBagLayout.columnWidths[columnIndex] = 145;
		gridBagLayout.columnWeights[columnIndex] = 0;
		
		for (int shotIndex = 0; shotIndex < shotCount; shotIndex++) {
			gridBagLayout.columnWidths[++columnIndex] =  0;
			gridBagLayout.columnWeights[columnIndex] = 0;
		}
		
		gridBagLayout.columnWidths[++columnIndex] = 0;
		gridBagLayout.columnWeights[columnIndex] = Double.MIN_VALUE;
		
		for (int rowIndex = 0; rowIndex < teamSize; rowIndex++) {
			gridBagLayout.rowHeights[rowIndex] = 0;
			gridBagLayout.rowWeights[rowIndex] = 0;
		}

		gridBagLayout.rowHeights[teamSize] = 0;
		gridBagLayout.rowWeights[teamSize] = Double.MIN_VALUE;
		
		setLayout(gridBagLayout);

		columnIndex = 0;
		
		JLabel lblShooterNameHeader = new JLabel("Skytt");
		lblShooterNameHeader.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblShooterNameHeader = new GridBagConstraints();
		gbc_lblShooterNameHeader.insets = new Insets(0, 0, 0, 5);
		gbc_lblShooterNameHeader.gridx = columnIndex++;
		gbc_lblShooterNameHeader.gridy = 0;
		gbc_lblShooterNameHeader.anchor = GridBagConstraints.LINE_START;
		gbc_lblShooterNameHeader.fill = GridBagConstraints.HORIZONTAL;
		add(lblShooterNameHeader, gbc_lblShooterNameHeader);
		
		JLabel lblLaneHeader = new JLabel("Bana");
		lblLaneHeader.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblLaneHeader = new GridBagConstraints();
		gbc_lblLaneHeader.insets = new Insets(0, 0, 0, 5);
		gbc_lblLaneHeader.gridx = columnIndex++;
		gbc_lblLaneHeader.gridy = 0;
		gbc_lblLaneHeader.fill = GridBagConstraints.HORIZONTAL;
		add(lblLaneHeader, gbc_lblLaneHeader);

		for (int shotIndex = 0; shotIndex < shotCount; shotIndex++) {
			JLabel lblShotNumber = new JLabel("" + (shotIndex + 1));
			GridBagConstraints gbc_lblShotNumber = new GridBagConstraints();
			gbc_lblShotNumber.insets = new Insets(0, 0, 0, 5);
			gbc_lblShotNumber.gridx = columnIndex++;
			gbc_lblShotNumber.gridy = 0;
			add(lblShotNumber, gbc_lblShotNumber);
		}
		
		JLabel lblTotalScore = new JLabel("Totalt");
		GridBagConstraints gbc_lblTotalScore = new GridBagConstraints();
		gbc_lblTotalScore.gridx = columnIndex++;
		gbc_lblTotalScore.gridy = 0;
		add(lblTotalScore, gbc_lblTotalScore);
		
		int rowIndex = 1;
		for (StringScoreViewModel stringScoreViewModel : stringScoreViewModels) {
			columnIndex = 0;
			
			JLabel lblShooterName = new JLabel(stringScoreViewModel.getShooterName());
			lblShooterName.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_lblShooterName = new GridBagConstraints();
			gbc_lblShooterName.insets = new Insets(0, 0, 0, 5);
			gbc_lblShooterName.gridx = columnIndex++;
			gbc_lblShooterName.gridy = rowIndex;
			gbc_lblShooterName.anchor = GridBagConstraints.LINE_START;
			gbc_lblShooterName.fill = GridBagConstraints.HORIZONTAL;
			add(lblShooterName, gbc_lblShooterName);
			
			JLabel lblLane = new JLabel(stringScoreViewModel.getLaneNumber());
			lblLane.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_lblLane = new GridBagConstraints();
			gbc_lblLane.insets = new Insets(0, 0, 0, 5);
			gbc_lblLane.gridx = columnIndex++;
			gbc_lblLane.gridy = rowIndex;
			gbc_lblLane.fill = GridBagConstraints.HORIZONTAL;
			add(lblLane, gbc_lblLane);

			for (ShotScoreViewModel shotScoreViewModel : stringScoreViewModel.getShotScoreModels()) {
				JTextField textField = new JTextField(1);
				
				textField.setText("0");
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(0, 0, 0, 5);
				gbc_textField.gridx = columnIndex++;
				gbc_textField.gridy = rowIndex;
				add(textField, gbc_textField);
				textField.setText(shotScoreViewModel.getValueSymbol());
				textField.addActionListener(shotScoreViewModel);
				textField.addFocusListener(shotScoreViewModel);
				textField.addKeyListener(shotScoreViewModel);
			}
					
			JLabel lblTotal = new JLabel("99");
			lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblTotal = new GridBagConstraints();
			gbc_lblTotal.insets = new Insets(0, 0, 0, 5);
			gbc_lblTotal.gridx = columnIndex++;
			gbc_lblTotal.gridy = rowIndex;
			add(lblTotal, gbc_lblTotal);
			
			stringScoreViewModel.setTotalScore(lblTotal);
			rowIndex++;
		}
	}

}
