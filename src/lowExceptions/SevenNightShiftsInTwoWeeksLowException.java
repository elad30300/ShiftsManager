package lowExceptions;

import entities.Shift;
import java.util.ArrayList;

public class SevenNightShiftsInTwoWeeksLowException extends LowException {
	public SevenNightShiftsInTwoWeeksLowException(ArrayList<Shift> breakedShifts) {
		super("ניסיון לשבץ עובד במשמרת לילה שמינית בשבועיים", breakedShifts);
	}
}