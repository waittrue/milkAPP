package com.inhand.milk.fragment.temperature_milk.details;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DetailsFragment  extends Fragment{

	private View view;
	private  PinnedHeaderListView  listView;
	private List<ItemEntity> listItem = new ArrayList<ItemEntity>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view  = inflater.inflate(R.layout.temperature_milk_details,container, false);
		listView = (PinnedHeaderListView)view.findViewById(R.id.listView);
		listView.setHeadView(getHeadView());
		getData();
		PinnedListViewAdapter adapter = new PinnedListViewAdapter(this.getActivity().getApplicationContext(),listItem);
		listView.setAdapter( adapter);
		setBack();
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private View getHeadView(){
		View headview ;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		headview = inflater.inflate(R.layout.temperature_milk_details_header, listView,false);
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
		ImageView back = (ImageView)view.findViewById(R.id.health_nutrition_back);
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
}
	
