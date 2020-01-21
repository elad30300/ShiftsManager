package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class WeekViewModel {
	private Date chosenDate;
	private ArrayList<WeekViewModelObserver> observers = new ArrayList();
	private static WeekViewModel sharedInstance = new WeekViewModel();

	public WeekViewModel() {
		this.setChosenDate(WeekManager.getCurrentDate());
	}

	public void addObserver(WeekViewModelObserver observer) {
		this.observers.add(observer);
		observer.onWeekSet();
	}

	public int getChosenWeek() {
		return WeekManager.getWeekNumber(this.chosenDate);
	}

	public void incrementChosenDateByWeek() {
		this.chosenDate = WeekManager.incrementDateByWeek(this.chosenDate);
		this.notifyWeekChange();
	}

	public void decrementChosenDateByWeek() {
		this.chosenDate = WeekManager.decrementDateByWeek(this.chosenDate);
		this.notifyWeekChange();
	}

	public static WeekViewModel getSharedInstance() {
		return sharedInstance;
	}

	public void notifyWeekChange() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			WeekViewModelObserver observer = (WeekViewModelObserver) var2.next();
			observer.onWeekSet();
		}

	}

	public static void setSharedInstance(WeekViewModel sharedInstance) {
		sharedInstance = sharedInstance;
	}

	public Date getChosenDate() {
		if (this.chosenDate == null) {
			this.setChosenDate(WeekManager.getCurrentDate());
		}

		return WeekManager.getCopy(this.chosenDate);
	}

	public void setChosenDate(Date chosenDate) {
		this.chosenDate = WeekManager.getCopy(chosenDate);
	}
	

	public interface WeekViewModelObserver {
		void onWeekSet();
	}
}