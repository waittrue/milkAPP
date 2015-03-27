package com.inhand.milk.fragment.temperature_amount;

import java.util.ArrayList;
import java.util.List;

import com.example.aaaa.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class AmountDay extends OnePaper {

	public AmountDay(Activity activity, Context context,int width) {
		super(activity, context, width,false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setExcle(Excle excle) {
		// TODO Auto-generated method stub
		excle.initDayText();
		excle.setLeftTiltle(mActivty.getResources().
				getString(R.string.milk_excle_day_left_title));
		excle.setRightTiltle(mActivty.getResources().
				getString(R.string.milk_excle_day_right_title));
	}

	@Override
	public void refreshData(List<List<float[]>> data) {
		// TODO Auto-generated method stub
		data.clear();
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<8;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*100+150;
			points.add(a);
		}
		data.add(points);
	}

}
