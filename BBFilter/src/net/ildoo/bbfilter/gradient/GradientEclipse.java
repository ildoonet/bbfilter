package net.ildoo.bbfilter.gradient;

import net.rim.device.api.util.MathUtilities;

public class GradientEclipse extends Gradient {
	private XY xy;
	
	protected float getGradient(int x, int y) {
		// test eclipse equation
		final float eclipse = (float) (
				(MathUtilities.pow(x - width / 2, 2.0) / MathUtilities.pow(xy.x - width / 2, 2.0)) +
				(MathUtilities.pow(y - height / 2, 2.0) / MathUtilities.pow(xy.y - height / 2, 2.0))
			);
		
		if (eclipse < 1.0f)
			return 1.0f;
		
		// filter function : fx = -0.005(x - 1)^2 + 1
		return Math.max(0.3f, -0.01f * (float)MathUtilities.pow(eclipse - 1, 2.0) + 1f);
	}

	public void setXY(XY xy, XY nullXy) {
		super.setXY(xy, nullXy);
		this.xy = xy;
	}
}
