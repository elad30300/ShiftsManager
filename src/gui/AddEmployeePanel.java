package gui;

import entities.Employee;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.EmployeesViewModel;
import model.EmployeesViewModel.EmployeesViewModelObserver;

public class AddEmployeePanel extends RTLJPanel implements EmployeesViewModelObserver {
	private JFrame context;
	EmployeesViewModel employeesViewModel = EmployeesViewModel.getSharedInstance();
	private LabeledTextFieldComponent firstNameTextFieldComponent;
	private LabeledTextFieldComponent lastNameTextFieldComponent;
	private LabeledTextFieldComponent idTextFieldComponent;
	private JButton saveButton;
	private ActionListener saveButtonActionListener = new SaveButtonActionListener();

	public AddEmployeePanel(JFrame context) {
		this.context = context;
		this.setLayout(new GridLayout(0, 1, 0, 0));
		this.firstNameTextFieldComponent = new LabeledTextFieldComponent("שם פרטי");
		this.add(this.firstNameTextFieldComponent);
		this.lastNameTextFieldComponent = new LabeledTextFieldComponent("שם משפחה");
		this.add(this.lastNameTextFieldComponent);
		this.idTextFieldComponent = new LabeledTextFieldComponent("תעודת זהות");
		this.add(this.idTextFieldComponent);
		RTLJPanel panel = new RTLJPanel();
		panel.setLayout(new FlowLayout(1, 5, 5));
		this.saveButton = new JButton("שמור");
		this.saveButton.addActionListener(this.saveButtonActionListener);
		panel.add(this.saveButton);
		this.add(panel);
		this.employeesViewModel.addObserver(this);
	}

	private void onSaveClicked() {
		if (this.validateForm()) {
			String id = this.idTextFieldComponent.getText().trim();
			String firstName = this.firstNameTextFieldComponent.getText().trim();
			String lastName = this.lastNameTextFieldComponent.getText().trim();
			Employee employee = new Employee(id, firstName, lastName);
			this.employeesViewModel.addEmployee(employee);
		} else {
			JOptionPane.showMessageDialog(this, "יש למלא את כל השדות");
		}

	}

	private boolean validateForm() {
		boolean correct = true;
		correct &= !this.firstNameTextFieldComponent.getText().equals("");
		correct &= !this.lastNameTextFieldComponent.getText().equals("");
		correct &= !this.idTextFieldComponent.getText().equals("");
		return correct;
	}

	public void onEmployeesChanged() {
	}

	public void onEmployeesFetchFail() {
	}

	public void onAddEmployeeFail() {
		JOptionPane.showMessageDialog(this, "הוספת העובד למערכת נכשלה, אנא נסה שוב.");
	}

	public void onAddEmployeeSuccess() {
		if (this.context != null) {
			this.context.dispose();
		}

	}

	public void onUpdateEmployeeFail() {
	}

	public void onRemoveEmployeeFail() {
	}

	public void onAddExistEmployee(String id) {
		JOptionPane.showMessageDialog((Component) null, String.format("קיים כבר עובד עם ת.ז %s", id));
	}
	
	private class SaveButtonActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			onSaveClicked();
		}
	}
}