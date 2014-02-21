package net.ildoo.app.maintab;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.button.DTextButtonField;
import com.dabinci.ui.tab.DTabContent;

public class TabSettings extends DTabContent {

	public void requestRefresh() {
		DTextButtonField btnInfo = DTextButtonField.newInstance("Info", DTextButtonField.TYPE_GREY);
		btnInfo.setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(Color.WHITE));
		btnInfo.setMargin(getBtnMargin());
		btnInfo.setBorder(getBtnBorder());
		btnInfo.setFontSize(DTextButtonField.TEXT_SIZE_NORMAL);
		btnInfo.setHeight(DTextButtonField.DEFAULT_HEIGHT_NORMAL);
		btnInfo.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
			}
		});
		add(btnInfo);
	}
}
