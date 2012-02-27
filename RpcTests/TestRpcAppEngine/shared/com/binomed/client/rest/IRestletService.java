package com.binomed.client.rest;

import org.restlet.resource.Get;

import com.binomed.client.IObjectA;
import com.binomed.client.rest.dto.RestletObjectA;

/**
 * Interface for resource REST without param
 * 
 * @author jefBinomed
 * 
 */
public interface IRestletService {

	/**
	 * @return an instance of {@link IObjectA}
	 * @throws Exception
	 */
	@Get
	RestletObjectA getMessage() throws Exception;

}
