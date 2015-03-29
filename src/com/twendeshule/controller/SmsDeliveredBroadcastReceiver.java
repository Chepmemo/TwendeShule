package com.twendeshule.controller;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SmsDeliveredBroadcastReceiver extends BroadcastReceiver{
	private Activity parentActivity;
	
	public SmsDeliveredBroadcastReceiver(Activity a) {
		super();
		this.parentActivity = a;
	}
	@Override
    public void onReceive(Context context, Intent intent) {
        switch(getResultCode()) {
        case Activity.RESULT_OK:
            
        	Toast.makeText(this.parentActivity, "SMS HAS BEEN DELIVERED ", Toast.LENGTH_SHORT).show();
			
            break;
            
        case Activity.RESULT_CANCELED:
        	
            Toast.makeText(this.parentActivity, "SMS DELIVERY TO FAILED.", Toast.LENGTH_SHORT).show();
            
            break;
            
        }
    }
	
}
