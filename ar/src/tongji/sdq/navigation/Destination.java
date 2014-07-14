package tongji.sdq.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Destination {
    private LinkedHashMap<String, Integer> destinationHashMap;
    private List<String> destinationList;
    private String[] destinationArray;

    public Destination() {
        destinationHashMap = new LinkedHashMap<String, Integer>();
        destinationHashMap.put("锟斤拷谴锟�", -1);
        destinationHashMap.put("锟斤拷锟斤拷锟斤拷", 12);
        destinationHashMap.put("锟较帮拷旃拷锟�", 10);
        destinationHashMap.put("锟斤拷锟斤拷旃拷锟�", 11);
        destinationHashMap.put("锟斤拷锟斤拷锟斤拷", 4);
        destinationHashMap.put("锟斤拷息锟斤拷", 3);
        destinationHashMap.put("锟斤拷台", 5);
        destinationHashMap.put("锟斤拷锟斤拷", 1);
    }

    public String[] getDestinationArray() {//锟斤拷取锟斤拷址锟叫憋拷
        destinationList = new ArrayList<String>();
        Iterator i = destinationHashMap.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            destinationList.add(entry.getKey().toString());
        }
        final int size = destinationList.size();
        destinationArray = (String[]) destinationList.toArray(new String[size]);
        return destinationArray;
    }

    public int getDestinationID(String destination) {//锟斤拷莸锟街凤拷锟斤拷取ID
        return destinationHashMap.get(destination);
    }
}
