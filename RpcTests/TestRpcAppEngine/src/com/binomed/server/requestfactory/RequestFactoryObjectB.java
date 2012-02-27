package com.binomed.server.requestfactory;

import java.util.ArrayList;

import com.binomed.client.IObjectB;

/**
 * Request factory implementation for {@link IObjectB}
 * 
 * @author jefBinomed
 * 
 */
public class RequestFactoryObjectB {

	public RequestFactoryObjectB() {
		super();
	}

	private String name;
	private int num;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/*
	 * 
	 * Service Part
	 */

	public static RequestFactoryObjectA getMessage() {

		RequestFactoryObjectB objB = new RequestFactoryObjectB();
		objB.setName("ObjectB");

		RequestFactoryObjectA result = new RequestFactoryObjectA();
		result.setName("ObjectA");
		result.setListObjectB(new ArrayList<RequestFactoryObjectB>());
		result.getListObjectB().add(objB);
		result.setObjectB(objB);

		return result;
	}

	public RequestFactoryObjectA getMessageWithParameter() {

		RequestFactoryObjectA result = new RequestFactoryObjectA();
		result.setName("WithParameter");

		RequestFactoryObjectB objB = new RequestFactoryObjectB();
		objB.setName(name);
		result.setObjectB(objB);

		if (num > 0) {
			result.setListObjectB(new ArrayList<RequestFactoryObjectB>());
			for (int i = 0; i < num; i++) {
				result.getListObjectB().add(objB);
			}
		}

		return result;
	}

}
