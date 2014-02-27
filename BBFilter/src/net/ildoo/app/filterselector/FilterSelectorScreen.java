package net.ildoo.app.filterselector;

import net.ildoo.bbfilter.FilteredBitmap;
import net.ildoo.bbfilter.filter.Filter;
import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;

import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DBitmapButtonField;
import com.dabinci.ui.button.DTextOnBitmapToggleButtonField;
import com.dabinci.ui.button.DTextOnBitmapToggleButtonField.onChangeToggleStatus;
import com.dabinci.ui.manager.DHorizontalEvenManager;
import com.dabinci.utils.DLogger;

public class FilterSelectorScreen extends FilterBaseScreen {
	private static final String TAG = "FilterSelector";
	
	private final Bitmap bitmap;
	private final FilterGroup filterGroup;
	
	public FilterSelectorScreen(final String title, final Bitmap bitmap, final Bitmap blurred, final FilterGroup filterGroup) {
		super(title, bitmap, blurred);
		setBackground(Color.BLACK);
		
		this.bitmap = bitmap;
		this.filterGroup = filterGroup;

		filterManager.requestThumbs(bitmap, filterGroup);
		tooltipManager.startWaitingDialog(2);
		
		// save/cancel button
		DBitmapButtonField btnSave = new DBitmapButtonField(DRes.getBitmap(Bitmap.getBitmapResource("ico_ok.png")));
		btnSave.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				
			}
		});
		
		DBitmapButtonField btnCancel = new DBitmapButtonField(DRes.getBitmap(Bitmap.getBitmapResource("ico_cancel.png")));
		btnCancel.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				close();
			}
		});
		DHorizontalEvenManager statusManager = new DHorizontalEvenManager(DRes.getPixel(3));
		statusManager.add(btnSave);
		statusManager.add(btnCancel);
		setStatus(statusManager);
	}

	public void onFiltered(Bitmap bitmap) {
		DLogger.log(TAG, "onFiltered()+");
		manager.setBitmap(bitmap);
		tooltipManager.stopWaitingDialog();
		DLogger.log(TAG, "onFiltered()-");
	}

	public void onThumbnailed(Bitmap original, final FilteredBitmap[] thumbs) {
		DLogger.log(TAG, "onThumbnailed()+");
		tooltipManager.stopWaitingDialog();
		
		manager.getThumbnailManager().deleteAll();
		DTextOnBitmapToggleButtonField btnOriginal = new DTextOnBitmapToggleButtonField("", original);
		btnOriginal.setToggleListener(new onChangeToggleStatus() {
			public void onChangeToggleStatus(boolean toggled) {
				manager.setBitmap(FilterSelectorScreen.this.bitmap);
			}
		});
		manager.getThumbnailManager().add(btnOriginal);
		
		for (int i = 0; i < thumbs.length; i ++) {
			final FilteredBitmap filterInfo = thumbs[i];
			DTextOnBitmapToggleButtonField btn = new DTextOnBitmapToggleButtonField(filterInfo.getFilterName(), filterInfo.getFilterBitmap());
			btn.setToggleListener(new onChangeToggleStatus() {
				public void onChangeToggleStatus(boolean toggled) {
					try {
						tooltipManager.startWaitingDialog();
						filterManager.requestFilter(FilterSelectorScreen.this.bitmap, (Filter) filterInfo.getFilterClass().newInstance());
					} catch (Exception e) { 
						DLogger.log(TAG, "onChangeToggleStatus e=" + e.toString());
					}
				}
			});
			manager.getThumbnailManager().add(btn);
		}
		
		btnOriginal.clickButton();
		DLogger.log(TAG, "onThumbnailed()-");
	}

	public void onBlurred(Bitmap bitmap, Field field) {
		tooltipManager.stopWaitingDialog();
		super.onBlurred(bitmap, field);
	}
	
	public void onProgressChanged(float percentage) {
	}

	public void onFilterFail() {
	}
}
