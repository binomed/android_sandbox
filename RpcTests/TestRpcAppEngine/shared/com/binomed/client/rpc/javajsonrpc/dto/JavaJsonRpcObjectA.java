package com.binomed.client.rpc.javajsonrpc.dto;

import java.util.List;

import com.binomed.client.IObjectA;

public class JavaJsonRpcObjectA implements IObjectA<JavaJsonRpcObjectB> {

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
