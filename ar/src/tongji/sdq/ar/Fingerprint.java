package tongji.sdq.ar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import android.graphics.PointF;

public class Fingerprint {
    int ID;
    String Map;
    PointF Location;
    HashMap<String, Integer> myCurrentMeasurements;

    /**
     * CONSTRUCTORS
     */
    public Fingerprint() {
    }

    public Fingerprint(HashMap<String, Integer> measurements) {
        this();
        myCurrentMeasurements = measurements;
    }

    public Fingerprint(int id, String map, PointF location, HashMap<String, Integer> measurements) {
        ID = id;
        Map = map;
        Location = location;
        myCurrentMeasurements = measurements;
    }

    /**
     * INSTANCE METHODS
     */

    public void setId(int id) {
        ID = id;
    }

    public int getId() {
        return ID;
    }

    public void setMap(String map) {
        Map = map;
    }

    public String getMap() {
        return Map;
    }

    public void setLocation(PointF location) {
        Location = location;
    }

    public void setLocation(float x, float y) {
        Location = new PointF(x, y);
    }

    public PointF getLocation() {
        return Location;
    }

    public void setMeasurements(HashMap<String, Integer> measurements) {
        myCurrentMeasurements = measurements;
    }

    public HashMap<String, Integer> getMeasurements() {
        return myCurrentMeasurements;
    }

    /**
     * calculates the (squared) euclidean distance to the given fingerprint
     */
    public float compare(Fingerprint fingerprint) {
        float result = 0f;

        HashMap<String, Integer> fingerprintMeasurements = fingerprint.getMeasurements();
        TreeSet<String> keys = new TreeSet<String>();
        keys.addAll(myCurrentMeasurements.keySet());
        keys.addAll(fingerprintMeasurements.keySet());

        for (String key : keys) {
            int value = 0;
            Integer fValue = fingerprintMeasurements.get(key);
            Integer mValue = myCurrentMeasurements.get(key);
            value = (fValue == null) ? -80 : (int) fValue;
            value -= (mValue == null) ? -80 : (int) mValue;
            result += value * value;
        }

        //result = FloatMath.sqrt(result); // squared euclidean distance is enough, this is not needed

        return result;
    }

    /**
     * compares the fingerprint to a set of fingerprints and returns the fingerprint with the smallest euclidean distance to it
     */
    public Fingerprint getClosestMatch(ArrayList<Fingerprint> fingerprints, PointF lastPosition) {
        //long time = System.currentTimeMillis();
        Fingerprint closest = null;
        float bestScore = -1;

        if (fingerprints != null) {
            for (Fingerprint fingerprint : fingerprints) {
//                if (!lastPosition.equals(0, 0)) {
//                    int distance2 = (int) ((fingerprint.Location.x - lastPosition.x) * (fingerprint.Location.x - lastPosition.x) + (fingerprint.Location.y - lastPosition.y) * (fingerprint.Location.y - lastPosition.y));
//                    if (distance2 > 150000)
//                        continue;
//                }
                float score = compare(fingerprint);
                if (bestScore == -1 || bestScore > score) {
                    bestScore = score;
                    closest = fingerprint;
                }
            }
        }

        //time = System.currentTimeMillis() - time;
        //Log.d("time", "\n\n\n\n\n\ncalculation location took " + time + " milliseconds.");
        return closest;
    }
}
