package com.zjlsdm.twodimensionalworld.task;

import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;

import android.os.AsyncTask;
import android.util.Log;

import com.zjlsdm.twodimensionalworld.util.NetTool;

public class GetDataTask extends AsyncTask<String, String, String>{
	private String dataUrl;
	
	public GetDataTask(String dataUrl){
		this.dataUrl = dataUrl;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			String getHtml = NetTool.sendTxt(dataUrl);
			Parser parser = new Parser(((HttpURLConnection)(new URL(dataUrl)).openConnection()));
			for(NodeIterator i = parser.elements();i.hasMoreNodes();){
				Node node = i.nextNode();
				Log.e("getText", node.getText());
				Log.e("getPlianText", node.toPlainTextString());
				Log.e("toHtml", node.toHtml());
				Log.e("toString", node.toString());
			}
			Log.e("++++", getHtml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
