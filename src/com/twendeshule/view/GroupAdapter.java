package com.twendeshule.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twendeshule.R;
import com.twendeshule.model.Group;

public class GroupAdapter extends ArrayAdapter<Group>{
	
	private Context context;
	
	private ArrayList<Group> groups;
	private TextView groupName;
	private TextView groupDescription;
	private ImageView groupImage;
	
	public GroupAdapter(Context c, ArrayList<Group> groups) {
		
	    super(c, R.layout.group_list, groups);
	    
	    this.context = c;
	    this.groups = groups;
	 }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.group_list, parent, false);
		
		groupName = (TextView)rowView.findViewById(R.id.group_name);
		groupDescription = (TextView)rowView.findViewById(R.id.group_desc);
		groupImage = (ImageView)rowView.findViewById(R.id.group_image);
		
		Group group = groups.get(position);
		groupName.setText(group.getGroupName());
		groupDescription.setText(group.getGroupDescription());
		groupImage.setImageResource(R.drawable.pipeline);
		
		return rowView;
		
	}
}
