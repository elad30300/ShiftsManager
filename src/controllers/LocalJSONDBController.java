package controllers;

import entities.Employee;
import entities.Shift;
import exceptions.EmployeeExist;
import exceptions.EmployeeNotExistException;
import exceptions.FileAlreadyExistsException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import model.WeekManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LocalJSONDBController implements DBController {
	public static final String FILEPATH = System.getProperty("user.home") + "/ShiftManager/Data/data.json";
	public static final String WORKERS_KEY = "workers";
	public static final String ID_FIELD_NAME = "id";
	public static final String FIRST_NAME_FIELD_NAME = "firstName";
	public static final String LAST_NAME_FIELD_NAME = "lastName";
	public static final String SHIFTS_KEY = "shifts";
	public static final String START_TIME_FIELD_NAME = "startTime";
	public static final String END_TIME_FIELD_NAME = "endTime";
	public static final String EMPLOYEE_ID_FIELD_NAME = "employeeId";
	public static final String TWO_SHIFTS_DATES_KEY = "twoShiftsDates";
	public static final String TWO_SHIFTS_DATES_FIELD_NAME = "date";
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat twoShiftsModeDatesStringFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public LocalJSONDBController() throws Exception {
		this.initializeFile();
	}

	private boolean createDataFilepath() throws Exception {
		String[] pathComponents = FILEPATH.split("/");
		String currentPath = "";
		String[] var6 = pathComponents;
		int var5 = pathComponents.length;

		for (int var4 = 0; var4 < var5; ++var4) {
			String component = var6[var4];
			currentPath = currentPath + "/" + component;
			File file = new File(currentPath);

			try {
				if (component.equals(pathComponents[pathComponents.length - 1])) {
					if (file.exists()) {
						throw new FileAlreadyExistsException();
					}

					if (!file.createNewFile()) {
						System.out.println("didnt create " + currentPath);
						throw new Exception("Couldn't create file with path: '" + currentPath + "'");
					}

					System.out.println("created " + currentPath);
				} else {
					if (!file.exists() && !file.mkdir()) {
						System.out.println("didnt create " + currentPath);
						throw new Exception("Couldn't create directory with path: '" + currentPath + "'");
					}

					System.out.println("created " + currentPath);
				}
			} catch (Exception var9) {
				throw var9;
			}
		}

		return true;
	}

	private void initializeFile() throws Exception {
		try {
			this.createDataFilepath();
			JSONObject rootObject = new JSONObject();
			rootObject.put("workers", new JSONArray());
			rootObject.put("shifts", new JSONArray());
			this.writeRootObject(rootObject);
		} catch (FileAlreadyExistsException var2) {
			System.out.println("file already exists");
		} catch (Exception var3) {
			System.out.println(var3);
			var3.printStackTrace();
			throw var3;
		}
	}

	private void writeRootObject(JSONObject rootObject) throws Exception {
		FileWriter fileWriter = null;

		try {
			fileWriter = this.createFileWriterToFilePath();
			fileWriter.write(rootObject.toJSONString());
			fileWriter.close();
		} catch (Exception var4) {
			if (fileWriter != null) {
				fileWriter.close();
			}

			throw var4;
		}
	}

	private boolean isEmployeeExist(String id) throws Exception {
		try {
			this.getEmployee(id);
			return true;
		} catch (EmployeeNotExistException var3) {
			return false;
		} catch (Exception var4) {
			throw var4;
		}
	}

	public void addEmployee(Employee employee) throws Exception {
		try {
			if (this.isEmployeeExist(employee.getId())) {
				throw new EmployeeExist(employee.getId());
			} else {
				this.addEmployeeToJSONFile(employee);
			}
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Employee> getAllEmployees() throws Exception {
		try {
			return this.getAllEmployeesFromJSONFile();
		} catch (Exception var2) {
			throw var2;
		}
	}

	public Employee getEmployee(String id) throws Exception {
		try {
			return this.getEmployeeFromJSONFile(id);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public void eraseEmployee(String id) throws Exception {
		try {
			this.eraseEmployeeFromJSONFile(id);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public void editEmployee(Employee employee) throws Exception {
		try {
			this.editEmployeeInJSONFile(employee);
		} catch (Exception var3) {
			throw var3;
		}
	}

	private FileReader createFileReaderToFilePath() throws Exception {
		try {
			return new FileReader(FILEPATH);
		} catch (Exception var2) {
			throw var2;
		}
	}

	private FileWriter createFileWriterToFilePath() throws Exception {
		try {
			return new FileWriter(FILEPATH);
		} catch (Exception var2) {
			throw var2;
		}
	}

	private JSONObject fromEmployeeJSONObject(Employee employee) {
		JSONObject object = new JSONObject();
		object.put("id", employee.getId());
		object.put("firstName", employee.getFirstName());
		object.put("lastName", employee.getLastName());
		return object;
	}

	private Employee fromJSONObjectToEmployee(JSONObject object) throws Exception {
		try {
			return new Employee((String) object.get("id"), (String) object.get("firstName"),
					(String) object.get("lastName"));
		} catch (Exception var3) {
			throw var3;
		}
	}

	private ArrayList<Employee> fromJSONArrayToEmployeesList(JSONArray array) throws Exception {
		ArrayList<Employee> employees = new ArrayList<Employee>();

		try {
			Iterator var4 = array.iterator();

			while (var4.hasNext()) {
				Object object = var4.next();
				employees.add(this.fromJSONObjectToEmployee((JSONObject) object));
			}

			return employees;
		} catch (Exception var5) {
			throw var5;
		}
	}

	private JSONArray fromEmployeesListToJSONArray(ArrayList<Employee> employees) throws Exception {
		try {
			JSONArray array = new JSONArray();
			Iterator var4 = employees.iterator();

			while (var4.hasNext()) {
				Employee employee = (Employee) var4.next();
				array.add(this.fromEmployeeJSONObject(employee));
			}

			return array;
		} catch (Exception var5) {
			throw var5;
		}
	}

	private JSONObject getRootObject() throws Exception {
		try {
			FileReader reader = this.createFileReaderToFilePath();
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			reader.close();
			return jsonObject;
		} catch (Exception var4) {
			System.out.println("getRootObject exception, trace: " + var4);
			throw var4;
		}
	}

	private JSONArray readEmployyesAsJsonArray() throws Exception {
		try {
			JSONObject jsonObject = this.getRootObject();
			JSONArray jsonArray = (JSONArray) jsonObject.get("workers");
			return jsonArray;
		} catch (Exception var3) {
			System.out.println("readEmployyesAsJsonArray exception, trace: " + var3);
			throw var3;
		}
	}

	private void writeNewEmployeesToJSONFile(JSONArray array) throws Exception {
		JSONObject jsonObject = this.getRootObject();
		jsonObject.replace("workers", array);
		FileWriter fileWriter = this.createFileWriterToFilePath();
		fileWriter.write(jsonObject.toJSONString());
		fileWriter.close();
	}

	private void addEmployeeToJSONFile(Employee employee) throws Exception {
		try {
			JSONArray jsonArray = this.readEmployyesAsJsonArray();
			jsonArray.add(this.fromEmployeeJSONObject(employee));
			this.writeNewEmployeesToJSONFile(jsonArray);
		} catch (Exception var3) {
			throw var3;
		}
	}

	private ArrayList<Employee> getAllEmployeesFromJSONFile() throws Exception {
		ArrayList employees = null;

		try {
			JSONArray jsonArray = this.readEmployyesAsJsonArray();
			employees = this.fromJSONArrayToEmployeesList(jsonArray);
			return employees;
		} catch (Exception var3) {
			System.out.println("getAllEmployeesFromJSONFile exception, stack trace: " + var3);
			throw var3;
		}
	}

	private Employee getEmployeeFromJSONFile(String id) throws Exception {
		try {
			ArrayList<Employee> employees = this.getAllEmployees();
			Iterator var4 = employees.iterator();

			while (var4.hasNext()) {
				Employee employee = (Employee) var4.next();
				if (employee.getId().equals(id)) {
					return employee;
				}
			}

			throw new EmployeeNotExistException(id);
		} catch (Exception var5) {
			System.out.println("getEmployeeFromJSONFile exception, stack trace: " + var5);
			throw var5;
		}
	}

	private void eraseEmployeeFromJSONFile(String id) throws Exception {
		try {
			ArrayList<Shift> shifts = this.getShiftsForEmployee(this.getEmployee(id));
			Iterator var4 = shifts.iterator();

			while (var4.hasNext()) {
				Shift shift = (Shift) var4.next();
				this.removeShift(shift);
			}

			ArrayList<Employee> employees = this.getAllEmployees();

			for (int i = 0; i < employees.size(); ++i) {
				if (((Employee) employees.get(i)).getId().equals(id)) {
					employees.remove(i--);
				}
			}

			this.writeNewEmployeesToJSONFile(this.fromEmployeesListToJSONArray(employees));
		} catch (Exception var5) {
			System.out.println("eraseEmployeeFromJSONFile exception, stack trace: " + var5);
			throw var5;
		}
	}

	private void editEmployeeInJSONFile(Employee employee) throws Exception {
		try {
			ArrayList<Employee> employees = this.getAllEmployees();
			Iterator var4 = employees.iterator();

			while (var4.hasNext()) {
				Employee changedEmployee = (Employee) var4.next();
				if (changedEmployee.getId().contentEquals(employee.getId())) {
					changedEmployee.set(employee);
				}
			}

			this.writeNewEmployeesToJSONFile(this.fromEmployeesListToJSONArray(employees));
		} catch (Exception var5) {
			System.out.println("eraseEmployeeFromJSONFile exception, stack trace: " + var5);
			throw var5;
		}
	}

	public void assignEmployeeToShift(Employee employee, Shift shift) throws Exception {
		try {
			this.assignEmployeeToShiftInJSONFile(shift, employee);
		} catch (Exception var4) {
			throw var4;
		}
	}

	public Shift getShiftByTime(Date startTime, Date endTime) throws Exception {
		try {
			return this.getShiftByTimeFromJSONFile(startTime, endTime);
		} catch (Exception var4) {
			throw var4;
		}
	}

	public ArrayList<Shift> getShiftsForEmployee(Employee employee) throws Exception {
		try {
			ArrayList<Shift> shifts = this.getAllShifts();
			return ShiftsController.filterShiftsByEmployee(employee, shifts);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Shift> getAllShifts(Date startDate, Date endDate) throws Exception {
		try {
			ArrayList<Shift> shifts = this.getAllShifts();
			return ShiftsController.filterShiftsByTimesRange(shifts, startDate, endDate);
		} catch (Exception var4) {
			throw var4;
		}
	}

	public ArrayList<Shift> getAllShifts(Date startDate) throws Exception {
		try {
			ArrayList<Shift> shifts = this.getAllShifts();
			return ShiftsController.filterShiftsByTimesRange(shifts, startDate, new Date(System.currentTimeMillis()));
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Shift> getAllShifts() throws Exception {
		try {
			ArrayList<Shift> shifts = this.getAllShiftsFromJSONFile();
			return shifts;
		} catch (Exception var2) {
			throw var2;
		}
	}

	public void removeShift(Shift shift) throws Exception {
		try {
			this.removeShiftFromJSONFile(shift);
		} catch (Exception var3) {
			throw var3;
		}
	}

	private String fromDateToJSONOutputString(Date date) {
		return this.dateFormat.format(date);
	}

	private JSONObject fromShiftJSONObject(Shift shift) {
		JSONObject object = new JSONObject();
		object.put("employeeId", shift.getEmployee() != null ? shift.getEmployee().getId() : "");
		object.put("startTime", this.fromDateToJSONOutputString(shift.getStartTime()));
		object.put("endTime", this.fromDateToJSONOutputString(shift.getEndTime()));
		return object;
	}

	public Shift getShiftByTimeFromJSONFile(Date startTime, Date endTime) throws Exception {
		try {
			ArrayList<Shift> shifts = this.getAllShifts();
			Iterator var5 = shifts.iterator();

			Shift shift;
			do {
				if (!var5.hasNext()) {
					return null;
				}

				shift = (Shift) var5.next();
			} while (!startTime.equals(shift.getStartTime()) || !endTime.equals(shift.getEndTime()));

			return shift;
		} catch (Exception var6) {
			throw var6;
		}
	}

	private Shift fromJSONObjectToShift(JSONObject object) throws Exception {
		try {
			Date startTime = this.dateFormat.parse((String) object.get("startTime"));
			Date endTime = this.dateFormat.parse((String) object.get("endTime"));
			if (object.containsKey("employeeId") && !object.get("employeeId").equals("")) {
				Employee employee = this.getEmployee((String) object.get("employeeId"));
				return new Shift(startTime, endTime, employee);
			} else {
				return new Shift(startTime, endTime);
			}
		} catch (Exception var5) {
			throw var5;
		}
	}

	private ArrayList<Shift> fromJSONArrayToShiftsList(JSONArray array) throws Exception {
		ArrayList<Shift> shifts = new ArrayList<Shift>();

		try {
			Iterator var4 = array.iterator();

			while (var4.hasNext()) {
				Object object = var4.next();
				if (object instanceof JSONObject) {
					shifts.add(this.fromJSONObjectToShift((JSONObject) object));
				}
			}

			return shifts;
		} catch (Exception var5) {
			throw var5;
		}
	}

	private JSONArray fromShiftsListToJSONArray(ArrayList<Shift> shifts) throws Exception {
		try {
			JSONArray array = new JSONArray();
			Iterator var4 = shifts.iterator();

			while (var4.hasNext()) {
				Shift shift = (Shift) var4.next();
				array.add(this.fromShiftJSONObject(shift));
			}

			return array;
		} catch (Exception var5) {
			throw var5;
		}
	}

	private JSONArray readShiftsAsJsonArray() throws Exception {
		try {
			JSONObject jsonObject = this.getRootObject();
			JSONArray jsonArray = (JSONArray) jsonObject.get("shifts");
			return jsonArray;
		} catch (Exception var3) {
			System.out.println("readShiftsAsJsonArray exception, trace: " + var3);
			throw var3;
		}
	}

	private void writeNewShiftsToJSONFile(JSONArray array) throws Exception {
		JSONObject jsonObject = this.getRootObject();
		FileWriter fileWriter = this.createFileWriterToFilePath();
		jsonObject.replace("shifts", array);
		fileWriter.write(jsonObject.toString());
		fileWriter.close();
	}

	private void removeShiftFromJSONFile(Shift shift) throws Exception {
		try {
			ArrayList<Shift> shifts = this.getAllShiftsFromJSONFile();

			for (int i = 0; i < shifts.size(); ++i) {
				Shift checkedShift = (Shift) shifts.get(i);
				if (checkedShift.getStartTime().equals(shift.getStartTime())
						&& checkedShift.getEndTime().equals(shift.getEndTime())) {
					shifts.remove(i--);
				}
			}

			this.writeNewShiftsToJSONFile(this.fromShiftsListToJSONArray(shifts));
		} catch (Exception var5) {
			throw var5;
		}
	}

	private void assignEmployeeToShiftInJSONFile(Shift shift, Employee employee) throws Exception {
		try {
			ArrayList<Shift> shifts = this.getAllShiftsFromJSONFile();
			boolean found = false;
			Iterator var6 = shifts.iterator();

			Shift newShift;
			while (var6.hasNext()) {
				newShift = (Shift) var6.next();
				if (shift.getStartTime().equals(newShift.getStartTime())
						&& shift.getEndTime().equals(newShift.getEndTime())) {
					newShift.setEmployee(employee);
					found = true;
				}
			}

			if (!found) {
				newShift = new Shift(shift.getStartTime(), shift.getEndTime(), employee);
				shifts.add(newShift);
			}

			this.writeNewShiftsToJSONFile(this.fromShiftsListToJSONArray(shifts));
		} catch (Exception var7) {
			System.err.println("assignEmployeeToShiftInJSONFile " + var7);
			throw var7;
		}
	}

	private ArrayList<Shift> getAllShiftsFromJSONFile() throws Exception {
		try {
			JSONArray jsonArray = this.readShiftsAsJsonArray();
			ArrayList<Shift> shifts = this.fromJSONArrayToShiftsList(jsonArray);
			return shifts;
		} catch (Exception var3) {
			System.err.println("getAllShiftsFromJSONFile " + var3);
			var3.printStackTrace();
			throw var3;
		}
	}

	public void removeShiftsForDay(Date date) throws Exception {
		try {
			date = WeekManager.setHoursOfDay(date, 0);
			ArrayList<Shift> shifts = this.getAllShifts();

			for (int i = 0; i < shifts.size(); ++i) {
				Date startDate = WeekManager.setHoursOfDay(((Shift) shifts.get(i)).getStartTime(), 0);
				if (startDate.equals(date)) {
					shifts.remove(i);
				}
			}

			this.writeNewShiftsToJSONFile(this.fromShiftsListToJSONArray(shifts));
		} catch (Exception var5) {
			throw var5;
		}
	}
}