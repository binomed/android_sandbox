package com.binomed.mapfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockFragment;

public class WebFragment extends SherlockFragment {// Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.fragment_web, container, false);

		WebView webView = (WebView) mainView.findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://gmaps-samples.googlecode.com/svn/trunk/articles-android-webmap/simple-android-map.html");

		return mainView;
	}

}
