package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class ManageWorkersWindow extends JFrame {
	public ManageWorkersWindow() {
		this.setLayout(new BorderLayout());
		ManageWorkersPanel manageWorkersPanel = new ManageWorkersPanel(this);
		this.add(manageWorkersPanel);
	}
}