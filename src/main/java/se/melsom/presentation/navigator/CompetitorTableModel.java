package se.melsom.presentation.navigator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import se.melsom.model.competitor.Competitor;
import se.melsom.model.competitor.CompetitorList;

@SuppressWarnings("serial")
public class CompetitorTableModel extends AbstractTableModel {
	private static Logger logger = Logger.getLogger(CompetitorTableModel.class);
	private static String[] columnNames = { "Namn", "Förband"};

	static final int NAME_COLUMN = 0;
	static final int UNIT_COLUMN = 1;
	
	private CompetitorList competitors;
	
	public CompetitorTableModel() {
	}
	
	public CompetitorList getCompetitors() {
		return competitors;
	}

	public void setCompetitors(CompetitorList competitors) {
		this.competitors = competitors;
		fireTableDataChanged();
	}

	public void shuffle() {
		if (competitors == null) {
			logger.warn("Competitor list is null!");
			return;
		}
		
		Map<String, Vector<Competitor>> competitorPerUnit = new HashMap<>();
		
		for (Competitor competitor : competitors.getCompetitors()) {
			Vector<Competitor> sublist = competitorPerUnit.get(competitor.getUnit());
			
			if (sublist == null) {
				sublist = new Vector<>();
				competitorPerUnit.put(competitor.getUnit(), sublist);
			}
			
			sublist.addElement(competitor);
		}
		
		Vector<Vector<Competitor>> sublists = new Vector<>(competitorPerUnit.values());
		Collections.sort(sublists, new Comparator<Vector<Competitor>>() {

			@Override
			public int compare(Vector<Competitor> a, Vector<Competitor> b) {
				return b.size() - a.size();
			}
		});
		
		int competitorCount = competitors.size();
		
		competitors.clear();
		
		do {
			for (Vector<Competitor> sublist : sublists) {
				if (sublist.size() == 0) {
					continue;
				}

				competitors.add(sublist.remove(0));
				
				if (competitors.size() % 3 == 0) {
					break;
				}
			}
		} while (competitors.size() < competitorCount);

		fireTableDataChanged();
	}

	public void move(int from, int to) {
		if (competitors == null) {
			logger.warn("Competitor list is null!");
			return;
		}
		

		competitors.add(to, competitors.remove(from));
		fireTableDataChanged();
	}


	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		if (competitors == null) {
			logger.error("Competitor list is null!");
			return 0;
		}

		return competitors.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (competitors == null) {
			logger.error("Competitor list is null!");
			return null;
		}
		
		Competitor competitioner = competitors.get(rowIndex);
		
		if (competitioner == null) {
			logger.error("No competitioner at row=" + rowIndex);
		}

		switch (columnIndex) {
		case NAME_COLUMN:
			return competitioner.getName();

		case UNIT_COLUMN:
			return competitioner.getUnit();

		}

		return null;
	}

}
