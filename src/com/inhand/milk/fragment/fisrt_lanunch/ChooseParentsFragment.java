package com.inhand.milk.fragment.fisrt_lanunch;

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
	private int State = 0;//1代表爸爸  2代表妈妈
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.first_launch_choose_parents, null);
		
		setTitle(getResources().getString(R.string.first_launch_choose_parents));
        
        mother = (ImageView) (view).findViewById(R.id.choose_parents_mother_imageview);
        father = (ImageView) (view).findViewById(R.id.choose_parents_father_imageView);
        motherName = (ImageView)(view.findViewById(R.id.choose_pratens_mother_name_imageview));
        fatherName = (ImageView)(view.findViewById(R.id.choose_parents_father_name_imageview));
        motherSelect = (ImageView)(view.findViewById(R.id.first_launch_parents_mother_selects));
        fatherSelect = (ImageView)(view.findViewById(R.id.first_launch_parents_father_selects));
        fatherName.setVisibility(View.INVISIBLE);
        motherName.setVisibility(View.INVISIBLE);
        fatherSelect.setAlpha(0f);
        motherSelect.setAlpha(0f);
        
		return view;
	}
	
	private void alpha(View v){
		v.setVisibility(View.VISIBLE);
		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(animotionTime1);
		animation.setFillAfter(true);
		v.startAnimation(animation);
	}
	
	private OnClickListener chooseMather  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lanunchBottom.setNextClickable(true);
			lanunchBottom.setNextListener(nextClickListener);
			State = 2;
			chooseMother();
		}
	};
	
	private OnClickListener chooseFather  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lanunchBottom.setNextClickable(true);
			lanunchBottom.setNextListener(nextClickListener);
			State = 1;
			chooseFather();
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
	}
	
	private void saveParentsInfo(){
		
	}

	@Override
	protected Fragment nextFragment() {
		// TODO Auto-generated method stub
		return new ChooseBabyInfoFragment();
	}
	
	private void chooseMother(){
		ObjectAnimator animator = new ObjectAnimator();
		animator.setTarget(motherSelect);
		animator.setPropertyName("alpha");
		motherSelect.setAlpha(0f);
		animator.setFloatValues(1f);
		animator.start();
		
		fatherSelect.setAlpha(0f);
	}
	private void chooseFather(){
		ObjectAnimator animator = new ObjectAnimator();
		animator.setTarget(fatherSelect);
		animator.setPropertyName("alpha");
		fatherSelect.setAlpha(0f);
		animator.setFloatValues(1f);
		animator.start();
		
		motherSelect.setAlpha(0f);
	}

	@Override
	protected void inAnimation() {
		// TODO Auto-generated method stub
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
				alpha(fatherName);
				motherName.setVisibility(View.VISIBLE);
				Animation mAnimation = new AlphaAnimation(0, 1);
				mAnimation.setDuration(animotionTime1);
				mAnimation.setFillAfter(true);
				
				mAnimation.setAnimationListener(new AnimationListener() {
					
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
						setOnclick();
					}
				});
				motherName.startAnimation(mAnimation);
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
		motherSelect.setAlpha(0f);
		fatherSelect.setAlpha(0f);
	}


}