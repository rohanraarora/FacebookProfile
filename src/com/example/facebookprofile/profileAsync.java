package com.example.facebookprofile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


public class profileAsync extends AsyncTask<String, Integer, User> {

	profileImplement delegate;
	Context context;
	ProgressDialog dialog;
 	
	
	public profileAsync(Context context) {
		this.context = context;
	}
	@Override
	public void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		dialog.setMessage("Loading...Please wait");
		dialog.show();
	}
	
	public interface profileImplement{
		public void update(User user);
	}
	
	@Override
	
	protected User doInBackground(String... arg) {
		// TODO Auto-generated method stub
		String user = arg[0];
		try {
			URL url = new URL(user);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream input = connection.getInputStream();
			if(input == null) return null;
			Scanner networkScanner = new Scanner(input);
			StringBuffer buffer = new StringBuffer();
			int count = 0;
			while(networkScanner.hasNext()){
				String line = networkScanner.next();
				count += line.length();
				//publishProgress(count);
				buffer.append(line);
			}
			networkScanner.close();
			return MainActivity.parseJSON(buffer.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(User result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		if(delegate != null)
		delegate.update(result);
	}
	
//	protected void onProgressUpdate(Integer... values) {
//		// TODO Auto-generated method stub
//		super.onProgressUpdate(values);
//		dialog.setMessage("downloaded: " + values[0]);
//	}
}
