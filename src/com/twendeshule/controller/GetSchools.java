package com.twendeshule.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.twendeshule.model.School;

public class GetSchools {
	
	private String uri;
	
	public GetSchools(String uri){
		this.uri = uri;
	}
	
	public ArrayList<School> get(){
		
		ArrayList<School> schoolsList = new ArrayList<School>();
		
		HttpClient httpclient = new DefaultHttpClient();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		
		params.add(new BasicNameValuePair("fx", "getSchools"));
		
		HttpResponse response;
		
		String result = "NOTHING HERE";
		
		try {
			
			HttpPost post = new HttpPost(this.uri);
			
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			response = httpclient.execute(post);
			
			StatusLine statusLine = response.getStatusLine();
			
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				
				response.getEntity().writeTo(out);
				
				out.close();
				
				result = out.toString();
				
				try {
					
					JSONObject json = new JSONObject(result);
					
					Boolean success = json.getBoolean("success");
					
					if(success){
						//So far so good
						JSONArray schools = json.getJSONArray("data");
						
						for(int i = 0; i < schools.length(); i++){
							
							JSONObject sch = (JSONObject) schools.get(i);
							
							Map<String, String> data = new HashMap<String, String>();
							
							data.put("school_id", sch.getString("school_id"));
							data.put("school_name", sch.getString("school_name"));
							data.put("school_address", sch.getString("school_address"));
							data.put("school_location", sch.getString("school_location"));
							data.put("school_classes", sch.getString("school_classes"));
							
							School school = new School(data);
							
							schoolsList.add(school);
							
						}
						
					}
					
				} catch (JSONException e){
				
					e.printStackTrace();
					
				}
				
			} else {
				
				response.getEntity().getContent().close();
				
				throw new IOException(statusLine.getReasonPhrase());
				
			}
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return schoolsList;
	}
}
