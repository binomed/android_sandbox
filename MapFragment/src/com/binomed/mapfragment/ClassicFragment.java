package com.binomed.mapfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClassicFragment extends Fragment {

	Fragment webFragment, mapFragment;

	private static final String TAG_MAP = ClassicFragment.class.getName() + "mapTag";
	private static final String TAG_WEB = ClassicFragment.class.getName() + "webTag";

	private boolean web;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.fragment_classic, container, false);
		webFragment = new WebFragment();
		mapFragment = new MapFragment();
		changeAdapter(web);
		return mainView;
	}

	public void changeAdapter(boolean web) {
		this.web = web;
		Fragment tmpFragment = null;
		if (!web) {
			tmpFragment = getFragmentManager().findFragmentByTag(TAG_WEB);
		} else {
			tmpFragment = getFragmentManager().findFragmentByTag(TAG_MAP);
		}

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		if (tmpFragment != null) {
			transaction.replace(R.id.root, web ? webFragment : mapFragment, web ? TAG_WEB : TAG_MAP);
		} else {
			transaction.add(R.id.root, web ? webFragment : mapFragment, web ? TAG_WEB : TAG_MAP);
		}

		transaction.commit();
	}

}
