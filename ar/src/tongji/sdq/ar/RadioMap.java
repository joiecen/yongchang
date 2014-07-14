/***Designed by ShiDanqing&YuTian
 ***Tongji University***/
package tongji.sdq.ar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.HashMap;

public class RadioMap {
    private ArrayList<Fingerprint> fingerprints;

    /**
     * CONSTRUCTORS
     */
    public RadioMap(SQLiteDatabase mdb) {
        fingerprints = new ArrayList<Fingerprint>();

        //addhotspots();
        addFingerprintsAndHotpots(mdb);
    }

    /**
     * WiFi FingerPrint & BlueTooth HotSpot *
     */
    public ArrayList<Fingerprint> getFingerprints() {
        return fingerprints;
    }

    private void addFingerprintsAndHotpots(SQLiteDatabase mdb) {
        Cursor cursor = mdb.query("wifi_Average_Data",
                new String[]{"Map", "X", "Y", "mac", "ave_rssi"},
                null, null, null, null, null);

        PointF location;
        PointF oldlocation;
        String map;
        String oldmap;

        if (cursor.moveToFirst()) {
            HashMap<String, Integer> measurement = new HashMap<String, Integer>();
            oldlocation = new PointF(cursor.getInt(1), cursor.getInt(2));
            oldmap = cursor.getString(0);

            //empty
//            fingerprints.add(new Fingerprint(0, "", new PointF(0, 0), measurement));

            int i = 1;

            do {
                location = new PointF(cursor.getInt(1), cursor.getInt(2));
                map = cursor.getString(0);
                String mac = cursor.getString(3);
                int rssi = cursor.getInt(4);

                /**detect if location was different**/
                boolean if_not_location_equal = !((oldlocation.x == location.x) && (oldlocation.y == location.y));
                boolean if_not_map_equal = !oldmap.equals(map);

                if (if_not_location_equal || if_not_map_equal || cursor.isLast()) {
                    HashMap<String, Integer> measurement_temp;
                    measurement_temp = measurement;
                    measurement = new HashMap<String, Integer>();    //change the measurement address
                    fingerprints.add(new Fingerprint(i, oldmap, oldlocation, measurement_temp));
                    i++;
                }

                measurement.put(mac, rssi);
                oldlocation = location;
                oldmap = map;

            } while (cursor.moveToNext());
            cursor.close();
        }

        /******F7*******/


        /*Cursor cursorA = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"A"}, null, null, null);
        PointF locationA = new PointF(139 * 2, 168 * 2);
        HashMap<String, Integer> measurementA = new HashMap<String, Integer>();
        if (cursorA.moveToFirst()) {
            do {
                measurementA.put(cursorA.getString(0), cursorA.getInt(1));
            } while (cursorA.moveToNext());
        }
        fingerprints.add(new Fingerprint(1, "f7", locationA, measurementA));

        Cursor cursorB = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"B"}, null, null, null);
        PointF locationB = new PointF(360 * 2, 168 * 2);
        HashMap<String, Integer> measurementB = new HashMap<String, Integer>();
        if (cursorB.moveToFirst()) {
            do {
                measurementB.put(cursorB.getString(0), cursorB.getInt(1));
            } while (cursorB.moveToNext());
        }
        fingerprints.add(new Fingerprint(2, "f7", locationB, measurementB));

        Cursor cursorC = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"C"}, null, null, null);
        PointF locationC = new PointF(812 * 2, 168 * 2);
        HashMap<String, Integer> measurementC = new HashMap<String, Integer>();
        if (cursorC.moveToFirst()) {
            do {
                measurementC.put(cursorC.getString(0), cursorC.getInt(1));
            } while (cursorC.moveToNext());
        }
        fingerprints.add(new Fingerprint(3, "f7", locationC, measurementC));

        Cursor cursorD = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"D"}, null, null, null);
        PointF locationD = new PointF(139 * 2, 506 * 2);
        HashMap<String, Integer> measurementD = new HashMap<String, Integer>();
        if (cursorD.moveToFirst()) {
            do {
                measurementD.put(cursorD.getString(0), cursorD.getInt(1));
            } while (cursorD.moveToNext());
        }
        fingerprints.add(new Fingerprint(4, "f7", locationD, measurementD));

        Cursor cursorE = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"E"}, null, null, null);
        PointF locationE = new PointF(360 * 2, 506 * 2);
        HashMap<String, Integer> measurementE = new HashMap<String, Integer>();
        if (cursorE.moveToFirst()) {
            do {
                measurementE.put(cursorE.getString(0), cursorE.getInt(1));
            } while (cursorE.moveToNext());
        }
        fingerprints.add(new Fingerprint(5, "f7", locationE, measurementE));

        Cursor cursorF = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"F"}, null, null, null);
        PointF locationF = new PointF(812 * 2, 506 * 2);
        HashMap<String, Integer> measurementF = new HashMap<String, Integer>();
        if (cursorF.moveToFirst()) {
            do {
                measurementF.put(cursorF.getString(0), cursorF.getInt(1));
            } while (cursorF.moveToNext());
        }
        fingerprints.add(new Fingerprint(6, "f7", locationF, measurementF));

        Cursor cursorG = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"G"}, null, null, null);
        PointF locationG = new PointF(139 * 2, 976 * 2);
        HashMap<String, Integer> measurementG = new HashMap<String, Integer>();
        if (cursorG.moveToFirst()) {
            do {
                measurementG.put(cursorG.getString(0), cursorG.getInt(1));
            } while (cursorG.moveToNext());
        }
        fingerprints.add(new Fingerprint(7, "f7", locationG, measurementG));

        Cursor cursorH = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"H"}, null, null, null);
        PointF locationH = new PointF(812 * 2, 976 * 2);
        HashMap<String, Integer> measurementH = new HashMap<String, Integer>();
        if (cursorH.moveToFirst()) {
            do {
                measurementH.put(cursorH.getString(0), cursorH.getInt(1));
            } while (cursorH.moveToNext());
        }
        fingerprints.add(new Fingerprint(8, "f7", locationH, measurementH));

        *//******F1******//*
        Cursor cursor1A = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"1A"}, null, null, null);
        PointF location1A = new PointF(609 * 2, 757 * 2);
        HashMap<String, Integer> measurement1A = new HashMap<String, Integer>();
        if (cursor1A.moveToFirst()) {
            do {
                measurement1A.put(cursor1A.getString(0), cursor1A.getInt(1));
            } while (cursor1A.moveToNext());
        }
        fingerprints.add(new Fingerprint(11, "f1", location1A, measurement1A));

        Cursor cursor1B = mdb.query("wifi_Average_Data",
                new String[]{"mac", "ave_rssi"},
                "No = ?", new String[]{"1B"}, null, null, null);
        PointF location1B = new PointF(862 * 2, 503 * 2);
        HashMap<String, Integer> measurement1B = new HashMap<String, Integer>();
        if (cursor1B.moveToFirst()) {
            do {
                measurement1B.put(cursor1B.getString(0), cursor1B.getInt(1));
            } while (cursor1B.moveToNext());
        }
        fingerprints.add(new Fingerprint(12, "f1", location1B, measurement1B));*/

    }
}
