package gui;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LabeledTextFieldComponent extends RTLJPanel {
	private JTextField textField;

	public LabeledTextFieldComponent(String label) {
		FlowLayout flowLayout = new FlowLayout(1, 5, 5);
		this.setLayout(flowLayout);
		this.setAlignmentX(0.5F);
		this.setAlignmentY(0.5F);
		JLabel lblNewLabel = new JLabel(label + ":");
		this.add(lblNewLabel);
		this.textField = new JTextField();
		this.add(this.textField);
		this.textField.setColumns(10);
	}

	public String getText() {
		return this.textField.getText();
	}

	public void setText(String text) {
		this.textField.setText(text);
	}
}