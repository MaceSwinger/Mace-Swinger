package com.moomoohk.MaceSwingerLauncher.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.moomoohk.Mootilities.OSUtils.OSUtils;

public class ExternalIPTest
{
	public static void main(String[] args)
	{

		try
		{
			//			if (!SSLUtils.doneCerts && !SSLUtils.installCerts())
			//				return;

			System.out.println("Connecting to the Mace Swinger A.R.I.S.T.O.T.L.E. login API...");

			String charset = "UTF-8";

			HttpURLConnection connection = (HttpURLConnection) new URL("http://maceswinger.com/account/hi.php").openConnection();
			connection.setDoOutput(true);
			connection.setConnectTimeout(15 * 1000);
			connection.setReadTimeout(15 * 1000);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			connection.setRequestProperty("User-Agent", "Mace Swinger Launcher/1.0 (" + OSUtils.getCurrentOS().toString() + ")");
			connection.setRequestMethod("POST");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			final StringBuilder inputLines = new StringBuilder("");
			while ((inputLine = in.readLine()) != null)
				inputLines.append(inputLine.trim() + "\n");
			in.close();

			System.out.println(inputLines.toString().trim());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
