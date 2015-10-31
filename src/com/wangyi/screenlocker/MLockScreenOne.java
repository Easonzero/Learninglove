package com.wangyi.screenlocker;

import java.util.Random;

import com.wangyi.learninglove.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MLockScreenOne extends Activity {
	
	private int x;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mathone);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
		Random random = new Random();
		final int num = BaseDate.draw.length;
		
		x=random.nextInt(num);
		
		Button key = (Button)findViewById(R.id.button1);
		final ImageView image = (ImageView)findViewById(R.id.imageView1);
		RadioGroup select = (RadioGroup)findViewById(R.id.radiogroup2);
		final RadioButton rba = (RadioButton)findViewById(R.id.radio0);
		final RadioButton rbb = (RadioButton)findViewById(R.id.radio1);
		final RadioButton rbc = (RadioButton)findViewById(R.id.radio2);
		RadioButton rbd = (RadioButton)findViewById(R.id.radio3);
		
		image.setImageDrawable(getResources().getDrawable(BaseDate.draw[x]));
		
		select.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				String answer;
				if(checkedId==rba.getId()){answer="A";}
  				else if(checkedId==rbb.getId()){answer="B";}
  				else if(checkedId==rbc.getId()){answer="C";}
  				else{answer="D";}
				if(answer == BaseDate.answer[x])
				{
					MLockScreenOne.this.finish();
					overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
				}
				else
				{
					Toast.makeText(MLockScreenOne.this, "错误答案，获得奖励“再来一道”", 5000).show();
					if(x == (num-1)){
						x = 0;
					}
					else{
						x++;
					}
					image.setImageDrawable(getResources().getDrawable(BaseDate.draw[x]));
					RadioButton rb = (RadioButton)MLockScreenOne.this.findViewById(checkedId);
					rb.setChecked(false);
				}
			}
			
		});

		key.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MLockScreenOne.this, "本题答案："+BaseDate.answer[x], 5000).show();
			}
			
		});
		
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
