package com.binomed.client.rpc.javajsonrpc;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;
import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectB;

public interface IJavaJsonRpcService {

	JavaJsonRpcObjectA getMessage() throws JsonParseException, JsonMappingException, IOException;

	JavaJsonRpcObjectA getMessageWithParameter(JavaJsonRpcObjectB parameter) throws JsonParseException, JsonMappingException, IOException;

}
