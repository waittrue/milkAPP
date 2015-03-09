package com.inhand.milk.fragment.health;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;
import com.inhand.milk.fragment.health.nutrition.NutritionFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HealthFragment extends Fragment{

	private  View  view;
	private ListView listView;
	private List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view  = inflater.inflate(R.layout.health,null);
		setListView();
		setOnclickEvent();
		return view;
	}
	
	private void setListView() {
		listView = (ListView)view.findViewById(R.id.health_listview);
		String[] string = getResources().getStringArray(R.array.health_item_name);
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("image", R.drawable.health_icon_baby);
		map1.put("name", string[0]);
		map1.put("details", "生日：2014-08-08/性别：女");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("image", R.drawable.health_icon_nutrition);
		map2.put("name", string[1]);
		map2.put("details", "蛋白质：14g/钙：12g");
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("image", R.drawable.health_icon_statistics);
		map3.put("name", string[2]);
		map3.put("details", "饮奶温度，饮奶量");
		
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("image", R.drawable.health_icon_prompt);
		map4.put("name", string[3]);
		map4.put("details", "我们错过了就没有回头");
		
		data.add(map1);
		data.add(map2);
		data.add(map3);
		data.add(map4);
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), data, R.layout.health_item, 
					new String[]{"image","name","details"},
					new int[]{R.id.health_item_icon_container, R.id.health_item_name,R.id.health_item_details});
		listView.setAdapter(simpleAdapter);
	}
	private void setOnclickEvent(){
		if (listView == null)
			return;
		
		OnItemClickListener onItemClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				if (position < 0 || position > data.size())
					return;
				
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				switch (position) {
				case 0:
					break;
				case 1:
					fragmentTransaction.hide(HealthFragment.this);
					fragmentTransaction.add(R.id.Activity_fragments_container,new NutritionFragment(), null);
					fragmentTransaction.addToBackStack(null);
				default:
					break;
				}
				fragmentTransaction.commit();
				( (MainActivity)getActivity() ).inVisibleButtons();
			}
			
		};
		listView.setOnItemClickListener(onItemClickListener);
		
		ImageView menu = (ImageView)view.findViewById(R.id.home_menu_entry);
		menu.setOnClickListener(( (MainActivity)getActivity() ).getMyOnclickListener() );
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Health", "destroy");
	}
}

