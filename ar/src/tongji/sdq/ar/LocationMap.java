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
		locations.add(new Location(new PointF(1652,468),"wmlab","toilet","������","��ֻ��һ����ͨ�Ĳ�����ûʲô�ÿ��ġ�"));
		locations.add(new Location(new PointF(1776,2222),"wmlab","meeting","ʵ����","�������ͨ�����ý���о�����Ҫ�о����Ĵ���������ƶ�ͨ�����ۺͼ�����������ͨ�š����������ܸ�֪���Լ��������ͽ�ͨ�е�Ӧ�ã���ͬ�ô�ѧ��985���̡��������ص�ʵ���ҽ�������֮һ��"));
	}
	
	public ArrayList<Location> getLocations(){
		return locations;
	}
}
