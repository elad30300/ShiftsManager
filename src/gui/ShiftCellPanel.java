package gui;

import controllers.ShiftsController;
import entities.Employee;
import entities.Shift;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import lowExceptions.LowException;
import model.EmployeesViewModel;
import model.EmployeesViewModel.EmployeesViewModelObserver;

public class ShiftCellPanel extends RTLJPanel implements EmployeesViewModelObserver {
	private Shift shift;
	private ShiftsController shiftsController = ShiftsController.getSharedInstance();
	private EmployeesViewModel employeesViewModel = EmployeesViewModel.getSharedInstance();
	private JLabel startTimeLabel = new JLabel();
	private JLabel endTimeLabel = new JLabel();
	private JComboBox<EmployeeComboBoxItem> employeesComboBox = new JComboBox();
	private ActionListener onEmployeeActionListener = new assignEmployeeButtonActionListener();

	public ShiftCellPanel(Shift shift) {
		this.shift = shift;
		this.employeesViewModel.addObserver(this);
		this.intiailizeUI();
		this.fetchShiftData();
	}

	private void intiailizeUI() {
		this.initializeGeneralUI();
		this.initializeFirstRow();
		this.initializeSecondRow();
		this.initializeThirdRow();
	}

	private void setUIValues() {
		this.setStartTimeUI();
		this.setEndTimeUI();
		this.setComboBoxSelectedItemToEmployee();
	}

	private ArrayList<EmployeeComboBoxItem> convertEmployeeListToListObjects(ArrayList<Employee> employees) {
		ArrayList<EmployeeComboBoxItem> employeeComboBoxItems = new ArrayList();
		Iterator var4 = employees.iterator();

		while (var4.hasNext()) {
			Employee employee = (Employee) var4.next();
			employeeComboBoxItems.add(new EmployeeComboBoxItem(employee));
		}

		return employeeComboBoxItems;
	}

	private String convertEmployeeToListObject(Employee employee) {
		return employee.toString();
	}

	private Employee convertFromEmployeesListIndexToEmployeeEntity(int index) {
		return index == 0 ? null : (Employee) this.employeesViewModel.getEmployees().get(index - 1);
	}

	private void handleAssignSuccess(Employee employee) {
		this.fetchShiftData();
	}

	private void handleAssignFail(Exception ex) {
		if (ex instanceof LowException) {
			JOptionPane.showMessageDialog((Component) null, ((LowException) ex).getLowDescription());
		} else {
			JOptionPane.showMessageDialog((Component) null, "השמת עובד למשמרת נכשלה, אנא נסה שוב.");
		}

		this.fetchShiftData();
	}

	private void handleRemoveFail() {
		JOptionPane.showMessageDialog((Component) null, "ריקון משמרת נכשל, אנא נסה שוב.");
		this.fetchShiftData();
	}

	private void onShiftChanged() {
		this.setUIValues();
	}

	private void fetchShiftData() {
		(new FetchThread()).start();
	}

