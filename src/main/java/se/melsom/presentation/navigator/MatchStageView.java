package se.melsom.presentation.navigator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class MatchStageView extends JPanel {
	private static Logger logger = Logger.getLogger(MatchStageView.class);
	private JTable table;
	private JButton btnCreateTeams;
	private JButton btnScrambleList;
	private JButton btnMoveSelectedUp;
	private JButton btnMoveSelectedDown;
	private int teamSize = 10;

	static final Color BLUEISH_COLOR = new Color(226, 238, 253);
	static final Color GRAYISH_COLOR = new Color(247, 247, 247);

	private CompetitorTableModel tableModel = new CompetitorTableModel();
	
	public MatchStageView() {
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tableModel) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				if (getSelectedRow() != row) {
					if ((row / teamSize) % 2 == 0) {
						c.setBackground(BLUEISH_COLOR);
					} else {
						c.setBackground(GRAYISH_COLOR);
					}
				}

				return c;
			}
		};

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(20);
		table.getColumnModel().getColumn(CompetitorTableModel.NAME_COLUMN).setPreferredWidth(100);
		table.getColumnModel().getColumn(CompetitorTableModel.UNIT_COLUMN).setPreferredWidth(50);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				handleRowSelectedAction();
			}
		});

		scrollPane.setViewportView(table);

		JPanel controlPanel = new JPanel(new FlowLayout());
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(controlPanel, BorderLayout.SOUTH);


		btnCreateTeams = new JButton("Dela in skjutlag");
		btnCreateTeams.setName("CreateTeams");
		controlPanel.add(btnCreateTeams);

		btnScrambleList = new JButton("Blanda");
		btnScrambleList.setEnabled(false);
		btnScrambleList.setName("ShuffleList");
		controlPanel.add(btnScrambleList);

		btnMoveSelectedUp = new JButton("Upp");
		btnMoveSelectedUp.setEnabled(false);
		btnMoveSelectedUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleMoveSelectedUpAction();
			}
		});
		controlPanel.add(btnMoveSelectedUp);

		btnMoveSelectedDown = new JButton("Ner");
		btnMoveSelectedDown.setEnabled(false);
		btnMoveSelectedDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleMoveSelectedDownAction();
			}
		});

		controlPanel.add(btnMoveSelectedDown);
	}

	public void setViewModel(MatchStageViewModel viewModel) {	
		ActionListener[] listeners = btnCreateTeams.getActionListeners();
		
		for (ActionListener listener : listeners) {
			btnCreateTeams.removeActionListener(listener);
		}
		
		btnCreateTeams.addActionListener(viewModel);
		
		listeners = btnScrambleList.getActionListeners();
		
		for (ActionListener listener : listeners) {
			btnScrambleList.removeActionListener(listener);
		}

		btnScrambleList.addActionListener(viewModel);
	}
	
	public CompetitorTableModel getCompetitorTableModel() {
		return tableModel;
	}
	
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public void setCreateTeamsEnabled(boolean enabled) {
		btnCreateTeams.setEnabled(enabled);
	}
	
	public void setScrambleListEnabled(boolean enabled) {
		btnScrambleList.setEnabled(enabled);
	}
	
	private void handleRowSelectedAction() {
		int viewRow = table.getSelectedRow();

		btnMoveSelectedUp.setEnabled(false);
		btnMoveSelectedDown.setEnabled(false);

		if (viewRow > -1) {
			int modelRow = table.convertRowIndexToModel(viewRow);
			
			logger.trace("Row " + modelRow + " is selected.");
			btnMoveSelectedUp.setEnabled(modelRow > 0);
			btnMoveSelectedDown.setEnabled(modelRow < table.getRowCount() - 1);
		}
	}
	
	private void handleMoveSelectedUpAction() {
		logger.debug("handleMoveSelectedUpAction");
		int selectedRow = table.getSelectedRow();
		
		if (selectedRow > -1) {
			if (selectedRow > 0) {
				tableModel.move(selectedRow, selectedRow - 1);
				table.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
			}
		}
	}

	private void handleMoveSelectedDownAction() {
		logger.debug("handleMoveSelectedDownAction");
		int selectedRow = table.getSelectedRow();
		
		if (selectedRow > -1) {
			if (selectedRow < table.getRowCount()) {
				tableModel.move(selectedRow, selectedRow + 1);
				table.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
			}
		}
	}
	
}
