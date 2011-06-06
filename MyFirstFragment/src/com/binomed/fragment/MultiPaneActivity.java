package com.binomed.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MultiPaneActivity extends FragmentActivity implements FragmentA.OnBtnClick {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multi_main);
	}

	@Override
	public void btnClick() {
		FragmentB fragB = (FragmentB) getSupportFragmentManager().findFragmentById(R.id.fragmentB);
		fragB.changeText();

	}
}