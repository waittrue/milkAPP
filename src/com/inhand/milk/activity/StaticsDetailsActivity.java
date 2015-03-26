package com.inhand.milk.activity;

import com.example.aaaa.R;
import com.inhand.milk.fragment.temperature_milk.details.DetailsFragment;

import android.os.Bundle;

public class StaticsDetailsActivity extends SubActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initFragment() {
		// TODO Auto-generated method stub
		mFragment = new DetailsFragment();
		if (getIntent().getExtras().getBoolean("isTemperature"))
			((DetailsFragment)mFragment ).setTemperature(true);
		else 
			((DetailsFragment)mFragment ).setTemperature(false);
	}
	
	
}
