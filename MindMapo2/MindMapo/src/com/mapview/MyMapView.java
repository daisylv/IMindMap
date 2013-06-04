package com.mapview;

import java.util.ArrayList;
import java.util.List;

import com.createmap.Beziercurve;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

public class MyMapView extends View {
	
	private static List<MyShape> shapelist = new ArrayList<MyShape>();//维护一个全局list，存放已画的图形
	private static List<Beziercurve>curvelist = new ArrayList<Beziercurve>();//维护一个存放路径的List
	public static enum shapeEnum{iOval, iRect, RLong, LLong};//四种图形选项
	public int curEdit = -1;
	public float canvasscale=1;//画布的比例 初始为1
	public Point CanvasP=new Point(0,0); //画布的原点 初始为（0,0）
	public Point PrePoint;
   	//public Point PrePoint;
	public Point CurPoint;

	public boolean ismoveCan = false; //判断能否移动画布 初始为true
	//public boolean editstate = false;
	public MyMapView(Context context) {
		super(context);
		
//		Point p1 = new Point(100,200);
	/*	Point p2 = new Point(200,400);//测试用数据
		shapelist.add(new RectShape(p1, shapeEnum.iRect));
		shapelist.add(new RectShape(p2, shapeEnum.iRect));
		shapelist.add(new OvalShape(p2, shapeEnum.iOval));*/
//		shapelist.add(new RectShape(p1, shapeEnum.iRect));
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint mPaint = new Paint();
	    mPaint.setAntiAlias(true);//无锯齿
	    mPaint.setStyle(Paint.Style.STROKE);//空心设置MyShape curShape =  shapelist.get(i);
	    mPaint.setStrokeWidth((float) 2.5);
	    

		Paint linePaint = new Paint();
		linePaint.setColor(Color.DKGRAY);
		linePaint.setAntiAlias(true);
		linePaint.setStyle(Style.STROKE);
	    
	    //放大缩小画布
	    if(canvasscale>2)
	    {
	    	canvas.scale(2, 2,this.getWidth()/2,this.getHeight()/2);
	    }
	    
	    else if(canvasscale<=2&&canvasscale>=1)
	    {
	    	//this.setm
	    	//this.setVerticalScrollBarEnabled(true);
	    	//this.setScrollbarFadingEnabled(false);
	    	canvas.scale(canvasscale, canvasscale,this.getWidth()/2,this.getHeight()/2);
	    }
	    
	    //判断能否移动 
	    if(ismoveCan)
	    {
	    	Point temP = new Point(CurPoint.x+CanvasP.x-PrePoint.x, CurPoint.y+CanvasP.y-PrePoint.y); 
	        canvas.translate(temP.x,temP.y);

	    //canvas.translate(CanvasP.x/2, CanvasP.y/2);
	    }
//	    mPaint.setColor(Color.RED);
	   // RectF rect = new RectF(50, 100, 200, 180);
	    //canvas.drawRoundRect(rect, 10, 10, mPaint);
	    //Rect rect = new 
	    //初步想的是可以维护一个list存放要画的图形，每次重绘都画一遍，然后每次改变的都是指定的某个图形，其他没变的就
	    //还是跟以前一样……
	    //for循环依次画出
//	    for()
//	    for(int i = 0; i < shapelist.size(); ++i)
//	    	drawShape(canvas, mPaint);
	    
	    //将图画出来……其实这样写不太好，每次添加新的都要改
	    for(int i = 0; i < shapelist.size(); ++i)
		{
	    	Paint tPaint = new Paint();
	    	tPaint.setColor(Color.BLACK);
			
			
	    	Path longTextpath = new Path();
	    	longTextpath.reset();
			MyShape curShape =  shapelist.get(i);
			if(curShape.shape == shapeEnum.iOval)
			{
				
				mPaint.setColor(curShape.theColor);
				canvas.drawOval(((OvalShape)curShape).r, mPaint);
				if(curShape.text != null)
				{
					//canvas.drawt
					
					canvas.drawText(curShape.text, curShape.getTPoint(tPaint).x, curShape.getTPoint(tPaint).y, tPaint);
				}
				Log.d("event", "true0");
				
			
				//判断是否选中 并画虚线
				if(((OvalShape)curShape).editstate)
				{
					 Log.d("event", "true");
					super.onDraw(canvas);
					Paint BoundPaint = new Paint();
					BoundPaint.setColor(Color.GRAY);
					//canvas.drawOval(((OvalShape)curShape).r, BoundPaint);
					//mPaint.setColor(curShape.theColor);
					BoundPaint.setStyle(Paint.Style.STROKE);
					//Log.d("event", "true2");
					PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);   
					BoundPaint.setPathEffect(effects);  

					canvas.drawRect(((OvalShape)curShape).r, BoundPaint);
					
					
					//Log.d("event", "true3");
					//mPaint.setColor(curShape.theColor);
					//canvas.drawOval(((OvalShape)curShape).r, mPaint);
				}
				
				if(((OvalShape)curShape).LongShapeList.size()>0)
					
				{

					int pos = ((OvalShape)curShape).LongShapeList.get(0).isleft;
					int size = ((OvalShape)curShape).LongShapeList.size();
					if(size>0)
					{
						mPaint.setColor(Color.BLUE);
						canvas.drawLine(((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth/2), ((OvalShape)curShape).sp.y, ((OvalShape)curShape).sp.x+2*pos*(((OvalShape)curShape).curwidth/3), ((OvalShape)curShape).sp.y, mPaint);
						if(size==1)
						{
							if(((OvalShape)curShape).LongShapeList.get(0).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								canvas.drawLine(((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+5) , ((OvalShape)curShape).sp.y, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth + 15), ((OvalShape)curShape).sp.y, linePaint);
								if(pos < 0)
									canvas.drawText(((OvalShape)curShape).LongShapeList.get(0).text, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth+25+((OvalShape)curShape).LongShapeList.get(0).text.length()*15),((OvalShape)curShape).sp.y, tPaint);
								
								else canvas.drawText(((OvalShape)curShape).LongShapeList.get(0).text, ((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+15),((OvalShape)curShape).sp.y, tPaint);
							}
						}
						if(size==2)
						{
						//mPaint.setColor(Color.BLUE);
							//canvas.drawLine(((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth/2, ((OvalShape)curShape).sp.y, ((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth, ((OvalShape)curShape).sp.y, mPaint);
							if(((OvalShape)curShape).LongShapeList.get(0).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((OvalShape)curShape).sp.x + 2*pos*(((OvalShape)curShape).curwidth/3), ((OvalShape)curShape).sp.y);
								longTextpath.quadTo(((OvalShape)curShape).sp.x + pos*(2*((OvalShape)curShape).curwidth/3+5), ((OvalShape)curShape).sp.y-30, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth + 15), ((OvalShape)curShape).sp.y-30);
								canvas.drawPath(longTextpath, linePaint);
								if(pos < 0)
									canvas.drawText(((OvalShape)curShape).LongShapeList.get(0).text, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth+25 + ((OvalShape)curShape).LongShapeList.get(0).text.length()*15),((OvalShape)curShape).sp.y-30, tPaint);		
								
								else canvas.drawText(((OvalShape)curShape).LongShapeList.get(0).text, ((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+15),((OvalShape)curShape).sp.y-30, tPaint);
							
								}
					
							if(((OvalShape)curShape).LongShapeList.get(1).text!=null)//canvas.drawt
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((OvalShape)curShape).sp.x + 2*pos*(((OvalShape)curShape).curwidth/3), ((OvalShape)curShape).sp.y);
								  longTextpath.quadTo(((OvalShape)curShape).sp.x + pos*(2*((OvalShape)curShape).curwidth/3+5), ((OvalShape)curShape).sp.y+30, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth + 15), ((OvalShape)curShape).sp.y+30);
								  canvas.drawPath(longTextpath, linePaint);
								  if(pos < 0)
								  canvas.drawText(((OvalShape)curShape).LongShapeList.get(1).text, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth+25+((OvalShape)curShape).LongShapeList.get(0).text.length()*15),((OvalShape)curShape).sp.y+30, tPaint);		
									
								  else canvas.drawText(((OvalShape)curShape).LongShapeList.get(1).text, ((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+15),((OvalShape)curShape).sp.y+30, tPaint);		
							}
								 //canvas.drawText
							
						
					
						}
						if(size==3)
						{
							
							if(((OvalShape)curShape).LongShapeList.get(0).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((OvalShape)curShape).sp.x + 2*pos*(((OvalShape)curShape).curwidth/3), ((OvalShape)curShape).sp.y);
								longTextpath.quadTo(((OvalShape)curShape).sp.x + pos*(2*((OvalShape)curShape).curwidth/3+5), ((OvalShape)curShape).sp.y-30, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth + 15), ((OvalShape)curShape).sp.y-30);
								canvas.drawPath(longTextpath, linePaint);
								if(pos < 0)
									canvas.drawText(((OvalShape)curShape).LongShapeList.get(0).text, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth+25+((OvalShape)curShape).LongShapeList.get(0).text.length()*15),((OvalShape)curShape).sp.y-40, tPaint);
								else canvas.drawText(((OvalShape)curShape).LongShapeList.get(0).text, ((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+15),((OvalShape)curShape).sp.y-40, tPaint);
							}
					
							if(((OvalShape)curShape).LongShapeList.get(1).text!=null)
							{
						
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								canvas.drawLine(((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+5) , ((OvalShape)curShape).sp.y, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth + 15), ((OvalShape)curShape).sp.y, linePaint);
								if(pos < 0)
									canvas.drawText(((OvalShape)curShape).LongShapeList.get(1).text, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth+25+((OvalShape)curShape).LongShapeList.get(0).text.length()*15),((OvalShape)curShape).sp.y, tPaint);
								
								else canvas.drawText(((OvalShape)curShape).LongShapeList.get(1).text, ((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+15),((OvalShape)curShape).sp.y, tPaint);
							}
						
					
							if(((OvalShape)curShape).LongShapeList.get(2).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((OvalShape)curShape).sp.x + 2*pos*(((OvalShape)curShape).curwidth/3), ((OvalShape)curShape).sp.y);
								  longTextpath.quadTo(((OvalShape)curShape).sp.x + pos*(2*((OvalShape)curShape).curwidth/3+5), ((OvalShape)curShape).sp.y+30, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth + 15), ((OvalShape)curShape).sp.y+30);
								  canvas.drawPath(longTextpath, linePaint);
								  if(pos < 0)
								  	canvas.drawText(((OvalShape)curShape).LongShapeList.get(2).text, ((OvalShape)curShape).sp.x+pos*(((OvalShape)curShape).curwidth+25+ ((OvalShape)curShape).LongShapeList.get(0).text.length()*15),((OvalShape)curShape).sp.y+40, tPaint);
								  else canvas.drawText(((OvalShape)curShape).LongShapeList.get(2).text, ((OvalShape)curShape).sp.x+pos*(2*((OvalShape)curShape).curwidth/3+15),((OvalShape)curShape).sp.y+40, tPaint);
							}
						}
					}	

				}
				/*
				if(((OvalShape)curShape).LongShapeList.get()!=null )
				{
					mPaint.setColor(Color.BLUE);
					canvas.drawLine(((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth/2, ((OvalShape)curShape).sp.y, ((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth, ((OvalShape)curShape).sp.y, mPaint);
					if(((OvalShape)curShape).manageShape.text != null)
					{
						//canvas.drawt
					
						
						canvas.drawText(((OvalShape)curShape).manageShape.text, ((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth,((OvalShape)curShape).sp.y, tPaint);
					    //canvas.drawText
					}
					//canvas.drawRoundRect(((RectShape)curShape).r, 5, 5, mPaint);
				}*/
			
				/*if(((OvalShape)curShape).manageShape!=null )
				{
					mPaint.setColor(Color.BLUE);
					canvas.drawLine(((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth/2, ((OvalShape)curShape).sp.y, ((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth, ((OvalShape)curShape).sp.y, mPaint);
					if(((OvalShape)curShape).manageShape.text != null)
					{
						//canvas.drawt
					
						
						canvas.drawText(((OvalShape)curShape).manageShape.text, ((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth,((OvalShape)curShape).sp.y, tPaint);
					    //canvas.drawText
					}
					//canvas.drawRoundRect(((RectShape)curShape).r, 5, 5, mPaint);
				}*/
				//单
				
				Log.d("event", "true");
