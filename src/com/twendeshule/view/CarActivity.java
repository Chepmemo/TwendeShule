package com.twendeshule.view;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.twendeshule.TwendeShule;
import com.twendeshule.R;
import com.twendeshule.controller.RegisterCarHTTPTask;

public class CarActivity extends Activity {
	
	private EditText model;
	private EditText reg;
	private EditText capacity;
	
	private int parentId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_activity);
		
		Bundle bundle = getIntent().getExtras();
		
		this.parentId = bundle.getInt("PARENT_ID");
		
		model = (EditText)findViewById(R.id.car_model_text);
		reg = (EditText)findViewById(R.id.car_reg_text);
		capacity = (EditText)findViewById(R.id.car_cap_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.car, menu);
		return true;
	}
	
	public void registerCar(View v){
		
		SharedPreferences settings = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
        SharedPreferences.Editor editor = settings.edit();
        
        editor.putInt("CURR_STEP", 2);
        
        editor.commit();
        
        Map<String, String> data = new HashMap<String, String>();
        data.put("MODEL", model.getText().toString());
        data.put("REG", reg.getText().toString());
        data.put("CAP", capacity.getText().toString());
        data.put("PARENT_ID", Integer.valueOf(this.parentId).toString());
        
		new RegisterCarHTTPTask(this, data).execute(TwendeShule.SERVER_URL);
	}

}
