package com.dabinci.ui.button;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DResolution;

public class DTextButtonField extends DBaseButtonField {

	public final static int TYPE_BROWN = 0;
	public final static int TYPE_YELLOW = 1;
	public final static int TYPE_GREY = 2;
	
	private final static int COLOR_BG_BROWN = 0x3d2123;
	private final static int COLOR_BG_YELLOW = 0xffe400;
	private final static int COLOR_BG_GREY = 0xebeae8;
	
	private final static int BORDER_BROWN = 0x3d2123;
	private final static int BORDER_YELLOW = 0xf0d800;
	private final static int BORDER_GREY = 0xe0e0de;
	
	private final static int COLOR_TEXT_BROWN = 0xffffff;
	private final static int COLOR_TEXT_YELLOW = 0x493334;
	private final static int COLOR_TEXT_GREY = 0x222222;
	
	public final static int TEXT_SIZE = DResolution.getFontHeight(20);
	public final static int TEXT_SIZE_SMALL = DResolution.getFontHeight(12);
	
	private final static int DEFAULT_HEIGHT = DResolution.getPixel(32);
	private final static int DEFAULT_PADDING_HORIZONTAL = DResolution.getPixel(15);
	
	public final static int SMALL_BUTTON_HEIGHT = DResolution.getPixel(24);


	private String text;
	private int textColor;
	private final int borderColor;
	private int preferredHeight;
	private int preferredHorizontalPadding;
	
	private Runnable action;
	
	public static DTextButtonField newInstance(String text, int type) {
		return new DTextButtonField(text, type);
	}
	
	protected DTextButtonField(String text, int type) {
		this.text = text;
		
		int bgColor;
		switch (type) {
		case TYPE_BROWN:
		default:
			borderColor = BORDER_BROWN;
			bgColor = COLOR_BG_BROWN;
			textColor = COLOR_TEXT_BROWN;
			break;
		case TYPE_YELLOW:
			borderColor = BORDER_YELLOW;
			bgColor = COLOR_BG_YELLOW;
			textColor = COLOR_TEXT_YELLOW;
			break;
		case TYPE_GREY:
			borderColor = BORDER_GREY;
			bgColor = COLOR_BG_GREY;
			textColor = COLOR_TEXT_GREY;
			break;
		}
		
		setBackground(Field.VISUAL_STATE_FOCUS, BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_SELECTED_BG)));
		setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(bgColor));
		setFont(getFont().derive(getFont().getStyle(), TEXT_SIZE));
		setHeight(DEFAULT_HEIGHT);
		setHorizontalPadding(DEFAULT_PADDING_HORIZONTAL);
	}
	
	public DTextButtonField setHeight(int height) {
		preferredHeight = height;
		return this;
	}
	
	public DTextButtonField setHorizontalPadding(int padding) {
		preferredHorizontalPadding = padding;
		return this;
	}
	
	public DTextButtonField setAction(Runnable action) {
		this.action = action;
		return this;
	}
	
	public DTextButtonField setFontSize(int size) {
		setFont(getFont().derive(getFont().getStyle(), DTextButtonField.TEXT_SIZE_SMALL));
		return this;
	}
	
	public DTextButtonField setColor(int fontColor) {
		textColor = fontColor;
		return this;
	}
	
	protected void paint(Graphics graphics) {
		super.paintBackground(graphics);
		int textColor;
		if (getState() == Field.VISUAL_STATE_NORMAL) {
    		graphics.setColor(borderColor);
    		graphics.setStrokeWidth(1);
    		graphics.drawRect(0, 0, getWidth(), getHeight());
    		textColor = this.textColor;
		} else {
			textColor = Color.WHITE;
		}
		
		int y = (getHeight() - getFont().getHeight()) / 2;
		graphics.setColor(textColor);
		graphics.drawText(text, preferredHorizontalPadding, y, Graphics.HCENTER | Graphics.ELLIPSIS, getWidth() - preferredHorizontalPadding * 2);
	}

	protected void layout(int maxWidth, int maxHeight) {
		setExtent(maxWidth, preferredHeight);
	}

	public void setText(String text) {
		this.text = text;
		invalidate();
	}
	
	public String getText() {
		return text;
	}

	protected boolean navigationClick(int status, int time) {
		if (action!=null)
			action.run();
		
		return super.navigationClick(status, time);
	}
}
