package se.melsom.model.document.element;

import java.util.Vector;

import se.melsom.model.document.DocumentProcessor;
import se.melsom.model.document.DocumentRenderer;

public class PageElement extends DocumentElement {
	public enum Orientation {
		PORTRAIT,
		LANDSCAPE
	}
	
	private Orientation orientation;
	private Vector<DocumentElement> elements = new Vector<>();

	public PageElement() {
		super(12);
		setOrientation(Orientation.LANDSCAPE);
		setTopMargin(10);
		setBottomMargin(10);
		setLeftMargin(10);
		setRightMargin(10);
	}

	public void addElement(DocumentElement element) {
		elements.addElement(element);
	}

	@Override
	public void process(DocumentProcessor processor) {
		processor.processPage(this);
		
		for (DocumentElement element : elements) {
			element.process(processor);
		}
	}

	@Override
	public void render(DocumentRenderer renderer) {
		renderer.renderPage(this);
		
		for (DocumentElement element : elements) {
			element.render(renderer);
		}
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		
		switch (orientation) {
		case LANDSCAPE:
			setWidth(297);
			setHeight(210);
			break;
		
		case PORTRAIT:
			setWidth(210);
			setHeight(297);
			break;
		}
	}

}
