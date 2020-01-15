package se.melsom.presentation.competitor.selector;

import java.awt.Component;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ShooterSelectorEditor extends JTextField implements ComboBoxEditor {

	@Override
	public Component getEditorComponent() {
		return this;
	}

	@Override
	public void setItem(Object anObject) {
	}

	@Override
	public Object getItem() {
		return null;
	}
}
