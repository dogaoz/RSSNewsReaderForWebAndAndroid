package com.dogaozkaraca.bitpops.loadFeed;

import android.util.Log;

import com.dogaozkaraca.bitpops.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class tillNow_JSON {

	public static JSONObject getJSONFromUrl(String mUrl) {
		InputStream is = null;
		JSONObject jObj = null;
		String json = null;

		// Making HTTP request

		InputStream inputStream = null;
		HttpURLConnection urlConnection = null;
		Integer result = 0;
		try {
			URL url = new URL(mUrl);

			urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
			urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
			urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
			urlConnection.setRequestMethod("GET");
			int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
			if (statusCode ==  200) {
				inputStream = new BufferedInputStream(urlConnection.getInputStream());
				json = Utils.convertInputStreamToString(inputStream);

				Log.w("DO_RSS",json+"");
				result = 1; // Successful
			}else{
				result = 0; //"Failed to fetch data!";
			}
		} catch (Exception e) {
			Log.d("Error", e.getLocalizedMessage());
		}


		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		catch(Exception e)
		{

		}

		// return JSON String
		return jObj;

	}


}
