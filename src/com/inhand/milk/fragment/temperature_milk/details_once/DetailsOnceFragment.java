package com.inhand.milk.fragment.temperature_milk.details_once;

import com.example.aaaa.R;
import com.inhand.milk.fragment.TitleFragment;

import android.R.string;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsOnceFragment extends TitleFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView =  inflater.inflate(R.layout.temperature_milk_details_once, null);
		//setBack();
		setTitleview(getString(R.string.details_once_title_string), 2);
		setInfo();
		return mView;
	}
	
	
	private void setInfo(){
		TextView textView = (TextView)mView.findViewById(R.id.details_once_real_amount_num);
		textView.setText(getRealAmount());
		
		textView = (TextView)mView.findViewById(R.id.details_once_advise_amount_num);
		textView.setText(getAdviseAmount());
		
		textView = (TextView)mView.findViewById(R.id.details_once_temperature_start_num);
		textView.setText( getStartTemperature());
		
		textView = (TextView)mView.findViewById(R.id.details_once_temperature_end_num);
		textView.setText( getEndTemperature());
		
		textView = (TextView)mView.findViewById(R.id.details_once_health_num);
		textView.setText( getHealthNum());
		
		textView = (TextView)mView.findViewById(R.id.details_once_end_time_num);
		textView.setText( getEndTime());
	}
	private String getRealAmount(){
		return String.valueOf(  (int)(Math.random()*20+130) )+"ml";
	}
	private String getAdviseAmount(){
		return String.valueOf(  (int)(Math.random()*20+140))+"ml";
	}
	private String getStartTemperature(){
		return String.valueOf(  (int)(Math.random()*20+10) )+"бу";
	}
	private String getEndTemperature(){
		return String.valueOf( (int)(Math.random()*20) )+"бу";
	}
	private String getEndTime(){
		String string = "2015-3-12 18:49";
		return string;
	}
	private String getHealthNum(){
		return String.valueOf( (int)(Math.random()*10) );
	}
}
