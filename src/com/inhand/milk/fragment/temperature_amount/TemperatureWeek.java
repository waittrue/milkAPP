package com.inhand.milk.fragment.temperature_amount;

import java.util.ArrayList;
import java.util.List;

import com.example.aaaa.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class TemperatureWeek extends OnePaper {

	public TemperatureWeek(Activity activity,int width, Context context) {
		super(activity, context,width, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setExcle(Excle excle) {
		// TODO Auto-generated method stub
		excle.initWeekText();
		excle.setLeftTiltle(mActivty.getResources().
				getString(R.string.temperature_excle_week_left_title));
		excle.setRightTiltle( mActivty.getResources().
				getString(R.string.temperature_excle_week_right_title));
	}

	/**
	 * 返回当天前7天，温度分为两个<list<float[]>>
	 * 当天最高温度，当天最低温度
	 * 每个温度为     < float>
	 */
	
	@Override
	public void refreshData(List<List<float[]>> data) {
		// TODO Auto-generated method stub
		data.clear();
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<7;i++){
			float[] a = new float[1];
			a[0] =(float) Math.random()*20+30;
			points.add(a);
		}
		data.add(points);
		
	    points = new ArrayList<float[]>();
		for(int i = 0;i<7;i++){
			float[] a = new float[1];
			a[0] =(float) Math.random()*19;
			points.add(a);
		}
		data.add(points);
	}

}
