package com.binomed.client.rpc.javajsonrpc;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.binomed.client.rpc.javajsonrpc.dto.JavaJsonRpcObjectA;

public interface IJavaJsonRpcService {

	JavaJsonRpcObjectA getMessage() throws JsonParseException, JsonMappingException, IOException;

}
