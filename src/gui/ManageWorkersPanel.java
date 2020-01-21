package gui;

import entities.Employee;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import model.EmployeesViewModel;

public class ManageWorkersPanel extends RTLJPanel {
	private JFrame context;
	private EmployeesTablePanel employeesTablePanel;
	private EmployeesViewModel employeesViewModel = EmployeesViewModel.getSharedInstance();
	private ActionListener finishButtonActionListener = new FinishButtonActionListener();
	private ActionListener addButtonActionListener = new AddButtonActionListener();
	private ActionListener removeButtonActionListener = new RemoveButtonActionListener();

	public ManageWorkersPanel(JFrame context) {
		this.context = context;
		this.setLayout(new BorderLayout(0, 0));
		this.initializeTable();
		RTLJPanel bottomPanel = new RTLJPanel();
		this.add(bottomPanel, "South");
		bottomPanel.setLayout(new BorderLayout(0, 0));
		RTLJPanel bottomWestPanel = new RTLJPanel();
		bottomWestPanel.setLayout(new FlowLayout());
		JButton finishManageEmployeesButton = new JButton("סיים");
		finishManageEmployeesButton.addActionListener(this.finishButtonActionListener);
		bottomWestPanel.add(finishManageEmployeesButton);
		bottomPanel.add(bottomWestPanel, "West");
		RTLJPanel bottomEastPanel = new RTLJPanel();
		bottomEastPanel.setLayout(new FlowLayout());
		JButton addEmployeeButton = new JButton("הוסף");
		addEmployeeButton.addActionListener(this.addButtonActionListener);
		bottomEastPanel.add(addEmployeeButton);
		JButton removeEmployeesButton = new JButton("מחק");
		removeEmployeesButton.addActionListener(this.removeButtonActionListener);
		bottomEastPanel.add(removeEmployeesButton);
		bottomPanel.add(bottomEastPanel, "East");
	}

	private void initializeTable() {
		this.employeesTablePanel = new EmployeesTablePanel();
		this.add(this.employeesTablePanel);
	}

	private void onFinishClicked() {
		if (this.context != null) {
			this.context.dispose();
		}

	}

	private void onAddClicked() {
		AddEmployeeWindow addEmployeeWindow = new AddEmployeeWindow();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		addEmployeeWindow.setSize((int) (screenSize.getWidth() * 0.5D), (int) (screenSize.getHeight() * 0.5D));
		addEmployeeWindow.setVisible(true);
	}

	private void onRemoveClicked() {
		ArrayList<Employee> employees = this.employeesTablePanel.getSelectedEmployees();
		this.removeEmployees(employees);
	}

	private void removeEmployees(ArrayList<Employee> employees) {
		Iterator var3 = employees.iterator();

		while (var3.hasNext()) {
			Employee employee = (Employee) var3.next();
			this.removeEmployee(employee);
		}

	}

	private void removeEmployee(Employee employee) {
		this.employeesViewModel.removeEmployee(employee.getId());
	}
	
	private class FinishButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			onFinishClicked();
		}
	}
	
	private class AddButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			onAddClicked();
		}
	}
	
	private class RemoveButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			onRemoveClicked();
		}
	}
}