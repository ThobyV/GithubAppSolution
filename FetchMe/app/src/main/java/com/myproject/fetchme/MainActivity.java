package com.myproject.fetchme;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.graphics.Bitmap;
import android.graphics.drawable.*;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.net.*;



public class MainActivity extends Activity {

	ArrayList<saveData> objectList;

	ListViewAdapter adapter;
	
	/** This class works with the listview
	adapter class,it contains an Async method
	to help us download the images quietly
	from the json url and send to listvuew for display
	**/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_activity);				
		objectList = new ArrayList<saveData>();
		
		// get our items to display from the 
		//Github search Java users in lagos query url 
		
		new JSONAsyncTask().execute("https://api.github.com/search/users?q=location:lagos+language:java");

		//custom listview to contain them (thanks to listview adapter
		ListView listview = (ListView)findViewById(R.id.listview);
		adapter = new ListViewAdapter(getApplicationContext(), R.layout.item_listview, objectList);

		listview.setAdapter(adapter);

		
		//Set a listvuew item onclick listener to send the items to
		//a new activity for display or use
		
		listview.setOnItemClickListener(new OnItemClickListener() {

		 @Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				
				//Show username in a toast on itemclick
				Toast.makeText(getApplicationContext(), objectList.get(position).getUserName(), Toast.LENGTH_LONG).show();	
					
					//start activity on item click
					
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, UserInfo.class);
					
	/**how we specify the item we want to send to nee activity using 
	the item id from listview (position,id) since our user profile image/url are all parsed from string url
					**/
					
					String name = (objectList.get(position).getUserName());
					String url = (objectList.get(position).getProfileUrl());
					String profilepics = (objectList.get(position).getImage());
				    String dpurl = (objectList.get(position).getImgurl());
					
					
					//Using intent to send putExtra,we mean we want to send these
					//data to the next activity to recieve and use
		
					intent.putExtra("dpurl", dpurl);
					intent.putExtra("name", name);
					intent.putExtra("url", url);
				    intent.putExtra("profilepics", profilepics);
					startActivity(intent);
				}
			});
	}

	
	/*Finally our saviour the Async class which downloads the image data from url
	*and prevents ui freeze,also initializes download of all other json data like username,url
	*/
	
	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			
			/*display a message to calm nerves while asynclass is working to download image on activity opening using (onPrexecute)
			*/
			
			dialog.setMessage("Loading,please wait");
			dialog.setTitle("Connecting to github server");
			dialog.show();
			dialog.setCancelable(false);
		}

//start the quite process of downloading
		@Override
		protected Boolean doInBackground(String... urls) {
			try {

				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject jsono = new JSONObject(data);
					
					//"items" is our github json array 
					JSONArray jarray = jsono.getJSONArray("items");

					//load the items to a looo to get them from source,each time
					//assigning a unique id etc since they are all arrays.
					
					for (int i = 0; i < jarray.length(); i++) {
						JSONObject object = jarray.getJSONObject(i);

						saveData sent = new saveData();

						//recieve data from userinfo to parse them
						sent.setUserName(object.getString("login"));
						sent.setProfileUrl(object.getString("html_url"));
						sent.setImgurl(object.getString("avatar_url"));
						sent.setImage(object.getString("avatar_url"));
                  
						
						objectList.add(sent);
					}
					return true;
				}

				//------->

			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}

		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			adapter.notifyDataSetChanged();
			if(result == false)
				
				//throw a toast to tell if an error occured whike trying to download data
				//maybe next a view instead of a toast will suffice
				Toast.makeText(getApplicationContext(), "An error occured,please check your internet connection", Toast.LENGTH_LONG).show();

		}
	}






}

