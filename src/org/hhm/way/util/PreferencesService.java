package org.hhm.way.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {
	private Context context;

	public PreferencesService(Context context) {
		this.context = context;
	}

	public void saveIsReturnHome(String Order, String Order1) {
		// 获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("IsReturnHome", Order);
		editor.putString("IsShowInNext", Order1);

		editor.commit();
	}

	public Map<String, String> getIsReturnHomePerferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		params.put("IsReturnHome", preferences.getString("IsReturnHome", ""));
		params.put("IsShowInNext", preferences.getString("IsShowInNext", ""));

		return params;
	}

	public void savePhoneNumber(String Order) {
		// 获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("PhoneNumber", Order);

		editor.commit();
	}

	public Map<String, String> getPhoneNumberPerferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		params.put("PhoneNumber", preferences.getString("PhoneNumber", ""));

		return params;
	}

	public void saveOutcomingOrder(String Order) {
		// 获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("OutcomingOrder", Order);

		editor.commit();
	}

	public Map<String, String> getOutcomingOrderPerferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		params.put("OutcomingOrder",
				preferences.getString("OutcomingOrder", ""));

		return params;
	}

	public void saveLocationOrder(String Order) {
		// 获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("LocationOrder", Order);

		editor.commit();
	}

	public Map<String, String> getLocationOrderPerferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		params.put("LocationOrder", preferences.getString("LocationOrder", ""));

		return params;
	}

	public void saveVibrateOrder(String Order, String time) {
		// 获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("VibrateOrder", Order);
		editor.putString("VibrateTime", time);

		editor.commit();
	}

	public Map<String, String> getVibrateOrderPerferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		params.put("VibrateOrder", preferences.getString("VibrateOrder", ""));
		params.put("VibrateTime", preferences.getString("VibrateTime", ""));
		return params;
	}

	public void saveRingOrder(String Order, String time) {
		// 获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("RingOrder", Order);
		editor.putString("RingTime", time);

		editor.commit();
	}

	public Map<String, String> getRingOrderPerferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		params.put("RingOrder", preferences.getString("RingOrder", ""));
		params.put("RingTime", preferences.getString("RingTime", ""));
		return params;
	}

	public void saveFunctionCheckBoxPerferences(String sms, String incoming,
			String location, String dialog, String vibrate, String ring) {
		// 获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("sms", sms);
		editor.putString("incoming", incoming);
		editor.putString("location", location);
		editor.putString("dialog", dialog);
		editor.putString("vibrate", vibrate);
		editor.putString("ring", ring);

		editor.commit();
	}

	public Map<String, String> getFunctionCheckBoxPerferences() {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences("way",
				Context.MODE_PRIVATE);
		params.put("sms", preferences.getString("sms", ""));
		params.put("incoming", preferences.getString("incoming", ""));
		params.put("location", preferences.getString("location", ""));
		params.put("dialog", preferences.getString("dialog", ""));
		params.put("vibrate", preferences.getString("vibrate", ""));
		params.put("ring", preferences.getString("ring", ""));

		return params;
	}

}
