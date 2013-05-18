package com.example.helloworld.util;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader<T> {
	private static final String LOG_TAG = "ImageLoader";
	
	public interface URLObtainer<T> {
		// run in the background, not on the main thread
		URL getURL(T data) throws MalformedURLException;
	}
	
	private URLObtainer<T> mURLObtainer;
	private Map<ImageView, T> mLoadedImages = new HashMap<ImageView, T>();
	
	
	public ImageLoader(URLObtainer<T> urlObtainer) {
		mURLObtainer = urlObtainer;
	}
	
	
	public void load(final ImageView img, final T data) {
		if (mLoadedImages.containsKey(img)) {
			if (mLoadedImages.get(img).equals(data)) {
				// already loaded (or at least loading), ignore
				return;
			} else {
				Log.d(LOG_TAG, "recycling view is not yet supported");
				return;
			}
		}
		
		AsyncTask<Object, Object, Drawable> task = new AsyncTask<Object, Object, Drawable>() {

			@Override
			protected void onPreExecute() {   
				img.setImageResource(android.R.color.transparent);
			}

			@Override
			protected void onPostExecute(Drawable image) {   
				img.setImageDrawable(image);
			}

			@Override
			protected Drawable doInBackground(Object... arg0) {
				// TODO Auto-generated method stub
				Object content = null;
			    try{
			      URL url = mURLObtainer.getURL(data);
			      content = url.getContent();
			    }
			      catch(Exception ex)
			    {
			    	Log.e("MainActivity", "Exception!", ex);
			    }
			    InputStream is = (InputStream)content;
			    return Drawable.createFromStream(is, "src");
			}
        	
        };
        
        task.execute();
    }
}
