package com.inhand.milk.temperature;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import com.example.aaaa.R;
import com.inhand.milk.MainActivity;
import com.inhand.milk.details.details_fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class tempreture_milk_fragment extends Fragment {
	
	private int width,height;
	
	private LinearLayout.LayoutParams line_params;
	private ViewPager viewPager;
	private boolean isInit = false;
    private PagerAdapter pagerAdapter;
    private List<View> listView = new ArrayList<View>();

	private float lineLength = 53;//sp
	private ImageView imageView; 
	private View  view;
	private TextView day,week,month;
	private float bili;//上面线条滑动距离和下面页面滑动距离的比值；
	private float lineCenterX;
	private View layout1,layout2,layout3;
	private boolean temperture ;
	private int  selectColor = Color.rgb(0xb2, 0xeb, 0xf9);
	private int  noSelectColor = Color.rgb(0x02,0xb4,0xba);
	private int State = 0;//0代表在日状态上，1代表在周上，2代表在月上
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
		 view = inflater.inflate(R.layout.temperature_milk, container, false);

		 android.util.Log.i("oncreateView", "1111111  ");
	     
	     return view;

    }  
	private void init(View view){
		//先得到屏幕大小px
		
		 DisplayMetrics dm = new DisplayMetrics();
	     getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		 width= dm.widthPixels;
	     height = dm.heightPixels;
	     
	  
	     //将单位进行转换  还有一些基本的比例转换
	     if(isInit == false){
	    	 float  scale = this.getResources().getDisplayMetrics().density;
	    	 lineLength = lineLength *scale;
	    	 bili = (1 - 0.11f*2)/3;
	    	 lineCenterX = width*0.11f +width*0.26f/2;
	    	 isInit = true;
	    	 }
		 
		 //初始化一些视图部件
		  day = (TextView)view.findViewById(R.id.day);
		  week = (TextView)view.findViewById(R.id.week);
		  month = (TextView)view.findViewById(R.id.month);
		  LayoutInflater inflater=(LayoutInflater)this.getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  layout1 = inflater.inflate(R.layout.circle_excle, null);
		  layout2 = inflater.inflate(R.layout.circle_excle, null);
		  layout3 = inflater.inflate(R.layout.circle_excle, null);
		  
		  //最后初始化viewpaper,主要是适配器和监视器
		  initPaperView(view);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		 super.onStart();
		 android.util.Log.i("onstart", "111111111  ");	
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 android.util.Log.i("onresume", " 1111111 ");
		 
		 init(view);
		 addClick(view); 
		 drawLine(view);
		 //Toast.makeText(this.getActivity().getApplicationContext(), String.valueOf(lineLength), Toast.LENGTH_SHORT).show();
		 
		 if(temperture == true){
			
			  ((TextView)view.findViewById(R.id.title) ).setText("饮奶温度统计");
			 drawTpExcle(view);
			 drawTpCircle(view);
		 }
		 else {
			 ((TextView)view.findViewById(R.id.title) ).setText("饮奶奶量统计");	
			 drawMilkExcle(view);
			 drawMilkCircle(view);
		 }
		 drawPaperView();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 android.util.Log.i("onpause", " 11111 ");
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		 android.util.Log.i("onstop", " 1111 ");
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		 android.util.Log.i("ondestroyview", " 111 ");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 android.util.Log.i("ondestroy", " 1111 ");
	}
	private void drawPaperView(){
		listView.add(layout1);
		listView.add(layout2);
		listView.add(layout3);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(State);
	}
   //三个图标颜色的改变
	private void changeTextColor(int arg){
		switch (arg) {
		case 0:
			day.setTextColor(selectColor);
			week.setTextColor(noSelectColor);
			month.setTextColor(noSelectColor);
			break;
		case 1:
			day.setTextColor(noSelectColor);
			week.setTextColor(selectColor);
			month.setTextColor(noSelectColor);
			break;
		case 2:
			day.setTextColor(noSelectColor);
			week.setTextColor(noSelectColor);
			month.setTextColor(selectColor);
			break;	
		}
	}
	//添加所有的点击事件
	private void addClick(View view){
		
		day.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				changeTextColor(0);
				viewPager.setCurrentItem(0, true);
			}
		});
		week.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				changeTextColor(1);
				viewPager.setCurrentItem(1, true);
			
			}
		});
		month.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				changeTextColor(2);
				viewPager.setCurrentItem(2, true);
			}
		});
		 
		 
	}
	//画最上面移动的线，
	private void drawLine(View view){
		imageView = new ImageView(this.getActivity().getApplicationContext());
		imageView.setBackgroundColor(selectColor);
		LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.line_move);
		line_params = new LinearLayout.LayoutParams((int)lineLength,
							LinearLayout.LayoutParams.MATCH_PARENT);
		line_params.setMargins( (int)(lineCenterX - lineLength/2 + width*bili*State), 0, (int)( width - lineCenterX -lineLength/2 -  width*bili*State), 0);
		linearLayout.addView(imageView,line_params);
		changeTextColor(State);
	}
	
	private void drawMilkExcle(View view){
		if (layout1 == null){
			Toast.makeText(getActivity(), "layout1 ==null", Toast.LENGTH_SHORT).show();
			return;
		}
		LinearLayout linearlayout1 = (LinearLayout)layout1.findViewById(R.id.excle);
		LinearLayout linearlayout2 = (LinearLayout)layout2.findViewById(R.id.excle);
		LinearLayout linearlayout3 = (LinearLayout)layout3.findViewById(R.id.excle);
		linearlayout1.removeAllViews();
		linearlayout2.removeAllViews();
		linearlayout3.removeAllViews();
		LinearLayout.LayoutParams excleParams = (LinearLayout.LayoutParams)new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.MATCH_PARENT);
		
		
		Excle excle = new Excle(getActivity().getApplicationContext());
		excle.initDayText();
		excle.setRightTiltle("今天");
		excle.setLeftTiltle("饮奶奶量/ml");
		excle.setRange(250, 40);
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<9;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*100+150;
			points.add(a);
			
		}
		excle.setOnClickListener(onClickListener);
		excle.addLine(points);		
		
		linearlayout1.addView(excle ,excleParams);
		
		
		
		
		excle = new Excle(getActivity().getApplicationContext());
		excle.initWeekText();
		excle.setRightTiltle("本周");
		excle.setLeftTiltle("饮奶奶量/ml");
		excle.setRange(250, 40);
		excle.setOnClickListener(onClickListener);
		points = new ArrayList<float[]>();
		for(int i = 0;i<7;i++){
			
			float[] a = new float[1];
			a[0] =(float) Math.random()*100+150;
			points.add(a);
			
		}
		excle.addLine(points);	
		linearlayout2.addView(excle ,excleParams);	
		
		
		excle = new Excle(getActivity().getApplicationContext());
		excle.initMonthText();
		excle.setRightTiltle("本月");
		excle.setLeftTiltle("饮奶奶量/ml");
		excle.setRange(250, 40);
		excle.setOnClickListener(onClickListener);
		points = new ArrayList<float[]>();
			for(int i = 0;i<30;i++){
				
				float[] a = new float[1];
				a[0] =(float) Math.random()*100+150;
				points.add(a);
			}
			
		excle.addLine(points);
		
   
		linearlayout3.addView(excle ,excleParams);

	}
	private  List<float[]> getTpUpDayData(){
		
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<9;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*20+30;
			points.add(a);
			
		}
		return points;
	}
	private List<float[]> getTpdownDayData(){
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<9;i++){
			
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*30;
			points.add(a);
			
		}
		return points;
	}
	private  List<float[]> getTpUpWeekData(){
		List<float[]>  points = new ArrayList<float[]>();
		for(int i = 0;i<7;i++){	
			float[] a = new float[1];
			a[0] =(float) Math.random()*30+20;
			points.add(a);	
		}
		return points;
	}
	private  List<float[]> getTpDownWeekData(){
		List<float[]>  points = new ArrayList<float[]>();
		for(int i = 0;i<7;i++){	
			float[] a = new float[1];
			a[0] =(float) Math.random()*20;
			points.add(a);	
		}
		return points;
	}
	
	private  List<float[]> getTpUpMonthData(){
		List<float[]>  points = new ArrayList<float[]>();
		for(int i = 0;i<30;i++){	
			float[] a = new float[1];
			a[0] =(float) Math.random()*20 +30;
			points.add(a);	
		}
		return points;
	}
	
	private  List<float[]> getTpDownMonthData(){
		List<float[]>  points = new ArrayList<float[]>();
		for(int i = 0;i<30;i++){	
			float[] a = new float[1];
			a[0] =(float) Math.random()*20;
			points.add(a);	
		}
		return points;
	}
 	private  void drawTpExcle(View  view){
		LinearLayout linearlayout1 = (LinearLayout)layout1.findViewById(R.id.excle);
		LinearLayout linearlayout2 = (LinearLayout)layout2.findViewById(R.id.excle);
		LinearLayout linearlayout3 = (LinearLayout)layout3.findViewById(R.id.excle);
		linearlayout1.removeAllViews();
		linearlayout2.removeAllViews();
		linearlayout3.removeAllViews();
		
		LinearLayout.LayoutParams excleParams = (LinearLayout.LayoutParams)new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.MATCH_PARENT);
		
		linearlayout1.removeAllViews();
		Excle excle = new Excle(getActivity().getApplicationContext());
		excle.initDayText();
		excle.setRightTiltle("今天");
		excle.setLeftTiltle("饮奶温度/c°");
		excle.setRange(50, 10);
		excle.setOnClickListener(onClickListener);
		excle.addLine(getTpUpDayData());
		excle.addLine(getTpdownDayData());
		
	//	Toast.makeText(this.getActivity().getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
		linearlayout1.addView(excle ,excleParams);
		
		
		
		excle = new Excle(getActivity().getApplicationContext());
		excle.initWeekText();
		excle.setRightTiltle("本周");
		excle.setLeftTiltle("饮奶温度/c°");
		excle.setRange(50, 0);
		excle.setOnClickListener(onClickListener);
		excle.addLine(getTpUpWeekData());	
		excle.addLine(getTpDownWeekData());
		linearlayout2.addView(excle ,excleParams);	
		
		
		excle = new Excle(getActivity().getApplicationContext());
		excle.initMonthText();
		excle.setRightTiltle("本月");
		excle.setLeftTiltle("饮奶温度/c°");
		excle.setRange(50, 0);
		excle.setOnClickListener(onClickListener);
		excle.addLine(getTpUpMonthData());
		excle.addLine(getTpDownMonthData());
		linearlayout3.addView(excle ,excleParams);
	  
	}
	
	
  private  void  drawTpCircle(View view){
	  
	  float line = (width*0.13f);
      float r = (width*0.35f/2);
      tpCircle circle1 = new tpCircle(this.getActivity().getApplicationContext(), r,line );
      tpCircle circle2 = new tpCircle(this.getActivity().getApplicationContext(), r,line );
      tpCircle circle3 = new tpCircle(this.getActivity().getApplicationContext(), r,line );
      LinearLayout linearLayout1 = (LinearLayout)layout1.findViewById(R.id.home_circle);   
      LinearLayout linearLayout2 = (LinearLayout)layout2.findViewById(R.id.home_circle);   
      LinearLayout linearLayout3 = (LinearLayout)layout3.findViewById(R.id.home_circle);   
   
    
      
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
      		LinearLayout.LayoutParams.WRAP_CONTENT);
      linearLayout1.removeAllViews();
      linearLayout1.addView(circle1, params);
      linearLayout2.removeAllViews();
      linearLayout2.addView(circle2, params);
      linearLayout3.removeAllViews();
      linearLayout3.addView(circle3, params);
      switch (State) {
      case 0:
		circle1.start();
		Toast.makeText(getActivity().getApplicationContext(), "0state",Toast.LENGTH_SHORT).show();
		break;
      case 1:
    	circle2.start();
    	Toast.makeText(getActivity().getApplicationContext(), "1state",Toast.LENGTH_SHORT).show();
    	break;
      case 2:
    	circle3.start();
    	Toast.makeText(getActivity().getApplicationContext(), "2state",Toast.LENGTH_SHORT).show();
	  break;
	}
         
      
  }
