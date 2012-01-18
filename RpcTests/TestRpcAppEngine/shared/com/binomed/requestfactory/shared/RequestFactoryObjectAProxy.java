package com.binomed.requestfactory.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName("com.binomed.requestfactory.client.RequestFactoryObjectA")
public interface RequestFactoryObjectAProxy extends ValueProxy {

	RequestFactoryObjectBProxy getObjectB();

	void setObjectB(RequestFactoryObjectBProxy objectB);

	List<RequestFactoryObjectBProxy> getListObjectB();

	void setListObjectB(List<RequestFactoryObjectBProxy> listObjectB);

	String getName();

	void setName(String name);

}
