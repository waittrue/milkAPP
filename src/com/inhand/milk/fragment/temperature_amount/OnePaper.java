package com.inhand.milk.fragment.temperature_amount;

import java.util.ArrayList;
import java.util.List;
import com.example.aaaa.R;
import com.inhand.milk.activity.StaticsDetailsActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public abstract class OnePaper {
	private LayoutInflater inflater;
	protected Activity mActivty;
	private View layout;
	private Context mContext;
	private boolean mIsTemperature;
	private Excle excle;
	private int max = -1 ,min = 1000;
	private View circle;
	private int mWidth;
	private List<List<float[]>> data = new ArrayList<List<float[]>>();
	
private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass( mActivty, StaticsDetailsActivity.class);
			intent.putExtra("isTemperature", mIsTemperature);
			mActivty.startActivity(intent);
		}
	};
	
	public OnePaper(Activity activity,Context context,int width,boolean isTemperature) {
		mActivty = activity;
	    inflater = activity.getLayoutInflater(); 
	    mContext = context;
	    layout = inflater.inflate(R.layout.temperature_amount_circle_excle, null);
	    mIsTemperature = isTemperature;
	    if (mIsTemperature){
	    	max = 50;
	    	min = 0;
	    }
	    else {
	    	max = 250;
	    	min = 40;
	    }
	    mWidth = width;
	}

	private void drawExcle(){
		LinearLayout.LayoutParams excleParams = (LinearLayout.LayoutParams)new LinearLayout.LayoutParams(mWidth,
				LinearLayout.LayoutParams.MATCH_PARENT);
		LinearLayout linearlayout1 = (LinearLayout)layout.findViewById(R.id.temperature_milk_excle_container);
		excle = new Excle( mContext );
		
		excle.setRange(max, min);
		excle.setOnClickListener(onClickListener);
		linearlayout1.addView(excle ,excleParams);
		
		refreshData(data);
		excle.addLine( data);
		setExcle(excle);
	}
	
	protected abstract void setExcle(Excle excle);

	public abstract  void refreshData(List<List<float[]>> data);
	public void updateDate(){
		refreshData(data);
	}
	public void drawCicle(int width){
		if (!mIsTemperature){
			 float r  =  (float)width/5;
			 circle = new MilkCircle(mContext, r);
			 }
		else{ 
			float line = (width*0.13f);
		 	float r = (width*0.35f/2);
			circle = new TempratureCircle(mContext, r, line);
		}
		LinearLayout linearLayout = (LinearLayout)layout.findViewById(R.id.temperature_milk_circle_container);   
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		      		LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayout.addView(circle);
		 
	}
	
	public void start(){
		if (mIsTemperature)
			((TempratureCircle)circle).start();
		else 
			((MilkCircle)circle).start();
	}
	
	
	protected  View getPaper(){
		drawExcle();
		drawCicle(mWidth);
		return layout;
	}
	
	
}
