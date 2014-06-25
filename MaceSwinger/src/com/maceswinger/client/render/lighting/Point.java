package com.maceswinger.client.render.lighting;
import org.lwjgl.util.vector.Vector2f;


public class Point {

	public float distance;
	public float angle;
	public Vector2f pos;
	public Point(Vector2f pos, float d, float a){
		this.pos=pos;
		this.angle = a;
		this.distance = d;
	}
}
