package com.binomed.test.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class ExpandableFragment extends Fragment implements IFragmentCacheColor {

	private boolean cacheColorHint;
	private boolean transparentCacheColorHint;
	private ExpandableListView list;
	private CustomAdapterExpand adapter;

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.fragment_expand, container, false);
		list = (ExpandableListView) mainView.findViewById(R.id.listExpand);
		list.setGroupIndicator(null);
		adapter = new CustomAdapterExpand(getActivity().getApplicationContext());
		redraw();
		return mainView;

	}

	private void redraw() {
		if (list != null) {
			if (cacheColorHint) {
				if (transparentCacheColorHint) {
					list.setCacheColorHint(android.R.color.transparent);
				} else {
					list.setCacheColorHint(android.R.color.darker_gray);
				}
			} else {
				list.setCacheColorHint(android.R.color.background_dark);
			}

			list.setAdapter(adapter);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.test.custom.IFragmentCacheColor#setCacheColorHint(boolean)
	 */
	@Override
	public void setCacheColorHint(boolean useCacheColorHint) {
		this.cacheColorHint = useCacheColorHint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.test.custom.IFragmentCacheColor#setTransparentCacheColorHint(boolean)
	 */
	@Override
	public void setTransparentCacheColorHint(boolean useTransparentCacheColorHint) {
		this.transparentCacheColorHint = useTransparentCacheColorHint;
		redraw();
	}

}
