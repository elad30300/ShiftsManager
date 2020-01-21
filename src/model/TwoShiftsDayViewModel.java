package model;

import java.util.ArrayList;
import java.util.Iterator;

public class TwoShiftsDayViewModel {
	private boolean isTwoShiftsDay;
	private ArrayList<TwoShiftsDayModeViewModelObserver> observers = new ArrayList();

	public void addObserver(TwoShiftsDayModeViewModelObserver observer) {
		this.observers.add(observer);
	}

	public TwoShiftsDayViewModel() {
		this.setTwoShiftsDay(false);
	}

	public boolean isTwoShiftsDay() {
		return this.isTwoShiftsDay;
	}

	public void setTwoShiftsDay(boolean isTwoShiftsDay) {
		this.isTwoShiftsDay = isTwoShiftsDay;
		this.notifyObserversIndicatorChanged();
	}

	public void notifyObserversIndicatorChanged() {
		Iterator var2 = this.observers.iterator();

		while (var2.hasNext()) {
			TwoShiftsDayModeViewModelObserver observer = (TwoShiftsDayModeViewModelObserver) var2.next();
			observer.onTwoShiftsDayIndicatorChanged();
		}

	}
	
	public interface TwoShiftsDayModeViewModelObserver {
		void onTwoShiftsDayIndicatorChanged();
	}
}