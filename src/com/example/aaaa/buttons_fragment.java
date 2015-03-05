package com.example.aaaa;

import com.example.bluetooth.Bluetooth;
import com.example.bluetooth.bluetooth_fragment;
import com.example.details.details_fragment;
import com.example.temperature.tempreture_milk_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class buttons_fragment extends Fragment {

	private View view;
	android.app.FragmentManager fragmentManager ;
	android.app.FragmentTransaction fragmentTransaction;

	private tempreture_milk_fragment tempreture,milk;
	private bluetooth_fragment bluetooth;
	private Home_Fragment home;
	private  int height =45	;//dp
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view  = inflater.inflate(R.layout.buttons, null);
		float scale = this.getResources().getDisplayMetrics().density;
		//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		//		(int)(height*scale) );
		//view.setLayoutParams(params);
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fragmentManager = getFragmentManager();  
		fragmentTransaction = fragmentManager.beginTransaction();  
		
		home = new Home_Fragment();
		fragmentTransaction.add(R.id.translate, home,"home"); 
		details_fragment details = new details_fragment();
		//fragmentTransaction.add(R.id.translate,details, "detals");
        fragmentTransaction.commit();  
      
       
         ImageButton button3 = ( ImageButton)view.findViewById(R.id.imageButton3);
		 button3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					fragmentManager = getFragmentManager();  
					fragmentTransaction = fragmentManager.beginTransaction(); 
			       if (tempreture != null){
			    	   if (milk != null)
			    		   if( !milk.isHidden())
			    			   fragmentTransaction.hide(milk);
			    	   
			    	   if( !home.isHidden())
			    		   fragmentTransaction.hide(home);
			    	   if (tempreture.isHidden())
			    		   fragmentTransaction.show(tempreture);
			    	   
			    	   tempreture.refresh();
			       }
			       else {
			    	   tempreture = new tempreture_milk_fragment();
			    	   tempreture.isTemperature(true);
			    	   
			    	   if (milk != null)
			    		   if( !milk.isHidden())
			    			   fragmentTransaction.hide(milk);
			    	   
			    	   if( !home.isHidden())
			    		   fragmentTransaction.hide(home);
			    	   
			    	   fragmentTransaction.add(R.id.translate,tempreture, "tempreture");
			       }
			       fragmentTransaction.commit();
	
					
				//	fragmentTransaction.hide(fragmentManager.findFragmentByTag("home"));
				//	fragmentTransaction.add(R.id.translate , fragment, "temperature");
				//	fragmentTransaction.addToBackStack(null);
				//	fragmentTransaction.commit();  
					//fragmentTransaction.replace(R.id.translate , fragment, null);
					
					
				}
			});
		 
		 
		 
		 
		 ImageButton button4 = ( ImageButton)view.findViewById(R.id.imageButton4);
		 
		 button4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					fragmentManager = getFragmentManager();  
					fragmentTransaction = fragmentManager.beginTransaction(); 
					
					//Toast.makeText(getApplicationContext(),String.valueOf( fragmentManager.getBackStackEntryCount()),
				//			Toast.LENGTH_SHORT).show();
			       if (milk !=null){
			    	   if(tempreture != null)
			    		   if(  !tempreture.isHidden() )
			    			   fragmentTransaction.hide(tempreture);
			    	   
			    	   if(  !home.isHidden() )
			    		    fragmentTransaction.hide(home);
			    	   if ( milk.isHidden() )
			    		   fragmentTransaction.show(milk);
			    	   
			    	   milk.refresh();
			    	   
			       }
			       else {
			    	   if(tempreture != null)
			    		   if(  !tempreture.isHidden() )
			    			   fragmentTransaction.hide(tempreture);
			    	   
			    	   if(  !home.isHidden() )
			    		    fragmentTransaction.hide(home);
			    	   
			    	   milk = new tempreture_milk_fragment();
			    	   milk.isTemperature(false);
			    	   fragmentTransaction.add(R.id.translate,milk, "milk");
			       }
			       fragmentTransaction.commit();
	
				//	fragmentTransaction.hide(fragmentManager.findFragmentByTag("home"));
				//	fragmentTransaction.add(R.id.translate , fragment, "temperature");
				//	fragmentTransaction.addToBackStack(null);
				//	fragmentTransaction.commit();  
					//fragmentTransaction.replace(R.id.translate , fragment, null);
					
					
				}
			});
       
		 
		 
		 ImageButton button2= ( ImageButton)view.findViewById(R.id.imageButton2);
		 
		 button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					fragmentManager = getFragmentManager();  
					fragmentTransaction = fragmentManager.beginTransaction(); 
					fragmentManager.popBackStack();
					//fragmentTransaction.commit();  
					//Toast.makeText(getApplicationContext(), "fsdafs", Toast.LENGTH_SHORT).show();
				}
			});
		
        
		 
		 ImageButton button5 = (ImageButton)view.findViewById(R.id.imageButton5);
		 button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fragmentManager = getFragmentManager();  
				fragmentTransaction = fragmentManager.beginTransaction(); 
				
				
				
				Toast.makeText(v.getContext(),"dfsfa",Toast.LENGTH_SHORT).show();
				if(!home.isHidden())
					fragmentTransaction.hide(home);
				if(milk !=null)
					if(!milk.isHidden())
						fragmentTransaction.hide(milk);
				
				if(tempreture !=null)
					if(!tempreture.isHidden())
						fragmentTransaction.hide(tempreture);
				
				if(bluetooth == null){
					bluetooth = new bluetooth_fragment(  ((MainActivity)buttons_fragment.this.getActivity() ).getBluetooth());
					fragmentTransaction.add(R.id.translate, bluetooth,"bluetooth");
				}
				else {
					fragmentTransaction.show(bluetooth);
				}
		    	   
		    	
		       fragmentTransaction.commit();
		       
		       ( (MainActivity)buttons_fragment.this.getActivity() ).inVisibleButtons();
		      
			}		
		});
	
	}
		
	}
	
	

