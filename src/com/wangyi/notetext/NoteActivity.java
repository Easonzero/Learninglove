package com.wangyi.notetext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import com.wangyi.learninglove.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class NoteActivity extends Activity {
	private String basePath;
	private String name;
	private String action;
	private EditText noteText;
	private Button cancel;
	private Button save;
	private Button set;
	private EditText title;
	private File file;
	Animation lInAnimator;
    Animation rInAnimator;
    Animation lOutAnimator;
    Animation rOutAnimator;
    Animation animation;
    private OnTouchListener touchListener = new OnTouchListener(){

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				view.startAnimation(animation);
			}
			return false;
		}
		 
	 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notetext);
        
        noteText = (EditText) findViewById(R.id.noteText);
        cancel = (Button) findViewById(R.id.left);
        save = (Button) findViewById(R.id.right);
        title = (EditText) findViewById(R.id.title);
        set = (Button) findViewById(R.id.set);
        
        rInAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_in_right);
        rOutAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_out_right);
        lInAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_in_left);
        lOutAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_out_left);
        animation = (AnimationSet) AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
        
        basePath = this.getIntent().getStringExtra("basePath");
        name = this.getIntent().getStringExtra("name");
        action = this.getIntent().getStringExtra("action");
        
        if(action.equals("view")){
        	cancel.setText("退\n\n出");
        	save.setText("编\n\n辑");
        	try {
        		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(basePath+name)));
        		String line;
        		String text = "";
				while((line = br1.readLine())!=null){
					text = text + line + "\n";
				}
				noteText.setText(text);
					br1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	noteText.setFocusable(false);
        	title.setFocusable(false);
        	noteText.setFocusableInTouchMode(false);
        	title.setFocusableInTouchMode(false);
        	title.setText(name.substring(0, name.indexOf(".")));
        }
        
        set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(save.getVisibility() == View.GONE){
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    				cancel.startAnimation(lInAnimator);
    				save.startAnimation(rInAnimator);
    				cancel.setVisibility(View.VISIBLE);
    				save.setVisibility(View.VISIBLE);
    				imm.hideSoftInputFromWindow(noteText.getWindowToken(), 0);
    			}
    			else{
    				cancel.startAnimation(lOutAnimator);
    				save.startAnimation(rOutAnimator);
    				cancel.setVisibility(View.GONE);
    				save.setVisibility(View.GONE);
    			}
			}
        	
        });
        
        cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if(cancel.getText().toString().equals("取\n\n消")){
					cancel.setText("退\n\n出");
		        	save.setText("编\n\n辑");
		        	noteText.setFocusable(false);
		        	title.setFocusable(false);
		        	noteText.setFocusableInTouchMode(false);
		        	title.setFocusableInTouchMode(false);
		        	cancel.startAnimation(lOutAnimator);
    				save.startAnimation(rOutAnimator);
    				cancel.setVisibility(View.GONE);
    				save.setVisibility(View.GONE);
				}
				else{
					NoteActivity.this.finish();
				}
			}
        	
        });
        
        save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				noteText.getText().toString();
				if(save.getText().toString().equals("保\n\n存")){
				try {
					if(title.getText().toString().equals("")){
						file =new File(basePath+name);
					}
					else{
						file =new File(basePath+title.getText().toString()+".txt");
						File file0 =new File(basePath+name);
						file0.delete();
					}
					if(!file.exists()){
						file.createNewFile();
					}
					FileWriter fileWritter = new FileWriter(file.getPath());
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					String texts = noteText.getText().toString();
					String[] text = texts.split("\n");
					bufferWritter.write(text[0]);
					for(int i = 1;i < text.length;i++){
						bufferWritter.newLine();
						bufferWritter.write(text[i]);
					}
					bufferWritter.close();
					cancel.setText("退\n\n出");
		        	save.setText("编\n\n辑");
		        	noteText.setFocusable(false);
		        	title.setFocusable(false);
		        	noteText.setFocusableInTouchMode(false);
		        	title.setFocusableInTouchMode(false);
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				}
				else{
					cancel.setText("取\n\n消");
		        	save.setText("保\n\n存");
		        	noteText.setFocusableInTouchMode(true);
		        	title.setFocusableInTouchMode(true);
		        	title.setFocusable(true);
		        	noteText.setFocusable(true);
		        	cancel.startAnimation(lOutAnimator);
    				save.startAnimation(rOutAnimator);
    				cancel.setVisibility(View.GONE);
    				save.setVisibility(View.GONE);
				}
			}
        	
        });
        
        cancel.setOnTouchListener(touchListener);
        save.setOnTouchListener(touchListener);
    }
    
}