	private void initializeGeneralUI() {
		GridLayout mainLayout = new GridLayout(0, 1, 0, 0);
		this.setLayout(mainLayout);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	private void setBackground() {
		if (this.shift.getEmployee() == null) {
			this.setBackground(Color.GREEN);
		}

	}

	private void setStartTimeUI() {
		this.startTimeLabel.setText((new SimpleDateFormat("HH:mm")).format(this.shift.getStartTime()));
	}

	private void setEndTimeUI() {
		this.endTimeLabel.setText((new SimpleDateFormat("HH:mm")).format(this.shift.getEndTime()));
	}

	private void initializeFirstRow() {
		RTLJPanel firstRowPanel = new RTLJPanel();
		firstRowPanel.add(new JLabel("התחלה: "));
		this.setStartTimeUI();
		firstRowPanel.add(this.startTimeLabel);
		this.add(firstRowPanel);
	}

	private void initializeSecondRow() {
		RTLJPanel secondRowPanel = new RTLJPanel();
		secondRowPanel.add(new JLabel("סיום: "));
		this.setEndTimeUI();
		secondRowPanel.add(this.endTimeLabel);
		this.add(secondRowPanel);
	}

	private void initializeThirdRow() {
		RTLJPanel thirdRowPanel = new RTLJPanel();
		this.initializeComboBox(thirdRowPanel);
		this.add(thirdRowPanel);
	}

	private void setEmployeesComboBoxItems() {
		this.employeesComboBox.removeAllItems();
		this.employeesComboBox.addItem(new EmployeeComboBoxItem(null));
		ArrayList<Employee> employees = this.employeesViewModel.getEmployees();
		ArrayList<EmployeeComboBoxItem> employeesReperesentations = this.convertEmployeeListToListObjects(employees);
		Iterator var4 = employeesReperesentations.iterator();

		while (var4.hasNext()) {
			EmployeeComboBoxItem employeeReperesentation = (EmployeeComboBoxItem) var4.next();
			this.employeesComboBox.addItem(employeeReperesentation);
		}

	}

	private ArrayList<EmployeeComboBoxItem> getAllItemsFromEmployeysComboBox() {
		ComboBoxModel<EmployeeComboBoxItem> boxModel = this.employeesComboBox.getModel();
		ArrayList<EmployeeComboBoxItem> items = new ArrayList();

		for (int i = 0; i < boxModel.getSize(); ++i) {
			items.add((EmployeeComboBoxItem) boxModel.getElementAt(i));
		}

		return items;
	}

	private void setComboBoxSelectedItemToEmployee() {
		ArrayList<EmployeeComboBoxItem> items = this.getAllItemsFromEmployeysComboBox();
		if (this.shift.getEmployee() == null) {
			this.employeesComboBox.setSelectedIndex(0);
		} else {
			System.out.println("set combo box for shift - employee is " + this.shift.getEmployee());
			Iterator var3 = items.iterator();

			while (var3.hasNext()) {
				EmployeeComboBoxItem item = (EmployeeComboBoxItem) var3.next();
				if (item.getEmployee() != null && item.getEmployee().getId().equals(this.shift.getEmployee().getId())) {
					this.employeesComboBox.setSelectedItem(item);
				}
			}

		}
	}

	private void setEmployeesComboBoxUI() {
		this.setEmployeesComboBoxItems();
		this.setComboBoxSelectedItemToEmployee();
		this.employeesComboBox.addActionListener(this.onEmployeeActionListener);
	}

	private void initializeComboBox(JPanel context) {
		this.employeesComboBox = new JComboBox();
		((JLabel) this.employeesComboBox.getRenderer()).setHorizontalAlignment(0);
		this.setEmployeesComboBoxUI();
		context.add(this.employeesComboBox);
	}

	private Employee extractEmployee() {
		try {
			Employee employee = ((EmployeeComboBoxItem) this.employeesComboBox.getSelectedItem()).getEmployee();
			return employee;
		} catch (Exception var2) {
			return null;
		}
	}

	private void onAssignEmployee() {
		(new UpdateEmployeeThread()).start();
	}

	private void initializeFourthRow() {
		RTLJPanel fourthRowPanel = new RTLJPanel();
		JButton saveButton = new JButton("שמור");
		fourthRowPanel.add(saveButton);
		this.add(fourthRowPanel);
	}

	public Shift getShift() {
		return this.shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
		this.onShiftChanged();
	}

	public void onEmployeesChanged() {
		this.setEmployeesComboBoxItems();
		this.setComboBoxSelectedItemToEmployee();
	}

	public void onEmployeesFetchFail() {
	}

	public void onAddEmployeeFail() {
	}

	public void onAddEmployeeSuccess() {
	}

	protected void finalize() throws Throwable {
		super.finalize();
		this.employeesViewModel.removeObserver(this);
	}

	public void onUpdateEmployeeFail() {
	}

	public void onRemoveEmployeeFail() {
	}

	public void onAddExistEmployee(String id) {
	}

	private class assignEmployeeButtonActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (this.isActionPerformedIntentinally(e)) {
				onAssignEmployee();
			}

		}

		private boolean isActionPerformedIntentinally(ActionEvent e) {
			return e.getModifiers() > 0;
		}
	}
	
	private class EmployeeComboBoxItem extends Component {
		private Employee employee;

		public EmployeeComboBoxItem(Employee employee) {
			this.employee = employee;
		}

		public Employee getEmployee() {
			return this.employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

		public String toString() {
			return this.employee == null ? "--" : this.employee.toString();
		}
	}
	
	private class FetchThread implements Runnable {

		public void run() {
			Shift fetchedShift = shiftsController.getShiftByTime(shift.getStartTime(), shift.getEndTime());
			if (fetchedShift != null) {
				setShift(fetchedShift);
			} else {
				setUIValues(); // TODO probably mistake - should check it out with debug!!
			}

		}

		public void start() {
			this.run();
		}
	}
	
	private class UpdateEmployeeThread implements Runnable {

		public void run() {
			Employee employee = extractEmployee();
			if (employee != null) {
				try {
					shiftsController.assign(employee, shift);
					handleAssignSuccess(employee);
				} catch (Exception ex) {
					handleAssignFail(ex);
				}
			} else {
				try {
					shiftsController.remove(shift);
				} catch (Exception ex) {
					System.err.println("remove shift exception");
					ex.printStackTrace();
					handleRemoveFail();
				}

			}
		}

		public void start() {
			this.run();
		}
	}
}