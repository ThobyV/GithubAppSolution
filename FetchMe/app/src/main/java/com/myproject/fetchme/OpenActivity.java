package com.myproject.fetchme;


import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import android.widget.Button;
import android.widget.Button.OnClickListener;

import android.view.View.OnClickListener;


//This is our launch activity to display some cool stuff.
public class OpenActivity extends Activity
{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.openview);
		
		//Button to start Our Main activity when you're done reading
		Button start = (Button) findViewById(R.id.openbutton);
		start.setOnClickListener (new OnClickListener() {
			
			@Override 
			public void onClick(View arg0)
			{
				Intent openit = new Intent (OpenActivity.this, MainActivity.class);
			     startActivity(openit);
				}
			
		});
		
	
}



}
