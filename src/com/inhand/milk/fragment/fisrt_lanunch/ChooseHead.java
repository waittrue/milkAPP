package com.inhand.milk.fragment.fisrt_lanunch;

import java.nio.channels.spi.SelectorProvider;

import com.example.aaaa.R;
import com.inhand.milk.utils.firstlanunch.Ruler;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.LauncherActivity;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseHead extends FirstLaunchFragment {
	
	private ImageView rulerJt,icon;
	private HorizontalScrollView ScrollView;
	private LinearLayout rulerContainer;
	private TextView num,unit;
	private int spacing ,spacingnum = 2;
	private static final int TIME = 1000;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.first_launch_choose_head, null);
		setTitle(getResources().getString(R.string.first_launch_choose_head_info));
		initViews(view);
		setPre();
		setNext();
		return view;
	}
	
	private void initViews(View view){
		rulerJt = (ImageView)view.findViewById(R.id.first_launch_choose_head_ruler_jt_icon);
		icon = (ImageView)view.findViewById(R.id.first_launch_choose_head_icon_imageview);
		ScrollView = (HorizontalScrollView)view.findViewById(R.id.first_launch_choose_head_scrollView);
		rulerContainer = (LinearLayout)view.findViewById(R.id.first_launch_choose_head_ruler_container);
		num = (TextView)view.findViewById(R.id.first_launch_choose_head_num_textview);
		unit = (TextView)view.findViewById(R.id.first_launch_choose_head_unit_textview);
		
		spacing = (int) width/30;
		Ruler rulerView = new Ruler( getActivity().getApplicationContext(), width*0.75f ,
					0f,(float) getResources().getDimension(R.dimen.first_lanunch_weight_ruler_height),
					0, 500,spacing,spacingnum, true);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);  
		
		lp.setMargins(0, (int) getResources().getDimension(R.dimen.first_lanunch_weight_ruler_margin)
				, 20, 0);
		rulerContainer.addView(rulerView,lp);
		
		ScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 Log.i("ruler", String.valueOf( (ScrollView.getScrollX()) ) );
		    
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
			        // ���Լ�����ScrollView�Ĺ����¼�
			        Log.i("ruler", String.valueOf( (ScrollView.getScrollX()) ) );
			        float x = ScrollView.getScrollX();
			        x = x/spacing * spacingnum /10f;
			        num.setText( String.format("%.1f",x));
			    }
				return false;
			}
		});
		String sex = getExtraInfo();
		if (sex.equals("boy"))
			icon.setImageDrawable(getResources().getDrawable(R.drawable.first_launch_weight_boy_icon));
		else if (sex.equals("girl"))
			icon.setImageDrawable(getResources().getDrawable(R.drawable.first_launch_weight_girl_icon));
	}
	private void setPre(){
		lanunchBottom.setPreListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enterPreFragment();
			}
		});
	}
	private void setNext(){
		lanunchBottom.setNextListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				outAnimation();
				lanunchBottom.finishApper();
			}
		});
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden){
			setTitle(getResources().getString(R.string.first_launch_choose_head_info));
			icon.clearAnimation();
			inAnimation();
			setNext();
			setPre();
		}
	}

	@Override
	protected Fragment nextFragment() {
		// TODO Auto-generated method stub
		return new ChooseMilk();
	}

	@Override
	protected void inAnimation() {
		// TODO Auto-generated method stub
		alphAnimation(rulerJt, 1f, TIME);
		alphAnimation(ScrollView, 1f, TIME);
		alphAnimation(num, 1f, TIME);
		alphAnimation(unit,1f, TIME);
		
	}

	@Override
	protected void outAnimation() {
		// TODO Auto-generated method stub
		alphAnimation(rulerJt, 0f, TIME);
		alphAnimation(ScrollView, 0f, TIME);
		alphAnimation(num, 0f, TIME);
		alphAnimation(unit,0f, TIME);
		
		AlphaAnimation animator = new AlphaAnimation(1f, 0f);
		animator.setDuration(TIME);
		animator.setFillAfter(true);
		animator.setAnimationListener(new AnimationListener() {
			
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
				save();
			}
		});
		
		icon.startAnimation(animator);
		
	}
	private void save(){
		Toast.makeText(this.getActivity().getApplicationContext(), num.getText().toString(),
					1000).show();
	}

}
