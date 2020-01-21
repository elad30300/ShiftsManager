package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.WeekManager;
import model.WeekViewModel;
import model.WeekViewModel.WeekViewModelObserver;

public class WeekChooserPanel extends RTLJPanel implements WeekViewModelObserver {
	private JLabel weekDatesLabel = new JLabel("New label");
	private WeekViewModel weekViewModel;
	private ActionListener backWeekActionListener = new BackWeekActionListener();
	private ActionListener nextWeekActionListener = new NextWeekActionListener();

	public WeekChooserPanel(WeekViewModel weekViewModel) {
		this.weekViewModel = weekViewModel;
		this.initialize();
	}

	private void initialize() {
		this.weekViewModel.addObserver(this);
//		this.setLayout(new BorderLayout(0, 0));
		this.setLayout(new FlowLayout());
		JButton backWeekButton = new JButton(">");
		backWeekButton.addActionListener(this.backWeekActionListener);
		this.add(backWeekButton);
		
		this.weekDatesLabel = new JLabel("New label");
		this.weekDatesLabel.setHorizontalAlignment(0);
		this.add(this.weekDatesLabel);
		
//		this.add(backWeekButton, "East");
		JButton nextWeekButton = new JButton("<");
		nextWeekButton.addActionListener(this.nextWeekActionListener);
		this.add(nextWeekButton);
//		this.add(nextWeekButton, "West");
		this.initializeWeek();
	}

	private void onBackWeekClicked() {
		if (this.weekViewModel != null) {
			this.weekViewModel.decrementChosenDateByWeek();
		}

	}

	private void onNextWeekClicked() {
		if (this.weekViewModel != null) {
			this.weekViewModel.incrementChosenDateByWeek();
		}

	}

	private void initializeWeek() {
		this.onWeekSet();
	}

	public void onWeekSet() {
		Date chosenDate = this.weekViewModel.getChosenDate();
		Calendar calendar = WeekManager.getCalendarForDate(chosenDate);
		if (chosenDate == null) {
			String weekDatesText = "לא נבחר שבוע";
			this.weekDatesLabel.setText(weekDatesText);
		} else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = WeekManager.getFirstDateForWeek(chosenDate);
			String startDateString = simpleDateFormat.format(startDate);
			Date endDate = WeekManager.getLastDateForWeek(chosenDate);
			String endDateString = simpleDateFormat.format(endDate);
			String weekDatesText = startDateString + " עד " + endDateString;
			this.weekDatesLabel.setText(weekDatesText);
		}
	}
	
	private class BackWeekActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			onBackWeekClicked();
		}
	}
	
	private class NextWeekActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			onNextWeekClicked();
		}
	}
}