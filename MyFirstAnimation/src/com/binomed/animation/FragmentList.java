package com.binomed.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class FragmentList extends Fragment {

	private ListView list;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.fragment_list, container, false);
		list = (ListView) mainView.findViewById(R.id.listView);
		return mainView;
	}

	public void changeAdapter(BaseAdapter adapter) {
		list.setAdapter(adapter);
	}

	public void setOnFocusListener(View.OnFocusChangeListener focusListener) {
		list.setOnFocusChangeListener(focusListener);
	}

	public void requestFocus() {
		list.requestFocus();
	}

}
