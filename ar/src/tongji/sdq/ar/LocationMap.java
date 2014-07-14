package tongji.sdq.ar;

import java.util.ArrayList;

import android.graphics.PointF;

public class LocationMap {
	private ArrayList<Location> locations;
	
	/** CONSTRUCTORS */
	public LocationMap(){
		locations = new ArrayList<Location>();
		addLocations();
	}
	
	private void addLocations(){
		locations.add(new Location(new PointF(1652,468),"wmlab","toilet","卫生间","这只是一个普通的厕所，没什么好看的。"));
		locations.add(new Location(new PointF(1776,2222),"wmlab","meeting","实验室","宽带无线通信与多媒体研究室主要研究第四代宽带无线移动通信理论和技术、车联网通信、车联网智能感知，以及在汽车和交通中的应用，是同济大学“985工程”教育部重点实验室建设内容之一。"));
	}
	
	public ArrayList<Location> getLocations(){
		return locations;
	}
}
