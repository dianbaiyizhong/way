package org.hhm.way;

import java.util.ArrayList;
import java.util.HashMap;

import org.hhm.way.left.AboutActivity;
import org.hhm.way.left.FunctionIntroActivity;
import org.hhm.way.left.LogActivity;
import org.hhm.way.left.NoviceGuidActivity;
import org.hhm.way.left.SetActivity;
import org.hhm.way.left.UserBackActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("ShowToast") public class MenuLeftFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu_left, null);
		final ListView list = (ListView) view
				.findViewById(R.id.LV_FunctionMenu_Left);

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.menu_setting);
		map1.put("ItemTitle", "设置");
		listItem.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.menu_log);
		map2.put("ItemTitle", "日志");
		listItem.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ItemImage", R.drawable.menu_guide);
		map3.put("ItemTitle", "新手引导");
		listItem.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("ItemImage", R.drawable.menu_function);
		map4.put("ItemTitle", "功能说明");
		listItem.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("ItemImage", R.drawable.menu_about);
		map5.put("ItemTitle", "关于");
		listItem.add(map5);

		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("ItemImage", R.drawable.menu_update);
		map6.put("ItemTitle", "检查更新");
		listItem.add(map6);

		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("ItemImage", R.drawable.menu_user);
		map7.put("ItemTitle", "用户反馈");
		listItem.add(map7);

		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("ItemImage", R.drawable.menu_exit);
		map8.put("ItemTitle", "退出");
		listItem.add(map8);

		SimpleAdapter listItemAdapter = new SimpleAdapter(this.getActivity(),
				listItem,

				R.layout.listview_menu_left, new String[] { "ItemImage",
						"ItemTitle" }, new int[] { R.id.ItemImage,
						R.id.ItemTitle });

		list.setAdapter(listItemAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {

				// 调转到设置页面
				if (index == 0) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), SetActivity.class);
					startActivity(intent);
				} else if (index == 4) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), AboutActivity.class);
					startActivity(intent);
				} else if (index == 6) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), UserBackActivity.class);
					startActivity(intent);
				} else if (index == 1) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), LogActivity.class);
					startActivity(intent);
				} else if (index == 7) {

					AlertDialog.Builder alert = new AlertDialog.Builder(
							getActivity());
					alert.setTitle("提示");
					alert.setMessage("你要退出捕食者监听吗?");

					alert.setPositiveButton("退出",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									System.exit(0);
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

				}else if(index==2){
					Intent intent = new Intent();
					intent.setClass(getActivity(), NoviceGuidActivity.class);
					startActivity(intent);
				}else if(index==3){
					Intent intent = new Intent();
					intent.setClass(getActivity(), FunctionIntroActivity.class);
					startActivity(intent);
					
				}else if(index==5){
					Toast.makeText(getActivity(), "要检查更新?给我台服务器再说。", 3000).show();
				}

			}
		});

		return view;
		// }
	}
}
