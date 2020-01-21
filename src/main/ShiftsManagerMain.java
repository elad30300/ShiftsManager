package main;

import gui.GUIManager;
import gui.MainWindow;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;


public class ShiftsManagerMain {
	public static void main(String[] args) {
		try {
			GUIManager.setLookAndFeel();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, " התרחשה שגיאה בעת הגדרת ערכת הנושא, זה לא ישפיע על שימושך בתוכנה" + ex);
		}

		MainWindow mainWindow = new MainWindow();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainWindow.setSize(screenSize);
		mainWindow.setVisible(true);
	}
}