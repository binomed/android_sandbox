package com.binomed.gdg.form;

import java.io.ByteArrayInputStream;
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
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.View;

import com.binomed.gdg.form.tools.IntentIntegrator;
import com.binomed.gdg.form.tools.IntentResult;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.draft10.AccessProtectedResource;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class MainActivity extends Activity {

	private static final int DIALOG_ACCOUNTS = 1;
	private static final String AUTH_TOKEN_TYPE = "oauth2:"+DriveScopes.DRIVE_FILE;
	
	private static final String TAG = "GdgFormValidator";
	
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
							useDriveAPI(token);
						} catch (OperationCanceledException e) {
							// TODO: The user has denied you access to the API,
							// you should handle that
						} catch (Exception e) {
							Log.e(TAG, e.getLocalizedMessage(),
									e);
						}
					}
				}, null);
	}

	protected void useDriveAPI(String accessToken) {
		// Setting up the Tasks API Service
//		HttpTransport transport = AndroidHttp.newCompatibleTransport();
//		AccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(
//				accessToken);
		
		
//		// Set up the HTTP transport and JSON factory
//		HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
//		JsonFactory jsonFactory = new JacksonFactory();
//		
//		 String json = "{"+//
//			       "\"web\": {"+//
//			       "  \"client_id\": \"[[YOUR_CLIENT_ID]]\","+//
//			       "  \"client_secret\": \"[[YOUR_CLIENT_SECRET]]\","+//
//			       "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\","+//
//			       "  \"token_uri\": \"https://accounts.google.com/o/oauth2/token\""+//
//			       "}"+//
//			    "}";//
//		
//		
//		GoogleClientSecrets clientSecrets =
//		          GoogleClientSecrets.load(jsonFactory,new ByteArrayInputStream(json.getBytes()));
//		      flow =
//		          new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
//		              .setAccessType("offline").setApprovalPrompt("force").build();
//
//		// Set up OAuth 2.0 access of protected resources
//		// using the refresh and access tokens, automatically
//		// refreshing the access token when it expires
//		Credential credential = new Credential.Builder(accessMethod)
//		    .setJsonFactory(jsonFactory)
//		    .setTransport(httpTransport)
//		    .setTokenServerEncodedUrl(tokenServerEncodedUrl)
//		    .setClientAuthentication(clientAuthentication)
//		    .setRequestInitializer(requestInitializer)
//		    .build();
//		
//		
//		Drive service = new Drive.Builder(transport, new JacksonFactory(), accessProtectedResource) //
//				.setApplicationName("GdgFormValidator") //
//				.build();
//		
//		try {
//			com.google.api.services.drive.Drive.Files.List request = service.files().list();
//			FileList list = request.execute();
//			for (File file : list.getItems()){
//				Log.i(TAG, file.getId()+" | "+file.getTitle()+" | "+file.getMimeType());
//			}
//		} catch (IOException e) {
//			Log.e(TAG, e.getLocalizedMessage(),
//					e);
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
