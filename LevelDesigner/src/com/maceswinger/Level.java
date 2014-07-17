package com.maceswinger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.moomoohk.Mootilities.OSUtils.OSUtils;

public class Level {

	private Main main;
	private int width, height;
	private byte[] tiles;
	public Tile selected;
	private List<Tile> included = new ArrayList<Tile>();

	public Level(Main m) {
		this.main = m;
	}
	
	public boolean loadLevel(String path) {
		this.width = 30;
		this.height = 10;
		this.tiles = new byte[width * height];
		for (int y = height-1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
			setTile(x, y, Tile.tiles[new Random().nextInt(4)]);
			}
		}
		setTile(3, 4, Tile.PLAYER);
		saveLevel("bant");
		return false;
	}

	public boolean saveLevel(String name) {
		if(!included.contains(Tile.PLAYER)){
			System.out.println("Level must contain player spawn point!");
			return false;
		}
		long time = System.currentTimeMillis();
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(OSUtils.getDynamicStorageLocation()+"Mace Swinger"
							+ File.separator + name + ".map"), "utf-8"));// TODO add seperate folder for levels
	
			for (Tile t : included) {
				String type = t.getClass().getName();
				type=type.split("ger.", 2)[1];
				writer.write(t.getC() + ":"+type+"(" + t.name + ")\n");
			}
			writer.write("{\n");

			for (int y = height-1; y >= 0; y--) {
				for (int x = 0; x < width; x++) {
					writer.write(getTile(x,y).getC());
					//if(x==width-1)writer.write(";\n");
				}
				writer.write(";\n");
			}
			writer.write("}");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
		long dt = System.currentTimeMillis()-time;
		System.out.println("Saved in: "+dt+" ms");
		System.out.println("Saved at: "+OSUtils.getDynamicStorageLocation()+"Mace Swinger"
		+ File.separator + name + ".map");
		return true;
	}

	public void setTile(int x, int y, Tile t) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		tiles[x + y * width] = t.getId();
		if (!included.contains(t)&&t!=Tile.VOID) {
			included.add(t);
		}
	}

	public Tile getTile(int x, int y) {

		if (0 > x || x >= width || 0 > y || y >= height)

			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];

	}

	public void renderTiles() {

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				getTile(x, y).setX(x * 32).setY(y * 32).render(this);

			}
		}

	}
}
