package com.binomed.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class FragmentTab extends Fragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.fragment_tab, container, false);

		TabHost tabHost = (TabHost) mainView.findViewById(android.R.id.tabhost);
		tabHost.setup();
		TabSpec tab1 = tabHost.newTabSpec("Tab");
		tab1.setContent(new TabContentFactory() {

			@Override
			public View createTabContent(String arg0) {
				TextView view = new TextView(getActivity());
				view.setText("Un texte");
				return view;
			}
		});
		tab1.setIndicator("onglet 1");
		TabSpec tab2 = tabHost.newTabSpec("Tab2");
		tab2.setContent(new TabContentFactory() {

			@Override
			public View createTabContent(String arg0) {
				TextView view = new TextView(getActivity());
				view.setText("Un texte 2 ");
				return view;
			}
		});
		tab2.setIndicator("onglet 2");

		tabHost.addTab(tab1);
		tabHost.addTab(tab2);

		return mainView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
