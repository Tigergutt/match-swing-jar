package se.melsom.presentation.competitor.selector;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import se.melsom.model.shooter.Shooter;
import se.melsom.model.shooter.ShooterField;

@SuppressWarnings("serial")
public class ShooterSelectorRenderer extends JLabel implements ListCellRenderer<Shooter> {
	private ShooterField field;
	
	public ShooterSelectorRenderer(ShooterField field) {
		this.field = field;
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Shooter> list, Shooter value, int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
		}
		
		switch (field) {
		case FORENAME:
			setText(value.getForename() + " " + value.getSurname());
			break;
			
		case SURNAME:
			setText(value.getSurname() + " " + value.getForename());
			break;
		}

		setFont(list.getFont());		
        
        return this;
	}

}
