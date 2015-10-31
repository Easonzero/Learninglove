package com.wangyi.screenlocker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.wangyi.learninglove.R;

public class BaseDate {
	static String eWord[] = {"donkey","squid","lobster","shark","seal","spermwhale","killerwhale"}; 
	static String eMean[] = {"n.¿","n.����","n.��Ϻ","n.����","n.����","n.Ĩ�㾨","n.����"};
	static String baseWord[] = {"dumpling","dull","duke","due","duck","ducat","dry","drunk","drum",   
		"drug","drown","drop","driver","drive","drink","drill","drier","dress","dream","drawing","drawer"};
	static String baseMean[] = {"n.����","a.�����ģ������ģ�������","n.������(ŷ�޹�����)���� ","a.Ӧ֧���ģ������ģ�Ԥ��Ӧ����(to)������",
		"n.Ѽ��Ѽ��v.���ص���ͷ(������)����� ","n.��ȥ����ŷ�޵�������ͨ�õĽ����","a.��(��)�ģ��ɿʵģ����� vt.ʹ���ɹ��","a.��Ƶģ�(��)����� n.����ߣ���",
		"n.�ģ�������Ͱ��Ͳv.�н�����û�","v.������ҩ/��ҩ��(ʳ����ϵ�);��ҩ����","v.������(������)����(������)","v.Ͷ�£����䣻�½�������n.�Σ��½���΢��",
		"n.˾����[��е]����","v.��ʻ����������(����׮��)���룻������ʹ","v.(drank,drunk)�ȣ��� n.���ϣ��Ⱦ�","n.��ͷ���괲����������ϰ v.��(��)������",
		"n.�����","n.��װ��Ůװ��ͯװ v.�������£����","n.��(��)������vi.���Σ���vt.���룻�뵽","n.ͼ����ͼ�������裻�����","n.��ҷ��"};
	static int[] draw = {R.drawable.math1,R.drawable.math2,R.drawable.math3,R.drawable.math4,R.drawable.math5,R.drawable.math6,R.drawable.math7};
    static String[] answer = {"D","C","D","C","C","C","B"};
    static String[] gogo = {"��׳��Ŭ�����ϴ�ͽ�˱�","Ī�ҽ��ʧ�ܣ�ֻ�ҽ�ڳɹ�","��������Щ�����׵������У�����ʱ�������","��֮�����ܣ���������","ÿһ����Ŭ���ı��󣬱��мӱ����ʹ�","���Ქ�ֵ���һ���ܺ����ջ�"};
    public static void read(){
    	if(new File("/sdcard/learninglove/eWord.txt").exists()){
    		try {BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/learninglove/eWord.txt")));
			for(int i = 0;i<eWord.length ;i++ ){
				eWord[i] = br1.readLine();
			}
			BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/learninglove/eMean.txt")));
			for(int i = 0;i<eMean.length ;i++ ){
				eMean[i] = br2.readLine();
			}
			BufferedReader br3 = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/learninglove/baseWord.txt")));
			for(int i = 0;i<baseWord.length ;i++ ){
				baseWord[i] = br3.readLine();
			}
			BufferedReader br4 = new BufferedReader(new InputStreamReader(new FileInputStream("/sdcard/learninglove/baseMean.txt")));
			for(int i = 0;i<baseMean.length ;i++ ){
				baseMean[i] = br4.readLine();
			}
			br1.close();
    		br2.close();
    		br3.close();
    		br4.close();
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
    public static void write(){
    	try {File file =new File("/sdcard/learninglove/eWord.txt");
		if(!file.exists()){
			
				file.createNewFile();
			
		}
		FileWriter fileWritter = new FileWriter(file.getPath());
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		for(int i=0;i<eWord.length;i++){
			bufferWritter.write(eWord[i]);
			bufferWritter.newLine();
		}
		bufferWritter.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    	try {File file =new File("/sdcard/learninglove/eMean.txt");
		if(!file.exists()){
			
				file.createNewFile();
			
		}
		FileWriter fileWritter = new FileWriter(file.getPath());
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		for(int i=0;i<eMean.length;i++){
			bufferWritter.write(eMean[i]);
			bufferWritter.newLine();
		}
		bufferWritter.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    	try {File file =new File("/sdcard/learninglove/baseWord.txt");
		if(!file.exists()){
			
				file.createNewFile();
			
		}
		FileWriter fileWritter = new FileWriter(file.getPath());
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		for(int i=0;i<baseWord.length;i++){
			bufferWritter.write(baseWord[i]);
			bufferWritter.newLine();
		}
		bufferWritter.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    	try {File file =new File("/sdcard/learninglove/baseMean.txt");
		if(!file.exists()){
			
				file.createNewFile();
			
		}
		FileWriter fileWritter = new FileWriter(file.getPath());
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		for(int i=0;i<baseMean.length;i++){
			bufferWritter.write(baseMean[i]);
			bufferWritter.newLine();
		}
		bufferWritter.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    }
}
