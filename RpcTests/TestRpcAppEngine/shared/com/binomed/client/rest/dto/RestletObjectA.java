package com.binomed.client.rest.dto;

import java.io.Serializable;
import java.util.List;

import com.binomed.client.IObjectA;

/**
 * Rest Instance of {@link IObjectA}
 * 
 * @author jefBinomed
 * 
 */
public class RestletObjectA implements IObjectA<RestletObjectB>, Serializable {

	private static final long serialVersionUID = -7215060306717430450L;

	public RestletObjectA() {
		super();
	}

	private RestletObjectB objectB;

	private List<RestletObjectB> listObjectB;

	private String name;

	@Override
	public RestletObjectB getObjectB() {
		return objectB;
	}

	@Override
	public void setObjectB(RestletObjectB objectB) {
		this.objectB = objectB;
	}

	@Override
	public List<RestletObjectB> getListObjectB() {
		return listObjectB;
	}

	@Override
	public void setListObjectB(List<RestletObjectB> listObjectB) {
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
