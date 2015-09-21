package org.hhm.way.left;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hhm.way.R;
import org.hhm.way.util.PreferencesService;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SetActivity extends Activity {
	private PreferencesService preferencesService;

	TextView tv_left_actionbar;

	Button btn_back_to_left;

	CheckBox ck_1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

		setContentView(R.layout.left_set);
		setActionBarLayout(R.layout.actionbar_left);

		preferencesService = new PreferencesService(this);

		final Map<String, String> params = preferencesService
				.getIsReturnHomePerferences();
		ck_1 = (CheckBox) findViewById(R.id.checkBox1);
		if (params.get("IsReturnHome").equals("1")) {

			ck_1.setChecked(true);

		}
		ck_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					preferencesService.saveIsReturnHome("1",
							params.get("IsShowInNext"));

				} else {
					preferencesService.saveIsReturnHome("0",
							params.get("IsShowInNext"));
				}

			}
		});

		final ListView list = (ListView) findViewById(R.id.LV_Left_Set);

		final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("set_description", "控制手机回拨电话的指令:");
		map1.put("set_order", "指令:");

		Map<String, String> params1 = preferencesService
				.getOutcomingOrderPerferences();

		map1.put("set_order_value", params1.get("OutcomingOrder"));

		listItem.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("set_description", "获得手机当前位置的指令:");
		map2.put("set_order", "指令:");

		Map<String, String> params2 = preferencesService
				.getLocationOrderPerferences();

		map2.put("set_order_value", params2.get("LocationOrder"));

		listItem.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("set_description", "控制手机震动的指令:");
		map3.put("set_order", "指令:");

		Map<String, String> params3 = preferencesService
				.getVibrateOrderPerferences();

		map3.put("set_order_value", params3.get("VibrateOrder"));
		map3.put("set_ordertime", "持续时间:");
		map3.put("set_ordertime_value", params3.get("VibrateTime"));
		listItem.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("set_description", "控制手机播放铃声指令:");
		map4.put("set_order", "指令:");

		Map<String, String> params4 = preferencesService
				.getRingOrderPerferences();

		map4.put("set_order_value", params4.get("RingOrder"));
		map4.put("set_ordertime", "持续时间:");
		map4.put("set_ordertime_value", params4.get("RingTime"));
		listItem.add(map4);

		final SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,

		R.layout.listview_left_set, new String[] { "set_description",
				"set_order", "set_order_value", "set_ordertime",
				"set_ordertime_value" }, new int[] { R.id.set_description,
				R.id.set_order, R.id.set_order_value, R.id.set_ordertime,
				R.id.set_ordertime_value });

		list.setAdapter(listItemAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				View view = LayoutInflater.from(SetActivity.this).inflate(
						R.layout.alertwindow_userinfo, null);
				final EditText ET_order = (EditText) view
						.findViewById(R.id.alertwindow_userinfo_order);
				final EditText ET_ordertime = (EditText) view
						.findViewById(R.id.alertwindow_userinfo_ordertime);

				if (index == 0) {

					Builder builder = new Builder(SetActivity.this);

					builder.setTitle("设置回拨短号指令");
					builder.setIcon(R.drawable.ic_launcher);

					// 隐藏
					ET_ordertime.setVisibility(View.INVISIBLE);

					ET_order.setText(preferencesService
							.getOutcomingOrderPerferences().get(
									"OutcomingOrder"));
					builder.setView(view);
					builder.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							preferencesService.saveOutcomingOrder(ET_order
									.getText().toString());
							listItem.get(0).put("set_order_value",
									ET_order.getText());
							listItemAdapter.notifyDataSetChanged();

						}
					});
					builder.setNegativeButton("取消", null);
					builder.show();
				} else if (index == 1) {

					Builder builder = new Builder(SetActivity.this);

					builder.setTitle("设置获得被控手机位置指令");
					builder.setIcon(R.drawable.ic_launcher);

					// 隐藏
					ET_ordertime.setVisibility(View.INVISIBLE);
					ET_order.setText(preferencesService
							.getLocationOrderPerferences().get("LocationOrder"));
					builder.setView(view);
					builder.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							preferencesService.saveLocationOrder(ET_order
									.getText().toString());

							listItem.get(1).put("set_order_value",
									ET_order.getText());
							listItemAdapter.notifyDataSetChanged();

						}
					});
					builder.setNegativeButton("取消", null);
					builder.show();

				} else if (index == 2) {

					Builder builder = new Builder(SetActivity.this);

					builder.setTitle("设置被控手机震动指令");
					builder.setIcon(R.drawable.ic_launcher);

					ET_order.setText(preferencesService
							.getVibrateOrderPerferences().get("VibrateOrder"));

					ET_ordertime.setText(preferencesService
							.getVibrateOrderPerferences().get("VibrateTime"));
					builder.setView(view);
					builder.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							preferencesService.saveVibrateOrder(ET_order
									.getText().toString(), ET_ordertime
									.getText().toString());

							listItem.get(2).put("set_order_value",
									ET_order.getText());
							listItem.get(2).put("set_ordertime_value",
									ET_ordertime.getText());
							listItemAdapter.notifyDataSetChanged();

						}
					});
					builder.setNegativeButton("取消", null);
					builder.show();

				} else if (index == 3) {

					Builder builder = new Builder(SetActivity.this);

					builder.setTitle("设置被控手机响铃指令");
					builder.setIcon(R.drawable.ic_launcher);

					ET_order.setText(preferencesService
							.getRingOrderPerferences().get("RingOrder"));
					ET_ordertime.setText(preferencesService
							.getRingOrderPerferences().get("RingTime"));
					builder.setView(view);
					builder.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							preferencesService.saveRingOrder(ET_order.getText()
									.toString(), ET_ordertime.getText()
									.toString());

							listItem.get(3).put("set_order_value",
									ET_order.getText());
							listItem.get(3).put("set_ordertime_value",
									ET_ordertime.getText());
							listItemAdapter.notifyDataSetChanged();

						}
					});
					builder.setNegativeButton("取消", null);
					builder.show();

				}

			}
		});

	}

	public void setActionBarLayout(int layoutId) {

		ActionBar actionBar = getActionBar();

		if (null != actionBar) {

			actionBar.setDisplayShowHomeEnabled(false);

			actionBar.setDisplayShowCustomEnabled(true);

			LayoutInflater inflator = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View v = inflator.inflate(layoutId, null);

			tv_left_actionbar = (TextView) v
					.findViewById(R.id.tv_left_actionbar);

			tv_left_actionbar.setText("设置");

			// 设置按钮返回
			btn_back_to_left = (Button) v.findViewById(R.id.btn_back_to_left);
			btn_back_to_left
					.setOnClickListener(new android.view.View.OnClickListener() {

						@Override
						public void onClick(View arg0) {
							finish();

						}
					});

			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

			actionBar.setCustomView(v, layout);

		}

	}
}
