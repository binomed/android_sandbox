package com.binomed.rpc.shared;

import com.binomed.rpc.shared.dto.SimpleBean;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("test")
public interface TestService extends RemoteService {

	SimpleBean getABean();

}
