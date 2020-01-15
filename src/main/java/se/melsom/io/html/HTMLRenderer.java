package se.melsom.io.html;

import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import se.melsom.model.document.DocumentRenderer;
import se.melsom.model.document.element.BodyTextElement;
import se.melsom.model.document.element.HeadingElement;
import se.melsom.model.document.element.PageElement;
import se.melsom.model.document.element.TableElement;
import se.melsom.model.document.element.table.Cell;
import se.melsom.model.document.element.table.Row;

public class HTMLRenderer implements DocumentRenderer {
	private HTMLDocument htmlDocument;
	
	public HTMLRenderer() {
		HTMLEditorKit editor = new HTMLEditorKit();
		this.htmlDocument = (HTMLDocument) editor.createDefaultDocument();
	}

	public HTMLDocument getHTML() {
		return htmlDocument;
	}

	@Override
	public void renderPage(PageElement element) {
	}

	@Override
	public void renderHeading(HeadingElement element) {
		int level = element.getLevel();
		String text = element.getText();
		
		insertHTML("<h" + level + ">" + text + "</h" + level + ">");
	}

	@Override
	public void renderBodyText(BodyTextElement element) {
		insertHTML("<p>" + element.getText() + "</p>");
	}

	@Override
	public void renderTable(TableElement tableElement) {
		String html = "<table>";
		
		for (Row row : tableElement.getRows()) {
			html += "<tr>";
			for (Cell cell : row.getCells()) {
				if (cell.isBold()) {
					html += "<th>" + cell.getContent() + "</th>";
				} else {
					html += "<td>" + cell.getContent() + "</td>";				
				}
			}
			html += "</tr>";
		}
		
		html += "</table>";
		
		insertHTML(html);
	}
	
	void insertHTML(String html) {
		try {
			htmlDocument.insertBeforeEnd(htmlDocument.getDefaultRootElement(), html);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
