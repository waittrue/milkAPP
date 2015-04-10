package com.inhand.milk.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.example.aaaa.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends Activity{

	private final float timeJump = 2f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				jumpActivity();
			}
		};
		timer.schedule(task, (long) (1000 * timeJump));
	}
	private void jumpActivity(){
		Intent intent = new Intent();
		if ( isfirst() ){
			intent.setClass(WelcomeActivity.this,FirstLanunchActivity.class );
		}
		else {
			intent.setClass(WelcomeActivity.this, MainActivity.class);
		}
		startActivity(intent);
	}
	private boolean isfirst(){
		return true;
	}
}
