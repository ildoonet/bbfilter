package com.dabinci.ui.screen;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DRes;
import com.dabinci.ui.label.DLabelField;

public class DTitleBar extends Manager {
	public final static int TYPE_RIGHT = 0;		// 오른쪽 필드(rightField)를 오른쪽 정렬함		[-[title]-[desc]-----------[right]-]
	public final static int TYPE_LEFT = 1;		// 오른쪽 필드(rightField)를 왼쪽 정렬함		[-[title]-[desc]-[right] ----------]
	
	private final static int DEFAULT_MARGIN = DRes.getPixel(8);
	public final static int HEIGHT = DRes.getPixel(20);
	private final static int DEFAULT_MARGIN_DESC = DRes.getPixel(3);

	protected final static int BACKGROUND_COLOR = DColor.getColor(DColor.COLOR_BG);
	
	protected DLabelField titleField, descField;
	protected BitmapField iconField;
	private Field rightField;
	private int type;
	

	public DTitleBar(String title, String desc, Field right){
		this(null, title, desc, right, TYPE_RIGHT);
	}
	
	public DTitleBar(String title, String desc, Field right, int type){
		this(null, title, desc, right, type);
	}

	public DTitleBar(Bitmap icon, String title, String desc, Field right) {
		this(icon, title, desc, right, TYPE_RIGHT);
	}
	
	public DTitleBar(Bitmap icon, String title, String desc, Field right, int type){
		super(USE_ALL_WIDTH);
		
		if (icon != null) {
			iconField = new BitmapField(DRes.getBitmap(icon));
		}
		
		titleField = DLabelField.newInstance(title, Field.NON_FOCUSABLE)
			.setFontSize(DLabelField.FONT_SIZE_TITLE)
			.setColor(DLabelField.COLOR_WHITE);
		
		descField = DLabelField.newInstance(desc, Field.NON_FOCUSABLE)
			.setFontSize(DLabelField.FONT_SIZE_TITLE)
			.setColor(DLabelField.COLOR_YELLOW);
		descField.setPadding(0, DEFAULT_MARGIN_DESC, 0, DEFAULT_MARGIN_DESC);
		
		this.type = type;
		
		setBackground(BackgroundFactory.createSolidBackground(BACKGROUND_COLOR));
		
		if (iconField != null)
			add(iconField);
		
		add(titleField);
		add(descField);

		rightField = right;
		if (rightField != null)
			add(rightField);
	}
	
	public void setTitle(String title) {
		titleField.setText(title);
	}
	
	public void setDesc(String desc) {
		descField.setText(desc);
	}
	
	public Field getRightField() {
		return rightField;
	}
	
	protected void sublayout(int width, int height) {
		int available_width = width - DEFAULT_MARGIN * 2;
		int maxHeight = HEIGHT;
		int posX = 0;
		
		if (iconField != null && iconField.getManager() == this) {
			layoutChild(iconField, available_width, height);
			available_width -= iconField.getWidth();
		}
		
		if (rightField != null && rightField.getManager() == this) {
			layoutChild(rightField, available_width, height);
			maxHeight = Math.max(maxHeight, rightField.getHeight());
			available_width -= rightField.getWidth();
		}

		layoutChild(descField, available_width, height);		//description 먼저 크기를 계산함
		available_width -= descField.getWidth();
		
		layoutChild(titleField, available_width, height);		//타이틀 크기를 계산함
		maxHeight = Math.max(maxHeight, titleField.getHeight());
		

		if (iconField != null && iconField.getManager() == this) {
    		setPositionChild(iconField, DEFAULT_MARGIN, getY(iconField, maxHeight));
    		posX = DEFAULT_MARGIN + iconField.getWidth();
    		maxHeight = Math.max(maxHeight, iconField.getHeight());
		}
		setPositionChild(titleField, posX + DEFAULT_MARGIN, getY(titleField, maxHeight));		//타이틀 포지셔닝
		maxHeight = Math.max(maxHeight, descField.getHeight());
		setPositionChild(descField, titleField.getWidth() + titleField.getLeft(), getY(descField, maxHeight));		//디스크립션 포지셔닝
		
		if (rightField != null && rightField.getManager() == this) {
    		if (this.type == TYPE_RIGHT) {
    			setPositionChild(rightField, width - rightField.getWidth(), getY(rightField, maxHeight));
    		} else {
    			setPositionChild(rightField, 
    				Math.min(width - rightField.getWidth() - DEFAULT_MARGIN, descField.getLeft() + descField.getWidth()), 
    				getY(rightField, maxHeight));
    		}
		}
		
		setExtent(width, maxHeight);
	}

	private int getY(Field field, int maxHeight) {
		return Math.max(0, (maxHeight - field.getHeight() )/2);
	}
}
