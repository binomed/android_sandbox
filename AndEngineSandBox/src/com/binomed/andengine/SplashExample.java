package com.binomed.andengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashExample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btnTank = (Button) findViewById(R.id.tank);
		Button btnPeg = (Button) findViewById(R.id.peg);
		Button btnPegBis = (Button) findViewById(R.id.pegbis);
		Button btnTest = (Button) findViewById(R.id.tests);

		btnTank.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MonActivite.class);
				startActivity(intent);
			}
		});
		btnPeg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MonActiviteInteraction.class);
				startActivity(intent);

			}
		});
		btnPegBis.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MonActiviteDansUneVue.class);
				startActivity(intent);

			}
		});
		btnTest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MonJeuActivite.class);
				startActivity(intent);

			}
		});

	}

}
