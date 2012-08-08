package com.binomed.gdg.form;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.binomed.gdg.form.tools.IntentIntegrator;
import com.binomed.gdg.form.tools.IntentResult;
import com.google.api.client.auth.oauth2.draft10.AccessProtectedResource;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

public class MainActivity extends Activity {

	private static final int DIALOG_ACCOUNTS = 1;
	private static final String AUTH_TOKEN_TYPE = "oauth2:"+DriveScopes.DRIVE_FILE;
	
	private AccountManager accountManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		accountManager = AccountManager.get(this);
		Account[] accounts = accountManager.getAccountsByType("com.google");
		if (accounts.length > 1) {
			showDialog(DIALOG_ACCOUNTS);
		} else if (accounts.length == 1) {
			gotAccount(accounts[0]);
		}
	}
	

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ACCOUNTS:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select a Google account");
			final Account[] accounts = accountManager
					.getAccountsByType("com.google");
			final int size = accounts.length;
			String[] names = new String[size];
			for (int i = 0; i < size; i++) {
				names[i] = accounts[i].name;
			}
			builder.setItems(names, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Stuff to do when the account is selected by the user
					gotAccount(accounts[which]);
				}

			});
			return builder.create();
		}
		return null;
	}

	private void gotAccount(Account account) {
		accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this,
				new AccountManagerCallback<Bundle>() {
					public void run(AccountManagerFuture<Bundle> future) {
						try {
							// If the user has authorized your application to
							// use the tasks API
							// a token is available.
							String token = future.getResult().getString(
									AccountManager.KEY_AUTHTOKEN);
							// Now you can use the Tasks API...
							useTasksAPI(token);
						} catch (OperationCanceledException e) {
							// TODO: The user has denied you access to the API,
							// you should handle that
						} catch (Exception e) {
							Log.e("GdgFormValidator", e.getLocalizedMessage(),
									e);
						}
					}
				}, null);
	}

	protected void useTasksAPI(String accessToken) {
		// Setting up the Tasks API Service
//		HttpTransport transport = AndroidHttp.newCompatibleTransport();
//		AccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(
//				accessToken);
//		Drive service = new Drive.Builder(transport, new JacksonFactory(), accessProtectedResource) //
//				.setApplicationName("GdgFormValidator") //
//				.build();
//		
//		try {
//			service.files().list();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// TODO: now use the service to query the Tasks API
	}

	public void clickEvent(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.button1:
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
			break;
		default:
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			// handle scan result
		}
		// else continue with any other code you need in the method
	}

}
