package com.binomed.test.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CustomAdapter extends BaseAdapter {

	private final Context context;

	private static final String HELLO = "Hello ";

	public CustomAdapter(Context context) {
		super();
		this.context = context;
	}

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
			view = new CustomView(context);
		}
		view.changeText(HELLO + arg0, arg0);
		return view;
	}
}