package com.twendeshule.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;

import com.twendeshule.TwendeShule;

public class ScheduleHelper{
	
	private Activity parentActivity;
	
	private Map<String, String> eventData;
	private String rRule= "";
	
	public ScheduleHelper(Activity a, Map<String, String> data){
		
		this.parentActivity = a;
		this.eventData = data;
		
		this.rRule = data.get("selected_days");
		
	}
	
	public long schedule(){
		
		if(createCalendar()){
			
			return createEvent();
			
		}
		
		return -1;
	}
	
	@SuppressWarnings("unused")
	private boolean createCalendar(){
		
		if(getCalendarId() == -1){
			
			ContentValues values = new ContentValues();
			
			values.put(Calendars.ACCOUNT_NAME, TwendeShule.MY_ACCOUNT_NAME);
			
			values.put(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
			
			values.put(Calendars.NAME, "TWENDE SHULE");
			
			values.put(Calendars.CALENDAR_DISPLAY_NAME, "TWENDE SHULE");
			
			values.put(Calendars.CALENDAR_COLOR, 0xff00ffff);
			
			values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
			
			values.put(Calendars.OWNER_ACCOUNT, "twende.shule@gmail.com");
			
			values.put(Calendars.CALENDAR_TIME_ZONE, "Africa/Nairobi");
			
			Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon(); 
			
			builder.appendQueryParameter(Calendars.ACCOUNT_NAME, "twende.shule@gmail.com");
			
			builder.appendQueryParameter(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
			
			builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");
			
			Uri uri = parentActivity.getContentResolver().insert(builder.build(), values);
			
			return true;
		} else {
			//Calendar already exists, return immediately
			return true;
		
		}
		
	}
	
	private long getCalendarId() {
		
	   String[] projection = new String[]{Calendars._ID};
	   
	   String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" 
               + Calendars.ACCOUNT_TYPE + " = ?))";
	   
	   String[] selArgs = new String[]{TwendeShule.MY_ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL}; 
	   
	   Cursor cursor = parentActivity.getContentResolver().
	                   query(
		                  Calendars.CONTENT_URI, 
		                  projection, 
		                  selection, 
		                  selArgs, 
		                  null);
	   
	   if (cursor.moveToFirst()) { 
	      return cursor.getLong(0); 
	   } 
	   
	   return -1; 
	   
	}
	
	public long createEvent(){
		
		long calId = getCalendarId();
		
		if (calId == -1) {
		   // no calendar account; react meaningfully
		   return -1;
		}
		
		
		Calendar cal = new GregorianCalendar();
		
		cal.setTimeZone(TimeZone.getTimeZone("EAT"));
		
		cal.set(Calendar.HOUR, Integer.valueOf(eventData.get("hour")));
		cal.set(Calendar.MINUTE, Integer.valueOf(eventData.get("minute")));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		long start = cal.getTimeInMillis();
		
		ContentValues values = new ContentValues();
		
		values.put(Events.DTSTART, start);
		values.put(Events.DTEND, start);
		
		values.put(Events.RRULE, "FREQ=DAILY;COUNT=365;BYDAY=" +rRule+ ";WKST=MO");
		
		values.put(Events.TITLE, "Twende Shule!");
		values.put(Events.EVENT_LOCATION, "Kileleshwa");
		values.put(Events.CALENDAR_ID, calId);
		values.put(Events.EVENT_TIMEZONE, "GMT+03:00");
		
		values.put(Events.DESCRIPTION, "It's your time to take the kids to school on Twende Shule. Safe Journey");
		
		// reasonable defaults exist:
		values.put(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
		
		values.put(Events.SELF_ATTENDEE_STATUS, Events.STATUS_CONFIRMED);
		
		values.put(Events.ALL_DAY, 0);
		
		values.put(Events.ORGANIZER, "twende.shule@gmail.com");
		
		values.put(Events.GUESTS_CAN_INVITE_OTHERS, 1);
		
		values.put(Events.GUESTS_CAN_MODIFY, 1);
		
		values.put(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
		
		Uri uri = parentActivity.getContentResolver().insert(Events.CONTENT_URI, values);
		
		long eventId = Long.valueOf(uri.getLastPathSegment());
		
		setReminder(eventId);
		
		return eventId;
	}
	
	private void setReminder(Long eventId){
		
		ContentValues values = new ContentValues();

		values.put(Reminders.EVENT_ID, eventId);
		values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
		values.put(Reminders.MINUTES, 30);
		
		parentActivity.getContentResolver().insert(Reminders.CONTENT_URI, values); 
		
	}
}
