package net.ildoo.bbfilter.filter;

import net.rim.device.api.system.Bitmap;

abstract class Filter {
	protected abstract float[] getTransformMatrix();

	public Bitmap filtering(final Bitmap bitmap) {
		if (bitmap == null)
			throw new IllegalArgumentException("Bitmap argument can not be null.");
		
		final int[] argbs = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getARGB(argbs, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		
		// apply filter set
		final ColorMatrix cm = new ColorMatrix();
		
		if (getTransformMatrix() != null) {
			cm.set(getTransformMatrix());
			run(cm, argbs);
		}
		
		if (getSaturation() <= 1.0f && getSaturation() > 0) {
			cm.setSaturation(getSaturation());
			run(cm, argbs);
		}
		
		// generate bitmap object
		final Bitmap revised = new Bitmap(bitmap.getWidth(), bitmap.getHeight());
		revised.setARGB(argbs, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		
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
	
	protected int[] doExtra(final int[] argbs) {
		return argbs;
	}
}
