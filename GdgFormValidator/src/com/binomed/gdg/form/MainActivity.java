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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.binomed.gdg.form.tools.IntentIntegrator;
import com.binomed.gdg.form.tools.IntentResult;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class MainActivity extends Activity {

	private static final int DIALOG_ACCOUNTS = 1;
	private static final int DIALOG_FORM = 2;
	private static final String AUTH_TOKEN_TYPE = "oauth2:" + DriveScopes.DRIVE_FILE + " " + DriveScopes.DRIVE + " " + DriveScopes.DRIVE_READONLY + " " + DriveScopes.DRIVE_METADATA_READONLY;
	private static final String APP_NAME = "GdgFormValidator";
	private FileList list;

	private static final String TAG = "GdgFormValidator";

	private AccountManager accountManager;

	private MainActivity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		activity = this;

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
		case DIALOG_ACCOUNTS: {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select a Google account");
			final Account[] accounts = accountManager.getAccountsByType("com.google");
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
		case DIALOG_FORM: {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Select a Google Form");
			final int size = list.getItems().size();
			String[] names = new String[size];
			for (int i = 0; i < size; i++) {
				names[i] = list.getItems().get(i).getTitle();
			}
			builder.setItems(names, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Stuff to do when the account is selected by the user
					gotForm(list.getItems().get(which).getId());
				}

			});
			return builder.create();
		}
		}
		return null;
	}

	private void gotAccount(final Account account) {
		accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
			public void run(AccountManagerFuture<Bundle> future) {
				try {
					// If the user has authorized your application to
					// use the tasks API
					// a token is available.
					String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
					// Now you can use the Tasks API...
					useDriveAPI(token, account.name);
				} catch (OperationCanceledException e) {
					// TODO: The user has denied you access to the API,
					// you should handle that
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}
			}
		}, null);
	}

	protected void useDriveAPI(final String accessToken, String accountName) {
		// Setting up the Tasks API Service

		final HttpTransport transport = AndroidHttp.newCompatibleTransport();
		final JsonFactory jsonFactory = new JacksonFactory();
		// final HttpRequestInitializer credential = getCredentialHttpRequest(accessToken);
		// final HttpRequestInitializer credential = getCredentialGoogleAccount(accessToken, accountName);
		final HttpRequestInitializer credential = getCredentialGoogle(accessToken);

		AsyncTask<Void, Void, FileList> listFilesTask = new AsyncTask<Void, Void, FileList>() {

			@Override
			protected void onPostExecute(final FileList list) {
				if (list == null) {
					return;
				}
				MainActivity.this.list = list;
				showDialog(DIALOG_FORM);
				for (File file : list.getItems()) {
					Log.i(TAG, file.getId() + " | " + file.getTitle() + " | " + file.getMimeType());
				}

				AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
				builder.setTitle("Select a Google Form");
				final int size = list.getItems().size();
				String[] names = new String[size];
				for (int i = 0; i < size; i++) {
					names[i] = list.getItems().get(i).getTitle();
				}
				builder.setItems(names, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Stuff to do when the account is selected by the user
						gotForm(list.getItems().get(which).getId());
					}

				});
				builder.create().show();

				Log.i(TAG, "Items listed");
			}

			@Override
			protected FileList doInBackground(Void... params) {
				Drive service = new Drive.Builder(transport, jsonFactory, credential) //
						// .setJsonHttpRequestInitializer(new GoogleKeyInitializer(Keys.API_KEY)) //
						.setApplicationName(APP_NAME)//
						.build();

				try {
					com.google.api.services.drive.Drive.Files.List request = service.files().list() //
							.setQ("mimeType='application/vnd.google-apps.form'")//
					;
					final FileList list = request.execute();
					return list;

				} catch (IOException e) {

					Log.e(TAG, e.getLocalizedMessage(), e);
				}
				return null;
			}
		}.execute();

	}

	private void gotForm(String id) {
		// TODO Auto-generated method stub

	}

	private HttpRequestInitializer getCredentialGoogle(final String accessToken) {
		GoogleCredential credential = new GoogleCredential();
		credential.setAccessToken(accessToken);
		return credential;
	}

	private HttpRequestInitializer getCredentialGoogleAccount(final String accessToken, final String accountName) {
		GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(accessToken, getApplicationContext(), DriveScopes.DRIVE_FILE);
		credential.setAccountName(accountName);
		return credential;
	}

	private HttpRequestInitializer getCredentialHttpRequest(final String accessToken) {
		final HttpRequestInitializer credential = new HttpRequestInitializer() {

			@Override
			public void initialize(HttpRequest request) throws IOException {
				request.getHeaders().setAuthorization(GoogleHeaders.getGoogleLoginValue(accessToken));

			}
		};
		return credential;
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
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			// handle scan result
		}
		// else continue with any other code you need in the method
	}

}
