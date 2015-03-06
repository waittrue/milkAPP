package com.inhand.milk;

import com.example.aaaa.R;
import com.inhand.milk.bluetooth.bluetooth_fragment;
import com.inhand.milk.temperature.tempreture_milk_fragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ButtonsFragment extends Fragment {

	private View view;
	private tempreture_milk_fragment tempreture,amount;
	private bluetooth_fragment bluetooth;
	private HomeFragment home;
	private CurrentFragment currentFragment = CurrentFragment.HOME; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view  = inflater.inflate(R.layout.buttons, null);
		Log.i("buttons", "oncreateview");
		return view;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("buttons", "onStart");
		attchHome();
		initButtons();
	}
	
	private void attchHome(){
		FragmentManager  fragmentManager = getFragmentManager();  
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (home ==null)
			home = new HomeFragment();
		fragmentTransaction.add(R.id.translate, home,"HOME"); 
        fragmentTransaction.commit();  
	}
	
	private void initButtons(){
		 ImageButton button1 = (ImageButton)view.findViewById(R.id.imageButton1);
		 button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					FragmentManager fragmentManager = getFragmentManager();  
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
					
					switch (currentFragment) {
					case  HOME:
						fragmentTransaction.hide(home);
						break;
					case AMOUNT:
						return;
					case TEMPERATURE:
						fragmentTransaction.hide(tempreture);
						break;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					}
					currentFragment = CurrentFragment.AMOUNT;
					
					if(amount == null){
						amount =  new tempreture_milk_fragment();
						amount.isTemperature(false);
						fragmentTransaction.add(R.id.translate, amount, "AMOUNT");
					}
					else{
						fragmentTransaction.show(amount);
					}
					fragmentTransaction.commit();
					amount.refresh();
				}    
			});
		 
		 ImageButton button2 = (ImageButton)view.findViewById(R.id.imageButton2);
		 button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					FragmentManager fragmentManager = getFragmentManager();  
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
					
					switch (currentFragment) {
					case  HOME:
						fragmentTransaction.hide(home);
						break;
					case AMOUNT:
						fragmentTransaction.hide(amount);
						break;
					case TEMPERATURE:
						return;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					}
					currentFragment = CurrentFragment.TEMPERATURE;
					
					if(tempreture == null){
						tempreture =  new tempreture_milk_fragment();
						tempreture.isTemperature(true);
						fragmentTransaction.add(R.id.translate, tempreture, "TENPRETURE");
					}
					else{
						fragmentTransaction.show(tempreture);
					}
					//tempreture.refresh();
					fragmentTransaction.commit();
					
				}    
			});
		 
		 ImageButton button3 = (ImageButton)view.findViewById(R.id.imageButton3);
		 button3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					FragmentManager fragmentManager = getFragmentManager();  
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
		 
					switch (currentFragment) {
					case  HOME:
						return;
					case AMOUNT:
						fragmentTransaction.hide(amount);
						break;
					case TEMPERATURE:
						fragmentTransaction.hide(tempreture);
						break;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					}
					currentFragment  = CurrentFragment.HOME;	
					fragmentTransaction.show(home);
					fragmentTransaction.commit();
				}    
			});
		
		 ImageButton button5 = (ImageButton)view.findViewById(R.id.imageButton5);
		 button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();  
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
				
				switch (currentFragment) {
				case  HOME:
					fragmentTransaction.hide(home);
					break;
				case AMOUNT:
					fragmentTransaction.hide(amount);
					break;
				case TEMPERATURE:
					fragmentTransaction.hide(tempreture);
					break;
				case PERSONCENTER:
					return;
				}
				currentFragment = CurrentFragment.HEALTH;
				
				if(bluetooth == null){
					bluetooth = new bluetooth_fragment(  ((MainActivity)ButtonsFragment.this.getActivity() ).getBluetooth());
					fragmentTransaction.add(R.id.translate, bluetooth,"BLUETOOTH");
				}
				else {
					fragmentTransaction.show(bluetooth);
				}
				fragmentTransaction.commit();
			}		
		});
		 
	}
	
	private enum CurrentFragment{
		HOME,TEMPERATURE,AMOUNT,HEALTH,PERSONCENTER
	} 
}
	
	

