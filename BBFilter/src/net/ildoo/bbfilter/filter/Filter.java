package net.ildoo.bbfilter.filter;

import net.ildoo.bbfilter.FilterCache;
import net.ildoo.bbfilter.gradient.Gradient;
import net.rim.device.api.system.Bitmap;

public abstract class Filter {
	private static final String TAG = "Filter";
	protected abstract float[] getTransformMatrix();

	public abstract String getName();
	
	public Bitmap filtering(final Bitmap bitmap, final boolean saveToCache) {
		if (bitmap == null)
			throw new IllegalArgumentException("Bitmap argument can not be null.");
		
		if (saveToCache == true && FilterCache.getInstance().containsKey(this.getClass().getName())) {
			Bitmap cached = (Bitmap) FilterCache.getInstance().get(this.getClass().getName());
			if (cached != null)
				return cached;
		}
		
		final int[] argbs = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getARGB(argbs, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		
		// apply filter set
		final ColorMatrix cm = new ColorMatrix();
		
		if (getTransformMatrix() != null) {
			cm.set(getTransformMatrix());
			
			if (getBrightness() != 1.0f) {
				float f = getBrightness();
				
				ColorMatrix cm2 = new ColorMatrix();
				cm2.set(new float[] {
					f, 0, 0, 0, 0,
					0, f, 0, 0, 0,
					0, 0, f, 0, 0,
					0, 0, 0, 1, 0
				});
				cm.preConcat(cm2);
			}
			
			run(cm, argbs);
		}
		
		if (getSaturation() <= 1.0f && getSaturation() > 0) {
			cm.setSaturation(getSaturation());
			run(cm, argbs);
		}
		
		if (getGradient(bitmap.getWidth(), bitmap.getHeight()) != null) {
			Gradient g = getGradient(bitmap.getWidth(), bitmap.getHeight());
			g.gradient(argbs, bitmap.getWidth(), bitmap.getHeight());
		}
		
		// generate bitmap object
		final Bitmap revised = new Bitmap(bitmap.getWidth(), bitmap.getHeight());
		revised.setARGB(argbs, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		
		if (saveToCache) {
			FilterCache.getInstance().put(this.getClass().getName(), revised);
		}
		
		return revised;
	}
	
	private void run(ColorMatrix cm, int[] argbs) {
		for (int i = 0; i < argbs.length; i++) {
            argbs[i] = cm.getColor(argbs[i]);
		}
	}
	
	protected float getSaturation() {
		return 1.0f;
	}
	
	protected Gradient getGradient(int width, int height) {
		return null;
	}
	
	protected float getBrightness() {
		return 1.0f;
	}
	
	protected int[] doExtra(final int[] argbs) {
		return argbs;
	}
}