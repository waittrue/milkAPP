package com.inhand.milk.fragment.home;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment{
	
	private Circle circle;
	private float  width;
	
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        // ---Inflate the layout for this fragment---  
        View view = inflater.inflate(R.layout.home, container, false);
        setHome(view);
        return view;
    }  

	
	private void setHome(View view){
		DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        width= dm.widthPixels;
     
	    float r =width/4;
		circle= new Circle(this.getActivity().getApplicationContext(),r);
		LinearLayout linearCircle = (LinearLayout)view.findViewById(R.id.temperature_milk_circle_container);
		linearCircle.addView(circle);
		circle.start();
		
		setOthers(view);
		
	}
	
	private void setOthers(View view){
		
		TextView tuisong = (TextView) view.findViewById(R.id.tuisong);
		LinearLayout.LayoutParams paramsss = (LinearLayout.LayoutParams) tuisong.getLayoutParams();
		paramsss.width = (int)(width*0.85);
		tuisong.setLayoutParams(paramsss);
		
		ImageView  menubutton = (ImageView) view.findViewById(R.id.menu_entry);
		menubutton.setOnClickListener(  ((MainActivity)getActivity()).getMyOnclickListener() );
	}
	
	
}
