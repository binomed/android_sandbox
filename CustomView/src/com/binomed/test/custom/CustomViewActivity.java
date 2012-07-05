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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author Jef Basic class for testing the cache color hint mecanism with custom view in order to have a great performance
 * 
 */
public class CustomViewActivity extends FragmentActivity implements OnCheckedChangeListener {

	/**
	 * All ui checkBox
	 */
	private CheckBox expandListCheck, cacheColorCheck, transparentColorCheck;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
	}

	/**
	 * Inflate the ui Views
	 */
	private void initViews() {

		expandListCheck = (CheckBox) findViewById(R.id.expandCheck);
		cacheColorCheck = (CheckBox) findViewById(R.id.useCacheColor);
		transparentColorCheck = (CheckBox) findViewById(R.id.transparentCacheColor);

		expandListCheck.setOnCheckedChangeListener(this);
		cacheColorCheck.setOnCheckedChangeListener(this);
		transparentColorCheck.setOnCheckedChangeListener(this);

		display();

	}

	/**
	 * Method which change the fragment to show according to checkboxs
	 */
	private void display() {
		Fragment fragmentRecycle = getSupportFragmentManager().findFragmentById(R.id.root_container);
		Fragment fragment = getFragment();
		// This checkbox has only senses if the cacheColorCheck is checked
		transparentColorCheck.setEnabled(cacheColorCheck.isChecked());

		// We replace the correct framgent
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (fragmentRecycle == null) {
			transaction.add(R.id.root_container, fragment);
		} else if ((fragment != null) && !fragmentRecycle.equals(fragment)) {
			transaction.replace(R.id.root_container, fragment);
		}
		transaction.commit();

	}

	/**
	 * Gives the fragment corresponding
	 * 
	 * @param fragmentRecycle
	 * @return
	 */
	protected Fragment getFragment() {
		if (expandListCheck.isChecked()) {
			ExpandableFragment resultFragment = null;
			resultFragment = new ExpandableFragment();
			resultFragment.setLayout(cacheColorCheck.isChecked() ? transparentColorCheck.isChecked() && transparentColorCheck.isEnabled() ? R.layout.fragment_expand_cache_color_transparent : R.layout.fragment_expand_cache_color_gray : R.layout.fragment_expand);
			return resultFragment;
		} else {

			ListFragment resultFragment = null;
			resultFragment = new ListFragment();
			resultFragment.setLayout(cacheColorCheck.isChecked() ? transparentColorCheck.isChecked() && transparentColorCheck.isEnabled() ? R.layout.fragment_list_cache_color_transparent : R.layout.fragment_list_cache_color_gray : R.layout.fragment_list);
			return resultFragment;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// Each times a chek
		display();
	}
}