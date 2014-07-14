/***Designed by ShiDanqing.
 ***Tongji University***/
package tongji.sdq.ar;

import java.util.ArrayList;
import java.util.List;

import tongji.sdq.navigation.Navigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MapView extends ImageView {

    private MainApplication myApplication;
    private Navigation myNavigation = new Navigation();
    private List<Integer> routeIDs = new ArrayList<Integer>();
    private List<PointF> routePoints = new ArrayList<PointF>();
    private int oldPositionId = -1;
    private int oldDestinationId = -1;

    //3 states
    private static final int MAP_STATE_NONE = 0;
    private static final int MAP_STATE_DRAG = 1;
    private static final int MAP_STATE_ZOOM = 2;

    // These matrices will be used to move and zoom image
    private Matrix myMatrix = new Matrix();
    private Matrix mySavedMatrix = new Matrix();

    private int mode = MAP_STATE_NONE;

    // Remember some things for zooming
    private PointF mStart = new PointF();
    private PointF mid = new PointF();
    float mOldDist = 1f;

    //position view
    private PositionView position = new PositionView(getContext());
    private MarkView mark = new MarkView(getContext());
    //line view
    private LineView line = new LineView(getContext());


    /**
     * CONSTRUCTORS
     */

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * INSTANCE METHODS
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float[] values = new float[9];
        myMatrix.getValues(values);

        myApplication = (MainApplication) getContext().getApplicationContext();

        Position currentPosition = myApplication.getPosition();

        /***navigation***/
        if (myApplication.navigationModeOnOrOff()) {
            //if the position change, then use the navigation algorithm
            if ((oldPositionId != currentPosition.PositionID) ||
                    oldDestinationId != myApplication.getDestinationID()) {
                //update the oldPositionId and oldDestinationId
                oldPositionId = currentPosition.PositionID;
                oldDestinationId = myApplication.getDestinationID();
                //find path
                myNavigation.setDestinationID(myApplication.getDestinationID());
                routeIDs = myNavigation.getRouteIDs(currentPosition.PositionID);
                routePoints = myNavigation.getRoutePoints(currentPosition.PositionID);
            }

            //draw the line
            for (int i = 0; i < routeIDs.size(); i++)
                for (int j = i; j < routeIDs.size(); j++) {

                    if (i != j && myNavigation.boolArcs[routeIDs.get(i)][routeIDs.get(j)] == 1) {
                        line.setStart(routePoints.get(i).x, routePoints.get(i).y);
                        line.setEnd(routePoints.get(j).x, routePoints.get(j).y);
                        line.drawTheLineOnMap(canvas, values);
                    }

                }
        }

        /***set mark position***/
        Position markPosition = myApplication.getMarkPosition();
        if (myApplication.markModeOnOrOff()) {
            mark.setPosition(markPosition.position.x, markPosition.position.y);
            mark.drawThePositionOnMap(canvas, values);
        }

        /***set current position***/
        if (myApplication.positionModeOnOrOff()) {
            position.setPosition(currentPosition.position.x, currentPosition.position.y);
            position.drawThePositionOnMap(canvas, values);
//				changeMap(currentPosition.Map);
        }

    }

//		private void changeMap(String map){
//			if(map.equals("f1")){
//				this.setBackgroundResource(R.drawable.f1);
//			}else if(map.equals("f7")){
//				this.setBackgroundResource(R.drawable.f7);
//			}
//		}


    /**
     * Map moving and zooming
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                onTouchStart(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                onMultiTouchStart(event);
                break;
            case MotionEvent.ACTION_UP:
                onTouchEnd(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onMultiTouchEnd(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
        }

        return true;
    }

    public void onTouchStart(MotionEvent event) {
        mySavedMatrix.set(myMatrix);
        mStart.set(event.getX(), event.getY()); // save the location where touch started
        mode = MAP_STATE_DRAG;
    }

    public void onTouchEnd(MotionEvent event) {
        mode = MAP_STATE_NONE;
    }

    public void onMultiTouchStart(MotionEvent event) {
        mOldDist = spacing(event);

        // start zoom mode if touch points are spread far enough from each other
        if (mOldDist > 10f) {
            mySavedMatrix.set(myMatrix);
            midPoint(mid, event);
            mode = MAP_STATE_ZOOM;
        }
    }

    public void onMultiTouchEnd(MotionEvent event) {
        mySavedMatrix.set(myMatrix);
        mode = MAP_STATE_DRAG;
    }

    public void onTouchMove(MotionEvent event) {
        if (mode == MAP_STATE_DRAG) {
            mapMove(event);
        } else if (mode == MAP_STATE_ZOOM) {
            mapZoom(event);
        }
    }

    public void mapMove(MotionEvent event) {
        myMatrix.set(mySavedMatrix);
        myMatrix.postTranslate(event.getX() - mStart.x, event.getY() - mStart.y); // translates map
        setImageMatrix(myMatrix);
    }

    public void mapZoom(MotionEvent event) {
        float newDist = spacing(event);

        // zoom in/out if touch points are spread far enough from each other
        if (newDist > 10f) {
            myMatrix.set(mySavedMatrix);
            float scale = newDist / mOldDist;
            myMatrix.postScale(scale, scale, mid.x, mid.y); // zoom in/out
            setImageMatrix(myMatrix);
        }
    }

    /**
     * Helper methods for zoom functionality
     */

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


}
