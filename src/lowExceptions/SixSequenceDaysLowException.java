package lowExceptions;

import entities.Shift;
import java.util.ArrayList;

public class SixSequenceDaysLowException extends LowException {
	public SixSequenceDaysLowException(ArrayList<Shift> breakedShifts) {
		super("ניסיון לשבץ משמרת שביעית ברציפות", breakedShifts);
	}
}