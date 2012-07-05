/*
 * Copyright (C) 2012 Binomed (http://blog.binomed.fr)
 *
 * Licensed under the Eclipse Public License - v 1.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE PUBLIC 
 * LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR DISTRIBUTION OF THE PROGRAM 
 * CONSTITUTES RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.
 */
package com.binomed.test.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class ExpandableFragment extends Fragment {

	/**
	 * The expandable list
	 */
	private ExpandableListView list;
	/**
	 * The base adapter
	 */
	private CustomAdapterExpand adapter;

	/**
	 * The layout to use
	 */
	private int layout;

	public ExpandableFragment() {
		super();
	}

	/**
	 * @param layout
	 */
	public void setLayout(int layout) {
		this.layout = layout;
	}

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mainView = inflater.inflate(layout, container, false);
		list = (ExpandableListView) mainView.findViewById(R.id.listExpand);
		// We don't want a group indicator
		list.setGroupIndicator(null);
		adapter = new CustomAdapterExpand(getActivity().getApplicationContext());
		list.setAdapter(adapter);
		return mainView;

	}

}
