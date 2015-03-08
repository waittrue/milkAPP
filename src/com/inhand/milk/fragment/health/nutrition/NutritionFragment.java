package com.inhand.milk.fragment.health.nutrition;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;
import com.inhand.milk.fragment.temperature_milk.MilkCircle;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NutritionFragment extends Fragment{

	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.health_nutrition, null);
		addRing();
		setBackEvent();
		return view;
	}
	
	private void addRing(){
		DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width= dm.widthPixels * 0.35f;
		LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.health_nutrition_sector);
		Ring ring = new Ring(getActivity().getApplicationContext(),width);
		linearLayout.addView(ring);
		
	}
	private void setBackEvent(){
		ImageView backImage = (ImageView)view.findViewById(R.id.health_nutrition_back);
		backImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				android.app.FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentManager.popBackStack();
				fragmentTransaction.commit();
				( (MainActivity)getActivity() ).visibleButtons();
			}
		});
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Health_nutrition", "destroy");
	}
	
	
}
