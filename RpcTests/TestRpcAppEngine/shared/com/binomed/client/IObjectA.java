package com.binomed.client;

import java.io.Serializable;
import java.util.List;

/**
 * Simple POJO with an instance of {@link IObjectB}
 * 
 * @author jefBinomed
 * 
 * @param <T>
 */
public interface IObjectA<T extends IObjectB> extends Serializable {

	T getObjectB();

	void setObjectB(T objectB);

	List<T> getListObjectB();

	void setListObjectB(List<T> listObjectB);

	String getName();

	void setName(String name);

}
