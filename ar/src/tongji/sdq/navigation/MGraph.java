package tongji.sdq.navigation;

import java.util.ArrayList;


import android.graphics.PointF;


public class MGraph {
    public final static int INFINITY = Integer.MAX_VALUE;
    private int vexNum;
    private ArrayList<PointF> posCoord;
    private PointF start;
    private PointF end;
    private int[][] boolArcs;

    public MGraph(int vexNum, int[][] boolArcs, ArrayList<PointF> posCoord) {
        this.vexNum = vexNum;
        this.posCoord = posCoord;
        this.boolArcs = boolArcs;
        this.vexNum = boolArcs.length;
    }

    private float[][] boolToArcs(int[][] boolArcs) {
        float Dist = 0f;
        float[][] arcsDist = new float[vexNum][vexNum];
        for (int i = 0; i < boolArcs.length; i++) {
            for (int j = 0; j < boolArcs.length; j++) {
                if (boolArcs[i][j] != 0) {
                    start = posCoord.get(i);
                    end = posCoord.get(j);
                    Dist = getDistance(start, end);
                    arcsDist[i][j] = Dist;
                } else {
                    arcsDist[i][j] = INFINITY;
                }
            }
        }
        return arcsDist;
    }

    //����start��end�������ľ���
    public float getDistance(PointF start, PointF end) {
        // TODO Auto-generated method stub
        float distance = (float) (Math.sqrt(Math.pow((start.x - end.x), 2) + Math.pow((start.y - end.y), 2)));
        return distance;
    }

    public float[][] getArcs() {
        return this.boolToArcs(boolArcs);
    }

    public int getVexNum() {
        return vexNum;
    }

    public ArrayList<PointF> getArray() {
        return this.posCoord;
    }

    public int getKey(PointF currPos) {
        // TODO Auto-generated method stub
        int i = 0;
        int key = -1;
        while (i < this.posCoord.size()) {
            if (posCoord.get(i).equals(currPos.x, currPos.y))
                key = i;
            i++;
        }
        return key;
    }
}
