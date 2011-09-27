package com.binomed.test.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
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
			view.changeText("Hello : " + arg0, arg0);
			return view;
		}
	}

	class CustomView extends View {

		private Paint mainPaint = new Paint();
		private Paint paint = new Paint();
		private Paint paintBlue = new Paint();
		private Paint paintGray = new Paint();

		private String text;
		private String textLong = "un texte qui est long et qui merrite d'etre couper dcar c'est long";
		private int index;
		private int mAscent, mAscentMain;
		private int specSizeWidth;

		public CustomView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		public CustomView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public CustomView(Context context) {
			super(context);
			mainPaint.setColor(Color.WHITE);
			mainPaint.setStyle(Style.FILL);
			mainPaint.setTextSize(20);
			mainPaint.setAntiAlias(true);
			paint.setColor(Color.WHITE);
			paint.setTextSize(15);
			paint.setAntiAlias(true);
			paintBlue.setColor(Color.BLUE);
			paintBlue.setTextSize(15);
			paintBlue.setAntiAlias(true);
			paintGray.setColor(Color.GRAY);
			paintGray.setTextSize(15);
			paintGray.setAntiAlias(true);
		}

		public void changeText(String text, int index) {
			this.text = text;
			this.index = index;
			requestLayout();
			invalidate();

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
			this.specSizeWidth = specSize;

			if (specMode == MeasureSpec.EXACTLY) {
				// We were told how big to be
				result = specSize;
			} else {
				// Measure the text
				result = 0;
				switch (index % 10) {
				case 9:
					result += (int) paint.measureText(text);

				case 8:
					result += (int) paint.measureText(text);

				case 7:
					result += (int) paint.measureText(text);

				case 6:
					result += (int) paint.measureText(text);

				case 5:
					result += (int) paint.measureText(text);

				case 4:
					result += (int) paint.measureText(text);

				case 3:
					result += (int) paint.measureText(text);

				case 2:
					result += (int) paint.measureText(text);

				case 1:
					result += (int) paint.measureText(text);

				case 0:
					result += (int) paint.measureText(text);

				default:
					result += getPaddingLeft() + getPaddingRight();
					break;
				}
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
			mAscentMain = (int) mainPaint.ascent();
			if (specMode == MeasureSpec.EXACTLY) {
				// We were told how big to be
				result = specSize;
			} else {

				int width = getPaddingLeft() + getPaddingRight();
				int nbLines = 1, nbLinesMain = 1;
				int i = index % 10;
				while (i >= 0) {
					width += (int) paint.measureText(text) + 5;
					if (width > specSizeWidth) {
						nbLines++;
						width = getPaddingLeft() + getPaddingRight();
					}
					i--;

				}
				String[] split = textLong.split(" ");
				width = getPaddingLeft() + getPaddingRight();
				for (String splitText : split) {
					width += (int) mainPaint.measureText(splitText + " ");
					if (width > specSizeWidth) {
						nbLinesMain++;
						width = getPaddingLeft() + getPaddingRight();
					}

				}

				result = (nbLines * (int) (-mAscent + paint.descent())) + (nbLinesMain * (int) (-mAscentMain + mainPaint.descent())) + 5;

				// Measure the text (beware: ascent is a negative number)
				// result = (int) (-mAscent + paint.descent()) + getPaddingTop() + getPaddingBottom();
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

			int width = 0;
			int i = index % 10;
			int posY = getPaddingTop() - mAscentMain;
			int posX = getPaddingLeft();
			String[] split = textLong.split(" ");
			width = getPaddingLeft() + getPaddingRight();
			for (String splitText : split) {
				width += (int) mainPaint.measureText(splitText + " ");
				if (width > (specSizeWidth - getPaddingRight())) {
					width = 0;
					;
					posX = getPaddingLeft();
					posY += (int) (-mAscentMain + mainPaint.descent());
				}
				canvas.drawText(splitText + " ", posX, posY, mainPaint);
				posX += mainPaint.measureText(splitText + " ");

			}
			posY += 5 - mAscent;
			width = getPaddingLeft() + getPaddingRight();
			posX = getPaddingLeft();
			Paint tmpPaint = null;
			while (i >= 0) {

				width += (int) paint.measureText(text);
				if (width > (specSizeWidth - getPaddingRight())) {
					width = 0;
					posX = getPaddingLeft();
					posY += (int) (-mAscent + paint.descent());
				}
				switch (i % 3) {
				case 0:
					tmpPaint = paint;

					break;
				case 1:
					tmpPaint = paintBlue;

					break;
				case 2:
					tmpPaint = paintGray;

					break;

				default:
					break;
				}
				canvas.drawText(text, posX, posY, tmpPaint);
				posX += paint.measureText(text) + 5;
				i--;
			}
			// invalidate();
		}

	}
}