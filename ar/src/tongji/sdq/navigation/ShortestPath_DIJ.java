package tongji.sdq.navigation;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;

public class ShortestPath_DIJ {
    private boolean[][] P;
    private float[] D;
    public final static int INFINITY = Integer.MAX_VALUE;
    PointF currPos;
    MGraph mGraph;
    int v0;

    //	private ArrayList<PointF> posCoord;
    public void DIJ(MGraph mGraph, int v0) {
        //v0=mGraph.getKey(currPos);
        //System.out.println("v0="+v0);
        int vexNum = mGraph.getVexNum();
//		posCoord=mGraph.getArray();
        P = new boolean[vexNum][vexNum];
        D = new float[vexNum];
        boolean[] finish = new boolean[vexNum];
        for (int v = 0; v < vexNum; v++) {
            finish[v] = false;
            D[v] = mGraph.getArcs()[v0][v];
            for (int w = 0; w < vexNum; w++)
                P[v][v0] = false;
            if (D[v] < INFINITY) {
                P[v][v0] = true;
                P[v][v] = true;
            }
        }
        D[v0] = 0;
        finish[v0] = true;
        int v = -1;
        for (int i = 0; i < vexNum; i++) {
            if (i == v0) i++;
            float min = INFINITY;
            for (int w = 0; w < vexNum; w++) {

                if (!finish[w]) {

                    if (D[w] < min) {
                        v = w;
//						System.out.println("w"+w);
                        min = D[w];
                    }
                }
            }
            finish[v] = true;
            for (int w = 0; w < vexNum; w++)
                if (!finish[w] && mGraph.getArcs()[v][w] < INFINITY && (min + mGraph.getArcs()[v][w] < D[w])) {
                    D[w] = min + mGraph.getArcs()[v][w];
                    System.arraycopy(P[v], 0, P[w], 0, P[v].length);
                    P[w][w] = true;

                }
        }
    }

    public List<Integer> getPointsArray(int intwhat) {
        List<Integer> points = new ArrayList<Integer>();
        boolean path[] = this.getP(intwhat);
        for (int i = 0; i < path.length; i++) {
            if (path[i])
                points.add(i);
        }
        return points;
    }

    public float[] getD() {
        return D;
    }

    public boolean[] getP(int item) {
        return P[item];
    }
}
