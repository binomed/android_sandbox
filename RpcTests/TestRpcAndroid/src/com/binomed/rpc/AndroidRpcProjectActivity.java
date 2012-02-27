package com.binomed.rpc;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.binomed.client.IObjectA;
import com.binomed.client.IObjectB;
import com.binomed.client.IObjectBMap;
import com.binomed.client.requestfactory.MyRequestFactory;
import com.binomed.client.requestfactory.MyRequestFactory.HelloWorldRequest;
import com.binomed.client.requestfactory.shared.RequestFactoryObjectAProxy;
import com.binomed.client.requestfactory.shared.RequestFactoryObjectBProxy;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectB;
import com.binomed.rpc.javajsonrpc.JavaJsonRpcServiceProxy;
import com.binomed.rpc.requestFactory.Util;
import com.binomed.rpc.rest.RestletAccesClass;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import cz.eman.jsonrpc.client.HttpJsonClient;

/**
 * Main Activity Class
 * 
 * @author jefBinomed
 * 
 */
public class AndroidRpcProjectActivity extends TabActivity implements OnClickListener {

	/**
	 * Url for localhost in android
	 */
	public static final String LOCALHOST = "http://10.0.2.2:8888"; //$NON-NLS-1$
	private static final String TAG = "AndroidRpcProjectActivity";

	/**
	 * Text zone for recepting server responses
	 */
	private EditText requestFactory, jsonRpc, restlet;
	/**
	 * Text zone for specifying the number of objects expected
	 */
	private EditText nbParamRequestFactory, nbParamJsonRpc, nbParamRestlet;
	/**
	 * Buttons for launching the services
	 */
	private Button btnRequestFactory, btnJsonRpc, btnRestlet, btnRequestFactoryParams, btnJsonRpcParams, btnRestletParams;
	private Context mContext;

	/*
	 * Types for abastract class AsyncTask
	 */
	private static final int JSON_RPC = 1;
	private static final int REQUEST_FACTORY = 2;
	private static final int REST = 3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ActivityGroup#onResume()
	 */
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
		tabHost.addTab(jsonRpcTab); // Adding jsonRpc tab
		tabHost.addTab(requestFactoryTab); // Adding request factory tab
		tabHost.addTab(restTab); // Adding rest tab

		/*
		 * Map the xml with fields
		 */
		requestFactory = (EditText) findViewById(R.id.requestFactory);
		nbParamRequestFactory = (EditText) findViewById(R.id.nbParamRequestFactory);
		btnRequestFactory = (Button) findViewById(R.id.btnRequestFactory);
		btnRequestFactoryParams = (Button) findViewById(R.id.btnRequestFactoryParams);
		jsonRpc = (EditText) findViewById(R.id.jsonRpc);
		nbParamJsonRpc = (EditText) findViewById(R.id.nbParamJsonRpc);
		btnJsonRpc = (Button) findViewById(R.id.btnJsonRpc);
		btnJsonRpcParams = (Button) findViewById(R.id.btnJsonParams);
		restlet = (EditText) findViewById(R.id.rest);
		nbParamRestlet = (EditText) findViewById(R.id.nbParamRest);
		btnRestlet = (Button) findViewById(R.id.btnRest);
		btnRestletParams = (Button) findViewById(R.id.btnRestParams);

