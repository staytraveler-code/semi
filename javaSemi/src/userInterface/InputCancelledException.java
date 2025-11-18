package userInterface;

public class InputCancelledException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InputCancelledException() {
		
	}
	
	public InputCancelledException(String msg) {
		super(msg);
	}
}
