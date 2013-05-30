package com.createmap;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Paint.Style;

public class Beziercurve {

	public int startX, startY, controlX, controlY, endX, endY;
	public Path path;
	public Paint paint;
	public int parent = -1;//表示连线一端的图形，总是设定先选择parent图形
	public int child = -1;//表示连线的另一端的图形
	
	public Beziercurve(Point start, Point end) {
		// TODO Auto-generated constructor stub
		this.startX = start.x;
		this.startY = start.y;
		this.endX = end.x;
		this.endY = end.y;
		
		controlX = startX;
		controlY = endY;
		
		Setcurve();
	}
	
	public void Setcurve(){
		
		path = new Path();
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(Color.RED);
		
		path.reset();
		path.moveTo(startX, startY);
		path.quadTo(controlX, controlY, endX, endY);
		//canvas.drawPath(path, paint);
	}

	public void refresh(Point pp, Point pc) {
		// TODO Auto-generated method stub
		this.startX = pp.x;
		this.startY = pp.y;
		this.endX = pc.x;
		this.endY = pc.y;
		controlX = startX;
		controlY = endY;
		
		Setcurve();
	}


}

