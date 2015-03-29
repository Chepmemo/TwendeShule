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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class NewGroupHTTPTask extends AsyncTask<String, String, String>{

	private Activity parentActivity;
	private Map<String, String> data;
	
	private ProgressDialog dialog;
	
	public NewGroupHTTPTask(Activity a, Map<String, String> data){
		
		this.parentActivity = a;
		
		this.data = data;
	}
	
	@Override
	protected void onPreExecute(){
		
		super.onPreExecute();
		
        dialog = new ProgressDialog(this.parentActivity);
        
        dialog.setMessage("Registering new group...");
        dialog.setCancelable(false);
        dialog.show();
	}
	
	@Override
	protected String doInBackground(String... uri) {
		HttpClient httpclient = new DefaultHttpClient();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		
		params.add(new BasicNameValuePair("fx", "addGroup"));
		
		params.add(new BasicNameValuePair("grp_name", data.get("GRP_NAME")));
		params.add(new BasicNameValuePair("grp_desc", data.get("GRP_DESC")));
		params.add(new BasicNameValuePair("grp_leader", data.get("GRP_LEADER")));
		
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
				
				//Display a toast and move to the next Activity
				Toast.makeText(this.parentActivity, "Group has been added.", Toast.LENGTH_SHORT).show();

				//Stay here for now
				
			} else {
				
				Toast.makeText(this.parentActivity, "Sorry, Group could not be added", Toast.LENGTH_LONG).show();
			
			}
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
    }
}
