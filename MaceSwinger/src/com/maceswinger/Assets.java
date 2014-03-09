package com.maceswinger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Assets
{
	private static class TextureAtlas
	{
		public Texture texture;
		public HashMap<String, Rectangle> sprites = new HashMap<String, Rectangle>();

		public TextureAtlas(String spriteSheet)
		{
			loadTexture(spriteSheet);
			loadSprites(spriteSheet);
		}

		private void loadSprites(String spriteSheet)
		{
			Scanner s = new Scanner(Resources.get("atlas/" + spriteSheet + ".atlas"));
			while (s.hasNextLine())
			{
				String line = s.nextLine();
				String key = line.substring(0, line.indexOf(":"));
				line = line.substring(key.length() + 1);
				int x = Integer.parseInt(line.substring(0, line.indexOf(",")));
				line = line.substring((x + "").length() + 1);
				int y = Integer.parseInt(line.substring(0, line.indexOf(",")));
				line = line.substring((y + "").length() + 1);
				int w = Integer.parseInt(line.substring(0, line.indexOf(",")));
				line = line.substring((w + "").length() + 1);
				int h = Integer.parseInt(line.substring(0, line.indexOf(";")));
				line = line.substring((h + "").length() + 1);
				sprites.put(key, new Rectangle(x, y, w, h));
			}
			s.close();
		}

		private void loadTexture(String spriteSheet)
		{
			InputStream in = null;
			try
			{
				in = Resources.get("image/" + spriteSheet + ".png");
				PNGDecoder decoder = new PNGDecoder(in);

				ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
				decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
				buf.flip();

				int textureID = GL11.glGenTextures();
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

				texture = new Texture(textureID, decoder.getWidth(), decoder.getHeight());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (in != null)
						in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private static HashMap<String, TextureAtlas> textures = new HashMap<String, TextureAtlas>();

	public static Sprite get(String spriteSheet, String sprite)
	{
		if (!textures.containsKey(spriteSheet))
			textures.put(spriteSheet, new TextureAtlas(spriteSheet));
		return new Sprite(textures.get(spriteSheet).texture, textures.get(spriteSheet).sprites.get(sprite));
	}
}
