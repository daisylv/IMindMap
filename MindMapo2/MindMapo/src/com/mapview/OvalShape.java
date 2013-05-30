package com.mapview;

import android.graphics.Color;
import android.graphics.Point;
import com.mapview.MyMapView.shapeEnum;

public class OvalShape extends MyShape {

	
	
	public OvalShape(Point p, shapeEnum e) {
		super(p, e);
		shape = e;
		
		theColor = Color.GREEN;
	}
}

