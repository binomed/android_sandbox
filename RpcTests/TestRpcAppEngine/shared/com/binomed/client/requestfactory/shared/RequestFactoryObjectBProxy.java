package com.binomed.client.requestfactory.shared;

import com.binomed.client.IObjectB;
import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * RequestFacotry bean for {@link IObjectB}
 * 
 * @author jefBinomed
 * 
 */
@ProxyForName("com.binomed.server.requestfactory.RequestFactoryObjectB")
public interface RequestFactoryObjectBProxy extends ValueProxy {

	String getName();

	void setName(String name);

	int getNum();

	void setNum(int num);

}
