package com.binomed.test.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/**
 * @author Jef BaseExpandableListAdapter for the ExpandList
 * 
 */
public class CustomAdapterExpand extends BaseExpandableListAdapter {

	private final Context context;

	private static final String HELLO = "Hello Expand ";
	private static final String HELLO_CHILD = "Hello Child ";

	private static final int limit = 100;
	private static final int limitChild = 20;
	private static final String[] STRING_ARRAY = new String[limit];
	private static final String[] STRING_CHILD_ARRAY = new String[limit + limitChild];

	static {

		for (int i = 0; i < limit; i++) {
			STRING_ARRAY[i] = HELLO + i;
		}
		for (int i = 0; i < limit + limitChild; i++) {
			STRING_CHILD_ARRAY[i] = HELLO_CHILD + i;
		}

	}

	/**
	 * @param context
	 */
	public CustomAdapterExpand(Context context) {
		super();
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int arg0, int arg1) {
		return arg0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		CustomView view = null;
		if (convertView != null) {
			view = (CustomView) convertView;
		} else {
			view = new CustomView(context, 20);
		}
		view.changeText(STRING_CHILD_ARRAY[groupPosition + childPosition], groupPosition);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return limitChild;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return groupPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		return limit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		CustomView view = null;
		if (convertView != null) {
			view = (CustomView) convertView;
		} else {
			view = new CustomView(context, 0);
		}
		view.changeText(STRING_ARRAY[groupPosition], groupPosition);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}