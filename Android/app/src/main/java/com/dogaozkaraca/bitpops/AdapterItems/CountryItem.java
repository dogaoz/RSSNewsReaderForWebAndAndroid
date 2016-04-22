package com.dogaozkaraca.bitpops.AdapterItems;


import android.content.Context;
import android.graphics.drawable.Drawable;

public class CountryItem {

	private String title;
	private Context ctx;
	public CountryItem(String string,Context ctx2) {
		// TODO Auto-generated constructor stub
		this.title = string;
		this.ctx = ctx2;


	}

	public String getCountryCoded()
	{
		return title;
	}

	public String getCountry() {
		try{
			return ctx.getResources().getString(ctx.getResources().getIdentifier(title, "string", ctx.getPackageName()));
		}
		catch (Exception e) {

		return title;
		}

	}

	public void setCountry(String title) {
		this.title = title;
	}

	public Drawable getDrawable() {
		return ctx.getResources().getDrawable(ctx.getResources().getIdentifier(title, "drawable", ctx.getPackageName()));
	}






}