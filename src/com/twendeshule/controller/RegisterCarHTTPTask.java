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
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.twendeshule.view.ChildActivity;

public class RegisterCarHTTPTask extends AsyncTask<String, String, String>{
	
	private Activity parentActivity;
	private Map<String, String> data;
	
	private ProgressDialog dialog;
	
	public RegisterCarHTTPTask(Activity a, Map<String, String> data){
		
		this.parentActivity = a;
		
		this.data = data;
	}
	
	@Override
	protected void onPreExecute(){
		
		super.onPreExecute();
		
        dialog = new ProgressDialog(this.parentActivity);
        
        dialog.setMessage("Registering Car...");
        dialog.setCancelable(false);
        dialog.show();
	}
	
	@Override
	protected String doInBackground(String... uri) {
		HttpClient httpclient = new DefaultHttpClient();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		
		params.add(new BasicNameValuePair("fx", "registerCar"));
		
		params.add(new BasicNameValuePair("car_model", data.get("MODEL")));
		params.add(new BasicNameValuePair("car_reg", data.get("REG")));
		params.add(new BasicNameValuePair("car_capacity", data.get("CAP")));
		params.add(new BasicNameValuePair("parent_id", data.get("PARENT_ID")));
		
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
        
        if (dialog.isShowing()){
            dialog.dismiss();
        }
        
        try {
        	
        	Log.i("RESULT", result);
        	
			JSONObject json = new JSONObject(result);
			
			Boolean success = json.getBoolean("success");
			
			System.out.println(success);
			
			if(success){
				int parent_id = Integer.valueOf(this.data.get("PARENT_ID"));
				
				//Display a toast and move to the next Activity
				Toast.makeText(this.parentActivity, "Congratulations, Your car has been registered.", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(this.parentActivity, ChildActivity.class);
				
				intent.setType("text/plain");
				
				intent.putExtra("PARENT_ID", parent_id);
				
				this.parentActivity.startActivity(intent);
				
			} else {
				
				Toast.makeText(this.parentActivity, "Registration Failed to Complete.", Toast.LENGTH_LONG).show();
			
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
    }
}
