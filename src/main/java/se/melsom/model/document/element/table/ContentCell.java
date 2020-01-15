package se.melsom.model.document.element.table;

import java.util.Vector;

public class ContentCell implements Cell {
	private String text = "";
	private float contentWidth = 0;
	private float contentHeight = 0;
	private float fieldWidth = 0;
	private float fieldHeight = 0;
	private boolean isBold = false;
	private Vector<FillerCell> fillers = new Vector<>();
	
	public ContentCell(String text, boolean isBold) {
		this.text = text;
		this.isBold = isBold;
	}
	
	public ContentCell(float width, float height) {
		contentWidth = width;
		contentHeight = height;
		fieldWidth = width;
		fieldHeight = height;
	}
	
	@Override
	public String getContent() {
		return text;
	}
	
	@Override
	public float getContentWidth() {
		return contentWidth;
	}

	@Override
	public void setContentWidth(float width) {
		contentWidth = width / (1 + fillers.size());
		fieldWidth = contentWidth;
		
		for (FillerCell filler : fillers) {
			filler.setFieldWidth(fieldWidth);
		}
		
	}

	@Override
	public float getContentHeight() {
		return contentHeight;
	}

	@Override
	public void setContentHeight(float height) {
		contentHeight = height;
		fieldHeight = height;
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


	public boolean isBold() {
		return isBold;
	}

	public void addFiller(FillerCell filler) {
		fillers.addElement(filler);
	}

	@Override
	public int getFillerCount() {
		return fillers.size();
	}
}
