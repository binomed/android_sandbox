package com.binomed.rpc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.binomed.requestfactory.client.MyRequestFactory;
import com.binomed.requestfactory.client.MyRequestFactory.HelloWorldRequest;
import com.binomed.requestfactory.shared.RequestFactoryObjectAProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class TestRpcAndroidActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */

	public static final String LOCALHOST = "10.0.2.2:8080"; //$NON-NLS-1$
	private static final String TAG = "TestAndroidActivity";

	private EditText requestFactory, jsonRpc, restlet;
	private Button btnRequestFactory, btnJsonRpc, btnRestlet;
	private Context mContext;

	/**
	 * A {@link BroadcastReceiver} to receive the response from a register or unregister request, and to update the UI.
	 */
	private final BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String accountName = intent.getStringExtra(DeviceRegistrar.ACCOUNT_NAME_EXTRA);
			int status = intent.getIntExtra(DeviceRegistrar.STATUS_EXTRA, DeviceRegistrar.ERROR_STATUS);
			String message = null;
			String connectionStatus = Util.DISCONNECTED;
			if (status == DeviceRegistrar.REGISTERED_STATUS) {
				message = "registration_succeeded";
				connectionStatus = Util.CONNECTED;
			} else if (status == DeviceRegistrar.UNREGISTERED_STATUS) {
				message = "unregistration_succeeded";
			} else {
				message = "registration_error";
			}

			// Set connection status
			SharedPreferences prefs = Util.getSharedPreferences(mContext);
			prefs.edit().putString(Util.CONNECTION_STATUS, connectionStatus).commit();

			// Display a notification
			Util.generateNotification(mContext, String.format(message, accountName));
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Register a receiver to provide register/unregister notifications
		registerReceiver(mUpdateUIReceiver, new IntentFilter(Util.UPDATE_UI_INTENT));

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		SharedPreferences prefs = Util.getSharedPreferences(mContext);
		String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
		if (Util.DISCONNECTED.equals(connectionStatus)) {
			startActivity(new Intent(this, AccountsActivity.class));
		}
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

		// JSONRPCHttpClient client = (JSONRPCHttpClient) JSONRPCHttpClient.create(LOCALHOST);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnJsonRPC: {

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