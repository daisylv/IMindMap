package com.example.mindmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.createmap.ColorItem;
import com.createmap.ColorPicker;
import com.mapview.MyMapView;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements ColorItem {

	public static Point point = new Point();// 用于获取每次用户点击屏幕的点
	private MyMapView theView;
	private RelativeLayout layout;
	public static Canvas canvas;
	public Button curBtn;// 画曲线
	public Button remove;// 删除
	public Button ovalBtn;
	public Button rectBtn;
	public Button editBtn;
	public Button colorBtn;// 选中状态下编辑颜色
	public Button textBtn;//给椭圆什么的加字
	public Button RShapeBtn;
	public Button LShapeBtn;

	public enum stateEnum {
		locked, addShape, editShape, addCurve, remove
	};// 用户状态

	public stateEnum state = stateEnum.locked;// 初始化为锁定，即无法编辑，无法添加图形
	public MyMapView.shapeEnum shapeType;
	private int parent = -1;
	private int child = -1;
	private ColorItem colorItem = this;
	private Context thecontext = this;
	public Button save;
	public SlidingDrawer sld;
	private ListView mListView;
	private List<Map<String, Object>> mData;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 除去任务栏
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.activity_main);
		setupViews();// 初始化slidedrawer中的button
		point.x = -1;
		point.y = -1;
		theView = new MyMapView(this);
		// theView.layout(100, 100, 400, 800);
		layout = (RelativeLayout) findViewById(R.id.layout);
		
		sld = (SlidingDrawer)findViewById(R.id.slidingdrawer);

		layout.addView(theView);
		//layout.addView(sld);
	}

	/*
	 * private void addMapShape(String text) { // TODO Auto-generated method
	 * stub if(text == MyMapView.shapeEnum.iOval.toString())
	 * theView.addShape(point, MyMapView.shapeEnum.iOval); if(text ==
	 * MyMapView.shapeEnum.iRect.toString()) theView.addShape(point,
	 * MyMapView.shapeEnum.iRect); }
	 */

	private void setupViews() { // 加上adapter
		
		mListView = (ListView) findViewById(R.id.content);
		
		//sld.setAlpha(1);
		mData = getData();
		MyAdapter adapter = new MyAdapter(this);

		mListView.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {// buttons in the slidedrawer
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "ovalBtn");
		map.put("info", "ovalBtn");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "rectBtn");
		map.put("info", "rectBtn");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "editBtn");
		map.put("info", "editBtn");
		map.put("img", R.drawable.i3);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "curBtn");
		map.put("info", "curBtn");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "remove");
		map.put("info", "remove");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "colorBtn");
		map.put("info", "colorBtn");
		map.put("img", R.drawable.i3);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "save");
		map.put("info", "save");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "RTBtn");
		map.put("info", "RTBtn");
		map.put("img", R.drawable.i2);
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "LTBtn");
		map.put("info", "LTBtn");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "TShapeBtn");
		map.put("info", "TShapeBtn");
		map.put("img", R.drawable.i3);
		list.add(map);

		return list;
	}

	public final class ViewHolder {//
		public ImageView img;

		public Button viewBtn;
	}

	public class MyAdapter extends BaseAdapter {// myadapter to add button

		private LayoutInflater mInflater;
		String btnName;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			btnName = (String) mData.get(position).get("title");
			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.vlist, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				
				holder.viewBtn = (Button) convertView
						.findViewById(R.id.view_btn);
				//holder.viewBtn.setAlpha(1);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img"));

			holder.viewBtn.setText(btnName);
			holder.viewBtn.setMaxWidth(100);

			if (btnName.equals("ovalBtn")) {

				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						state = stateEnum.addShape;
						shapeType = MyMapView.shapeEnum.iOval;
						sld.animateClose();
					}
				});
			}
			if (btnName.equals("rectBtn")) {

				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						state = stateEnum.addShape;
						shapeType = MyMapView.shapeEnum.iRect;
						sld.animateClose();
					}
				});
			}
			if (btnName.equals("editBtn")) {

				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						state = stateEnum.editShape;
						sld.animateClose();
					}
				});
			}
			if (btnName.equals("curBtn")) {

				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						state = stateEnum.addCurve;
						sld.animateClose();
					}
				});
			}
			if (btnName.equals("remove")) {

				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						state = stateEnum.remove;
						sld.animateClose();
					}
				});
			}
			if (btnName.equals("colorBtn")) {

				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!(theView.curEdit < 0))
							new ColorPicker(thecontext, colorItem).show();
						sld.animateClose();
					}
				});
			}
			if (btnName.equals("save")) {

				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@SuppressLint("SdCardPath")
					@Override
					public void onClick(View v) {

						Bitmap bitmap = Bitmap.createBitmap(theView.getWidth(),
								theView.getHeight(), Bitmap.Config.ARGB_8888);
						Canvas canvas = new Canvas(bitmap);
						canvas.drawColor(Color.WHITE);
						theView.draw(canvas);
						File f = new File("/sdcard/Mindmap/");

						if (!f.exists())
							f.mkdirs();
						try {
							long time=System.currentTimeMillis();
							FileOutputStream fos = new FileOutputStream(f
									.getPath() + "/"+time+".jpg");
							// dv.cacheBitmap.compress(Bitmap.CompressFormat.JPEG,
							// 50, fos);
							bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
									fos);
							Toast.makeText(getApplicationContext(), "图片保存成功！",
								     Toast.LENGTH_SHORT).show();
							fos.close();
							sld.animateClose();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}

			if (btnName.equals("TShapeBtn")) {
				if(theView.curEdit >= 0){
					holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						setText();
						sld.animateClose();
						theView.invalidate();
					}
				});
				}
			}
			
			if (btnName.equals("RTBtn")) {
				if(theView.curEdit >= 0){
					holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						shapeType = MyMapView.shapeEnum.RLong;
						theView.addShape(null, shapeType);
						setlongText();
						sld.animateClose();
						theView.invalidate();
					}
				});
				}
			}

			if (btnName.equals("LTBtn")) {
				if(theView.curEdit >= 0){
				holder.viewBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						shapeType = MyMapView.shapeEnum.LLong;
						theView.addShape(null, shapeType);
						setlongText();
						sld.animateClose();

						theView.invalidate();
					}
				});
				}
			}
			
			return convertView;
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		System.exit(0);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (event.getPointerCount() > 1) {
			Point p1 = new Point((int) event.getX(event.getPointerId(0)),
					(int) event.getY(event.getPointerId(0)));
			Point p2 = new Point((int) event.getX(event.getPointerId(1)),
					(int) event.getY(event.getPointerId(1)));
			double dis_x = p2.x > p1.x ? (double) (p2.x - p1.x)
					: (double) (p2.x - p1.x); // 横向距离 (取正数，因为边长不能是负数)
			double dis_y = p2.y > p1.y ? (double) (p2.y - p1.y)
					: (double) (p2.y - p1.y);
			double distance1 = Math.sqrt(dis_x * dis_x + dis_y * dis_y);
			// 处于编辑状态
			if (state == stateEnum.editShape)//
			{

				// 求放缩比例 相对于初始距离 设为200

				// portion为相对比例 初始为200 随放大缩小而不断变化
				if (!(theView.curEdit < 0)) {
					float scale0 = (float) distance1 / theView.getportion();
					theView.resizeShape(scale0);
					if (state != stateEnum.locked) {
						theView.setmoveCanvas(false);
					}
					theView.invalidate();
				}
			}

			// 对canvas进行放缩
			else {
				float scale1 = (float) distance1 / 250;
				Log.d("event", "canvas");
				// canvas.scale(0.5f, 0.5f);
				theView.ChangeCanvas(scale1);

				if (state != stateEnum.locked) {
					theView.setmoveCanvas(false);
				}

				theView.invalidate();
				Log.d("event", "canvas0");
				// canvas
			}
			Log.d("event", "ScaleMode");
		} else {
			int X = (int) event.getRawX();
			int Y = (int) event.getRawY();
			point.x = X - 20;
			point.y = Y - 120;
			Point p = new Point(point);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				theView.saveP(p);
				Log.d("event", "down");
				// theView.setmoveCanvas(false);
				// 对按下后的操作 直接判断状态 非locked的状态均不可移动画布
				/*
				 * if(state == stateEnum.locked) { theView.setmoveCanvas(true);
				 * } else { theView.setmoveCanvas(false); }
				 */
				if (!(theView.curEdit < 0)) {
					theView.setstate(false);
				}
				if (state == stateEnum.addShape)// 判断是否处于添加图形状态
				{
					parent = -1;
					child = -1;// 重新初始化，以免刚刚不小心点了curv

					theView.addShape(p, shapeType);
					state = stateEnum.locked;
					if (!(theView.curEdit < 0)) {
						theView.setstate(false);
					}
					// theView.setmoveCanvas(false);

					theView.invalidate();
					// theView.setmoveCanvas(true);
					Log.d("Test", p.toString());
				}

				if (state == stateEnum.remove) {
					parent = -1;
					child = -1;// 重新初始化，以免刚刚不小心点了curv

					theView.remove(p);
					state = stateEnum.locked;
					theView.invalidate();
				}

				if (state == stateEnum.addCurve) {
					if (parent < 0) {
						parent = theView.getFocus(p);
						Log.d("event", "addcurve11");
					}
					if ((!(parent < 0)) && (child < 0)
							&& (parent != theView.getFocus(p))) {
						child = theView.getFocus(p);
						Log.d("event", "addcurve12");
					}
					if ((!(parent < 0)) && (!(child < 0))) {

						Log.d("event", "addcurve01");
						theView.addCurve(parent, child);
						theView.invalidate();
						parent = -1;
						child = -1;
					}

				}

				// theView.setmoveCanvas(true);
				if (state == stateEnum.editShape)// 判断是否处于编辑状态,
													// 用户必须每次都点击编辑button才能选中图形，否则点击其他地方就会恢复非编辑状态
				{
					parent = -1;
					child = -1;// 重新初始化，以免刚刚不小心点了curv

					int index = theView.getFocus(p);// 要改一下判断，因为scale，图形的选中判断有改变
					if ((!(index < 0)) && (theView.curEdit < 0)) {
						theView.setEdit(index);
						theView.setstate(true);

						theView.invalidate();
						// state = stateEnum.locked;
					}
					if (theView.curEdit >= 0) {
						if (index < 0 || index != theView.curEdit) {
							theView.recovery(theView.curEdit);
							state = stateEnum.locked;

							theView.invalidate();
						}
					}
				}

				break;

			case MotionEvent.ACTION_MOVE:
				// theView.setmoveCanvas(true);

				if (theView.curEdit >= 0) {
					if (state == stateEnum.editShape) {
						theView.moveShape(theView.curEdit, p);
						// if(theView.getFocus(p) != theView.curEdit)
						// theView.recovery(theView.curEdit);
						// state = stateEnum.locked;
						theView.setstate(true);

						theView.invalidate();
					}

					// 移动画布
					else if (state == stateEnum.addShape) {

						theView.addShape(p, shapeType);
						state = stateEnum.locked;
						if (!(theView.curEdit < 0)) {
							theView.setstate(false);
						}
						// theView.setmoveCanvas(false);

						theView.invalidate();
						// 。。。

					}

					else {
						if (theView.Compare(p)) {

							theView.SetCurP(p);
							theView.setmoveCanvas(true);

							// theView.movecanvas(p);
							// theView.setmoveCanvas(true);
							theView.invalidate();
							// theView.setmoveCanvas(false);
						}
						//
						Log.d("event", "move01");
					}
					Log.d("event", "move");
				} else {
					// 。。。
					Log.d("event", "move02");
					if (theView.Compare(p)) {

						theView.SetCurP(p);
						theView.setmoveCanvas(true);

						// theView.movecanvas(p);
						// theView.setmoveCanvas(true);

						// theView.setmoveCanvas(false);
						theView.invalidate();
					}
					// theView.setmoveCanvas(false);
					Log.d("event", "move03");
				}
				break;
			case MotionEvent.ACTION_UP:

				Log.d("event", "up");
				break;

			}
		}
		// theView.invalidate();
		return super.onTouchEvent(event);
	}

	@Override
	public void ItemClickListener(int position) {
		// TODO Auto-generated method stub
		// 实现接口
		theView.changeColor(position);
		theView.invalidate();
	}

	public void setText() {
		
		Builder builder = new AlertDialog.Builder(thecontext);
		builder.setTitle("TextInput");
		final EditText editt = new EditText(thecontext);
		builder.setView(editt);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				theView.editText(editt.getText().toString());

				theView.invalidate();
			}
		});
		builder.setNegativeButton("Cancel", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void setlongText() {
		Builder builder = new AlertDialog.Builder(thecontext);
		builder.setTitle("TextInput");
		final EditText editt = new EditText(thecontext);
		builder.setView(editt);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				theView.editlongText(editt.getText().toString());

				theView.invalidate();
			}
		});
		builder.setNegativeButton("Cancel", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
