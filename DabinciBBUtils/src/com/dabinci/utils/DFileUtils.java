package com.dabinci.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.JPEGEncodedImage;

public class DFileUtils {
	private static final String TAG = "DFileUtils";

	public static boolean hasSdcardStorage() {
        boolean sdCardPresent = false;
        String root = null;
        Enumeration e = FileSystemRegistry.listRoots();
        while (e.hasMoreElements()) {
            root = (String)e.nextElement();
            if(root.equalsIgnoreCase("SDCard/")) {
                sdCardPresent = true;
                break;
            }     
        }            

        return sdCardPresent;
	}
	
	public static boolean hasBuiltInStorage(){
		boolean r = false;
		
		boolean hasSystemStorage = false;
		boolean hasInternalStorage = false;
		
		Enumeration v = FileSystemRegistry.listRoots();
		for (Enumeration e = v ; e.hasMoreElements() ;) {
			String storageType = e.nextElement().toString().toLowerCase();
	        if(storageType.indexOf("system")>=0){
	        	hasSystemStorage = true;
	        }
	        else if(storageType.indexOf("store")>=0){
	        	hasInternalStorage = true;
	        }
	    }
		
		r = hasSystemStorage & hasInternalStorage;
		
		return r;
	}
	
	public static String getDefaultPhotoFolderPath() {
		if (hasSdcardStorage()) {
			return System.getProperty("fileconn.dir.memorycard.photos");
		}
		
		return System.getProperty("fileconn.dir.photos");
	}
	
	public static byte[] getBytesFromFile(String filePath) {
		FileConnection fconn = null;
		InputStream input = null;

		try {
			fconn = (FileConnection)Connector.open(filePath);
			long length = 0;
			length = fconn.fileSize();
			if (length > Integer.MAX_VALUE) {
				System.out.println("File is too large");
				fconn.close();
				return null;
			}
			
			byte[] bytes = new byte[(int) length]; // Create the byte array to hold the data
			int offset = 0;
			int numRead = 0;

			input = fconn.openInputStream();
			while (offset < bytes.length
					&& (numRead = input.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				System.out.println("Could not completely read file");
				throw new IOException("Could not completely read file " + fconn.getName());
			}

			return bytes;
		} catch (Exception e) {
			System.out.println("FileUtils.getBytesFromFile - e : " + e.toString());
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}

			if (fconn != null) {
    			try {
    				fconn.close();
    			} catch (IOException e) {
    			}
			}
		}
	}
	
	public static boolean saveBitmapAsJPG(String path, Bitmap bmp) {
		DLogger.log(TAG, "saveBitmapAsJPG start - path : " + path);
		FileConnection fconn = null;
		OutputStream output = null;
		try {
			JPEGEncodedImage encodedImage = JPEGEncodedImage.encode(bmp, 80);
			if (encodedImage == null) {
				DLogger.log(TAG, "JPEGEncodedImage.encode() is null.");
				return false;
			}
			
			byte[] imageBytes = encodedImage.getData();
			if (imageBytes == null) {
				DLogger.log(TAG, "JPEGEncodedImage.encode().getData() is null.");
				return false;
			}
			
			fconn = (FileConnection) Connector.open(path);
			if (!fconn.exists()) {
				fconn.create();
			}
			output = fconn.openOutputStream();
			output.write(imageBytes);
			output.flush();
			return true;
		} catch (Exception e) {
			DLogger.log(TAG, "FileUtils.saveBitmapAsJPG() e = " + e.toString());
			return false;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}

			if (fconn != null) {
				try {
					fconn.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void createFolder(String path) {
		DLogger.log(TAG, "+createFolder() " + path);
		FileConnection fc = null;
		try {
			fc = (FileConnection) Connector.open(path, Connector.READ_WRITE);
			if (false == fc.exists()) {
				fc.mkdir();
			}
		} catch (Exception e) {
			DLogger.log(TAG, "createFolder() " + path + ", e: " + e.toString());
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (Exception e) {
				}
			}
		}
		DLogger.log(TAG, "-createFolder() " + path);
	}
}
