package com.binomed.client.rpc.javajsonrpc;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.binomed.client.IObjectA;
import com.binomed.client.IObjectB;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectB;

/**
 * Java-Json-Rpc service
 * 
 * @author jefBinomed
 * 
 */
public interface IJavaJsonRpcService {

	/**
	 * Returns an instance of {@link IObjectA}
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	JavaJsonRpcObjectA getMessage() throws JsonParseException, JsonMappingException, IOException;

	/**
	 * Returns an isntance of {@link IObjectA} with #parameter {@link IObjectB}
	 * 
	 * @param parameter
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	JavaJsonRpcObjectA getMessageWithParameter(JavaJsonRpcObjectB parameter) throws JsonParseException, JsonMappingException, IOException;

}
