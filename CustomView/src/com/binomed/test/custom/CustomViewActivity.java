package com.binomed.test.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class CustomViewActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(new CustomAdapter());

		// setContentView(new CustomView(this));

	}

	class CustomAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 100;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			CustomView view = null;
			if (arg1 != null) {
				view = (CustomView) arg1;
			} else {
				view = new CustomView(getApplicationContext());
			}
			return view;
		}
	}

	class CustomView extends View {

		private Paint paint = new Paint();

		private String text;
		private int mAscent;

		public CustomView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		public CustomView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public CustomView(Context context) {
			super(context);
			paint.setColor(Color.WHITE);
			paint.setTextSize(25);
			paint.setAntiAlias(true);
		}

		/**
		 * @see android.view.View#measure(int, int)
		 */
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
		}

		/**
		 * Determines the width of this view
		 * 
		 * @param measureSpec
		 *            A measureSpec packed into an int
		 * @return The width of the view, honoring constraints from measureSpec
		 */
		private int measureWidth(int measureSpec) {
			int result = 0;
			int specMode = MeasureSpec.getMode(measureSpec);
			int specSize = MeasureSpec.getSize(measureSpec);

			if (specMode == MeasureSpec.EXACTLY) {
				// We were told how big to be
				result = specSize;
			} else {
				// Measure the text
				result = (int) paint.measureText(text) + getPaddingLeft() + getPaddingRight();
				if (specMode == MeasureSpec.AT_MOST) {
					// Respect AT_MOST value if that was what is called for by measureSpec
					result = Math.min(result, specSize);
				}
			}

			return result;
		}

		/**
		 * Determines the height of this view
		 * 
		 * @param measureSpec
		 *            A measureSpec packed into an int
		 * @return The height of the view, honoring constraints from measureSpec
		 */
		private int measureHeight(int measureSpec) {
			int result = 0;
			int specMode = MeasureSpec.getMode(measureSpec);
			int specSize = MeasureSpec.getSize(measureSpec);

			mAscent = (int) paint.ascent();
			if (specMode == MeasureSpec.EXACTLY) {
				// We were told how big to be
				result = specSize;
			} else {
				// Measure the text (beware: ascent is a negative number)
				result = (int) (-mAscent + paint.descent()) + getPaddingTop() + getPaddingBottom();
				if (specMode == MeasureSpec.AT_MOST) {
					// Respect AT_MOST value if that was what is called for by measureSpec
					result = Math.min(result, specSize);
				}
			}
			return result;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawText("Hello World", getPaddingLeft(), getPaddingTop() - mAscent, paint);
			// invalidate();
		}

	}
}