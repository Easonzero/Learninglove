package com.wangyi.screenlocker;

import com.wangyi.learninglove.R;
import com.wangyi.learninglove.DAR;
import com.wangyi.learninglove.OnLock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TimeActivityOne extends Activity {
	Handler handler;
	Animation animation;
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
		if(MainActivity.kind.equals("TLockScreen")){
			setContentView(R.layout.timeone);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
			TextView tell = (TextView) findViewById(R.id.textView1);
			tell.getPaint().setFakeBoldText(true);
			final EditText longtime = (EditText) findViewById(R.id.editText1);
			Button confirm = (Button) findViewById(R.id.button1);
			Button back = (Button) findViewById(R.id.button2);
			animation = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
			confirm.setOnTouchListener(listener);
			back.setOnTouchListener(listener);
			confirm.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(longtime.getText().toString().equals("")){
						Toast.makeText(TimeActivityOne.this,"«Î ‰»ÎÀ¯∆¡ ±º‰",5000).show();
					}
					else{
						int x = Integer.parseInt(longtime.getText().toString());
						TLockScreen.min = x;
						TLockScreen.timebase = x*60000+System.currentTimeMillis();
						Intent intentonlock = new Intent(TimeActivityOne.this,OnLock.class);
						TimeActivityOne.this.startActivity(intentonlock);
						TimeActivityOne.this.finish();
					}
				}
			});
		
			handler = new Handler(){
	        	public void dispatchMessage(Message msg){
	        		switch(msg.what){
	        		case 0:
	        			MainActivity.instance.finish();
						Intent intent = new Intent(TimeActivityOne.this,MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						TimeActivityOne.this.finish();
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
		}
		else{
			this.finish();
		}
	}
}