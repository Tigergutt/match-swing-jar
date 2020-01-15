package se.melsom.model.document;

import se.melsom.model.document.element.BodyTextElement;
import se.melsom.model.document.element.HeadingElement;
import se.melsom.model.document.element.PageElement;
import se.melsom.model.document.element.TableElement;

public interface DocumentProcessor {

	void processPage(PageElement element);
	
	void processHeading(HeadingElement element);

	void processTable(TableElement element);

	void processBodyText(BodyTextElement element);
}
