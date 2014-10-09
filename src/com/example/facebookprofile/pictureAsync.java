package com.example.facebookprofile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class pictureAsync extends AsyncTask<String, Integer, Bitmap> {

	Context context;
	onImageDownloaded delegate;
	
	public interface onImageDownloaded{
		public void setImage(Bitmap bitmap);
	}
	public pictureAsync(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		String picUrl = params[0];
		try {
			URL url = new URL(picUrl);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(delegate!=null)
		delegate.setImage(result);
	}
}
