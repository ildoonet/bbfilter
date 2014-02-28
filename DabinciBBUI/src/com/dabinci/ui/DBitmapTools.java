package com.dabinci.ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.PNGEncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.util.Arrays;
import net.rim.device.api.util.ByteVector;

/**
 * @author    CurtisKim
 */
public class DBitmapTools {
	public static Bitmap ResizeBitmap(Bitmap bmpSrc, int nWidth, int nHeight) {
		if (bmpSrc == null)
			return null;
		
		Bitmap revised = new Bitmap(nWidth, nHeight);
		bmpSrc.scaleInto(revised, Bitmap.FILTER_BOX, Bitmap.SCALE_TO_FIT);
		 
		return revised;
	}
	
	public static Bitmap resizeTransparentBitmapByHeight(Bitmap bmpSrc, int targetHeight) {
		return resizeTransparentBitmapByHeight(bmpSrc, targetHeight, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FIT);
	}
	
	public static Bitmap resizeTransparentBitmapByHeight(Bitmap bmpSrc, int targetHeight, int nFilterType, int nAspectRatio) {
		if (bmpSrc==null) return null;
		
		int width = bmpSrc.getWidth();
		int height = bmpSrc.getHeight();
		
		double ratio = (double)targetHeight / height;
		int targetWidth = (int)(width * ratio);
		
		return ResizeTransparentBitmap(bmpSrc, targetWidth, targetHeight, nFilterType, nAspectRatio);
	}
	
    /**
     * Resizes a bitmap with an alpha channel (transparency) without the artifacts introduced
     *   by <code>scaleInto()</code>.
     *
     * @param bmpSrc        Source Bitmap
     * @param nWidth        New Width
     * @param nHeight       New Height
     * @param nFilterType   Filter quality to use. Can be <code>Bitmap.FILTER_LANCZOS</code>,
     *                           <code>Bitmap.FILTER_BILINEAR</code> or
     *                           <code>Bitmap.FILTER_BOX</code>.
     * @param nAspectRatio  Specifies how the picture is resized. Can be
     *                           <code>Bitmap.SCALE_TO_FIT</code>,
     *                           <code>Bitmap.SCALE_TO_FILL</code> or
     *                           <code>Bitmap.SCALE_STRETCH</code>.
     * @return              The resized Bitmap in a new object.
     */
    public static Bitmap ResizeTransparentBitmap(Bitmap bmpSrc, int nWidth, int nHeight, int nFilterType, int nAspectRatio) {
        if (bmpSrc == null)
            return null;

        //Get the original dimensions of the bitmap
        int nOriginWidth = bmpSrc.getWidth();
        int nOriginHeight = bmpSrc.getHeight();
        if(nWidth == nOriginWidth && nHeight == nOriginHeight)
            return bmpSrc;

        //Prepare a drawing bitmap and graphic object
        Bitmap bmpOrigin = new Bitmap(nOriginWidth, nOriginHeight);
        Graphics graph = Graphics.create(bmpOrigin);

        //Create a line of transparent pixels for later use
        int[] aEmptyLine = new int[nWidth];
        for(int x = 0; x < nWidth; x++)
            aEmptyLine[x] = 0x00000000;
        //Create two scaled bitmaps
        Bitmap[] bmpScaled = new Bitmap[2];
        for(int i = 0; i < 2; i++)
        {
            //Draw the bitmap on a white background first, then on a black background
            graph.setColor((i == 0) ? Color.WHITE : Color.BLACK);
            graph.fillRect(0, 0, nOriginWidth, nOriginHeight);
            graph.drawBitmap(0, 0, nOriginWidth, nOriginHeight, bmpSrc, 0, 0);

            //Create a new bitmap with the desired size
            bmpScaled[i] = new Bitmap(nWidth, nHeight);
            if(nAspectRatio == Bitmap.SCALE_TO_FIT)
            {
                //Set the alpha channel of all pixels to 0 to ensure transparency is
                //applied around the picture, if needed by the transformation
                for(int y = 0; y < nHeight; y++)
                    bmpScaled[i].setARGB(aEmptyLine, 0, nWidth, 0, y, nWidth, 1);
            }

            //Scale the bitmap
            bmpOrigin.scaleInto(bmpScaled[i], nFilterType, nAspectRatio);
        }

        //Prepare objects for final iteration
        Bitmap bmpFinal = bmpScaled[0];
        int[][] aPixelLine = new int[2][nWidth];

        //Iterate every line of the two scaled bitmaps
        for(int y = 0; y < nHeight; y++)
        {
            bmpScaled[0].getARGB(aPixelLine[0], 0, nWidth, 0, y, nWidth, 1);
            bmpScaled[1].getARGB(aPixelLine[1], 0, nWidth, 0, y, nWidth, 1);

            //Check every pixel one by one
            for(int x = 0; x < nWidth; x++)
            {
                //If the pixel was untouched (alpha channel still at 0), keep it transparent
                if(((aPixelLine[0][x] >> 24) & 0xff) == 0)
                    aPixelLine[0][x] = 0x00000000;
                else
                {
                    //Compute the alpha value based on the difference of intensity
                    //in the red channel
                    int nAlpha = ((aPixelLine[1][x] >> 16) & 0xff) -
                                    ((aPixelLine[0][x] >> 16) & 0xff) + 255;
                    if(nAlpha == 0)
                        aPixelLine[0][x] = 0x00000000; //Completely transparent
                    else if(nAlpha >= 255)
                        aPixelLine[0][x] |= 0xff000000; //Completely opaque
                    else {
                        //Compute the value of the each channel one by one
                        int nRed = ((aPixelLine[0][x] >> 16 ) & 0xff);
                        int nGreen = ((aPixelLine[0][x] >> 8 ) & 0xff);
                        int nBlue = (aPixelLine[0][x] & 0xff);

                        nRed = (int)(255 + (255.0 * ((double)(nRed-255)/(double)nAlpha)));
                        nGreen = (int)(255 + (255.0 * ((double)(nGreen-255)/(double)nAlpha)));
                        nBlue = (int)(255 + (255.0 * ((double)(nBlue-255)/(double)nAlpha)));

                        if (nRed < 0) nRed = 0;
                        if (nGreen < 0) nGreen = 0;
                        if (nBlue < 0) nBlue = 0;
                        aPixelLine[0][x] = nBlue | (nGreen<<8) | (nRed<<16) | (nAlpha<<24);
                    }
                }
            }

            //Change the pixels of this line to their final value
            bmpFinal.setARGB(aPixelLine[0], 0, nWidth, 0, y, nWidth, 1);
        }
        
        //Memory Manager
        bmpOrigin = null;
        graph = null;
        aPixelLine =null;
        
        return bmpFinal;
    }
    
