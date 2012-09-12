package com.binomed.mapfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerFragment extends Fragment {

	private ViewPager viewPager;

	private FragmentPagerAdapter adapterWeb, adapterMap;

	private boolean web;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.fragment_viewpager, container, false);

		adapterMap = new MapFragmentPagerAdapter(getFragmentManager());
		adapterWeb = new WebFragmentPagerAdapter(getFragmentManager());

		viewPager = (ViewPager) mainView.findViewById(R.id.pager);
		if (!web) {
			viewPager.setAdapter(adapterMap);
		} else {
			viewPager.setAdapter(adapterWeb);

		}

		return mainView;
	}

	public void changeAdapter(boolean web) {
		this.web = web;
		if (viewPager != null) {
			viewPager.setAdapter(web ? adapterWeb : adapterMap);
			viewPager.getAdapter().notifyDataSetChanged();
		}
	}

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
