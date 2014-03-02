package net.ildoo.app.maintab;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DTextButtonField;
import com.dabinci.ui.tab.DTabContent;

public class TabSettings extends DTabContent {

	public void requestRefresh() {
		BitmapField icon = new BitmapField(Bitmap.getBitmapResource("icon.png"), Field.FIELD_HCENTER);
		icon.setMargin(DRes.getPixel(10), 0, 0, 0);
		add(icon);
		
		LabelField lbVersion = new LabelField("Version : " + ApplicationDescriptor.currentApplicationDescriptor().getVersion());
		lbVersion.setMargin(getBtnMargin());
		add(lbVersion);
		
		DTextButtonField btnFeedback = DTextButtonField.newInstance("Feedback(email)", DTextButtonField.TYPE_GREY);
		btnFeedback.setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(Color.WHITE));
		btnFeedback.setMargin(getBtnMarginWithoutBottom());
		btnFeedback.setBorder(getBtnBorderWithoutBottom());
		btnFeedback.setFontSize(DTextButtonField.TEXT_SIZE_NORMAL);
		btnFeedback.setHeight(DTextButtonField.DEFAULT_HEIGHT_NORMAL);
		btnFeedback.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				try {
					ApplicationDescriptor ad = ApplicationDescriptor.currentApplicationDescriptor();
					String this_version = ad.getVersion();

					String title = "About 'Dabinci Cam'";
					String contents = "Device ID : "
							+ Integer.toHexString(DeviceInfo.getDeviceId())
							+ "\n" + "Device Name : "
							+ DeviceInfo.getDeviceName() + "\n"
							+ "Os Version :  "
							+ DeviceInfo.getPlatformVersion() + "("
							+ DeviceInfo.getSoftwareVersion() + ")\n"
							+ "App Version : " + this_version + "\n"
							+ "Write what you want to say to me here...";
					Invoke.invokeApplication(Invoke.APP_TYPE_MESSAGES, new MessageArguments(MessageArguments.ARG_NEW, "bbapps@ildoo.net", title, contents));

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
			}
		});
		add(btnFeedback);
		
		DTextButtonField btnApps = DTextButtonField.newInstance("See other apps!", DTextButtonField.TYPE_GREY);
		btnApps.setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(Color.WHITE));
		btnApps.setMargin(getBtnMarginWithoutTop());
		btnApps.setBorder(getBtnBorder());
		btnApps.setFontSize(DTextButtonField.TEXT_SIZE_NORMAL);
		btnApps.setHeight(DTextButtonField.DEFAULT_HEIGHT_NORMAL);
		btnApps.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				Browser.getDefaultSession().displayPage(
								"http://appworld.blackberry.com/webstore/vendor/15534/?lang=en");
				Browser.getDefaultSession().showBrowser();
			}
		});
		add(btnApps);
	}
}
