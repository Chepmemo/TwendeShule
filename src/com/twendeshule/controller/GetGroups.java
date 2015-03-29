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

import android.util.Log;

import com.twendeshule.model.Group;

public class GetGroups {
	
	private String uri;
	private String groupType;
	private int parentId;
	
	public GetGroups(String uri, String groupType, int parentId){
		this.uri = uri;
		this.groupType = groupType;
		this.parentId = parentId;
	}
	
	public ArrayList<Group> get(){
		
		ArrayList<Group> groupList = new ArrayList<Group>();
		
		HttpClient httpclient = new DefaultHttpClient();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		
		params.add(new BasicNameValuePair("fx", "getGroups"));
		params.add(new BasicNameValuePair("group_type", groupType));
		params.add(new BasicNameValuePair("parent_id", Integer.valueOf(this.parentId).toString()));
		
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
					Log.i("RESULT", result);
					
					JSONObject json = new JSONObject(result);
					
					Boolean success = json.getBoolean("success");
					
					if(success){
						//So far so good
						JSONArray groups = json.getJSONArray("data");
						
						for(int i = 0; i < groups.length(); i++){
							
							JSONObject grp = (JSONObject) groups.get(i);
							
							Log.i("GRP", grp.toString());
							
							Map<String, String> data = new HashMap<String, String>();
							
							data.put("grp_id", grp.getString("grp_id"));
							data.put("grp_name", grp.getString("grp_name"));
							data.put("grp_leader", grp.getString("grp_leader"));
							data.put("grp_desc", grp.getString("grp_desc"));
							data.put("grp_image", grp.getString("grp_image"));
							
							Group Group = new Group(data);
							
							groupList.add(Group);
							
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
		
		return groupList;
	}
}
