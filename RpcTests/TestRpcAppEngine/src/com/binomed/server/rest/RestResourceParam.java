package com.binomed.server.rest;

import java.util.ArrayList;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.binomed.client.rest.dto.RestletObjectA;
import com.binomed.client.rest.dto.RestletObjectB;

/**
 * Implementation of rest service with param
 * 
 * @author jefBinomed
 * 
 */
public class RestResourceParam extends ServerResource {

	/**
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Post
	public RestletObjectA getMessageWithParameter(RestletObjectB parameter) throws Exception {
		RestletObjectA result = new RestletObjectA();
		result.setName("WithParameter");
		result.setObjectB(parameter);

		if ((parameter != null) && (parameter.getNum() > 0)) {
			result.setListObjectB(new ArrayList<RestletObjectB>());
			for (int i = 0; i < parameter.getNum(); i++) {
				result.getListObjectB().add(parameter);
			}
		}

		return result;
	}

}
