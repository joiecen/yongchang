package tongji.sdq.ar;

import android.graphics.PointF;

public class Location {
	public PointF point = new PointF(0,0);//position in the map
	public String map = new String();
	public String type = new String();
	public String info = new String();
	public String text = new String();
	public float x,y = 0;//position on the screen
	public boolean isshow; //whether the canvas was showed  on the screen
	public boolean isclick;//whether the canvas was click
	/** CONSTRUCTORS */
	public Location(PointF point,String map){
		this.point = point;
		this.map = map;
		this.isclick=false;
	}
	public Location(PointF point,String map,String type, String info,String text){
		this(point,map);
		this.type = type;
		this.info = info;
		this.text=text;
	}
	
	public void setisclick(boolean k){
		isclick=k;
	}
	
}