//				canvas.drawCircle(((OvalShape)curShape).sp.x,((OvalShape)curShape).sp.y, ((OvalShape)curShape).radius, mPaint);
			}
			if(curShape.shape == shapeEnum.iRect)
			{
				mPaint.setColor(curShape.theColor);
				canvas.drawRoundRect(((RectShape)curShape).r, 5, 5, mPaint);
//				canvas.drawRect(((RectShape)curShape).rectf,  mPaint);
				if(curShape.text != null)
				{
					
					canvas.drawText(curShape.text, curShape.getTPoint(tPaint).x, curShape.getTPoint(tPaint).y, tPaint);
				}
				
				if(((RectShape)curShape).editstate)
				{
					 Log.d("event", "true");
					super.onDraw(canvas);
					Paint RBoundPaint = new Paint();
					RBoundPaint.setColor(Color.GRAY);
					//canvas.drawOval(((OvalShape)curShape).r, BoundPaint);
					//mPaint.setColor(curShape.theColor);
					RBoundPaint.setStyle(Paint.Style.STROKE);
					//Log.d("event", "true2");
					PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);   
					RBoundPaint.setPathEffect(effects);  

					//canvas.drawRect(((RectShape)curShape).r, RBoundPaint);
					//RectF VirRect = new RectF();
					RectF VirRect=new RectF(((RectShape)curShape).r.left,((RectShape)curShape).r.top,((RectShape)curShape).r.right,((RectShape)curShape).r.bottom);
					VirRect.left -= 3.5;
					VirRect.top -= 3.5;
					VirRect.right += 3.5;
					VirRect.bottom += 3.5;
					canvas.drawRect(VirRect, RBoundPaint);
					//Log.d("event", "true3");
					//mPaint.setColor(curShape.theColor);
					//canvas.drawOval(((OvalShape)curShape).r, mPaint);
				}
				
				/*
				if(((RectShape)curShape).manageShape!=null )
				{
					mPaint.setColor(Color.BLUE);
					canvas.drawLine(((LongTextShape)curShape).sp.x+((LongTextShape)curShape).curwidth/2, ((LongTextShape)curShape).sp.y, ((LongTextShape)curShape).sp.x+((LongTextShape)curShape).curwidth, ((LongTextShape)curShape).sp.y, mPaint);
					if(((RectShape)curShape).manageShape.text != null)
					{
						//canvas.drawt
						canvas.drawText(((RectShape)curShape).manageShape.text, 50,50, tPaint);
					}
					//canvas.drawRoundRect(((RectShape)curShape).r, 5, 5, mPaint);
				}
				*/
				
				if(((RectShape)curShape).LongShapeList.size() > 0)
					
				{
					int pos = ((RectShape)curShape).LongShapeList.get(0).isleft;
					
					int size = ((RectShape)curShape).LongShapeList.size();
					if(size>0)
					{
						mPaint.setColor(Color.BLUE);
						canvas.drawLine(((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth/2), ((RectShape)curShape).sp.y, ((RectShape)curShape).sp.x+2*pos*(((RectShape)curShape).curwidth/3), ((RectShape)curShape).sp.y, mPaint);
						if(size==1)
						{
							if(((RectShape)curShape).LongShapeList.get(0).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								canvas.drawLine(((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+5) , ((RectShape)curShape).sp.y, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth + 15), ((RectShape)curShape).sp.y, linePaint);
								if(pos < 0)
									canvas.drawText(((RectShape)curShape).LongShapeList.get(0).text, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth+25+((RectShape)curShape).LongShapeList.get(0).text.length()*15),((RectShape)curShape).sp.y, tPaint);
								
								else canvas.drawText(((RectShape)curShape).LongShapeList.get(0).text, ((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+15),((RectShape)curShape).sp.y, tPaint);
							}
						}
						if(size==2)
						{
						//mPaint.setColor(Color.BLUE);
							//canvas.drawLine(((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth/2, ((OvalShape)curShape).sp.y, ((OvalShape)curShape).sp.x+((OvalShape)curShape).curwidth, ((OvalShape)curShape).sp.y, mPaint);
							if(((RectShape)curShape).LongShapeList.get(0).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((RectShape)curShape).sp.x + 2*pos*(((RectShape)curShape).curwidth/3), ((RectShape)curShape).sp.y);
								longTextpath.quadTo(((RectShape)curShape).sp.x + pos*(2*((RectShape)curShape).curwidth/3+5), ((RectShape)curShape).sp.y-30, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth + 15), ((RectShape)curShape).sp.y-30);
								canvas.drawPath(longTextpath, linePaint);
								if(pos < 0)
									canvas.drawText(((RectShape)curShape).LongShapeList.get(0).text, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth+25 + ((RectShape)curShape).LongShapeList.get(0).text.length()*15),((RectShape)curShape).sp.y-30, tPaint);		
								
								else canvas.drawText(((RectShape)curShape).LongShapeList.get(0).text, ((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+15),((RectShape)curShape).sp.y-30, tPaint);
							
								}
					
							if(((RectShape)curShape).LongShapeList.get(1).text!=null)//canvas.drawt
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((RectShape)curShape).sp.x + 2*pos*(((RectShape)curShape).curwidth/3), ((RectShape)curShape).sp.y);
								  longTextpath.quadTo(((RectShape)curShape).sp.x + pos*(2*((RectShape)curShape).curwidth/3+5), ((RectShape)curShape).sp.y+30, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth + 15), ((RectShape)curShape).sp.y+30);
								  canvas.drawPath(longTextpath, linePaint);
								  if(pos < 0)
								  canvas.drawText(((RectShape)curShape).LongShapeList.get(1).text, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth+25+((RectShape)curShape).LongShapeList.get(0).text.length()*15),((RectShape)curShape).sp.y+30, tPaint);		
									
								  else canvas.drawText(((RectShape)curShape).LongShapeList.get(1).text, ((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+15),((RectShape)curShape).sp.y+30, tPaint);		
							}
								 //canvas.drawText
							
						
					
						}
						if(size==3)
						{
							
							if(((RectShape)curShape).LongShapeList.get(0).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((RectShape)curShape).sp.x + 2*pos*(((RectShape)curShape).curwidth/3), ((RectShape)curShape).sp.y);
								longTextpath.quadTo(((RectShape)curShape).sp.x + pos*(2*((RectShape)curShape).curwidth/3+5), ((RectShape)curShape).sp.y-30, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth + 15), ((RectShape)curShape).sp.y-30);
								canvas.drawPath(longTextpath, linePaint);
								if(pos < 0)
									canvas.drawText(((RectShape)curShape).LongShapeList.get(0).text, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth+25+((RectShape)curShape).LongShapeList.get(0).text.length()*15),((RectShape)curShape).sp.y-40, tPaint);
								else canvas.drawText(((RectShape)curShape).LongShapeList.get(0).text, ((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+15),((RectShape)curShape).sp.y-40, tPaint);
							}
					
							if(((RectShape)curShape).LongShapeList.get(1).text!=null)
							{
						
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								canvas.drawLine(((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+5) , ((RectShape)curShape).sp.y, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth + 15), ((RectShape)curShape).sp.y, linePaint);
								if(pos < 0)
									canvas.drawText(((RectShape)curShape).LongShapeList.get(1).text, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth+25+((RectShape)curShape).LongShapeList.get(0).text.length()*15),((RectShape)curShape).sp.y, tPaint);
								
								else canvas.drawText(((RectShape)curShape).LongShapeList.get(1).text, ((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+15),((RectShape)curShape).sp.y, tPaint);
							}
						
					
							if(((RectShape)curShape).LongShapeList.get(2).text!=null)
							{
								tPaint.setTextSize(20);
								tPaint.setUnderlineText(true);
								longTextpath.moveTo(((RectShape)curShape).sp.x + 2*pos*(((RectShape)curShape).curwidth/3), ((RectShape)curShape).sp.y);
								  longTextpath.quadTo(((RectShape)curShape).sp.x + pos*(2*((RectShape)curShape).curwidth/3+5), ((RectShape)curShape).sp.y+30, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth + 15), ((RectShape)curShape).sp.y+30);
								  canvas.drawPath(longTextpath, linePaint);
								  if(pos < 0)
								  	canvas.drawText(((RectShape)curShape).LongShapeList.get(2).text, ((RectShape)curShape).sp.x+pos*(((RectShape)curShape).curwidth+25+ ((RectShape)curShape).LongShapeList.get(0).text.length()*15),((RectShape)curShape).sp.y+40, tPaint);
								  else canvas.drawText(((RectShape)curShape).LongShapeList.get(2).text, ((RectShape)curShape).sp.x+pos*(2*((RectShape)curShape).curwidth/3+15),((RectShape)curShape).sp.y+40, tPaint);
							}
						}
					}	
				}
				
				}
			if(curShape.shape == shapeEnum.RLong || curShape.shape == shapeEnum.LLong)
			{
				mPaint.setColor(Color.BLUE);
				int pos = ((LongTextShape)curShape).isleft;
				canvas.drawLine(((LongTextShape)curShape).sp.x+pos*(((LongTextShape)curShape).curwidth/2), ((LongTextShape)curShape).sp.y, ((LongTextShape)curShape).sp.x+pos*(((LongTextShape)curShape).curwidth), ((LongTextShape)curShape).sp.y, mPaint);
				if(curShape.text != null)
				{
					//canvas.drawt
					//canvas.drawText(curShape.text, , tPaint);
				}
				//canvas.drawRoundRect(((RectShape)curShape).r, 5, 5, mPaint);
			}
			
		}
	    //canvas.drawCircle(100,100,20,mPaint);  
	    //canvas.drawCircle(MainActivity.point.x, MainActivity.point.y, 30, mPaint);
		

	    for(int i = 0; i < curvelist.size(); ++i)
		{
			//画曲线
	    	canvas.drawPath(curvelist.get(i).path, curvelist.get(i).paint);
		}

	    
	    
	    //放缩画布
	    
	}
	
	//获取用户要编辑的图形
	public int getFocus(Point p)
	{
		if(shapelist.size() >= 1)
		{
			double closest = Math.sqrt(Math.pow((p.x-shapelist.get(0).sp.x), 2)+Math.pow((p.y-shapelist.get(0).sp.y), 2));
			int index = 0;
			for(int i = 1; i < shapelist.size(); ++i)
			{
				double cur = Math.sqrt(Math.pow((p.x-shapelist.get(i).sp.x), 2)+Math.pow((p.y-shapelist.get(i).sp.y), 2));
				if(cur < closest)
				{
					closest = cur;
					index = i;
				}
			}
			if(closest <= shapelist.get(index).curwidth/2)
				return index;
			return -1;
		}
		else return -1;
	}
	
	//添加图形
	public void addShape(Point p, shapeEnum e)
	{
		if(p == null && (e == shapeEnum.RLong || e == shapeEnum.LLong))
		{
			Point pp = shapelist.get(curEdit).sp;
//			Point cur = new Point(pp.x)
			MyShape textShape = new LongTextShape(pp, e);
			if(e == shapeEnum.RLong)
				((LongTextShape)textShape).isleft = 1;
			if(e == shapeEnum.LLong)
				((LongTextShape)textShape).isleft = -1;
			 if(shapelist.get(curEdit).LongShapeList.size()<3)
			 {
				shapelist.get(curEdit).LongShapeList.add((LongTextShape) textShape);
			 }
			
			//LongShapeList
			//shapelist.get(curEdit).manageShape=(LongTextShape) textShape;
			//shapelist.add(textShape);
			
		}
		else 
		{
			Point cuP = p;
			switch(e)
			{
				case iOval:
					shapelist.add(new OvalShape(cuP, e));
					break;
				case iRect:
					shapelist.add(new RectShape(cuP, e));
					break;
				case RLong:
					break;
				case LLong:
					break;
			}
			
			for (int i = 0; i != shapelist.size(); i++)
				if(shapelist.get(i).shape == shapeEnum.iRect)
					Log.d("Test", shapelist.get(i).r.toString());
		}
		
	}
	
	//编辑图形，这里仅仅改变了颜色
	public void setEdit(int index)
	{
		if(index >= 0 ){
			this.curEdit = index;
		}
	}
	
	//移动图形
	public void moveShape(int index, Point movePt)
	{
		shapelist.get(index).sp = movePt;
		shapelist.get(index).refresh();
		refreshCurve(index);
		//Log.d("Location", shapelist.get(index).r.toString());


		
		Log.d("Location", shapelist.get(index).r.toString());
	}
	
	public void refreshCurve(int index)
	{
		Point p = new Point(shapelist.get(index).sp);
		for(int i = 0; i < curvelist.size(); ++i)
		{
			if(curvelist.get(i).parent == index)
			{
				Point pp = new Point(p);
				int child = curvelist.get(i).child;
				Point pc = new Point(shapelist.get(child).sp);
				curvePoint(pp, pc, index, child);
				curvelist.get(i).refresh(pp, pc);
			}
			else if(curvelist.get(i).child == index)
			{
				Point pc = new Point(p);
				int parent = curvelist.get(i).parent;
				Point pp = new Point(shapelist.get(parent).sp);
				curvePoint(pp, pc, parent, index);
				curvelist.get(i).refresh(pp, pc);
			}
		}
	}

	
	//恢复成未选中状态
	public void recovery(int index)
	{
		this.curEdit = -1;
	}
	
	public void resizeShape(float scale)
	{
		shapelist.get(curEdit).resize(scale);
		refreshCurve(curEdit);
	}
	
	//单
		//获取到当前比例吧 用来与两点间距离做比较得出放缩比例
		public float getportion()
		{
			return shapelist.get(curEdit).portion;
		}
		//单
	
		//获取编辑状态
		public boolean getstate()
		{
			Log.d("event", "true01");
			if(curEdit<0)
			{
				Log.d("event", "true02");
				return false;
			}
			else
			{
				Log.d("event", "true03");
			return shapelist.get(curEdit).editstate;
				//return true;
			}
		}
		
		//设置编辑状态
		public void setstate(boolean b)
		{
			shapelist.get(curEdit).editstate=b;
		}
		
		//改变画布的比例 float型参数s传递比例
		public void ChangeCanvas(float s)
		{
			canvasscale =  s;
			//super.onDraw(canvas);
		}
		
		public void SetCurP(Point p)
		{
			CurPoint = p;
		}

		
		//移动 canvas 参数为移动后的画布原点
		//public void movecanvas(Point mp)
		//{
		//	CanvasP = mp;
		//}
		
		//设置能否移动
		public void setmoveCanvas(boolean b)
		{
			ismoveCan = b;
		}
		
		public void saveP(Point p)
		{
			PrePoint = p;
		}
		
		public boolean Compare(Point p)
		{
			if(Math.abs(p.x-PrePoint.x)<10&&Math.abs(p.y-PrePoint.y)<10)
			{
				return false;
			}
			
			else
			{
				return true;
			}
		}
		
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

			setMeasuredDimension(800, 800);

			}

		
		public void remove(Point p)
		{
			// TODO Auto-generated method stub
			int index = getFocus(p);
			if(index >= 0)
			{
				shapelist.remove(index);
				curveRemove(index);//删除关联的曲线
				curEdit = -1;
			}
		}
		
		public void curveRemove(int index)
		{
			for(int i = 0; i < curvelist.size(); ++i)
			{
				int parent = curvelist.get(i).parent;
				int child = curvelist.get(i).child;
				if(parent == index || child == index)
				{
					curvelist.remove(i);
					--i;
				}
				
				/*
				if(child == index)
					curvelist.remove(i);*/
			}
		}

		public void addCurve(int parent, int child) {
			// TODO Auto-generated method stub
			//判断位置
			Log.d("event", "addcurve02");
			Point pparent = new Point(shapelist.get(parent).sp);
			Point pchild = new Point(shapelist.get(child).sp);

			curvePoint(pparent, pchild, parent, child);
			Log.d("event", "addcurve03");
			Beziercurve cur = new Beziercurve(pparent, pchild);//这个地方有问题。。。。暂时不懂
			cur.refresh(pparent, pchild);
			cur.parent = parent;
			cur.child = child;
			Log.d("event", "addcurve04");
			curvelist.add(cur);
		}
		
		public void curvePoint(Point pparent, Point pchild, int parent, int child)
		{
			if(Math.abs((pparent.y - pchild.y)) < (shapelist.get(parent).curheight/2))
			{
				if(pchild.x > pparent.x)
				{
					pparent.x += shapelist.get(parent).curwidth/2;
					pchild.x -= shapelist.get(child).curwidth/2;

				}
				if(pchild.x < pparent.x)
				{
					pparent.x -= shapelist.get(parent).curwidth/2;
					pchild.x += shapelist.get(child).curwidth/2;
				}
			}
			
			else if(Math.abs((pparent.y - pchild.y)) > (shapelist.get(parent).curheight/2))
			{
				if(pchild.y > pparent.y)
				{
					pparent.y += shapelist.get(parent).curheight/2;
					if(pchild.x > pparent.x)
					{
						if(Math.abs(pchild.x - pparent.x) < (shapelist.get(child).curwidth/2))
							pchild.y -=  shapelist.get(child).curheight/2;
						else pchild.x -= shapelist.get(child).curwidth/2;
					}
					else 
					{
						if(Math.abs(pchild.x - pparent.x) < (shapelist.get(child).curwidth/2))
							pchild.y -=  shapelist.get(child).curheight/2;
						else pchild.x += shapelist.get(child).curwidth/2;
						
					}
					
				}
				if(pchild.y < pparent.y)
				{
					pparent.y -= shapelist.get(parent).curheight/2;
					if(pchild.x > pparent.x)
					{
						if(Math.abs(pchild.x - pparent.x) < (shapelist.get(child).curwidth/2))
							pchild.y +=  shapelist.get(child).curheight/2;
						else pchild.x -= shapelist.get(child).curwidth/2;
					}
					else 
					{
						if(Math.abs(pchild.x - pparent.x) < (shapelist.get(child).curwidth/2))
							pchild.y +=  shapelist.get(child).curheight/2;
						else pchild.x += shapelist.get(child).curwidth/2;
						
					}
				}
			}
			/*else
			{
				if(pchild.y > pparent.y)
				{
					pparent.y += shapelist.get(parent).curheight/2;
					if(pchild.x < pparent.x)
						pchild.x += shapelist.get(child).curwidth/2;
					else 
						pchild.x -= shapelist.get(child).curwidth/2;
				}
				if(pchild.y < pparent.y)
				{
					pparent.y -= shapelist.get(parent).curheight/2;
					if(pchild.x > pparent.x)
						pchild.x -= shapelist.get(child).curwidth/2;
					else pchild.x += shapelist.get(child).curwidth/2;
				}
			}*/
		}

		public void changeColor(int position) {
			// TODO Auto-generated method stub
			switch(position)
			{
			case 0:
				shapelist.get(curEdit).theColor = Color.BLACK;
				break;
			case 1:
				shapelist.get(curEdit).theColor = Color.BLUE;
				break;
			case 2:
				shapelist.get(curEdit).theColor = Color.RED;
				break;
			case 3:
				shapelist.get(curEdit).theColor = Color.YELLOW;
				break;
			case 4:
				shapelist.get(curEdit).theColor = Color.GREEN;
				break;
			case 5:
				shapelist.get(curEdit).theColor = Color.argb(255, 165, 42, 42);
				break;
			}
		}

		
		public void editlongText(String text) {
			// TODO Auto-generated method stub
			
			int location = shapelist.get(curEdit).LongShapeList.size()-1;
			shapelist.get(curEdit).LongShapeList.get(location).text = text;
		}
		
		public void editText(String text) {
			// TODO Auto-generated method stub
			
			shapelist.get(curEdit).text = text;
		}


		
}

