package gui;

import java.awt.GridLayout;
import java.util.Date;
import model.WeekManager;
import model.WeekViewModel;
import model.WeekViewModel.WeekViewModelObserver;

public class WeekShiftsPanel extends RTLJPanel implements WeekViewModelObserver {
	private WeekViewModel weekViewModel;

	public WeekShiftsPanel(WeekViewModel weekViewModel) {
		this.weekViewModel = weekViewModel;
		this.weekViewModel.addObserver(this);
		this.initialize();
	}

	public void initialize() {
		GridLayout layout = new GridLayout(0, 7, 0, 0);
		this.setLayout(layout);
		this.initializeDayShiftsPanels();
	}

	private void initializeDayShiftsPanels() {
		this.removeAll();
		this.setDayShiftsPanels();
	}

	private void setDayShiftsPanels() {
		Date chosenDate = WeekManager.getCopy(this.weekViewModel.getChosenDate());
		Date startDate = WeekManager.getFirstDateForWeek(chosenDate);
		Date lastDate = WeekManager.getLastDateForWeek(chosenDate);

		for (Date date = WeekManager.getCopy(startDate); !date.after(lastDate); date = WeekManager
				.incrementDateByDays(date, 1)) {
			this.addDayShiftsPanelForDate(date);
		}

	}

	private void addDayShiftsPanelForDate(Date date) {
		DayShiftsPanel dayShiftsPanel = new DayShiftsPanel(date);
		this.add(dayShiftsPanel);
	}

	public void onWeekSet() {
		this.initialize();
	}
}