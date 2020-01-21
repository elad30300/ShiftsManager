package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controllers.LocalJSONDBController;
import controllers.ShiftsController;
import entities.Shift;
import model.ShiftsViewModel;
import model.TwoShiftsDayViewModel;
import model.TwoShiftsDayViewModel.TwoShiftsDayModeViewModelObserver;
import model.WeekManager;
import model.ShiftsViewModel.ShiftsViewModelObserver;

public class DayShiftsPanel extends RTLJPanel implements ShiftsViewModelObserver {
	private Date date;
	private ShiftsViewModel shiftsViewModel = ShiftsViewModel.getSharedinstance();
	private TwoShiftsDayViewModel twoShiftsDayViewModel = new TwoShiftsDayViewModel();
	private UpperPanel upperPanel;
	private ShiftsPanel shiftsPanel;

	public DayShiftsPanel(Date date) {
		this.date = date;
		this.setLayout(new BorderLayout());
		this.initializeUpperPanel();
		this.initializeShiftsPanel();
	}

	public void initializeUpperPanel() {
		this.upperPanel = new UpperPanel(this, this.date, this.twoShiftsDayViewModel);
		this.add(this.upperPanel, "North");
	}

	public void initializeShiftsPanel() {
		if (this.shiftsPanel != null) {
			this.remove(this.shiftsPanel);
		}

		this.shiftsPanel = new ShiftsPanel(this.date, this.twoShiftsDayViewModel);
		this.add(this.shiftsPanel);
	}

	public void onShiftsUpdated() {
	}

	public void onFetchFailed() {
	}

	private class ShiftsPanel extends RTLJPanel implements TwoShiftsDayModeViewModelObserver {
		private Date date;
		private TwoShiftsDayViewModel twoShiftsDayViewModel;

		public ShiftsPanel(Date date, TwoShiftsDayViewModel twoShiftsDayViewModel) {
			this.date = date;
			this.twoShiftsDayViewModel = twoShiftsDayViewModel;
			this.twoShiftsDayViewModel.addObserver(this);
			GridLayout layout = new GridLayout(0, 1, 0, 0);
			this.setLayout(layout);
			this.setShiftCellsPanels();
		}

		public void setShiftCellsPanels() {
			Date startTime = WeekManager.getCopy(this.date);
			ArrayList<Shift> shifts = new ArrayList<Shift>();

			try {
				Date endTime;
				if (this.twoShiftsDayViewModel.isTwoShiftsDay()) {
					startTime = WeekManager.setTimeInDay(startTime, 7, 0);
					endTime = WeekManager.incrementDateByHours(startTime, 12);
					shifts.add(new Shift(startTime, endTime));
					shifts.add(new Shift(endTime, WeekManager.incrementDateByHours(endTime, 12)));
				} else {
					startTime = WeekManager.setTimeInDay(startTime, 7, 0);
					endTime = WeekManager.incrementDateByHours(startTime, 8);
					shifts.add(new Shift(startTime, endTime));
					startTime = endTime;
					endTime = WeekManager.incrementDateByHours(endTime, 8);
					shifts.add(new Shift(startTime, endTime));
					startTime = endTime;
					endTime = WeekManager.incrementDateByHours(endTime, 8);
					shifts.add(new Shift(startTime, endTime));
				}

				Iterator var5 = shifts.iterator();

				while (var5.hasNext()) {
					Shift shift = (Shift) var5.next();
					this.add(new ShiftCellPanel(shift));
				}
			} catch (Exception var6) {
				System.out.println("DayShiftsPanel exception: " + var6 + " " + var6.getStackTrace());
				var6.printStackTrace();
			}

		}

		public void onTwoShiftsDayIndicatorChanged() {
			this.removeAll();
			this.setShiftCellsPanels();
			GUIManager.refreshComponent(this);
		}
	}

	private class UpperPanel extends RTLJPanel implements TwoShiftsDayModeViewModelObserver {
		private Date date;
		private TwoShiftsDayViewModel twoShiftsDayViewModel;
		private JLabel dayNameLabel;
		private JCheckBox twoShiftsDayCheckBox;

