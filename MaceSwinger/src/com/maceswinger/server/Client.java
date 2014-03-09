package com.maceswinger.server;

public class Client
{
	private String username, ip;

	public Client(String username, String ip)
	{
		this.username = username;
		this.ip = ip;
	}

	public String getUsername()
	{
		return username;
	}

	public String getIP()
	{
		return this.ip;
	}

	public String toString()
	{
		return this.username;
	}
}
