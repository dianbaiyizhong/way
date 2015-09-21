package org.hhm.way.singleton;

import android.app.Activity;

public class UserInfo extends Activity {

	private String phoneNumber;
	private String outComingOrder;
	private String locationOrder;
	private String vibrate;
	private String ring;
	private String vibrateTime;
	private String ringTime;
	public static UserInfo userInfo = new UserInfo();

	public UserInfo() {

	}

	public static UserInfo getInstacne() {

		return userInfo;

	}

	public String getVibrateTime() {
		return vibrateTime;
	}

	public void setVibrateTime(String vibrateTime) {
		this.vibrateTime = vibrateTime;
	}

	public String getRingTime() {
		return ringTime;
	}

	public void setRingTime(String ringTime) {
		this.ringTime = ringTime;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOutComingOrder() {
		return outComingOrder;
	}

	public void setOutComingOrder(String outComingOrder) {
		this.outComingOrder = outComingOrder;
	}

	public String getLocationOrder() {
		return locationOrder;
	}

	public void setLocationOrder(String locationOrder) {
		this.locationOrder = locationOrder;
	}

	public String getVibrate() {
		return vibrate;
	}

	public void setVibrate(String vibrate) {
		this.vibrate = vibrate;
	}

	public String getRing() {
		return ring;
	}

	public void setRing(String ring) {
		this.ring = ring;
	}

	public static UserInfo getUserInfo() {
		return userInfo;
	}

	public static void setUserInfo(UserInfo userInfo) {
		UserInfo.userInfo = userInfo;
	}

}
