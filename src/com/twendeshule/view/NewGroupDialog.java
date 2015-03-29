package com.twendeshule.view;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.twendeshule.R;

public class NewGroupDialog extends DialogFragment{
	private EditText grpName;
	private EditText grpDesc;
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NewGroupdDialogListener {
        public void onDialogPositiveClick(Map<String, String> data);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NewGroupdDialogListener listener;
    
	 // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
	 @Override
	 public void onAttach(Activity activity) {
	     super.onAttach(activity);
	     // Verify that the host activity implements the callback interface
	        try {
	            //Instantiate the NoticeDialogListener so we can send events to the host
	            listener = (NewGroupdDialogListener) activity;
	        } catch (ClassCastException e) {
	            // The activity doesn't implement the interface, throw exception
	            throw new ClassCastException(activity.toString()
	                    + " must implement NoticeDialogListener");
	        }
	 }

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        View v = inflater.inflate(R.layout.new_group, null);
        
        grpName = (EditText)v.findViewById(R.id.group_name);
        grpDesc = (EditText)v.findViewById(R.id.group_desc);

        builder.setView(v)
        	// Add action buttons
        	.setMessage(R.string.dialog_new_grp)
               .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   Map<String, String> data = new HashMap<String, String>();
                	   
                	   data.put("GRP_NAME", grpName.getText().toString());
                	   data.put("GRP_DESC", grpDesc.getText().toString());
                	   
                	   // Send the positive button event back to the host activity
                       listener.onDialogPositiveClick(data);
                       
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   // Send the negative button event back to the host activity
                       listener.onDialogNegativeClick(NewGroupDialog.this);

                   }
               }); 
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
}
