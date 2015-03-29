package com.twendeshule.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.twendeshule.R;
import com.twendeshule.TwendeShule;
import com.twendeshule.controller.EventHTTPTask;
import com.twendeshule.model.Reminder;

public class ScheduleActivity extends Activity {
	
	private Spinner selectReminder;
	private TextView selectedDays;
	private Spinner daySelect;
	private TimePicker timePicker;
	private ArrayList<Reminder> remindersList;
	private int num_days = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_activity);
		
		selectReminder = (Spinner)findViewById(R.id.reminder_select);
		daySelect = (Spinner)findViewById(R.id.day_select);
		timePicker = (TimePicker)findViewById(R.id.start_time);
		selectedDays = (TextView)findViewById(R.id.selected_days);
		
		remindersList = new ArrayList<Reminder>();
		
		populateReminders();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}
	
	public void addSelected(View v){
		
		String initial = selectedDays.getText().toString();
		
		String selected = daySelect.getSelectedItem().toString().substring(0, 2);
		
		if(!initial.contains(selected)){
			//Day not in selection, add it
			if(initial.equals("") || initial.equals(null)){
				
				initial += selected;
				
				
			} else {
				
				initial += ","+selected;
				
			}
			
			selectedDays.setText(initial);
			num_days++;
		}
	}
	
	public void done(View v){
		//Send data to the database
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();

		String time = (new StringBuilder().append(pad(hour)).append(":").append(pad(minute))).toString();
		 
		int reminder = ((Reminder)selectReminder.getSelectedItem()).getValue();
		
		Map<String, String> data = new HashMap<String, String>();
		
		data.put("start_time", time);
		
		data.put("reminder", pad(Integer.valueOf(reminder)).toString());
		data.put("num_days", pad(Integer.valueOf(num_days)).toString());
		
		data.put("selected_days", selectedDays.getText().toString());
		
		data.put("hour", Integer.valueOf(hour).toString());
		data.put("minute", Integer.valueOf(minute).toString());
		
		new EventHTTPTask(this, data).execute(TwendeShule.SERVER_URL);
		
	}
	
	private void populateReminders(){
		
		remindersList.add(new Reminder("0 Min", 0));
		remindersList.add(new Reminder("1 Min Before", 1));
		remindersList.add(new Reminder("5 Min Before", 5));
		remindersList.add(new Reminder("10 Min Before", 10));
		remindersList.add(new Reminder("15 Min Before", 15));
		remindersList.add(new Reminder("20 Min Before", 20));
		remindersList.add(new Reminder("25 Min Before", 25));
		remindersList.add(new Reminder("30 Min Before", 30));
		
		List<String> lables = new ArrayList<String>();
		 
	    for (int i = 0; i < remindersList.size(); i++) {
	        lables.add(remindersList.get(i).getText());
	    }
		
	    ArrayAdapter<Reminder> spinnerAdapter = new ArrayAdapter<Reminder>(this, android.R.layout.simple_spinner_dropdown_item, remindersList);
	    
	    // attaching data adapter to spinner
	    selectReminder.setAdapter(spinnerAdapter);
	    
	}
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}

}
