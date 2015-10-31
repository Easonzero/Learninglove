package com.wangyi.notetext;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wangyi.learninglove.InDialog;
import com.wangyi.learninglove.R;
import com.wangyi.learninglove.MyAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReadActivity extends Activity {
	private Button back;
	private Button character;
	private Button search;
	private TextView title;
	ListView list;
	private ArrayList<String> names = null;  
	private ArrayList<String> paths = null;
	private String nowPath;
	private String nowName;
	private RelativeLayout relativelayout;
	Animation animation;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_activity);
		
		back = (Button)findViewById(R.id.button1);
		title = (TextView)findViewById(R.id.textView1);
		list = (ListView)findViewById(R.id.ListView);
		character = (Button)findViewById(R.id.button2);
		search = (Button)findViewById(R.id.button3);
		relativelayout = (RelativeLayout) findViewById(R.id.relativelayout);
		animation = (Animation)AnimationUtils.loadAnimation(this, R.animator.slide_small_big);
		relativelayout.setBackground(getResources().getDrawable(R.drawable.back_ground_11));
		
		back.setOnTouchListener(listener);
		character.setOnTouchListener(listener);
		search.setOnTouchListener(listener);
		
		String[] subjectName = fileList("/sdcard/learninglove/Note/");
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("ϣ�������ĿƱʼǣ�");
		builder.setItems(subjectName, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO Auto-generated method stub
				String[] subjectName = fileList("/sdcard/learninglove/Note/");
				title.setText(subjectName[which] + "�ʼ�");
				nowName = subjectName[which];
				nowPath = "/sdcard/learninglove/Note/"+subjectName[which]+"/";
				showFileDir("/sdcard/learninglove/Note/"+subjectName[which]+"/");
			}
		});
		builder.setCancelable(false);
		builder.create().show();
		
		character.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String[] subjectName = fileList("/sdcard/learninglove/Note/");
				Builder builder = new AlertDialog.Builder(ReadActivity.this);
				builder.setTitle("ϣ�������ĿƱʼǣ�");
				builder.setItems(subjectName, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO Auto-generated method stub
						String[] subjectName = fileList("/sdcard/learninglove/Note/");
						title.setText(subjectName[which] + "�ʼ�");
						nowName = subjectName[which];
						nowPath = "/sdcard/learninglove/Note/"+subjectName[which]+"/";
						showFileDir("/sdcard/learninglove/Note/"+subjectName[which]+"/");
					}
				});
				builder.setCancelable(false);
				builder.create().show();
			}
			
		});
		
		handler = new Handler(){
        	public void dispatchMessage(Message msg){
        		switch(msg.what){
        		case 0:
        			ReadActivity.this.finish();
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
		
		search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final InDialog searchDialog = new InDialog(ReadActivity.this);
				searchDialog.setTitle("�����������ҵıʼǱ�ǩ��");
				android.content.DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
		        	@Override
		          	public void onClick(DialogInterface dialog, int which) {
		        		// TODO Auto-generated method stub
		        		showFileDir(nowPath,new FileFilter()
		                {
		                    public boolean accept(File pathname)
		                    {
		                    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this);
		                    	String tap = prefs.getString(pathname.getName(), "");
		                        String regEx = searchDialog.editText.getText().toString();
		                        Pattern pat=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
		                        Matcher mat = pat.matcher(tap);
		                        if(mat.find()){
		                        	return true;
		                        }
		                        return false;
		                    }
		                });
		        		
		        		
		        	}
				};
				searchDialog.setButton("ȷ��", listener2);
				searchDialog.setButton2("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				searchDialog.show();
			}
			
		});
		
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				String path = paths.get(position);
				File file = new File(path);
				fileHandle(file);
			}
			
		});
		
		title.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Builder builder = new AlertDialog.Builder(ReadActivity.this);
				builder.setTitle("�Ƿ�ɾ���ÿ�Ŀ�ʼ�");
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
					
				});
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						File file = new File("/sdcard/learninglove/Note/"+nowName);
						File[] childFile = file.listFiles();
						for(int i = 0;i < childFile.length;i++){
							childFile[i].delete();
						}
						file.delete();
						
						String[] subjectName = fileList("/sdcard/learninglove/Note/");
						Builder builder = new AlertDialog.Builder(ReadActivity.this);
						builder.setTitle("ϣ�������ĿƱʼǣ�");
						builder.setItems(subjectName, new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int which) {
								// TODO Auto-generated method stub
								String[] subjectName = fileList("/sdcard/learninglove/Note/");
								title.setText(subjectName[which] + "�ʼ�");
								nowPath = "/sdcard/learninglove/Note/"+subjectName[which]+"/";
								nowName = subjectName[which];
								showFileDir("/sdcard/learninglove/Note/"+subjectName[which]+"/");
							}
						});
						builder.setCancelable(false);
						builder.create().show();
					}
					
				});
				builder.create().show();
			}
			
		});
	}
	
	public void showFileDir(String path){  
		names = new ArrayList<String>();  
		paths = new ArrayList<String>();  
		File file = new File(path);  
		File[] files = file.listFiles();  
		  
		for (File f : files){  
		names.add(f.getName());  
		paths.add(f.getPath());  
		}  
		list.setAdapter(new MyAdapter(this,names, paths));  

	}
	
	private void showFileDir(String path , FileFilter filefilter){  
		names = new ArrayList<String>();  
		paths = new ArrayList<String>();  
		File file = new File(path);  
		File[] files = file.listFiles(filefilter);  
		  
		for (File f : files){  
		names.add(f.getName());  
		paths.add(f.getPath());  
		}  
		list.setAdapter(new MyAdapter(this,names, paths));  

	}
	
	private void fileHandle(final File file){
	    android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	    	String suffix = file.getName().substring(file.getName().length()-4,file.getName().length());
	    	if (which == 0){
	    		if(suffix.equals(".jpg")){
	    			openImageFile(file);
	    		}
	    		else if(suffix.equals(".txt")){
	    			openTextFile(file);
	    		}
	    	}
	    	else if(which == 1){
	    		final InDialog renameDialog = new InDialog(ReadActivity.this)  ;      
	            renameDialog.setTitle("��ӱ�ǩ�");
	    		android.content.DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
	        	@Override
	          	public void onClick(DialogInterface dialog, int which) {
	        		// TODO Auto-generated method stub
	        		String modifyName = renameDialog.editText.getText().toString();
	        		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
	        		editor.putString(file.getName(), modifyName);
	        		editor.commit();
	        		showFileDir(nowPath);
	       	}
	    };
		renameDialog.setButton("ȷ��", listener2);
		renameDialog.setButton2("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
	  		renameDialog.show();
	    }
	    else{
	      	new AlertDialog.Builder(ReadActivity.this)
	       	.setTitle("ע��!")
	       	.setMessage("ȷ��Ҫɾ�����ļ���")
	       	.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	       		@Override
	       		public void onClick(DialogInterface dialog, int which) {
	       			if(file.delete()){
	       				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
	       				editor.remove(file.getName());
	       				showFileDir(file.getParent());
	       				displayToast("ɾ���ɹ���");
	       			}
	       			else{
	       				displayToast("ɾ��ʧ�ܣ�");
	       			}
	       		}
	     	})
	       	.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	          	@Override
	           	public void onClick(DialogInterface dialog, int which) {
		        
	          	}
	      	}).show();
	    	}
	    }
	    };
	        String[] menu = {"���ļ�","��ӱ�ǩ","ɾ���ļ�"};
	        new AlertDialog.Builder(ReadActivity.this)
	        .setTitle("��ѡ��Ҫ���еĲ���!")
	        .setItems(menu, listener)
	        .setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                
	            }
	        }).show();
		}
	    private void openImageFile(File file){
	    	Intent intent = new Intent();
	    	intent.setAction(android.content.Intent.ACTION_VIEW);
	    	intent.setDataAndType(Uri.fromFile(file), "image/*");
	    	startActivity(intent); 
	    }
	    
	    private void openTextFile(File file){
	    	Intent intent = new Intent(ReadActivity.this,NoteActivity.class);
	    	intent.putExtra("basePath", nowPath);
	    	intent.putExtra("name",file.getName());
	    	intent.putExtra("action","view");
	    	startActivityForResult(intent,0);
	    }
	    
	    private void displayToast(String message){
	        Toast.makeText(ReadActivity.this, message, Toast.LENGTH_SHORT).show();
	    }
	    
	    private String[] fileList(String path){
	    	File file = new File(path); 
    		File[] files = file.listFiles();
    		String[] subjectName = new String[files.length];
    		for (int i = 0 ;i < files.length;i++){ 
    			subjectName[i] = files[i].getName();
    		}
    		return subjectName;
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
		
		protected void onActivityResult(final int requestCode, int resultCode, Intent intent){
			this.showFileDir(nowPath);
		}
	}
	
