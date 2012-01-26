package com.binomed.client.rest;

import org.restlet.resource.Post;

import com.binomed.client.rest.dto.RestletObjectA;
import com.binomed.client.rest.dto.RestletObjectB;

public interface IRestletServiceParam {

	@Post
	RestletObjectA getMessageWithParameter(RestletObjectB parameter) throws Exception;

}
