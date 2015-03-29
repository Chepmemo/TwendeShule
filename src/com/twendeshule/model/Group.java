package com.twendeshule.model;

import java.util.Map;

public class Group {
	
	private int groupId;
	private String groupName;
	private int groupLeader;
	private String groupDescription;
	private String groupImageUrl;
	
	public Group(Map<String, String> data){
		
		this.groupId = Integer.valueOf(data.get("grp_id"));
		this.groupName = data.get("grp_name");
		this.groupLeader = Integer.valueOf(data.get("grp_leader"));
		this.groupDescription = data.get("grp_desc");
		this.groupImageUrl = data.get("grp_image");
		
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupLeader() {
		return groupLeader;
	}

	public void setGroupLeader(int groupLeader) {
		this.groupLeader = groupLeader;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getGroupImageUrl() {
		return groupImageUrl;
	}

	public void setGroupImageUrl(String groupImageUrl) {
		this.groupImageUrl = groupImageUrl;
	}
	
	@Override
	public String toString(){
		
		return this.groupName;
		
	}
	
}
