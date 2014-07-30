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

import android.os.AsyncTask;
import android.util.Log;

import com.zjlsdm.twodimensionalworld.util.RegexUtil;

public class GetDataTask extends AsyncTask<String, String, String> {
	private static final String WEEKTIME_REGEX = "^div.*acgdb-content=\"sp-animelist\".*";
	private static final String NAME_REGEX = "^em acgdb-timestamp.*";

	private String dataUrl;
	private HashMap<String, List<String>> mainData = new HashMap<String, List<String>>();

	public GetDataTask(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	@Override
	protected String doInBackground(String... params) {
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
					private HashMap<String, String> itemData = new HashMap<String, String>();
					@Override
					public boolean accept(Node node) {
						if(RegexUtil.checkString(node.getText(), NAME_REGEX)){
							itemData.put("time", node.getNextSibling().toPlainTextString());
							itemData.put("name", node.getParent().toHtml().split("<u>")[1].split("</u>")[0]);
							namesOfWeek.add(itemData);
							return true;
						}
						return false;
					}
				});
				names.add(namesOfWeek);
			}
			
			for(List<HashMap<String, String>> tempList:names){
				for(HashMap<String, String> tempString:tempList){
					Log.e("time", tempString.get("time"));
					Log.e("name", tempString.get("name"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String temp : strList) {
			Log.e("++++++++", temp);
		}

		return null;
	}
}
