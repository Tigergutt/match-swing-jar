package se.melsom.model.document.element;

import java.util.Vector;

import se.melsom.model.document.DocumentProcessor;
import se.melsom.model.document.DocumentRenderer;

public class SectionElement extends DocumentElement {
	private Vector<DocumentElement> elements = new Vector<>();

	public SectionElement() {
		super(12);
	}
	
	public void addElement(DocumentElement element) {
		elements.addElement(element);
	}

	@Override
	public void process(DocumentProcessor processor) {
		for (DocumentElement element : elements) {
			element.process(processor);
		}
	}

	@Override
	public void render(DocumentRenderer renderer) {
		for (DocumentElement element : elements) {
			element.render(renderer);
		}
	}

}
