package com.inhand.milk.fragment.fisrt_lanunch;

import com.inhand.milk.activity.FirstLanunchActivity;
import com.inhand.milk.utils.firstlanunch.FirstLanunchBottom;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FirstLaunchFragment extends Fragment {
	protected FirstLanunchBottom lanunchBottom;
	protected  abstract void enterNextFragmet();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		lanunchBottom = ((FirstLanunchActivity)this.getActivity()).getFirstLanunchBottom();
		return  super.onCreateView(inflater, container, savedInstanceState);
	}
	
}
