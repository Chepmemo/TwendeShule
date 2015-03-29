package com.twendeshule.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.twendeshule.TwendeShule;
import com.twendeshule.view.HomeActivity;

public class EventHTTPTask extends AsyncTask<String, String, String> {
	private Activity parentActivity;
	
	private int parent_id;
	private int grp_id;
	private String start_time;
	private String reminder;
	private String num_days;
	
	private ProgressDialog dialog;
	
	SharedPreferences liveData;
	Map<String, String> eventData;
	
	public EventHTTPTask(Activity a, Map<String, String> data){
		
		this.parentActivity = a;
		this.eventData = data;
		
		start_time = data.get("start_time");
		reminder = data.get("reminder");
		num_days = data.get("num_days");
		
		liveData = this.parentActivity.getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
		this.parent_id = liveData.getInt("PARENT_ID", -1);
		this.grp_id = liveData.getInt("CURRENT_GRP", -1);
	}
	
	@Override
	protected void onPreExecute(){
		
		super.onPreExecute();
		
        dialog = new ProgressDialog(this.parentActivity);
        
        dialog.setMessage("Updting shared calendar...");
        dialog.setCancelable(false);
        dialog.show();
	}
	
	@Override
	protected String doInBackground(String... uri) {
		
		String responseString = "{success: false, msg: Did not visit the server!}";
		
		long event_id = new ScheduleHelper(parentActivity, eventData).schedule();
		
		if(event_id != -1){
			
			HttpClient httpclient = new DefaultHttpClient();
			
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(1);
			
			//Function to be called by index.php
			params.add(new BasicNameValuePair("fx", "addEvent"));
			
			params.add(new BasicNameValuePair("parent_id", Integer.valueOf(parent_id).toString()));
			params.add(new BasicNameValuePair("grp_id", Integer.valueOf(grp_id).toString()));
			params.add(new BasicNameValuePair("start_time", start_time));
			params.add(new BasicNameValuePair("reminder", reminder));
			params.add(new BasicNameValuePair("num_days", num_days));
			params.add(new BasicNameValuePair("event_id", Long.valueOf(event_id).toString()));
			
			HttpResponse response;

			try {
				
				HttpPost post = new HttpPost(uri[0]);
				
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
				
				response = httpclient.execute(post);
				
				StatusLine statusLine = response.getStatusLine();
				
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					
					response.getEntity().writeTo(out);
					
					out.close();
					
					responseString = out.toString();
					
				} else {
					
					response.getEntity().getContent().close();
					
					throw new IOException(statusLine.getReasonPhrase());
					
				}
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		
		return responseString;
	}
	
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        
        //SharedPreferences.Editor editor = settings.edit();
		Log.i("RESULT", result);
		
        if (dialog.isShowing()){
            dialog.dismiss();
        }
        
        try {
        	
			JSONObject json = new JSONObject(result);
			
			Boolean success = json.getBoolean("success");
			
			
			if(success){
				//Display a toast and move to the next Activity
				Toast.makeText(this.parentActivity, "Your shedule has been shared with group members", Toast.LENGTH_SHORT).show();
				
				this.parentActivity.startActivity(new Intent(this.parentActivity, HomeActivity.class));
				

				JSONObject data = json.getJSONObject("data");
				
				int scheduleId = data.getInt("schedule_id");
				
				SharedPreferences.Editor editor = liveData.edit();
				
				editor.putInt("SCHEDULE_ID", scheduleId);
				
				editor.commit();
				
			} else {
				
				
				Toast.makeText(this.parentActivity, json.getString("msg"), Toast.LENGTH_LONG).show();
			
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
    }

}
