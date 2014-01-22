package com.ildoo.filterreverser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class Reverser {

	private BufferedImage imageOriginal;
	private BufferedImage imageFiltered;

	public Reverser(final String originalFilePath) throws IOException {
		imageOriginal = ImageIO.read(new File(originalFilePath));
	}
	
	public void openFilteredFile(final String filteredFilePath) throws IOException {
		if (filteredFilePath == null)
			throw new IllegalArgumentException("can not be null");
		
		imageFiltered = ImageIO.read(new File(filteredFilePath));
		
		if (imageFiltered.getWidth() != imageOriginal.getWidth() || imageFiltered.getHeight() != imageOriginal.getHeight()) {
			imageFiltered = null;
			throw new IllegalArgumentException("filtered image size should be equal to the one of original.");
		}
	}
	
	public void reverse() {
		final int SIZE = imageOriginal.getWidth() * imageOriginal.getHeight();
		double[][] y = new double[3][SIZE];
		double[][] x = new double[SIZE][3];

		for (int i = 0; i < imageOriginal.getWidth(); i++) {
			for (int j = 0; j < imageOriginal.getHeight(); j++) {
				final int idx = j * imageOriginal.getWidth() + i;
				
				final int filteredRGB = imageFiltered.getRGB(i, j);
				final int filteredR = (filteredRGB >> 16 ) & 0xff;
		        final int filteredG = (filteredRGB >> 8 ) & 0xff;
		        final int filteredB = (filteredRGB & 0xff);
			
		        final int originalRGB = imageOriginal.getRGB(i, j);
				final int originalR = (originalRGB >> 16 ) & 0xff;
		        final int originalG = (originalRGB >> 8 ) & 0xff;
		        final int originalB = (originalRGB & 0xff);
		        
		        // set parameters
		        y[0][idx] = filteredR;
		        y[1][idx] = filteredG;
		        y[2][idx] = filteredB;
		        
		        x[idx][0] = originalR;
		        x[idx][1] = originalG;
		        x[idx][2] = originalB;
			}
		}
		
		for (int i = 0; i < 3; i++) {
			OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
			regression.newSampleData(y[i], x);
			double[] residuals = regression.estimateRegressionParameters();

			switch (i) {
			case 0:
				System.out.print("R");
				break;
			case 1:
				System.out.print("G");
				break;
			case 2:
				System.out.print("B");
				break;
			}
			System.out.println(Arrays.toString(residuals));
			System.out.println(regression.estimateRegressionStandardError());
		}
	}
}