		public UpperPanel(DayShiftsPanel var1, Date date, TwoShiftsDayViewModel twoShiftsDayViewModel) {
			this.dayNameLabel = new JLabel("", SwingUtilities.CENTER);
			this.twoShiftsDayCheckBox = new JCheckBox();
			this.date = date;
			this.twoShiftsDayViewModel = twoShiftsDayViewModel;
			this.twoShiftsDayViewModel.addObserver(this);
			this.initializeGeneralUI();
			this.initializeDayNameLabel();
			this.initializeTwoShiftsDayCheckBox();
			this.add(this.twoShiftsDayCheckBox);
			this.add(this.dayNameLabel);
		}

		private void initializeGeneralUI() {
			this.setLayout(new GridLayout(0, 1, 0, 0));
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}

		private void initializeDayNameLabel() {
			this.setDayNameLabel(this.date);
		}

		public void setDayNameLabel(Date date) {
			try {
				this.dayNameLabel.setText(WeekManager.getDayOfWeekName(WeekManager.getDayOfWeek(date)));
			} catch (Exception ex) {
				this.dayNameLabel.setText("לא ידוע");
			}

		}

		public void initializeTwoShiftsDayCheckBox() {
			this.twoShiftsDayCheckBox.setText("2 משמרות ביום");
			this.twoShiftsDayCheckBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			this.twoShiftsDayCheckBox.addActionListener(new TwoShiftsCheckBoxActionListener());
			this.automaticShiftsModeCheck();
		}

		public void setTwoShiftsDayCheckBoxValue(boolean value) {
			this.twoShiftsDayCheckBox.setSelected(value);
		}

		public void onTwoShiftsCheckboxClicked() {
			int result = JOptionPane.showConfirmDialog(null,
					"פעולה זו תמחק את כל המשמרות אשר נקבעו ליום זה, האם אתה בטוח בכך?");
			if (result == 0) {
				this.onChangeShiftModeConfirmed();
			} else {
				this.onChangeShiftModeNotConfirmed();
			}
		}

		private void changeShiftMode(boolean isTwoShiftMode) {
			(new ChangeShiftsModeThread()).start(isTwoShiftMode);
		}

		private void automaticShiftsModeCheck() {
			(new GetShiftsModeThread()).start();
		}

		private void onChangeShiftModeConfirmed() {
			this.changeShiftMode(this.twoShiftsDayCheckBox.isSelected());
		}

		private void onChangeShiftModeNotConfirmed() {
			this.twoShiftsDayCheckBox.setSelected(!this.twoShiftsDayCheckBox.isSelected());
		}

		private void handleChangeShiftModeSuccess(boolean isTwoShiftMode) {
			this.twoShiftsDayViewModel.setTwoShiftsDay(isTwoShiftMode);
		}

		private void handleChangeShiftModeFail() {
			JOptionPane.showConfirmDialog(null, "שינוי מספר המשמרות ביום נכשל, אנא נסה שוב");
		}

		private void handleCheckShiftModeFail() {
			JOptionPane.showConfirmDialog(null, "בדיקת מספר המשמרות ביום נכשל, אנא נסה שוב");
		}

		public Date getDate() {
			return this.date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public void onTwoShiftsDayIndicatorChanged() {
			this.setTwoShiftsDayCheckBoxValue(this.twoShiftsDayViewModel.isTwoShiftsDay());
		}

		private class TwoShiftsCheckBoxActionListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				onTwoShiftsCheckboxClicked();
			}
		}

		private class ChangeShiftsModeThread implements Runnable {
			private boolean value;

			public void run() {
				try {
					ShiftsController.getSharedInstance().removeShiftsForDay(date);
					handleChangeShiftModeSuccess(value);
				} catch (Exception var2) {
					handleChangeShiftModeFail();
				}

			}

			public void start(boolean value) {
				this.value = value;
				this.run();
			}
		}
		
		private class GetShiftsModeThread implements Runnable {


			public void run() {
				try {
					boolean isTwoShiftsMode = ShiftsController.isTwoShiftsModeDay(date);
					handleChangeShiftModeSuccess(isTwoShiftsMode);
				} catch (Exception var2) {
					handleCheckShiftModeFail();
				}

			}

			public void start() {
				this.run();
			}
		}
	}
}