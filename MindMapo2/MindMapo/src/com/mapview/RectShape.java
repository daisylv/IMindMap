package com.mapview;

import android.graphics.Color;
import android.graphics.Point;
import com.mapview.MyMapView.shapeEnum;

public class RectShape extends MyShape {
	
	public RectShape(Point p, shapeEnum e) {
		super(p, e);
		
		
		shape = e;
		
		theColor = Color.BLUE;
		// TODO Auto-generated constructor stub
	}

}

