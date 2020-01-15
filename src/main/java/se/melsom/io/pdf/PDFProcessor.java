package se.melsom.io.pdf;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import se.melsom.model.document.DocumentProcessor;
import se.melsom.model.document.element.BodyTextElement;
import se.melsom.model.document.element.HeadingElement;
import se.melsom.model.document.element.PageElement;
import se.melsom.model.document.element.TableElement;
import se.melsom.model.document.element.table.Cell;
import se.melsom.model.document.element.table.Row;

public class PDFProcessor implements DocumentProcessor {
	private static Logger logger = Logger.getLogger(PDFProcessor.class);

	@Override
	public void processPage(PageElement element) {
	}
	
	@Override
	public void processHeading(HeadingElement element) {
		PDFont font = PDType1Font.HELVETICA;
		
		if (element.isBold()) {
			font = PDType1Font.HELVETICA_BOLD;
		}
		
		element.setWidth(getStringWidth(font, element.getText(), element.getFontSize()));
		element.setHeight(getFontHeight(font, element.getFontSize()));
	}
	
	@Override
	public void processBodyText(BodyTextElement element) {
		PDFont font = PDType1Font.HELVETICA;
		
		if (element.isBold()) {
			font = PDType1Font.HELVETICA_BOLD;
		}
		
		element.setWidth(getStringWidth(font, element.getText(), element.getFontSize()));
		element.setHeight(getFontHeight(font, element.getFontSize()));
	}
	
	@Override
	public void processTable(TableElement table) {
		logger.trace("Process table");
		for (Row row : table.getRows()) {
			logger.trace("Process row: " + row);
			
			for (Cell cell : row.getCells()) {
				String content = cell.getContent();

				if (content.length() == 0) {
					continue;
				}
				
				logger.trace("Process cell: " + cell);
				PDFont font = PDType1Font.HELVETICA;
				
				if (cell.isBold()) {
					font = PDType1Font.HELVETICA_BOLD;
				}
				
				
				cell.setContentWidth(getStringWidth(font, cell.getContent(), table.getFontSize()));
				cell.setContentHeight(getFontHeight(font, table.getFontSize()));
			}
		}
		
		table.autosizeColumns();
	}
	
	float getStringWidth(PDFont font, String text, float fontSize) {
		float width = 0;
		
		try {
			width = pointsToMillimeters(font.getStringWidth(text) / 1000f * fontSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return width;
	}

	float getFontHeight(PDFont font, int fontSize) {
		return pointsToMillimeters(font.getFontDescriptor().getCapHeight() / 1000f * fontSize);
	}
	
	float pointsToMillimeters(float pt) {
		return pt * 25.4f / 72f;
	}
}
