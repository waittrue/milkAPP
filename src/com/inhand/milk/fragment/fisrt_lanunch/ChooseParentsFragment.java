package com.inhand.milk.fragment.fisrt_lanunch;

import java.util.concurrent.Delayed;

import android.R.bool;
import android.R.integer;
import android.animation.ObjectAnimator;
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
import android.widget.Toast;

import com.example.aaaa.R;
import com.inhand.milk.activity.FirstLanunchActivity;
import com.inhand.milk.utils.firstlanunch.FirstLanunchBottom;

public class ChooseParentsFragment extends FirstLaunchFragment {
	private ImageView mother,father,motherName,fatherName,motherSelect,fatherSelect;
	private static final int animotionTime1 = 1000;
	private static final int animotionTime2 = 300;
	private int State = 0;//1����ְ�  2��������
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.first_launch_choose_parents, null);
	    
        mother = (ImageView) (view).findViewById(R.id.choose_parents_mother_imageview);
        father = (ImageView) (view).findViewById(R.id.choose_parents_father_imageView);
        motherName = (ImageView)(view.findViewById(R.id.choose_pratens_mother_name_imageview));
        fatherName = (ImageView)(view.findViewById(R.id.choose_parents_father_name_imageview));
        motherSelect = (ImageView)(view.findViewById(R.id.first_launch_parents_mother_selects));
        fatherSelect = (ImageView)(view.findViewById(R.id.first_launch_parents_father_selects));
        fatherSelect.setAlpha(0f);
        motherSelect.setAlpha(0f);
        fatherName.setAlpha(0f);
        motherName.setAlpha(0f);
        setOnclick();
        setTitle(getResources().getString(R.string.first_launch_choose_parents));
		return view;
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if(!hidden){
			setTitle(getResources().getString(R.string.first_launch_choose_parents));
		      fatherName.setAlpha(0f);
		      motherName.setAlpha(0f);
		      fatherName.clearAnimation();
		      motherName.clearAnimation();
		      inAnimation();
		      clickNextable();
		}
			
	}

	private OnClickListener chooseMather  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			chooseMother();
			State = 2;
			clickNextable();
		}
	};
	
	private OnClickListener chooseFather  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			chooseFather();
			State = 1;
			clickNextable();
		}
	};
	
	private OnClickListener nextClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lanunchBottom.NextRightAnimation();
			motherSelect.setAlpha(0f);
			fatherSelect.setAlpha(0f);
			outAnimation();
			save();
		}
	};
	
	private void setOnclick(){
		mother.setOnClickListener(chooseMather);
		father.setOnClickListener(chooseFather);
	}
	/*
	 * state��ʾ 1��ʾ�ְ�2��ʾ����
	 */
	private void save(){
		Toast.makeText(this.getActivity().getApplicationContext(),
				String.valueOf(State), 1000).show();
	}

	@Override
	protected Fragment nextFragment() {
		// TODO Auto-generated method stub
		return new ChooseBabyInfo();
	}
	
	private void chooseMother(){
		if ( State != 2 ){
			alphAnimation(motherSelect, 1f, 100);
			fatherSelect.clearAnimation();
			fatherSelect.setAlpha(0f);
		}
	}
	private void chooseFather(){
		if ( State != 1){
			alphAnimation(fatherSelect, 1f, 100);
			motherSelect.clearAnimation();
			motherSelect.setAlpha(0f);
			Log.i("parents", String.valueOf( fatherName.getAlpha()) );
		}
	}

	@Override
	protected void inAnimation() {
		// TODO Auto-generated method stub
		Log.i("parents", "start inAnimation");
		
		
		Animation animation = new TranslateAnimation(width/2, 0, 0, 0);
		animation.setDuration(animotionTime1);
		animation.setFillAfter(true);
		father.startAnimation(animation);
		
		animation = new TranslateAnimation(-width/2, 0, 0, 0);
		animation.setDuration(animotionTime1);
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
				alphAnimation(fatherName, 1f, animotionTime1);
				alphAnimation(motherName, 1f, animotionTime1);
				if (State == 1)
					 fatherSelect.setAlpha(1f);
				 else if (State == 2)
					 motherSelect.setAlpha(1f);
			}

			private void Delayed(int animotiontime1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void outAnimation() {
		// TODO Auto-generated method stub
		
		
		Animation animation = new TranslateAnimation(0, width/2, 0, 0);
		animation.setDuration(animotionTime1);
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
	private void clickNextable(){
		if (State == 1 || State ==2)
			lanunchBottom.setNextListener(nextClickListener);
	}

}