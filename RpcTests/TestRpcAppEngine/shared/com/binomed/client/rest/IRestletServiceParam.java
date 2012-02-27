package com.binomed.client.rest;

import org.restlet.resource.Post;

import com.binomed.client.IObjectA;
import com.binomed.client.rest.dto.RestletObjectA;
import com.binomed.client.rest.dto.RestletObjectB;

/**
 * Interface for resource REST with param
 * 
 * @author jefBinomed
 * 
 */
public interface IRestletServiceParam {

	/**
	 * return an instance of {@link IObjectA}
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Post
	RestletObjectA getMessageWithParameter(RestletObjectB parameter) throws Exception;

}
