package com.example.details;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;



import com.example.aaaa.MainActivity;
import com.example.aaaa.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class details_fragment  extends Fragment{

	private View view;
	private  PinnedHeaderListView  listview;


	List<ItemEntity> listItem = new ArrayList<ItemEntity>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view  = inflater.inflate(R.layout.details,container, false);
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listview = (PinnedHeaderListView)view.findViewById(R.id.listView);
		//listview.setHeadView( getHeadView() );
		listview.setHeadView(getHeadView());
		getData();
		myAdapter adapter = new myAdapter(this.getActivity().getApplicationContext(),listItem);
		
		listview.setAdapter( adapter);
		setBack();
		
	}
	
	private View getHeadView(){
		View headview ;
		LayoutInflater inflater = getActivity().getLayoutInflater();//LayoutInflater.from(getActivity().getApplicationContext());
		headview = inflater.inflate(R.layout.listdetails2, listview,false);
		//headview.setBackgroundColor(Color.GREEN);
		return headview;
	}
	
	
	//先这样，以后输入参数是日期
	private  void  getData(){
		for(int j =0 ;j<3;j++){
			
			for(int i=0;i<8;i++){
				String title  ;
				Calendar calendar = Calendar.getInstance();
				calendar.roll(Calendar.DATE, -j);
				int day = calendar.get(Calendar.DATE) ;
				int month = calendar.get(Calendar.MONTH);
				title = String.valueOf(month)+"月"+String.valueOf(day)+"日";
				
				
				String  amount =   String.valueOf( (int)( Math.random()*150) );
				
				String  time = "22:30";
				
			    ItemEntity  itemEntity = new ItemEntity(title, 0, amount, time);
				listItem.add(itemEntity);	
				}
			}
		}
	
	

	
	
	private void setBack(){
		ImageView back = (ImageView)view.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();  
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
				fragmentManager.popBackStack();
				( (MainActivity) getActivity()).visibleButtons();
			}
		});
		
	}
	
	public class ItemEntity {
		private  String mTitle;
		private   int  mPicture;
		private  String mAmount;
		private  String mTime;
		public ItemEntity(String title,int picture,String amount,String time) {
			// TODO Auto-generated constructor stub
			mTime = time;
			mPicture = picture;
			mTitle =title;
			mAmount = amount;
		}
		
		public String getTitle() {
			return mTitle;
		}
		public int getPicture() {
			return mPicture;
			
		}
		public String getAmount() {
			return mAmount;
			
		}
		public String getTime() {
			return mTime;
		}
	}
	
	/*
	
	
	  private List<ItemEntity> createTestData() {
	        
	        List<ItemEntity> data = new ArrayList<ItemEntity>();
	        
	        ItemEntity itemEntity1 = new ItemEntity("路人甲", "马三立");
	        ItemEntity itemEntity2 = new ItemEntity("路人甲", "赵本山");
	        ItemEntity itemEntity3 = new ItemEntity("路人甲", "郭德纲");
	        ItemEntity itemEntity4 = new ItemEntity("路人甲", "周立波");
	        ItemEntity itemEntity5 = new ItemEntity("事件乙", "**贪污");
	        ItemEntity itemEntity6 = new ItemEntity("事件乙", "**照门");
	        ItemEntity itemEntity7 = new ItemEntity("书籍丙", "10天学会***");
	        ItemEntity itemEntity8 = new ItemEntity("书籍丙", "**大全");
	        ItemEntity itemEntity9 = new ItemEntity("书籍丙", "7天精通**");
	        ItemEntity itemEntity10 = new ItemEntity("书籍丙", "**秘籍");
	        ItemEntity itemEntity11 = new ItemEntity("书籍丙", "**宝典");
	        ItemEntity itemEntity12 = new ItemEntity("地方丁", "河南");
	        ItemEntity itemEntity13 = new ItemEntity("地方丁", "天津");
	        ItemEntity itemEntity14 = new ItemEntity("地方丁", "北京");
	        ItemEntity itemEntity15 = new ItemEntity("地方丁", "上海");
	        ItemEntity itemEntity16 = new ItemEntity("地方丁", "广州");
	        ItemEntity itemEntity17 = new ItemEntity("地方丁", "湖北");
	        ItemEntity itemEntity18 = new ItemEntity("地方丁", "重庆");
	        ItemEntity itemEntity19 = new ItemEntity("地方丁", "山东");
	        ItemEntity itemEntity20 = new ItemEntity("地方丁", "陕西");
	        
	        data.add(itemEntity1);
	        data.add(itemEntity2);
	        data.add(itemEntity3);
	        data.add(itemEntity4);
	        data.add(itemEntity5);
	        data.add(itemEntity6);
	        data.add(itemEntity7);
	        data.add(itemEntity8);
	        data.add(itemEntity9);
	        data.add(itemEntity10);
	        data.add(itemEntity11);
	        data.add(itemEntity12);
	        data.add(itemEntity13);
	        data.add(itemEntity14);
	        data.add(itemEntity15);
	        data.add(itemEntity16);
	        data.add(itemEntity17);
	        data.add(itemEntity18);
	        data.add(itemEntity19);
	        data.add(itemEntity20);
	        
	        return data;
	        
	    }
	    
	    // ===========================================================
	    // Inner and Anonymous Classes
	    // ===========================================================
		
		class ItemEntity {
			private String mTitle;
			private String mContent;
			
			public ItemEntity(String pTitle, String pContent) {
				mTitle = pTitle;
				mContent = pContent;
			}
			
			public String getTitle() {
				return mTitle;
			}
			public String getContent() {
				return mContent;
			}
		}
		*/
	}
	
