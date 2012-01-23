package com.binomed.android.rpc.rest;

import org.restlet.engine.Engine;
import org.restlet.ext.httpclient.HttpClientHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.resource.ClientResource;

import com.binomed.android.rpc.TestRpcAndroidActivity;
import com.binomed.client.rest.IRestletService;
import com.binomed.client.rest.dto.RestletObjectA;

public class RestletAccesClass {

	public static RestletObjectA callService() throws Exception {

		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		Engine.getInstance().getRegisteredClients().clear();
		Engine.getInstance().getRegisteredClients().add(new HttpClientHelper(null));

		ClientResource clientResource = new ClientResource(TestRpcAndroidActivity.LOCALHOST + "/rest/");

		IRestletService service = clientResource.wrap(IRestletService.class);

		RestletObjectA result = service.getMessage();

		return result;
	}

}
