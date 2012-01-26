package com.binomed.client.rest;

import org.restlet.resource.Get;

import com.binomed.client.rest.dto.RestletObjectA;

public interface IRestletService {

	@Get
	RestletObjectA getMessage() throws Exception;

}
