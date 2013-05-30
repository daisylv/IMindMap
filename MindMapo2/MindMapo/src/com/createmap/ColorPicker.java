package com.createmap;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.mindmap.R;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

/*棰滆壊瀵硅瘽妗嗭紝杩欎釜鐢ㄦ潵鏀瑰彉鍥惧舰鐨勯鑹诧紝绾挎潯鐨勯鑹茶繕娌＄‘瀹氭槸绾㈣壊濂借繕鏄伆鑹插ソ锛� * 绾挎潯缁熶竴涓�釜棰滆壊锛屽叾瀹炴敼涔熷彲浠�*/
public  class ColorPicker extends Dialog implements OnItemClickListener
{
	private GridView colorGrid;//涓�釜gridview
	private ColorItem colorItem; //棰滆壊锛屼竴涓帴鍙�	
	private String colorName[] = {"BLACK", "BLUE", "RED", "YELLOW", "GREEN", "DKGRAY"};//棰滆壊鍚�	
	
	private int colorItems[] = {R.drawable.black, R.drawable.blue,
			R.drawable.red, R.drawable.yellow, R.drawable.green, R.drawable.dkgray};//棰滆壊鍥剧墖
	
	public ColorPicker(Context context, ColorItem colorItem) {
		super(context, R.style.dialog_screen);//璁剧疆View鐨勯鏍�		
		setContentView(R.layout.colorpicker);//View
		this.colorItem = colorItem;
		setProperty();//璁剧疆绐楀彛椋庢牸
		
		colorGrid = (GridView)this.findViewById(R.id.GridView_toolbar);
		colorGrid.setAdapter(getAdapter(colorName, colorItems));//鍔犲叆棰滆壊map
		colorGrid.setOnItemClickListener(this);//璁剧疆渚﹀惉
		
		View view = findViewById(R.id.mainlayout); //鑳屾櫙
		view.getBackground().setAlpha(0);
		view.setBackgroundColor(Color.argb(127, 255, 192, 203));//绮夌孩
		
		this.setCanceledOnTouchOutside(true);//鐐瑰嚮鍚庢秷澶�	
		}

	//閫氳繃Adapter鍚慓ridView澧炲姞鑷畾涔夊厓绱�	
		
		private SimpleAdapter getAdapter(String[] colorName, int[] colorItems) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < colorName.length; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("colorImage", colorItems[i]);
			map.put("colorText", colorName[i]);
			data.add(map);
		}//浠ヤ笅涓篈dapter鏄犲皠
		SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), data, R.layout.item_color, 
				new String[]{"colorImage", "colorText"}, new int[]{R.id.color_image, R.id.color_text});
		return simpleAdapter;
	}

	private void setProperty() {
		Window w = getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.alpha = 1.0f;
		lp.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		// TODO Auto-generated method stub
		
		w.setAttributes(lp);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		colorItem.ItemClickListener(arg2);//鍝嶅簲浜嬩欢
		this.dismiss();
	}
	
	
}
