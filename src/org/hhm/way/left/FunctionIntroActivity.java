package org.hhm.way.left;

import java.util.ArrayList;

import org.hhm.way.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FunctionIntroActivity extends Activity {
	
	TextView tv_left_actionbar;

	Button btn_back_to_left;
	
	
	
	
	 private ViewPager viewPager;  
	 private ArrayList<View> pageViews;  
	 private ImageView imageView;  
	 private ImageView[] imageViews; 
	 // 包裹滑动图片LinearLayout
	 private ViewGroup main;
	 // 包裹小圆点的LinearLayout
	 private ViewGroup group;
	    
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // 设置无标题窗口
      //requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setActionBarLayout(R.layout.actionbar_left);

      LayoutInflater inflater = getLayoutInflater();  
      pageViews = new ArrayList<View>();  
   
      pageViews.add(inflater.inflate(R.layout.function_intro_1, null));  
      pageViews.add(inflater.inflate(R.layout.function_intro_2, null));  
  
      
      imageViews = new ImageView[pageViews.size()];  
      main = (ViewGroup)inflater.inflate(R.layout.function_intro_main, null);  
      
      group = (ViewGroup)main.findViewById(R.id.viewGroup);  
      viewPager = (ViewPager)main.findViewById(R.id.guidePages);  
      
      for (int i = 0; i < pageViews.size(); i++) {  
          imageView = new ImageView(FunctionIntroActivity.this);  
          imageView.setLayoutParams(new LayoutParams(20,20));  
          imageView.setPadding(20, 0, 20, 0);  
          imageViews[i] = imageView;  
          
          if (i == 0) {  
              //默认选中第一张图片
              imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);  
          } else {  
              imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
          }  
          
          group.addView(imageViews[i]);  
      }  
      
      setContentView(main);
      
      viewPager.setAdapter(new GuidePageAdapter());  
      viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
  }
  
  // 指引页面数据适配器
  class GuidePageAdapter extends PagerAdapter {  
	  
      @Override  
      public int getCount() {  
          return pageViews.size();  
      }  

      @Override  
      public boolean isViewFromObject(View arg0, Object arg1) {  
          return arg0 == arg1;  
      }  

      @Override  
      public int getItemPosition(Object object) {  
          // TODO Auto-generated method stub  
          return super.getItemPosition(object);  
      }  

      @Override  
      public void destroyItem(View arg0, int arg1, Object arg2) {  
          // TODO Auto-generated method stub  
          ((ViewPager) arg0).removeView(pageViews.get(arg1));  
      }  

      @Override  
      public Object instantiateItem(View arg0, int arg1) {  
          // TODO Auto-generated method stub  
          ((ViewPager) arg0).addView(pageViews.get(arg1));  
          return pageViews.get(arg1);  
      }  

      @Override  
      public void restoreState(Parcelable arg0, ClassLoader arg1) {  
          // TODO Auto-generated method stub  

      }  

      @Override  
      public Parcelable saveState() {  
          // TODO Auto-generated method stub  
          return null;  
      }  

      @Override  
      public void startUpdate(View arg0) {  
          // TODO Auto-generated method stub  

      }  

      @Override  
      public void finishUpdate(View arg0) {  
          // TODO Auto-generated method stub  

      }  
  } 
  
  // 指引页面更改事件监听器
  class GuidePageChangeListener implements OnPageChangeListener {  
  	  
      @Override  
      public void onPageScrollStateChanged(int arg0) {  
          // TODO Auto-generated method stub  

      }  

      @Override  
      public void onPageScrolled(int arg0, float arg1, int arg2) {  
          // TODO Auto-generated method stub  

      }  

      @Override  
      public void onPageSelected(int arg0) {  
          for (int i = 0; i < imageViews.length; i++) {  
              imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
              
              if (arg0 != i) {  
                  imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
              }  
          }
      }  
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

			tv_left_actionbar.setText("功能说明");

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

