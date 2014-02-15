package com.dabinci.ui.manager;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;

/**
 * 둥근 형태의 border를 설정할 수 있는 매니저
 * */
public class DBorderManager extends Manager {
	private Field borderField;		// 전체 매니저에 꽉 차도록 border가 그려지게 할 대리자 border field
	private Manager manager;		// 실제 필드들이 삽입될 delegate manager
	private XYEdges padding;		// 실제 필드들이 삽입될 영역을 결정할 패딩값
	
	/**
	 * @param insideBorder : 둥근 형태의 border. 이 매니저의 최소 크기는 border의 크기로 결정된다. (단, 필드가 하나도 삽입되지 않으면 크기 없음)
	 * @param padding : 전체 view 내에서 실제 필드들이 삽입될 위치를 padding값으로 설정한다. 둥근 부분 위에 필드가 겹쳐지지 않도록 주의해야함.
	 * */
	public DBorderManager(long style, Border insideBorder, XYEdges padding){
		super(style);
		
		this.padding = padding;
		
		super.add(borderField = new FullSizeBorder(insideBorder));		//내부에 border를 그릴 필드를 먼저 삽입한다
		super.add(manager = new VerticalFieldManager());				//실제 필드들이 삽입될 매니저 대리자(delegate manager)
	}

	public void setBorder(Border border) {
		borderField.setBorder(border);
	}
	
	public void setBackgroundColor(int color) {
		borderField.setBackground(BackgroundFactory.createSolidBackground(color));
	}
	
	public boolean isBorderSet() {
		return (borderField.getBorder() != null);
	}
	
	public void setPadding(int top, int right, int bottom, int left) {
		XYEdges padding = new XYEdges(top, right, bottom, left);
		setPadding(padding);
	}
	
	public void setPadding(XYEdges padding) {
		this.padding = padding;
		invalidate();
	}

	public void add(Field field) {
		manager.add(field);
	}
	
	public void delete(Field field) {
		manager.delete(field);
	}
	
	public void deleteAll() {
		manager.deleteAll();
	}
	
	protected void sublayout(int width, int height) {
		if (manager.getFieldCount() == 0) {
			setExtent(0, 0);
			return;
		}

		// delegate manager를 layout 함
		final int horizontal_padding = padding.left + padding.right;
		final int vertical_padding = padding.top + padding.bottom;

		layoutChild(manager, 
				width - horizontal_padding, 
				height - vertical_padding);
		setPositionChild(manager, padding.left, padding.top);
		
		// delegate manager의 크기를 기준으로해서 전체 매니저의 크기와 보더를 레이아웃함
		int min_width = 0, min_height = 0;
		if(borderField.getBorder() != null) {
    		min_width = borderField.getBorder().getLeft() + borderField.getBorder().getRight() + 1;
    		min_height = borderField.getBorder().getTop() + borderField.getBorder().getBottom() + 1;
		}

		int field_width = Math.max(manager.getWidth() + horizontal_padding, min_width);
		int field_height = Math.max(manager.getHeight() + vertical_padding, min_height);
		
		setExtent(field_width, field_height);
		
		layoutChild(borderField, field_width, field_height);
		setPositionChild(borderField, 0, 0);
	}

	/**
	 * 보더를 내부에 그리기 위해 널필드를 활용한다.
	 * */
	private class FullSizeBorder extends NullField {
		public FullSizeBorder(Border border) {
			super(Field.NON_FOCUSABLE);
			setBorder(border);
		}
		
		protected void layout(int width, int height) {
			setExtent(width, height);
		}
	}
}
