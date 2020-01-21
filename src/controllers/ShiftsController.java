package controllers;

import entities.Employee;
import entities.Shift;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import lowExceptions.LowException;
import lowExceptions.NoLowException;
import lowExceptions.SevenNightShiftsInTwoWeeksLowException;
import lowExceptions.SixSequenceDaysLowException;
import lowExceptions.SplitNightShiftsInTwoWeeksLowException;
import lowExceptions.ThreeSequenceSaturdaysLowException;
import lowExceptions.TwoSeuqenceShiftsLowException;
import model.WeekManager;

public class ShiftsController {
	private static ShiftsController sharedInstance = new ShiftsController();
//	private Delegate delegate;
	private DataController dataController = DataController.getSharedInstance();

	public static ShiftsController getSharedInstance() {
		return sharedInstance;
	}

//	public void setDelegate(Delegate delegate) {
//		this.delegate = delegate;
//	}
//
//	private void notifyException(Exception ex) {
//		if (this.delegate != null) {
//			this.delegate.handleShiftsControllerException(ex);
//		}
//
//	}

	public void assign(Employee employee, Shift shift) throws Exception {
		try {
			LowException breakedLow = this.getLowBreakByAssignment(shift, employee);
			if (!(breakedLow instanceof NoLowException)) {
				throw breakedLow;
			} else {
				this.dataController.assignEmployeeToShift(employee, shift);
			}
		} catch (Exception var4) {
			throw var4;
		}
	}

	public Shift getShiftByTime(Date startTime, Date endTime) {
		try {
			return this.dataController.getShiftByTime(startTime, endTime);
		} catch (Exception var4) {
//			this.notifyException(var4);
			return null;
		}
	}

	public ArrayList<Shift> getShiftsForEmployee(Employee employee) {
		try {
			return this.dataController.getShiftsForEmployee(employee);
		} catch (Exception var3) {
//			this.notifyException(var3);
			return null;
		}
	}

	public ArrayList<Shift> getAll() {
		try {
			return this.dataController.getAllShifts();
		} catch (Exception var2) {
//			this.notifyException(var2);
			return null;
		}
	}