private  void  drawMilkCircle(View view){
	  
	//  float line = (width*0.13f);
      //float r = (width*0.35f/2);
	float r  =  width/5;
       milkCircle circle1= new milkCircle(this.getActivity().getApplicationContext(), r );
       milkCircle circle2= new milkCircle(this.getActivity().getApplicationContext(), r );
       milkCircle circle3= new milkCircle(this.getActivity().getApplicationContext(), r );
      LinearLayout linearLayout1 = (LinearLayout)layout1.findViewById(R.id.home_circle);   
      LinearLayout linearLayout2 = (LinearLayout)layout2.findViewById(R.id.home_circle);   
      LinearLayout linearLayout3 = (LinearLayout)layout3.findViewById(R.id.home_circle);   
   
    
      
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
      		LinearLayout.LayoutParams.WRAP_CONTENT);
      linearLayout1.removeAllViews();
      linearLayout2.removeAllViews();
      linearLayout3.removeAllViews();
      linearLayout1.addView(circle1, params);
      linearLayout2.addView(circle2, params);
      linearLayout3.addView(circle3, params);
      
      switch (State) {
      case 0:
		circle1.start();
		break;
      case 1:
    	circle2.start();
    	break;
      case 2:
    	  circle3.start();
		break;
	}
             
  }
	
	

	private void initPaperView(View view) {
		// TODO Auto-generated method stub
		viewPager  = (ViewPager)view.findViewById(R.id.horizontalScrollView1);    
		 
		pagerAdapter = new PagerAdapter() {
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				container.removeView(listView.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				container.addView(listView.get(position),0);
				
				return  listView.get(position);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return listView.size();
			}
		};

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				changeTextColor(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub				
					if(arg2 != 0 || arg1 !=0){//初始的时候  arg2=0  arg1=0  要把这个状态排除在  
						float startx = arg0*width*bili;
						line_params.setMargins( (int)( startx + lineCenterX -lineLength/2 +arg2*bili ) , 0,
							(int)(	width - startx - lineCenterX - lineLength/2 -arg2*bili), 0);
						imageView.setLayoutParams(line_params);
					}		
					State = arg0;
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub	
			}
		});
		
	}
	
	public void isTemperature(boolean t){
		temperture = t;
	}
	
	public int  getState (){
		return State;
	}
	public void  setState(int arg){
		State = arg;
	}
	
	public void refresh(){
		 
		// listView.clear();
		 if(temperture == true){
			
			 drawTpExcle(view);
			 drawTpCircle(view);
		 }
		 else {
			 drawMilkExcle(view);
			 drawMilkCircle(view);
		 }
		// drawPaperView();
		// Toast.makeText(this.getActivity().getApplicationContext(), String.valueOf( Math.random()*20 ),Toast.LENGTH_SHORT).show();
		 
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(v.getContext(),"dianle", Toast.LENGTH_SHORT).show();
			FragmentManager fragmentManager = getFragmentManager();  
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
			fragmentTransaction.hide(tempreture_milk_fragment.this);
			fragmentTransaction.add(R.id.translate , new details_fragment(), "details");
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			( (MainActivity)getActivity() ).inVisibleButtons();
		}
	};
}
