package com.binomed.client.rpc.javajsonrpc.dto;

import java.util.List;

import com.binomed.client.IObjectA;

/**
 * Java-Json instance of {@link IObjectA}
 * 
 * @author jefBinomed
 * 
 */
public class JavaJsonRpcObjectA implements IObjectA<JavaJsonRpcObjectB> {

	private static final long serialVersionUID = 5272266815459595655L;

	public JavaJsonRpcObjectA() {
		super();
	}

	private JavaJsonRpcObjectB objectB;

	private List<JavaJsonRpcObjectB> listObjectB;

	private String name;

	@Override
	public JavaJsonRpcObjectB getObjectB() {
		return objectB;
	}

	@Override
	public void setObjectB(JavaJsonRpcObjectB objectB) {
		this.objectB = objectB;
	}

	@Override
	public List<JavaJsonRpcObjectB> getListObjectB() {
		return listObjectB;
	}

	@Override
	public void setListObjectB(List<JavaJsonRpcObjectB> listObjectB) {
		this.listObjectB = listObjectB;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
