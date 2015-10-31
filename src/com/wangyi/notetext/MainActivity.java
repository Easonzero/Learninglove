package com.wangyi.notetext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wangyi.learninglove.InDialog;
import com.wangyi.learninglove.NoteDialog;
import com.wangyi.learninglove.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends Activity {
	Animation animation;
	Handler handler;
	NoteDialog notedialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notetext_activity_main);
        
        File file = new File("/sdcard/learninglove/Note/");
        file.mkdirs();
        Button back = (Button)findViewById(R.id.button1);
        Button read = (Button)findViewById(R.id.button3);
        Button write = (Button)findViewById(R.id.button2);
        Button add = (Button)findViewById(R.id.button4);
        
        animation = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
        
        back.setOnTouchListener(listener);
        add.setOnTouchListener(listener);
        read.setOnTouchListener(listener);
        write.setOnTouchListener(listener);
        
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
				File file = new File("/sdcard/learninglove/Note/");
				
				Time t=new Time();
	            t.setToNow();
	            final int year=t.year;
	            final int month=t.month+1;
	            final int day=t.monthDay;
	            final int hour=t.hour;
	            final int minute=t.minute;
	            
	            File[] files = file.listFiles();
	    		final String[] subjectName = new String[files.length];
	    		for (int i = 0 ;i < files.length;i++){ 
	    			subjectName[i] = files[i].getName();
	    		}
	            
	            Builder builder = new AlertDialog.Builder(MainActivity.this);
	            builder.setTitle("请选择笔记科目");
	            builder.setItems(subjectName, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, final int which) {
						notedialog = new NoteDialog(MainActivity.this);
						notedialog.setTitle("如何记笔记呢？");
						notedialog.camera.setOnTouchListener(listener);
						notedialog.pencil.setOnTouchListener(listener);
						notedialog.camera.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								File fileName = new File("/sdcard/learninglove/Note/"+subjectName[which]+"/"+year+"年"+month+"月"+day+"日"+"_"+hour+minute+".jpg");
							    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							    Uri originalUri = Uri.fromFile(fileName);
							    intent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
							    startActivityForResult(intent, which);
							    notedialog.cancel();
							}
							
						});
						notedialog.pencil.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(MainActivity.this,NoteActivity.class);
								intent.putExtra("basePath", "/sdcard/learninglove/Note/"+subjectName[which]+"/");
								intent.putExtra("name", year+"年"+month+"月"+day+"日"+"_"+hour+minute+".txt");
								intent.putExtra("action","edit");
								startActivity(intent);
								notedialog.cancel();
							}
							
						});
						notedialog.show();
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
							File file = new File("/sdcard/learninglove/Note/" + setSubject.editText.getText().toString()+"/");
							if(file.exists()){
								new AlertDialog.Builder(MainActivity.this).setTitle("该科目已存在").create().show();
							}
							else{
								file.mkdirs();
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