	public void remove(Shift shift) throws Exception {
		try {
			this.dataController.removeShift(shift);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Shift> shiftsBreakedBySixSequenceDaysLow(Shift shift, Employee employee) throws Exception {
		try {
			int maxNumberOfShiftsAllowed = 6;
			Date startDate = shift.getStartTime();
			Date earliestDateToCheck = WeekManager.setHoursOfDay(WeekManager.decrementDateByDays(startDate, 6), 0);
			Date latestDateToCheck = WeekManager.setHoursOfDay(startDate, 23);

			for (Date finalDate = WeekManager.setHoursOfDay(WeekManager.incrementDateByDays(startDate, 6),
					23); !latestDateToCheck.after(
							finalDate); latestDateToCheck = WeekManager.incrementDateByDays(latestDateToCheck, 1)) {
				ArrayList<Shift> shifts = filterShiftsByEmployee(employee,
						this.dataController.getAllShifts(earliestDateToCheck, latestDateToCheck));
				if (shifts.size() >= 6) {
					return shifts;
				}

				earliestDateToCheck = WeekManager.incrementDateByDays(earliestDateToCheck, 1);
			}

			return new ArrayList<Shift>();
		} catch (Exception var9) {
			throw var9;
		}
	}

	public boolean isSaturdayShift(Shift shift) {
		return WeekManager.getCalendarForDate(shift.getStartTime()).get(7) == 7;
	}

	public ArrayList<Shift> shiftsBreakedBySaturdayThreeSequenceWeeksLow(Shift shift, Employee employee)
			throws Exception {
		try {
			if (!this.isSaturdayShift(shift)) {
				return new ArrayList<Shift>();
			} else {
				int maxNumberOfShiftsAllowed = 3;
				Date startDate = shift.getStartTime();
				Date earliestDateToCheck = WeekManager.setHoursOfDay(WeekManager.decrementDateByWeeks(startDate, 3), 0);
				Date latestDateToCheck = WeekManager.setHoursOfDay(startDate, 23);

				for (Date finalDate = WeekManager.setHoursOfDay(WeekManager.incrementDateByWeeks(startDate, 3),
						23); !latestDateToCheck.after(finalDate); latestDateToCheck = WeekManager
								.incrementDateByWeeks(latestDateToCheck, 1)) {
					ArrayList<Shift> shifts = filterShiftsByEmployee(employee,
							this.dataController.getAllShifts(earliestDateToCheck, latestDateToCheck));
					shifts = filterShiftsByDayOfWeek(shifts, 7);
					if (shifts.size() >= maxNumberOfShiftsAllowed) {
						return shifts;
					}

					earliestDateToCheck = WeekManager.incrementDateByWeeks(earliestDateToCheck, 1);
				}

				return new ArrayList<Shift>();
			}
		} catch (Exception var9) {
			throw var9;
		}
	}

	public boolean isNightShift(Shift shift) throws Exception {
		try {
			Date timeToCheck = WeekManager.incrementDateByDays(shift.getStartTime(), 1);
			timeToCheck = WeekManager.setHoursOfDay(timeToCheck, 0);
			return shift.getStartTime().before(timeToCheck) && shift.getEndTime().after(timeToCheck);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public ArrayList<Shift> shiftsBreakedBySevenNightsInTwoWeeksLow(Shift shift, Employee employee) throws Exception {
		try {
			if (!this.isNightShift(shift)) {
				return new ArrayList<Shift>();
			} else {
				int maxNumberOfShiftsAllowed = 7;
				Date startDate = shift.getStartTime();
				Date earliestDateToCheck = WeekManager.setHoursOfDay(WeekManager.decrementDateByWeeks(startDate, 1), 0);
				earliestDateToCheck = WeekManager.setDayOfWeek(earliestDateToCheck, 1);
				Date latestDateToCheck = WeekManager.setHoursOfDay(startDate, 23);
				latestDateToCheck = WeekManager.setDayOfWeek(latestDateToCheck, 7);

				for (Date finalDate = WeekManager.setHoursOfDay(WeekManager.incrementDateByWeeks(latestDateToCheck, 1),
						23); !latestDateToCheck.after(finalDate); latestDateToCheck = WeekManager
								.incrementDateByWeeks(latestDateToCheck, 1)) {
					ArrayList<Shift> shifts = filterShiftsByEmployee(employee,
							this.dataController.getAllShifts(earliestDateToCheck, latestDateToCheck));
					if (shifts.size() >= maxNumberOfShiftsAllowed) {
						return shifts;
					}

					earliestDateToCheck = WeekManager.incrementDateByWeeks(earliestDateToCheck, 1);
				}

				return new ArrayList<Shift>();
			}
		} catch (Exception var9) {
			throw var9;
		}
	}

	public ArrayList<Shift> shiftsBreakedBySevenSplitNightsInTwoWeeksLow(Shift shift, Employee employee)
			throws Exception {
		try {
			if (this.isNightShift(shift)) {
				int maxNumberOfShiftsAllowed = 5;
				Date startDate = shift.getStartTime();
				Date earliestDateToCheck = WeekManager.setHoursOfDay(WeekManager.decrementDateByWeeks(startDate, 1), 0);
				earliestDateToCheck = WeekManager.setDayOfWeek(earliestDateToCheck, 1);
				Date latestDateToCheck = WeekManager.setHoursOfDay(WeekManager.getCopy(earliestDateToCheck), 23);
				latestDateToCheck = WeekManager.setDayOfWeek(latestDateToCheck, 7);
				Date finalDate = WeekManager.setHoursOfDay(WeekManager.incrementDateByWeeks(latestDateToCheck, 1), 23);
				if (!latestDateToCheck.after(finalDate)) {
					ArrayList<Shift> shifts = this.dataController.getAllShifts(earliestDateToCheck, latestDateToCheck);
					shifts = filterShiftsByEmployee(employee, shifts);
					int numberOfNightShiftsInPreviousWeek = shifts.size();
					earliestDateToCheck = WeekManager.incrementDateByWeeks(earliestDateToCheck, 1);
					latestDateToCheck = WeekManager.incrementDateByWeeks(latestDateToCheck, 1);
					shifts = this.dataController.getAllShifts(earliestDateToCheck, latestDateToCheck);
					shifts = filterShiftsByEmployee(employee, shifts);
					int numberOfNightShiftsInCurrentWeek = shifts.size();
					if (numberOfNightShiftsInPreviousWeek == maxNumberOfShiftsAllowed && numberOfNightShiftsInCurrentWeek < 7 - maxNumberOfShiftsAllowed) {
						return shifts;
					} else {
						return numberOfNightShiftsInCurrentWeek >= maxNumberOfShiftsAllowed ? shifts : new ArrayList<Shift>();
					}
				} else {
					return new ArrayList<Shift>();
				}
			} else {
				return new ArrayList<Shift>();
			}
		} catch (Exception var11) {
			throw var11;
		}
	}

	public ArrayList<Shift> shiftsBreakedByTwoShiftsBreakLow(Shift shift, Employee employee) throws Exception {
		try {
			int maxNumberOfShiftsAllowed = 1;
			Date startDate = shift.getStartTime();
			Date earliestDateToCheck = WeekManager.decrementDateByHours(startDate, 12);
			Date latestDateToCheck = WeekManager.getCopy(startDate);
			ArrayList<Shift> shifts = filterShiftsByEmployee(employee,
					this.dataController.getAllShifts(earliestDateToCheck, latestDateToCheck));
			if (shifts.size() >= 1) {
				return shifts;
			} else {
				earliestDateToCheck = WeekManager.getCopy(startDate);
				latestDateToCheck = WeekManager.incrementDateByHours(startDate, 12);
				shifts = filterShiftsByEmployee(employee,
						this.dataController.getAllShifts(earliestDateToCheck, latestDateToCheck));
				return shifts.size() >= maxNumberOfShiftsAllowed ? shifts : new ArrayList<Shift>();
			}
		} catch (Exception var8) {
			throw var8;
		}
	}

	public LowException getLowBreakByAssignment(Shift shift, Employee employee) throws Exception {
		try {
			new ArrayList<Shift>();
			ArrayList<Shift> breakedShifts = this.shiftsBreakedByTwoShiftsBreakLow(shift, employee);
			if (breakedShifts.size() > 0) {
				return new TwoSeuqenceShiftsLowException(breakedShifts);
			}

			breakedShifts = this.shiftsBreakedBySixSequenceDaysLow(shift, employee);
			if (breakedShifts.size() > 0) {
				return new SixSequenceDaysLowException(breakedShifts);
			}

			breakedShifts = this.shiftsBreakedBySaturdayThreeSequenceWeeksLow(shift, employee);
			if (breakedShifts.size() > 0) {
				return new ThreeSequenceSaturdaysLowException(breakedShifts);
			}

			breakedShifts = this.shiftsBreakedBySevenNightsInTwoWeeksLow(shift, employee);
			if (breakedShifts.size() > 0) {
				return new SevenNightShiftsInTwoWeeksLowException(breakedShifts);
			}

			breakedShifts = this.shiftsBreakedBySevenSplitNightsInTwoWeeksLow(shift, employee);
			if (breakedShifts.size() > 0) {
				return new SplitNightShiftsInTwoWeeksLowException(breakedShifts);
			}
		} catch (Exception var4) {
			throw var4;
		}

		return new NoLowException();
	}

	public static ArrayList<Shift> filterShiftsByEmployee(Employee employee, ArrayList<Shift> shifts) {
		for (int i = 0; i < shifts.size(); ++i) {
			if (!((Shift) shifts.get(i)).getEmployee().getId().equals(employee.getId())) {
				shifts.remove(i--);
			}
		}

		return shifts;
	}

	public static ArrayList<Shift> filterShiftsByTimesRange(ArrayList<Shift> shifts, Date firstTime, Date lastTime) {
		for (int i = 0; i < shifts.size(); ++i) {
			Shift shift = (Shift) shifts.get(i);
			if (shift.getStartTime().before(firstTime) || shift.getStartTime().after(lastTime)) {
				shifts.remove(i--);
			}
		}

		return shifts;
	}

	public static ArrayList<Shift> filterShiftsByDayOfWeek(ArrayList<Shift> shifts, int dayOfWeek) {
		Calendar calendar = Calendar.getInstance();

		for (int i = 0; i < shifts.size(); ++i) {
			Shift shift = (Shift) shifts.get(i);
			if (WeekManager.getCalendarForDate(shift.getStartTime()).get(7) != dayOfWeek) {
				shifts.remove(i--);
			}
		}

		return shifts;
	}

	public static ArrayList<Shift> filterShiftsByDay(ArrayList<Shift> shifts, Date date) throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
			date = WeekManager.setHoursOfDay(date, 0);

			for (int i = 0; i < shifts.size(); ++i) {
				Shift shift = (Shift) shifts.get(i);
				Date startDate = WeekManager.setHoursOfDay(shift.getStartTime(), 0);
				if (!startDate.equals(date)) {
					shifts.remove(i--);
				}
			}

			return shifts;
		} catch (Exception var6) {
			throw var6;
		}
	}

	public void removeShiftsForDay(Date date) throws Exception {
		try {
			this.dataController.removeShiftsForDay(date);
		} catch (Exception var3) {
			throw var3;
		}
	}

	public static boolean isTwoShiftsModeDay(Date date) throws Exception {
		try {
			ArrayList<Shift> shifts = getSharedInstance().getAll();
			shifts = filterShiftsByDay(shifts, date);
			if (shifts.size() > 0) {
				Shift shift = (Shift) shifts.get(0);
				int startHour = WeekManager.getHourOfDay(shift.getStartTime());
				int endHour = WeekManager.getHourOfDay(shift.getEndTime());
				if (startHour == 19 || endHour == 19) {
					return true;
				}
			}

			return false;
		} catch (Exception var5) {
			throw var5;
		}
	}
}