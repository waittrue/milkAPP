package com.example.aaaa;

import java.lang.reflect.Field;

import android.R.menu;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home_Fragment extends Fragment{
	
	Circle a;
	private int  width;
	private int height;
	private  DisplayMetrics dm;
	private  float scale ;
	
	
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        // ---Inflate the layout for this fragment---  
        View view = inflater.inflate(R.layout.home, container, false);
        setHome(view);
        return view;
    }  

	
	private void setHome(View view){
		scale = this.getResources().getDisplayMetrics().density;
		Toast.makeText(view.getContext(), String.valueOf(scale), Toast.LENGTH_SHORT).show();
		dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        width= dm.widthPixels;
        height = dm.heightPixels;
  
	    int r =width/4;
		a= new Circle(this.getActivity().getApplicationContext(),r);
		LinearLayout linearCircle = (LinearLayout)view.findViewById(R.id.circle);
		linearCircle.addView(a);
			
		a.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(v.getContext(), "chakanggengduo ",Toast.LENGTH_SHORT).show();
			}
		}); 
		a.start();
		
		TextView tuisong = (TextView) view.findViewById(R.id.tuisong);
		LinearLayout.LayoutParams paramsss = (LinearLayout.LayoutParams) tuisong.getLayoutParams();
		paramsss.width = (int)(width*0.85);
		tuisong.setLayoutParams(paramsss);
		
		ImageView  menubutton = (ImageView) view.findViewById(R.id.menu_entry);
		menubutton.setOnClickListener(  ((MainActivity)getActivity()).getMyOnclickListener() );
		
	}
	
	
	
}
