package se.melsom.model.document.element;

import se.melsom.model.document.DocumentProcessor;
import se.melsom.model.document.DocumentRenderer;

public abstract class DocumentElement {
	private float x;
	private float y;
	private float width;
	private float height;
	private float leftMargin = 1;
	private float rightMargin = 1;
	private float topMargin = 1;
	private float bottomMargin = 1;
	private int fontSize;
	private boolean isBold = false;
	
	public DocumentElement(int fontSize) {
		this.fontSize = fontSize;
	}

	public abstract void process(DocumentProcessor renderer);

	public abstract void render(DocumentRenderer renderer);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(float leftMargin) {
		this.leftMargin = leftMargin;
	}

	public float getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(float rightMargin) {
		this.rightMargin = rightMargin;
	}

	public float getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(float topMargin) {
		this.topMargin = topMargin;
	}

	public float getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(float bottomMargin) {
		this.bottomMargin = bottomMargin;
	}

	public void setMargins(float left, float right, float top, float bottom) {
		setLeftMargin(left);
		setRightMargin(right);
		setTopMargin(top);
		setBottomMargin(bottom);
	}

	public int getFontSize() {
		return fontSize;
	}

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}
}
