package com.binomed.client.rest.dto;

import java.io.Serializable;
import java.util.HashMap;

import com.binomed.client.IObjectB;
import com.binomed.client.IObjectBMap;

/**
 * Rest Instance of {@link IObjectB}
 * 
 * @author jefBinomed
 * 
 */
public class RestletObjectB implements IObjectBMap, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9046805886041076231L;

	public RestletObjectB() {
		super();
	}

	private String name;

	private int num;
	private HashMap<String, String> map;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public HashMap<String, String> getMap() {
		return map;
	}

	@Override
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	@Override
	public int getNum() {
		return num;
	}

	@Override
	public void setNum(int num) {
		this.num = num;

	}

}
