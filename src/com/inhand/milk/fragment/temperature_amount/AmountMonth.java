package com.inhand.milk.fragment.temperature_amount;

import java.util.ArrayList;
import java.util.List;

import com.example.aaaa.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class AmountMonth extends OnePaper {
	
	public AmountMonth(Activity activity, Context context,int width) {
		super(activity, context, width,false);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void setExcle(Excle excle) {
		// TODO Auto-generated method stub
		excle.initMonthText();
		excle.setLeftTiltle(mActivty.getResources().
				getString(R.string.milk_excle_month_left_title));
		excle.setRightTiltle(mActivty.getResources().
				getString(R.string.milk_excle_month_right_title));
	}

	@Override
	public void refreshData(List<List<float[]>> data) {
		// TODO Auto-generated method stub
		data.clear();
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i< 30;i++){
			float[] a = new float[1];
			a[0] =(float) Math.random()*100+150;
			points.add(a);
		}
		data.add(points);
	}

}
