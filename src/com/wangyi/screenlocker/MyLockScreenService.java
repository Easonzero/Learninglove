package com.wangyi.screenlocker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MyLockScreenService extends Service {
	private static String kind = null;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.SCREEN_OFF");
		intentFilter.addAction("android.intent.action.SCREEN_ON");
		BroadcastReceiver UnlockerBroadcastReceiver = new BroadcastReceiver(){
		
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				String action = arg1.getAction();
				if(action == "android.intent.action.SCREEN_OFF"){
					try {File file =new File("/sdcard/learninglove/screen.txt");
					if(!file.exists()){
						
							file.createNewFile();
						
					}
					FileWriter fileWritter = new FileWriter(file.getPath());
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write("false");
					bufferWritter.close();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
			try {BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/learninglove/kind.txt")));
				kind = br.readLine();
				Intent intent = new Intent();
				if(kind.equals("ELockScreen")){
					intent.setClass(arg0,ELockScreen.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg0.startActivity(intent);
				}
				else if(kind.equals("MLockScreenOne")){
					intent.setClass(arg0,MLockScreenOne.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg0.startActivity(intent);
				}
				else if(kind.equals("MLockScreenTwo")){
					intent.setClass(arg0,MLockScreenTwo.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg0.startActivity(intent);
				}
				else if(kind.equals("TLockScreen")){
					intent.setClass(arg0,TLockScreen.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg0.startActivity(intent);
				}
				br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(action == "android.intent.action.SCREEN_ON"){
				try {File file =new File("/sdcard/learninglove/screen.txt");
				if(!file.exists()){
					
						file.createNewFile();
					
				}
				FileWriter fileWritter = new FileWriter(file.getPath());
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write("true");
				bufferWritter.close();
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
			}
		};
		registerReceiver(UnlockerBroadcastReceiver, intentFilter);
		
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		startService(new Intent(this, MyLockScreenService.class));
	}

}
