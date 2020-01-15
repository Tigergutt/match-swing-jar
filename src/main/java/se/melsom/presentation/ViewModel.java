package se.melsom.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

public abstract class ViewModel implements ActionListener, FocusListener, KeyListener, ComponentListener {
	public abstract JComponent getView();
	
	public boolean isValidNumber(String currentValue, char ch, int maxDigitCount) {
		if (!Character.isDigit(ch)) {
			return false;
		}
		
		if (currentValue.length() + 1 > maxDigitCount) {
			return false;
		}

		if (currentValue.length() == 0 && ch == '0') {
			return false;
		}
		
		return true;
	}
	
	public boolean isValidMinutes(String currentValue, char ch) {
		if (!Character.isDigit(ch)) {
			return false;
		}
		
		int digit = Integer.parseInt(Character.toString(ch));

		if (currentValue.length() == 0) {
			if (digit == 0 || digit > 6) {
				return false;
			}
		} else if (currentValue.length() == 1) {
			if (currentValue.equals("6") && digit != 0) {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
	}

	public boolean isValidTime(String currentValue, Character ch) {
		switch (currentValue.length()) {
		case 0:
		case 1:
			try {
				Integer.parseInt(Character.toString(ch));
			} catch (Exception e) {
				return false;
			}
			break;
			
		case 2:
			if (ch != ':') {
				return false;
			}
			break;
			
		case 3:
		case 4:
			try {
				Integer.parseInt(Character.toString(ch));
			} catch (Exception e) {
				return false;
			}
			break;

		default:
			return false;
		}
		
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}
