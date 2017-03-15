package com.myproject.fetchme;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;


/***
A helper class to help us download selected 
user Image using id from listview 
to userProfile Activity

**/

public  class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
 {
	ImageView imagedpp;
	
	public DownloadImageTask(ImageView imagedpp) {
		this.imagedpp = imagedpp;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {

			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		imagedpp.setImageBitmap(result);
		
	}
	}
