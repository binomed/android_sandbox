package com.binomed.test.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListFragment extends Fragment implements IFragmentCacheColor {

	private boolean cacheColorHint;
	private boolean transparentCacheColorHint;

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.fragment_list, container, false);
		ListView list = (ListView) mainView.findViewById(R.id.list);
		if (cacheColorHint) {
			if (transparentCacheColorHint) {
				list.setCacheColorHint(android.R.color.transparent);
			} else {
				list.setCacheColorHint(android.R.color.darker_gray);
			}
		} else {
			list.setCacheColorHint(android.R.color.background_dark);
		}

		list.setAdapter(new CustomAdapter(getActivity().getApplicationContext()));

		return mainView;

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

	}

}
