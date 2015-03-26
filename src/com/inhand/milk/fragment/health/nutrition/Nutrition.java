package com.inhand.milk.fragment.health.nutrition;

import com.example.aaaa.R;
import com.inhand.milk.fragment.TitleFragment;
import com.inhand.milk.utils.HealthNutritionDisk;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Nutrition extends TitleFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView =  inflater.inflate(R.layout.health_nutrition, container, false);
		addRing();
		setTitleview(getResources().getString(R.string.health_nutrition), 2);
		//setBackEvent();
		return mView;
	}
	private void addRing(){
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width= dm.widthPixels * 0.35f;
		LinearLayout linearLayout = (LinearLayout)mView.findViewById(R.id.health_nutrition_sector);
		HealthNutritionDisk ring = new HealthNutritionDisk(getActivity().getApplicationContext(),width);
		linearLayout.addView(ring);
		
	}
	
}
