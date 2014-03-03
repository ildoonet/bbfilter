package net.ildoo.app;

import net.ildoo.app.maintab.MainTabScreen;
import net.rim.device.api.applicationcontrol.ApplicationPermissions;
import net.rim.device.api.applicationcontrol.ApplicationPermissionsManager;
import net.rim.device.api.ui.UiApplication;

import com.Localytics.LocalyticsSession.LocalyticsSession;
import com.dabinci.os.DabinciOSUtil;

/**
 * This class extends the UiApplication class, providing a graphical user
 * interface.
 */
public class FilterApp extends UiApplication {
	private final static String APPLICATION_KEY = "8dc6380b0b549ff0a44ce32-c7526966-9d1a-11e3-9754-005cf8cbabd8"; 
    private LocalyticsSession _session = new LocalyticsSession(APPLICATION_KEY);
	
    public LocalyticsSession getLocalyticsSession() {
    	return this._session;
    }
    
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
		
		_session.open();
		_session.upload();
	}
	
	void setPermission() {
		int permissions_list[] = {
				(ApplicationPermissions.PERMISSION_INTERNET),
				(ApplicationPermissions.PERMISSION_WIFI),
				(ApplicationPermissions.PERMISSION_LOCATION_DATA),
				(ApplicationPermissions.PERMISSION_FILE_API),
				(ApplicationPermissions.PERMISSION_MEDIA),
				(ApplicationPermissions.PERMISSION_PHONE),
				(ApplicationPermissions.PERMISSION_CROSS_APPLICATION_COMMUNICATION),
				(ApplicationPermissions.PERMISSION_INPUT_SIMULATION),
			};

		/* Permission Check */
		ApplicationPermissionsManager manager = ApplicationPermissionsManager.getInstance();
		ApplicationPermissions current = manager.getApplicationPermissions();

		ApplicationPermissions permissions = new ApplicationPermissions();

		boolean needRequest = false;
		for (int i = 0; i < permissions_list.length; i++) {
			int int_permission = permissions_list[i];

			int filepermission = int_permission;
			int allow = ApplicationPermissions.VALUE_ALLOW;

			if (current.getPermission(filepermission) != allow) {
				try {
					permissions.addPermission(int_permission);
					needRequest = true;
				} catch (Exception e) {
				}
			}
			// end if
		}// end for i

		if (needRequest) {
			DabinciOSUtil.getInstance().requestPermission(permissions);
			
			try {
				ApplicationPermissionsManager.getInstance().invokePermissionsRequest(permissions);
			} catch (Exception e) {
			}
		}
	}
	
	public boolean requestClose() {
		onExit();
		return super.requestClose();
	}
	
	private void onExit() {
		_session.close();
	}
}
