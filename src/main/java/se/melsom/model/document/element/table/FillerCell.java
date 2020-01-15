package se.melsom.model.document.element.table;

public class FillerCell implements Cell {
	private float width;
	private float contentWidth = 0;
	private float contentHeight = 0;
	private float fieldWidth = 0;
	private float fieldHeight = 0;
	private ContentCell parent;
	
	public FillerCell(ContentCell parent) {
		this.parent = parent;
		parent.addFiller(this);
	}

	public String getContent() {
		return "";
	}
	
	@Override
	public float getContentWidth() {
		return contentWidth;
	}

	@Override
	public void setContentWidth(float width) {
		contentWidth = width;
	}

	@Override
	public float getContentHeight() {
		return contentHeight;
	}

	@Override
	public void setContentHeight(float height) {
		contentHeight = height;
	}

	@Override
	public float getFieldWidth() {
		return fieldWidth;
	}

	@Override
	public void setFieldWidth(float width) {
		fieldWidth = width;
	}

	@Override
	public float getFieldHeight() {
		return fieldHeight;
	}

	@Override
	public void setFieldHeight(float height) {
		fieldHeight = height;
	}

	public float getFillWidth() {
		return width;
	}

	public void setFillWidth(float width) {
		this.width = width;
	}

	@Override
	public boolean isBold() {
		return parent.isBold();
	}

	@Override
	public int getFillerCount() {
		return 0;
	}
}
