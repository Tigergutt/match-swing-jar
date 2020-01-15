package se.melsom.presentation.navigator;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StringView extends JPanel {

	public StringView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	public void setViewModel(StringViewModel viewModel) {
	}

	public void clear() {
		removeAll();
	}

	public void addSubView(JComponent component) {
		add(component);
	}

}
