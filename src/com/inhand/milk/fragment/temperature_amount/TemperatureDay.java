package com.inhand.milk.fragment.temperature_amount;

import java.util.ArrayList;
import java.util.List;

import com.example.aaaa.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class TemperatureDay extends OnePaper {

	public TemperatureDay(Activity activity,int width, Context context) {
		super(activity, context,width, true);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void setExcle(Excle excle) {
		// TODO Auto-generated method stub
		excle.setLeftTiltle(mActivty.getResources().getString(R.string.temperature_excle_day_left_title));
		excle.setRightTiltle(mActivty.getResources().getString(R.string.temperature_excle_day_right_title));
		excle.initDayText();
	}

	/**
	 * 返回当天温度，温度分为两个<list<float[]>>
	 * 一个代表最高温度，一个最低温度
	 * 每个温度为     时间 和 温度<float float> 时间3：30 = 3.5
	 */
	@Override
	public void  refreshData(List<List<float[]>> data) {
		// TODO Auto-generated method stub
		data.clear();
		
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<8;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*20+30;
			points.add(a);
		}
		data.add(points);
		
		points = new ArrayList<float[]>();
		for(int i = 0;i<8;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*19;
			points.add(a);
		}
		data.add(points);
	}

}
