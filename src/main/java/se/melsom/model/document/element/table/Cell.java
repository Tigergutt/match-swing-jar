package se.melsom.model.document.element.table;

public interface Cell {

	String getContent();

	float getContentWidth();

	void setContentWidth(float width);

	float getContentHeight();

	void setContentHeight(float height);

	float getFieldWidth();

	void setFieldWidth(float width);

	float getFieldHeight();

	void setFieldHeight(float height);

	boolean isBold();
	
	int getFillerCount();
}
