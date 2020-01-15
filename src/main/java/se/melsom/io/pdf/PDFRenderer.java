package se.melsom.io.pdf;

import java.awt.Color;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import se.melsom.model.document.DocumentRenderer;
import se.melsom.model.document.element.BodyTextElement;
import se.melsom.model.document.element.HeadingElement;
import se.melsom.model.document.element.PageElement;
import se.melsom.model.document.element.TableElement;
import se.melsom.model.document.element.table.Cell;
import se.melsom.model.document.element.table.Row;

public class PDFRenderer implements DocumentRenderer {
	private static Logger logger = Logger.getLogger(PDFRenderer.class);
	private PDDocument document;
	private PDPage currentPage = null;
	private float y = 0;
	private float leftMargin = 0;

	public PDFRenderer() {
		document = new PDDocument();
	}
	
	public void save() throws IOException {
		document.save("test.pdf");
	}

	@Override
	public void renderPage(PageElement element) {
		switch (element.getOrientation()) {
		case LANDSCAPE:
			currentPage = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
			break;
			
		case PORTRAIT:
			currentPage = new PDPage(PDRectangle.A4);
			break;
		}
		
		document.addPage(currentPage);
		
		leftMargin = millimetersToPoints(element.getLeftMargin());
		y = millimetersToPoints(element.getHeight() - element.getTopMargin());
	}

	@Override
	public void renderHeading(HeadingElement element) {
		if (currentPage == null) {
			return;
		}

		logger.debug("Heading: " + element.getText());
		try (PDPageContentStream contents = createContentStream()) {
			PDFont font = PDType1Font.HELVETICA;
			
			if (element.isBold()) {
				font = PDType1Font.HELVETICA_BOLD;
			}

			float x = leftMargin;
			y -= millimetersToPoints(element.getTopMargin() + element.getHeight());	
			
			logger.debug("[" + x + "," + y + "](" + element.getText() + ")");

			contents.beginText();
			contents.setFont(font, element.getFontSize());
			contents.newLineAtOffset(x, y);
			contents.showText(element.getText());
			contents.endText();
			
			y -= millimetersToPoints(element.getBottomMargin());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void renderBodyText(BodyTextElement element) {
		if (currentPage == null) {
			return;
		}

		try (PDPageContentStream contents = createContentStream()) {
			PDFont font = PDType1Font.HELVETICA;
			contents.beginText();
			contents.setFont(font, element.getFontSize());
			contents.showText(element.getText());
			contents.endText();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void renderTable(TableElement table) {
		if (currentPage == null) {
			return;
		}

		float tableY = y;

		// Renter content
		for (Row row : table.getRows()) {
			logger.trace(row);
			
			float contentX = leftMargin;

			logger.trace("[" + contentX + "," + y + "]:" + row);

			for (Cell cell : row.getCells()) {
				float contentY = tableY;
				
				contentY -= millimetersToPoints(table.getTopMargin());
				contentY -= millimetersToPoints(cell.getContentHeight());
				
				try (PDPageContentStream contents = createContentStream()) {
					PDFont font = PDType1Font.HELVETICA;
					
					if (cell.isBold()) {
						font = PDType1Font.HELVETICA_BOLD;
					}

					contentX += millimetersToPoints(table.getLeftMargin());
					contents.beginText();
					contents.setFont(font, table.getFontSize());
					contents.newLineAtOffset(contentX, contentY);
					contents.showText(cell.getContent());
					contents.endText();
					contentX += millimetersToPoints(cell.getFieldWidth());
					contentX += millimetersToPoints(table.getLeftMargin());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			tableY -= millimetersToPoints(table.getTopMargin());
			tableY -= millimetersToPoints(row.getHeight());
			tableY -= millimetersToPoints(table.getBottomMargin());
		}
		
		tableY = y;
		for (Row row : table.getRows()) {
			float tableX = leftMargin;
			float cellHeight = 0;
			
			cellHeight +=  millimetersToPoints(table.getTopMargin());
			cellHeight +=  millimetersToPoints(row.getHeight());
			cellHeight +=  millimetersToPoints(table.getBottomMargin());
			tableY -= cellHeight;

			for (int columnIndex = 0; columnIndex < row.getCellCount(); columnIndex++) {
				Cell cell = row.getCell(columnIndex);
				float cellWidth = 0;
								
				cellWidth += millimetersToPoints(table.getLeftMargin());
				cellWidth += millimetersToPoints(cell.getFieldWidth());
				cellWidth += millimetersToPoints(table.getLeftMargin());
				
				cellWidth += cell.getFillerCount() * cellWidth;
				columnIndex += cell.getFillerCount();
				
				try (PDPageContentStream contents = createContentStream()) {
					contents.setStrokingColor(Color.BLACK);
					contents.addRect(tableX, tableY, cellWidth, cellHeight);
					contents.stroke();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				tableX += cellWidth;
			}
			
		}
		
		y = tableY;
	}

	PDPageContentStream createContentStream() throws IOException {
		return new PDPageContentStream(document, currentPage, PDPageContentStream.AppendMode.APPEND, false);
	}
	
	
	float millimetersToPoints(float mm) {
		return mm * 72f / 25.4f;
	}

}
