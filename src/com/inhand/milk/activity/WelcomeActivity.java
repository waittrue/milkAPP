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
			save();
		}
		else {
			intent.setClass(WelcomeActivity.this, MainActivity.class);
		}
		startActivity(intent);
	}
	/**
	 * 
	 * @return ��һ�ε�½�����棬�����
	 */
	private boolean isfirst(){
		return true;
	}
	/**
	 * ����ֵ����ʾ�Ѿ��ǵ�½����
	 */
	private void save(){
		
	}
}
