package com.binomed.client;

import java.io.Serializable;

/**
 * Simple POJO with a String and an int
 * 
 * @author jefBinomed
 * 
 */
public interface IObjectB extends Serializable {

	String getName();

	void setName(String name);

	int getNum();

	void setNum(int num);

}
