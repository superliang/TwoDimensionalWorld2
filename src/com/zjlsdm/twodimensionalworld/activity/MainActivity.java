package com.zjlsdm.twodimensionalworld.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zjlsdm.twodimensionalworld.R;
import com.zjlsdm.twodimensionalworld.task.GetDataTask;

public class MainActivity extends Activity {
	private static final String DATAURL = "http://www.acgdb.com/anime201407/bangumi";
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GetDataTask task = new GetDataTask(DATAURL);// 获取网络数据，保存sqlite
		task.execute("");

	}

	/**
	 * ActionBar 上面的菜单
	 * 菜单内容是选择日期
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item.getItemId();// 触发时间控件
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}
}
