package org.hhm.way;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hhm.way.adapter.PhoneAdapter;
import org.hhm.way.db.impl.LogImpl;
import org.hhm.way.listener.ShakeListener;
import org.hhm.way.listener.ShakeListener.OnShakeListener;
import org.hhm.way.receiver.IncomingReceiver;
import org.hhm.way.receiver.SMSReceiver;
import org.hhm.way.singleton.UserInfo;
import org.hhm.way.util.Call;
import org.hhm.way.util.PreferencesService;
import org.hhm.way.util.pojo.Contact;
import org.hhm.way.view.Switch;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	ShakeListener mShakeListener = null;
	Call call = new Call();
	List<Contact> ContactList;
	Button btn_StartMonitor, btn_StopMonitor;
	BroadcastReceiver sms_receiver = new SMSReceiver();
	BroadcastReceiver incoming_receiver = new IncomingReceiver();
	LogImpl logImpl = new LogImpl();

	UserInfo userInfo = UserInfo.getInstacne();

	AutoCompleteTextView phoneNumber;

	// 这个参数用来记载是否已经开始监听
	int isUnregisterBoardCast = 0;

	private PreferencesService preferencesService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 注释以下这个代码可以去掉actionbar
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 以下这个代码可以让actionbar不干扰activity布局
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		// 设置主界面视图
		setContentView(R.layout.activity_main);
		// 设置是否能够使用ActionBar来滑动
		setSlidingActionBarEnabled(true);

		// 设置actionbar背景
		setActionBarLayout(R.layout.actionbar);

		// 初始化滑动菜单
		initSlidingMenu(savedInstanceState);

		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new shakeLitener());

		Switch s = (Switch) findViewById(R.id.switch_function);

		preferencesService = new PreferencesService(this);
		// 保存用户参数
		phoneNumber = (AutoCompleteTextView) findViewById(R.id.ET_PhoneNumber);

		// 获取通讯录
		buildContactData();

		FindContactData();
		// ArrayList<HashMap<String, Object>> contactlistItem = new
		// ArrayList<HashMap<String, Object>>();

		// for (int i = 0; i < cantactlist.size(); i++) {
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("name", cantactlist.get(i).getmContactsName());
		// map.put("number", cantactlist.get(i).getmContactsNumber());
		// map.put("photo", cantactlist.get(i).getmContactsPhonto());
		//
		// contactlistItem.add(map);
		// }

		// SimpleAdapter adapt = new SimpleAdapter(this, contactlistItem,
		// R.layout.phonenumber_auto, new String[] { "number", "name",
		// "photo" }, new int[] { R.id.contactnumber,
		// R.id.contactname, R.id.contactphoto });

		LoadUserInfo();

		final ListView list = (ListView) findViewById(R.id.LV_FunctionMenu);

		btn_StartMonitor = (Button) findViewById(R.id.StartMonitor);

		btn_StopMonitor = (Button) findViewById(R.id.StopMonitor);

		btn_StartMonitor.setOnClickListener(new OnClickListener() {

			// 开始监听按钮
			@Override
			public void onClick(View arg0) {

				// 传入log
				logImpl.Insert("被控端服务开启", "监控号码:" + phoneNumber.getText(),
						MainActivity.this);

				preferencesService.savePhoneNumber(phoneNumber.getText()
						.toString());

				userInfo.setPhoneNumber(phoneNumber.getText().toString());
				preferencesService.savePhoneNumber(phoneNumber.getText()
						.toString());

				// 锁住editext防止用户开启监控之后修改
				phoneNumber.setEnabled(false);
				// 开始服务之后，要把开启按钮锁住
				btn_StartMonitor.setEnabled(false);

				String[] functionCheckBox = new String[6];

				for (int i = 0; i < list.getChildCount(); i++) {

					RelativeLayout layout = (RelativeLayout) list.getChildAt(i);// 获得子item的layout
					Switch s = (Switch) layout
							.findViewById(R.id.switch_function);
					// 点击之后不能再使用那个控件
					s.setEnabled(false);

					if (i == 0) {
						if (s.isChecked() == true) {
							functionCheckBox[i] = "1";

						} else {
							functionCheckBox[i] = "0";
						}

						s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton arg0,
									boolean arg1) {
								System.out.println(arg1);

							}
						});

					}

					if (i == 1) {
						if (s.isChecked() == true) {
							functionCheckBox[i] = "1";
							// 如果此项被选中，证明用户启用了监听来电的功能,此时，要开一个来电监听服务
							IntentFilter intentFilter = new IntentFilter();
							intentFilter
									.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
							intentFilter.setPriority(Integer.MAX_VALUE);
							registerReceiver(incoming_receiver, intentFilter);

						} else {
							functionCheckBox[i] = "0";
						}
						s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton arg0,
									boolean arg1) {
								System.out.println(arg1);

							}
						});
					}
					if (i == 2) {
						if (s.isChecked() == true) {
							functionCheckBox[i] = "1";

						} else {
							functionCheckBox[i] = "0";
						}

						s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton arg0,
									boolean arg1) {
								System.out.println(arg1);

							}
						});
					}
					if (i == 3) {
						if (s.isChecked() == true) {
							functionCheckBox[i] = "1";

						} else {
							functionCheckBox[i] = "0";
						}

						s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton arg0,
									boolean arg1) {
								System.out.println(arg1);

							}
						});
					}
					if (i == 4) {
						if (s.isChecked() == true) {
							functionCheckBox[i] = "1";

						} else {
							functionCheckBox[i] = "0";
						}
						s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton arg0,
									boolean arg1) {
								System.out.println(arg1);

							}
						});
					}
					if (i == 5) {
						if (s.isChecked() == true) {
							functionCheckBox[i] = "1";

						} else {
							functionCheckBox[i] = "0";
						}
						s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(CompoundButton arg0,
									boolean arg1) {
								System.out.println(arg1);

							}
						});
					}

				}

				// 储存参数
				preferencesService.saveFunctionCheckBoxPerferences(
						functionCheckBox[0], functionCheckBox[1],
						functionCheckBox[2], functionCheckBox[3],
						functionCheckBox[4], functionCheckBox[5]);

				// 开启短信服务
				IntentFilter filter = new IntentFilter();
				filter.addAction("android.provider.Telephony.SMS_RECEIVED");
				registerReceiver(sms_receiver, filter);

				isUnregisterBoardCast = 1;

				// 再判断用户是否选择要返回桌面

				final Map<String, String> params_f = preferencesService
						.getIsReturnHomePerferences();
				final CheckBox ck = new CheckBox(MainActivity.this);

				if (params_f.get("IsShowInNext").equals("1")) {
					// 这里先取得用户是否已经选择不再提示返回桌面的提示框

					AlertDialog.Builder alert = new AlertDialog.Builder(
							MainActivity.this);
					alert.setTitle("提示");
					alert.setMessage("已启动服务,是否跳转到桌面");
					alert.setView(ck);
					ck.setText("不再提示");

					alert.setPositiveButton("跳转",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 保存跳转桌面设置
									preferencesService.saveIsReturnHome("1",
											params_f.get("IsShowInNext"));
									// 判断是否保存仍然提示弹框
									if (ck.isChecked()) {
										preferencesService.saveIsReturnHome(
												params_f.get("IsReturnHome"),
												"0");

									}

									// 打开下拉菜单信息
									shownotity();
									// 回到桌面
									Intent intent = new Intent();
									intent.setAction(Intent.ACTION_MAIN);
									intent.addCategory(Intent.CATEGORY_HOME);
									startActivity(intent);

								}

							});

					alert.setNegativeButton("不跳转",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// 保存跳转桌面设置
									preferencesService.saveIsReturnHome("0",
											params_f.get("IsShowInNext"));
									// 判断是否保存仍然提示弹框
									if (ck.isChecked()) {
										preferencesService.saveIsReturnHome(
												params_f.get("IsReturnHome"),
												"0");

									}

								}

							});

					alert.show();

				} else {
					// 如果用户没有选择弹窗不再显示
					if (params_f.get("IsReturnHome").equals("1")) {
						// 打开下拉菜单信息
						shownotity();
						// 回到桌面
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						startActivity(intent);

					}

				}
			}

		});

		// 点击停止监听的时候，把服务都停掉
		btn_StopMonitor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				try {
					unregisterReceiver(sms_receiver);
					unregisterReceiver(incoming_receiver);
					isUnregisterBoardCast = 0;

				} catch (Exception e) {
					isUnregisterBoardCast = 0;

				}

				// 在停止服务的时候，要把switch的enable设为可选
				if (isUnregisterBoardCast == 0) {
					for (int i = 0; i < list.getChildCount(); i++) {
						RelativeLayout layout = (RelativeLayout) list
								.getChildAt(i);// 获得子item的layout
						Switch s = (Switch) layout
								.findViewById(R.id.switch_function);
						s.setEnabled(true);

					}

					phoneNumber.setEnabled(true);

					btn_StartMonitor.setEnabled(true);
				}

			}
		});

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		// 获取上次用户默认值checkbox
		Map<String, String> params6 = preferencesService
				.getFunctionCheckBoxPerferences();
		String sms = "0";
		String incoming = "0";
		String location = "0";
		String dialog = "0";
		String vibrate = "0";
		String ring = "0";
		try {
			sms = params6.get("sms");
			incoming = params6.get("incoming");
			location = params6.get("location");
			dialog = params6.get("dialog");
			vibrate = params6.get("vibrate");
			ring = params6.get("ring");

		} catch (Exception e) {
			e.printStackTrace();

		}

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.iconlist_1);
		map1.put("ItemTitle", "短信监控");
		map1.put("ItemText", "将收到短信内容转发到主控手机");
		if (sms.equals("1")) {

			map1.put("ItemSwitch", true);

		}
		listItem.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.iconlist_2);
		map2.put("ItemTitle", "来电监控");
		map2.put("ItemText", "将呼入电话号码发送到主控手机");
		if (incoming.equals("1")) {
			map2.put("ItemSwitch", true);

		}
		listItem.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ItemImage", R.drawable.iconlist_3);
		map3.put("ItemTitle", "位置监控");
		map3.put("ItemText", "将被控手机当前位置发送到主控手机");
		if (location.equals("1")) {
			map3.put("ItemSwitch", true);

		}
		listItem.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("ItemImage", R.drawable.iconlist_4);
		map4.put("ItemTitle", "回电监控");
		map4.put("ItemText", "控制被控手机拨打电话到主控手机");
		if (dialog.equals("1")) {
			map4.put("ItemSwitch", true);

		}
		listItem.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("ItemImage", R.drawable.iconlist_5);
		map5.put("ItemTitle", "控制震动");
		map5.put("ItemText", "控制被控手机震动");
		if (vibrate.equals("1")) {
			map5.put("ItemSwitch", true);

		}
		listItem.add(map5);

		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("ItemImage", R.drawable.iconlist_6);
		map6.put("ItemTitle", "控制铃声");
		map6.put("ItemText", "控制被控手机铃声");
		if (ring.equals("1")) {
			map6.put("ItemSwitch", true);

		}
		listItem.add(map6);

		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,

		R.layout.listview_functionmenu, new String[] { "ItemImage",
				"ItemTitle", "ItemText", "ItemSwitch" }, new int[] {
				R.id.ItemImage, R.id.ItemTitle, R.id.ItemText,
				R.id.switch_function });

		list.setAdapter(listItemAdapter);

		// 添加点击
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) {

				System.out.println("点击第" + index + "个项目");
			}
		});

		// 添加长按点击
		list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("长按菜单-ContextMenu");
				menu.add(0, 0, 0, "弹出长按菜单0");
				menu.add(0, 1, 0, "弹出长按菜单1");
			}
		});

		// 对整个listview进行监听，在点击开始服务之后，不能再触碰listview的一切

		list.setOnTouchListener(new android.view.View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {

				switch (event.getAction()) {

				case MotionEvent.ACTION_UP: {
					// 松开事件发生后执行代码的区域

					if (isUnregisterBoardCast == 1) {
						// 弹出提示框不能触碰设置
						AlertDialog.Builder alert = new AlertDialog.Builder(
								MainActivity.this);
						alert.setTitle("提示");
						alert.setMessage("启动服务后，不能修改服务数据");

						alert.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}

								});

						alert.show();

					}
					break;
				}

				}

				return false;
			}
		});

		phoneNumber.setOnTouchListener(new android.view.View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP: {
					if (isUnregisterBoardCast == 1) {
						// 弹出提示框不能触碰设置
						AlertDialog.Builder alert = new AlertDialog.Builder(
								MainActivity.this);
						alert.setTitle("提示");
						alert.setMessage("启动服务后，不能修改服务数据");

						alert.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}

								});

						alert.show();

					}
					break;
				}
				}
				return false;
			}
		});

	}

	// 对edittext锁定

	/**
	 * 初始化滑动菜单
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {

		// 设置滑动菜单的视图
		// 这里设置一个没有任何关系的布局，原因未知
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.left_menu_frame, new MenuLeftFragment()).commit();
		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动阴影的图像资源
		sm.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	public void OpenLeftMenu(View view) {
		toggle();
	}

	public void OpenBackMenu(View view) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, BackMenuActivity.class);
		startActivity(intent);
	}

	/**
	 * 菜单按钮点击事件，通过点击ActionBar的Home图标按钮来打开滑动菜单
	 */

	private void LoadUserInfo() {

		Map<String, String> params1 = preferencesService
				.getOutcomingOrderPerferences();

		Map<String, String> params2 = preferencesService
				.getLocationOrderPerferences();

		Map<String, String> params3 = preferencesService
				.getRingOrderPerferences();

		Map<String, String> params4 = preferencesService
				.getVibrateOrderPerferences();

		Map<String, String> params5 = preferencesService
				.getPhoneNumberPerferences();

		if (params1.get("OutcomingOrder") == "") {
			preferencesService.saveOutcomingOrder("dialog");
		}
		if (params2.get("LocationOrder") == "") {
			preferencesService.saveLocationOrder("where");

		}
		if (params3.get("RingOrder") == "") {
			preferencesService.saveRingOrder("ring", "4000");

		}
		if (params4.get("VibrateOrder") == "") {
			preferencesService.saveVibrateOrder("vibrate", "3000");

		}

		if (params5.get("PhoneNumber") != "") {

			phoneNumber.setText(preferencesService.getPhoneNumberPerferences()
					.get("PhoneNumber"));
		}

		// 初始化是否返回桌面
		Map<String, String> params6 = preferencesService
				.getIsReturnHomePerferences();
		if (params6.get("IsReturnHome") == ""
				&& params6.get("IsShowInNext") == "") {
			preferencesService.saveIsReturnHome("1", "1");

		}

	}

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		setTitle("点击了长按菜单里面的第" + item.getItemId() + "个项目");
		return super.onContextItemSelected(item);
	}

	public void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getActionBar();
		if (null != actionBar) {

			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);

			LayoutInflater inflator = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v, layout);
		}
	}

	private static final int NOTICE_ID = 1222;

	private void shownotity() {

		// 获得通知管理器，通知是一项系统服务

		final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// 初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间

		Notification notification = new Notification(R.drawable.ic_launcher,
				"服务启动", System.currentTimeMillis());
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		// 点击通知后的Intent，此例子点击后还是在当前界面
		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(
				this, MainActivity.class), 0);

		// 设置通知信息
		notification.setLatestEventInfo(this, "服务启动", "被控端服务启动", intent);

		// 通知

		manager.notify(NOTICE_ID, notification);

	}

	private void buildContactData() {
		List<Contact> cantactlist = call
				.getPhoneContacts(getApplicationContext());
		String[] phonenumber = new String[cantactlist.size()];
		for (int i = 0; i < cantactlist.size(); i++) {
			phonenumber[i] = cantactlist.get(i).getmContactsNumber();
		}

		ContactList = new ArrayList<Contact>();
		for (int i = 0; i < phonenumber.length; i++) {
			Contact contact = new Contact();
			contact.setmContactsName(cantactlist.get(i).getmContactsName());
			contact.setmContactsNumber(cantactlist.get(i).getmContactsNumber());
			contact.setmContactsPhonto(cantactlist.get(i).getmContactsPhonto());
			ContactList.add(contact);
		}

	}

	private void FindContactData() {
		phoneNumber = (AutoCompleteTextView) findViewById(R.id.ET_PhoneNumber);
		PhoneAdapter mAdapter = new PhoneAdapter(ContactList,
				getApplicationContext());
		phoneNumber.setAdapter(mAdapter);
		phoneNumber.setThreshold(1); // 设置输入一个字符 提示，默认为2

		phoneNumber.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {

			}
		});

	}

	private class shakeLitener implements OnShakeListener {

		@Override
		public void onShake() {
			FrameLayout fl = (FrameLayout) findViewById(R.id.activity_main_id);
			int num = (int) ((Math.random()) * 3);

			if (num == 0) {
				fl.setBackgroundResource(R.drawable.bg3);

			} else if (num == 1) {
				fl.setBackgroundResource(R.drawable.main_bg);

			} else if (num == 2) {
				fl.setBackgroundResource(R.drawable.main_bei);

			}

		}

	}

}
