package se.melsom.presentation.competitor.selector;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import se.melsom.model.shooter.Shooter;
import se.melsom.model.shooter.ShooterField;
import se.melsom.presentation.competitor.RegisterShooterViewModel;

@SuppressWarnings("serial")
public class ShooterSelector extends JComboBox<Shooter> {
	private JTextField textField;
	private Vector<Shooter> records = new Vector<>();
	private DefaultComboBoxModel<Shooter> model = new DefaultComboBoxModel<>(records);

	public ShooterSelector(ShooterField field, RegisterShooterViewModel viewModel) {
//		this.viewModel = viewModel;
		
		setEditable(true);
		setModel(model);
		setRenderer(new ShooterSelectorRenderer(field));
		setEditor(new ShooterSelectorEditor());

		addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if (getSelectedItem() instanceof Shooter) {
					Shooter selected = (Shooter) getSelectedItem();

					viewModel.shooterSelected(selected);
					model.removeAllElements();
				}
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				model.removeAllElements();
			}
		});

		textField = (JTextField) getEditor().getEditorComponent();
		textField.setFocusable(true);
		textField.setText("");
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent key) {
				if (key.isActionKey() || key.getKeyCode() == KeyEvent.VK_ESCAPE) {
					return;
				} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
					if (getSelectedItem() instanceof Shooter) {
						Shooter selected = (Shooter) getSelectedItem();

						viewModel.shooterSelected(selected);
					}
					
					model.removeAllElements();
					setPopupVisible(false);
					return;
				} else {
					viewModel.fieldEdited();
				}
				
				String name = textField.getText();

				records.clear();
				model.removeAllElements();

				if (name.length() == 0) {
					return;
				}

				for (Shooter shooter : viewModel.getAvailabeShooters(field, name)) {
					model.addElement(shooter);
				}

				setPopupVisible(true);
			}
		});
	}
	
	public String getText() {
		return textField.getText();
	}

	public void setText(String text) {
		textField.setText(text);
	}
}
