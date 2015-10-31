package com.wangyi.screenlocker;

import java.util.Calendar;

import com.wangyi.learninglove.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class TimeActivityTwo extends Activity {
	Handler handler;
	Animation animation;
	private int size;
	OnTouchListener listener = new OnTouchListener(){

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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetwo);
		final TimePicker from = (TimePicker) findViewById(R.id.timePicker1);
		final TimePicker to = (TimePicker) findViewById(R.id.timePicker2);
		to.setIs24HourView(true);
		from.setIs24HourView(true);          
		Button back = (Button) findViewById(R.id.button2);
		Button confirm = (Button) findViewById(R.id.button1);
		animation = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
		back.setOnTouchListener(listener);
		confirm.setOnTouchListener(listener);
		size = TimeActivityTwoDate.list.size();
		handler = new Handler(){
        	public void dispatchMessage(Message msg){
        		switch(msg.what){
        		case 0:
        			TimeActivityTwo.this.finish();
        			overridePendingTransition(R.animator.slide_in_down, R.animator.slide_out_up);
        			break;
        		}
        	}
        };
        
        back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessageDelayed(0, 300);
			}
        	
        });
		
		confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance(); 
				
				
				int m = from.getCurrentMinute();
				int h = from.getCurrentHour();
				int tm = to.getCurrentMinute();
				int th = to.getCurrentHour();
				
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.set(Calendar.HOUR_OF_DAY, h);
				calendar.set(Calendar.MINUTE, m);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				
				Intent intent = new Intent(TimeActivityTwo.this, TLockScreenControl.class);
				int delt = (tm > m)?(60*(th - h) + (tm - m)):(60*(th - 1 - h) + (60 + tm - m));
				intent.putExtra("min", delt);
	            PendingIntent sender = PendingIntent.getBroadcast(TimeActivityTwo.this,size, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
	            if(System.currentTimeMillis() <= calendar.getTimeInMillis() + 60*1000){
	            	am.setRepeating(AlarmManager.RTC,calendar.getTimeInMillis(),24*60*60*1000,sender);
	            }
	            else{
	            	am.setRepeating(AlarmManager.RTC,calendar.getTimeInMillis() + 24*60*60*1000,24*60*60*1000,sender);
	            }
	            size = size + 1;
	            TimeActivityTwoDate.list.add("Ê±¼äÉèÖÃ"+size+"  "+timeResult(h,m)+"¡ª¡ª"+timeResult(th,tm));
	            TimeActivityTwoDate.adapter.notifyDataSetChanged();
				handler.sendEmptyMessageDelayed(0, 300);
			}
			
		});
	}
	
	private String timeResult(int h,int m){
		String time;
		if(m < 10){
        	if(h<10){
        		time = "0"+h+":"+"0"+m;
        	}
        	else{
        		time = h+":"+"0"+m;
        	}
        }
        else{
        	if(h<10){
        		time = "0"+h+":"+m;
        	}
        	else{
        		time = h+":"+m;
        	}
        	
        }
		return time;
	}
}
