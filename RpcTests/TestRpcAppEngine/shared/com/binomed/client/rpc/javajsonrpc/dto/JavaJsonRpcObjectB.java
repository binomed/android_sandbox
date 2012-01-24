package com.binomed.client.rpc.javajsonrpc.dto;

import java.util.HashMap;

import com.binomed.client.IObjectBMap;

public class JavaJsonRpcObjectB implements IObjectBMap {

	public JavaJsonRpcObjectB() {
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
