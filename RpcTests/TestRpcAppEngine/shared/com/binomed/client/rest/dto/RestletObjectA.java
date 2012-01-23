package com.binomed.client.rest.dto;

import java.io.Serializable;
import java.util.List;

public class RestletObjectA implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestletObjectA() {
		super();
	}

	private RestletObjectB objectB;

	private List<RestletObjectB> listObjectB;

	private String name;

	public RestletObjectB getObjectB() {
		return objectB;
	}

	public void setObjectB(RestletObjectB objectB) {
		this.objectB = objectB;
	}

	public List<RestletObjectB> getListObjectB() {
		return listObjectB;
	}

	public void setListObjectB(List<RestletObjectB> listObjectB) {
		this.listObjectB = listObjectB;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
