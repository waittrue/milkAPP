package com.inhand.milk.fragment.temperature_milk;

import java.util.ArrayList;
import java.util.List;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;
import com.inhand.milk.fragment.temperature_milk.details.DetailsFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TempretureMilkFragment extends Fragment {
	private int width;
	private LinearLayout.LayoutParams lineParams;
	private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private List<View> listView = new ArrayList<View>();
	private float lineLength ;
	private ImageView imageView; 
	private View  view;
	private TextView day,week,month;
	private float lineScale;//���������������������ҳ�滬������ı�ֵ��
	private float lineCenterX;
	private View layoutDay,layoutWeek,layoutMonth;
	private Excle excleDay,excleWeek,excleMonth;
	private TempratureCircle tpCircleDay,tpCircleWeek,tpCircleMonth;
	private MilkCircle milkCircleDay,milkCircleWeek,milkCircleMonth;
	private boolean isTemperture ;
	private int  selectColor;
	private int  noSelectColor;
	private int State = 0;//0��������״̬�ϣ�1���������ϣ�2����������
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
		 view = inflater.inflate(R.layout.temperature_milk, container, false);
		 initVariable();
	     return view;
    }  
	private void initVariable(){
		DisplayMetrics dm = new DisplayMetrics();
	    getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		width= dm.widthPixels;
	    lineLength = width/7;
    	lineScale = (1 - 0.11f*2)/3;
    	lineCenterX = width*0.11f +width*0.26f/2;
	    
    	selectColor = getResources().getColor(R.color.temperature_milk_checked_color);
    	noSelectColor = getResources().getColor(R.color.temperature_milk_unchecked_color);
    	day = (TextView)view.findViewById(R.id.temperature_milk_day_show);
    	week = (TextView)view.findViewById(R.id.temperature_milk_week_show);
		month = (TextView)view.findViewById(R.id.temperature_milk_month_show);
		LayoutInflater inflater=(LayoutInflater)this.getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutDay = inflater.inflate(R.layout.temperature_milk_circle_excle, null);
		layoutWeek = inflater.inflate(R.layout.temperature_milk_circle_excle, null);
		layoutMonth = inflater.inflate(R.layout.temperature_milk_circle_excle, null);
		initPaperView(view);
		
		addClick(view); 
		drawLine(view);
		
		String title ;
		if(isTemperture == true){
			title =  getResources().getString(R.string.temperature_title_text);
			((TextView)view.findViewById( R.id.health_nutrition_header_layout) ).setText(title);
			drawTemperatrueExcle(view);
			drawTpCircle(view);
		}
		else {
			title = getResources().getString(R.string.milk_title_text);
			((TextView)view.findViewById(R.id.health_nutrition_header_layout) ).setText(title);	
			drawMilkExcle(view);
			drawMilkCircle(view);
		}
		drawPaperView();
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		 super.onStart();
		 android.util.Log.i("temperature", "onstart  ");		
	}
	
	private void drawPaperView(){
		listView.clear();
		listView.add(layoutDay);
		listView.add(layoutWeek);
		listView.add(layoutMonth);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(State);
	}
	
   //����ͼ����ɫ�ĸı�
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
	
	//�������еĵ���¼�
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
	
	
	//���������ƶ����ߣ�
	private void drawLine(View view){
		imageView = new ImageView(this.getActivity().getApplicationContext());
		imageView.setBackgroundColor(selectColor);
		LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.temperature_milk_time_line_layout);
		lineParams = new LinearLayout.LayoutParams((int)lineLength,
							LinearLayout.LayoutParams.MATCH_PARENT);
		lineParams.setMargins( (int)(lineCenterX - lineLength/2 + width*lineScale*State), 0, (int)( width - lineCenterX -lineLength/2 -  width*lineScale*State), 0);
		linearLayout.addView(imageView,lineParams);
		changeTextColor(State);
	}
	
	private void drawMilkExcle(View view){
		
		LinearLayout linearlayout1 = (LinearLayout)layoutDay.findViewById(R.id.temperature_milk_excle_container);
		LinearLayout linearlayout2 = (LinearLayout)layoutWeek.findViewById(R.id.temperature_milk_excle_container);
		LinearLayout linearlayout3 = (LinearLayout)layoutMonth.findViewById(R.id.temperature_milk_excle_container);
		linearlayout1.removeAllViews();
		linearlayout2.removeAllViews();
		linearlayout3.removeAllViews();
		LinearLayout.LayoutParams excleParams = (LinearLayout.LayoutParams)new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.MATCH_PARENT);
		
		String string ;
		
		excleDay = new Excle(getActivity().getApplicationContext());
		excleDay.initDayText();
		string  = getResources().getString(R.string.milk_excle_day_right_title);
		excleDay.setRightTiltle(string);
		string  = getResources().getString(R.string.milk_excle_day_left_title);
		excleDay.setLeftTiltle(string);
		excleDay.setRange(250, 40);	
		excleDay.setOnClickListener(onClickListener);
		excleDay.clearData();
		excleDay.addLine(getMilkDayData(9));		
		linearlayout1.addView(excleDay ,excleParams);
		
		excleWeek = new Excle(getActivity().getApplicationContext());
		excleWeek.initWeekText();
		string = getResources().getString(R.string.milk_excle_week_right_title);
		excleWeek.setRightTiltle(string);
		string = getResources().getString(R.string.milk_excle_week_left_title);
		excleWeek.setLeftTiltle(string);
		excleWeek.setRange(250, 40);
		excleWeek.setOnClickListener(onClickListener);
		excleWeek.clearData();
		excleWeek.addLine(getMilkData(7));	
		linearlayout2.addView(excleWeek ,excleParams);	
		
		excleMonth = new Excle(getActivity().getApplicationContext());
		excleMonth.initMonthText();
		string =  getResources().getString(R.string.milk_excle_month_right_title);
		excleMonth.setRightTiltle(string);
		string =  getResources().getString(R.string.milk_excle_month_left_title);
		excleMonth.setLeftTiltle(string);
		excleMonth.setRange(250, 40);
		excleMonth.setOnClickListener(onClickListener);	
		excleMonth.clearData();
		excleMonth.addLine(getMilkData(30));
		linearlayout3.addView(excleMonth ,excleParams);

	}
	private List<float[]> getMilkDayData(int count){
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<count;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*100+150;
			points.add(a);
		}
		return points;
	}
	private List<float[]> getMilkData(int count){
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<count;i++){
			float[] a = new float[1];
			a[0] =(float) Math.random()*100+150;
			points.add(a);
		}
		return points;
	}
	private  List<float[]> getTpUpDayData(int count){
		
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<count;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*20+30;
			points.add(a);
		}
		return points;
	}
	private List<float[]> getTpdownDayData(int count){
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<count;i++){
			float[] a = new float[2];
			a[0] = 3*i;
			a[1] =(float) Math.random()*30;
			points.add(a);	
		}
		return points;
	}
	private List<float[]> getTpupData(int count){
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<count;i++){
			float[] a = new float[1];
			a[0] =(float) Math.random()*30+20;
			points.add(a);	
		}
		return points;
	}
	
	private List<float[]> getTpdownData(int count){
		List<float[]> points = new ArrayList<float[]>();
		for(int i = 0;i<count;i++){
			float[] a = new float[1];
			a[0] =(float) Math.random()*30;
			points.add(a);	
		}
		return points;
	}
	
 	private  void drawTemperatrueExcle(View  view){
		LinearLayout linearlayout1 = (LinearLayout)layoutDay.findViewById(R.id.temperature_milk_excle_container);
		LinearLayout linearlayout2 = (LinearLayout)layoutWeek.findViewById(R.id.temperature_milk_excle_container);
		LinearLayout linearlayout3 = (LinearLayout)layoutMonth.findViewById(R.id.temperature_milk_excle_container);
		linearlayout1.removeAllViews();
		linearlayout2.removeAllViews();
		linearlayout3.removeAllViews();
		
		LinearLayout.LayoutParams excleParams = (LinearLayout.LayoutParams)new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.MATCH_PARENT);
		
		String string;
		
		excleDay = new Excle(getActivity().getApplicationContext());
		excleDay.initDayText();
		string = getResources().getString(R.string.temperature_excle_day_right_title);
		excleDay.setRightTiltle(string);
		string = getResources().getString(R.string.temperature_excle_day_left_title);
		excleDay.setLeftTiltle(string);
		excleDay.setRange(50, 10);
		excleDay.setOnClickListener(onClickListener);
		excleDay.clearData();
		excleDay.addLine(getTpUpDayData(9));
		excleDay.addLine(getTpdownDayData(9));
		linearlayout1.addView(excleDay ,excleParams);
		
		
		
		excleWeek = new Excle(getActivity().getApplicationContext());
		excleWeek.initWeekText();
		string = getResources().getString(R.string.temperature_excle_week_right_title);
		excleWeek.setRightTiltle(string);
		string = getResources().getString(R.string.temperature_excle_week_left_title);
		excleWeek.setLeftTiltle(string);
		excleWeek.setRange(50, 0);
		excleWeek.setOnClickListener(onClickListener);
		excleWeek.clearData();
		excleWeek.addLine(getTpupData(7));	
		excleWeek.addLine(getTpdownData(7));
		linearlayout2.addView(excleWeek ,excleParams);	
		
		
		excleMonth = new Excle(getActivity().getApplicationContext());
		excleMonth.initMonthText();
		string = getResources().getString(R.string.temperature_excle_month_right_title);
		excleMonth.setRightTiltle(string);
		string = getResources().getString(R.string.temperature_excle_month_left_title);
		excleMonth.setLeftTiltle(string);
		excleMonth.setRange(50, 0);
		excleMonth.setOnClickListener(onClickListener);
		excleMonth.clearData();
		excleMonth.addLine(getTpupData(30));
		excleMonth.addLine(getTpdownData(30));
		linearlayout3.addView(excleMonth,excleParams);
	  
	}
	
	
  private  void  drawTpCircle(View view){
	  
	  float line = (width*0.13f);
      float r = (width*0.35f/2);
      tpCircleDay = new TempratureCircle(this.getActivity().getApplicationContext(), r,line );
      tpCircleWeek = new TempratureCircle(this.getActivity().getApplicationContext(), r,line );
      tpCircleMonth = new TempratureCircle(this.getActivity().getApplicationContext(), r,line );
      LinearLayout linearLayout1 = (LinearLayout)layoutDay.findViewById(R.id.temperature_milk_circle_container);   
      LinearLayout linearLayout2 = (LinearLayout)layoutWeek.findViewById(R.id.temperature_milk_circle_container);   
      LinearLayout linearLayout3 = (LinearLayout)layoutMonth.findViewById(R.id.temperature_milk_circle_container);   
   
    
      
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
      		LinearLayout.LayoutParams.WRAP_CONTENT);
      linearLayout1.removeAllViews();
      linearLayout1.addView(tpCircleDay, params);
      linearLayout2.removeAllViews();
      linearLayout2.addView(tpCircleWeek, params);
      linearLayout3.removeAllViews();
      linearLayout3.addView(tpCircleMonth, params);
      switch (State) {
	      case 0:
			tpCircleDay.start();
			break;
	      case 1:
	    	tpCircleWeek.start();
	    	break;
	      case 2:
	    	tpCircleMonth.start();
		  break;
      }
         
      
  }
  private  void  drawMilkCircle(View view){
	  float r  =  width/5;
	  milkCircleDay= new MilkCircle(this.getActivity().getApplicationContext(), r );
	  milkCircleWeek= new MilkCircle(this.getActivity().getApplicationContext(), r );
	  milkCircleMonth= new MilkCircle(this.getActivity().getApplicationContext(), r );
	  LinearLayout linearLayout1 = (LinearLayout)layoutDay.findViewById(R.id.temperature_milk_circle_container);   
	  LinearLayout linearLayout2 = (LinearLayout)layoutWeek.findViewById(R.id.temperature_milk_circle_container);   
	  LinearLayout linearLayout3 = (LinearLayout)layoutMonth.findViewById(R.id.temperature_milk_circle_container);   	      
	  
	  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
	      		LinearLayout.LayoutParams.WRAP_CONTENT);
	  linearLayout1.removeAllViews();
	  linearLayout2.removeAllViews();
	  linearLayout3.removeAllViews();
	  linearLayout1.addView(milkCircleDay, params);
	  linearLayout2.addView(milkCircleWeek, params);
	  linearLayout3.addView(milkCircleMonth, params);
	      
	  switch (State) {
		  case 0:
			  milkCircleDay.start();
			  break;
		  case 1:
			  milkCircleWeek.start();
			  break;
		  case 2:
			  milkCircleMonth.start();
			  break;
	  }
  }
	
	

  private void initPaperView(View view) {
	  	Log.i("initPaperView", "papaerview");
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
				if(arg2 != 0 || arg1 !=0){//��ʼ��ʱ��  arg2=0  arg1=0  Ҫ�����״̬�ų���  
					float startx = arg0*width*lineScale;
					lineParams.setMargins( (int)( startx + lineCenterX -lineLength/2 +arg2*lineScale ), 0,
										(int)(	width - startx - lineCenterX - lineLength/2 -arg2*lineScale), 0);
					imageView.setLayoutParams(lineParams);
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
		isTemperture = t;
	}
	
	public int  getState (){
		return State;
	}
	public void  setState(int arg){
		State = arg;
	}
	
	public void refresh(){
	
		 if(isTemperture == true){
			 excleDay.clearData();
			 excleWeek.clearData();
			 excleMonth.clearData();
			 
			 excleDay.addLine(getTpUpDayData(9));
			 excleDay.addLine(getTpdownDayData(9));
			 excleWeek.addLine(getTpupData(7));
			 excleWeek.addLine(getTpdownData(7));
			 excleMonth.addLine(getTpdownData(30));
			 excleMonth.addLine(getTpupData(30));
			 excleDay.invalidate();
			 excleMonth.invalidate();
			 excleWeek.invalidate();
			 switch (State) {
			 	case 0:
			 		tpCircleDay.start();
			 		break;
			 	case 1:
			 		tpCircleWeek.start();
			 		break;
			 	case 2:
			    	tpCircleMonth.start();
			    	break;
		      }
		 }
		 else {
			 excleDay.clearData();
			 excleWeek.clearData();
			 excleMonth.clearData();
			 
			 excleDay.addLine(getMilkDayData(9));
			 excleWeek.addLine(getMilkData(7));
			 excleMonth.addLine(getMilkData(30));
			 
			 excleDay.invalidate();
			 excleMonth.invalidate();
			 excleWeek.invalidate();
			 
			 switch (State) {
			  case 0:
				  milkCircleDay.start();
				  break;
			  case 1:
				  milkCircleWeek.start();
				  break;
			  case 2:
				  milkCircleMonth.start();
				  break;
			  }
		 }
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FragmentManager fragmentManager = getFragmentManager();  
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
			fragmentTransaction.hide(TempretureMilkFragment.this);
			fragmentTransaction.add(R.id.Activity_fragments_container , new DetailsFragment(), "details");
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			( (MainActivity)getActivity() ).inVisibleButtons();
		}
	};
}