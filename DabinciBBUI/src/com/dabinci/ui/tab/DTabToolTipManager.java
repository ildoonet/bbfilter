package com.dabinci.ui.tab;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

import com.dabinci.ui.DResolution;
import com.dabinci.ui.manager.DBorderManager;

public class DTabToolTipManager extends Manager {

	private final Manager contents;
	private final DTabToolTipField tooltip;
	
	public DTabToolTipManager(Manager contents){
		super(0l);
		tooltip = new DTabToolTipField();
		
		add(this.contents = contents);
	}

	protected void sublayout(int width, int height) {
		setExtent(width, height);
		
		layoutChild(contents, width, height);
		setPositionChild(contents, 0, 0);
		
		if (tooltip.getManager() == this && tooltip.isToolTipOn()) {
			layoutChild(tooltip, width, height);
			setPositionChild(tooltip, tooltip.getParentX() - tooltip.getWidth() / 2, 0);
		}
	}
	
	public DTabToolTipField getToolTipField() {
		return tooltip;
	}
	

	public void onToolTip(boolean enabled) {
		if (enabled) {
			if (tooltip.getManager() == null)
				add(tooltip);
		} else {
			if (tooltip.getManager() == this)
				delete(tooltip);
		}
	}
	
	public static class DTabToolTipField extends DBorderManager {
		private static Border border;
		
		static {
			int b = DResolution.getPixel(15);
			border = BorderFactory.createRoundedBorder(
					new XYEdges(b, b, b, b),
					0x00000000,
					Border.STYLE_FILLED
					);
		}
		
		private final LabelField tipLabel;
		private int x, y;
		
		private DTabToolTipField() {
			super(0l, border, new XYEdges(3, 5, 3, 5));
			
			tipLabel = new LabelField("") {
				protected void paint(Graphics graphics) {
					graphics.setColor(Color.WHITE);
					super.paint(graphics);
				}
			};
			
			add(tipLabel);
		}

		public void setToolTip(final String text, final int x, final int y) {
			tipLabel.setText(text);
			this.x = x;
			this.y = y;
		}
		
		public boolean isToolTipOn() {
			return (tipLabel.getText().length() > 0);
		}
		
		public int getParentX() {
			return x;
		}
		
		public int getParentY() {
			return y;
		}
	}
}
