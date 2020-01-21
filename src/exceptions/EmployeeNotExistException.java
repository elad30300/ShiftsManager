package exceptions;

public class EmployeeNotExistException extends Exception {
	private String employeeId;

	public EmployeeNotExistException(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
}