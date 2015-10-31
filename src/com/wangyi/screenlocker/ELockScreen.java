package com.wangyi.screenlocker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import com.wangyi.learninglove.R;

import android.widget.AbsoluteLayout.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.wangyi.learninglove.ScrollingTextView;

public class ELockScreen extends Activity {
	TextToSpeech tts;
	private static int count;
	Animation animation;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.english);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		animation = (AnimationSet) AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
		BaseDate.read();
		Random random = new Random();
		int num = BaseDate.eWord.length;
		final int x;
		
		final Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("是否标记该单词为已熟记？");
		builder.setMessage("标记已熟记的单词将不会再出现。");
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		count = prefs.getInt("count", 0);
		Button speak = (Button)findViewById(R.id.button2);
		Button confirm = (Button)findViewById(R.id.button1);
		final ScrollingTextView mean = (ScrollingTextView)findViewById(R.id.textView1);
		final EditText word = (EditText)findViewById(R.id.editText1);
		
		speak.setOnTouchListener(listener);
		confirm.setOnTouchListener(listener);
		
		x=random.nextInt(num);
		mean.setText(BaseDate.eMean[x]);
		builder.setPositiveButton("痛快的跟它拜拜！",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if(count < BaseDate.baseWord.length){
					String term;
					term = BaseDate.eWord[x];
					BaseDate.eWord[x] = BaseDate.baseWord[count];
					BaseDate.baseWord[count] = term;
					term = BaseDate.eMean[x];
					BaseDate.eMean[x] = BaseDate.baseMean[count];
					BaseDate.baseMean[count] = term;
					mean.setText(BaseDate.eMean[x]);
					BaseDate.write();
					count++;
				}
				else{
					Toast.makeText(ELockScreen.this, "恭喜您，词库已被您一扫而空", 5000).show();
					count = 0;
				}
			}
		});
		
		builder.setNegativeButton("算了，我再熟练下", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(ELockScreen.this, "革命尚未成功，同志还需努力", 5000).show();
			}
			
		});
		tts = new TextToSpeech(this, new OnInitListener()
		{
			@Override
			public void onInit(int arg0) {
				// TODO Auto-generated method stub
				if(arg0 == TextToSpeech.SUCCESS){
					int result = tts.setLanguage(Locale.US);
				}
			}
			
		});
		
		speak.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tts.speak(BaseDate.eWord[x], TextToSpeech.QUEUE_ADD, null);
			}
			
		});
		
		
		
		mean.setOnTouchListener(new OnTouchListener(){
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				TextView key = (TextView)findViewById(R.id.textView2);
				int l = key.getLeft();
				int r = key.getRight();
				int t = key.getTop();
				int b = key.getBottom();
				int new_y;
				int save_y = 0;
				key.setText(BaseDate.eWord[x]);
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:{
						mean.setVisibility(View.INVISIBLE);
						key.setVisibility(View.VISIBLE);
						l = key.getLeft();
						r = key.getRight();
						t = key.getTop();
						b = key.getBottom();
						break;
					}
					case MotionEvent.ACTION_MOVE:{
						new_y = (int) event.getY();
						if(new_y<-5&&new_y>(-t)){
							int new_t = t+new_y;
							int new_b = b+new_y;
							key.layout(l,new_t,r,new_b);
							if(new_y < (-t/3)){
								builder.create().show();
							}
						}
						else if(new_y>-5&&new_y<0){
							int new_t = t+save_y;
							int new_b = b+save_y;
							key.layout(l,new_t,r,new_b);
						}
						save_y = new_y;
						break;
					}
					case MotionEvent.ACTION_UP:{
						mean.setVisibility(View.VISIBLE);
						key.setVisibility(View.INVISIBLE);
						key.layout(l,t,r,b);
						break;
					}
					default:
						break;
				}
				
				return true;
			}
			
		});
		
		confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 //TODO Auto-generated method stub
				String password = word.getText().toString();
				if(password.equals(BaseDate.eWord[x]))
				{
					
					ELockScreen.this.finish();
					overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
				}
				else
				{
					Toast.makeText(ELockScreen.this, "拼写错误", 5000).show();
					word.setText(null);
				}
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(tts != null){
			tts.shutdown();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor.putInt("count", count);
		editor.commit();
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
		


