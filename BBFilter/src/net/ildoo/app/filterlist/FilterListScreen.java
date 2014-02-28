package net.ildoo.app.filterlist;

import net.ildoo.bbfilter.FilterManager;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

import com.dabinci.ui.DRes;
import com.dabinci.ui.screen.DMainScreen;

public class FilterListScreen extends DMainScreen {
	public FilterListScreen(final Bitmap original) {
		super();
		setTitle("Select Filter");

		// 유저가 지정한 이미지
		final Bitmap smallBitmap = new Bitmap(FilterManager.THUMBW, FilterManager.THUMBH);
		original.scaleInto(smallBitmap, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FILL);
		
		HorizontalFieldManager hfm = new HorizontalFieldManager(Manager.NO_HORIZONTAL_SCROLL | Manager.USE_ALL_WIDTH);
		final int padding_h = DRes.getPixel(10);
		final int padding_v = DRes.getPixel(10);
		hfm.setPadding(padding_v, padding_h, padding_v, padding_h);
		hfm.add(new BitmapField(smallBitmap));
		Field lbDesc;
		hfm.add(lbDesc = new LabelField("Select a filter", Field.FIELD_VCENTER | LabelField.ELLIPSIS));
		lbDesc.setMargin(0, 0, 0, DRes.getPixel(3));
		add(hfm);
		
		// 필터 리스트
		FilterListManager fList = new FilterListManager(FilterManager.getPurchasedFilterGroups(), original);
		add(fList);
	}
}
