package com.inhand.milk.activity;

import com.example.aaaa.R;
import com.inhand.milk.fragment.health.nutrition.Nutrition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public abstract class SubActivity extends BaseActivty{

	protected Fragment mFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_minor);
		FragmentManager manager = getFragmentManager();
		FragmentTransaction mTransaction = manager.beginTransaction();
		initFragment();
		mTransaction.add(R.id.Activity_fragments_container,mFragment);
		mTransaction.commit();	
	}
	protected abstract void initFragment();
}
