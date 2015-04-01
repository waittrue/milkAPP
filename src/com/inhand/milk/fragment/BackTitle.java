package com.inhand.milk.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.aaaa.R;
import com.inhand.milk.activity.MainActivity;

public class BackTitle extends BaseTitle{

	private String mTitle;
	private Activity mActivity;
	public BackTitle( String title) {
		mTitle = title;
		
	}
	public View getView(Activity activity) {
		// TODO Auto-generated method stu
		mActivity = activity;
		OnClickListener listener =  new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager manager = mActivity.getFragmentManager();
				if (manager.getBackStackEntryCount() == 0)
					mActivity.finish();
				else {
					manager.popBackStack();
					manager.beginTransaction().commit();
				}
			}
		};
		return setView(activity, R.layout.title_minor,
				R.drawable.back_icon,mTitle,
				 listener);
	}
	
}
