package com.mapview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

public class MyShape {
	
	public LongTextShape manageShape=null;
	public List<LongTextShape> LongShapeList = new ArrayList<LongTextShape>();
	public Point sp;
	public MyMapView.shapeEnum shape;
	public int theColor;
	public RectF r;
	public boolean editstate =false;
	public float portion = 200;
	public float Tscale = 1;
	public float curwidth = 120;
	public float curheight = 80;
	public String text = null;
	public Point tp = new Point();//for draw text
	public float tsize = 0;
	public int tColor = Color.BLACK;
	
	public MyShape(Point p, MyMapView.shapeEnum e) {
		// TODO Auto-generated constructor stub
		r=new RectF(p.x-60, p.y-40, p.x+60, p.y+40);
		
		this.sp = p;
	}
	
	public void refresh()
	{
		r.left = (float) (sp.x - curwidth*0.5);
		r.top = (float) (sp.y - curheight*0.5);
		r.right = (float) (sp.x + curwidth*0.5);
		r.bottom = (float) (sp.y + curheight*0.5);
	}
	//单
	
	public void resize(float scale) {
			//r.left -= scale*20;
			//r.top -= scale*20;
			//r.right += scale*20;
			//r.bottom += scale*20;
			//Log.d("event", "Enter");
			
			//放缩公式  下面这个不行 上面这个可以  很奇怪
			if(scale>1)
			{
				
			r.left -= (scale-1)*curwidth*0.5;
			r.top -= (scale-1)*curheight*0.5;
			r.right += (scale-1)*curwidth*0.5;
			r.bottom += (scale-1)*curheight*0.5;
			curwidth=scale*curwidth;
			curheight=scale*curheight;
			portion = portion * scale;
			Tscale = scale;
			Log.d("event", "Enter");
			}
			
	         if(scale <1)
			{
				r.left += (1-scale)*curwidth*0.5;
				r.top += (1-scale)*curheight*0.5;
				r.right -= (1-scale)*curwidth*0.5;
				r.bottom -= (1-scale)*curheight*0.5;
				curwidth=scale*curwidth;
				curheight=scale*curheight;
				portion = portion * scale;
				Tscale = scale;
				Log.d("event", "Enter2");
			}
	}
	         
	         //单
	public Point getTPoint(Paint paint)
	{
		int tlen = text.length();
		if(tlen == 1)
			tsize = (float)(curwidth*0.45)/tlen;
		else tsize = (float)(curwidth*0.9)/tlen;
		tp.x = sp.x;
		tp.y = (int) (sp.y+tsize*0.25);
		paint.setTextSize(tsize);
		paint.setColor(tColor);
		paint.setTextAlign(Align.CENTER);
		return tp;
	}
}

