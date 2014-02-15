package com.dabinci.ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;

public class DResolution {
	public static final int PIXEL_DEFAULT_WIDTH = 640;
	public static final int PIXEL_DEFAULT_HEIGHT = 480;
	
	public static double ratio_width = 0.0;
	public static double ratio_bitmap_width = 0.0;
	
	public static int getPixel(int pixel) {
		if (ratio_width < 0.1) {
			ratio_width =  Math.min(1.7, (double)Display.getWidth() / PIXEL_DEFAULT_WIDTH); 
		}
		
		return (int)(pixel * ratio_width);
	}
	
	public static int getFontHeight(int height) {
		return (int)(getPixel(height) * 1.3);
	}
	
	public static Bitmap getBitmap(Bitmap bitmap) {
		int width = getPixel(bitmap.getWidth());
		int height = getPixel(bitmap.getHeight());
		return GPATools.ResizeTransparentBitmap(bitmap, width, height, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FIT);
	}
	
	public static Bitmap getBitmap(Bitmap bitmap, int width, int height) {
		return GPATools.ResizeTransparentBitmap(bitmap, width, height, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FIT);
	}
	
	public static Bitmap getBitmap(String bitmap, int width, int height) {
		return getBitmap(Bitmap.getBitmapResource(bitmap), width, height);
	}
	
	public static Bitmap getBitmapByHeight(Bitmap bitmap, int height) {
		return GPATools.resizeTransparentBitmapByHeight(bitmap, height);
	}
	
	public static Bitmap getProfileBitmapByHeight(Bitmap bitmap, int width, int height) {
		return GPATools.getDefaultBitmapResized(bitmap, width, height);
	}

	public static boolean isVerticalDisplay() {
		return (Display.getWidth() < Display.getHeight());
	}
}
