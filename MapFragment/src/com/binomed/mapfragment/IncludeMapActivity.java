package com.binomed.mapfragment;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

public class IncludeMapActivity extends MapActivity {

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_map);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
