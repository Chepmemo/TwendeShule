package com.twendeshule.view;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;

import com.twendeshule.R;
import com.twendeshule.TwendeShule;
import com.twendeshule.controller.NewGroupHTTPTask;

public class HomeActivity extends FragmentActivity implements NewGroupDialog.NewGroupdDialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
    public void newGroup(View v){
    	
    	DialogFragment dialog = new NewGroupDialog();

    	dialog.show(getSupportFragmentManager(), "new group");
    	
    }
    
    public void allGroups(View v){
    	
    	Intent intent = new Intent(HomeActivity.this, GroupListActivity.class);
    	
    	startActivity(intent);
    	
    }


	@Override
	public void onDialogPositiveClick(Map<String, String> data) {
		SharedPreferences liveData = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
		int parent_id = liveData.getInt("PARENT_ID", 0);
		
		data.put("GRP_LEADER", Integer.valueOf(parent_id).toString());
		
		new NewGroupHTTPTask(this, data).execute(TwendeShule.SERVER_URL);
		
	}


	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
	}
    
}
