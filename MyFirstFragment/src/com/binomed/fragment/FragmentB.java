package com.binomed.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentB extends Fragment {

	public interface OnReadyFragment {

		void ready();
	}

	TextView text;
	int compt = 0;

	private OnReadyFragment listener;

	public void setListener(OnReadyFragment listener) {
		this.listener = listener;
	}

	public void changeText() {
		Log.i("FragmentB", "changeText");
		compt++;
		text.setText("Change : " + compt);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("FragmentB", "onAttach");
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("FragmentB", "onCreate");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("FragmentB", "onCreateView");
		View mainView = inflater.inflate(R.layout.fragment_b, container, false);

		text = (TextView) mainView.findViewById(R.id.text);
		return mainView;
	}

	@Override
	public void onPause() {
		Log.i("FragmentB", "onPause");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i("FragmentB", "onResume");

		if (listener != null) {
			listener.ready();
		}
		// TODO Auto-generated method stub
		super.onResume();
	}

}
