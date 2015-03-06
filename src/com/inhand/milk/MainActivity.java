package com.inhand.milk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.aaaa.R;
import com.inhand.milk.bluetooth.Bluetooth;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MainActivity extends Activity {
	 
	android.app.FragmentManager fragmentManager;
	android.app.FragmentTransaction fragmentTransaction ;
	ButtonsFragment buttons  ;
	 Bluetooth bluetooth ;
	  SlidingMenu menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    fragmentManager = getFragmentManager();  
		fragmentTransaction = fragmentManager.beginTransaction();  
		buttons = new ButtonsFragment();
		fragmentTransaction.add(R.id.buttons, buttons,"buttons"); 
        fragmentTransaction.commit();  
        
        
       // bluetooth = new Bluetooth(this);
       // bluetooth.openBlue();
        //bluetooth.startSearch();   
        setSlidingMenu();
      
	 }
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		/*
		  Log.i("MainActivity", "invisible");
	        inVisibleButtons();
	        Log.i("MainActivity", "visible");
	        visibleButtons();
	        Log.i("MainActivity", "invisible");
	        inVisibleButtons();   
	        */
	}


	private void  setSlidingMenu(){
		  menu = new SlidingMenu(this);
	        
	        menu.setMode(SlidingMenu.LEFT);
	       menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	     //  menu.setShadowWidthRes(R.dimen.menuBehindOffset);
	       // menu.setShadowDrawable(R.drawable.ic_launcher);
	        menu.setBackgroundResource(R.drawable.side_menu_bg);
	        menu.setBehindOffsetRes(R.dimen.menuBehindOffset);
	        menu.setFadeDegree(0.8f);
	       menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	       
	       
	       	View  view = getLayoutInflater().inflate(R.layout.menu, null);
	        menu.setMenu(view );

	     ListView listView = (ListView)view.findViewById(R.id.listView);
	     
	     List< Map<String,Object> > listitems = new ArrayList<Map<String,Object>>();
	     Map<String, Object> map1 = new HashMap<String, Object>();
	     Map<String, Object> map2 = new HashMap<String, Object>();
	     Map<String, Object> map3 = new HashMap<String, Object>();
	     map1.put("image", R.drawable.side_family_ico);
	     map1.put("text","��ͥ��Ա");
	     map2.put("image", R.drawable.side_advise_ico);
	     map2.put("text","�������");
	     map3.put("image", R.drawable.side_menu);
	     map3.put("text","��ƿ����");
	     listitems.add(map1);
	     listitems.add(map3);
	     listitems.add(map2);
	    
	     
	     
	     SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitems, R.layout.menu_item,
	    		 	new String[] {"image","text"}, 
	    		 	new int[] { R.id.menu_image,  R.id.menu_text}  );
	     
	     listView.setAdapter(simpleAdapter);
		
	}

 private OnClickListener  onClickListener = new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		  menu.toggle();
	}
};
	

	public android.view.View.OnClickListener getMyOnclickListener(){
		return onClickListener;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public  void visibleButtons(){
		/*
		 fragmentManager = getFragmentManager();  
		 fragmentTransaction = fragmentManager.beginTransaction();  
		 if(buttons == null )
			 return ;
		 else if (!buttons.isVisible()){
			 fragmentTransaction.show(buttons);
		 	 fragmentTransaction.commit();
		 	 }
		 	 */
		
		 findViewById(R.id.buttons).setVisibility(View.VISIBLE);
			 
	}
	
	public void inVisibleButtons(){
		/*
		fragmentTransaction = getFragmentManager().beginTransaction()  ; 
		 if(buttons == null )
			 return ;
		 else if (buttons.isVisible()){
			 Toast.makeText(this.getApplicationContext(),"invisible" ,
					 	Toast.LENGTH_SHORT).show();
			 fragmentTransaction.hide(buttons);
			 fragmentTransaction.commit();
		 }
		 */
		
		 findViewById(R.id.buttons).setVisibility(View.GONE);

	}
	public Bluetooth getBluetooth(){
		return bluetooth;
	}

	
}
