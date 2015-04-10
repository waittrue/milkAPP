package com.inhand.milk.fragment.fisrt_lanunch;

import android.R.integer;
import android.app.Fragment;
import android.media.Image;
import android.net.NetworkInfo.State;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.aaaa.R;
import com.inhand.milk.activity.FirstLanunchActivity;
import com.inhand.milk.utils.firstlanunch.FirstLanunchBottom;

public class ChooseParentsFragment extends FirstLaunchFragment {
	private float width;
	private ImageView mother,father,motherName,fatherName;
	private int State = 0;//1代表爸爸  2代表妈妈
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.choose_parents, null);
		
		DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        width= dm.widthPixels;
        
        mother = (ImageView) (view).findViewById(R.id.choose_parents_mother_imageview);
        father = (ImageView) (view).findViewById(R.id.choose_parents_father_imageView);
        motherName = (ImageView)(view.findViewById(R.id.choose_pratens_mother_name_imageview));
        fatherName = (ImageView)(view.findViewById(R.id.choose_parents_father_name_imageview));
        fatherName.setVisibility(View.INVISIBLE);
        motherName.setVisibility(View.INVISIBLE);
		return view;
	}
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		enterAnimation();
	}



	private void alpha(View v){
		v.setVisibility(View.VISIBLE);
		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		v.startAnimation(animation);
	}
	private void enterAnimation(){
	
		Animation animation = new TranslateAnimation(width/2, 0, 0, 0);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		father.startAnimation(animation);
		
		animation = new TranslateAnimation(-width/2, 0, 0, 0);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		mother.startAnimation(animation);
		
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				alpha(fatherName);
				alpha(motherName);
			}
		});
	}
	private void outAnimation(){
		Animation animation = new TranslateAnimation(0, width/2, 0, 0);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		father.startAnimation(animation);
		fatherName.startAnimation(animation);
		
		animation = new TranslateAnimation(0,-width/2, 0, 0);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		mother.startAnimation(animation);
		motherName.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				enterNextFragmet();
			}
		});
	}
	private OnClickListener chooseMather  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lanunchBottom.setNextClickable(true);
			State = 2;
		}
	};
	private OnClickListener chooseFather  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lanunchBottom.setNextClickable(true);
			State = 1;
		}
	};
	private OnClickListener nextClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i("dianji", "dianji");
			lanunchBottom.NextRightAnimation();
			outAnimation();
			saveParentsInfo();
		}
	};
	
	private void setOnclick(){
		mother.setOnClickListener(chooseMather);
		father.setOnClickListener(chooseFather);
		lanunchBottom.setNextListener(nextClickListener);
		
	}
	@Override
	protected void enterNextFragmet() {
		// TODO Auto-generated method stub
		
	}
	private void saveParentsInfo(){
		
	}
	
}
