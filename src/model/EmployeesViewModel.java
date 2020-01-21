package model;

import controllers.EmployeesController;
import entities.Employee;
import exceptions.EmployeeExist;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

public class EmployeesViewModel {
	private ArrayList<Employee> employees = new ArrayList();
	private EmployeesController employeesController = EmployeesController.getSharedInstance();
	private static EmployeesViewModel sharedInstance = new EmployeesViewModel();
	private ArrayList<EmployeesViewModelObserver> observers = new ArrayList();

	public void addObserver(EmployeesViewModelObserver observer) {
		this.observers.add(observer);
	}

	public void removeObserver(EmployeesViewModelObserver observer) {
		this.observers.remove(observer);
	}

	public EmployeesViewModel() {
		this.fetchEmployees();
	}

	public void fetchEmployees() {
		(new FetchEmployeesThread()).start();
	}

	public void addEmployee(Employee employee) {
		(new AddEmployeeThread()).start(employee);
	}

	public void updateEmployee(Employee employee) {
		(new UpdateEmployeeThread()).start(employee);
	}

	public static EmployeesViewModel getSharedInstance() {
		return sharedInstance;
	}

	public ArrayList<Employee> getEmployees() {
		return this.employees;
	}

	public Employee getEmployee(String id) {
		Iterator var3 = this.employees.iterator();

		while (var3.hasNext()) {
			Employee employee = (Employee) var3.next();
			if (employee.getId().equals(id)) {
				return employee;
			}
		}

		return null;
	}

	public void removeEmployee(String id) {
		(new RemoveEmployeeThread()).start(id);
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
		this.notifyEmployeesChanged();
	}

	private void notifyEmployeesChanged() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			EmployeesViewModelObserver observer = (EmployeesViewModelObserver) var2.next();
			observer.onEmployeesChanged();
		}

	}

	private void notifyEmployeesFetchFail() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			EmployeesViewModelObserver observer = (EmployeesViewModelObserver) var2.next();
			observer.onEmployeesFetchFail();
		}

	}

	private void notifyAddEmployeeFail() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			EmployeesViewModelObserver observer = (EmployeesViewModelObserver) var2.next();
			observer.onAddEmployeeFail();
		}

	}

	private void notifyAddEmployeeSuccess() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			EmployeesViewModelObserver observer = (EmployeesViewModelObserver) var2.next();
			observer.onAddEmployeeSuccess();
		}

	}

	private void notifyUpdateEmployeeFail() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			EmployeesViewModelObserver observer = (EmployeesViewModelObserver) var2.next();
			observer.onUpdateEmployeeFail();
		}

	}

	private void notifyRemoveEmployeeFail() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			EmployeesViewModelObserver observer = (EmployeesViewModelObserver) var2.next();
			observer.onRemoveEmployeeFail();
		}

	}

	private void notifyAddExistEmployee(String id) {
		Iterator var3 = this.observers.iterator();

		while (var3.hasNext()) {
			EmployeesViewModelObserver observer = (EmployeesViewModelObserver) var3.next();
			observer.onAddExistEmployee(id);
		}

	}

	public void handleEmployeesControllerException(Exception ex) {
		JOptionPane.showMessageDialog((Component) null, "התרחשה תקלה בעובדי");
	}
	
	private class AddEmployeeThread implements Runnable {
		private Employee employee;

		public void run() {
			try {
				employeesController.add(this.employee);
				notifyAddEmployeeSuccess();
				fetchEmployees();
			} catch (EmployeeExist var2) {
				notifyAddExistEmployee(employee.getId());
			} catch (Exception var3) {
				notifyAddEmployeeFail();
			}

		}

		public void start(Employee employee) {
			this.setEmployee(employee);
			this.run();
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}
	}

	private class FetchEmployeesThread implements Runnable {
		public void run() {
			try {
				setEmployees(employeesController.getAll());
			} catch (Exception var2) {
				notifyEmployeesFetchFail();
			}

		}

		public void start() {
			this.run();
		}
	}

	private class RemoveEmployeeThread implements Runnable {
		private String id = "";

		public void run() {
			try {
				employeesController.erase(this.id);
				setEmployees(employeesController.getAll());
				ShiftsViewModel.getSharedinstance().updateWeekShifts();
			} catch (Exception var2) {
				notifyRemoveEmployeeFail();
			}

		}

		public void start(String id) {
			this.setId(id);
			this.run();
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	private class UpdateEmployeeThread implements Runnable {
		private Employee employee;

		public void run() {
			try {
				employeesController.edit(this.employee);
				fetchEmployees();
			} catch (Exception var2) {
				notifyUpdateEmployeeFail();
			}

		}

		public void start(Employee employee) {
			this.setEmployee(employee);
			this.run();
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}
	}

	public interface EmployeesViewModelObserver {
		void onEmployeesChanged();

		void onEmployeesFetchFail();

		void onAddEmployeeFail();

		void onAddExistEmployee(String var1);

		void onAddEmployeeSuccess();

		void onUpdateEmployeeFail();

		void onRemoveEmployeeFail();
	}
}