package com.wangyi.screenlocker;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.wangyi.learninglove.R;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.DigitalClock;
import android.widget.TextView;
import android.widget.Toast;

public class TLockScreen extends Activity {
	public static long timebase;
	public static int min;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		setContentView(R.layout.time);
		
		final int num = BaseDate.gogo.length;
		Random random = new Random();
		int x=random.nextInt(num);
		
		TextView alltime = (TextView)findViewById(R.id.textView1);
		final TextView gogo = (TextView)findViewById(R.id.textView2);
		alltime.setText("预设锁屏时间为"+min+"分钟");
		gogo.setText(BaseDate.gogo[x]);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_BLUR_BEHIND|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(timebase < System.currentTimeMillis()){
					TLockScreen.this.finish();
					overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
				}
			}
			
		};
		Timer timer = new Timer();
		timer.schedule(task, 0, 1000);
		
		gogo.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				Random random = new Random();
				int x=random.nextInt(num);
				gogo.setText(BaseDate.gogo[x]);
				return false;
			}
			
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) {
	        return false;
	    }
		else if(keyCode == KeyEvent.KEYCODE_MENU) {
			return false;
	    }
		return super.onKeyDown(keyCode, event);
	}
	
	
}
