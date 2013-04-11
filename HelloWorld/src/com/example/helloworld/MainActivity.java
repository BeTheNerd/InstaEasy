package com.example.helloworld;

import com.blinxbox.restig.auth.InstagramAuthDialog;
import com.blinxbox.restig.auth.InstagramAuthDialog.DialogListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
	public void OnMedia(int count) {
		// TODO Auto-generated method stub
		EditText username = (EditText)findViewById(R.id.username);
		username.setText("" + count);
	}
    
    
    
}
