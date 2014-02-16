/*
* Copyright (c) 2011 Research In Motion Limited.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.dabinci.ui.wait;

import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;

import com.dabinci.ui.GPATools;

public class ProgressAnimationField extends Field implements Runnable 
{
	private static final int ANIMATION_FRAME_INTERVAL = 50;

    private int frameInterval = ANIMATION_FRAME_INTERVAL;
    
    private Bitmap[] _bitmap;
    private int _frameWidth;
    private int _frameHeight;
    
    private int _currentFrame;
    private int _timerID = -1;
    
    private Application _application;
    private boolean _visible;
          
    public ProgressAnimationField(long style) 
    {
        super( style | Field.NON_FOCUSABLE );
        _bitmap = new Bitmap[] {
        	GPATools.getTransparentBitmap(60, 60),
        	Bitmap.getBitmapResource("wait1.png"),
        	Bitmap.getBitmapResource("wait2.png"),
        	Bitmap.getBitmapResource("wait3.png"),
        	Bitmap.getBitmapResource("wait4.png"),
        	Bitmap.getBitmapResource("wait5.png"),
        	Bitmap.getBitmapResource("wait6.png"),
        	Bitmap.getBitmapResource("wait7.png"),
        	Bitmap.getBitmapResource("wait8.png"),
        	Bitmap.getBitmapResource("wait9.png"),
        	Bitmap.getBitmapResource("wait10.png"),
        	Bitmap.getBitmapResource("wait11.png"),
        	Bitmap.getBitmapResource("wait12.png"),
        	Bitmap.getBitmapResource("wait13.png"),
        	Bitmap.getBitmapResource("wait14.png"),
        	Bitmap.getBitmapResource("wait15.png"),
        };
        _frameWidth = 60;
        _frameHeight = 60;
        
        _application = Application.getApplication();
    	System.out.println("111+");
    }
    
    public void run() 
    {
        if( _visible ) {
            invalidate();
        }
    }
    
    protected void layout( int width, int height ) 
    {
        setExtent( _frameWidth, _frameHeight );
    }
    
    protected void paint( Graphics g ) 
    {
        g.drawBitmap( 0, 0, _frameWidth, _frameHeight, _bitmap[_currentFrame], 0, 0);
        _currentFrame++;
        if( _currentFrame >= _bitmap.length) {
            _currentFrame = 0;
        }
    }
    
    protected void onDisplay() 
    {
        super.onDisplay();
        _visible = true;
        if( _timerID == -1 ) {
			_timerID = _application.invokeLater( this, frameInterval, true ); 
        } 
    }
    
    protected void onUndisplay() 
    {
        super.onUndisplay();
        _visible = false;
        if( _timerID != -1 ) {
            _application.cancelInvokeLater( _timerID );
            _timerID = -1;
        }
    }
}


