/**
 * LocalyticsSession.java - BlackBerry Localytics SDK.
 * See the BlackBerry integration guide: http://www.localytics.com/docs/blackberry-integration/
 * 
 * Use of this software is governed by the Localytics Modified BSD License:
 * The Localytics Modified BSD License
 * Copyright (c) 2013, Char Software, Inc. d/b/a Localytics
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1) Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * 2) Neither the name of Char Software, Inc., Localytics nor the names of its contributors may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * 3) THIS SOFTWARE IS PROVIDED BY CHAR SOFTWARE, INC. D/B/A LOCALYTICS ”AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL CHAR SOFTWARE, INC. D/B/A LOCALYTICS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.Localytics.LocalyticsSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;

import net.ildoo.app.FilterConfig;
import net.rim.device.api.i18n.Locale;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.synchronization.UIDGenerator;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.util.StringUtilities;

final public class LocalyticsSession {
	
	// Determines whether logMessage calls actually cause output.
	private static final boolean DO_LOGGING = FilterConfig.isDebugMode;
	
    // Whether or not to the simulator should use MDS to connect.
    // By default this should be false, however if you are testing
    // in an environment where MDS will be the expected connection method,
    // set this to true to have the simulator attempt to use MDS. This variable
    // has no effect on what happens on a real device.
    private static final boolean USE_MDS_IN_SIMULATOR = false;
    	
	private static final String LibraryVersion = "bb_2.1";
	private static final String ANALYTICS_URL = "http://analytics.localytics.com/api/v2/applications/";
	private static final String BaseFileDir = "file:///store/home/user/";
	private static final String LocalyticsDir = "localytics/";
	private static final String LogPrefix = "(localytics)";
	private static final String CustomDimensinPrefix = "c_";
	private static final String SessionFilePrefix = "s_";
	private static final String UploadFilePrefix = "u_"; 
	
	// The location in the persisted store the original Localytics ID was stored in. This is only used
	// in cases where an application was installed on the device Prior to BB library version 1.1
	private static final long DEVICE_ID_KEY = 0x75eab1eed15cb42eL; // com.Localytics.StorageHelper.deviceId	
	private static boolean CheckStore = true; // Only check the persisted store for a value once. 
	private static int SequenceNumber = 1;
	
	private boolean _isSessionOpen = false;
	private boolean _isUploading = false;
	private String _applicationKey = null;
	private String _sessionUUID = null;
	private String _sessionFilePath = null;
	private String _sessionStart = null;
	
	/**
	 * Creates the LocalyticsSession object. The first time this object is initialized it will create a directory in
	 * the device user's home directory unique to this application.
	 */
	public LocalyticsSession(final String appKey)
	{
		this._applicationKey = appKey;
		String sessionFilePath = null;
		
		// Create the directory structure if it doesn't already exist.
		try
		{
			sessionFilePath = LocalyticsSession.BaseFileDir + LocalyticsDir;
			FileConnection fc = (FileConnection) Connector.open(sessionFilePath, Connector.READ_WRITE);
			if(!fc.exists()) {
				fc.mkdir();
			}
			fc.close();
			
			this._sessionFilePath = sessionFilePath + this._applicationKey + "/";
			fc = (FileConnection) Connector.open(this._sessionFilePath, Connector.READ_WRITE);
			if(!fc.exists()) {
				fc.mkdir();
			}
			fc.close();
		}
		catch(Exception e)
		{
			logMessage("Failed to create " + sessionFilePath + " error: " + e.getMessage());
		}
	}
	
	/**
     * Opens the session.  This must be the first call because until this happens no data will be
     * collected.  If there are too many sessions already stored, then the session will not be 
     * opened and all subsequent Localytics calls will return immediately.  This is a synchronous
     * call so once it is completed Localytics will not affect the app at all until the next
     * Localytics call.
     */
	public void open()
	{
		try
		{
			// Gaurantee that only one session can be opened.
			synchronized(LocalyticsSession.class)
			{
				if(this._isSessionOpen == true)
				{
					logMessage("Session already open.");
					return;
				}
				this._isSessionOpen = true;
			}
				
			StringBuffer openString = new StringBuffer();			
			this._sessionUUID = LocalyticsSession.generateRandomId();
			this._sessionStart = currentTime();
			this._sessionFilePath = BaseFileDir + LocalyticsDir + this._applicationKey + "/" + SessionFilePrefix + this._sessionUUID; 
			this._sessionFilePath = this._sessionFilePath + ".txt"; // Allows built in media browser to display files on device
			
	        // Format of an open session:
	        //{ "dt":"s",       // This is a session blob
	        //  "ct": long,     // seconds since Unix epoch
	        //  "u": string     // A unique ID attached to this session
	        //  "nth": int,     // This is the nth session on the device. (not required)
	        //  "new": boolean, // New vs returning (not required)
	        //  "sl": long,     // seconds since last session (not required)
	        //  "lat": double,  // latitude (not required)
	        //  "lng": double,  // longitude (not required)
	        //  "c0" : string,  // custom dimensions (not required)
	        //  "c1" : string,
	        //  "c2" : string,
	        //  "c3" : string }
	        openString.append("{\"dt\":\"s\",");
	        openString.append("\"ct\":" + this._sessionStart + ",");
	        openString.append("\"u\":\"" + this._sessionUUID + "\",");
	        
	        openString.append("\"c0\":" + escapeString(getCustomDimension(0, this._applicationKey)) + "," );
	        openString.append("\"c1\":" + escapeString(getCustomDimension(1, this._applicationKey)) + "," );
	        openString.append("\"c2\":" + escapeString(getCustomDimension(2, this._applicationKey)) + "," );
	        openString.append("\"c3\":" + escapeString(getCustomDimension(3, this._applicationKey)) );        
	        
	        openString.append("}\n");
	        
	        if(appendTextToFile(openString.toString(), this._sessionFilePath) == true)
	        {
	        	logMessage("Session successfully opened.");
				this._isSessionOpen = true;
	        }
	        else
	        {
	        	logMessage("Error opening session.");
				this._isSessionOpen = false;
	        }
		}
		catch(Exception e)
		{
			logMessage("Unable to open session: " + e.getMessage());
		}
	}
	
    /**
     * Allows a session to tag a particular event as having occurred.  For
     * example, if a view has three buttons, it might make sense to tag
     * each button click with the name of the button which was clicked.
     * For another example, in a game with many levels it might be valuable
     * to create a new tag every time the user gets to a new level in order
     * to determine how far the average user is progressing in the game.
     * 
     * Best Practices
     * * DO NOT use tags to record personally identifiable information.</li>
     * * The best way to use tags is to create all the tag strings as predefined
     *   constants and only use those.  This is more efficient and removes the risk of
     *   collecting personal information.</li>
     * * Do not set tags inside loops or any other place which gets called
     *   frequently.  This can cause a lot of data to be stored and uploaded.</li>
     * @param event The name of the event which occurred.
     * @param attributes A hashtable of key/value pairs describing the attributes of the event
     */	
	public void tagEvent(final String eventName, final Hashtable attributes)
	{
		try
		{
			if(this._isSessionOpen == false) 
			{
				logMessage("Event not tagged because session wasn't open.");
				return;
			}
			
			// Sample Event Blob
		    //{ "dt":"e",  // event data time
	        //  "ct":1302559181,   // client time
	        //  "u":"48afd8beebd3",   // unique id
	        //  "su":"696c44ebf6f",   // session id
	        //  "n":"Button Clicked",  // event name
	        //  "lat": double,   // lat (optional)
	        //  "lng": double,   // lng (optional)
	        //  "attrs":   // event attributes (optional)
	        //  {
	        //      "Button Type":"Round"
	        //  },
	        //  "c0" : string, // custom dimensions (optional)
	        //  "c1" : string,
	        //  "c2" : string,
	        //  "c3" : string }
	
	        StringBuffer eventString = new StringBuffer();
	        eventString.append("{\"dt\":\"e\",");
	        eventString.append("\"ct\":" + currentTime() + ",");
	        eventString.append("\"u\":\"" + LocalyticsSession.generateRandomId() + "\",");
	        eventString.append("\"su\":\"" + this._sessionUUID + "\",");
	                
	        eventString.append("\"c0\":" + escapeString(getCustomDimension(0, this._applicationKey)) + "," );
	        eventString.append("\"c1\":" + escapeString(getCustomDimension(1, this._applicationKey)) + "," );
	        eventString.append("\"c2\":" + escapeString(getCustomDimension(2, this._applicationKey)) + "," );
	        eventString.append("\"c3\":" + escapeString(getCustomDimension(3, this._applicationKey)) + "," );
	        
	        eventString.append("\"n\":" + LocalyticsSession.escapeString(eventName));
	
	        if (attributes != null)
	        {
	            eventString.append(",\"attrs\": {");
	        	
	            Enumeration attr_enum = attributes.keys();
	            for(int currentAttr =0; attr_enum.hasMoreElements(); currentAttr++)
	            {
	            	String key = (String)attr_enum.nextElement();
	            	if(currentAttr > 0) { eventString.append(","); }
	            	eventString.append(escapeString(key) + ":" + escapeString((String)attributes.get(key)));
	            }
	            eventString.append("}");
	        }
	        	
	        eventString.append("}\n");
	        appendTextToFile(eventString.toString(), this._sessionFilePath);            
	        logMessage("Tagged event: " + escapeString(eventName));
		}
		catch(Exception e)
		{
			logMessage("Unable to tag event: " + e.getMessage());
		}
	}
	
	/**
	 * Convenience method for calling tagEvent without any attributes.
	 * @param eventName the name of the event
	 */
	public void tagEvent(final String eventName)
	{
		tagEvent(eventName, null);
	}
	
     /**
      * Closes the opened session.  One this is called data will no longer be written to the
      * session and it cannot be reopened.  Closing a session does not cause data to be
      * uploaded.  This is a synchronous call.
      */
     public void close()
     {
    	 try
    	 {
	    	 // Session can only be closed once.
	 		synchronized(LocalyticsSession.class)
	 		{
		    	if(this._isSessionOpen == false) 
		    	{
		    		logMessage("Session could not be closed b/c it is not open.");
		    		return;
		    	}
		       
		    	this._isSessionOpen = false;
	 		}
	
	    	// Sample close blob
	        //{ "dt":"c", // close data type
	        //  "u":"abec86047d-ae51", // unique id for teh close
	        //  "ss": session_start_time, // time the session was started
	        //  "su":"696c44ebf6f",   // session uuid
	        //  "ct":1302559195,  // client time
	        //  "ctl":114,  // session length (optional)
	        //  "cta":60, // active time length (optional)
	        //  "fl":["1","2","3","4","5","6","7","8","9"], // Flows (optional)
	        //  "lat": double,  // lat (optional)
	        //  "lng": double,  // lng (optional)
	        //  "c0" : string,  // custom dimensions (otpinal)
	        //  "c1" : string,
	        //  "c2" : string,
	        //  "c3" : string }
	        
	        StringBuffer closeString = new StringBuffer();
	        closeString.append("{\"dt\":\"c\",");
	        closeString.append("\"u\":\"" + LocalyticsSession.generateRandomId() + "\",");
	        closeString.append("\"ss\":" + this._sessionStart + ",");
	        closeString.append("\"su\":\"" + this._sessionUUID + "\",");
	        closeString.append("\"ct\":" + currentTime() + ",");
	        closeString.append("\"c0\":" + escapeString(getCustomDimension(0, this._applicationKey)) + "," );
	        closeString.append("\"c1\":" + escapeString(getCustomDimension(1, this._applicationKey)) + "," );
	        closeString.append("\"c2\":" + escapeString(getCustomDimension(2, this._applicationKey)) + "," );
	        closeString.append("\"c3\":" + escapeString(getCustomDimension(3, this._applicationKey)) );
	        closeString.append("}\n");
	        
	        appendTextToFile(closeString.toString(), this._sessionFilePath); // the close blob    	
	    	logMessage("Session closed");
    	 }
    	 catch(Exception e)
    	 {
    		 logMessage("Unable to close session: " + e.getMessage());
    	 }
     }
     
     /**
      * Starts an upload. Only one upload may happen at a time. A Thread is returned in case the user wishes
      * to know when the upload completes however it is not recommended to consume this. Instead upload should be 
      * treated as a fire-and-forget operation.
	  * @return The thread the upload is running on
      */
     public Thread upload()
     {
    	 try
    	 {
	    	 // Only one upload should be able to happen at a time.
	    	 synchronized(LocalyticsSession.class)
	    	 {
		    	 if(this._isUploading)
		    	 {
		    		 logMessage("Cannot start upload b/c previous upload is not yet complete.");
		    		 return null;
		    	 }
		    	 
		    	 this._isUploading = true;
	    	 }
		    	 
	         UploaderThread uploader = new UploaderThread(this._applicationKey); 
	         uploader.setPriority(Thread.MIN_PRIORITY);
	         uploader.start();
	         return uploader;
    	 }
    	 catch(Exception e)
    	 {
    		 logMessage("Unable to start uploader: " + e.getMessage());
    		 return null;
    	 }
     }
     
     /**
      * Sets a custom dimension. This value will be persisted until it is changed.
      * @param position The position of the dimension to change. Valid values are 0 through 3.
      * @param value The value to store in this dimension. Set this to an empty string to clear the value.
      */
     public void setCustomDimension(final int dimensionPosition, final String value)
     {
    	 try
    	 {
    		 String filename = BaseFileDir + LocalyticsDir + this._applicationKey + "/" + LocalyticsSession.CustomDimensinPrefix + Integer.toString(dimensionPosition);
    		 LocalyticsSession.overwriteFile(value, filename);
    	 }
    	 catch(Exception e)
    	 {
    		 logMessage("Unable to set custom dimension." + e.getMessage());
    	 }
     }
     
     /**
      * Returns the value for a given custom dimension
      * @param dimensionPosition The position (0 to 3) to get the value of
      * @param appKey The application to get a value for
      * @return The value of the custom dimension or an empty string if the value is empty or not there
      */
     private static String getCustomDimension(final int dimensionPosition, final String appKey)
     {
    	 String filename = BaseFileDir + LocalyticsDir + appKey + "/" + LocalyticsSession.CustomDimensinPrefix + Integer.toString(dimensionPosition);
    	 String contents = LocalyticsSession.readTextFile(filename);
    	 
    	 if (contents == null || contents.length() == 0)
    	 {
    		 return "";
    	 }
    	 
    	 return contents;
     }
     
     /**
      * Callback called after an upload completes.
      */
     private void uploadComplete()
     {
    	 this._isUploading = false;
     }
     
     /**
      * Constructs a blob header for an upload. Each blob has a unique ID and sequence number.
      */
     private static String getBlobHeader(final String appKey) 
     {    	 
         //{ "dt":"h",  // data type, h for header
         //  "pa": int, // persistent store created at
         //  "seq": int,  // blob sequence number, incremented on each new blob, 
         //               // remembered in the persistent store
         //  "u": string, // A unique ID for the blob. Must be the same if the blob is re-uploaded!
         //  "attrs": {
         //    "dt": "a" // data type, a for attributes
         //    "au":string // Localytics Application Id
         //    "du":string // Device UUID
         //    "lv":string // Library version
         //    "av":string // Application version
         //    "dp":string // Device Platform
         //    "dll":string // Locale Language (optional)
         //    "dlc":string // Locale Country (optional)
         //    "dma":string // Device Manufacturer (optional)
         //    "dmo":string // Device Model
         //    "dov":string // Device OS Version
         //    "nca":string // Network Carrier (optional)
         //    "dac":string // Data Connection Type (optional)
         //    "udid":string } } // client side hashed version of the udid
     	
	     StringBuffer  blobString = new StringBuffer();
	     blobString.append("{\"dt\":\"h\",");     	
	
	     blobString.append("\"seq\":" + Integer.toString(LocalyticsSession.SequenceNumber) + ",");
	     LocalyticsSession.SequenceNumber += 1;
	     
	     blobString.append("\"u\":\"" + LocalyticsSession.generateRandomId() + "\",");     	     	 
	     blobString.append("\"attrs\":");
	     blobString.append("{\"dt\":\"a\",");
	     blobString.append("\"au\":\"" + appKey + "\",");
	     blobString.append("\"du\":\"" + LocalyticsSession.getDeviceUUID() + "\",");
	     blobString.append("\"lv\":\"" + LocalyticsSession.LibraryVersion + "\",");
	     blobString.append("\"av\":\"" + ApplicationDescriptor.currentApplicationDescriptor().getVersion() + "\",");
	     blobString.append("\"dp\":\"BlackBerry\",");
	     blobString.append("\"dll\":\"" + Locale.getDefaultForSystem().getDisplayLanguage() + "\",");
	     blobString.append("\"dma\":\"RIM\",");
	     blobString.append("\"dmo\":\"" + DeviceInfo.getDeviceName() + "\",");
	     blobString.append("\"dov\":\"" + LocalyticsSession.getOsVersion() + "\"");
	
	     blobString.append("}}\n");
	
	     return blobString.toString();     	
     }
     
	/**
     * Escapes strings for the JSON parser
     * @param rawString The string we want to escape.
     * @return An escaped string ready for JSON
     */
    private static String escapeString(final String rawString)
    {	
        StringBuffer parseString = new StringBuffer("\"");
        
        int startRead = 0;       // Index to start reading at
        int stopRead = 0;        // Index characters get read from and where the substring ends
        int bufferLength = rawString.length();
        
        if (rawString == null || bufferLength == 0) {
            return "\"\"";
        }
        
        // Every time we come across a " or \, append what we have so far, append a \,
        // Skip null characters or newlines,
        // then manage our indexes to continue where we left off.
        while (stopRead < bufferLength)
        {
            if (rawString.charAt(stopRead) == '\"' || rawString.charAt(stopRead) == '\\')
            {
                parseString.append(rawString.substring(startRead, stopRead));
                parseString.append('\\');
                startRead = stopRead;
            }
            // Skip null characters.
            else if (rawString.charAt(stopRead) == '\0' || rawString.charAt(stopRead) == '\n')
            {
                parseString.append(rawString.substring(startRead, stopRead));
                startRead = stopRead + 1;
            }
            stopRead++;
        }
        // Append whatever is left after parsing
        parseString.append(rawString.substring(startRead, stopRead));
        // and finish with a closing "
        parseString.append('\"');
        return parseString.toString();
    }
    
    /**
     * Creates a random string by concatenating a random long to the current time.     
     * @return a randomly generated string which is likely to be unique.
     */
    private static String generateRandomId()
    {
        // Generate a random ID (and make sure it is positive).        
        long randomNumber = UIDGenerator.makeLUID(UIDGenerator.getUniqueScopingValue(), UIDGenerator.getUID());
        randomNumber = randomNumber < 0 ? (randomNumber * -1) : randomNumber;
        return Long.toString(randomNumber) + Long.toString(System.currentTimeMillis());        
    }
    
    /**
     * Gets the current time in seconds since Unix Epoch
     */
    private static String currentTime()
    {
    	return Long.toString(System.currentTimeMillis() / 1000);
    }
		
	/**
	 * Appends a given string to a given file. If the file doesn't exist it is created.
	 * @param text Text to add to the file
	 * @param filename Full path and name of the target file
	 */
    private static boolean appendTextToFile(final String text, final String filename)
    {
    	OutputStream os = null;
    	FileConnection fconn = null;
    	
		synchronized(LocalyticsSession.class)
		{
	    	try 
	    	{
	    		fconn = (FileConnection) Connector.open(filename, Connector.READ_WRITE);
	    		if (!fconn.exists()) {
	    			fconn.create();
	    		}
	
	    		os = fconn.openOutputStream(fconn.fileSize());
	    		os.write(text.getBytes());
	    	} 
	    	catch (IOException e) {
	    		logMessage("Error writing to file (" + filename + "): " + e.getMessage());
	    		return false;
	    	} 
	    	finally 
	    	{
	    		try
	    		{
	    			if (null != os)
	    				os.close();
	    			if (null != fconn)
	    				fconn.close();
	    		} 
	    		catch (IOException e) {
	    			logMessage("Error closing file: (" + filename + "): " + e.getMessage());
	    			return false;
	    		}
	    	}
		}
    	
    	return true;
    }
    
    /**
     * Deletes a file if it exists and recreates it with new contents.
     * Creates the file if it does not exist.
	 * @param contents New contents of the file
	 * @param filename Full path and name of the target file 
     */
    private static void overwriteFile(final String contents, final String filename)
    {
    	FileConnection fconn = null;
    	
    	try 
    	{
    		synchronized(LocalyticsSession.class)
    		{
				fconn = (FileConnection) Connector.open(filename, Connector.READ_WRITE);
				if(fconn.exists())
				{
					fconn.delete();
					fconn.close();
				}
    		}
		} 
    	catch (IOException e) {
			logMessage("Unable to delete open file:" + filename);
		}
    	
    	appendTextToFile(contents, filename);
    }
    
    /**
     * Returns the contents of a file
     * @param filename The full path and name of the file
     */
    private static String readTextFile(final String fName) 
    {
    	String result = null;
    	FileConnection fconn = null;
    	InputStream is = null;
    	
    	synchronized(LocalyticsSession.class)
    	{
	    	try 
	    	{
	    		fconn = (FileConnection) Connector.open(fName, Connector.READ_WRITE);
	    		if (!fconn.exists())
	    		{
	    			return null;
	    		}
	    		
	    		is = fconn.openInputStream();
	    		byte[] data = IOUtilities.streamToBytes(is);
	    		result = new String(data);
	    	} 
	    	catch (IOException e) {
	    		logMessage("Error reading file: " + e.getMessage());
	    	} 
	    	finally 
	    	{    		
	    		try 
	    		{
	    			if (null != is)
	    				is.close();
	    	     
	    			if (null != fconn)
	    				fconn.close();
	    		} 
	    		catch (IOException e) {
	    			logMessage("Unable to close file: " + e.getMessage());
	    		}
	    	}
    	}
    	
    	return result;
    }
    
    /**
     * Loops through all upload files and concatenates their contents
     * @param appKey application get contents for
     * @return a string containing the upload body
     */
    private static String getUploadContents(final String appKey)
    {
    	String path = LocalyticsSession.BaseFileDir + LocalyticsDir + appKey + "/";    	    	
    	StringBuffer contents = new StringBuffer();
    	Enumeration fileEnum = null;
    	FileConnection fc;
    	
    	try {
			fc = (FileConnection)Connector.open(path);
			fileEnum = fc.list();
    	} catch (IOException e) {
			logMessage("Error collecting data to upload: " + e.getMessage());
			return "";
		}
		
    	// Loop through every file in the directory and get the contents out of the upload files
		while(fileEnum.hasMoreElements())
		{
			String currentFile = (String)fileEnum.nextElement();
			if (currentFile.startsWith(LocalyticsSession.UploadFilePrefix))
			{
				contents.append(readTextFile(path + currentFile));				
			}
		}
		
		return contents.toString();
    }
    
    /**
     * Deletes all files for appKey that start with the upload prefix
     */
    private static void deleteUploadFiles(final String appKey)
    {
    	String path = LocalyticsSession.BaseFileDir + LocalyticsDir + appKey + "/";
    	Enumeration fileEnum = null;
    	FileConnection fc = null;
    	
		try 
		{
			fc = (FileConnection)Connector.open(path);
			fileEnum = fc.list();
			
	    	// Loop through every file in the directory
			while(fileEnum.hasMoreElements())
			{
				String currentFile = (String)fileEnum.nextElement();
				if (currentFile.startsWith(LocalyticsSession.UploadFilePrefix))
				{
					FileConnection deleter = (FileConnection)Connector.open(path + currentFile);
					deleter.delete();
					deleter.close();
				}
			}
		} 
		catch (IOException e) 
		{
			logMessage("Error deleting upload files: " + e.getMessage());
		}
		finally
		{
			try {
				fc.close();
			} catch (IOException e) {
				logMessage("Unable to close file: " + e.getMessage());
			}
		}
    }
    
    /**
     * Collects every session file on the device and puts them all in a single upload file with
     * a unique upload header. The new file is saved with the upload prefix so it can be uploaded
     */
    private static void renameOrAppendSessionFiles(final String appKey)
    {
    	String path = LocalyticsSession.BaseFileDir + LocalyticsDir + appKey + "/";
    	Enumeration fileEnum = null;
    	FileConnection fc = null;
    	boolean addedHeader = false;
    	
    	try {
			fc = (FileConnection)Connector.open(path);
			fileEnum = fc.list();
			
	    	// Path to a new upload file that will be created if there are any new sessions to store.
	    	String newUploadFile = path + UploadFilePrefix + generateRandomId() + ".txt";
	    	
	    	// Loop through every file in the directory
			while(fileEnum.hasMoreElements())
			{
				String currentFile = (String)fileEnum.nextElement();
				if (currentFile.startsWith(LocalyticsSession.SessionFilePrefix))
				{
					if(!addedHeader)
					{
						appendTextToFile(getBlobHeader(appKey), newUploadFile);
						addedHeader = true;
					}
					
					appendTextToFile(readTextFile(path + currentFile), newUploadFile);
					FileConnection deleter = (FileConnection)Connector.open(path + currentFile);
					deleter.delete();
					deleter.close();
				}
			}
    	}
    	catch (IOException e) {
			logMessage("Error renaming or appending session files: " + e.getMessage());
			return;
		}
    }
    
    /**
     * Gets the version of a running Blackberry application and returns it. This determines the OS version.
     * @return the version of the operating system
     */
    protected static String getOsVersion()
    {                                        
        // DeviceInfo.getOSVersion returns null on 4.2 so this is the only foolproof way to get OS version
        ApplicationDescriptor[] appDescriptors = ApplicationManager.getApplicationManager().getVisibleApplications();                                
        int numApps = appDescriptors.length;        
        for (int currentApp = numApps-1; currentApp>=0; --currentApp)
        {            
            if ((appDescriptors[currentApp].getModuleName()).equals("net_rim_bb_ribbon_app"))
            {                
                return appDescriptors[currentApp].getVersion();                
            }
        }
        return null;
    }
    
    /**
     * Gets the the type of radio network this device is currently using.
     * @return A pretty string for the radio type
     */
    protected static String getNetworkType()
    {
        switch (RadioInfo.getNetworkType())
        {
            // Return any of the valid return types.
            case RadioInfo.NETWORK_802_11 : return "802_11";
            case RadioInfo.NETWORK_CDMA : return "cdma";           
            case RadioInfo.NETWORK_GPRS : return "gprs";
            case RadioInfo.NETWORK_IDEN : return "iden";         
            case RadioInfo.NETWORK_NONE : return "none";
            case RadioInfo.NETWORK_UMTS : return "umts";
        }
  
        return ""; 
    }
    
    /**
     * Retrieves the DeviceId for this device from the persistent store.  
     * In older versions of the library, the ID was generated by the library and 
     * written to the persistent store.  As a result, this function now looks to see
     * if an old value is still around and uses that.  Otherwise the device PIN is
     * hashed and returned.  Simulators will always generate a unique ID and store it
     * so that each simulator will appear as a different device to somebody testing 
     * their integration.
     *      
     * @return A deviceId which will be consistent across all apps and sessions
     */    
    public static String getDeviceUUID()
    {           
        String deviceId = null;        
        
        if(CheckStore)
        {
	        // Check for the old deviceId.  This will only be present if this device
	        // has run an application with the older version of the Localytics library or
	        // if this is a simulator.
	        PersistentObject deviceIdStore = PersistentStore.getPersistentObject(LocalyticsSession.DEVICE_ID_KEY);
	        if(deviceIdStore != null)
	        {
	            deviceId = (String)deviceIdStore.getContents();            
	            if(deviceId != null)  // if an old id was found, return it
	            {
	                return deviceId;
	            }
	            
	            // if this was a simulator, and no ID was found, generate one.
	            if(DeviceInfo.isSimulator())
	            {
	                deviceId = LocalyticsSession.generateRandomId();
	                deviceIdStore.setContents(deviceId);
	                deviceIdStore.commit();
	                return deviceId;
	            }
	        }
        }
        
        // Getting here means no original ID was stored so this is safe to use.
        String devicePIN = Integer.toString(DeviceInfo.getDeviceId(), 16);
        return Long.toString( StringUtilities.stringHashToLong(devicePIN), 16 );   
    }   
    
    /**
     * Determined whether or not this device is configured to use an MDS server
     * @return true if the device uses MDS, false otherwise.
     */
    protected static String isMDSConfigured()
    {
        return CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS) ? "true" : "false"; 
    }    
    
    /**
     * Prepends a LocalyticsSession String and logs a message to the console.
     * @param message Message to log.
     */
    private static void logMessage(final String message)
    {
    	if(LocalyticsSession.DO_LOGGING == false) {
    		return;
    	}
    	
    	 System.out.println(LocalyticsSession.LogPrefix + message);
    }
    
    /**
     * Determine the optimal way to connect to the network, and get the bits to the webserice
     * @param sessions the sessions to upload
     * @return true if an upload succeeded, false otherwise.
     */
    private static boolean uploadSessions(final String sessions, final String appKey)
    {
        int response;
        
        logMessage(sessions);
        
        // figure out how to connect, or abort the upload if nothing is found
        String connectionString = getConnectionString();
        if(connectionString == null)
        {
            logMessage("No connection string found.");
            return false;
        }
        
        String targetUrl = LocalyticsSession.ANALYTICS_URL + appKey + "/uploads" + connectionString;
        logMessage("upload URL: " + targetUrl);
        
        try
        {
            HttpConnection connection = (HttpConnection)Connector.open(targetUrl);
            connection.setRequestMethod(HttpConnection.POST);
            connection.setRequestProperty("Content-type", "application/json");
            OutputStream os = connection.openOutputStream(); 
            os.write(sessions.getBytes());
                
            response = connection.getResponseCode();
            connection.close();
            if(response != -1)
            {
                logMessage("SUCCESS! response was: " + Integer.toString(response));
                return true;
            }
        }
        catch(IOException e)
        {
            logMessage("Upload failed: " + e.getMessage());
            return false;
        }
        
        logMessage("Upload succeeded but server response was invalid.");
        return false;        
    }
    
    /**
     * The thread which performs uploads in the background. It terminates after a single execution. 
     */
    final private class UploaderThread extends Thread
    {
    	private String _appKey = null;
    	
    	/**
    	 * Creates a new Localytics Uploader thread
    	 * @param appKey Application to upload the data for
    	 */
    	public UploaderThread(String appKey)
    	{
    		this._appKey = appKey;
    	}
    	
    	/**
    	 * Uploads the data on disk and then deletes it on success.
    	 */
        public void run()
        {
        	try
        	{
	        	//logMessage("Beginning Upload");
	        	renameOrAppendSessionFiles(this._appKey);
	        	String uploadBody = getUploadContents(this._appKey);
	        	logMessage("upload body: " + uploadBody);
	        	
	        	if(uploadBody == null || uploadBody.length() == 0)
	        	{
	        		logMessage("Nothing to upload. Aborting");
	        		return;
	        	}
	        	
	        	if(uploadSessions(uploadBody, this._appKey) == false)
	        	{
	        		logMessage("Upload failed");
	        		return;
	        	}
	        	
	        	// Getting here means the upload succeeded so we can delete all upload files.
	        	deleteUploadFiles(this._appKey);
	        	uploadComplete();
        	}
        	catch(Exception e)
        	{
        		logMessage("Unable to run uploader: " + e.getMessage());
        	}
        }
    };
    
    /**
     * Determines what connection type to use and returns the necessary string to use it.
     * @return A string with the connection info
     */
    private static String getConnectionString()
    {
        // This code is based on the connection code developed by Mike Nelson of AccelGolf.
        // http://blog.accelgolf.com/2009/05/22/blackberry-cross-carrier-and-cross-network-http-connection        
        String connectionString = null;                
                        
        // Simulator behavior is controlled by the USE_MDS_IN_SIMULATOR variable.
        if(DeviceInfo.isSimulator())
        {
            if(LocalyticsSession.USE_MDS_IN_SIMULATOR)
            {
                    logMessage("Device is a simulator and USE_MDS_IN_SIMULATOR is true");
                    connectionString = ";deviceside=false";                 
            }
            else
            {
                    logMessage("Device is a simulator and USE_MDS_IN_SIMULATOR is false");
                    connectionString = ";deviceside=true";
            }
        }                                        
                
        // Wifi is the preferred transmission method
        else if(WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED)
        {
            logMessage("Device is connected via Wifi.");
            connectionString = ";interface=wifi";
        }
                        
        // Is the carrier network the only way to connect?
        else if((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_DIRECT) == CoverageInfo.COVERAGE_DIRECT)
        {
            logMessage("Carrier coverage.");
                        
            String carrierUid = getCarrierBIBSUid();
            if(carrierUid == null) 
            {
                // Has carrier coverage, but not BIBS.  So use the carrier's TCP network
                logMessage("No Uid");
                connectionString = ";deviceside=true";
            }
            else 
            {
                // otherwise, use the Uid to construct a valid carrier BIBS request
                logMessage("uid is: " + carrierUid);
                connectionString = ";deviceside=false;connectionUID="+carrierUid + ";ConnectionType=mds-public";
            }
        }                
        
        // Check for an MDS connection instead (BlackBerry Enterprise Server)
        else if((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_MDS) == CoverageInfo.COVERAGE_MDS)
        {
            logMessage("MDS coverage found");
            connectionString = ";deviceside=false";
        }
        
        // If there is no connection available abort to avoid bugging the user unnecssarily.
        else if(CoverageInfo.getCoverageStatus() == CoverageInfo.COVERAGE_NONE)
        {
            logMessage("There is no available connection.");
        }
        
        // In theory, all bases are covered so this shouldn't be reachable.
        else
        {
            logMessage("no other options found, assuming device.");
            connectionString = ";deviceside=true";
        }        
        
        return connectionString;
    }
    
    /**
     * Looks through the phone's service book for a carrier provided BIBS network
     * @return The uid used to connect to that network.
     */
    private static String getCarrierBIBSUid()
    {
        ServiceRecord[] records = ServiceBook.getSB().getRecords();
        int currentRecord;
        
        for(currentRecord = 0; currentRecord < records.length; currentRecord++)
        {
            if(records[currentRecord].getCid().toLowerCase().equals("ippp"))
            {
                if(records[currentRecord].getName().toLowerCase().indexOf("bibs") >= 0)
                {
                    return records[currentRecord].getUid();
                }
            }
        }
        
        return null;
    }
}