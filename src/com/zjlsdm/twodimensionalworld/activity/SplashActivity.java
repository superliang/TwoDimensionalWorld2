package com.zjlsdm.twodimensionalworld.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.zjlsdm.twodimensionalworld.R;
import com.zjlsdm.twodimensionalworld.util.ToastUtil;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟三秒

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//去掉ActionBar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//页面跳入和跳出的动画方式
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
		
	}
	@Override
	protected void onResume(){
		super.onResume();
		init();
	}
	private void init(){
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = new Intent(SplashActivity.this,
							MainActivity.class);
					SplashActivity.this.startActivity(mainIntent);
					SplashActivity.this.finish();
				}
			}, SPLASH_DISPLAY_LENGHT);
		} else {
			ToastUtil.showToast(this, "无法成功连接网络或网络异常", Toast.LENGTH_LONG);
		}
		
	}
}