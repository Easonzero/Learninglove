package com.wangyi.screenlocker;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.wangyi.learninglove.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.wangyi.screenlocker.*;

public class MainActivity extends Activity {
	private final String LOCK_SCREEN_ON_OFF = "lock_screen_on_off";
	private final String E_name = "e_name";
	private final String M1_name = "m1_name";
	private final String M2_name = "m2_name";
	private final String M_name = "m_name";
	private final String T_name = "t_name";
	public static String kind;
	public static boolean mIsLockScreenOn;
	private boolean E;
	private boolean M;
	private boolean M1;
	private boolean M2;
	private boolean T;
	private CheckBox mSetOnOff;
	private RadioGroup choose;
	private RadioButton eLS;
	private RadioButton mLS;
	private RadioButton tLS;
	private RadioGroup choose2;
	public RadioButton time1;
	public RadioButton time2;
	private RadioGroup choose3;
	public RadioButton math1;
	public RadioButton math2;
	private Button back;
	private Button set;
	private Button set2;
	Animation animation;
	Handler handler;
	private OnTouchListener listener = new OnTouchListener(){

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				view.startAnimation(animation);
			}
			return false;
		}
		 
	};
	public static MainActivity instance = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen_activity_main);
        instance = this;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);		
		mIsLockScreenOn = prefs.getBoolean(LOCK_SCREEN_ON_OFF, false);
		E = prefs.getBoolean(E_name, true);
		M = prefs.getBoolean(M_name, false);
		T = prefs.getBoolean(T_name, false);
		M1 = prefs.getBoolean(M1_name,true);
		M2 = prefs.getBoolean(M2_name, false);
		kind = prefs.getString("kind", "ELockScreen");
		mSetOnOff = (CheckBox) findViewById(R.id.set_onoff);
		choose = (RadioGroup) findViewById(R.id.radioGroup1);
		eLS = (RadioButton) findViewById(R.id.radio0);
		mLS = (RadioButton) findViewById(R.id.radio1);
		tLS = (RadioButton) findViewById(R.id.radio2);
		choose2 = (RadioGroup) findViewById(R.id.radioGroup2);
		time1 = (RadioButton) findViewById(R.id.radio3);
		time2 = (RadioButton) findViewById(R.id.radio4);
		back = (Button) findViewById(R.id.button3);
		set = (Button) findViewById(R.id.button2);
		choose3 = (RadioGroup) findViewById(R.id.radioGroup3);
		math1 = (RadioButton) findViewById(R.id.radio5);
		math2 = (RadioButton) findViewById(R.id.radio6);
		set2 = (Button) findViewById(R.id.button4);
		
		animation = (AnimationSet) AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
		set.setOnTouchListener(listener);
		set2.setOnTouchListener(listener);
		back.setOnTouchListener(listener);
		
		mSetOnOff.setChecked(mIsLockScreenOn);
		eLS.setChecked(E);
		mLS.setChecked(M);
		tLS.setChecked(T);
		time1.setChecked(false);
		time2.setChecked(true);
		
		if(mSetOnOff.isChecked()){
			mSetOnOff.setText("设置锁屏 [目前状态：开启]");
			choose.setEnabled(true);
			eLS.setEnabled(true);
			mLS.setEnabled(true);
			tLS.setEnabled(true);
			choose3.setEnabled(true);
			time1.setEnabled(true);
			time2.setEnabled(true);
			set.setEnabled(true);
			choose2.setEnabled(true);
			math1.setEnabled(true);
			math2.setEnabled(true);
			set2.setEnabled(true);
		}
		else{
			mSetOnOff.setText("设置锁屏 [目前状态：关闭]");
			choose.setEnabled(false);
			eLS.setEnabled(false);
			mLS.setEnabled(false);
			tLS.setEnabled(false);
			choose3.setEnabled(false);
			time1.setEnabled(false);
			time2.setEnabled(false);
			set.setEnabled(false);
			choose2.setEnabled(false);
			math1.setEnabled(false);
			math2.setEnabled(false);
			set2.setEnabled(false);
		}
		if(T){
			choose2.setVisibility(View.VISIBLE);
			time1.setVisibility(View.VISIBLE);
			time2.setVisibility(View.VISIBLE);
			set.setVisibility(View.VISIBLE);
		}
		else if(M){
			choose3.setVisibility(View.VISIBLE);
			math1.setVisibility(View.VISIBLE);
			math2.setVisibility(View.VISIBLE);
			math1.setChecked(M1);
			math2.setChecked(M2);
			if(M2){
				set2.setVisibility(View.VISIBLE);
			}
		}
		else{
			choose2.setVisibility(View.INVISIBLE);
			time1.setVisibility(View.INVISIBLE);
			time2.setVisibility(View.INVISIBLE);
			set.setVisibility(View.GONE);
		}
		
		mSetOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(buttonView.isChecked()){
					mSetOnOff.setText("设置锁屏 [目前状态：开启]");
					choose.setEnabled(true);
					eLS.setEnabled(true);
					mLS.setEnabled(true);
					tLS.setEnabled(true);
					choose3.setEnabled(true);
					time1.setEnabled(true);
					time2.setEnabled(true);
					set.setEnabled(true);
					choose2.setEnabled(true);
					math1.setEnabled(true);
					math2.setEnabled(true);
					set2.setEnabled(true);
					try {File file =new File("/sdcard/learninglove/kind.txt");
					if(!file.exists()){
						
							file.createNewFile();
						
					}
					FileWriter fileWritter = new FileWriter(file.getPath());
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write(kind);
					bufferWritter.close();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				}
				else{
					mSetOnOff.setText("设置锁屏 [目前状态：关闭]");
					choose.setEnabled(false);
					eLS.setEnabled(false);
					mLS.setEnabled(false);
					tLS.setEnabled(false);
					choose3.setEnabled(false);
					time1.setEnabled(false);
					time2.setEnabled(false);
					set.setEnabled(false);
					choose2.setEnabled(false);
					math1.setEnabled(false);
					math2.setEnabled(false);
					set2.setEnabled(false);
				}
			}
			
		});
		choose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				
				if(eLS.isChecked())
				{
					kind = "ELockScreen";
					try {File file =new File("/sdcard/learninglove/kind.txt");
					if(!file.exists()){
						
							file.createNewFile();
						
					}
					FileWriter fileWritter = new FileWriter(file.getPath());
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write(kind);
					bufferWritter.close();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
					choose3.setVisibility(View.GONE);
					math1.setVisibility(View.GONE);
					math2.setVisibility(View.GONE);
					set2.setVisibility(View.GONE);
					choose2.setVisibility(View.INVISIBLE);
					time1.setVisibility(View.INVISIBLE);
					time2.setVisibility(View.INVISIBLE);
					set.setVisibility(View.GONE);
					 
				}
				else if(mLS.isChecked())
				{
					if(math1.isChecked()){
						kind = "MLockScreenOne";
						try {File file =new File("/sdcard/learninglove/kind.txt");
						if(!file.exists()){
							
								file.createNewFile();
							
						}
						FileWriter fileWritter = new FileWriter(file.getPath());
						BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(kind);
						bufferWritter.close();
						} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
						}
						set2.setVisibility(View.GONE);
					}
					else if(math2.isChecked()){
						kind = "MLockScreenTwo";
						try {File file =new File("/sdcard/learninglove/kind.txt");
						if(!file.exists()){
							
								file.createNewFile();
							
						}
						FileWriter fileWritter = new FileWriter(file.getPath());
						BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
						bufferWritter.write(kind);
						bufferWritter.close();
						} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
						}
						set2.setVisibility(View.VISIBLE);
					}
					choose2.setVisibility(View.GONE);
					time1.setVisibility(View.GONE);
					time2.setVisibility(View.GONE);
					set.setVisibility(View.GONE);
					choose3.setVisibility(View.VISIBLE);
					math1.setVisibility(View.VISIBLE);
					math2.setVisibility(View.VISIBLE);
				}
				else
				{
					kind = "TLockScreen";
					try {File file =new File("/sdcard/learninglove/kind.txt");
					if(!file.exists()){
						
							file.createNewFile();
						
					}
					FileWriter fileWritter = new FileWriter(file.getPath());
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write(kind);
					bufferWritter.close();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
					choose3.setVisibility(View.GONE);
					math1.setVisibility(View.GONE);
					math2.setVisibility(View.GONE);
					set2.setVisibility(View.GONE);
					choose2.setVisibility(View.VISIBLE);
					time1.setVisibility(View.VISIBLE);
					time2.setVisibility(View.VISIBLE);
					set.setVisibility(View.VISIBLE);
					
				}
			}
		});
		choose2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if(arg1 == R.id.radio3){
					
					set.setVisibility(View.GONE);
					Intent timeone = new Intent(MainActivity.this,TimeActivityOne.class);
					timeone.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					MainActivity.this.startActivity(timeone);
				}
				else{
					
					set.setVisibility(View.VISIBLE);
				}
			}
			
		});
		
		choose3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if(arg1 == R.id.radio5){
					kind = "MLockScreenOne";
					try {File file =new File("/sdcard/learninglove/kind.txt");
					if(!file.exists()){
						
							file.createNewFile();
						
					}
					FileWriter fileWritter = new FileWriter(file.getPath());
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write(kind);
					bufferWritter.close();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
					set2.setVisibility(View.GONE);
				}
				else if(arg1 == R.id.radio6){
					kind = "MLockScreenTwo";
					try {File file =new File("/sdcard/learninglove/kind.txt");
					if(!file.exists()){
						
							file.createNewFile();
						
					}
					FileWriter fileWritter = new FileWriter(file.getPath());
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write(kind);
					bufferWritter.close();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
					set2.setVisibility(View.VISIBLE);
				}
				
			}
		});
		
		handler = new Handler(){
			public void dispatchMessage(Message msg){
        		switch(msg.what){
        		case 1:
        			MainActivity.this.finish();
        			break;
        		}
			}
		};
		
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessageDelayed(1, 300);
			}
        });
		
		set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,TimeActivityTwoDate.class);
				MainActivity.this.startActivity(intent);
				overridePendingTransition(R.animator.slide_in_down, R.animator.slide_out_up);
			}
			
		});
		set2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				File file = new File("/sdcard/learninglove/correct/");
				File[] files = file.listFiles();
	    		final String[] subjectName = new String[files.length];
	    		for (int i = 0 ;i < files.length;i++){ 
	    			subjectName[i] = files[i].getName();
	    		}
				Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("设置错题库的科目：");
				builder.setItems(subjectName, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO Auto-generated method stub
						try {
							File file =new File("/sdcard/learninglove/key.txt");
							if(!file.exists()){
								file.createNewFile();
							}
							FileWriter fileWritter = new FileWriter(file.getPath());
							BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
							bufferWritter.write(subjectName[which]);
							bufferWritter.close();
						
						} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				});
				builder.create().show();
			}
			
		});
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	
    	startService(new Intent(this, MyLockScreenService.class));
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	
    	super.onStop();
    	mIsLockScreenOn = mSetOnOff.isChecked();
    	String term = kind;
    	E = eLS.isChecked();
    	M = mLS.isChecked();
    	T = tLS.isChecked();
    	M1 = math1.isChecked();
    	M2 = math2.isChecked();
    	try {File file =new File("/sdcard/learninglove/kind.txt");
		if(!file.exists()){
			
				file.createNewFile();
			
		}
		FileWriter fileWritter = new FileWriter(file.getPath());
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(kind);
		bufferWritter.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    	if(mIsLockScreenOn){
    		EnableSystemKeyguard(false);
    	}
    	else {
    		stopService(new Intent(this, MyLockScreenService.class));
    		kind = "none";
    		try {File file =new File("/sdcard/learninglove/kind.txt");
			if(!file.exists()){
				
					file.createNewFile();
				
			}
			FileWriter fileWritter = new FileWriter(file.getPath());
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(kind);
			bufferWritter.close();
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
    		EnableSystemKeyguard(true);
    	}
    	
    	SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor.putBoolean(LOCK_SCREEN_ON_OFF, mIsLockScreenOn);
		editor.putBoolean(E_name, E);
		editor.putBoolean(M_name, M);
		editor.putBoolean(T_name, T);
		editor.putBoolean(M1_name, M1);
		editor.putBoolean(M2_name,M2);
		editor.putString("kind", term);
		editor.commit();
    	    	
    }
    
    void EnableSystemKeyguard(boolean bEnable){
    	KeyguardManager mKeyguardManager=null;
    	KeyguardLock mKeyguardLock=null; 
    	
    	mKeyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);  
    	mKeyguardLock = mKeyguardManager.newKeyguardLock(""); 
    	if(bEnable)
    		mKeyguardLock.reenableKeyguard();
    	else
    		mKeyguardLock.disableKeyguard();
    }
}
