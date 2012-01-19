package com.binomed.client.rpc.javajsonrpc.dto;

import java.util.List;

public class JavaJsonRpcObjectA {

	public JavaJsonRpcObjectA() {
		super();
	}

	private JavaJsonRpcObjectB objectB;

	private List<JavaJsonRpcObjectB> listObjectB;

	private String name;

	public JavaJsonRpcObjectB getObjectB() {
		return objectB;
	}

	public void setObjectB(JavaJsonRpcObjectB objectB) {
		this.objectB = objectB;
	}

	public List<JavaJsonRpcObjectB> getListObjectB() {
		return listObjectB;
	}

	public void setListObjectB(List<JavaJsonRpcObjectB> listObjectB) {
		this.listObjectB = listObjectB;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
