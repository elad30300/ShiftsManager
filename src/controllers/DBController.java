package controllers;

import entities.Employee;
import entities.Shift;
import java.util.ArrayList;
import java.util.Date;

public interface DBController {
	void addEmployee(Employee var1) throws Exception;

	ArrayList<Employee> getAllEmployees() throws Exception;

	Employee getEmployee(String var1) throws Exception;

	void eraseEmployee(String var1) throws Exception;

	Shift getShiftByTime(Date var1, Date var2) throws Exception;

	void editEmployee(Employee var1) throws Exception;

	void assignEmployeeToShift(Employee var1, Shift var2) throws Exception;

	ArrayList<Shift> getShiftsForEmployee(Employee var1) throws Exception;

	ArrayList<Shift> getAllShifts(Date var1, Date var2) throws Exception;

	ArrayList<Shift> getAllShifts(Date var1) throws Exception;

	ArrayList<Shift> getAllShifts() throws Exception;

	void removeShift(Shift var1) throws Exception;

	void removeShiftsForDay(Date var1) throws Exception;
}