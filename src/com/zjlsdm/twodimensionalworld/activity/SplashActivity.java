package com.zjlsdm.twodimensionalworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zjlsdm.twodimensionalworld.R;
import com.zjlsdm.twodimensionalworld.task.InitSystemTask;
import com.zjlsdm.twodimensionalworld.util.NetworkUtil;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟三秒
	
	private ProgressBar progressBar;
	private TextView tvProgress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉ActionBar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar_splash);
		tvProgress = (TextView) findViewById(R.id.progressBar_text_splash);
		// 页面跳入和跳出的动画方式
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
		init();
	}

	private void init() {
		final int networkState = NetworkUtil.getAPNType(this);

		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

		if (isFirstRun) {
			//第一次使用软件
			Editor editor = sharedPreferences.edit();
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			
			progressBar.setVisibility(View.VISIBLE);
			tvProgress.setVisibility(View.VISIBLE);
			InitSystemTask systemTask = new InitSystemTask(this,progressBar,tvProgress,NetworkUtil.getAPNType(this));
			systemTask.execute("");
			
//			GetDataTask task = new GetDataTask(GetUrlEntity.BangumiUrl[2]);// 获取网络数据，保存sqlite
//			task.execute("");
		} else {
			// 页面跳转到MainActivity
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = new Intent(SplashActivity.this,
							MainActivity.class);
					mainIntent.putExtra("network", networkState + "");
					SplashActivity.this.startActivity(mainIntent);
					SplashActivity.this.finish();
				}
			}, SPLASH_DISPLAY_LENGHT);
		}
		
		
		

	}
}