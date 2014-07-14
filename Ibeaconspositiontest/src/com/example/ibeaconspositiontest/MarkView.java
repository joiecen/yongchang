package com.example.ibeaconspositiontest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class MarkView extends View{
	
	private Paint markPaint;
	private PointF myposition, relativePosition;
	private float posRadius;//蓝色圆形区域
	//private Bitmap myposimage;

	public MarkView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		markPaint = new Paint();
		markPaint.setColor(Color.CYAN);
		markPaint.setAlpha(60);
		posRadius=100f;
		myposition = new PointF(0,0);	
		 relativePosition = new PointF(0, 0);
		//myposimage = ((BitmapDrawable)getResources().getDrawable(R.drawable.arrow)).getBitmap();//能不能用别的方法？
		
	}
	
	public void setmarkposition(float x,float y)
	{
		myposition.x = x;
		myposition.y = y;
	}
	
	 public void drawThePositionOnMap(Canvas canvas, float[] matrixValues) {
	        relativePosition.x = matrixValues[2] + myposition.x * matrixValues[0];
	        relativePosition.y = matrixValues[5] + myposition.y * matrixValues[4];
	        canvas.drawCircle(relativePosition.x, relativePosition.y, posRadius, markPaint);
	    }


}
