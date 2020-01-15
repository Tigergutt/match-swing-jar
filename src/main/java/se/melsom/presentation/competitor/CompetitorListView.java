package se.melsom.presentation.competitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class CompetitorListView extends JInternalFrame {
	private static Logger logger = Logger.getLogger(CompetitorListView.class);
	
	private JTable table;
	private int teamSize = 10;

	static final Color BLUEISH_COLOR = new Color(226, 238, 253);
	static final Color GRAYISH_COLOR = new Color(247, 247, 247);


	public CompetitorListView(CompetitorListViewModel viewModel) {
		addComponentListener(viewModel);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setBounds(500, 10, 300, 400);
		setTitle("Deltagarlista: ");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(viewModel) {
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
		table.getColumnModel().getColumn(CompetitorListViewModel.NAME_COLUMN).setPreferredWidth(100);
		table.getColumnModel().getColumn(CompetitorListViewModel.UNIT_COLUMN).setPreferredWidth(50);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int viewRow = table.getSelectedRow();
				if (viewRow > -1) {
					int modelRow = table.convertRowIndexToModel(viewRow);

					logger.trace("Row " + modelRow + " is selected.");
				}
			}
		});

		scrollPane.setViewportView(table);

//		JPanel controlPanel = new JPanel();
//		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
//		flowLayout.setAlignment(FlowLayout.RIGHT);
//		getContentPane().add(controlPanel, BorderLayout.SOUTH);
//
//		btnScrambleList = new JButton("Blanda");
//		btnScrambleList.setEnabled(false);
//		btnScrambleList.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				viewModel.shuffle();
//			}
//		});
//		controlPanel.add(btnScrambleList);
//
//		btnMoveSelectedUp = new JButton("Upp");
//		btnMoveSelectedUp.setEnabled(false);
//		btnMoveSelectedUp.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int selectedRow = table.getSelectedRow();
//				
//				if (selectedRow > -1) {
//					if (selectedRow > 0) {
//						viewModel.move(selectedRow, selectedRow - 1);
//						table.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
//					}
//				}
//			}
//		});
//		controlPanel.add(btnMoveSelectedUp);
//
//		btnMoveSelectedDown = new JButton("Ner");
//		btnMoveSelectedDown.setEnabled(false);
//		btnMoveSelectedDown.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int selectedRow = table.getSelectedRow();
//				
//				if (selectedRow > -1) {
//					if (selectedRow < viewModel.size()) {
//						viewModel.move(selectedRow, selectedRow + 1);
//						table.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
//					}
//				}
//			}
//		});
//		controlPanel.add(btnMoveSelectedDown);
	}
	
	public int getTeamSize() {
		return teamSize;
	}
	
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
}
