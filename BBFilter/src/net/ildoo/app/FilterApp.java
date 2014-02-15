package net.ildoo.app;

import net.ildoo.app.maintab.MainTabScreen;
import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a graphical user
 * interface.
 */
public class FilterApp extends UiApplication {
	/**
	 * Entry point for application
	 * 
	 * @param args
	 *            Command line arguments (not used)
	 */
	public static void main(String[] args) {
		// Create a new instance of the application and make the currently
		// running thread the application's event dispatch thread.
		FilterApp theApp = new FilterApp();
		theApp.enterEventDispatcher();
	}

	/**
	 * Creates a new FilterApp object
	 */
	public FilterApp() {
		// Push a screen onto the UI stack for rendering.
		pushScreen(new MainTabScreen());
	}
}
