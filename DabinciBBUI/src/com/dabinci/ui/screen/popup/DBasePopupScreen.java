package com.dabinci.ui.screen.popup;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.dabinci.ui.DRes;
import com.dabinci.ui.manager.DHorizontalEvenManager;

public abstract class DBasePopupScreen extends PopupScreen {
	public final static int TYPE_NORMAL = 0;
	public final static int TYPE_INFO = 1;
	public final static int TYPE_ALERT = 2;
	
	protected final LabelField lb;
	protected final DHorizontalEvenManager btnWrapper;
	
	public DBasePopupScreen(int type){
		super(new VerticalFieldManager());
		add(new NullField(Field.FOCUSABLE));
		
		HorizontalFieldManager hfm = new HorizontalFieldManager(Manager.NO_HORIZONTAL_SCROLL | Field.USE_ALL_WIDTH);
		
		Bitmap infoBmp  = null;
		switch (type) {
		case TYPE_NORMAL:
		default:
			break;
			
		case TYPE_INFO:
			infoBmp = DRes.getBitmap(Bitmap.getBitmapResource("ico_info.png"));
			break;
		
		case TYPE_ALERT:
			infoBmp = DRes.getBitmap(Bitmap.getBitmapResource("ico_alert.png"));
			break;
		}
		
		if (infoBmp != null) {
			BitmapField bf = new BitmapField(infoBmp, Field.FIELD_VCENTER);
			bf.setMargin(0, DRes.getPixel(10), 0, 0);
			hfm.add(bf);
		}
		
		lb = new LabelField("", Field.FIELD_VCENTER);
		lb.setMargin(0, 0, DRes.getPixel(10), 0);
		hfm.add(lb);
		
		add(hfm);
		
		btnWrapper = new DHorizontalEvenManager(0);
		add(btnWrapper);
	}
	
	public void show() {
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				UiApplication.getUiApplication().pushScreen(DBasePopupScreen.this);
			}
		});
	}
	
	protected boolean keyDown(int keycode, int status) {
		if (Keypad.key(keycode) == Keypad.KEY_ESCAPE) {
			close();
		}
		
		return super.keyDown(keycode, status);
	}
}
