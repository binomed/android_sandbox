package com.binomed.test.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class CustomAdapterExpand extends BaseExpandableListAdapter {

	private final Context context;

	private static final String HELLO = "Hello Expand ";
	private static final String HELLO_CHILD = "Hello Child ";

	public CustomAdapterExpand(Context context) {
		super();
		this.context = context;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return arg0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		CustomView view = null;
		if (convertView != null) {
			view = (CustomView) convertView;
		} else {
			view = new CustomView(context);
		}
		view.changeText(HELLO + (groupPosition + childPosition), groupPosition);
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 20;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		return 100;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		CustomView view = null;
		if (convertView != null) {
			view = (CustomView) convertView;
		} else {
			view = new CustomView(context);
		}
		view.changeText(HELLO + groupPosition, groupPosition);
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}