		// Add the listeners
		btnRequestFactory.setOnClickListener(this);
		btnRequestFactoryParams.setOnClickListener(this);
		btnJsonRpc.setOnClickListener(this);
		btnJsonRpcParams.setOnClickListener(this);
		btnRestlet.setOnClickListener(this);
		btnRestletParams.setOnClickListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// We just launch an instance of TaskTest (an async task)
		switch (v.getId()) {
		case R.id.btnJsonRpc: {
			new TaskTest(jsonRpc, btnJsonRpc, JSON_RPC, -1).execute();
			break;
		}
		case R.id.btnJsonParams: {
			new TaskTest(jsonRpc, btnJsonRpcParams, JSON_RPC, Integer.valueOf(nbParamJsonRpc.getText().toString())).execute();
			break;
		}
		case R.id.btnRequestFactory: {
			new TaskTest(requestFactory, btnRequestFactory, REQUEST_FACTORY, -1).execute();
			break;
		}
		case R.id.btnRequestFactoryParams: {
			new TaskTest(requestFactory, btnRequestFactoryParams, REQUEST_FACTORY, Integer.valueOf(nbParamRequestFactory.getText().toString())).execute();
			break;
		}
		case R.id.btnRest: {
			new TaskTest(restlet, btnRestlet, REST, -1).execute();
			break;
		}
		case R.id.btnRestParams: {
			new TaskTest(restlet, btnRestletParams, REST, Integer.valueOf(nbParamRestlet.getText().toString())).execute();
			break;
		}
		default:
			break;
		}

	}

	/**
	 * The asynTask use for the call of server
	 * 
	 * @author jefBinomed
	 * 
	 */
	class TaskTest extends AsyncTask<Void, Void, IObjectA<? extends IObjectB>> {

		/**
		 * The output text
		 */
		private EditText text;
		/**
		 * The boutton wich launch the task
		 */
		private Button btn;
		/**
		 * The type (Rest, json, request factory)
		 */
		private int type;
		/**
		 * The number of expected beans in result
		 */
		private int nbParams;
		/**
		 * Use only for requestFactory
		 */
		private IObjectA<? extends IObjectB> message;
		/**
		 * The time of call of service
		 */
		private long time;

		public TaskTest(EditText text, Button btn, int type, int nbParams) {
			this.text = text;
			this.btn = btn;
			this.type = type;
			this.nbParams = nbParams;
			btn.setEnabled(false);
			text.setText("contacting server");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected IObjectA<? extends IObjectB> doInBackground(Void... params) {
			time = System.currentTimeMillis();
			try {
				IObjectA<? extends IObjectB> result = null;
				switch (type) {
				case JSON_RPC: {
					// We instanciate the proxy JSON and call the correct service
					JavaJsonRpcServiceProxy proxy = new JavaJsonRpcServiceProxy(new HttpJsonClient(new URL(LOCALHOST + "/jsonrpc/javajsonrpc")));
					if (nbParams == -1) {
						result = proxy.getMessage();
					} else {
						JavaJsonRpcObjectB paramB = new JavaJsonRpcObjectB();
						paramB.setName("Name From Json RPC");
						paramB.setMap(new HashMap<String, String>());
						paramB.getMap().put("key", "value");
						paramB.setNum(nbParams);
						result = proxy.getMessageWithParameter(paramB);

					}
					break;
				}
				case REQUEST_FACTORY: {
					// We get the proxy and call the correct method with a CallBack mecanism

					// The classes used were taken from generation of code done by the plugin Android AppEngine
					Thread.currentThread().setContextClassLoader(mContext.getClassLoader());
					MyRequestFactory requestFactory = Util.getRequestFactory(mContext, MyRequestFactory.class);
					final HelloWorldRequest request = requestFactory.helloWorldRequest();
					if (nbParams == -1) {
						request.getMessage().fire(new Receiver<RequestFactoryObjectAProxy>() {
							@Override
							public void onFailure(ServerFailure error) {
								Log.e(TAG, "Failure with request factory request : " + error.getMessage());
								message = null;
							}

							@Override
							public void onSuccess(RequestFactoryObjectAProxy result) {
								JavaJsonRpcObjectA objA = new JavaJsonRpcObjectA();
								objA.setName(result.getName());
								if (result.getObjectB() != null) {
									JavaJsonRpcObjectB objB = new JavaJsonRpcObjectB();
									objB.setName(result.getObjectB().getName());
								}
								if ((result.getListObjectB() != null) && (result.getListObjectB().size() > 0)) {
									JavaJsonRpcObjectB objB = null;
									objA.setListObjectB(new ArrayList<JavaJsonRpcObjectB>());
									for (RequestFactoryObjectBProxy objBProxy : result.getListObjectB()) {
										objB = new JavaJsonRpcObjectB();
										objB.setName(objBProxy.getName());
										objA.getListObjectB().add(objB);
									}

								}
								message = objA;
							}
						});
					} else {
						RequestFactoryObjectBProxy parameterB = request.create(RequestFactoryObjectBProxy.class);
						parameterB.setName("Name from request factory");
						parameterB.setNum(nbParams);
						request.getMessageWithParameter().using(parameterB).fire(new Receiver<RequestFactoryObjectAProxy>() {
							@Override
							public void onFailure(ServerFailure error) {
								Log.e(TAG, "Failure with request factory request : " + error.getMessage());
								message = null;
							}

							@Override
							public void onSuccess(RequestFactoryObjectAProxy result) {
								// We have to convert the beans into an implementation of interface in order to manage automatic show in the text result zone.
								JavaJsonRpcObjectA objA = new JavaJsonRpcObjectA();
								objA.setName(result.getName());
								if (result.getObjectB() != null) {
									JavaJsonRpcObjectB objB = new JavaJsonRpcObjectB();
									objB.setName(result.getObjectB().getName());
								}
								if ((result.getListObjectB() != null) && (result.getListObjectB().size() > 0)) {
									JavaJsonRpcObjectB objB = null;
									objA.setListObjectB(new ArrayList<JavaJsonRpcObjectB>());
									for (RequestFactoryObjectBProxy objBProxy : result.getListObjectB()) {
										objB = new JavaJsonRpcObjectB();
										objB.setName(objBProxy.getName());
										objA.getListObjectB().add(objB);
									}

								}

								message = objA;
							}
						});
					}
					result = message;
					break;
				}
				case REST: {
					// We just call the correct service
					if (nbParams == -1) {
						result = RestletAccesClass.callService();
					} else {
						result = RestletAccesClass.callServiceWithParam(nbParams);

					}
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
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
			// We finally show the results
			if (result != null) {
				StringBuilder str = new StringBuilder();
				str.append("Type : ").append(typeName).append("\n");
				str.append("Time : ").append(String.valueOf(System.currentTimeMillis() - time)).append("ms \n");
				str.append("Object A : ").append(result.getName()).append("\n");
				IObjectB objB = result.getObjectB();
				if (objB != null) {
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