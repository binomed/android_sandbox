/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.binomed.android.rpc.requestFactory;

import java.net.URI;
import java.net.URISyntaxException;

import android.content.Context;
import android.util.Log;

import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.vm.RequestFactorySource;

/**
 * Utility methods for getting the base URL for client-server communication and retrieving shared preferences.
 */
public class Util {

	/**
	 * Tag for logging.
	 */
	private static final String TAG = "Util";

	// Shared constants

	/**
	 * Value for {@link #CONNECTION_STATUS} key.
	 */
	public static final String CONNECTING = "connecting";

	/*
	 * URL suffix for the RequestFactory servlet.
	 */
	public static final String RF_METHOD = "/gwtRequest";

	public static final String LOCALHOST = "http://10.0.2.2:8888"; //$NON-NLS-1$

	/**
	 * Creates and returns an initialized {@link RequestFactory} of the given type.
	 */
	public static <T extends RequestFactory> T getRequestFactory(Context context, Class<T> factoryClass) {
		T requestFactory = RequestFactorySource.create(factoryClass);

		String authCookie = null;

		String uriString = LOCALHOST + RF_METHOD;
		URI uri;
		try {
			uri = new URI(uriString);
		} catch (URISyntaxException e) {
			Log.w(TAG, "Bad URI: " + uriString, e);
			return null;
		}
		requestFactory.initialize(new SimpleEventBus(), new AndroidRequestTransport(uri, authCookie));

		return requestFactory;
	}

}
