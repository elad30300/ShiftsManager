package lowExceptions;

import entities.Shift;
import java.util.ArrayList;

public class TwoSeuqenceShiftsLowException extends LowException {
	public TwoSeuqenceShiftsLowException(ArrayList<Shift> breakedShifts) {
		super("ניסיון לשבץ עובד בשני משמרות ברציפות", breakedShifts);
	}
}