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
package com.binomed.client.requestfactory;

import com.binomed.client.IObjectA;
import com.binomed.client.requestfactory.shared.RequestFactoryObjectAProxy;
import com.binomed.client.requestfactory.shared.RequestFactoryObjectBProxy;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ServiceName;

/**
 * Instance of Request Factory
 * 
 * @author jefBinomed
 * 
 */
public interface MyRequestFactory extends RequestFactory {

	/**
	 * Service exposed to client
	 * 
	 * @author jefBinomed
	 * 
	 */
	@ServiceName("com.binomed.server.requestfactory.RequestFactoryObjectB")
	public interface HelloWorldRequest extends RequestContext {

		/**
		 * Returns a {@link IObjectA} without param
		 * 
		 * @return
		 */
		Request<RequestFactoryObjectAProxy> getMessage();

		/**
		 * Returns a {@link IObjectA} with param
		 * 
		 * @return
		 */
		InstanceRequest<RequestFactoryObjectBProxy, RequestFactoryObjectAProxy> getMessageWithParameter();

	}

	/**
	 * Get the service to expose
	 * 
	 * @return
	 */
	HelloWorldRequest helloWorldRequest();

}
