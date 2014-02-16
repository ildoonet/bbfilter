package net.ildoo.app;

import net.ildoo.app.maintab.MainTabScreen;
import net.rim.device.api.applicationcontrol.ApplicationPermissions;
import net.rim.device.api.applicationcontrol.ApplicationPermissionsManager;
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
		setPermission();
		pushScreen(new MainTabScreen());
	}
	
	void setPermission() {
		int permissions_list[] = {
				(ApplicationPermissions.PERMISSION_INTERNET),
				(ApplicationPermissions.PERMISSION_WIFI),
				(ApplicationPermissions.PERMISSION_LOCATION_DATA),
				(ApplicationPermissions.PERMISSION_FILE_API),
				(ApplicationPermissions.PERMISSION_MEDIA),
				(ApplicationPermissions.PERMISSION_PHONE),
				(ApplicationPermissions.PERMISSION_CROSS_APPLICATION_COMMUNICATION) };

		/* Permission Check */
		ApplicationPermissionsManager manager = ApplicationPermissionsManager
				.getInstance();
		ApplicationPermissions current = manager.getApplicationPermissions();

		ApplicationPermissions permissions = new ApplicationPermissions();

		for (int i = 0; i < permissions_list.length; i++) {
			int int_permission = permissions_list[i];

			int filepermission = int_permission;
			int allow = ApplicationPermissions.VALUE_ALLOW;

			if (current.getPermission(filepermission) != allow) {
				try {
					permissions.addPermission(int_permission);
				} catch (Exception e) {
				}
			}
			// end if
		}// end for i

		try {
			ApplicationPermissionsManager.getInstance()
					.invokePermissionsRequest(permissions);
		} catch (Exception e) {
		}
	}
}
