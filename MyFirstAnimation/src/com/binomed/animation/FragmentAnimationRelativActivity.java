package com.binomed.animation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class FragmentAnimationRelativActivity extends FragmentActivity {
	/** Called when the activity is first created. */

	private ImageButton btnExpand;
	private final Handler mHandler = new Handler();

	private int dist = 200;
	private int delay = 500;
	private int widthLeftFull, widthLeftLight;

	private boolean hideRight;

	private AdapaterFull adapaterFull;
	private AdapaterLight adapaterLight;

	private FrameLayout frameLayout = null;
	private FragmentEditText fragText = null;
	private FragmentList fragList = null;

	private FrameLayout.LayoutParams paramsLeft, paramsRight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_relativ_animation);

		btnExpand = (ImageButton) findViewById(R.id.btnExpand);
		frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
		fragText = (FragmentEditText) getSupportFragmentManager().findFragmentById(R.id.fragmentText);
		fragList = (FragmentList) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
		hideRight = false;

		adapaterFull = new AdapaterFull(getApplicationContext());
		adapaterLight = new AdapaterLight(getApplicationContext());
		fragList.changeAdapter(adapaterLight);
		frameLayout.setVisibility(View.INVISIBLE);

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				paramsLeft = (android.widget.FrameLayout.LayoutParams) fragList.getView().getLayoutParams();
				paramsRight = (android.widget.FrameLayout.LayoutParams) fragText.getView().getLayoutParams();

				int totalWidth = frameLayout.getWidth();
				widthLeftFull = Double.valueOf(totalWidth * 0.50).intValue();
				widthLeftLight = Double.valueOf(totalWidth * 0.25).intValue();
				dist = widthLeftFull - widthLeftLight;
				int widthRight = Double.valueOf(totalWidth * 0.75).intValue();
				// widthLeft = 100;
				// widthRight = 300;

				// paramsLeft = new FrameLayout.LayoutParams(widthLeft, android.widget.FrameLayout.LayoutParams.FILL_PARENT);
				// paramsRight = new FrameLayout.LayoutParams(widthRight, android.widget.FrameLayout.LayoutParams.FILL_PARENT);
				paramsLeft.width = widthLeftLight;
				paramsRight.width = widthRight;
				paramsRight.gravity = Gravity.RIGHT;

				fragList.getView().setLayoutParams(paramsLeft);
				fragText.getView().setLayoutParams(paramsRight);
				frameLayout.setVisibility(View.VISIBLE);
			}
		}, delay + 200);

		btnExpand.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hideRight = !hideRight;
				extendList();
				fragList.requestFocus();

			}
		});
		View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (!arg1 && hideRight) {
					hideRight = false;
					extendList();
				}
			}
		};

		fragList.setOnFocusListener(focusListener);

	}

	private void extendList() {
		if (hideRight) {
			TranslateAnimation animation = new TranslateAnimation(fragText.getView().getLeft(), fragText.getView().getLeft() + dist, 0, 0);
			animation = new TranslateAnimation(0, dist, 0, 0);
			animation.setStartOffset(0);// layoutRight.getLeft());
			animation.setDuration(delay);
			animation.setFillAfter(true);
			fragText.getView().startAnimation(animation);

			fragList.changeAdapter(adapaterFull);

			paramsLeft.width = widthLeftFull;
			fragList.getView().setLayoutParams(paramsLeft);

			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					btnExpand.setImageResource(android.R.drawable.ic_media_rew);
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
					btnExpand.setImageResource(android.R.drawable.ic_media_ff);
					paramsLeft.width = widthLeftLight;
					fragList.getView().setLayoutParams(paramsLeft);
					fragList.changeAdapter(adapaterLight);
				}
			}, delay + 200);
		}
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