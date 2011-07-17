package com.binomed.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentEditText extends Fragment {

	private EditText text;
	private View view;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.fragment_edit_text, container, false);
		text = (EditText) mainView.findViewById(R.id.textView);
		view = mainView.findViewById(R.id.textLayout);
		return mainView;
	}

	// public void setOnFocusListener(View.OnFocusChangeListener focusListener) {
	// // text.setOnFocusChangeListener(focusListener);
	// view.setOnFocusChangeListener(focusListener);
	// }

	// public void setOnClickListener(View.OnClickListener focusListener) {
	// text.setOnClickListener(focusListener);
	// view.setOnClickListener(focusListener);
	// }

}
