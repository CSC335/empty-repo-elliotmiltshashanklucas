package interfaces;

/**
 * The Action interface provides a single method, onAction, which is intended to
 * encapsulate any action or behavior that needs to be executed. It is designed
 * to be used as a functional interface within the context of event handling or
 * similar scenarios where a callback is required.
 */
public interface Action {
	/**
	 * Performs the action defined by this Action instance.
	 */
	public void onAction();
}
