package org.hhm.way.util;

import org.hhm.way.singleton.UserInfo;

import android.telephony.SmsManager;

public class Message {

	public void sendMessage(String content) {
		UserInfo userInfo = UserInfo.getInstacne();

		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(userInfo.getPhoneNumber(), null, content,
				null, null);

	}
}
