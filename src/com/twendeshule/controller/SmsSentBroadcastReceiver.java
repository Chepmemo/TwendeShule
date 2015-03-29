package com.twendeshule.controller;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsSentBroadcastReceiver extends BroadcastReceiver{
	private Activity parentActivity;
	
	public SmsSentBroadcastReceiver(Activity a) {
		
		super();
		
		this.parentActivity = a;
	}
	@Override
    public void onReceive(Context context, Intent intent) {
        switch (getResultCode()) {
        case Activity.RESULT_OK:
        	
            Toast.makeText(this.parentActivity, "SMS SENT", Toast.LENGTH_LONG).show();
            break;
            
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
        	
            Toast.makeText(this.parentActivity, "GENERIC FAILURE", Toast.LENGTH_SHORT).show();
            break;
            
        case SmsManager.RESULT_ERROR_NO_SERVICE:
        	
            Toast.makeText(this.parentActivity, "NO SERVICE", Toast.LENGTH_SHORT).show();
            break;
            
        case SmsManager.RESULT_ERROR_NULL_PDU:
        	
            Toast.makeText(this.parentActivity, "NULL PDU", Toast.LENGTH_SHORT).show();
            break;
            
        case SmsManager.RESULT_ERROR_RADIO_OFF:
        	
            Toast.makeText(this.parentActivity, "RADIO OFF", Toast.LENGTH_SHORT).show();
            break;
            
        default:
        	
            break;
            
        }
        
    }
	
}
