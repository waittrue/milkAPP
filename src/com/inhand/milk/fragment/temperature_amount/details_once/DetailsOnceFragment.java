package com.inhand.milk.fragment.temperature_amount.details_once;

import java.sql.Date;

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
		mView =  inflater.inflate(R.layout.temperature_amount_details_once, null);
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
	
	/**
	 * 返回点击的那一次的所有相关信息
	 * 重写下面所有函数
	 * 应该看一下就明白怎么返回
	 *
	 *这边没有输入具体时间,开始我忘了写，我们暂时定输入参数为Date类型,date
	 *看效果你就先默认今天
	 * 
	 */
	private String getRealAmount(){
		Date date;
		return String.valueOf(  (int)(Math.random()*20+130) )+"ml";
	}
	private String getAdviseAmount(){
		Date date;
		return String.valueOf(  (int)(Math.random()*20+140))+"ml";
	}
	private String getStartTemperature(){
		Date date;
		return String.valueOf(  (int)(Math.random()*20+10) )+"°";
	}
	private String getEndTemperature(){
		Date date;
		return String.valueOf( (int)(Math.random()*20) )+"°";
	}
	private String getEndTime(){
		Date date;
		String string = "2015-3-12 18:49";
		return string;
	}
	private String getHealthNum(){
		return String.valueOf( (int)(Math.random()*10) );
	}
}
