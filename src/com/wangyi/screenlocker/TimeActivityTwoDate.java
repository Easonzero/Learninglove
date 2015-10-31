package com.wangyi.screenlocker;

import java.util.ArrayList;

import com.wangyi.learninglove.R;
import com.wangyi.learninglove.TimeAdapter;
import com.wangyi.notetext.MainActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TimeActivityTwoDate extends Activity {
	public static ArrayList<String> list = new ArrayList<String>();
	public static TimeAdapter adapter = null;
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
	 Animation animation;
	 Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetwodate);
		loadArray(this,list);
		Button back = (Button) findViewById(R.id.button1);
		Button add = (Button) findViewById(R.id.button2);
		ListView lv = (ListView) findViewById(R.id.ListView);
		animation = (AnimationSet) AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
		
		back.setOnTouchListener(listener);
		add.setOnTouchListener(listener);
		
		adapter = new TimeAdapter(this,list);
		lv.setAdapter(adapter);
		
		handler = new Handler(){
        	public void dispatchMessage(Message msg){
        		switch(msg.what){
        		case 1:
        			TimeActivityTwoDate.this.finish();
        			overridePendingTransition(R.animator.slide_in_down, R.animator.slide_out_up);
        			break;
        		}
        	}
        };
        
        back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessageDelayed(1, 300);
			}
        	
        });
		
		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TimeActivityTwoDate.this,TimeActivityTwo.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				TimeActivityTwoDate.this.startActivity(intent);
				overridePendingTransition(R.animator.slide_in_down, R.animator.slide_out_up);
			}
			
		});
	}
	
	public boolean saveArray(ArrayList<String> sKey) {  
		SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);  
		SharedPreferences.Editor mEdit1= sp.edit();  
		mEdit1.putInt("Status_size",sKey.size());    
	
		for(int i=0;i<sKey.size();i++) {  
			mEdit1.remove("Status_" + i);  
		    mEdit1.putString("Status_" + i, sKey.get(i));    
		}  
		
		    return mEdit1.commit();       
	} 
	
	public void loadArray(Context mContext,ArrayList<String> sKey) {  
	    SharedPreferences mSharedPreference1=PreferenceManager.getDefaultSharedPreferences(mContext);
	    sKey.clear();
	    int size = mSharedPreference1.getInt("Status_size", 0);  

	    for(int i=0;i<size;i++) {
	        sKey.add(mSharedPreference1.getString("Status_" + i, null));  

	    }
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		list = adapter.getTimes();
		saveArray(list);
	}
	
	
}
