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
package com.binomed.server.requestfactory;

import java.util.ArrayList;

public class HelloWorldService {

	public HelloWorldService() {
	}

	public static RequestFactoryObjectA getMessage() {

		RequestFactoryObjectB objB = new RequestFactoryObjectB();
		objB.setName("ObjectB");

		RequestFactoryObjectA result = new RequestFactoryObjectA();
		result.setName("ObjectA");
		result.setListObjectB(new ArrayList<RequestFactoryObjectB>());
		result.getListObjectB().add(objB);
		result.setObjectB(objB);

		return result;
	}

	public static RequestFactoryObjectA getMessageWithParameter(RequestFactoryObjectB parameter) {

		RequestFactoryObjectA result = new RequestFactoryObjectA();
		result.setName("WithParameter");
		result.setObjectB(parameter);

		if ((parameter != null) && (parameter.getNum() > 0)) {
			result.setListObjectB(new ArrayList<RequestFactoryObjectB>());
			for (int i = 0; i < parameter.getNum(); i++) {
				result.getListObjectB().add(parameter);
			}
		}

		return result;
	}

	private String name;
	private int num;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
