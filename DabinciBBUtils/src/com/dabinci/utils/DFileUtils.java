package com.dabinci.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

public class DFileUtils {
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
}
