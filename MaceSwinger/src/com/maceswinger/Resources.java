package com.maceswinger;

import java.io.InputStream;

/**
 * Manages all game resources.
 * 
 * @since Feb 5, 2014
 */
public class Resources
{
	/**
	 * Returns an InputStream to a certain resource file.
	 * 
	 * @param filePath
	 *            Path to resource file
	 * @return And InputStream to the resource file
	 */
	public static InputStream get(String filePath)
	{
		return new Resources().getClass().getClassLoader().getResourceAsStream(filePath);
	}
}
