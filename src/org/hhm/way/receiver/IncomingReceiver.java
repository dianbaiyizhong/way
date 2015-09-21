package org.hhm.way.receiver;

import org.hhm.way.util.Message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class IncomingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		// 呼入电话
		if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
			doReceivePhone(context, intent);
		}
	}

	public void doReceivePhone(Context context, Intent intent) {
		String phoneNumber = intent
				.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int state = telephony.getCallState();

		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING: {

			Message message = new Message();
			message.sendMessage("正在来电,电话号码为:" + phoneNumber);

		}
			break;
		case TelephonyManager.CALL_STATE_IDLE: {

		}
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK: {

		}
			break;
		}
	}

}
