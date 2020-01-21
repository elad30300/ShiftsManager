package controllers;

import entities.Employee;
import entities.Shift;
import java.util.ArrayList;
import java.util.Date;

public class DataController {
	private static DataController sharedInstance = new DataController();
	private DBController dbController;

	public DataController() {
		try {
			this.dbController = new LocalJSONDBController();
		} catch (Exception var2) {
			System.err.println("couldn't create data controller!");
			var2.printStackTrace();
		}

	}

	public static DataController getSharedInstance() {
		return sharedInstance;
	}

	public void addEmployee(Employee employee) throws Exception {
		try {
			this.dbController.addEmployee(employee);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public Shift getShiftByTime(Date startTime, Date endTime) throws Exception {
		try {
			return this.dbController.getShiftByTime(startTime, endTime);
		} catch (Exception var4) {
			throw var4;
		}
	}

	public ArrayList<Employee> getAllEmployees() throws Exception {
		try {
			return this.dbController.getAllEmployees();
		} catch (Exception var2) {
			System.out.println("DC getAll exc");
			throw var2;
		}
	}

	public Employee getEmployee(String id) throws Exception {
		try {
			return this.dbController.getEmployee(id);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public void eraseEmployee(String id) throws Exception {
		try {
			this.dbController.eraseEmployee(id);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public void editEmployee(Employee employee) throws Exception {
		try {
			this.dbController.editEmployee(employee);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public void assignEmployeeToShift(Employee employee, Shift shift) throws Exception {
		try {
			this.dbController.assignEmployeeToShift(employee, shift);
		} catch (Exception var4) {
			throw var4;
		}
	}

	public ArrayList<Shift> getShiftsForEmployee(Employee employee) throws Exception {
		try {
			return this.dbController.getShiftsForEmployee(employee);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Shift> getAllShifts(Date startDate, Date endDate) throws Exception {
		try {
			return this.dbController.getAllShifts(startDate, endDate);
		} catch (Exception var4) {
			throw var4;
		}
	}

	public ArrayList<Shift> getAllShifts(Date startDate) throws Exception {
		try {
			return this.dbController.getAllShifts(startDate);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Shift> getAllShifts() throws Exception {
		try {
			return this.dbController.getAllShifts();
		} catch (Exception var2) {
			throw var2;
		}
	}

	public void removeShift(Shift shift) throws Exception {
		try {
			this.dbController.removeShift(shift);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public void removeShiftsForDay(Date date) throws Exception {
		try {
			this.dbController.removeShiftsForDay(date);
		} catch (Exception var3) {
			throw var3;
		}
	}
}