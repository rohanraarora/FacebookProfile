package com.example.facebookprofile;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.facebookprofile.pictureAsync.onImageDownloaded;
import com.example.facebookprofile.profileAsync.profileImplement;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements profileImplement, onImageDownloaded {
	
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View view){
		EditText enterUsername = (EditText) findViewById(R.id.enterUsername);
		String s = "https://graph.facebook.com/" + enterUsername.getEditableText().toString();
		pictureAsync picTask = new pictureAsync(this);
		profileAsync task = new profileAsync(this);
		picTask.execute(s + "/picture?type=large");
		picTask.delegate = this;
		task.delegate = this;
		task.execute(s);
		
		
	}
	
	public static User parseJSON(String jsonString) throws JSONException{
		JSONObject top = new JSONObject(jsonString);
		String name = "NA";
		if(top.has("name"))
		name = top.getString("name");
		String username = top.getString("username");
		String id = "NA";
		if(top.has("id"))
		id = top.getString("id");
		String gender = "NA";
		if(top.has("gender"))
		gender = top.getString("gender");
		String link = "https://www.facebook.com/"+username;
		if(top.has("link"))
		link = top.getString("link");
		User output = new User(name, username, id, gender, link);
		return output;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		if(user == null){
			Toast t = Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT);
			t.show();
		}
		else{
			TextView name = (TextView) findViewById(R.id.name);
			TextView id = (TextView) findViewById(R.id.id);
			TextView gender = (TextView) findViewById(R.id.gender);
			TextView link = (TextView) findViewById(R.id.link);
			name.setText(user.name);
			id.setText(user.id);
			gender.setText(user.gender);
			link.setText(user.link);
		}
	}

	@Override
	public void setImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		if(bitmap != null){
			ImageView image = (ImageView) findViewById(R.id.imageView1);
			image.setImageBitmap(bitmap);
		}
	}

}
