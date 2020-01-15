package se.melsom.logging;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class TextAreaAppender extends AppenderSkeleton implements DocumentListener {
	private JTextArea textArea;
	private PatternLayout layout;

	public TextAreaAppender(JTextArea textArea) {
		this.textArea = textArea;
		textArea.getDocument().addDocumentListener(this);
		layout = new PatternLayout("%-5p %3x - %m%n");
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void append(LoggingEvent loggingEvent) {
		textArea.append(layout.format(loggingEvent));
	}

	public boolean requiresLayout() {
		return false;
	}

	public void close() {
	}

	public void changedUpdate(DocumentEvent event) {
	}

	public void removeUpdate(DocumentEvent event) {
	}

	public void insertUpdate(DocumentEvent event) {
		// textArea_.scrollToEnd();
	}
}