package se.melsom.model.document.element;

import se.melsom.model.document.DocumentProcessor;
import se.melsom.model.document.DocumentRenderer;

public class BodyTextElement extends DocumentElement {
	String text;

	public BodyTextElement(String text) {
		super(12);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public void process(DocumentProcessor processor) {
		processor.processBodyText(this);
	}

	@Override
	public void render(DocumentRenderer renderer) {
		renderer.renderBodyText(this);
	}

}
