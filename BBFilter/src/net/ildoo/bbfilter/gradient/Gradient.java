package net.ildoo.bbfilter.gradient;

import net.ildoo.bbfilter.filter.ColorMatrix;

public abstract class Gradient {
	protected XY fromXY, toXY;
	protected final ColorMatrix cm;
	protected int width, height;
	
	public Gradient() {
		cm = new ColorMatrix();
	}

	public void gradient(final int[] argbs, final int width, final int height) {
		if (fromXY == null || toXY == null)
			throw new IllegalStateException("setXY() must be called before gradient() is used.");

		if (argbs == null || argbs.length == 0)
			throw new IllegalArgumentException("argbs[] can not be null or 0-length array.");

		if (argbs.length != width * height)
			throw new IllegalArgumentException("size of argbs[] must be equal to width * height");

		this.width = width;
		this.height = height;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
		        final int t = getTransformedColor(argbs[x + y * width], x, y);
		        argbs[x + y * width] = t;
			}
		}
	}
	
	public void setXY(final XY fromXY, final XY toXY) {
		this.fromXY = fromXY;
		this.toXY = toXY;
	}
	
	protected int getTransformedColor(final int argb, final int x, final int y) {
		float s = getGradient(x, y);
		if (s == 1.0f)
			return argb;
		
		final int nRed = (argb >> 16 ) & 0xff;
        final int nGreen = (argb >> 8 ) & 0xff;
        final int nBlue = (argb & 0xff);		

        final int red = (int) ((nRed+0.01f) * s);
        final int green = (int) ((nGreen+0.1f) * s);
        final int blue = (int) ((nBlue+0.1f) * s);
        
        return blue | green << 8 | red << 16 | 0xff000000;
	}        		
	
	protected abstract float getGradient(final int x, final int y);
	
	public static class XY {
		public int x;
		public int y;
		
		public static XY create(int x, int y) {
			XY xy = new XY();
			xy.x = x;
			xy.y = y;
			return xy;
		}
	}
}
