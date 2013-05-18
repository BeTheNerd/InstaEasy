package com.example.helloworld;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.blinxbox.restig.auth.InstagramAuthDialog;
import com.blinxbox.restig.auth.InstagramAuthDialog.DialogListener;
import com.blinxbox.restinstagram.Parameter;
import com.blinxbox.restinstagram.types.MediaPost;
import com.blinxbox.restinstagram.types.MediaPost.User;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements MediaListener {
	
	private Button _loginBtn;
	private static final String mAppId = "8873b03ecdc14bb29b8dbd78466bcf92";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _loginBtn = (Button)findViewById(R.id.loginBtn);
        _loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InstagramModule module = new InstagramModule(mAppId, MainActivity.this);
				authorize(module, MainActivity.this, "likes", "comments");
			}
		});
		
    }

    public void authorize(final DialogListener listener,
            final Activity activity, final String... permissions) {
    	
        //InstagramModule module = new InstagramModule();
        final InstagramAuthDialog dialog = new InstagramAuthDialog(activity,
                listener, mAppId, permissions);
        dialog.setCancelable(false);
        dialog.show();
    }

        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void OnMedia(final List<MediaPost> medias) {
		
		ListView itemList = (ListView)findViewById(R.id.itemList);
		
		itemList.setAdapter(new ListAdapter() {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return medias.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return medias.get(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public int getItemViewType(int position) {
				return 0;
			}

			@Override
			public int getViewTypeCount() {
				return 1;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View view;
				if (convertView != null) {
					view = convertView;
				}
				else {
					view = getLayoutInflater().inflate(R.layout.item_view, null);
				}
				EditText txt1 = (EditText)view.findViewById(R.id.description);
				EditText txt2 = (EditText)view.findViewById(R.id.numOfLikes);
				txt1.setText(medias.get(position).getCaption().getText());
				txt2.setText(medias.get(position).getLikes().getCount() + "");
				
				final ImageView img = (ImageView)view.findViewById(R.id.image);
				
				
				new AsyncTask<Object, Object, Drawable>() {

					@Override
					protected void onPostExecute(Drawable image) {   
						img.setImageDrawable(image); 
					}

					@Override
					protected Drawable doInBackground(Object... arg0) {
						// TODO Auto-generated method stub
						Object content = null;
					    try{
					      URL url = new URL(medias.get(position).getImages().getThumbnail().getUrl());
					      content = url.getContent();
					    }
					      catch(Exception ex)
					    {
					    	Log.e("MainActivity", "Exception!", ex);
					    }
					    InputStream is = (InputStream)content;
					    return Drawable.createFromStream(is, "src");
					}
		        	
		        }.execute();
				
						
				return view;
			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void registerDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean areAllItemsEnabled() {
				return true;
			}

			@Override
			public boolean isEnabled(int position) {
				return true;
			}
			
		});
		
		for (int i =0 ; i < medias.size(); ++i) {
//			Log.d("InstagramModule", "image: caption = '" + medias.get(i).getCaption().getText() + 
//					"', likes = " + medias.get(i).getLikes().getCount()); 
			 
		}
	}
    
    
    
}
