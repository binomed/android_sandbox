package com.binomed.client.requestfactory.shared;

import com.binomed.client.IObjectA;
import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName("com.binomed.server.requestfactory.RequestFactoryObjectA")
public interface RequestFactoryObjectAProxy extends IObjectA<RequestFactoryObjectBProxy>, ValueProxy {

}
