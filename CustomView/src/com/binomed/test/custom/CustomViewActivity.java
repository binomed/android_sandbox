package com.binomed.test.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CustomViewActivity extends FragmentActivity implements OnCheckedChangeListener {

	private static final boolean expandView = true;
	private static final boolean framgentView = true;

	private CheckBox expandListCheck, cacheColorCheck, transparentColorCheck;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
	}

	private void initViews() {

		expandListCheck = (CheckBox) findViewById(R.id.expandCheck);
		cacheColorCheck = (CheckBox) findViewById(R.id.useCacheColor);
		transparentColorCheck = (CheckBox) findViewById(R.id.transparentCacheColor);

		expandListCheck.setOnCheckedChangeListener(this);
		cacheColorCheck.setOnCheckedChangeListener(this);
		transparentColorCheck.setOnCheckedChangeListener(this);

		display();

	}

	private void display() {
		IFragmentCacheColor fragment = null;
		Fragment fragmentRecycle = getSupportFragmentManager().findFragmentById(R.id.root_container);

		if (expandListCheck.isChecked()) {
			fragment = getFragmentExpand(fragmentRecycle);
		} else {
			fragment = getFragmentList(fragmentRecycle);

		}
		fragment.setCacheColorHint(cacheColorCheck.isChecked());
		fragment.setTransparentCacheColorHint(transparentColorCheck.isChecked());
		transparentColorCheck.setEnabled(cacheColorCheck.isChecked());

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (fragmentRecycle == null) {
			transaction.add(R.id.root_container, (Fragment) fragment);
		} else if ((fragment != null) && !fragmentRecycle.equals(fragment)) {
			transaction.replace(R.id.root_container, (Fragment) fragment);
		}
		transaction.commit();

	}

	protected IFragmentCacheColor getFragmentExpand(Fragment fragmentRecycle) {
		ExpandableFragment resultFragment = null;
		if (fragmentRecycle != null && fragmentRecycle instanceof ExpandableFragment) {
			resultFragment = (ExpandableFragment) fragmentRecycle;
		} else {
			resultFragment = new ExpandableFragment();
		}
		return resultFragment;
	}

	protected IFragmentCacheColor getFragmentList(Fragment fragmentRecycle) {
		ListFragment resultFragment = null;
		if (fragmentRecycle != null && fragmentRecycle instanceof ListFragment) {
			resultFragment = (ListFragment) fragmentRecycle;
		} else {
			resultFragment = new ListFragment();
		}
		return resultFragment;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		display();
	}
}