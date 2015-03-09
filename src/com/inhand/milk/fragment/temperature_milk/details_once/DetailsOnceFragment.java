package com.inhand.milk.fragment.temperature_milk.details_once;

import com.example.aaaa.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DetailsOnceFragment extends Fragment{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view =  inflater.inflate(R.layout.temperature_milk_details_once, null);
		setBack();
		return view;
	}
	
	private void setBack(){
		ImageView back = (ImageView)view.findViewById(R.id.details_once_back);
		OnClickListener onClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentManager.popBackStack();
				fragmentTransaction.commit();
			}
		};
		back.setOnClickListener(onClickListener);
	}
	
}
