package com.wangyi.correct;

import android.app.Activity;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wangyi.learninglove.R;
import com.wangyi.learninglove.MyAdapter;
import com.wangyi.learninglove.InDialog;

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
import android.provider.MediaStore;
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
		relativelayout.setBackground(getResources().getDrawable(R.drawable.back_ground_22));
		
		back.setOnTouchListener(listener);
		character.setOnTouchListener(listener);
		search.setOnTouchListener(listener);
		
		String[] subjectName = fileList("/sdcard/learninglove/correct/");
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("希望查阅哪科错题本？");
		builder.setItems(subjectName, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO Auto-generated method stub
				String[] subjectName = fileList("/sdcard/learninglove/correct/");
				title.setText(subjectName[which] + "错题本");
				nowPath = "/sdcard/learninglove/correct/"+subjectName[which]+"/";
				nowName = subjectName[which];
				showFileDir("/sdcard/learninglove/correct/"+subjectName[which]+"/");
			}
		});
		builder.setCancelable(false);
		builder.create().show();
		
		character.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String[] subjectName = fileList("/sdcard/learninglove/correct/");
				Builder builder = new AlertDialog.Builder(ReadActivity.this);
				builder.setTitle("希望查阅哪科错题本？");
				builder.setItems(subjectName, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO Auto-generated method stub
						String[] subjectName = fileList("/sdcard/learninglove/correct/");
						title.setText(subjectName[which] + "错题本");
						nowPath = "/sdcard/learninglove/correct/"+subjectName[which]+"/";
						nowName = subjectName[which];
						showFileDir("/sdcard/learninglove/correct/"+subjectName[which]+"/");
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
				searchDialog.setTitle("请输入所查找的错题标签：");
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
				searchDialog.setButton("确定", listener2);
				searchDialog.setButton2("取消", new DialogInterface.OnClickListener() {
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
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				String path = paths.get(position);
				File file = new File(path);
				if (file.isDirectory()){
					showFileDir(path);  
				}  
				else{  
					fileHandle(file,position);  
				}  
			}
			
		});
		
		title.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Builder builder = new AlertDialog.Builder(ReadActivity.this);
				builder.setTitle("是否删除该科目错题本");
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
					
				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						File file = new File("/sdcard/learninglove/correct/"+nowName);
						File fileAnswer = new File("/sdcard/learninglove/correctAnswer/"+nowName);
						File[] childFile = file.listFiles();
						File[] childFileAnswer = fileAnswer.listFiles();
						for(int i = 0;i < childFile.length;i++){
							childFile[i].delete();
							childFileAnswer[i].delete();
						}
						file.delete();
						fileAnswer.delete();
						
						String[] subjectName = fileList("/sdcard/learninglove/correct/");
						Builder builder = new AlertDialog.Builder(ReadActivity.this);
						builder.setTitle("希望查阅哪科错题本？");
						builder.setItems(subjectName, new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int which) {
								// TODO Auto-generated method stub
								String[] subjectName = fileList("/sdcard/learninglove/correct/");
								title.setText(subjectName[which] + "错题本");
								nowPath = "/sdcard/learninglove/correct/"+subjectName[which]+"/";
								nowName = subjectName[which];
								showFileDir("/sdcard/learninglove/correct/"+subjectName[which]+"/");
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
	
	private void showFileDir(String path){  
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
	
	private void fileHandle(final File file,final int position){
	    android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	    	if (which == 0){
	    		
	    		Builder builder = new AlertDialog.Builder(ReadActivity.this);
	    		builder.setItems(new String[]{"看题","看解析"},new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if(arg1 == 0){
							openFile(file);
						}
						else if(arg1 == 1){
							File file2 = new File("/sdcard/learninglove/correctAnswer/"+nowName+"/"+file.getName().substring(0,file.getName().indexOf("g")+1));
							openFile(file2);
						}
					}
	    			
	    		});
	    		builder.create().show();
	    	}
	    	else if(which == 1){
	    		final InDialog renameDialog = new InDialog(ReadActivity.this)  ; 
	    		renameDialog.setTitle("添加标签项：");
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
		renameDialog.setButton("确定", listener2);
		renameDialog.setButton2("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
	  		renameDialog.show();
	    }
	    else if(which == 2){
	    	File fileName = new File("/sdcard/learninglove/correctAnswer/"+nowName+"/"+file.getName().substring(0,file.getName().indexOf("g")+1));
			if(!fileName.exists()){
				Intent intentc = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri originalUri = Uri.fromFile(fileName);
				intentc.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
				startActivity(intentc);
			}
			else{
				displayToast("该题已拥有解析！");
			}
			
	    }
	    else if(which == 4){
	      	new AlertDialog.Builder(ReadActivity.this)
	       	.setTitle("注意!")
	       	.setMessage("确定要删除此文件吗？")
	       	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	       		@Override
	       		public void onClick(DialogInterface dialog, int which) {
	       			File fileName = new File("/sdcard/learninglove/correctAnswer/"+nowName+"/"+file.getName().substring(0,file.getName().indexOf("g")+1));
	       			if(file.delete()){
	       				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
	       				editor.remove(file.getName());
	       				showFileDir(file.getParent());
	       				displayToast("删除成功！");
	       				if(fileName.exists()){
	       					fileName.delete();
	       				}
	       			}
	       			else{
	       				displayToast("删除失败！");
	       			}
	       		}
	     	})
	       	.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	          	@Override
	           	public void onClick(DialogInterface dialog, int which) {
		        
	          	}
	      	}).show();
	    	}
	    else{
	    	Builder builder = new AlertDialog.Builder(ReadActivity.this);
    		builder.setItems(new String[]{"裁剪错题","裁剪解析"},new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if(arg1 == 0){
						Intent intent = new Intent(ReadActivity.this,CropActivity.class);
				    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				    	intent.putExtra("filePath", file.getPath());
				    	startActivity(intent);
					}
					else if(arg1 == 1){
						File file1 = new File("/sdcard/learninglove/correctAnswer/"+nowName+"/"+file.getName().substring(0,file.getName().indexOf("g")+1));
						Intent intent = new Intent(ReadActivity.this,CropActivity.class);
				    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				    	intent.putExtra("filePath", file1.getPath());
				    	startActivity(intent);
					}
				}
    			
    		});
    		builder.create().show();
	    }
	    }
	    };
	        String[] menu = {"打开文件","添加标签","添加解析","裁剪","删除文件"};
	        new AlertDialog.Builder(ReadActivity.this)
	        .setTitle("请选择要进行的操作!")
	        .setItems(menu, listener)
	        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                
	            }
	        }).show();
		}
	    private void openFile(File file){
	    	Intent intent = new Intent();

	    	intent.setAction(android.content.Intent.ACTION_VIEW);

	    	intent.setDataAndType(Uri.fromFile(file), "image/*");

	    	startActivity(intent); 
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
	}
