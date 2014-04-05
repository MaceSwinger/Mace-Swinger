package com.maceswinger.utils;

import org.lwjgl.input.Mouse;

import com.maceswinger.client.GameClient;

public class CustomMouse {
	
	
	
	public static double getX(){
		return Mouse.getX()*CustomDisplay.getxScale();
		
	}
	public static double getY(){
		return Mouse.getY()*CustomDisplay.getyScale();
		
	}
	public static boolean isButtonDown(int i) {
		return Mouse.isButtonDown(i);
	}
}
