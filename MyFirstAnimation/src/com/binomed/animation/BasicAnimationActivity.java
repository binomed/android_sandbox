package com.binomed.animation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class BasicAnimationActivity extends Activity {
	/** Called when the activity is first created. */

	private View layoutRight, layoutLeft;
	private ImageButton btnExpand;
	private ListView list;

	private int dist = 200;

	private boolean hideRight;

	private AdapaterFull adapaterFull;
	private AdapaterLight adapaterLight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_animation);

		layoutRight = findViewById(R.id.layoutRight);
		layoutLeft = findViewById(R.id.layoutLeft);
		btnExpand = (ImageButton) findViewById(R.id.btnExpand);
		list = (ListView) findViewById(R.id.listView);
		hideRight = false;

		adapaterFull = new AdapaterFull(getApplicationContext());
		adapaterLight = new AdapaterLight(getApplicationContext());
		list.setAdapter(adapaterLight);

		btnExpand.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hideRight = !hideRight;
				if (hideRight) {
					list.setAdapter(adapaterFull);
					btnExpand.setImageResource(android.R.drawable.ic_media_ff);
					TranslateAnimation animation = new TranslateAnimation(layoutRight.getLeft(), layoutRight.getLeft() + dist, 0, 0);
					animation = new TranslateAnimation(0, dist, 0, 0);
					animation.setStartOffset(0);// layoutRight.getLeft());
					animation.setDuration(500);
					animation.setFillAfter(true);
					layoutRight.startAnimation(animation);
				} else {
					list.setAdapter(adapaterLight);
					btnExpand.setImageResource(android.R.drawable.ic_media_rew);
					TranslateAnimation animation = new TranslateAnimation(layoutRight.getLeft() + dist, layoutRight.getLeft(), 0, 0);
					animation = new TranslateAnimation(dist, 0, 0, 0);
					animation.setStartOffset(0);// layoutRight.getLeft());
					animation.setDuration(500);
					animation.setFillAfter(true);
					layoutRight.startAnimation(animation);
				}

			}
		});

	}

	class AdapaterFull extends BaseAdapter {

		private Context ctx;

		public AdapaterFull(Context ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			TextView text = null;
			if (arg1 != null) {
				text = (TextView) arg1;
			} else {
				text = new TextView(ctx);
			}
			text.setText("TextFull" + arg0);
			return text;
		}

	}

	class AdapaterLight extends BaseAdapter {

		private Context ctx;

		public AdapaterLight(Context ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			TextView text = null;
			if (arg1 != null) {
				text = (TextView) arg1;
			} else {
				text = new TextView(ctx);
			}
			text.setText("TextLight" + arg0);
			return text;
		}

	}

}