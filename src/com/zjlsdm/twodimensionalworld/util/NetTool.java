package com.zjlsdm.twodimensionalworld.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.protocol.HTTP;

public class NetTool {
	private static final int TIMEOUT = 10000;

	public static String sendTxt(String urlpath)
			throws Exception {
		try{
			 URL url = new URL(urlpath); 
			 HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			 conn.setDoOutput(true);
			 conn.setRequestProperty("Content-Type", "text/html");
			 conn.setRequestProperty("Charset", HTTP.UTF_8);
			 conn.setReadTimeout(TIMEOUT);  
			 conn.setRequestMethod("GET");
			 if (conn.getResponseCode() == 200) {
				 InputStream inStream = conn.getInputStream();  
				 byte[] data = readInputStream(inStream);
				 String resultStr = new String(data);
				 return resultStr;
			 }
			 return "error to getWEB!";		
		}catch(MalformedURLException e){
			e.printStackTrace();
			return "error MalformedURLException!";
		}catch(Exception e){
			e.printStackTrace();
			return "error Exception!";
		}

	}
    public static byte[] readInputStream(InputStream inStream) throws Exception{ 
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        byte[] buffer = new byte[1024*8]; 
        int len = 0; 
        while( (len=inStream.read(buffer)) != -1 ){ 
            outStream.write(buffer, 0, len); 
        } 
        inStream.close(); 
        return outStream.toByteArray();
    }
}
