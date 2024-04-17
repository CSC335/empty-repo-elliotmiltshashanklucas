package view;

/**
 * The Observer interface defines a method for receiving updates from an
 * observed object.
 */
public interface Observer {
	/**
	 * Called to update the observer about changes in the observed subject's state.
	 */
	public void update();
}
