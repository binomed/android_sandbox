package com.binomed.server.requestfactory;

import java.util.List;

public class RequestFactoryObjectA {

	public RequestFactoryObjectA() {
		super();
	}

	private RequestFactoryObjectB objectB;

	private List<RequestFactoryObjectB> listObjectB;

	private String name;

	public RequestFactoryObjectB getObjectB() {
		return objectB;
	}

	public void setObjectB(RequestFactoryObjectB objectB) {
		this.objectB = objectB;
	}

	public List<RequestFactoryObjectB> getListObjectB() {
		return listObjectB;
	}

	public void setListObjectB(List<RequestFactoryObjectB> listObjectB) {
		this.listObjectB = listObjectB;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
