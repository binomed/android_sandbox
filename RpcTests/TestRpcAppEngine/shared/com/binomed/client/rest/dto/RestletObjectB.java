package com.binomed.client.rest.dto;

import java.io.Serializable;
import java.util.HashMap;

public class RestletObjectB implements /* IObjectBMap, */Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestletObjectB() {
		super();
	}

	private String name;

	private HashMap<String, String> map;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

}
