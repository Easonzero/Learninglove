package com.wangyi.learninglove;

import java.io.File;
import java.util.ArrayList;

import com.wangyi.learninglove.R;
import com.wangyi.screenlocker.TLockScreenControl;
import com.wangyi.screenlocker.TimeActivityTwoDate;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TimeAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<String> times = null;
    private Context context;
    Animation animation;
    public TimeAdapter(Context context,ArrayList<String> times){
    	this.context = context;
        inflater = LayoutInflater.from(context);
        this.times = times;
        animation = (Animation)AnimationUtils.loadAnimation(context, R.animator.slide_small_big);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return times.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        MyListener myListener = null;
        if (null == convertView){
            convertView = inflater.inflate(R.layout.timedate, null);
            holder = new ViewHolder();
            holder.text = (TextView)convertView.findViewById(R.id.textView1);
            holder.image = (ImageView)convertView.findViewById(R.id.imageView1); 
            holder.image.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View view, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						view.startAnimation(animation);
					}
					return false;
				}
            
            });
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        myListener = new MyListener(position);
        holder.text.setText(times.get(position));
        holder.image.setOnClickListener(myListener);
        return convertView;
    }
    private class ViewHolder{
        private TextView text;
        private ImageView image;
    }
    
    public ArrayList<String> getTimes(){
		return times;
    }
    
    private class MyListener implements OnClickListener{  
        int mPosition;  
        public MyListener(int inPosition){  
            mPosition = inPosition;  
        }  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
        	Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("是否删除该项？");
			builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);  
					Intent intent = new Intent(context, TLockScreenControl.class);
					PendingIntent sender = PendingIntent.getBroadcast(context,mPosition, intent, PendingIntent.FLAG_UPDATE_CURRENT);
					am.cancel(sender);
					times.remove(mPosition);
					TimeAdapter.this.notifyDataSetChanged();
				}
				
			});
			
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
				
			});
			builder.create().show();  
        }  
          
    }
}
