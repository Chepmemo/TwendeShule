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
import com.twendeshule.view.CarActivity;

public class RegisterParentHTTPTask extends AsyncTask<String, String, String>{	
	private Activity parentActivity;
	private Map<String, String> data;
	
	private ProgressDialog dialog;
	
	SharedPreferences liveData;
	
	ParentLocator parentLocator;
	
	public RegisterParentHTTPTask(Activity a, Map<String, String> data){
		
		this.parentActivity = a;
		
		this.data = data;
		
		liveData = this.parentActivity.getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
		parentLocator = new ParentLocator(a);
	}
	
	@Override
	protected void onPreExecute(){
		
		super.onPreExecute();
		
        dialog = new ProgressDialog(this.parentActivity);
        
        dialog.setMessage("Just a Moment...");
        dialog.setCancelable(false);
        dialog.show();
	}
	
	@Override
	protected String doInBackground(String... uri) {
		
		getLocation();
		
		HttpClient httpclient = new DefaultHttpClient();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		
		params.add(new BasicNameValuePair("fx", "registerParent"));
		
		params.add(new BasicNameValuePair("parent_name", data.get("FULL_NAME")));
		params.add(new BasicNameValuePair("parent_email", data.get("EMAIL")));
		params.add(new BasicNameValuePair("parent_residence", data.get("RESIDENCE")));
		params.add(new BasicNameValuePair("parent_phone", data.get("PHONE")));
		
		params.add(new BasicNameValuePair("lat", liveData.getString("LAT", "N/A")));
		params.add(new BasicNameValuePair("lon", liveData.getString("LON", "N/A")));
		
		params.add(new BasicNameValuePair("parent_password", data.get("PASSWORD")));
		
		HttpResponse response;
		String responseString = "NOTHING HERE";
		
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
		
		return responseString;
		
	}


	@Override
    protected void onPostExecute(String result) {
		
        super.onPostExecute(result);
        Log.i("RESULT", result);
        SharedPreferences.Editor editor = liveData.edit();
        
        if(dialog.isShowing()){
        	dialog.dismiss();
        }
        
        try {
			JSONObject json = new JSONObject(result);
			
			Boolean success = json.getBoolean("success");
			
			System.out.println(success);
			
			if(success){
				int parent_id = json.getInt("parent_id");
				
				//Display a toast and move to the next Activity
				Toast.makeText(this.parentActivity, "Congratulations, You've just been registred as a parent into Twende Shule", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(this.parentActivity, CarActivity.class);
				
				intent.setType("text/plain");
				
				intent.putExtra("PARENT_ID", parent_id);
				
				editor.putInt("PARENT_ID", parent_id);
				
				editor.commit();
				
				this.parentActivity.startActivity(intent);
				
			} else {
				
				Toast.makeText(this.parentActivity, "Registration Failed to Complete.", Toast.LENGTH_LONG).show();
			
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
    }
	
	private boolean getLocation(){
		
		// check if GPS enabled     
        if(parentLocator.canGetLocation()){
             
            double latitude = parentLocator.getLatitude();
            double longitude = parentLocator.getLongitude();
             
            SharedPreferences.Editor editor = liveData.edit();
            
            editor.putString("LAT", Double.valueOf(latitude).toString());
            editor.putString("LON", Double.valueOf(longitude).toString());
            
            editor.commit();
            
            return true;
            
        } else {
        	
        	parentLocator.showSettingsAlert();
            
            return false;
        }
	}

}
