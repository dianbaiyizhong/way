package org.hhm.way.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OutComingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			String phoneNumber = intent
					.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

			System.out.println("对方正在去电:" + phoneNumber);
		}

	}

}
