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
	static String eMean[] = {"n.驴","n.鱿鱼","n.龙虾","n.鲨鱼","n.海豹","n.抹香鲸","n.虎鲸"};
	static String baseWord[] = {"dumpling","dull","duke","due","duck","ducat","dry","drunk","drum",   
		"drug","drown","drop","driver","drive","drink","drill","drier","dress","dream","drawing","drawer"};
	static String baseMean[] = {"n.饺子","a.呆笨的；单调的；阴暗的","n.公爵，(欧洲公国的)君主 ","a.应支付的，正当的，预定应到的(to)，由于",
		"n.鸭，鸭肉v.忽地低下头(或弯腰)，躲避 ","n.过去曾在欧洲的许多国家通用的金币名","a.干(旱)的；干渴的；枯燥 vt.使干燥，晒干","a.醉酒的；(喻)陶醉的 n.酗酒者，醉汉",
		"n.鼓，鼓声；桶，筒v.有节奏地敲击","v.掺麻醉药/毒药于(食物、饮料等);用药麻醉","v.淹死，(高声音)遮掩(低声音)","v.投下，滴落；下降；降低n.滴；下降；微量",
		"n.司机；[机械]起子","v.驾驶；开动；把(钉、桩等)打入；驱；迫使","v.(drank,drunk)喝，饮 n.饮料；喝酒","n.钻头，钻床；操练，演习 v.钻(孔)；操练",
		"n.吹风机","n.服装；女装，童装 v.给…穿衣；打扮","n.梦(想)，理想vi.做梦；想vt.梦想；想到","n.图画，图样，素描；提款；提存","n.拖曳者"};
	static int[] draw = {R.drawable.math1,R.drawable.math2,R.drawable.math3,R.drawable.math4,R.drawable.math5,R.drawable.math6,R.drawable.math7};
    static String[] answer = {"D","C","D","C","C","C","B"};
    static String[] gogo = {"少壮不努力，老大徒伤悲","莫找借口失败，只找借口成功","世界上那些最容易的事情中，拖延时间最不费力","人之所以能，是相信能","每一发奋努力的背后，必有加倍的赏赐","含泪播种的人一定能含泪收获"};
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
