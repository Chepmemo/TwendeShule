package com.twendeshule.view;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.twendeshule.TwendeShule;
import com.twendeshule.controller.GetGroups;
import com.twendeshule.model.Group;

public class GroupListActivity extends ListActivity {
	private ArrayList<Group> groups = null;
	SharedPreferences liveData;
	
	GroupAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);
		
		liveData = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
		int parentId = liveData.getInt("PARENT_ID", -1);

		new FetchGroupsHTTPTask(this, parentId).execute(TwendeShule.SERVER_URL);
	  }

	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
		
	    Group group = (Group) getListAdapter().getItem(position);
	    
	    int groupId = group.getGroupId();
	    SharedPreferences.Editor editor = liveData.edit();
	    
	    editor.putInt("CURRENT_GRP", groupId);
	    
	    editor.commit();
	    
	    String groupName = group.getGroupName();
	    String groupDescription = group.getGroupDescription();
	    String groupImageUrl = group.getGroupImageUrl();
	    
	    Intent intent = new Intent(this, GroupActivity.class);
	    
	    intent.setType("text/plain");
	    intent.putExtra("GROUP_ID", groupId);
	    intent.putExtra("GROUP_NAME", groupName);
	    intent.putExtra("GROUP_DESC", groupDescription);
	    intent.putExtra("GROUP_IMAGE", groupImageUrl);
	    
	    startActivity(intent);
	    
	  }
	
	
	public class FetchGroupsHTTPTask extends AsyncTask<String, String, String>{
		
		ListActivity parentActivity;
		int parentId;
		
		public FetchGroupsHTTPTask(ListActivity a, int parentId){
			
			this.parentActivity = a;
			this.parentId = parentId;
			
		}
		
		@Override
		protected void onPreExecute(){
			
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... uri) {
			
			groups = new GetGroups(uri[0], "all", this.parentId).get();
			
			return groups.toString();
			
		}
		
		
		@Override
		protected void onPostExecute(String result){
			
			super.onPostExecute(result);
	        
	        setAdapter();
	    
		}
	}
	private void setAdapter(){
		
		adapter = new GroupAdapter(this, groups);
	    
	    setListAdapter(adapter);
		
	}
}

