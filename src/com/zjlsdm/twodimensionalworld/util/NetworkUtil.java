package com.zjlsdm.twodimensionalworld.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	public static final int NONETWORK = -1;
	public static final int WIFI = 1;
	public static final int CMWAP = 2;
	public static final int CMNET = 3;

	/**
	 * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
	 * 
	 * @param context
	 * @return
	 */

	public static int getAPNType(Context context) {

		int netType = NONETWORK;

		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo == null) {

			return netType;

		}

		int nType = networkInfo.getType();

		if (nType == ConnectivityManager.TYPE_MOBILE) {

			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {

				netType = CMNET;

			}

			else {

				netType = CMWAP;

			}

		}

		else if (nType == ConnectivityManager.TYPE_WIFI) {

			netType = WIFI;

		}

		return netType;

	}

}
