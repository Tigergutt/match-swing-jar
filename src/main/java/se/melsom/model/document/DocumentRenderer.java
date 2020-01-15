package se.melsom.model.document;

import se.melsom.model.document.element.BodyTextElement;
import se.melsom.model.document.element.HeadingElement;
import se.melsom.model.document.element.PageElement;
import se.melsom.model.document.element.TableElement;

public interface DocumentRenderer {

	void renderPage(PageElement element);
	
	void renderHeading(HeadingElement element);

	void renderTable(TableElement element);

	void renderBodyText(BodyTextElement element);
}
