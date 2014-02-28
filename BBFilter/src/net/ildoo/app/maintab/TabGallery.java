package net.ildoo.app.maintab;

import net.ildoo.app.FilterConfig;
import net.ildoo.app.filterlist.FilterListScreen;
import net.ildoo.bbfilter.FilterCache;
import net.rim.blackberry.api.invoke.CameraArguments;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.device.api.io.file.FileSystemJournal;
import net.rim.device.api.io.file.FileSystemJournalEntry;
import net.rim.device.api.io.file.FileSystemJournalListener;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EventInjector;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.picker.FilePicker;

import com.dabinci.os.DabinciOSUtil;
import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DBitmapButtonField;
import com.dabinci.ui.tab.DTabContent;
import com.dabinci.utils.DFileUtils;
import com.dabinci.utils.DLogger;

public class TabGallery extends DTabContent {
	protected static final String TAG = "TabGallery";

	private final CameraListener cameraListener = new CameraListener();
	
	public TabGallery() {
		// camera button
		DBitmapButtonField btnCamera = new DBitmapButtonField(
				DRes.getBitmap(Bitmap.getBitmapResource("ico_camera_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("ico_camera.png")), 
				Field.USE_ALL_WIDTH);
		btnCamera.setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(Color.WHITE));
		btnCamera.setMargin(getBtnMarginWithoutBottom());
		btnCamera.setBorder(getBtnBorderWithoutBottom());
		btnCamera.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				startCamera();
			}
		});
		add(btnCamera);
		
		// gallery button
		DBitmapButtonField btnGallery = new DBitmapButtonField(
				DRes.getBitmap(Bitmap.getBitmapResource("ico_gallery_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("ico_gallery.png")), 
				Field.USE_ALL_WIDTH);
		btnGallery.setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(Color.WHITE));
		btnGallery.setMargin(getBtnMarginWithoutTop());
		btnGallery.setBorder(getBtnBorder());
		btnGallery.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				startImagePicker();
			}
		});
		add(btnGallery);
	}
	
	public void requestRefresh() {

	}
	
	private void startImagePicker() {
		FilePicker fp = FilePicker.getInstance();
		DabinciOSUtil.getInstance().setPicturePicker(fp);
		String path = fp.show();
		openFromFilePath(path);
	}
	
	private void startCamera() {
		Invoke.invokeApplication(Invoke.APP_TYPE_CAMERA, new CameraArguments(CameraArguments.ARG_CAMERA_APP));

		UiApplication.getUiApplication().removeFileSystemJournalListener(cameraListener);
		UiApplication.getUiApplication().addFileSystemJournalListener(cameraListener);
	}
	
	private void openFromFilePath(final String path) {
		if (path == null)
			return;

		DLogger.tlog(TAG, "load image " + path);
		
		byte[] bmpbyte = DFileUtils.getBytesFromFile(path);
		
		if (bmpbyte == null || bmpbyte.length == 0)
			return;
		
		Bitmap bitmap = Bitmap.createBitmapFromBytes(bmpbyte, 0, bmpbyte.length, 1);

		int width = FilterConfig.getInstance().getInt(FilterConfig.KEY_RESOLUTION_WIDTH, Display.getWidth());
		int height = FilterConfig.getInstance().getInt(FilterConfig.KEY_RESOLUTION_HEIGHT, Display.getHeight());
		
		Bitmap resized = bitmap;
		if (bitmap.getWidth() * bitmap.getHeight() > width * height) {
			resized = new Bitmap(width, height);
			bitmap.scaleInto(resized, Bitmap.FILTER_LANCZOS);
		}
		
		FilterListScreen s = new FilterListScreen(resized);
		UiApplication.getUiApplication().pushScreen(s);
		
		FilterCache.getInstance().clear();
	}
	
	private class CameraListener implements FileSystemJournalListener {
		private long _lastUSN = FileSystemJournal.getNextUSN();
		
	    public void fileJournalChanged() {
	        long nextUSN = FileSystemJournal.getNextUSN();
	        for (long lookUSN = nextUSN - 1; lookUSN >= _lastUSN; --lookUSN) {
	            FileSystemJournalEntry entry = FileSystemJournal.getEntry(lookUSN);
	            if (entry == null) {
	                break; 
	            }
	            String path = entry.getPath();
	            if (path == null)
	            	continue;
	            
                if (false == (path.endsWith("png") || path.endsWith("jpg") || path.endsWith("bmp") || path.endsWith("gif")))
            		continue;
                
                switch (entry.getEvent()) {
                    case FileSystemJournalEntry.FILE_ADDED:
                        //either a picture was taken or a picture was added to the BlackBerry device 
                    	DLogger.log(TAG, path);
                    	
                    	// successfully found the photo.
                    	EventInjector.KeyEvent inject = new EventInjector.KeyEvent(EventInjector.KeyEvent.KEY_DOWN, Characters.ESCAPE, 0);
            			inject.post();
            			inject.post();
            			
            			UiApplication.getUiApplication().removeFileSystemJournalListener(cameraListener);

            			openFromFilePath(path);
                    	break;
                    case FileSystemJournalEntry.FILE_DELETED:
                        //a picture was removed from the BlackBerry device;
                        break;
                }
	        } // end for loop

	        _lastUSN = nextUSN;
	    }
	}
}
