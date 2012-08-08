/*
 * Copyright (C) 2012 Binomed (http://blog.binomed.fr)
 *
 * Licensed under the Eclipse Public License - v 1.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE PUBLIC 
 * LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR DISTRIBUTION OF THE PROGRAM 
 * CONSTITUTES RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.
 */
package com.binomed.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

/**
 * @author Jef
 * 
 * Complex custom view that only show text
 *
 */
public class CustomView extends View {

	private static final String SPACE = " ";

	private final Paint mainPaint = new Paint();
	private final Paint paint = new Paint();
	private final Paint paintBlue = new Paint();
	private final Paint paintGray = new Paint();

	private String text;
	private final String textLong = "A text which is very long and have to be cut because it's too long !";
	private final String[] splitTextLong = textLong.split(SPACE);
	private int index;
	private int mAscent, mAscentMain;
	private final int measureSpace;
	private int specSizeWidth;
	private final int paddingLeft;


	/**
	 * @param context
	 * @param paddingLeft
	 */
	public CustomView(Context context, int paddingLeft) {
		super(context);
		this.paddingLeft = paddingLeft;
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

		measureSpace = (int) mainPaint.measureText(SPACE);
	}

	/**
	 * @param text
	 * @param index
	 */
	public void changeText(String text, int index) {
		this.text = text;
		this.index = index;
		// we force the view to change
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
			width = getPaddingLeft() + getPaddingRight();
			String splitText = null;
			for (int cpt = 0; cpt < splitTextLong.length; cpt++) {
				splitText = splitTextLong[cpt];
				width += (int) mainPaint.measureText(splitText) + measureSpace;
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

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = 0;
		int i = index % 10;
		int posY = getPaddingTop() - mAscentMain;
		int posX = getPaddingLeft();
		width = getPaddingLeft() + getPaddingRight();
		String splitText = null;
		int measureTmp = -1;
		for (int cpt = 0; cpt < splitTextLong.length; cpt++) {
			splitText = splitTextLong[cpt];
			measureTmp = (int) mainPaint.measureText(splitText);
			width += measureTmp + measureSpace;
			if (width > (specSizeWidth - getPaddingRight())) {
				width = 0;
				;
				posX = getPaddingLeft();
				posY += (int) (-mAscentMain + mainPaint.descent());
			}
			canvas.drawText(splitText, posX, posY, mainPaint);
			posX += measureTmp;
			canvas.drawText(SPACE, posX, posY, mainPaint);
			posX += measureSpace;

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
	}

	/* (non-Javadoc)
	 * @see android.view.View#getPaddingLeft()
	 */
	@Override
	public int getPaddingLeft() {
		return paddingLeft;
	}
	
	

}