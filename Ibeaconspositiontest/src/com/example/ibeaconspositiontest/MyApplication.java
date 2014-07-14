package com.example.ibeaconspositiontest;

import com.example.ibeaconspositiontest.Position;

import android.R.integer;
import android.app.Application;
import android.graphics.PointF;
import android.util.Log;

public class MyApplication extends Application {
	/**
	 * map**
	 */
	private float  x;
	private float y;
	private String displayMapName = new String();

	public void setDisplayMapName(String name) {
		this.displayMapName = name;
	}

	public String getDisplayMapName() {
		return displayMapName;
	}

	/**
	 * position**
	 */
	private Position mPosition = new Position();

	public Position getPosition() {
		Log.i("get", "" + mPosition.position.x + ":" + mPosition.position.y);// correct
		return mPosition;
	}

	public void setPosition(float x, float y) {
		this.mPosition.position.x = x;
		this.mPosition.position.y = y;
	}

	// public void setPosition(int id, String map, float x, float y) {
	public void setPosition(int id, float x, float y) {
		this.mPosition.PositionID = id;
		// this.mPosition.Map = map;
		this.mPosition.position.x = x;
		this.mPosition.position.y = y;
		Log.i("set", "" + mPosition.position.x + ":" + mPosition.position.y);

	}

	/**
	 * position mode on or off**
	 */
	private boolean POSITION_MODE;

	public boolean positionModeOnOrOff() {
		return POSITION_MODE;
	}

	public void setPositionMode(boolean POSITION_MODE) {
		this.POSITION_MODE = POSITION_MODE;
	}

	/**
	 * mark mode on or off**
	 */
	private boolean MARK_MODE;

	public boolean markModeOnOrOff() {
		return MARK_MODE;
	}

	public void setMarkMode(boolean MARK_MODE) {
		this.MARK_MODE = MARK_MODE;
	}

	/**
	 * mark point**
	 */
	private Position markPosition;

	public int getMarkID() {
		return markPosition.PositionID;
	}

	public Position getMarkPosition() {
		return markPosition;
	}

	public void setMark(Position mark) {
		this.markPosition = mark;
	}

//	public float getX() {
//		return x;
//	}
//
//	public void setX(float x) {
//		this.x = x;
//	}
//
//	public float getY() {
//		Log.i("x", "" + y);
//		return y;
//
//	}
//
//	public void setY(float y) {
//		this.y = y;
//		Log.i("seti", "y"+y);
//
//	}
}

class Position {
	int PositionID;
	// String Map = new String();
	PointF position = new PointF(0,0);
}