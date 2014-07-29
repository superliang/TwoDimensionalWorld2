package com.zjlsdm.twodimensionalworld.task;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

import android.os.AsyncTask;
import android.util.Log;

public class GetDataTask extends AsyncTask<String, String, String> {
	private String dataUrl;

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
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean accept(Node node) {
					Node need = node;
					if(getStringsByRegex(node.getText())){
						strList.add(need.toPlainTextString());
						return true;
					}
					return false;
				}
			});
			// for(NodeIterator i = parser.elements();i.hasMoreNodes();){
			// Node node = i.nextNode();
			// Log.e("getText", node.getText());
			// Log.e("getPlianText", node.toPlainTextString());
			// Log.e("toHtml", node.toHtml());
			// Log.e("toString", node.toString());
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(String temp:strList){
			Log.e("++++++++",temp);
		}
		
		return null;
	}

	private static boolean getStringsByRegex(String txt) {
		String regex = "span class=\"none\" acgdb-data-type=\"summary\"";

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(txt);

		if (m.find()) {

			return true;

		}

		return false;

	}

}
