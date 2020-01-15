package se.melsom.model.document.element;

import se.melsom.model.document.DocumentProcessor;
import se.melsom.model.document.DocumentRenderer;

public class HeadingElement extends DocumentElement {
	private int level;
	private String text;
	
	public HeadingElement(int level, String text) {
		super(level);
		this.level = level;
		this.text = text;
		setBold(true);
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public void process(DocumentProcessor processor) {
		processor.processHeading(this);
	}

	@Override
	public void render(DocumentRenderer renderer) {
		renderer.renderHeading(this);
	}

}
