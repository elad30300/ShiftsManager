package model;

import controllers.DataController;
import entities.Shift;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import model.ShiftsViewModel.ShiftsViewModelObserver;
import model.WeekViewModel.WeekViewModelObserver;

public class ShiftsViewModel implements WeekViewModelObserver {
	private ArrayList<Shift> weekShifts = new ArrayList();
	private ArrayList<ShiftsViewModelObserver> observers = new ArrayList();
	private WeekViewModel weekViewModel = WeekViewModel.getSharedInstance();
	private static final ShiftsViewModel sharedInstance = new ShiftsViewModel();

	public void addObserver(ShiftsViewModelObserver observer) {
		this.observers.add(observer);
		observer.onShiftsUpdated();
	}

	public ShiftsViewModel() {
		this.fetchWeekShifts(this.weekViewModel.getChosenDate());
	}

	private void fetchWeekShifts(Date date) {
		try {
			Date startDate = WeekManager.getFirstDateForWeek(date);
			Date endDate = WeekManager.getLastDateForWeek(date);
			this.weekShifts = DataController.getSharedInstance().getAllShifts(startDate, endDate);
			this.notifyObserversThatShiftsChanged();
		} catch (Exception var4) {
			this.notifyObserversThatFetchFailed();
		}

	}

	public void updateWeekShifts() {
		this.onWeekSet();
	}

	public void onWeekSet() {
		Date chosenDate = WeekManager.getCopy(this.weekViewModel.getChosenDate());
		this.fetchWeekShifts(chosenDate);
	}

	private void notifyObserversThatShiftsChanged() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			ShiftsViewModelObserver observer = (ShiftsViewModelObserver) var2.next();
			observer.onShiftsUpdated();
		}

	}

	private void notifyObserversThatFetchFailed() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			ShiftsViewModelObserver observer = (ShiftsViewModelObserver) var2.next();
			observer.onFetchFailed();
		}

	}

	public ArrayList<Shift> getWeekShifts() {
		return this.weekShifts;
	}

	public void setWeekShifts(ArrayList<Shift> weekShifts) {
		this.weekShifts = weekShifts;
	}

	public static ShiftsViewModel getSharedinstance() {
		return sharedInstance;
	}
	

	public interface ShiftsViewModelObserver {
		void onShiftsUpdated();

		void onFetchFailed();
	}
}