package lowExceptions;

import entities.Shift;
import java.util.ArrayList;

public class ThreeSequenceSaturdaysLowException extends LowException {
	public ThreeSequenceSaturdaysLowException(ArrayList<Shift> breakedShifts) {
		super("ניסיון לשבץ משמרת שבת פעם רביעית ברציפות", breakedShifts);
	}
}