    static public Bitmap getDefaultBitmapResized(Bitmap original, int width, int height) {
    	if(width<=0 || height <=0 || original==null)
    		return original;
    	
    	int bitmap_width = original.getWidth();
    	int bitmap_height = original.getHeight();
    	
    	if(width < bitmap_width || height < bitmap_height )
    	{
    		//원하는 사이즈가 더 작은 경우 fitting해서 리턴함
    		Bitmap revised = new Bitmap(width, height);
    		Graphics g_revised = Graphics.create(revised);
    		
    		//배경색 지정함
    		g_revised.setColor(Color.WHITE);
    		g_revised.fillRect(0, 0, width, height);
    		
    		//이미지 그림
    		Bitmap resizedBmp = ResizeTransparentBitmap(original, width, height, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FIT);
    		g_revised.drawBitmap(0, 0, width, height, resizedBmp, 0, 0);
    		
    		return revised;
    	}
    	else
    	{
    		//더 큰 경우 중앙에 위치시켜서 리턴함
    		Bitmap revised = new Bitmap(width, height);
    		Graphics g_revised = Graphics.create(revised);
    		
    		//투명 배경 지정함
    		g_revised.setColor(Color.WHITE);
    		g_revised.fillRect(0, 0, width, height);
    			
    		//중앙에 아이콘 위치시킴
    		int revised_x = Math.max(0, 
    				(width - bitmap_width)/2);
    		int revised_y = Math.max(0, 
    				(height - bitmap_height)/2);
    		g_revised.drawBitmap(revised_x, revised_y, width, height, original, 0, 0);
    		
    		return revised;
    	}
    }
    
    static public Bitmap getTransparentBitmap(int width, int height) {
		return getTransparentBitmap(width, height, 0);
    }
    
    public static Bitmap getTransparentBitmap(int width, int height, int alpha) {
    	if (width <= 0 || height <= 0)
			return null;
    	
        int dataTransparent[] = new int[width * height];
        final int value = (int)(0xff * ((double)alpha / 200)) * 0x1000000;
        Arrays.fill(dataTransparent, value);
                        
        Bitmap revised = new Bitmap(Bitmap.ROWWISE_16BIT_COLOR, width, height);
        revised.createAlpha(Bitmap.ALPHA_BITDEPTH_8BPP);
        revised.setARGB(dataTransparent, 0, width, 0, 0, width, height);   
    	
    	return revised;
    }
    
	/**
	 * 바이트벡터로부터 비트맵을 생성함
	 * */
	public static Bitmap getBitmapFromByteVector(ByteVector byteVector) {
		// 유저가 지정한 사진이 저장되어 있는 경우
		try {
			byte[] byte_arr = byteVector.getArray();

			EncodedImage ei = EncodedImage.createEncodedImage(byte_arr, 0, -1);
			Bitmap bmp = ei.getBitmap();

			return bmp;
		} catch (Exception e) {
			// do nothing
			System.out.println(e);
		}
		return null;
	}
	
	/**
	 * 비트맵 객체를 ByteVector로 변환함
	 * */
	public static ByteVector getByteVectorFromBitmap(Bitmap bmp) {
		if (bmp == null)
			return null;
		
		//비트맵 데이터를 바이트 배열로 가져옴
		byte[] bitmap_arr = getBytesFromBitmap(bmp);
		
		//바이트 베열을 벡터 객체로 생성함
		ByteVector a = new ByteVector(bitmap_arr.length);
		
		for (int i = 0; i < bitmap_arr.length; i++)
			a.addElement(bitmap_arr[i]);
		
		return a;
	}
	
	/**
	 * 비트맵 객체를 byte array로 변환함
	 * */
	public static byte[] getBytesFromBitmap(Bitmap bmp) { 
		PNGEncodedImage png = PNGEncodedImage.encode(bmp);
		return png.getData();
    }
} 