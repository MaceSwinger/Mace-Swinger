package com.moomoohk.updatemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Class used to add the server's certificate to the KeyStore with your trusted certificates.
 */
public class SSLUtils
{
	public static boolean doneCerts = false;

	public static boolean installCerts()
	{
		try
		{
			String host = "maceswinger.com";
			int port = (host.split(":").length == 1) ? 443 : Integer.parseInt(host.split(":")[1]);
			char[] passphrase = "changeit".toCharArray();

			File file = new File(new File(System.getProperty("java.home") + "/lib/security"), "cacerts");
			passphrase = "changeit".toCharArray();
			System.out.println("Loading KeyStore " + file + "...");
			InputStream in = new FileInputStream(file);
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(in, passphrase);
			in.close();

			SSLContext context = SSLContext.getInstance("TLS");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
			SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
			context.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory factory = context.getSocketFactory();

			try
			{
				System.out.println("Opening connection to " + host + ":" + port + "...");
				SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
				socket.setSoTimeout(15 * 1000);
				System.out.println("Starting SSL handshake...");
				socket.startHandshake();
				socket.close();
			}
			catch (SSLException e)
			{
				e.printStackTrace();
				return false;
			}
			catch (SocketException e)
			{
				e.printStackTrace();
				return false;
			}
			catch (SocketTimeoutException e)
			{
				e.printStackTrace();
				return false;
			}
			catch (UnknownHostException e)
			{
				e.printStackTrace();
				return false;
			}

			X509Certificate[] chain = tm.chain;
			if (chain == null)
			{
				System.out.println("Could not obtain server certificate chain");
				return false;
			}

			System.out.println("Server sent " + chain.length + " certificate" + (chain.length > 1 ? "s" : ""));
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			for (int i = 0; i < chain.length; i++)
			{
				X509Certificate cert = chain[i];
				sha1.update(cert.getEncoded());
				md5.update(cert.getEncoded());
			}
			System.out.print("Adding to truststore... ");
			try
			{
				for (int i = 0; i < chain.length; i++)
				{
					X509Certificate cert = chain[i];
					String alias = host + "-" + (i + 1);
					ks.setCertificateEntry(alias, cert);
					OutputStream out = new FileOutputStream(file.getAbsolutePath());
					ks.store(out, "changeit".toCharArray());
					out.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("Done");
			System.out.println();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		doneCerts = true;
		return true;
	}

	private static class SavingTrustManager implements X509TrustManager
	{

		private final X509TrustManager tm;
		private X509Certificate[] chain;

		SavingTrustManager(X509TrustManager tm)
		{
			this.tm = tm;
		}

		@Override
		public X509Certificate[] getAcceptedIssuers()
		{
			return new X509Certificate[0];
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
		{
			throw new UnsupportedOperationException();
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
		{
			this.chain = chain;
			tm.checkServerTrusted(chain, authType);
		}
	}
}