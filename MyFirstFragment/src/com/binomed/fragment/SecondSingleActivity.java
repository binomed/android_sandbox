package com.binomed.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class SecondSingleActivity extends FragmentActivity implements FragmentB.OnReadyFragment {
	/** Called when the activity is first created. */
	private Fragment fragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty);

		fragment = new FragmentB();
		getSupportFragmentManager().beginTransaction().add(R.id.root_container, fragment).commit();

		((FragmentB) fragment).setListener(this);

	}

	@Override
	public void ready() {
		((FragmentB) fragment).changeText();

	}

}