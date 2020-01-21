package gui;

import entities.Shift;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import model.EmployeesViewModel;
import model.ShiftsViewModel;
import model.WeekViewModel;
import model.EmployeesViewModel.EmployeesViewModelObserver;
import model.ShiftsViewModel.ShiftsViewModelObserver;

public class MainWindow extends JFrame implements EmployeesViewModelObserver, ShiftsViewModelObserver {
	private JTable table;
	private Panel mainPanel;
	private static final int TABLE_NUMBER_OF_ROW = 7;
	private static final int TABLE_NUMBER_OF_COLUMNS = 4;
	private final EmployeesViewModel employeesViewModel = EmployeesViewModel.getSharedInstance();
	private final ShiftsViewModel shiftsViewModel = ShiftsViewModel.getSharedinstance();
	private final WeekViewModel weekViewModel = WeekViewModel.getSharedInstance();

	public MainWindow() {
		this.employeesViewModel.addObserver(this);
		this.shiftsViewModel.addObserver(this);
		this.initializeMenuBar();
		this.initializeWeekShiftsPanel();
	}

	private void initializeMenuBar() {
      JMenuBar menuBar = new JMenuBar();
      menuBar.setLayout(new BorderLayout(0, 0));
      this.setJMenuBar(menuBar);
      JButton btnNewButton = new JButton("נהל עובדים");
      btnNewButton.addActionListener(new ManageWorkersButtonActionListener());
      JPanel sidePanel = new RTLJPanel();
      sidePanel.setLayout(new FlowLayout());
      sidePanel.add(btnNewButton);
      menuBar.add(sidePanel, BorderLayout.EAST);
      WeekChooserPanel weekChooserPanel = new WeekChooserPanel(this.weekViewModel);
      menuBar.add(weekChooserPanel);
   }

	private void initializeMainPanel() {
		BorderLayout mainLayout = new BorderLayout();
		this.mainPanel = new Panel(mainLayout);
		this.getContentPane().add(this.mainPanel);
	}

	private void initializeWeekShiftsPanel() {
		this.initializeMainPanel();
		WeekShiftsPanel weekShiftsPanel = new WeekShiftsPanel(this.weekViewModel);
		this.mainPanel.add(weekShiftsPanel);
		this.mainPanel.add(weekShiftsPanel, "Center");
	}

	private JPanel createDayShiftPanel() {
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		JPanel panel = new JPanel(layout);
		panel.add(new ShiftCellPanel(new Shift(new Date(2020, 1, 12, 7, 0), new Date(2020, 1, 12, 15, 0))));
		panel.add(new ShiftCellPanel(new Shift(new Date(2020, 1, 12, 7, 0), new Date(2020, 1, 12, 15, 0))));
		panel.add(new ShiftCellPanel(new Shift(new Date(2020, 1, 12, 7, 0), new Date(2020, 1, 12, 15, 0))));
		return panel;
	}

	private JPanel createSaturdayShiftPanel() {
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		JPanel panel = new JPanel(layout);
		panel.add(new ShiftCellPanel(new Shift(new Date(2020, 1, 12, 7, 0), new Date(2020, 1, 12, 15, 0))));
		panel.add(new ShiftCellPanel(new Shift(new Date(2020, 1, 12, 7, 0), new Date(2020, 1, 12, 15, 0))));
		return panel;
	}

	private JLabel createTableLabel(String text) {
		JLabel label = new JLabel(text, 0);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return label;
	}

	public void onManageWorkerClicked(ActionEvent e) {
		ManageWorkersWindow manageWorkersWindow = new ManageWorkersWindow();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		manageWorkersWindow.setSize((int) (screenSize.getWidth() * 0.5D), (int) (screenSize.getHeight() * 0.5D));
		manageWorkersWindow.setVisible(true);
	}

	public void onEmployeesChanged() {
	}

	public void onEmployeesFetchFail() {
		JOptionPane.showMessageDialog(this, "התרחשה תקלה בייבוא העובדים, אנא נסה שוב");
	}

	public void onAddEmployeeFail() {
	}

	public void onAddEmployeeSuccess() {
	}

	public void onUpdateEmployeeFail() {
	}

	public void onRemoveEmployeeFail() {
	}

	public void onShiftsUpdated() {
		this.initializeWeekShiftsPanel();
	}

	public void onFetchFailed() {
		JOptionPane.showMessageDialog(this, "התרחשה תקלה בייבוא המשמרות, אנא נסה שוב");
	}

	public void onAddExistEmployee(String id) {
	}
	
	private class ManageWorkersButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			onManageWorkerClicked(e);
		}
	}
}