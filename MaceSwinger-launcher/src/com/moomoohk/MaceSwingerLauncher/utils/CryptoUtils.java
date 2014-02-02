package com.moomoohk.MaceSwingerLauncher.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.moomoohk.MaceSwingerLauncher.MainFrame;
import com.moomoohk.Mootilities.ExceptionHandling.ExceptionDisplayDialog;

/*
 * Copyright 2003-2010 Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland
 * www.source-code.biz, www.inventec.ch/chdh
 * 
 * This module is multi-licensed and may be used under the terms of any of the following licenses:
 * 
 * EPL, Eclipse Public License, V1.0 or later, http://www.eclipse.org/legal
 * LGPL, GNU Lesser General Public License, V2.1 or later, http://www.gnu.org/licenses/lgpl.html
 * GPL, GNU General Public License, V2 or later, http://www.gnu.org/licenses/gpl.html
 * AL, Apache License, V2.0 or later, http://www.apache.org/licenses
 * BSD, BSD License, http://www.opensource.org/licenses/bsd-license.php
 * MIT, MIT License, http://www.opensource.org/licenses/MIT
 * 
 * Please contact the author if you need another license.
 * This module is provided "as is", without warranties of any kind.
 * 
 * For the purposes of Mace Swinger, we are using the MIT license
 */
public class CryptoUtils
{
	private static final char[] map1 = new char[64];
	static
	{
		int i = 0;
		for (char c = 'A'; c <= 'Z'; c++)
			map1[i++] = c;
		for (char c = 'a'; c <= 'z'; c++)
			map1[i++] = c;
		for (char c = '0'; c <= '9'; c++)
			map1[i++] = c;
		map1[i++] = '+';
		map1[i++] = '/';
	}

	private static final byte[] map2 = new byte[128];
	static
	{
		for (int i = 0; i < map2.length; i++)
			map2[i] = -1;
		for (int i = 0; i < 64; i++)
			map2[map1[i]] = (byte) i;
	}

	/**
	 * Encodes a byte array into Base64 format. No blanks or line breaks are inserted in the output.
	 * 
	 * @param in
	 *            An array containing the data bytes to be encoded.
	 * @param iOff
	 *            Offset of the first byte in <code>in</code> to be processed.
	 * @param iLen
	 *            Number of bytes to process in <code>in</code>, starting at <code>iOff</code>.
	 * @return A character array containing the Base64 encoded data.
	 */
	public static String base64Encode(byte[] in)
	{
		int iOff = 0;
		int iLen = in.length;
		int oDataLen = (iLen * 4 + 2) / 3;
		int oLen = ((iLen + 2) / 3) * 4;
		char[] out = new char[oLen];
		int ip = iOff;
		int iEnd = iOff + iLen;
		int op = 0;
		while (ip < iEnd)
		{
			int i0 = in[ip++] & 0xff;
			int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
			int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
			int o0 = i0 >>> 2;
			int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
			int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			int o3 = i2 & 0x3F;
			out[op++] = map1[o0];
			out[op++] = map1[o1];
			out[op] = op < oDataLen ? map1[o2] : '=';
			op++;
			out[op] = op < oDataLen ? map1[o3] : '=';
			op++;
		}
		return new String(out);
	}

	/**
	 * Decodes a byte array from Base64 format. No blanks or line breaks are allowed within the Base64 encoded input data.
	 * 
	 * @param in
	 *            A character array containing the Base64 encoded data.
	 * @param iOff
	 *            Offset of the first character in <code>in</code> to be processed.
	 * @param iLen
	 *            Number of characters to process in <code>in</code>, starting at <code>iOff</code>.
	 * @return An array containing the decoded data bytes.
	 * @throws IllegalArgumentException
	 *             If the input is not valid Base64 encoded data.
	 */
	public static byte[] base64Decode(String s)
	{
		char[] in = s.toCharArray();
		int iOff = 0;
		int iLen = s.toCharArray().length;
		if (iLen % 4 != 0)
			throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
		while (iLen > 0 && in[iOff + iLen - 1] == '=')
			iLen--;
		int oLen = (iLen * 3) / 4;
		byte[] out = new byte[oLen];
		int ip = iOff;
		int iEnd = iOff + iLen;
		int op = 0;
		while (ip < iEnd)
		{
			int i0 = in[ip++];
			int i1 = in[ip++];
			int i2 = ip < iEnd ? in[ip++] : 'A';
			int i3 = ip < iEnd ? in[ip++] : 'A';
			if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
				throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
			int b0 = map2[i0];
			int b1 = map2[i1];
			int b2 = map2[i2];
			int b3 = map2[i3];
			if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
				throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
			int o0 = (b0 << 2) | (b1 >>> 4);
			int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
			int o2 = ((b2 & 3) << 6) | b3;
			out[op++] = (byte) o0;
			if (op < oLen)
				out[op++] = (byte) o1;
			if (op < oLen)
				out[op++] = (byte) o2;
		}
		return out;
	}

	private static byte[] key = { 0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79 };

	public static String AESencrypt(String strToEncrypt)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final String encryptedString = base64Encode(cipher.doFinal(strToEncrypt.getBytes()));
			return encryptedString;
		}
		catch (Exception e)
		{
			new ExceptionDisplayDialog(MainFrame.mainFrame, e);
		}
		return null;

	}

	public static String AESdecrypt(String strToDecrypt)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			final String decryptedString = new String(cipher.doFinal(base64Decode(strToDecrypt)));
			return decryptedString;
		}
		catch (Exception e)
		{
			new ExceptionDisplayDialog(MainFrame.mainFrame, e);
		}
		return null;
	}

	public static String toSHA512(byte[] bytes)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] encryptedBytes = md.digest(bytes);
			String encryptedString = "";
			for (int i = 0; i < encryptedBytes.length; i++)
				encryptedString += Integer.toString((encryptedBytes[i] & 0xff) + 0x100, 16).substring(1);
			return encryptedString;
		}
		catch (Exception e)
		{
			new ExceptionDisplayDialog(MainFrame.mainFrame, e);
		}
		return null;
	}
}