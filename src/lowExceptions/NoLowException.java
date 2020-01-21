package lowExceptions;

import java.util.ArrayList;

public class NoLowException extends LowException {
	public NoLowException() {
		super("שום חוק לא הופר", new ArrayList());
	}
}