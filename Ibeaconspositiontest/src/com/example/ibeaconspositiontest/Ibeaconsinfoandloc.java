package com.example.ibeaconspositiontest;

import android.R.integer;
import android.R.string;
import android.graphics.PointF;

public class Ibeaconsinfoandloc 
{
    public PointF ibPointF = new PointF(0,0); //ibeacons�ڵ�ͼ�ϵ�λ������
    public String type = new String();//��λ����ӦͼƬ���࣬�Ƿ���Ҫ������
    public String info = new String();//��λʱ��ʾ��������Ϣ
   // public float x,y=0;//����
    public int ibmajor;
    public int ibminor;
    public int ibrssi;
    public int ibproximity;
    /**constructors*/
    public Ibeaconsinfoandloc(String type,int ibrssi,PointF ibPointF,String info,int ibmajor, int ibminor,int ibproximity)
    {
    	this.type = type;
    	this.ibrssi = ibrssi;
    	this.ibPointF = ibPointF;
    	this.info = info;
    	this.ibmajor = ibmajor;
    	this.ibminor = ibminor;
    	this.ibproximity = ibproximity;
    }
}
