package com.wangyi.learninglove;

import com.wangyi.learninglove.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class InDialog extends AlertDialog {
	public View view;  
	public EditText editText; 
	public InDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater factory = LayoutInflater.from(context);
		view = factory.inflate(R.layout.dialog, null);
		editText = (EditText)view.findViewById(R.id.editText);
		this.setView(view);
	}
	
}
