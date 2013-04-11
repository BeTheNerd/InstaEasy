package com.example.helloworld;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.blinxbox.restig.auth.DialogError;
import com.blinxbox.restig.auth.InstagramAuthDialog.DialogListener;
import com.blinxbox.restinstagram.DefaultInstagramClient;
import com.blinxbox.restinstagram.Parameter;
import com.blinxbox.restinstagram.types.MediaPost.User;

public class InstagramModule implements DialogListener {
	private final String mAppID;
	private MediaListener mMediaListener;
	
	public InstagramModule(String appID, MediaListener mediaListener) {
		mAppID = appID;
		mMediaListener = mediaListener;
	}
    //@Override
    public void onComplete(final Bundle values) {
        final String accessToken = values.getString("access_token");
        final DefaultInstagramClient client = new DefaultInstagramClient(mAppID, accessToken);
        Log.d("InstagramModule", "" + client.isSessionValid());
        Log.d("InstagramModule", "" + accessToken);
        
        new AsyncTask<Object, Object, List<User>>() {

			@Override
			protected List<User> doInBackground(Object... params) {
				List<User> users = client.fetchCollection("/users/search", User.class,
						new Parameter("q", "dushonok")
				).getData();
		        
		    	        
		        return users;
		    }
			
			@Override
			protected void onPostExecute(List<User> users) {   
				Log.d("InstagramModule", "users: " + users.size());
		        Log.d("InstagramModule", "name: " + users.get(0).getFullName());
		        
		        String userID = users.get(0).getId();
		        
		        ExtendedUser user = client.fetchObject("/users/"+userID, ExtendedUser.class);
		        
		        Log.d("InstagramModule", "followed_by count = " + user.counts.followed_by);
		        
				mMediaListener.OnMedia(user.counts.followed_by);     
			}
        	
        }.execute();
    }

    @Override
    public void onError(final DialogError error) {
        // Log error
    }

    @Override
    public void onCancel() {
        // User cancelled.
    }
}