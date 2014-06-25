package com.maceswinger.client.render.lighting;
import org.lwjgl.util.vector.Vector2f;



public class Ray {

	
	public Vector2f start, end;
	public Ray(Vector2f start, Vector2f end){
		this.start = start;
		this.end = end;
	}
}
