package com.binomed.mapfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {// FragmentActivity {

	private CheckBox checkWeb;
	private ViewPager viewPager;
	private Fragment webFragment, mapFragment;
	private FragmentPagerAdapter adapterWeb, adapterMap, adapterCustom;

	private static final String TAG_MAP = MainActivity.class.getName() + "mapTag";
	private static final String TAG_WEB = MainActivity.class.getName() + "webTag";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);

		checkWeb = (CheckBox) findViewById(R.id.checkWeb);
		viewPager = (ViewPager) findViewById(R.id.pager);

		checkWeb.setChecked(true);

		webFragment = new WebFragment();
		mapFragment = new MapFragment();

		adapterMap = new MapFragmentPagerAdapter(getSupportFragmentManager());
		adapterWeb = new WebFragmentPagerAdapter(getSupportFragmentManager());
		adapterCustom = new CustomFragmentPagerAdapter(getSupportFragmentManager());

		changeAdapter(checkWeb.isChecked());

	}

	/**
	 * Handle the click of a Feature button.
	 * 
	 * @param v
	 *            View
	 * @return void
	 */

	public void onClickFeature(View v) {
		int id = v.getId();
		boolean checked = ((CheckBox) v).isChecked();
		switch (id) {
		case R.id.checkWeb:
			changeAdapter(checked);
			break;
		default:
			break;
		}
	}

	public void changeAdapter(boolean web) {
		Fragment tmpFragment = null;
		if (!web) {
			tmpFragment = getSupportFragmentManager().findFragmentByTag(TAG_WEB);
		} else {
			tmpFragment = getSupportFragmentManager().findFragmentByTag(TAG_MAP);
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (tmpFragment != null) {
			transaction.replace(R.id.content, web ? webFragment : mapFragment, web ? TAG_WEB : TAG_MAP);
		} else {
			transaction.add(R.id.content, web ? webFragment : mapFragment, web ? TAG_WEB : TAG_MAP);
		}

		transaction.commit();

		((CustomFragmentPagerAdapter) adapterCustom).setWeb(web);
		// viewPager.setAdapter(web ? adapterWeb : adapterMap);
		viewPager.setAdapter(adapterCustom);
		viewPager.getAdapter().notifyDataSetChanged();
	}

	class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

		private boolean web;

		public CustomFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public void setWeb(boolean web) {
			this.web = web;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Fragment getItem(int arg0) {
			// return this.web ? new WebFragment() : new MapFragment();
			return arg0 == 0 ? new WebFragment() : new MapFragment();
		}
	};

	class WebFragmentPagerAdapter extends FragmentPagerAdapter {

		public WebFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 1;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Fragment getItem(int arg0) {
			return new WebFragment();
		}
	};

	class MapFragmentPagerAdapter extends FragmentPagerAdapter {

		public MapFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public int getCount() {
			return 1;
		}

		@Override
		public Fragment getItem(int arg0) {
			return new MapFragment();
		}
	};

}
