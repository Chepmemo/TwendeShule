package com.twendeshule.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.twendeshule.TwendeShule;
import com.twendeshule.R;

public class ChildActivity extends Activity {
	
	private EditText childName;
	private Spinner gender;
	private EditText age;
	
	private int parent_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.child_activity);
		
		Bundle bundle = getIntent().getExtras();
		
		this.parent_id = bundle.getInt("PARENT_ID");
		
		childName = (EditText)findViewById(R.id.child_names_text);
		gender = (Spinner)findViewById(R.id.child_sex_text);
		age = (EditText)findViewById(R.id.child_age_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.child, menu);
		return true;
	}

	public void registerChild(View v){
		
		SharedPreferences settings = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
        SharedPreferences.Editor editor = settings.edit();
        
        editor.putInt("CURR_STEP", 3);
        
        editor.putString("KID_NAME", childName.getText().toString());
        editor.putString("KID_GENDER", gender.getSelectedItem().toString());
        editor.putString("KID_AGE", age.getText().toString());
        editor.putInt("PARENT_ID", this.parent_id);
        
        editor.commit();
       
		Intent intent = new Intent(ChildActivity.this, SchoolActivity.class);
		
		ChildActivity.this.startActivity(intent);
	}
}
