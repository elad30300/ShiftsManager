package gui;

import entities.Employee;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import model.EmployeesViewModel;
import model.EmployeesViewModel.EmployeesViewModelObserver;

public class EmployeesTablePanel extends RTLJPanel {
	private EmployeesViewModel employeesViewModel = EmployeesViewModel.getSharedInstance();
	private EmployeeTableModel tableModel;
	private JTable table;

	public EmployeesTablePanel() {
		this.setLayout(new BorderLayout(0, 0));
		this.initializeTable();
	}

	private void initializeTable() {
		this.tableModel = new EmployeeTableModel();
		this.table = new JTable(this.tableModel);
		this.table.setRowHeight(30);
		this.table.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.add(this.table.getTableHeader(), "First");
		this.add(this.table, "Center");
	}

	public ArrayList<Employee> getSelectedEmployees() {
		int[] selectedRows = this.table.getSelectedRows();
		ArrayList<Employee> employees = new ArrayList();
		int[] var6 = selectedRows;
		int var5 = selectedRows.length;

		for (int var4 = 0; var4 < var5; ++var4) {
			int row = var6[var4];
			employees.add(this.employeesViewModel.getEmployee(this.tableModel.getIdForRow(row)));
		}

		return employees;
	}
	
	private class EmployeeTableModel extends AbstractTableModel implements EmployeesViewModelObserver {
		private String[] columnsNames;
		private Object[][] data;

		public EmployeeTableModel() {
			this.columnsNames = new String[]{"ת.ז", "שם פרטי", "שם משפחה"};
			employeesViewModel.addObserver(this);
			this.setData(employeesViewModel.getEmployees());
		}

		private Object[] fromEmployeeToDataObject(Employee employee) {
			Object[] object = new Object[this.getColumnCount()];
			object[0] = employee.getId();
			object[1] = employee.getFirstName();
			object[2] = employee.getLastName();
			return object;
		}

		private Object[][] fromEmployeesToData(ArrayList<Employee> employees) {
			int numberOfRows = employees.size();
			int numberOfColumns = this.getColumnCount();
			Object[][] objects = new Object[numberOfRows][numberOfColumns];

			for (int i = 0; i < numberOfRows; ++i) {
				objects[i] = this.fromEmployeeToDataObject((Employee) employees.get(i));
			}

			return objects;
		}

		private void setData(ArrayList<Employee> employees) {
			this.data = this.fromEmployeesToData(employees);
		}

		public int getColumnCount() {
			return this.columnsNames.length;
		}

		public int getRowCount() {
			return this.data.length;
		}

		public String getColumnName(int col) {
			return this.columnsNames[col];
		}

		public Object getValueAt(int row, int col) {
			return this.data[row][col];
		}

		public boolean isCellEditable(int row, int col) {
			return col > 0 && col < 3;
		}

		public String getIdForRow(int row) {
			return (String) this.data[row][0];
		}

		public String getFirstNameForRow(int row) {
			return (String) this.data[row][1];
		}

		public String getLastNameForRow(int row) {
			return (String) this.data[row][2];
		}

		public void setValueAt(Object value, int row, int col) {
			this.data[row][col] = value;
			this.fireTableCellUpdated(row, col);
			Employee employee = new Employee(employeesViewModel.getEmployee(this.getIdForRow(row)));
			employee.setFirstName(this.getFirstNameForRow(row));
			employee.setLastName(this.getLastNameForRow(row));
			if (employee != null) {
				employeesViewModel.updateEmployee(employee);
			}

		}

		public void onEmployeesChanged() {
			this.setData(employeesViewModel.getEmployees());
		}

		public void onEmployeesFetchFail() {
		}

		public void onAddEmployeeFail() {
		}

		public void onAddEmployeeSuccess() {
		}

		public void onUpdateEmployeeFail() {
			JOptionPane.showMessageDialog(null, "עריכת פרטי עובד נכשלה, אנא נסה שוב.");
			this.setData(employeesViewModel.getEmployees());
		}

		public void onRemoveEmployeeFail() {
			JOptionPane.showMessageDialog(null, "מחיקת עובד נכשלה, אנא נסה שוב.");
			this.setData(employeesViewModel.getEmployees());
		}

		public void onAddExistEmployee(String id) {
		}

		public String[] getColumnsNames() {
			return this.columnsNames;
		}

		public void setColumnsNames(String[] columnsNames) {
			this.columnsNames = columnsNames;
		}

		public Object[][] getData() {
			return this.data;
		}

		public void setData(Object[][] data) {
			this.data = data;
		}
	}
}