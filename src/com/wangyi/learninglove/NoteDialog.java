package com.wangyi.learninglove;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteDialog extends AlertDialog {
	public View view;  
	public Button camera;
	public Button pencil;
	public NoteDialog(Context context) {
		super(context);
		LayoutInflater factory = LayoutInflater.from(context);
		view = factory.inflate(R.layout.notedialog, null);
		camera = (Button)view.findViewById(R.id.camera);
		pencil = (Button)view.findViewById(R.id.pencil);
		this.setView(view);
		// TODO Auto-generated constructor stub
	}
}
