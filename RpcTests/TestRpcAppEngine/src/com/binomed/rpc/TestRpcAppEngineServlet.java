package com.binomed.rpc;

import com.binomed.rpc.shared.TestService;
import com.binomed.rpc.shared.dto.SimpleBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TestRpcAppEngineServlet extends RemoteServiceServlet implements TestService {

	@Override
	public SimpleBean getABean() {
		SimpleBean bean = new SimpleBean();
		return bean;
	}
}
