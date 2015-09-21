package org.hhm.way.left;

import org.hhm.way.R;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {
	TextView tv_left_actionbar;

	Button btn_back_to_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

		setContentView(R.layout.about);
		setActionBarLayout(R.layout.actionbar_left);

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

			tv_left_actionbar.setText("关于");

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
