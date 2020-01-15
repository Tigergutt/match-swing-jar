package se.melsom.model.document.element;

import java.util.Vector;

import org.apache.log4j.Logger;

import se.melsom.model.document.DocumentProcessor;
import se.melsom.model.document.DocumentRenderer;
import se.melsom.model.document.element.table.Cell;
import se.melsom.model.document.element.table.Row;

public class TableElement extends DocumentElement {
	private static Logger logger = Logger.getLogger(TableElement.class);
	private Vector<Row> content = new Vector<>();
	
	public TableElement(int fontSize) {
		super(fontSize);
	}
	
	public void autosizeColumns() {
		if (content.size() == 0) {
			return;
		}
		
		Row firstRow = content.get(0);
		
		logger.debug("Autosize table columns.");
		for (int columnIndex = 0; columnIndex < firstRow.getCellCount(); columnIndex++) {
			float width = 0;
			
			for (Row row : content) {
				Cell cell = row.getCell(columnIndex);
				
				width = Math.max(width, cell.getFieldWidth());
			}
			
			logger.debug("Set field width=" + width + " on column #" + columnIndex);
			
			for (Row row : content) {
				Cell cell = row.getCell(columnIndex);

//				if (cell instanceof TableCellFiller) {
//					continue;
//				}
				
				cell.setFieldWidth(width);
			}
		}
	}

	@Override
	public void process(DocumentProcessor processor) {
		processor.processTable(this);
	}

	@Override
	public void render(DocumentRenderer renderer) {
		renderer.renderTable(this);
	}
	
	public Vector<Row> getRows() {
		return content;
	}
	
	public void addRow(Row row) {
		content.addElement(row);
	}
}
