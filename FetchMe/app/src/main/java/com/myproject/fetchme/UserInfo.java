package com.myproject.fetchme;


import android.app.Activity;

import android.os.Bundle;
import android.os.AsyncTask;

import java.io.InputStream;
import java.io.IOException;

import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Button.OnClickListener;
import android.widget.*;

import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.graphics.drawable.*;

import android.media.*;

import android.net.Uri;
import java.util.*;


public class UserInfo extends Activity 
{
//Url browser button, this button will load user url browser intent 
	Button button;
	
//Share profile button
	Button sharebtn;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.userpage);
		Intent i = getIntent();
		
//Recieve loaded user data from mainActivity to send
//to views on itemclick in listview
		
		String name = i.getStringExtra("name");
		String url = i.getStringExtra("url");
		String dpurl = i.getStringExtra("dpurl");
		
		// set views for this activity
	  new DownloadImageTask((ImageView) findViewById(R.id.imagedpp)).execute(dpurl);
	   TextView WaitText = (TextView) findViewById(R.id.textwait);
	   TextView txtUsername = (TextView) findViewById(R.id.username);
	   TextView txtUserUrl = (TextView) findViewById(R.id.profileurl);
	   TextView txtPicurl = (TextView) findViewById(R.id.userdpx);

		txtUsername.setText(name);
		txtUserUrl.setText(url);
		txtPicurl.setText(dpurl);
		
		//url button id
		button = (Button) findViewById(R.id.button1);
       
		//share button id
		sharebtn = (Button) findViewById(R.id.share);
		
		
		
	//set user profile url parsed data to string so we can send to button intent
	
		final String ur = (url);
        
		//url button onclick listener and its method (what to do on click)
		//in this case pass url string to url call intent function
		
		button.setOnClickListener(new OnClickListener() {

				@Override
				
				public void onClick(View arg0) {
					Intent browserIntent =
						new Intent(Intent.ACTION_VIEW);
					browserIntent.setData(Uri.parse(ur));
					startActivity(browserIntent);
					
	}
			});
			
			//Declare local variables,arrange in array to send to shareintent
			final String getname  = name;
			final String geturl = url;
			String text = "Checkout this awesome developer ";
			String space = " @ ";
			final String message = text+space+getname+geturl;
			
			sharebtn.setOnClickListener (new OnClickListener()
			{
			
	@Override
	public void onClick(View arg0) {
		Intent shareIntent =
			new Intent(Intent.ACTION_SEND);
			
/**this code is our main call to type of action intent performs
in this case it calls other apps on phone that accepts text data types (strings) and then sends it to them
*/	
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT,message);
	    startActivity(Intent.createChooser(shareIntent,"Share on"));

	}

	});
	
		}	
			
	
}
