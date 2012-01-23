package com.binomed.client;

import java.util.List;

public interface IObjectA<T extends IObjectB> {

	T getObjectB();

	void setObjectB(T objectB);

	List<T> getListObjectB();

	void setListObjectB(List<T> listObjectB);

	String getName();

	void setName(String name);

}
