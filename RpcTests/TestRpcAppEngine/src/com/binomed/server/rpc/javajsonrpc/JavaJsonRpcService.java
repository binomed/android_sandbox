package com.binomed.server.rpc.javajsonrpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.binomed.client.rpc.javajsonrpc.IJavaJsonRpcService;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectB;

/**
 * Implementation of java-json service
 * 
 * @author jefBinomed
 * 
 */
public class JavaJsonRpcService implements IJavaJsonRpcService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.client.rpc.javajsonrpc.IJavaJsonRpcService#getMessage()
	 */
	@Override
	public JavaJsonRpcObjectA getMessage() throws JsonParseException, JsonMappingException, IOException {
		JavaJsonRpcObjectB objB = new JavaJsonRpcObjectB();
		objB.setName("ObjectB");
		objB.setMap(new HashMap<String, String>());
		objB.getMap().put("key", "value");

		JavaJsonRpcObjectA result = new JavaJsonRpcObjectA();
		result.setName("ObjectA");
		result.setListObjectB(new ArrayList<JavaJsonRpcObjectB>());
		result.getListObjectB().add(objB);
		result.setObjectB(objB);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.client.rpc.javajsonrpc.IJavaJsonRpcService#getMessageWithParameter(com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectB)
	 */
	@Override
	public JavaJsonRpcObjectA getMessageWithParameter(JavaJsonRpcObjectB parameter) throws JsonParseException, JsonMappingException, IOException {
		JavaJsonRpcObjectA result = new JavaJsonRpcObjectA();
		result.setName("WithParameter");
		result.setObjectB(parameter);

		if ((parameter != null) && (parameter.getNum() > 0)) {
			result.setListObjectB(new ArrayList<JavaJsonRpcObjectB>());
			for (int i = 0; i < parameter.getNum(); i++) {
				result.getListObjectB().add(parameter);
			}
		}

		return result;
	}

}
