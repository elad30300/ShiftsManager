package lowExceptions;

import entities.Shift;
import java.util.ArrayList;

public class SplitNightShiftsInTwoWeeksLowException extends LowException {
	public SplitNightShiftsInTwoWeeksLowException(ArrayList<Shift> breakedShifts) {
		super("ניסיון לשבץ לעובד בשבועיים 7 משמרות לילה לא מפוצלות (מותר עד 5 ו-2 בשבועיים)", breakedShifts);
	}
}