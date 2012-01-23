package com.binomed.android.rpc;

import java.net.URL;
import java.util.Map.Entry;

import android.app.TabActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.binomed.android.rpc.javajsonrpc.JavaJsonRpcServiceProxy;
import com.binomed.android.rpc.requestFactory.Util;
import com.binomed.android.rpc.rest.RestletAccesClass;
import com.binomed.client.IObjectA;
import com.binomed.client.IObjectB;
import com.binomed.client.IObjectBMap;
import com.binomed.client.requestfactory.MyRequestFactory;
import com.binomed.client.requestfactory.MyRequestFactory.HelloWorldRequest;
import com.binomed.client.requestfactory.shared.RequestFactoryObjectAProxy;
import com.binomed.rpc.R;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import cz.eman.jsonrpc.client.HttpJsonClient;

public class TestRpcAndroidActivity extends TabActivity implements OnClickListener {

	/** Called when the activity is first created. */

	public static final String LOCALHOST = "http://10.0.2.2:8888"; //$NON-NLS-1$
	private static final String TAG = "TestAndroidActivity";

	private EditText requestFactory, jsonRpc, restlet;
	private Button btnRequestFactory, btnJsonRpc, btnRestlet, btnRequestFactoryParams, btnJsonRpcParams, btnRestletParams;
	private Context mContext;

	private static final int JSON_RPC = 1;
	private static final int REQUEST_FACTORY = 2;
	private static final int REST = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {
		super.onResume();

		setContentView(R.layout.tabs);
		mContext = this;

		TabHost tabHost = getTabHost();

		// Tab for Json Rpc
		TabSpec jsonRpcTab = tabHost.newTabSpec("JsonRpc");
		// setting Title and Icon for the Tab
		jsonRpcTab.setIndicator("Json Rpc");
		jsonRpcTab.setContent(R.id.jsonRpcLayout);

		// Tab for Request Factory
		TabSpec requestFactoryTab = tabHost.newTabSpec("RequestFactory");
		requestFactoryTab.setIndicator("Request Factory");
		requestFactoryTab.setContent(R.id.requestFactoryLayout);

		// Tab for Rest
		TabSpec restTab = tabHost.newTabSpec("Rest");
		restTab.setIndicator("Rest");
		restTab.setContent(R.id.restLayout);

		// Adding all TabSpec to TabHost
		tabHost.addTab(jsonRpcTab); // Adding photos tab
		tabHost.addTab(requestFactoryTab); // Adding songs tab
		tabHost.addTab(restTab); // Adding videos tab

		requestFactory = (EditText) findViewById(R.id.requestFactory);
		btnRequestFactory = (Button) findViewById(R.id.btnRequestFactory);
		btnRequestFactoryParams = (Button) findViewById(R.id.btnRequestFactoryParams);
		jsonRpc = (EditText) findViewById(R.id.jsonRpc);
		btnJsonRpc = (Button) findViewById(R.id.btnJsonRpc);
		btnJsonRpcParams = (Button) findViewById(R.id.btnJsonParams);
		restlet = (EditText) findViewById(R.id.rest);
		btnRestlet = (Button) findViewById(R.id.btnRest);
		btnRestletParams = (Button) findViewById(R.id.btnRestParams);

		btnRequestFactory.setOnClickListener(this);
		btnRequestFactoryParams.setOnClickListener(this);
		btnJsonRpc.setOnClickListener(this);
		btnJsonRpcParams.setOnClickListener(this);
		btnRestlet.setOnClickListener(this);
		btnRestletParams.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnJsonRpc: {
			new TaskTest(jsonRpc, btnJsonRpc, JSON_RPC, false).execute();
			break;
		}
		case R.id.btnJsonParams: {
			new TaskTest(jsonRpc, btnJsonRpcParams, JSON_RPC, true).execute();
			break;
		}
		case R.id.btnRequestFactory: {
			new TaskTest(requestFactory, btnRequestFactory, REQUEST_FACTORY, false).execute();
			break;
		}
		case R.id.btnRequestFactoryParams: {
			new TaskTest(requestFactory, btnRequestFactoryParams, REQUEST_FACTORY, true).execute();
			break;
		}
		case R.id.btnRest: {
			new TaskTest(restlet, btnRestlet, REST, false).execute();
			break;
		}
		case R.id.btnRestParams: {
			new TaskTest(restlet, btnRestletParams, REST, true).execute();
			break;
		}
		default:
			break;
		}

	}

	class TaskTest extends AsyncTask<Void, Void, IObjectA<? extends IObjectB>> {

		private EditText text;
		private Button btn;
		private int type;
		private boolean withParams;
		private IObjectA<? extends IObjectB> message;
		private long time;

		public TaskTest(EditText text, Button btn, int type, boolean params) {
			this.text = text;
			this.btn = btn;
			this.type = type;
			this.withParams = params;
			btn.setEnabled(false);
			text.setText("contacting server");
		}

		@Override
		protected IObjectA<? extends IObjectB> doInBackground(Void... params) {
			time = System.currentTimeMillis();
			try {
				IObjectA<? extends IObjectB> result = null;
				switch (type) {
				case JSON_RPC: {
					JavaJsonRpcServiceProxy proxy = new JavaJsonRpcServiceProxy(new HttpJsonClient(new URL(LOCALHOST + "/jsonrpc/javajsonrpc")));
					result = proxy.getMessage();
					break;
				}
				case REQUEST_FACTORY: {
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
					result = message;
					break;
				}
				case REST: {
					result = RestletAccesClass.callService();
					break;
				}
				default:
					break;
				}

				return result;
			} catch (Exception e) {
				Log.e(TAG, "Error during contacting server", e);
				return null;
			}
		}

		@Override
		protected void onPostExecute(IObjectA<? extends IObjectB> result) {
			String typeName = null;
			switch (type) {
			case JSON_RPC:
				typeName = "Json Rpc";
				break;
			case REQUEST_FACTORY:
				typeName = "Request Fatory";
				break;
			case REST:
				typeName = "Restlet";
				break;

			default:
				break;
			}
			if (result != null) {
				StringBuilder str = new StringBuilder();
				str.append("Type : ").append(typeName).append("\n");
				str.append("Time : ").append(String.valueOf(System.currentTimeMillis() - time)).append("ms \n");
				str.append("Object A : ").append(result.getName()).append("\n");
				IObjectB objB = result.getObjectB();
				if (objB instanceof IObjectBMap) {
					str.append("Object B Map : ").append(objB.getName()).append("\n");
					if (((IObjectBMap) objB).getMap() != null) {
						for (Entry<String, String> entry : ((IObjectBMap) objB).getMap().entrySet()) {
							str.append("-> : Key").append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
						}
					}
				} else {
					str.append("Object B: ").append(objB.getName()).append("\n");
				}
				if ((result.getListObjectB() == null) || (result.getListObjectB().size() == 0)) {
					str.append("Nb objects B: ").append(0).append("\n");
				} else {
					str.append("Nb objects B: ").append(result.getListObjectB().size()).append("\n");
					for (IObjectB objBTmp : result.getListObjectB()) {
						str.append("--> ").append(objBTmp.getName()).append("\n");
					}
				}

				text.setText(str.toString());
			} else {
				text.setText("Failure during getting result");

			}
			btn.setEnabled(true);
		}

	}

}