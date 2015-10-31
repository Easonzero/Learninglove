package com.wangyi.learninglove;

import java.io.File;
import java.util.ArrayList;

import com.wangyi.learninglove.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Bitmap file;
    private Bitmap book;
    private Context context;
    private ArrayList<String> names = null;
    private ArrayList<String> paths = null;
    public MyAdapter(Context context,ArrayList<String> na,ArrayList<String> pa){
        names = na;
        paths = pa;
        this.context = context;
        file = BitmapFactory.decodeResource(context.getResources(),R.drawable.f);
        file = small(file,1f);
        book = BitmapFactory.decodeResource(context.getResources(),R.drawable.book);
        book = small(book,0.125f);
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return names.get(position);
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (null == convertView){
            convertView = inflater.inflate(R.layout.file, null);
            holder = new ViewHolder();
            holder.text = (TextView)convertView.findViewById(R.id.textView);
            holder.image = (ImageView)convertView.findViewById(R.id.imageView);
            holder.tap = (TextView)convertView.findViewById(R.id.tap);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        File f = new File(paths.get(position).toString());
        holder.text.setText(f.getName());
        holder.tap.setText(prefs.getString(f.getName(), ""));
        String suffix = f.getName().substring(f.getName().length()-4,f.getName().length());
        if(suffix.equals(".jpg")){
        	holder.image.setImageBitmap(file);
		}
		else if(suffix.equals(".txt")){
			holder.image.setImageBitmap(book);
		}
        return convertView;
    }
    private class ViewHolder{
        private TextView text;
        private TextView tap;
        private ImageView image;
    }
    private Bitmap small(Bitmap map,float num){
        Matrix matrix = new Matrix();
        matrix.postScale(num, num);
        return Bitmap.createBitmap(map,0,0,map.getWidth(),map.getHeight(),matrix,true);
    }
}



