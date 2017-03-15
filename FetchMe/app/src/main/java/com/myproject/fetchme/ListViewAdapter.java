package com.myproject.fetchme;

import java.io.InputStream;


import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.util.ArrayList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.*;


/**Listview adapter creates a custom
list view for our items to fit it
**/

public class ListViewAdapter extends ArrayAdapter<saveData> {
	ArrayList<saveData> objectList;
	LayoutInflater l;
	int Resource;
	ViewHolder holder;

	//Listview  population objects to create id and parse data
	
	public ListViewAdapter(Context context, int resource, ArrayList<saveData> objects) {
		super(context, resource, objects);
		l = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		objectList = objects;
	}

	
	/** recieve parsed json data from Asynctask class
	//hold the data,assign them to unique id's so that other activity
	can recieve the data. while Asynclass is done
	*/

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = l.inflate(Resource, null);
			holder.thumbnail = (ImageView) v.findViewById(R.id.imagedp);
			holder.usrname = (TextView) v.findViewById(R.id.username);
			holder.dpurll = (TextView) v.findViewById(R.id.userdp);
			holder.profurl = (TextView) v.findViewById(R.id.profileurl);
			v.setTag(holder);
		}
		
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		holder.thumbnail.setImageResource(R.drawable.ic_launcher);
		new DownloadImageTask(holder.thumbnail).execute(objectList.get(position).getImage());
		holder.usrname.setText(objectList.get(position).getUserName());
		holder.dpurll.setText(objectList.get(position).getImgurl());
		holder.profurl.setText(objectList.get(position).getProfileUrl());
		return v;

	}

	static class ViewHolder {
		public ImageView thumbnail;
		public TextView usrname;
		public TextView dpurll;
		public TextView profurl;
		

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		//download the images/data from url quietely in background to
		//prevent ui freeze
		
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
		
		//when image download is done the send the bitmap image
		//to activity image view for display
		
		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
}
