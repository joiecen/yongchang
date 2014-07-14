/***Designed by ShiDanqing.
 ***Tongji University***/
package tongji.sdq.ar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

public class LineView extends View {
    private Paint myPaint;
    private PointF startPosition, relativeStartPosition, endPosition, relativeEndPosition; //real position and relative position

    public LineView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        myPaint = new Paint();
        myPaint.setColor(Color.RED);
        myPaint.setAlpha(150);
        myPaint.setStrokeWidth(5);
        startPosition = new PointF(0, 0);
        relativeStartPosition = new PointF(0, 0);
        endPosition = new PointF(0, 0);
        relativeEndPosition = new PointF(0, 0);
    }

    public void setStart(float x, float y) {
        startPosition.x = x;
        startPosition.y = y;
    }

    public void setEnd(float x, float y) {
        endPosition.x = x;
        endPosition.y = y;
    }

    public void drawTheLineOnMap(Canvas canvas, float[] matrixValues) {
        relativeStartPosition.x = matrixValues[2] + startPosition.x * matrixValues[0];
        relativeStartPosition.y = matrixValues[5] + startPosition.y * matrixValues[4];
        relativeEndPosition.x = matrixValues[2] + endPosition.x * matrixValues[0];
        relativeEndPosition.y = matrixValues[5] + endPosition.y * matrixValues[4];
        canvas.drawLine(relativeStartPosition.x, relativeStartPosition.y, relativeEndPosition.x, relativeEndPosition.y, myPaint);
    }

}
