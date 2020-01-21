package exceptions;

public class EmployeeExist extends Exception {
	private String employeeId;

	public EmployeeExist(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
}