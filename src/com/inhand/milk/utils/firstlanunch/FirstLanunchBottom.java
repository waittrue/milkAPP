package com.inhand.milk.utils.firstlanunch;

import java.util.ArrayList;
import java.util.List;
import com.example.aaaa.R;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class FirstLanunchBottom extends ViewGroup {
	

	private float  width;
	private float toX;
	public FirstLanunchBottom(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	
	    DisplayMetrics dm = new DisplayMetrics();
	    ((WindowManager) context .getSystemService(Context.WINDOW_SERVICE) )
	    	.getDefaultDisplay().getMetrics(dm);
        width= dm.widthPixels;
	    
		LayoutParams layoutParams = new LayoutParams((int) (width/2.5), (int)(width/2.5 /4));
		
		List<Drawable> pictures = new ArrayList<Drawable>();
		pictures.add(getResources().getDrawable(R.drawable.first_launch_prev_ico) );
		pictures.add(getResources().getDrawable(R.drawable.first_launch_next_ico) );
		pictures.add(getResources().getDrawable(R.drawable.first_launch_finish_ico) );
		for (Drawable drawable : pictures) {
			ImageView imageView = new ImageView(context);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			imageView.setBackgroundDrawable(drawable);
			this.addView(imageView,layoutParams);
		}
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.first_launch_bottom_background));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childcount = getChildCount();
		int left,right,top,bottom;
		
		for (int i=0; i<childcount ;i++){
			View child = getChildAt(i);
			left = (getMeasuredWidth() - child.getMeasuredWidth())/2;
			right = left + child.getMeasuredWidth();
			top = (getMeasuredHeight() - child.getMeasuredHeight())/2;
			bottom = top + child.getMeasuredHeight();
			child.layout(left, top, right, bottom);
			if (i == childcount -1)
				child.setVisibility(INVISIBLE);
				
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
	}
	
	public void setPreListener(OnClickListener listener){
		getChildAt(0).setOnClickListener(listener);
	}
	public void setNextListener(OnClickListener listener){
		getChildAt(1).setOnClickListener(listener);
	}
	public void setFinishListener(OnClickListener listener){
		getChildAt(2).setOnClickListener(listener);
	}
	
	public void NextRightAnimation(){
		translate(getChildAt(0), true);
		translate(getChildAt(1), false);
	}
	public void NextLeftAnimation(){
		translate(getChildAt(0), false);
		translate(getChildAt(1), true);
	}
	public void LastAnimation(){
		NextLeftAnimation();
		appearAnimation(getChildAt(2));
	}
	
	private void translate(View view,boolean left){
		if (left == true)
			toX = -width/4;	
		else 
			toX = width/4;		
		ObjectAnimator animator = new ObjectAnimator();
		animator.setTarget(view);
		animator.setPropertyName("translationX");
		animator.setFloatValues(view.getTranslationX()+toX);
		animator.setDuration(1000);
		animator.start();
	}
	private void appearAnimation(View view){
		view.setAlpha(0);
		view.setVisibility(VISIBLE);
		ObjectAnimator animator = new ObjectAnimator();
		animator.setTarget(view);
		animator.setPropertyName("aplha");
		animator.setFloatValues(1);
		animator.setDuration(1000);
		animator.start();
	}
	public void setPreClickable(boolean a){
		if (a == true)
			getChildAt(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.first_launch_prev_ico));
		else 
			getChildAt(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.first_launch_prev_ico));
	}
	public void setNextClickable(boolean a){
		if (a == true)
			getChildAt(1).setBackgroundDrawable(getResources().getDrawable(R.drawable.first_launch_next_ico));
		else 
			getChildAt(1).setBackgroundDrawable(getResources().getDrawable(R.drawable.first_launch_next_ico));
	}
	public void setFinishClickable(boolean a){
		if (a == true)
			getChildAt(2).setBackgroundDrawable(getResources().getDrawable(R.drawable.first_launch_finish_ico));
		else 
			getChildAt(2).setBackgroundDrawable(getResources().getDrawable(R.drawable.first_launch_finish_ico));
	}
}