package com.binomed.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FirstSingleActivity extends FragmentActivity implements FragmentA.OnBtnClick {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty);

		Fragment fragment = new FragmentA();

		getSupportFragmentManager().beginTransaction().add(R.id.root_container, fragment).commit();

	}

	@Override
	public void btnClick() {
		Intent intent = new Intent(this, SecondSingleActivity.class);
		startActivity(intent);

	}
}