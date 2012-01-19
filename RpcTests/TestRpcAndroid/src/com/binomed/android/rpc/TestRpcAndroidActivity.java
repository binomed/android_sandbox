package com.binomed.android.rpc;

import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.binomed.android.rpc.javajsonrpc.JavaJsonRpcServiceProxy;
import com.binomed.android.rpc.requestFactory.Util;
import com.binomed.client.requestfactory.MyRequestFactory;
import com.binomed.client.requestfactory.MyRequestFactory.HelloWorldRequest;
import com.binomed.client.requestfactory.shared.RequestFactoryObjectAProxy;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;
import com.binomed.rpc.R;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import cz.eman.jsonrpc.client.HttpJsonClient;

public class TestRpcAndroidActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */

	public static final String LOCALHOST = "http://10.0.2.2:8888"; //$NON-NLS-1$
	private static final String TAG = "TestAndroidActivity";

	private EditText requestFactory, jsonRpc, restlet;
	private Button btnRequestFactory, btnJsonRpc, btnRestlet;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {
		super.onResume();

		setContentView(R.layout.main);
		mContext = this;

		requestFactory = (EditText) findViewById(R.id.requestFactoryResult);
		btnRequestFactory = (Button) findViewById(R.id.btnRequestFactory);
		jsonRpc = (EditText) findViewById(R.id.jsonRPCResult);
		btnJsonRpc = (Button) findViewById(R.id.btnJsonRPC);
		restlet = (EditText) findViewById(R.id.restletResult);
		btnRestlet = (Button) findViewById(R.id.btnRestlet);

		btnRequestFactory.setOnClickListener(this);
		btnJsonRpc.setOnClickListener(this);
		btnRestlet.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnJsonRPC: {
			btnJsonRpc.setEnabled(false);
			jsonRpc.setText("contacting server");

			// Use an AsyncTask to avoid blocking the UI thread
			new AsyncTask<Void, Void, JavaJsonRpcObjectA>() {

				@Override
				protected JavaJsonRpcObjectA doInBackground(Void... arg0) {
					try {
						JavaJsonRpcServiceProxy proxy = new JavaJsonRpcServiceProxy(new HttpJsonClient(new URL(LOCALHOST + "/jsonrpc/javajsonrpc")));
						return proxy.getMessage();
					} catch (Exception e) {
						Log.e(TAG, e.getMessage(), e);
						return null;
					}
				}

				@Override
				protected void onPostExecute(JavaJsonRpcObjectA result) {
					if (result != null) {
						jsonRpc.setText(result.getName() + ", B : " + result.getObjectB().getName());
					} else {
						jsonRpc.setText("Failure during getting result");

					}
					btnJsonRpc.setEnabled(true);
				}
			}.execute();
			break;
		}
		case R.id.btnRequestFactory: {
			btnRequestFactory.setEnabled(false);
			requestFactory.setText("contacting server");

			// Use an AsyncTask to avoid blocking the UI thread
			new AsyncTask<Void, Void, RequestFactoryObjectAProxy>() {
				private RequestFactoryObjectAProxy message;

				@Override
				protected RequestFactoryObjectAProxy doInBackground(Void... arg0) {
					Thread.currentThread().setContextClassLoader(mContext.getClassLoader());
					MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
					final HelloWorldRequest request = requestFactory.helloWorldRequest();
					Log.i(TAG, "Sending request to server");
					request.getMessage().fire(new Receiver<RequestFactoryObjectAProxy>() {
						@Override
						public void onFailure(ServerFailure error) {
							Log.e(TAG, "Failure with request factory request : " + error.getMessage());
							message = null;
						}

						@Override
						public void onSuccess(RequestFactoryObjectAProxy result) {
							message = result;
						}
					});
					return message;
				}

				@Override
				protected void onPostExecute(RequestFactoryObjectAProxy result) {
					if (result != null) {
						requestFactory.setText(result.getName() + ", B : " + result.getObjectB().getName());
					} else {
						requestFactory.setText("Failure during getting result");

					}
					btnRequestFactory.setEnabled(true);
				}
			}.execute();

			break;
		}
		case R.id.btnRestlet: {

			break;
		}
		default:
			break;
		}

	}
}