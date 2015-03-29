package com.twendeshule.model;

import java.util.Map;

public class School {
	private int schoolID;
	private String schoolName;
	private String schoolAddress;
	private String schoolLocation;
	private int numClasses;
	
	public School(Map<String, String> data){
		
		this.schoolID = Integer.valueOf(data.get("school_id"));
		this.schoolName = data.get("school_name");
		this.schoolAddress = data.get("school_address");
		this.schoolLocation = data.get("school_location");
		this.numClasses = Integer.valueOf(data.get("school_classes"));
		
	}
	
	public int getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(int schoolId) {
		this.schoolID = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
	public String getSchoolLocation() {
		return schoolLocation;
	}
	public void setSchoolLocation(String schoolLocation) {
		this.schoolLocation = schoolLocation;
	}
	public int getNumClasses() {
		return numClasses;
	}
	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}
	
	@Override
    public String toString () {
        return getSchoolName();
    }

	
}
