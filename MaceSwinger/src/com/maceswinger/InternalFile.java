package com.maceswinger;

import java.io.InputStream;

public class InternalFile
{
	public static InputStream get(String filePath)
	{
		return new InternalFile().getClass().getClassLoader().getResourceAsStream(filePath);
	}
}
