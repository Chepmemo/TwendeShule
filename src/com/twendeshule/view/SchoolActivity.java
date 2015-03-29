package com.twendeshule.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.twendeshule.TwendeShule;
import com.twendeshule.R;
import com.twendeshule.controller.GetSchools;
import com.twendeshule.controller.RegisterChildHTTPTask;
import com.twendeshule.model.School;

public class SchoolActivity extends Activity {
	
	private Spinner schoolSpinner;
	private Spinner levelSpinner;
	
	private ArrayList<School> schoolsList;
	
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_activity);
		
		schoolSpinner = (Spinner)findViewById(R.id.select_school);
		levelSpinner = (Spinner)findViewById(R.id.select_class);
		
		new FetchSchoolsHTTPTask().execute(TwendeShule.SERVER_URL);
	}
	
	
	public class FetchSchoolsHTTPTask extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute(){
			
			super.onPreExecute();
			
	        dialog = new ProgressDialog(SchoolActivity.this);
	        
	        dialog.setMessage("Loading Schools...");
	        dialog.setCancelable(true);
	        dialog.show();
		}
		
		@Override
		protected String doInBackground(String... uri) {
			
			
			schoolsList = new GetSchools(uri[0]).get();
			
			return schoolsList.toString();
			
		}
		
		
		@Override
		protected void onPostExecute(String result){
			
			super.onPostExecute(result);
			
	        if (dialog.isShowing()){
	            dialog.dismiss();
	        }
	        populateSchools();
		}

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.school, menu);
		return true;
	}
	
	public void submitToSchool(View v){
		
		SharedPreferences settings = getSharedPreferences(TwendeShule.PREFS_NAME, 0);
		
        SharedPreferences.Editor editor = settings.edit();
        
        editor.putInt("CURR_STEP", 4);
        
        editor.commit();
        
		Map<String, String> data = populate();
		
		data.put("KID_SCHOOL", String.valueOf(((School)schoolSpinner.getSelectedItem()).getSchoolID()));
		data.put("SCHOOL_NAME", String.valueOf(((School)schoolSpinner.getSelectedItem()).getSchoolName()));
		data.put("KID_LEVEL", levelSpinner.getSelectedItem().toString());
        
		new RegisterChildHTTPTask(this, data).execute(TwendeShule.SERVER_URL);
        
	}

	private Map<String, String> populate(){
		Map<String, String> data = new HashMap<String, String>();
		
		SharedPreferences settings = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
		data.put("KID_NAME", settings.getString("KID_NAME", "404"));
		data.put("KID_GENDER", settings.getString("KID_GENDER", "404"));
		data.put("KID_AGE", settings.getString("KID_AGE", "404"));
		data.put("PARENT_ID", Integer.valueOf(settings.getInt("PARENT_ID", 0)).toString());
		
		return data;
		
	}
	
	private void populateSchools(){
		
		List<String> lables = new ArrayList<String>();
	 
	    for (int i = 0; i < schoolsList.size(); i++) {
	        lables.add(schoolsList.get(i).getSchoolName());
	    }
	    
	    ArrayAdapter<School> spinnerAdapter = new ArrayAdapter<School>(this, android.R.layout.simple_spinner_dropdown_item, schoolsList);
	    
	    // attaching data adapter to spinner
	    schoolSpinner.setAdapter(spinnerAdapter);
	
	}
}
