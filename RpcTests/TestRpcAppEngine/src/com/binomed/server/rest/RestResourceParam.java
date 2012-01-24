package com.binomed.server.rest;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.binomed.client.rest.dto.RestletObjectA;
import com.binomed.client.rest.dto.RestletObjectB;

public class RestResourceParam extends ServerResource {

	@Get
	public RestletObjectA getMessageWithParameter(RestletObjectB parameter) throws Exception {
		RestletObjectA result = new RestletObjectA();
		result.setName("WithParameter");
		result.setObjectB(parameter);

		return result;
	}

}
