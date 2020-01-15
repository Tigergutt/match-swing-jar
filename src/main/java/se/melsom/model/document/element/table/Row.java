package se.melsom.model.document.element.table;

import java.util.Vector;

public class Row {
	private Vector<Cell> cells = new Vector<>();
	
	public float getHeight() {
		float height = 0;
		
		for (Cell cell : cells) {
			height = Math.max(height, cell.getFieldHeight());
		}
		
		return height;
	}

	public void addCell(Cell cell) {
		cells.addElement(cell);
	}

	public int getCellCount() {
		return cells.size();
	}
	
	public Vector<Cell> getCells() {
		return cells;
	}

	public Cell getCell(int index) {
		return cells.get(index);
	}

	@Override
	public String toString() {
		String string = "";
		
		for (Cell cell : cells) {
			if (string.length() > 0) {
				string += ",";
			}
			
			string += cell.getContent().toString();
		}
		
		return string;
	}
}
