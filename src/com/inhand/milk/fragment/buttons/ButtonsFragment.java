package com.inhand.milk.fragment.buttons;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;
import com.inhand.milk.fragment.bluetooth.bluetooth_fragment;
import com.inhand.milk.fragment.health.HealthFragment;
import com.inhand.milk.fragment.home.HomeFragment;
import com.inhand.milk.fragment.temperature_milk.TempretureMilkFragment;

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
	private TempretureMilkFragment tempreture,amount;
	private bluetooth_fragment bluetooth;
	private HomeFragment home;
	private HealthFragment health;
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
		fragmentTransaction.add(R.id.Activity_fragments_container, home,"HOME"); 
        fragmentTransaction.commit();  
	}
	
	private void initButtons(){
		 ImageButton button1 = (ImageButton)view.findViewById(R.id.buttons_milk_icon);
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
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
					}
					currentFragment = CurrentFragment.AMOUNT;
					
					if(amount == null){
						amount =  new TempretureMilkFragment();
						amount.isTemperature(false);
						fragmentTransaction.add(R.id.Activity_fragments_container, amount, "AMOUNT");
					}
					else{
						fragmentTransaction.show(amount);
						amount.refresh();
					}
					fragmentTransaction.commit();
					
				}    
			});
		 
		 ImageButton button2 = (ImageButton)view.findViewById(R.id.buttons_temperature_icon);
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
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
					}
					currentFragment = CurrentFragment.TEMPERATURE;
					
					if(tempreture == null){
						tempreture =  new TempretureMilkFragment();
						tempreture.isTemperature(true);
						fragmentTransaction.add(R.id.Activity_fragments_container, tempreture, "TENPRETURE");
					}
					else{
						fragmentTransaction.show(tempreture);
						tempreture.refresh();
					}	
					fragmentTransaction.commit();	
				}    
			});
		 
		 ImageButton button3 = (ImageButton)view.findViewById(R.id.buttons_home);
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
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
					}
					currentFragment  = CurrentFragment.HOME;	
					fragmentTransaction.show(home);
					fragmentTransaction.commit();
				}    
			});
		 
		 ImageButton button4 = (ImageButton)view.findViewById(R.id.buttons_health);
		 button4.setOnClickListener(new OnClickListener() {
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
						fragmentTransaction.hide(tempreture);
						break;
					case PERSONCENTER:
						fragmentTransaction.hide(bluetooth);
						break;
					case HEALTH:
						return;
					}
					currentFragment  = CurrentFragment.HEALTH;
					
					if(health == null){
						health = new HealthFragment();
						fragmentTransaction.add(R.id.Activity_fragments_container, health, "HEALTH");
					}
					else 
						fragmentTransaction.show(health);
					
					fragmentTransaction.commit();
				}    
			});
		
		 ImageButton button5 = (ImageButton)view.findViewById(R.id.buttons_person_center);
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
					case HEALTH:
						fragmentTransaction.hide(health);
						break;
				}
				currentFragment = CurrentFragment.PERSONCENTER;
				
				if(bluetooth == null){
					bluetooth = new bluetooth_fragment(  ((MainActivity)ButtonsFragment.this.getActivity() ).getBluetooth());
					fragmentTransaction.add(R.id.Activity_fragments_container, bluetooth,"BLUETOOTH");
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
	
	

