/***Designed by ShiDanqing.
 ***Tongji University***/
package tongji.sdq.navigation;

import java.util.ArrayList;
import java.util.List;


import android.graphics.PointF;

public class Navigation {
    private int destinationID;
    private int vexNum = 13;
    public int[][] boolArcs =
            {
                    {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //0
                    {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //1
                    {1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, //2
                    {0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0}, //3
                    {0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0}, //4
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0}, //5
                    {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0}, //6
                    {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1}, //7
                    {0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0}, //8
                    {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0}, //9
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0}, //10
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0}, //11
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1}, //12
            };
    private ArrayList<PointF> posCoord;
    private List<Integer> posIDs;
    ShortestPath_DIJ shortestPath;
    MGraph mgraph;

    public Navigation() {

        posCoord = new ArrayList<PointF>() {//��Ӧ���
            {
                add(new PointF(540, 1160));
                add(new PointF(750, 1160));
                add(new PointF(540, 840));
                add(new PointF(800, 840));
                add(new PointF(540, 600));
                add(new PointF(750, 600));
                add(new PointF(160, 400));
                add(new PointF(460, 400));
                add(new PointF(540, 400));
                add(new PointF(700, 400));
                add(new PointF(160, 320));
                add(new PointF(700, 320));
                add(new PointF(460, 220));
            }
        };
        mgraph = new MGraph(vexNum, boolArcs, posCoord);//���ͼ
        shortestPath = new ShortestPath_DIJ();
    }

    public void setDestinationID(int destinationID) {//�趨Ŀ���
        this.destinationID = destinationID;
    }

    public List<Integer> getRouteIDs(int currPosId) {//ȡ��·�������id
        shortestPath.DIJ(mgraph, currPosId);
        List<Integer> points = shortestPath.getPointsArray(destinationID);
        return points;
    }

    public List<PointF> getRoutePoints(int currPosId) {//ȡ��·����������ֵ
        List<PointF> pos = new ArrayList<PointF>();
        posIDs = getRouteIDs(currPosId);
        int id;
        for (int i = 0; i < posIDs.size(); i++) {
            id = posIDs.get(i);
            pos.add(posCoord.get(id));
        }
        return pos;
    }
}
