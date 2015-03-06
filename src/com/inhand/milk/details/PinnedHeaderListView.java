package com.inhand.milk.details;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PinnedHeaderListView  extends ListView{
	
	
	
	private  boolean drawHead = true;
	private View headView;
	private myAdapter myAdapter;
	private int mMeasureWidth;
	private int mMeasureHeight;
	
	
	public PinnedHeaderListView(Context context) {
		super(context);
	}
	
	public PinnedHeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public PinnedHeaderListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		Log.i("setAdapter", "1111111");
		super.setAdapter(adapter);
		myAdapter =	(myAdapter) adapter;
	   // this.setOnScrollListener(onScrollListener);
	}

	public void setHeadView(View v){
		headView = v;
		Log.i("setHeadView", "1111111");
		requestLayout();
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.i("onmeasure", "1111111");
		if ( headView != null){
			
			
			Log.i("minHeight", String.valueOf( 00000 ));
			
			measureChild(headView, widthMeasureSpec, heightMeasureSpec);
			
			mMeasureHeight = headView.getMeasuredHeight();
			mMeasureWidth  = headView.getMeasuredWidth();
			Log.i("h or w", String.valueOf(mMeasureHeight) +" "+ String.valueOf(mMeasureWidth));
		}
			
	}




	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		Log.i("onlayout", "1111111");
		if (headView != null){
			controlHeadView( getFirstVisiblePosition() );
		}
	}
	
	



	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		Log.i("dispatchDraw", "1111111");
		if (headView != null && drawHead){
			drawChild(canvas, headView, getDrawingTime());
			controlHeadView( getFirstVisiblePosition() );
		}
	}





	private OnScrollListener onScrollListener =  new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			controlHeadView(firstVisibleItem);
		}
	};
	
	private void controlHeadView(int position){
		if (headView == null){
			drawHead = false;
			return ;
		}
		int state = myAdapter.getHeadViewState(position);
		
		switch (state) {
		case HeadViewManager.HEAD_VIEW_GONE:
			 drawHead = false;
			 break;
			 
			 
		case HeadViewManager.HEAD_VIEW_VISIBLE:
			 myAdapter.configureHeadView(headView, position);	
			 headView.layout(0, 0, mMeasureWidth, mMeasureHeight);
			 drawHead = true;
			 break;
		case HeadViewManager.HEAD_VIEW_MOVE:
			myAdapter.configureHeadView(headView, position);	
			 drawHead = true;
			 View child =  getChildAt(0);
			 if (child != null){
				 int bottom = child.getBottom();
				 int height = headView.getHeight();//����Ҫ�� ���ⳤ��   С��   ���ݳ���
				 int y = bottom - height;
				 if ( y > 0 )
					 y = 0;
				 headView.layout(0, y, mMeasureWidth, mMeasureHeight + y);
			 }
				 
			break;
		}
	}
	

	public interface HeadViewManager{
		public static final int HEAD_VIEW_GONE = 0;
		public static final int HEAD_VIEW_VISIBLE = 1;
		public static final int HEAD_VIEW_MOVE = 2;
		int getHeadViewState(int position);
		void configureHeadView(View view ,int position);
	}
}
