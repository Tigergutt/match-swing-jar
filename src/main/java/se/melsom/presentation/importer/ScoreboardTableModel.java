package se.melsom.presentation.importer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ScoreboardTableModel extends AbstractTableModel {
	private static String[] columnNames = {
		"Namn",
		"Förband",
		"Poäng"
	};
	
	List<List<String>> content = new ArrayList<>();
	
	public void setContent(List<List<String>> value) {
		content.clear();
		content.addAll(value);
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return content.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Class<?> getColumnClass(int c) {
        return String.class;
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return content.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
    }

	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		content.get(rowIndex).set(columnIndex, value.toString());
    }
}
