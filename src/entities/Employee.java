package entities;

public class Employee {
	private String id;
	private String firstName;
	private String lastName;

	public Employee(String id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Employee(Employee employee) {
		this(employee.getId(), employee.getFirstName(), employee.getLastName());
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String toString() {
		return this.firstName + " " + this.lastName + " (" + this.id + ")";
	}

	public void set(Employee employee) {
		this.id = employee.id;
		this.firstName = employee.firstName;
		this.lastName = employee.lastName;
	}
}
