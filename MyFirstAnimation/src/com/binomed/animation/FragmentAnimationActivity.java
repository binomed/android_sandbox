package com.binomed.animation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class FragmentAnimationActivity extends FragmentActivity {
	/** Called when the activity is first created. */

	private ImageButton btnExpand;
	private final Handler mHandler = new Handler();

	private int dist = 200;
	private int delay = 500;

	private boolean hideRight;

	private AdapaterFull adapaterFull;
	private AdapaterLight adapaterLight;

	private FragmentEditText fragText = null;
	private FragmentList fragList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_animation);

		btnExpand = (ImageButton) findViewById(R.id.btnExpand);
		fragText = (FragmentEditText) getSupportFragmentManager().findFragmentById(R.id.fragmentText);
		fragList = (FragmentList) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
		hideRight = false;

		adapaterFull = new AdapaterFull(getApplicationContext());
		adapaterLight = new AdapaterLight(getApplicationContext());
		fragList.changeAdapter(adapaterLight);

		btnExpand.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hideRight = !hideRight;
				if (hideRight) {
					final int witdth = fragText.getView().getWidth();
					TranslateAnimation animation = new TranslateAnimation(fragText.getView().getLeft(), fragText.getView().getLeft() + dist, 0, 0);
					animation = new TranslateAnimation(0, dist, 0, 0);
					animation.setStartOffset(0);// layoutRight.getLeft());
					animation.setDuration(delay);
					animation.setFillAfter(true);
					fragText.getView().startAnimation(animation);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							btnExpand.setImageResource(android.R.drawable.ic_media_rew);
							fragList.changeAdapter(adapaterFull);
							fragList.getView().setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 75));
							// fragText.getView().setLayoutParams(new LinearLayout.LayoutParams(witdth, LayoutParams.FILL_PARENT));
						}
					}, delay + 200);
				} else {
					TranslateAnimation animation = new TranslateAnimation(fragText.getView().getLeft() + dist, fragText.getView().getLeft(), 0, 0);
					animation = new TranslateAnimation(dist, 0, 0, 0);
					animation.setStartOffset(0);// layoutRight.getLeft());
					animation.setDuration(500);
					animation.setFillAfter(true);
					fragText.getView().startAnimation(animation);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							fragList.changeAdapter(adapaterLight);
							btnExpand.setImageResource(android.R.drawable.ic_media_ff);
							fragList.getView().setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 25));
						}
					}, delay + 200);
				}

			}
		});

		fragText.getView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					TranslateAnimation animation = new TranslateAnimation(fragText.getView().getLeft() + dist, fragText.getView().getLeft(), 0, 0);
					animation = new TranslateAnimation(dist, 0, 0, 0);
					animation.setStartOffset(0);// layoutRight.getLeft());
					animation.setDuration(500);
					animation.setFillAfter(true);
					fragText.getView().startAnimation(animation);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							fragList.changeAdapter(adapaterLight);
							btnExpand.setImageResource(android.R.drawable.ic_media_ff);
							fragList.getView().setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 50));
						}
					}, delay);
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