package com.wangyi.correct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.edmodo.cropper.CropImageView;
import com.wangyi.learninglove.R;

public class CropActivity extends Activity {
	
	CropImageView cropImageView = null;
	ImageView croppedView = null;
	Bitmap croppedImage = null;
	Button confirm = null;
	Button cancel = null;
	Bitmap mBitmap = null;
	File file = null;
	Handler handler;
	Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cropper);
        
        cropImageView = (CropImageView) findViewById(R.id.imageView);
        croppedView =(ImageView) findViewById(R.id.imageView1);
        confirm = (Button) findViewById(R.id.button1);
        cancel = (Button) findViewById(R.id.button2);

        animation = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
        file = new File(getIntent().getStringExtra("filePath"));
        try {
        	mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(file));
			
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        cropImageView.setImageBitmap(mBitmap);
        
        cancel.setOnTouchListener(listener);
        confirm.setOnTouchListener(listener);
        
        handler = new Handler(){
        	public void dispatchMessage(Message msg){
        		switch(msg.what){
        		case 0:
        			CropActivity.this.finish();
        			break;
        		}
        	}
        };
        
        cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(cropImageView.getVisibility() == View.VISIBLE){
					handler.sendEmptyMessageDelayed(0, 300);
				}
				else{
					croppedView.setImageBitmap(null);
					croppedView.setVisibility(View.GONE);
					cropImageView.setVisibility(View.VISIBLE);
				}
			}
        	
        });
        
        confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(cropImageView.getVisibility() == View.VISIBLE){
					croppedImage = cropImageView.getCroppedImage();
					croppedView.setVisibility(View.VISIBLE);
					cropImageView.setVisibility(View.GONE);
					croppedView.setImageBitmap(croppedImage);
				}
				else{
					String croppedPath = file.getPath();
					file.delete();
					saveMyBitmap(croppedPath,croppedImage);
					CropActivity.this.finish();
				}
			}
        	
        });
    }
    
    public void saveMyBitmap(String bitPath,Bitmap mBitmap){
    	  File f = new File(bitPath);
    	  try {
    	   f.createNewFile();
    	  } catch (IOException e) {
    	   // TODO Auto-generated catch block
    	  }
    	  FileOutputStream fOut = null;
    	  try {
    	   fOut = new FileOutputStream(f);
    	  } catch (FileNotFoundException e) {
    	   e.printStackTrace();
    	  }
    	  mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
    	  mBitmap.recycle();
    	  mBitmap = null;
    	  try {
    	   fOut.flush();
    	  } catch (IOException e) {
    	   e.printStackTrace();
    	  }
    	  try {
    	   fOut.close();
    	  } catch (IOException e) {
    	   e.printStackTrace();
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
