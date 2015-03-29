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

public class LoginHTTPTask extends AsyncTask<String, String, String> {
	private Activity parentActivity;
	
	private String phone;
	private String password;
	private ProgressDialog dialog;
	
	SharedPreferences liveData;
	
	public LoginHTTPTask(Activity a, Map<String, String> data){
		this.parentActivity = a;
		
		phone = data.get("parent_phone");
		password = data.get("parent_password");
		
		liveData = this.parentActivity.getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
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

		HttpClient httpclient = new DefaultHttpClient();
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		
		//Function to be called by index.php
		params.add(new BasicNameValuePair("fx", "login"));
		
		params.add(new BasicNameValuePair("parent_phone", phone));
		params.add(new BasicNameValuePair("parent_password", password));
		
		
		HttpResponse response;
		String responseString = "Nothing yet!";

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
        
        //SharedPreferences.Editor editor = settings.edit();
		Log.e("RESULT", result);
		
        if (dialog.isShowing()){
            dialog.dismiss();
        }
        
        try {
        	
			JSONObject json = new JSONObject(result);
			
			Boolean success = json.getBoolean("success");
			
			
			if(success){
				//Display a toast and move to the next Activity
				Toast.makeText(this.parentActivity, "Welcome. You've just logged into Twende Shule", Toast.LENGTH_SHORT).show();
				
				this.parentActivity.startActivity(new Intent(this.parentActivity, HomeActivity.class));
				

				JSONObject data = json.getJSONObject("data");
				
				int parentId = data.getInt("parent_id");
				
				SharedPreferences.Editor editor = liveData.edit();
				
				editor.putInt("PARENT_ID", parentId);
				
				editor.commit();
				
			} else {
				
				Toast.makeText(this.parentActivity, "Login Failed. Try Again", Toast.LENGTH_LONG).show();
			
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
    }

}
