package com.binomed.client.rest.dto;

import java.io.Serializable;

public class RestletObjectB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestletObjectB() {
		super();
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
