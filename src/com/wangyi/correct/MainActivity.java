package com.wangyi.correct;

import java.io.File;
import java.util.ArrayList;

import com.wangyi.learninglove.InDialog;
import com.wangyi.learninglove.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
		private int year;
		private int month;
		private int day;
		private int hour;
		private int minute;
		Animation animation;
		Uri originalUri;
		Handler handler;
		
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correct_activity_main);
        File file = new File("/sdcard/learninglove/correct/");
        file.mkdirs();
        File fileElse = new File("/sdcard/learninglove/correctAnswer/");
        fileElse.mkdirs();
        
        Button back = (Button)findViewById(R.id.button1);
        Button read = (Button)findViewById(R.id.button3);
        Button write = (Button)findViewById(R.id.button2);
        Button add = (Button)findViewById(R.id.button4);
        
        animation = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
        
        back.setOnTouchListener(listener);
        read.setOnTouchListener(listener);
        write.setOnTouchListener(listener);
        add.setOnTouchListener(listener);
        
        handler = new Handler(){
        	public void dispatchMessage(Message msg){
        		switch(msg.what){
        		case 0:
        			MainActivity.this.finish();
        			overridePendingTransition(R.animator.slide_in_down, R.animator.slide_out_up);
        			break;
        		case 1:
        			Intent intent = new Intent(MainActivity.this,ReadActivity.class);
    				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				startActivity(intent);
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
        
        write.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
	                    
	            File file = new File("/sdcard/learninglove/correct/");
	            file.mkdirs();
	            Time t=new Time();
	            t.setToNow();
	            year=t.year;
	            month=t.month+1;
	            day=t.monthDay;
	            hour=t.hour;
	            minute=t.minute;
	            
	    		File[] files = file.listFiles();
	    		final String[] subjectName = new String[files.length];
	    		for (int i = 0 ;i < files.length;i++){ 
	    			subjectName[i] = files[i].getName();
	    		}
	            
	            Builder builder = new AlertDialog.Builder(MainActivity.this);
	            builder.setTitle("请选择错题科目");
	            builder.setItems(subjectName, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO Auto-generated method stub
						File fileName = new File("/sdcard/learninglove/correct/"+subjectName[which]+"/"+year+"年"+month+"月"+day+"日"+"_"+hour+minute+".jpg");
					    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					    Uri originalUri = Uri.fromFile(fileName);
					    intent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
					    startActivityForResult(intent, which);
					}
	            });
	           builder.create().show();
			}
        	
        });
        
        read.setOnClickListener(new OnClickListener(){

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
				final InDialog setSubject = new InDialog(MainActivity.this);
				setSubject.setTitle("请输入新建笔记科目名");
				setSubject.setButton("新建", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if(!(setSubject.editText.getText().toString().equals(""))){
							File file = new File("/sdcard/learninglove/correct/" + setSubject.editText.getText().toString()+"/");
							File fileAnswer = new File("/sdcard/learninglove/correctAnswer/" + setSubject.editText.getText().toString()+"/");
							if(file.exists()){
								new AlertDialog.Builder(MainActivity.this).setTitle("该科目已存在").create().show();
							}
							else{
								file.mkdirs();
								fileAnswer.mkdirs();
						}
						}
					}
					
				});
				setSubject.setButton2("取消", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
					
				});
				setSubject.show();
			}
        	
        });
    }
	
	protected void onActivityResult(final int requestCode, int resultCode, Intent intent){
		Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("是否继续收录该错题解析？");
		builder.setPositiveButton("收录", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				File file = new File("/sdcard/learninglove/correct/");
				File[] files = file.listFiles();
	    		String[] subjectName = new String[files.length];
	    		for (int i = 0 ;i < files.length;i++){ 
	    			subjectName[i] = files[i].getName();
	    		}
				File fileName = new File("/sdcard/learninglove/correctAnswer/"+subjectName[requestCode]+"/"+year+"年"+month+"月"+day+"日"+"_"+hour+minute+".jpg");
				Intent intentc = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				originalUri = Uri.fromFile(fileName);
		        intentc.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
		        startActivity(intentc);
				
			}
			
		});
		
		builder.setNegativeButton("日后再说",new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		if(resultCode == RESULT_OK){
			builder.create().show();
		}
	}
	
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
}
