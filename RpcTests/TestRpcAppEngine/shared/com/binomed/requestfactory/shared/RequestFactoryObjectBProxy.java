package com.binomed.requestfactory.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName("com.binomed.requestfactory.client.RequestFactoryObjectB")
public interface RequestFactoryObjectBProxy extends ValueProxy {

	String getName();

	void setName(String name);

}
