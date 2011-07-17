package com.binomed.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyFirstAnimationActivity extends Activity {
	/** Called when the activity is first created. */

	private Button btnBasic, btnFragment, btnFragmentRelativ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnBasic = (Button) findViewById(R.id.basic_animation);
		btnFragment = (Button) findViewById(R.id.fragment_animation);
		btnFragmentRelativ = (Button) findViewById(R.id.fragment_relativ_animation);
		btnBasic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), BasicAnimationActivity.class);
				startActivity(intent);

			}
		});
		btnFragment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), FragmentAnimationActivity.class);
				startActivity(intent);

			}
		});
		btnFragmentRelativ.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), FragmentAnimationRelativActivity.class);
				startActivity(intent);

			}
		});

	}
}