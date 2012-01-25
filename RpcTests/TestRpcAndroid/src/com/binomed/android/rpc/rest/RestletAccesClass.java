package com.binomed.android.rpc.rest;

import java.util.HashMap;

import org.restlet.engine.Engine;
import org.restlet.ext.httpclient.HttpClientHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.resource.ClientResource;

import com.binomed.android.rpc.TestRpcAndroidActivity;
import com.binomed.client.rest.IRestletService;
import com.binomed.client.rest.dto.RestletObjectA;
import com.binomed.client.rest.dto.RestletObjectB;

public class RestletAccesClass {

	private static ClientResource resource;
	private static ClientResource resourceWithParam;
	private static IRestletService service;
	private static IRestletService serviceWithParam;

	private static synchronized void init() {
		if (resource == null) {
			Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
			Engine.getInstance().getRegisteredClients().clear();
			Engine.getInstance().getRegisteredClients().add(new HttpClientHelper(null));
			resource = new ClientResource(TestRpcAndroidActivity.LOCALHOST + "/rest/test");
			resourceWithParam = new ClientResource(TestRpcAndroidActivity.LOCALHOST + "/rest/testParam");
			service = resource.wrap(IRestletService.class);
			serviceWithParam = resourceWithParam.wrap(IRestletService.class);
		}
	}

	public static RestletObjectA callService() throws Exception {

		init();

		RestletObjectA result = service.getMessage();

		return result;
	}

	public static RestletObjectA callServiceWithParam() throws Exception {
		init();

		RestletObjectB objB = new RestletObjectB();
		objB.setName("Name with Rest");
		objB.setMap(new HashMap<String, String>());
		objB.getMap().put("key", "value");

		RestletObjectA result = serviceWithParam.getMessageWithParameter(objB);

		return result;
	}

}
