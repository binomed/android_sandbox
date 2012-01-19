package com.binomed.server.rpc.javajsonrpc;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.binomed.client.rpc.javajsonrpc.IJavaJsonRpcService;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectB;

public class JavaJsonRpcService implements IJavaJsonRpcService {

	@Override
	public JavaJsonRpcObjectA getMessage() throws JsonParseException, JsonMappingException, IOException {
		JavaJsonRpcObjectB objB = new JavaJsonRpcObjectB();
		objB.setName("ObjectB");

		JavaJsonRpcObjectA result = new JavaJsonRpcObjectA();
		result.setName("ObjectA");
		result.setListObjectB(new ArrayList<JavaJsonRpcObjectB>());
		result.getListObjectB().add(objB);
		result.setObjectB(objB);

		return result;
	}

}
