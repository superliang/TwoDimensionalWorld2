package com.zjlsdm.twodimensionalworld.task;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zjlsdm.twodimensionalworld.activity.MainActivity;
import com.zjlsdm.twodimensionalworld.core.GetUrlEntity;
import com.zjlsdm.twodimensionalworld.dao.DatabaseHelper;
import com.zjlsdm.twodimensionalworld.util.RegexUtil;
import com.zjlsdm.twodimensionalworld.util.TimerUtil;

public class InitSystemTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private SQLiteDatabase db;
	private ProgressBar progressBar;
	private TextView tvProgress;
	private int networkState;

	public InitSystemTask(Context context, ProgressBar progressBar,
			TextView tvProgress, int networkState) {
		this.context = context;
		this.progressBar = progressBar;
		this.tvProgress = tvProgress;
		this.networkState = networkState;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String doInBackground(String... params) {
		DatabaseHelper database = new DatabaseHelper(context);
		db = database.getReadableDatabase();

		// 初始化TBL_URL_LIST这张表
		String[] urlText = { "一月番的URL", "四月番的URL", "七月番的URL", "十月番的URL" };
		for (int i = 0; i < GetUrlEntity.urlSize; i++) {
			ContentValues values = new ContentValues();
			values.put("URL", GetUrlEntity.BangumiUrl[i]);
			values.put("REMARK", urlText[i]);
			values.put("UPDATE_TIME", "0");
			db.insert("TBL_URL_LIST", null, values);
		}

		// 初始化当前的季度的新番数据
		String[] quarterTabName = { "TBL_BANGUMI_ONE", "TBL_BANGUMI_FOUR",
				"TBL_BANGUMI_SEVEN", "TBL_BANGUMI_TEN" };
		HashMap<String, Object> quarterData = getQuarterData(GetUrlEntity.BangumiUrl[TimerUtil
				.getQuarter()]);

		List<String> weekString = (List<String>) quarterData.get("WeekTime");
		List<List<HashMap<String, String>>> contentData = (List<List<HashMap<String, String>>>) quarterData
				.get("Time&Name");

		int index = 1;
		for (int i = 0; i < weekString.size(); i++) {
			for (HashMap<String, String> tempData : contentData.get(i)) {
				ContentValues values = new ContentValues();
				values.put("ORDER_INDEX", index + "");
				values.put("NAME", tempData.get("name"));
				values.put("TIME_DAY", tempData.get("time"));
				values.put("TIME_WEEK", weekString.get(i));
				db.insert(quarterTabName[TimerUtil.getQuarter()], null, values);
				index++;
			}
		}
		ContentValues values = new ContentValues();
		values.put("URL", GetUrlEntity.BangumiUrl[TimerUtil.getQuarter()]);
		db.update("TBL_URL_LIST", values, "UPDATE_TIME = ?",
				new String[]{System.currentTimeMillis() + ""});

		db.close();
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		progressBar.setVisibility(View.GONE);
		tvProgress.setVisibility(View.GONE);

		Intent mainIntent = new Intent(context, MainActivity.class);
		mainIntent.putExtra("network", networkState + "");
		context.startActivity(mainIntent);
		((Activity) context).finish();
	}

	/**
	 * 获取dataURL中网页的相关数据，获取该季度今天开始后的六天的数据以及昨天的数据
	 * 
	 * @param dataUrl
	 * @return
	 */
	private HashMap<String, Object> getQuarterData(String dataUrl) {
		final String WEEKTIME_REGEX = "^div.*acgdb-content=\"sp-animelist\".*";
		final String NAME_REGEX = "^em acgdb-timestamp.*";

		HashMap<String, Object> mainData = new HashMap<String, Object>();

		final List<String> strList = new ArrayList<String>();
		try {
			URL url = new URL(dataUrl);
			URLConnection urlConnection = url.openConnection();
			Parser parser = new Parser(urlConnection);
			parser.setEncoding("UTF-8");
			NodeList nodeList = parser.parse(new NodeFilter() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean accept(Node node) {
					HashMap<String, String> tempMap = new HashMap<String, String>();
					tempMap.put("getText", node.getText());
					tempMap.put("getPlianText", node.toPlainTextString());
					tempMap.put("toHtml", node.toHtml());
					tempMap.put("toString", node.toString());
					if (RegexUtil.checkString(node.getText(), WEEKTIME_REGEX)) {
						strList.add(node.toPlainTextString().substring(0, 4));
						return true;
					}
					return false;
				}
			});
			mainData.put("WeekTime", strList);

			List<List<HashMap<String, String>>> names = new ArrayList<List<HashMap<String, String>>>();
			for (SimpleNodeIterator i = nodeList.elements(); i.hasMoreNodes();) {
				Node node = i.nextNode();
				final List<HashMap<String, String>> namesOfWeek = new ArrayList<HashMap<String, String>>();
				node.collectInto(new NodeList(), new NodeFilter() {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean accept(Node node) {
						if (RegexUtil.checkString(node.getText(), NAME_REGEX)) {
							HashMap<String, String> itemData = new HashMap<String, String>();
							itemData.put("time", node.getNextSibling()
									.toPlainTextString());
							itemData.put("name", node.getParent().toHtml()
									.split("<u>")[1].split("</u>")[0]);
							namesOfWeek.add(itemData);
							return true;
						}
						return false;
					}
				});
				names.add(namesOfWeek);
			}

			mainData.put("Time&Name", names);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mainData;
	}
}
