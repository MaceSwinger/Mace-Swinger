package com.maceswinger.client.render.lighting;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

public class Light {
	public Vector2f location;
	public Vector2f prevLocation;
	public float radius;
	public float red;
	public float green;
	public float blue;
	public ArrayList<Point> points = new ArrayList<Point>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();
	public ArrayList<Float> angles = new ArrayList<Float>();
	public ArrayList<Intersect> intersects = new ArrayList<Intersect>();
	public Light(Vector2f location, float radius, float red, float green, float blue) {
		this.location = location;
		this.prevLocation =  null;
		this.radius= radius;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public boolean hasMoved(){
		if(prevLocation==null){
			this.prevLocation = new Vector2f(location.x,location.y);
			return true;
		}
		if(prevLocation.x!=location.x||prevLocation.y!=location.y){
			walls.clear();
			points.clear();
			angles.clear();
			intersects.clear();
			this.prevLocation.set(location.x, location.y);
			return true;
		}
		walls.clear();
		points.clear();
		angles.clear();
		return false;
	}
	public void clear(){
		walls.clear();
		points.clear();
		angles.clear();
		intersects.clear();
	}
}
