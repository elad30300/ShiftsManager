package controllers;

import entities.Employee;
import java.util.ArrayList;

public class EmployeesController {
	private static EmployeesController sharedInstance = new EmployeesController();
//	private EmployeeControllerDelegate delegate = null;
//
//	public void setDelegate(EmployeeControllerDelegate delegate) {
//		this.delegate = delegate;
//	}

	public static EmployeesController getSharedInstance() {
		return sharedInstance;
	}

//	public void notifyeException(Exception ex) {
//		if (this.delegate != null) {
//			this.delegate.handleEmployeesControllerException(ex);
//		}
//
//	}

	public void add(Employee employee) throws Exception {
		try {
			DataController.getSharedInstance().addEmployee(employee);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Employee> getAll() throws Exception {
		try {
			return DataController.getSharedInstance().getAllEmployees();
		} catch (Exception var2) {
			System.out.println("EC getAll exc");
			throw var2;
		}
	}

	public Employee get(String id) {
		try {
			return DataController.getSharedInstance().getEmployee(id);
		} catch (Exception var3) {
//			this.notifyeException(var3);
			return null;
		}
	}

	public ArrayList<Employee> getByFirstName(String firstName) {
		return null;
	}

	public ArrayList<Employee> getByLastName(String lastName) {
		return null;
	}

	public ArrayList<Employee> getByFullName(String firstName, String lastName) {
		return null;
	}

	public void erase(String id) {
		try {
			DataController.getSharedInstance().eraseEmployee(id);
		} catch (Exception var3) {
//			this.notifyeException(var3);
		}

	}

	public void edit(Employee employee) {
		try {
			DataController.getSharedInstance().editEmployee(employee);
		} catch (Exception var3) {
//			this.notifyeException(var3);
		}

	}
}