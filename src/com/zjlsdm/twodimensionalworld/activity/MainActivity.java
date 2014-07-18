package com.zjlsdm.twodimensionalworld.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.zjlsdm.twodimensionalworld.R;
import com.zjlsdm.twodimensionalworld.task.GetDataTask;

public class MainActivity extends Activity {
	private static final String DATAURL = "http://www.acgdb.com/anime201407/bangumi"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GetDataTask task = new GetDataTask(DATAURL);
		task.execute("");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
