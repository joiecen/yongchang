package com.example.ibeaconspositiontest;

/*
 *ibeacon uuid: d26d197e-4a1c-44ae-b504-dd7768870564
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.os.Bundle;
import android.os.RemoteException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager.OnDismissListener;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.ibeaconspositiontest.MainActivity;
import com.example.ibeaconspositiontest.R;
import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class MainActivity extends Activity implements IBeaconConsumer {

	protected static final String TAG = "MainActivity";
	private IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);

	private ArrayList<Ibeaconsinfoandloc> iblocations = new ArrayList<Ibeaconsinfoandloc>();

	private MapView mymap;
	private MyApplication myApplication;
	private PointF locatPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		verifyBluetooth();
		iBeaconManager.bind(this);
//		myApplication = (MyApplication) getApplication();
//		myApplication.setPositionMode(true);
//		mymap = (MapView) findViewById(R.id.mymap);		
//		myApplication.setPositionMode(true);
//		mymap.setVisibility(View.VISIBLE);
//		myApplication.setPosition(1, 300, 600);
		setapplicationThread.start();
		//Log.i("iblocat",""+iblocations.get(0).ibPointF.x);

	}

//	 private Runnable myRefreshMap = new Runnable() {
//	 public void run() {
//	 mymap.invalidate(); // redraws the map screen
//	 }
//	 };

	@SuppressLint("NewApi")
	private void verifyBluetooth() {
		// TODO Auto-generated method stub
		IBeaconManager.getInstanceForApplication(this).checkAvailability();
		try {
			if (!IBeaconManager.getInstanceForApplication(this)
					.checkAvailability()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						this);
				builder.setTitle("Bluetooth is not enabled");
				builder.setMessage("Please enable bluetooth in settings and restart this application.");
				builder.setPositiveButton(android.R.string.ok, null);
				builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub

					}
				});
				builder.show();
			}
		} catch (RuntimeException e) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Bluetooth LE not available");
			builder.setMessage("Sorry, this device does not support Bluetooth LE.");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					// finish();
					// System.exit(0);
				}

			});
			builder.show();
		}

	}

	public void onIBeaconServiceConnect() {
		// TODO Auto-generated method stub
		iBeaconManager.setRangeNotifier(new RangeNotifier() {

			@Override
			public void didRangeBeaconsInRegion(Collection<IBeacon> arg0,
					Region arg1) {
				// TODO Auto-generated method stub
				Iterator<IBeacon> iterator = arg0.iterator();
				
				//Log.i("ibsv", "iiibbb");测试是否运行到此部分 YES
				
				while (iterator.hasNext()) {
					IBeacon iBeacon = (IBeacon) iterator.next();
					int rssi = iBeacon.getRssi();
					int major = iBeacon.getMajor();
					int minor = iBeacon.getMinor();
					int proximity = iBeacon.getProximity();
					
					//测试能否传入oncreat函数     NO!!!
					iblocations.add(new Ibeaconsinfoandloc("testmap1",
							rssi, new PointF(50, 100), "试验坐标1", major,
							minor, proximity));
					Log.i("iblocat",""+iblocations.get(0).ibPointF.x);
					logToDisplay("success soon!!!");
					locatPoint=new PointF(100,600);
			//		setapplicationThread.start();            NO!!!
			//		myApplication.setPosition(1, 200, 400);
					
					/* 用case为每个ibeacon分配坐标、信息等 */
//					if (major == 1) 
//					{
//						switch (minor) {
//						case 1:
//							iblocations.add(new Ibeaconsinfoandloc("testmap1",
//									rssi, new PointF(50, 100), "试验坐标1", major,
//									minor, proximity));
//							break;
//						case 2:
//							iblocations.add(new Ibeaconsinfoandloc("testmap1",
//									rssi, new PointF(50, 200), "试验坐标2", major,
//									minor, proximity));
//							break;
//						}
//					} else if (major == 2) {
//						switch (minor) {
//						case 1:
//							iblocations.add(new Ibeaconsinfoandloc("testmap1",
//									rssi, new PointF(100, 100), "试验坐标3", major,
//									minor, proximity));
//							break;
//						case 2:
//							iblocations.add(new Ibeaconsinfoandloc("testmap1",
//									rssi, new PointF(100, 200), "试验坐标4", major,
//									minor, proximity));
//							break;
//						}
//					}

				}
				/** 按信号强度排序 */
//				int ibnum = iblocations.size();
//				PointF locatPoint = new PointF(0, 0);// 信号强度最大的ibeacon的定位坐标
//				for (int i = 0; i < ibnum - 1; i++) {
//					Ibeaconsinfoandloc detectedib = iblocations.get(i);
//					// PointF pointa = detectedib.ibPointF;
//					// filter the UNKNOWN ones
//					if (detectedib.ibproximity != IBeacon.PROXIMITY_UNKNOWN) {
//						// ranking
//						for (int j = i + 1; j < ibnum; j++) {
//							Ibeaconsinfoandloc detectedibnext = iblocations
//									.get(j);
//							// PointF pointb = detectedibnext.ibPointF;
//							if (detectedib.ibrssi < detectedibnext.ibrssi) {
//								locatPoint = detectedibnext.ibPointF;
//							} else {
//								locatPoint = detectedib.ibPointF;
//							}
//						}
//					} else {
//						continue;
//					}
//
//				}
				//myApplication.setPosition(1, 100, 100);
				
//				myApplication.setX(100);
//				myApplication.setY(100);
			}

		});

		try {
			iBeaconManager.startRangingBeaconsInRegion(new Region(
					"myMonitoringUniqueId",
					"d26d197e-4a1c-44ae-b504-dd7768870564", null, null));
		} catch (RemoteException e) {
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		iBeaconManager.unBind(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (iBeaconManager.isBound(this))
			iBeaconManager.setBackgroundMode(this, true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (iBeaconManager.isBound(this))
			iBeaconManager.setBackgroundMode(this, false);
	}
	
    //测试能否跳转到此线程  YES!  BUT!application get wrong
	private void logToDisplay(final String line)
	{
		//myApplication.setPosition(1, 200, 400);           no use
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				TextView textView = (TextView) MainActivity.this.findViewById(R.id.textView1);
				// textView.append(line + "\n\n");
				textView.setText(line);
				//myApplication.setPosition(1, 200, 400);      no use
				Log.i("iblocattwo",""+iblocations.get(0).ibPointF.x);
				//setapplicationThread.start();     NO !!!
			}
		});
	}
	
	Thread setapplicationThread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub			
			
			//Log.i("iblocatthree",""+locatPoint.x);
			myApplication = (MyApplication) getApplication();
			myApplication.setPositionMode(true);
			mymap = (MapView) findViewById(R.id.mymap);		
			myApplication.setPositionMode(true);
			mymap.setVisibility(View.VISIBLE);
			myApplication.setPosition(1, 100, 600);
			try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	});

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

}
