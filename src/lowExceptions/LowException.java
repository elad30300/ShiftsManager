package lowExceptions;

import entities.Shift;
import java.util.ArrayList;
import java.util.Iterator;

public class LowException extends Exception {
	private String lowDescription = "";
	private ArrayList<Shift> breakedShifts = new ArrayList();

	public LowException(String lowDescription, ArrayList<Shift> breakedShifts) {
		this.lowDescription = lowDescription;
		this.breakedShifts = breakedShifts;
	}

	public String getLowDescription() {
		String shiftsBreakedDetails = "";

		Shift shift;
		for (Iterator var3 = this.breakedShifts.iterator(); var3
				.hasNext(); shiftsBreakedDetails = shiftsBreakedDetails + shift + "\n") {
			shift = (Shift) var3.next();
		}

		return this.lowDescription + "\n" + "משמרות שלא מאפשרות קיום משמרת זו:\n" + shiftsBreakedDetails;
	}

	public void setLowDescription(String lowDescription) {
		this.lowDescription = lowDescription;
	}
}