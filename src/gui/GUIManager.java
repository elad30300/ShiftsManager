package gui;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIManager {
	
	public static void refreshComponent(Component component) {
		component.revalidate();
		component.repaint();
	}

//	public static void setFontToMaximum(JLabel label) {
//		Font labelFont = label.getFont();
//		String labelText = label.getText();
//		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
//		int componentWidth = label.getWidth();
//		double widthRatio = (double) componentWidth / (double) stringWidth;
//		int newFontSize = (int) ((double) labelFont.getSize() * widthRatio);
//		int componentHeight = label.getHeight();
//		int fontSizeToUse = Math.min(newFontSize, componentHeight);
//		label.setFont(new Font(labelFont.getName(), 0, fontSizeToUse));
//	}
	

	public static void setLookAndFeel() throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			System.out.println("UnsupportedLookAndFeelException ");
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException ");
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			System.out.println("InstantiationException ");
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException ");
			e.printStackTrace();
		}
	}
}