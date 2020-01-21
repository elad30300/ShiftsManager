package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class AddEmployeeWindow extends JFrame {
	public AddEmployeeWindow() {
		this.setLayout(new BorderLayout());
		AddEmployeePanel addEmployeePanel = new AddEmployeePanel(this);
		this.add(addEmployeePanel);
	}
}