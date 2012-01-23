package com.binomed.server.rest;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.binomed.client.rest.IRestletService;
import com.binomed.client.rest.dto.RestletObjectA;
import com.binomed.client.rest.dto.RestletObjectB;

public class RestResource extends ServerResource implements IRestletService {

	@Override
	@Get
	public RestletObjectA getMessage() throws Exception {
		RestletObjectB objB = new RestletObjectB();
		objB.setName("ObjectB");

		RestletObjectA result = new RestletObjectA();
		result.setName("ObjectA");
		result.setListObjectB(new ArrayList<RestletObjectB>());
		result.getListObjectB().add(objB);
		result.setObjectB(objB);

		return result;
	}

}
