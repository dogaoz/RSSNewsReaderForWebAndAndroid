package com.dogaozkaraca.bitpops.AdapterItems;


import android.util.Log;

public class Feed {

	private String title;
	private String url;

	public Feed(String title, String url) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.url = url;

	}


	public String getFaviconURL()
	{
		String[] fURL = url.split("/");

		String favicon = fURL[0] + "//" + fURL[2]+ "/apple-touch-icon.png";
		Log.w("fluxy",favicon);
		return favicon;
	}
	public String getFaviconURL_alt2()
	{
		String[] fURL = url.split("/");

		String favicon = fURL[0] + "//" + fURL[2]+ "/favicon-196x196.png";
		Log.w("fluxy",favicon);
		return favicon;
	}
	public String getFaviconURL_alt3()
	{
		String[] fURL = url.split("/");

		String favicon = fURL[0] + "//" + fURL[2]+ "/mstile-144x144.png";
		Log.w("fluxy",favicon);
		return favicon;
	}
	public String getFaviconURL_alt4()
	{
		String[] fURL = url.split("/");

		String favicon = "http://icons.better-idea.org/icon?size=144&url=" +fURL[2];
		Log.w("fluxy",favicon);
		return favicon;
	}
	public String getTitle()
	{

		return this.title;
	}
	public String getURL()
	{

		return this.url;
	}





}