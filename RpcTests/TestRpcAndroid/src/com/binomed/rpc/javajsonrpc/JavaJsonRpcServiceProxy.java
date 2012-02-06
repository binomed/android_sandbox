package com.binomed.rpc.javajsonrpc;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.binomed.client.rpc.javajsonrpc.IJavaJsonRpcService;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectB;

import cz.eman.jsonrpc.client.AbstractClientProxy;
import cz.eman.jsonrpc.client.ClientProvider;

public class JavaJsonRpcServiceProxy extends AbstractClientProxy<IJavaJsonRpcService> implements IJavaJsonRpcService {

	public JavaJsonRpcServiceProxy(ClientProvider clientProvider) {
		super(IJavaJsonRpcService.class, clientProvider);
	}

	@Override
	public JavaJsonRpcObjectA getMessage() throws JsonParseException, JsonMappingException, IOException {
		return (JavaJsonRpcObjectA) super.callMethod("getMessage", new Object[] {});
	}

	@Override
	public JavaJsonRpcObjectA getMessageWithParameter(JavaJsonRpcObjectB parameter) throws JsonParseException, JsonMappingException, IOException {
		return (JavaJsonRpcObjectA) super.callMethod("getMessageWithParameter", new Object[] { parameter });
	}

}
