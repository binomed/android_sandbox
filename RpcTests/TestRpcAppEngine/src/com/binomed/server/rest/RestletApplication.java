package com.binomed.server.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestletApplication extends Application {

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a
		// new instance of HelloWorldResource.
		Router router = new Router(getContext());

		// Defines only one route
		// router.attachDefault(new Directory(getContext(), "war:///"));
		// router.attach("/test/", RestResource.class);
		// router.attachDefault(RestResource.class);
		router.attach("/test", RestResource.class);
		// router.attach("/test/{param}", RestResourceParam.class);
		router.attach("/testParam", RestResourceParam.class);

		return router;
	}

}
