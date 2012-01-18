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
package com.binomed.rpc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;

import com.google.web.bindery.requestfactory.shared.Request;

/**
 * Register/unregister with the third-party App Engine server using RequestFactory.
 */
public class DeviceRegistrar {
	public static final String ACCOUNT_NAME_EXTRA = "AccountName";

	public static final String STATUS_EXTRA = "Status";

	public static final int REGISTERED_STATUS = 1;

	public static final int UNREGISTERED_STATUS = 2;

	public static final int ERROR_STATUS = 3;

	private static final String TAG = "DeviceRegistrar";

	public static void registerOrUnregister(final Context context, final String deviceRegistrationId, final boolean register) {
		final SharedPreferences settings = Util.getSharedPreferences(context);
		final String accountName = settings.getString(Util.ACCOUNT_NAME, "Unknown");
		final Intent updateUIIntent = new Intent(Util.UPDATE_UI_INTENT);

		String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

		Request<Void> req;
		SharedPreferences.Editor editor = settings.edit();
		if (register) {
			editor.putString(Util.DEVICE_REGISTRATION_ID, deviceRegistrationId);
		} else {
			clearPreferences(editor);
		}
		editor.commit();
		updateUIIntent.putExtra(ACCOUNT_NAME_EXTRA, accountName);
		updateUIIntent.putExtra(STATUS_EXTRA, register ? REGISTERED_STATUS : UNREGISTERED_STATUS);
		context.sendBroadcast(updateUIIntent);
	}

	private static void clearPreferences(SharedPreferences.Editor editor) {
		editor.remove(Util.ACCOUNT_NAME);
		editor.remove(Util.AUTH_COOKIE);
		editor.remove(Util.DEVICE_REGISTRATION_ID);
	}

}
