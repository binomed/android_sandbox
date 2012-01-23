package com.binomed.android.rpc.rest;

import org.restlet.resource.ClientResource;

import com.binomed.android.rpc.TestRpcAndroidActivity;
import com.binomed.client.rest.IRestletService;
import com.binomed.client.rest.dto.RestletObjectA;

public class RestletAccesClass {

	public static RestletObjectA callService() throws Exception {
		ClientResource clientResource = new ClientResource(TestRpcAndroidActivity.LOCALHOST + "/rest/");

		IRestletService service = clientResource.wrap(IRestletService.class);

		RestletObjectA result = service.getMessage();

		return result;
	}

}
