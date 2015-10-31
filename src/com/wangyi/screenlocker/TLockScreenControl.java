package com.wangyi.screenlocker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.wangyi.learninglove.OnLock;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

@SuppressLint("Wakelock") public class TLockScreenControl extends BroadcastReceiver {
	String screen;
	@Override
	public void onReceive(Context context, Intent intent) {
			int min = intent.getIntExtra("min", 0);
			TLockScreen.min = min;
			TLockScreen.timebase = min*60000+System.currentTimeMillis();
			try {BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/learninglove/screen.txt")));
				screen = br.readLine();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(screen.equals("true")){
				Intent intentonlock = new Intent();
				intentonlock.setClass(context, OnLock.class);
				intentonlock.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intentonlock);
			}
			else{
				Intent intentonlock = new Intent();
				intentonlock.setClass(context, TLockScreen.class);
				intentonlock.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
				@SuppressWarnings("deprecation")
				WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
				mWakelock.acquire(); 
				context.startActivity(intentonlock);
				mWakelock.release();
			}
	}
	
}
