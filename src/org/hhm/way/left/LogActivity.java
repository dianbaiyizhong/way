package org.hhm.way.left;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hhm.way.R;
import org.hhm.way.db.impl.LogImpl;
import org.hhm.way.db.pojo.Log;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LogActivity extends Activity {
	TextView tv_left_actionbar;
	Button btn_back_to_left;
	Button btn_refresh;
	LogImpl logImpl = new LogImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.log);
		setActionBarLayout(R.layout.actionbar_log);

		List<Log> loglist = logImpl.get(getApplicationContext());

		final ListView list = (ListView) findViewById(R.id.LV_log);

		final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		if (loglist.size() != 0) {

			for (int i = 0; i < loglist.size(); i++) {

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("title", loglist.get(i).getTitle());
				map.put("content", loglist.get(i).getContent());
				map.put("date", loglist.get(i).getDate());

				listItem.add(map);
			}
		} else {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", "数据为空!");
			map.put("content", "");
			map.put("date", "");

			listItem.add(map);
		}
		final SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,

		R.layout.listview_log, new String[] { "title", "content", "date" },
				new int[] { R.id.log_title, R.id.log_content, R.id.log_date });

		list.setAdapter(listItemAdapter);

		btn_refresh = (Button) findViewById(R.id.btn_refresh);

		btn_refresh.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						LogActivity.this);
				alert.setTitle("提示");
				alert.setMessage("确定要删除么?");
				alert.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								logImpl.Delete(getApplicationContext());

								// 删除listview的数据
								list.setAdapter(null);
								ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("title", "数据为空!");
								map.put("content", "");
								map.put("date", "");

								listItem.add(map);

								list.setAdapter(new SimpleAdapter(
										LogActivity.this,
										listItem,
										R.layout.listview_log,
										new String[] { "title", "content",
												"date" },
										new int[] { R.id.log_title,
												R.id.log_content, R.id.log_date }));

								listItemAdapter.notifyDataSetChanged();
							}

						});

				alert.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}

						});

				alert.show();

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
