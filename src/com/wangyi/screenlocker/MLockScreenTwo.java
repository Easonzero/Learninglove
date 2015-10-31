package com.wangyi.screenlocker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.wangyi.correct.MainActivity;
import com.wangyi.learninglove.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MLockScreenTwo extends Activity {
	public static String dName = null;
	Bitmap bitmap = null;
	Bitmap bitmap2 = null;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
		setContentView(R.layout.mathtwo);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		Button onLock = (Button) findViewById(R.id.button1);
		final ImageView question = (ImageView) findViewById(R.id.imageView1);
		final ImageView answer = (ImageView) findViewById(R.id.imageView2);
		final ImageView large = (ImageView) findViewById(R.id.imageView3);
		large.setVisibility(View.GONE);
		String answerPath = null;
		
		try{BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/learninglove/key.txt")));
		dName = br.readLine();
		File file = new File("/sdcard/learninglove/correct/"+dName+"/"); 
		File[] files = file.listFiles();
		String[] paths = new String[files.length];
		String[] names = new String[files.length];
		for (int i = 0 ;i < files.length;i++){ 
			paths[i] = files[i].getPath();
			names[i] = files[i].getName();
		}
		Random random = new Random();
		int num = paths.length;
		int x=random.nextInt(num);
			answerPath = "/sdcard/learninglove/correctAnswer/"+dName+"/"+names[x].substring(0,names[x].indexOf("g")+1);
				bitmap = getLoacalBitmap("/sdcard/learninglove/correct/"+dName+"/"+names[x]);
				question.setImageBitmap(bitmap);
				bitmap2 = getLoacalBitmap(answerPath);
				answer.setImageBitmap(bitmap2);
				
				question.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						large.setVisibility(View.VISIBLE);
						large.setImageBitmap(bitmap);
					}
					
				});
				
				answer.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						large.setVisibility(View.VISIBLE);
						large.setImageBitmap(bitmap2);
					}
					
				});
				
				large.setOnClickListener(new OnClickListener(){

					@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					large.setVisibility(View.GONE);
					large.setImageBitmap(null);
				}
						
			});
				}
		catch(Exception e){
			Builder builder = new AlertDialog.Builder(MLockScreenTwo.this);
            if(!(new File("/sdcard/learninglove/correct/"+dName+"/").exists())){
            	builder.setTitle(dName + "该科目还未建立错题本！");
            }
            else{
            	builder.setTitle(dName + "错题库空空如也，少年快去添点");
            }
            builder.create().show();
		}
		
		onLock.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if((!(bitmap == null))||(!(bitmap == null))){
					if(!(bitmap2 == null)){
						bitmap.recycle();
						bitmap2.recycle();
						bitmap = null;
						bitmap2 = null;
					}
					else{
						bitmap.recycle();
						bitmap = null;
					}
				}
				question.setImageBitmap(null);
				answer.setImageBitmap(null);
				MLockScreenTwo.this.finish();
				overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
			}
			
		});
	}
	
	public static Bitmap getLoacalBitmap(String url) {

	     try {

	          FileInputStream fis = new FileInputStream(url);

	          return BitmapFactory.decodeStream(fis);

	     } catch (FileNotFoundException e) {

	          e.printStackTrace();

	          return null;

	     }

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if((!(bitmap == null))||(!(bitmap2 == null))){
			if(!(bitmap2 == null)){
				bitmap.recycle();
				bitmap2.recycle();
				bitmap = null;
				bitmap2 = null;
			}
			else{
				bitmap.recycle();
				bitmap = null;
			}
		}
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return true;
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
