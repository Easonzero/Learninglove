package com.wangyi.learninglove;

import java.io.File;

import com.wangyi.correct.CropActivity;
import com.wangyi.correct.ReadActivity;
import com.wangyi.learninglove.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity{
	AnimatorSet animationSet;
	Button back;
    Button screenLock;
    Button noteText;
    Button correct;
    File file;
    Handler handler;
    Animation animation;
    RelativeLayout relative;
    Animation lInAnimator;
    Animation rInAnimator;
    Animation lOutAnimator;
    Animation rOutAnimator;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main_activity);
	        
	        back = (Button)findViewById(R.id.button1);
	        screenLock = (Button)findViewById(R.id.buttons);
	        noteText = (Button)findViewById(R.id.buttonb);
	        correct = (Button)findViewById(R.id.buttonc);
	        relative = (RelativeLayout)findViewById(R.id.relative);
	        
	        rInAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_in_right);
	        rOutAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_out_right);
	        lInAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_in_left);
	        lOutAnimator = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_out_left);
	        animation = (AnimationSet) AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
	        
	        file = new File("/sdcard/learninglove/");
	        file.mkdirs();
	                
	        screenLock.setVisibility(View.INVISIBLE);
	        noteText.setVisibility(View.INVISIBLE);
	        correct.setVisibility(View.INVISIBLE);
	        
	        handler = new Handler(){
	        	@Override
	        	public void dispatchMessage(Message msg){
	        		switch(msg.what){
	        		case 1:
	        			MainActivity.this.finish();
	        			break;
	        		case 2:
	        			noteText.startAnimation(lInAnimator);
						noteText.setVisibility(View.VISIBLE);
						break;
	        		case 3:
	        			correct.startAnimation(lInAnimator);
						correct.setVisibility(View.VISIBLE);
						break;
	        		case 4:
	        			screenLock.startAnimation(rInAnimator);
						screenLock.setVisibility(View.VISIBLE);
						break;
	        		case 5:
	        			correct.startAnimation(lOutAnimator);
	        			correct.setVisibility(View.INVISIBLE);
	        			break;
	        		case 6:
	        			screenLock.startAnimation(rOutAnimator);
	        			screenLock.setVisibility(View.INVISIBLE);
	        			break;
	        		case 7:
	        			noteText.startAnimation(lOutAnimator);
	        			noteText.setVisibility(View.INVISIBLE);
	        			break;
	        		}	
	        	}
	        };
	        
	        relative.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(screenLock.getVisibility() == View.INVISIBLE){
						handler.sendEmptyMessageDelayed(2, 0);
						handler.sendEmptyMessageDelayed(3, 550);
						handler.sendEmptyMessageDelayed(4, 280);
						noteText.startAnimation(lInAnimator);
						noteText.setVisibility(View.VISIBLE);
					}
					else{
						handler.sendEmptyMessageDelayed(5, 0);
						handler.sendEmptyMessageDelayed(6, 280);
						handler.sendEmptyMessageDelayed(7, 550);
					}
				}
	        	
	        });
	        
	        screenLock.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,com.wangyi.screenlocker.MainActivity.class);
					startActivity(intent);  
					overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
				}
	        	
	        });
	        
	        correct.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,com.wangyi.correct.MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
				}
	        	
	        });
	        
	        noteText.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,com.wangyi.notetext.MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
				}
	        });
	        
	        back.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					handler.sendEmptyMessageDelayed(1, 300);
				}
	        });
	        
	        back.setOnTouchListener(listener);
	        screenLock.setOnTouchListener(listener);
	        correct.setOnTouchListener(listener);
	        noteText.setOnTouchListener(listener);
	    }
	 
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
}
