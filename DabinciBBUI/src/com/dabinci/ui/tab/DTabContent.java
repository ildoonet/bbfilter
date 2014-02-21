package com.dabinci.ui.tab;

import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DResolution;

public abstract class DTabContent extends VerticalFieldManager {
	public DTabContent() {
		super(Manager.USE_ALL_WIDTH | Manager.VERTICAL_SCROLL);

		requestRefresh();
	}
	
	public abstract void requestRefresh();
	
	public XYEdges getBtnMargin() {
		int m = DResolution.getPixel(10);
		return new XYEdges(m, m, m, m);
	}
	
	public XYEdges getBtnMarginWithoutBottom() {
		int m = DResolution.getPixel(10);
		return new XYEdges(m, m, 0, m);
	}
	
	public XYEdges getBtnMarginWithoutVerticals() {
		int m = DResolution.getPixel(10);
		return new XYEdges(0, m, 0, m);
	}
	
	public XYEdges getBtnMarginWithoutTop() {
		int m = DResolution.getPixel(10);
		return new XYEdges(0, m, m, m);
	}
	
	public Border getBtnBorder() {
		int color = DColor.getColor(DColor.COLOR_LINE_GREY);
		Border b = BorderFactory.createSimpleBorder(
				new XYEdges(1, 1, 1, 1),
				new XYEdges(color, color, color, color),
				Border.STYLE_SOLID);
				
		return b;
	}
	
	public Border getBtnBorderWithoutBottom() {
		int color = DColor.getColor(DColor.COLOR_LINE_GREY);
		Border b = BorderFactory.createSimpleBorder(
				new XYEdges(1, 1, 0, 1),
				new XYEdges(color, color, color, color),
				Border.STYLE_SOLID);
				
		return b;
	}
}
