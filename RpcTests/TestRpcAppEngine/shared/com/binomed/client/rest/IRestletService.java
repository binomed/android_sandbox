package com.binomed.client.rest;

import org.restlet.resource.Get;

import com.binomed.client.rest.dto.RestletObjectA;
import com.binomed.client.rest.dto.RestletObjectB;

public interface IRestletService {

	@Get
	RestletObjectA getMessage() throws Exception;

	@Get(value = "parameter")
	RestletObjectA getMessageWithParameter(RestletObjectB parameter) throws Exception;

}
