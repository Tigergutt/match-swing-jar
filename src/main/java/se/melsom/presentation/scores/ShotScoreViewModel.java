package se.melsom.presentation.scores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import se.melsom.model.score.ShotScore;

public class ShotScoreViewModel implements ActionListener,FocusListener, KeyListener {
	private ShotScore shotScore;
	
	public ShotScoreViewModel(ShotScore shotScore) {
		this.shotScore = shotScore;
	}

	public String getValueSymbol() {
		if (shotScore.getScore() < 10) {
			return "" + shotScore.getScore();
		} else if (shotScore.isBullsEye()) {
			return "+";
		}
		
		return "*";
	}
	
	boolean setValueSymbol(String symbol) {
		switch(symbol) {
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			shotScore.setScore(Integer.parseInt(symbol));
			shotScore.setBullsEye(false);
			break;
			
		case "*":
			shotScore.setScore(10);
			shotScore.setBullsEye(false);
			break;
			
		case "+":
			shotScore.setScore(10);
			shotScore.setBullsEye(true);
			break;
			
		default:
			return false;
		}
		
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JTextField == false) {
			return;
		}
		
		JTextField textField = (JTextField) event.getSource();
		
		if (textField.getText().length() != 1) {
			return;
		}
		
		setValueSymbol(textField.getText());	
	}

	@Override
	public void keyTyped(KeyEvent event) {
		if (event.getSource() instanceof JTextField == false) {
			return;
		}
		
		JTextField textField = (JTextField) event.getSource();
		
		if (textField.getText().length() > 0) {
			event.consume();
			return;
		}
		
		if (!setValueSymbol("" + event.getKeyChar())) {
			event.consume();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void focusGained(FocusEvent event) {
		if (event.getSource() instanceof JTextField == false) {
			return;
		}
		
		JTextField textField = (JTextField) event.getSource();
		
		textField.setText("");
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() instanceof JTextField == false) {
			return;
		}
		
		JTextField textField = (JTextField) event.getSource();
		
		textField.setText(getValueSymbol());
	}

}
