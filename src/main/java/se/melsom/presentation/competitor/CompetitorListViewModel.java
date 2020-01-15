package se.melsom.presentation.competitor;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import se.melsom.application.ApplicationController;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventListener;
import se.melsom.model.competitor.Competitor;
import se.melsom.model.competitor.CompetitorList;
import se.melsom.model.program.ProgramStage;

@SuppressWarnings("serial")
public class CompetitorListViewModel extends AbstractTableModel implements ComponentListener, ModelEventListener {
	private static Logger logger = Logger.getLogger(CompetitorListViewModel.class);
	
	private ApplicationController controller;
	private CompetitorListView competitorListView;
	private static String[] columnNames = { "Namn", "Förband"};
	
	private ProgramStage stage = null;

	static final int NAME_COLUMN = 0;
	static final int UNIT_COLUMN = 1;
	
	public CompetitorListViewModel(ApplicationController controller) {
		this.controller = controller;
		competitorListView = new CompetitorListView(this);
	}
	
	public JInternalFrame getInternalFrame() {
		return competitorListView;
	}


	public void setStage(ProgramStage stage) {
		this.stage = stage;
		
		competitorListView.setTitle("Deltagarlista: " + stage.getTitle());
		competitorListView.setTeamSize(stage.getTeamSize());
		fireTableDataChanged();
	}

	public void showView() {
		logger.debug("showView()");
		if (competitorListView.isVisible()) {
			if (competitorListView.isIcon()) {
				try {
					competitorListView.setIcon(false);
				} catch (PropertyVetoException e) {
					logger.error("showView()", e);
				}
			} else {
				competitorListView.setVisible(false);
			}
		} else {
			competitorListView.setVisible(true);
		}
	}

	
	public int size() {
		if (stage == null) {
			logger.warn("Program stage is null!");
			return 0;
		}
		
		CompetitorList competitorList = stage.getCompetitorList();

		return competitorList.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (stage == null) {
			logger.error("Program stage is null!");
			return null;
		}
		
		Competitor competitor = stage.getCompetitorList().get(rowIndex);
		
		if (competitor == null) {
			logger.error("No competitioner at row=" + rowIndex);
		}

		switch (columnIndex) {
		case NAME_COLUMN:
			return competitor.getName();

		case UNIT_COLUMN:
			return competitor.getUnit();

		}

		return null;
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		controller.setCompetitorListChecked(false);
	}

	@Override
	public void handleEvent(ModelEvent event) {
	}
	
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

}
