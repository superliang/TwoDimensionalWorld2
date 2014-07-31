package com.zjlsdm.twodimensionalworld.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Context context = null;
	private static Toast toast = null;

	public static void showToast(Context context, CharSequence hint ,int duration) {
		if (ToastUtil.context == context) {
			toast.setView(toast.getView());
			toast.setText(hint);
			toast.setDuration(duration);
		} else {
			ToastUtil.context = context;
			toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
		}
		toast.show();
	}
	
	public static void showToast(Context context, int hint ,int duration) {
		if (ToastUtil.context == context) {
			toast.setView(toast.getView());
			toast.setText(hint);
			toast.setDuration(duration);
		} else {
			ToastUtil.context = context;
			toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}
