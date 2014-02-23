package net.ildoo.app.maintab;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.dabinci.ui.DResolution;
import com.dabinci.ui.manager.DCenterManager;
import com.dabinci.ui.tab.DTabContent;

public class TabStore extends DTabContent {
	public TabStore() {
		super(Field.USE_ALL_WIDTH | Manager.NO_VERTICAL_SCROLL);
	}
	
	public void requestRefresh() {
		deleteAll();
		
		DCenterManager manager = new DCenterManager();
		
		VerticalFieldManager vfm = new VerticalFieldManager(Manager.NO_VERTICAL_SCROLL);
		Bitmap iconBmp = DResolution.getBitmap(Bitmap.getBitmapResource("ico_alert_grey.png"));
		vfm.add(new BitmapField(iconBmp, Field.FIELD_HCENTER));
		vfm.add(new LabelField("Filter Store is coming soon", Field.FIELD_HCENTER));
		vfm.add(new NullField(Field.FOCUSABLE));
		
		manager.add(vfm);
		
		add(manager);
	}

}
