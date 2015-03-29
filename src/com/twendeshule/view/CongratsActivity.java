package com.twendeshule.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.twendeshule.R;

public class CongratsActivity extends Activity {

	private String schoolName;
	private TextView msgView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.congrats_activity);
		
		this.msgView = (TextView) findViewById(R.id.congrats_msg);
		
		Bundle bundle = getIntent().getExtras();
		
		this.schoolName = bundle.getString("SCHOOL_NAME");
		
		String msg = "Thank you for using Twende Shule.\n Your data has been submitted to " + this.schoolName + " for verification.\n Once they get back to us, we�ll do let you know.";
		
		this.msgView.setText(msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.congrats, menu);
		return true;
	}
	
	public void backHome(View v){
		Intent intent = new Intent(CongratsActivity.this, HomeActivity.class);
		
		CongratsActivity.this.startActivity(intent);
		
	}

}
