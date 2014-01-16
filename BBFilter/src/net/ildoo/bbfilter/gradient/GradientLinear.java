package net.ildoo.bbfilter.gradient;

public class GradientLinear extends Gradient {
	private int vectorX, vectorY;
	private float vectorSize;
	
	protected float getGradient(int x, int y) {
		int vectorX2 = x - fromXY.x;
		int vectorY2 = y - fromXY.y;
		
		int innerProduct = vectorX2 * vectorX + vectorY2 * vectorY;
		
		if (innerProduct <= 0)
			return 1.0f;
		
		final float s  = Math.max(1f, 2.0f - innerProduct / (vectorSize * vectorSize));
		return s;
	}
	
	public void setXY(XY fromXY, XY toXY) {
		super.setXY(fromXY, toXY);
		
		vectorX = (toXY.x - fromXY.x);
		vectorY = (toXY.y - fromXY.y);
		vectorSize = (float) Math.sqrt(vectorX * vectorX + vectorY * vectorY);
	}
}
