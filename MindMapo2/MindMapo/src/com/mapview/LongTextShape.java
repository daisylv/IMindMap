package com.mapview;

import android.graphics.Color;
import android.graphics.Point;

import com.mapview.MyMapView.shapeEnum;

public class LongTextShape extends MyShape {

	public MyShape parentShape = null;
	public int isleft = 0;
	
	public LongTextShape(Point p, shapeEnum e) {
		super(p, e);
		// TODO Auto-generated constructor stub
		this.shape = e;
		this.theColor = Color.GRAY;
//		this.curwidth = 
	}

}
