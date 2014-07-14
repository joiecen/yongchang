package com.example.ibeaconspositiontest;

import com.example.ibeaconspositiontest.MyApplication;
import com.example.ibeaconspositiontest.MarkView;
import com.example.ibeaconspositiontest.Position;
import com.example.ibeaconspositiontest.PositionView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class MapView extends ImageView{

	private MyApplication myApplication;

    // These matrixes will be used to move and zoom image
    private Matrix myMatrix = new Matrix();
    private Matrix mySavedMatrix = new Matrix();

    //position view
    private PositionView position = new PositionView(getContext());
    private MarkView mark = new MarkView(getContext());

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float[] values = new float[9];
        myMatrix.getValues(values);

        myApplication = (MyApplication) getContext().getApplicationContext();

        Position currentPosition = myApplication.getPosition();

        /***set mark position***/
        Position markPosition = myApplication.getMarkPosition();
        if (myApplication.markModeOnOrOff()) {
            mark.setmarkposition(markPosition.position.x, markPosition.position.y);
            mark.drawThePositionOnMap(canvas, values);
        }

        /***set current position***/
        if (myApplication.positionModeOnOrOff()) {
        	Log.i("run", ""+currentPosition.position);//incorrect
            position.setPosition(myApplication.getPosition().position.x,myApplication.getPosition().position.y);
            Log.i("hhh", ""+myApplication.getPosition().position.x);
            position.drawThePositionOnMap(canvas, values);
        }

    }

}