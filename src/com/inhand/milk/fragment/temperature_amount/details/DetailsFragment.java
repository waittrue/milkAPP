package com.inhand.milk.fragment.temperature_amount.details;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;
import com.inhand.milk.fragment.TitleFragment;
import com.inhand.milk.fragment.temperature_amount.details_once.DetailsOnceFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsFragment  extends TitleFragment{

	private  PinnedHeaderListView  listView;
	private List<ItemEntity> listItem = new ArrayList<ItemEntity>();
	private boolean isTemperature = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView  = inflater.inflate(R.layout.temperature_amount_details,container, false);
		if (!isTemperature)
			setTitleview(getString(R.string.details_milk_title_string), 2);
		else 
			setTitleview(getString(R.string.details_temperature_title_string), 2);
		listView = (PinnedHeaderListView)mView.findViewById(R.id.detals_listView);
		listView.setHeadView(getHeadView());
		getData();
		PinnedListViewAdapter adapter = new PinnedListViewAdapter(this.getActivity().getApplicationContext(),
								listItem);
		listView.setAdapter( adapter);
		setItemOnclick();
		return mView;
	}
	public void setTemperature(boolean aa) {
		this.isTemperature = aa;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private View getHeadView(){
		View headview ;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		headview = inflater.inflate(R.layout.temperature_amount_details_header, listView,false);	
		return headview;
	}
	
	
	//先这样，以后输入参数是日期
	private  void  getData(){
		for(int j =0 ;j<3;j++){
			for(int i=0;i<8;i++){
				String title ,amount ,time;
				Drawable picture = null;
				Calendar calendar = Calendar.getInstance();
				calendar.roll(Calendar.DATE, -j);
				int day = calendar.get(Calendar.DATE) ;
				int month = calendar.get(Calendar.MONTH);
				title = String.valueOf(month)+"月"+String.valueOf(day)+"日";
				if (isTemperature == false){
					amount = String.valueOf( (int)( Math.random() * 20 + 30 ) )+" ~ "+
									String.valueOf( (int)( Math.random() * 20 ) )+"°";
					if (i / 2 == 0)
						picture = getResources().getDrawable(R.drawable.details_temperature_normal);
					else 
						picture = getResources().getDrawable(R.drawable.details_temperature_warning);
					}
				else {
					amount =   String.valueOf( (int)( Math.random()*150) );
					if( Integer.parseInt(	amount ) < 100)
						picture =  getResources().getDrawable(R.drawable.details_amount_warning);			
					else 
						picture =  getResources().getDrawable(R.drawable.details_amount_normal);
					amount = amount +"ml";
					}
				time = "22:30";
			    ItemEntity  itemEntity = new ItemEntity(title, picture, amount, time);
				listItem.add(itemEntity);	
				}
			}
		}
	
	private void setItemOnclick(){
		PinnedHeaderListView listView = (PinnedHeaderListView)mView.findViewById(R.id.detals_listView);
		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.hide(DetailsFragment.this);
				fragmentTransaction.add(R.id.Activity_fragments_container, new DetailsOnceFragment(),
						"detailsOnce");
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		};
		listView.setOnItemClickListener(listener);
	}
	public class ItemEntity {
		private  String mTitle;
		private  Drawable  mPicture;
		private  String mAmount;
		private  String mTime;
		public ItemEntity(String title,Drawable picture,String amount,String time) {
			// TODO Auto-generated constructor stub
			mTime = time;
			mPicture = picture;
			mTitle =title;
			mAmount = amount;
		}
		
		public String getTitle() {
			return mTitle;
		}
		public Drawable getPicture() {
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
	
