package com.twendeshule.model;

public class Reminder {
	private String text;
	private int value;
	
	public Reminder(String t, int v){
		
		this.text = t;
		this.value = v;
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		return getText();
	}
}
