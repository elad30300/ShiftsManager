package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Shift {
	private Date startTime;
	private Date endTime;
	private Employee employee;

	public Shift(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Shift(Date startTime, Date endTime, Employee employee) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.employee = employee;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String toString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm, E");
		return "זמן התחלה : " + simpleDateFormat.format(this.startTime) + ", זמן סיום : "
				+ simpleDateFormat.format(this.endTime) + ", עובד: " + this.employee;
	}

	public int compareTo(Shift o) {
		if (this.getStartTime().after(o.getStartTime())) {
			return 1;
		} else {
			return this.getStartTime().before(o.getStartTime()) ? -1 : 0;
		}
	}
}
