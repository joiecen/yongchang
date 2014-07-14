package tongji.sdq.ar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import tongji.sdq.navigation.Destination;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {

	private SensorManager mSensorManager;
	private Sensor orientationSensor;
	private AugmentedRealityView ARView;
	
	private double mapOrientationOffset = 240;
	
/**********************************************/
	/**
     * STATE**
     */
    private static final boolean POSITION_STATE_OFF = false;
    private static final boolean POSITION_STATE_ON = true;
    private boolean POSITION_MODE = POSITION_STATE_ON;
    
    private boolean MAP_MODE = false;

    /**
     * Tabs**
     */
    private TabSpec myTabSpec1, myTabSpec2, myTabSpec3;
    private TabHost myTabHost;


    /**
     * to refresh UI by subThread**
     */
    private MapView myMap;
    private InfoView myInfo;
    private MyHandler myHandler = new MyHandler();

    /**
     * HTTP**
     */
    private String postUrl = Constant.ipsServerUrl + Constant.rssiPostUrl;
    HashMap<String, String> postMap;
    private String receiveString;

    /**
     * application&radio map**
     */
    private MainApplication myApplication;
    private RadioMap radioMap;

    /**
     * WiFi**
     */
    private WifiManager wifiManager;
    private List<ScanResult> scanResults;
    private StringBuilder scanBuilder = new StringBuilder();
    private String[] ssidArray = {"tongji1403", "AP1", "AP2", "AP3", "AP5"};
    List<String> ssidList = Arrays.asList(ssidArray);//convert to list
    private HashMap<String, Integer> wifiSignalCurrent = new HashMap<String, Integer>(); //current
    private HashMap<String, Integer> wifiSignalAfterFilter = new HashMap<String, Integer>(); //after filter


    /**
     * to simulate the movement**
     */
    private int movementCount = 5;
    private PointF lastPosition = new PointF(0, 0);
    private String lastMap = "jiading_ceie_7";
    private PointF currentPosition = new PointF(0, 0);
    private String currentMap = "jiading_ceie_7";

    /**
     * destination**
     */
    private Destination myDestination;

    /**
     * database import**
     */
    private final String DATABASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data";
    private final String DATABASE_NAME = "database_arDemo.db";

    private SQLiteDatabase openDatabase() {
        try {
            String dbFullPath = DATABASE_PATH + "/" + DATABASE_NAME;
            File dbDir = new File(DATABASE_PATH);
            if (!dbDir.exists()) {
                if (dbDir.mkdirs())
                    Log.d("db is create", "ok");
                Log.d("db is create", "No");
            }
            if (!new File(dbFullPath).exists()) {
                InputStream dbInputStream = getResources().openRawResource(R.raw.database);
                FileOutputStream dbOutputStream = new FileOutputStream(dbFullPath);
                byte[] buffer = new byte[8192];
                int count;
                while ((count = dbInputStream.read(buffer)) > 0) {
                    dbOutputStream.write(buffer, 0, count);
                }
                dbOutputStream.close();
                dbInputStream.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbFullPath, null);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }
/**************************************************/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/******************/
		myApplication = (MainApplication) getApplication();
        //Initialize the radio map
        radioMap = new RadioMap(openDatabase());
        //Initialize the destinations
        myDestination = new Destination();

        //find view by id
        myMap = (MapView) findViewById(R.id.myMap);
        
        myMap.setVisibility(View.INVISIBLE);

        //turn on WiFi
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        //use WiFi to set the position
        detectPositionThread.start();

        //the data ready to send
        postMap = new HashMap<String, String>();
        
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        POSITION_MODE = POSITION_STATE_ON;
        
        myApplication.setPositionMode(POSITION_MODE);//open or close

        
        /**********************/
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		orientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener(mListener, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
		ARView = (AugmentedRealityView) findViewById(R.id.augmentedRealityView);
		ARView.setMapOrientationOffset(120);
		ARView.setMyIndoorLocation(new PointF(0,0), "wmlab");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    /**
     * UI**
     */
    class MyHandler extends Handler {
        // receive message
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            // refresh UI
            Bundle receiveBundle = msg.getData();
            receiveString = receiveBundle.getString("map");
            if (receiveString.equals("jiading_ceie_1")) {
                myMap.setImageResource(R.drawable.jiading_ceie_1);
            } else if (receiveString.equals("jiading_ceie_2")) {
                myMap.setImageResource(R.drawable.jiading_ceie_2);
            } else if (receiveString.equals("jiading_ceie_3")) {
                myMap.setImageResource(R.drawable.jiading_ceie_4);
            } else if (receiveString.equals("jiading_ceie_4")) {
                myMap.setImageResource(R.drawable.jiading_ceie_4);
            } else if (receiveString.equals("jiading_ceie_5")) {
                myMap.setImageResource(R.drawable.jiading_ceie_5);
            } else if (receiveString.equals("jiading_ceie_6")) {
                myMap.setImageResource(R.drawable.jiading_ceie_6);
            } else if (receiveString.equals("jiading_ceie_7")) {
                myMap.setImageResource(R.drawable.jiading_ceie_7);
            }


//            super.handleMessage(msg);
//            // refresh UI
//            Bundle receiveBundle = msg.getData();
//            receiveString = receiveBundle.getString("receiveString");
//            
//            // consume an optional byte order mark (BOM) if it exists
//            if (receiveString != null && receiveString.startsWith("\ufeff")) {
//            	receiveString = receiveString.substring(1);
//        	}
//            
//			JSONObject receiveJsonObject = null;
//            try {
//            	receiveJsonObject = new JSONObject(receiveString);
//				JSONObject jsonPosition = receiveJsonObject.getJSONObject("position");
//				myApplication.setPosition(jsonPosition.getInt("id"),jsonPosition.getInt("x"),jsonPosition.getInt("y"));//position
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
        }
    }

    /**
     * WiFi thread to position**
     */
    Thread detectPositionThread = new Thread(new Runnable() {
        public void run() {
            while (true) {
                if (POSITION_MODE) {
                    if (movementCount < 10) {
                        float dx = (currentPosition.x - lastPosition.x) / 10;
                        float dy = (currentPosition.y - lastPosition.y) / 10;
                        myApplication.setPosition(lastPosition.x + dx * movementCount, lastPosition.y + dy * movementCount);
                        movementCount++;
                    } else {
                        position();
                        movementCount = 1;
                    }

                    myHandler.post(myRefreshMap);
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    
    /**
     * position method**
     */
    private void position() {
        /***WiFi scan***/
        wifiManager.startScan();
        scanResults = wifiManager.getScanResults();//锟斤拷锟斤拷锟斤拷锟斤拷锟借备锟叫憋拷

        wifiSignalCurrent.clear();
        for (ScanResult scanResult : scanResults) {
            //if (ssidList.contains(scanResult.SSID)) {
            wifiSignalCurrent.put(scanResult.BSSID, scanResult.level);
            //}
        }

        //MARKOV
        TreeSet<String> keys = new TreeSet<String>();
        keys.addAll(wifiSignalCurrent.keySet());
        keys.addAll(wifiSignalAfterFilter.keySet());
        for (String key : keys) {
            Integer value = wifiSignalCurrent.get(key);
            Integer oldValue = wifiSignalAfterFilter.get(key);
            if (oldValue == null) {
                wifiSignalAfterFilter.put(key, value);
            } else if (value == null) {
                wifiSignalAfterFilter.remove(key);
            } else {
                value = (int) (oldValue * 0f + value * 1f);
                wifiSignalAfterFilter.put(key, value);
            }
        }

        Fingerprint currentFingerprint = new Fingerprint(wifiSignalAfterFilter);
        Fingerprint closestMatch = currentFingerprint.getClosestMatch(radioMap.getFingerprints(), lastPosition);

        Iterator iter = wifiSignalAfterFilter.entrySet().iterator();
        scanBuilder.delete(0, scanBuilder.length());//锟斤拷锟�
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            scanBuilder.append(entry.getKey() + "," + entry.getValue() + ";");
        }
        //Log.i("rssi", scanBuilder.toString());

        /***HTTP***/
//		postMap.clear();
//        postMap.put("test", scanBuilder.toString());
//		if(NAVIGATION_MODE){
//			postMap.put("navigation", "1");
//		}
//		MapActivity.this.myHandler.sendMessage(HttpMethod.postHttpProcess(postUrl,postMap));

        ARView.setMyIndoorLocation(currentPosition, "wmlab");
        
        lastPosition = currentPosition;
        lastMap = currentMap;

        if (closestMatch.ID != 0) {
            currentPosition = closestMatch.Location;
            currentMap = closestMatch.Map;
        }

        changeMap(lastMap);

        myApplication.setPosition(closestMatch.ID, lastMap, lastPosition.x, lastPosition.y);
        myApplication.setInfoUrl("file:///android_asset/" + closestMatch.ID + ".html");
    }
    
    private void changeMap(String map) {
        Message msg = new Message();
        Bundle sendBundle = new Bundle();// 存放数据
        sendBundle.putString("map", map);
        msg.setData(sendBundle);
        MainActivity.this.myHandler.sendMessage(msg);
    }

    /**
     * refresh UI**
     */
    private Runnable myRefreshMap = new Runnable() {
        public void run() {
            myMap.invalidate(); // redraws the map screen
        }
    };
    
	private final SensorEventListener mListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent event) {
			if (ARView != null) {
				ARView.setCompass(event.values[0]);
				ARView.setPitch(event.values[1]);
				ARView.invalidate();
			}
			
			if(MAP_MODE){
				if(event.values[1]>40||event.values[1]<-40){
					MAP_MODE = false;
					myMap.setVisibility(View.INVISIBLE);
				}
			}else{
				if(event.values[1]<30&&event.values[1]>-30){
					MAP_MODE = true;
					myMap.setVisibility(View.VISIBLE);
				}
			}
			
			PositionView.setOrientation((float)(event.values[0] + mapOrientationOffset));
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();

		mSensorManager.registerListener(mListener, orientationSensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop() {
		mSensorManager.unregisterListener(mListener);
		super.onStop();
	}

}
