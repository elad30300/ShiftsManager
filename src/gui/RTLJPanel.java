package gui;

import java.awt.ComponentOrientation;
import javax.swing.JPanel;

public class RTLJPanel extends JPanel {
	public RTLJPanel() {
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	public void updateUI() {
		super.updateUI();
	}
}