package com.twendeshule.view;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.twendeshule.R;
import com.twendeshule.TwendeShule;

@SuppressWarnings("unused")
public class GroupActivity extends Activity {
	
	private int groupId;
	private String groupName;
	private String groupDescription;
	private String groupImageUrl;
	
	private TextView groupNameView;
	private TextView groupDescView;
	private EditText invitePhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_activity);
		
		Bundle bundle = getIntent().getExtras();
		this.groupId = bundle.getInt("GROUP_ID");
		this.groupName = bundle.getString("GROUP_NAME");
		this.groupDescription = bundle.getString("GROUP_DESC");
		this.groupImageUrl = bundle.getString("GROUP_IMAGE");
		
		invitePhone = (EditText)findViewById(R.id.invite_phone);
		groupNameView = (TextView)findViewById(R.id.group_name);
		groupDescView = (TextView)findViewById(R.id.group_desc);
		
		
		groupNameView.setText(groupName);
		groupDescView.setText(groupDescription);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group, menu);
		return true;
	}
	
	public void sendInvite(View v){
		String phoneNumber = invitePhone.getText().toString();
		
		sendSMS(phoneNumber);
	}
	
	public void createSchedule(View v){
		
		Intent intent = new Intent(this, ScheduleActivity.class);
		
		startActivity(intent);
		
	}
	
	private void sendSMS(String recipient){
		
		PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
		
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
		
        String smsText = "Download Twendeshule here "+TwendeShule.GOOGLE_PLAY_LINK;
        
		try {
			
			SmsManager sms = SmsManager.getDefault(); 
			
			sms.sendTextMessage(recipient, null, smsText, sentIntent, deliveredIntent);
			
		} catch(Exception e){
			
			Toast.makeText(this, "SMS COULD NOT BE SEND. PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show();
            
			e.printStackTrace();
		}
		
	}

}
