package com.binomed.rpc.shared;

import com.binomed.rpc.shared.dto.SimpleBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TestServiceAsync {

	void getABean(AsyncCallback<SimpleBean> callback);

}
