package com.binomed.gdg.form;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.binomed.gdg.form.tools.IntentIntegrator;
import com.binomed.gdg.form.tools.IntentResult;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.common.base.Objects;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

public class MainActivity extends Activity {

	private static final int DIALOG_ACCOUNTS = 1;
	private static final int DIALOG_FORM = 2;
	private static final String AUTH_TOKEN_TYPE = "oauth2:" //
			+ DriveScopes.DRIVE_FILE //
			+ " " + DriveScopes.DRIVE //
			+ " " + DriveScopes.DRIVE_READONLY //
			+ " " + DriveScopes.DRIVE_METADATA_READONLY //
			+ " https://spreadsheets.google.com/feeds";
	private static final String APP_NAME = "GdgFormValidator";
	private FileList list;
	private List<SpreadsheetEntry> spreadsheets;

	private static final String TAG = "GdgFormValidator";

	private AccountManager accountManager;
	private MainActivity activity;
	private Handler handler;
	private String token;
	private String accountName;
	private String formId;
	private SpreadsheetEntry spreadSheetEntry;

	private final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	private final JsonFactory jsonFactory = new JacksonFactory();
	private HttpRequestInitializer credential;
	private Drive driveService;
	private SpreadsheetService spreadSheetService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.activity = this;
		this.handler = new Handler();

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
			// if (list != null) {
			// final int size = list.getItems().size();
			// String[] names = new String[size];
			// for (int i = 0; i < size; i++) {
			// names[i] = list.getItems().get(i).getTitle();
			// }
			// builder.setItems(names, new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog, int which) {
			// // Stuff to do when the account is selected by the user
			// gotForm(list.getItems().get(which).getId());
			// }
			//
			// });
			// }
			if (spreadsheets != null) {
				final int size = spreadsheets.size();
				String[] names = new String[size];
				for (int i = 0; i < size; i++) {
					names[i] = spreadsheets.get(i).getTitle().getPlainText();
				}
				builder.setItems(names, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Stuff to do when the account is selected by the user
						gotForm(spreadsheets.get(which));
					}

				});
			}
			return builder.create();
		}
		}
		return null;
	}

	private void gotAccount(final Account account) {
		this.accountName = account.name;
		accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
			public void run(AccountManagerFuture<Bundle> future) {
				try {
					// If the user has authorized your application to
					// use the tasks API
					// a token is available.
					String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
					MainActivity.this.token = token;
					MainActivity.this.credential = getCredentialGoogle(token);

					initServices();

					// Now you can use the Tasks API...
					// useDriveAPI();
					asynTaskSpreadSheetStandAlone.execute();
				} catch (OperationCanceledException e) {
					// TODO: The user has denied you access to the API,
					// you should handle that
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}
			}
		}, null);
	}

	private void initServices() {
		// Setting up the Tasks API Service
		driveService = new Drive.Builder(transport, jsonFactory, credential) //
				.setApplicationName(APP_NAME)//
				.build();

		spreadSheetService = new SpreadsheetService(APP_NAME);
		spreadSheetService.setAuthSubToken(token);

	}

	protected void useDriveAPI() {
		asynTaskListForm.execute();
	}

	private void gotForm(String id) {
		this.formId = id;
		asynTaskSpreadSheet.execute();

	}

	private void gotForm(SpreadsheetEntry entry) {
		this.spreadSheetEntry = entry;
		asynTaskWorkSheet.execute();

	}

	private HttpRequestInitializer getCredentialGoogle(final String accessToken) {
		GoogleCredential credential = new GoogleCredential();
		credential.setAccessToken(accessToken);
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

	private AsyncTask<Void, Void, FileList> asynTaskListForm = new AsyncTask<Void, Void, FileList>() {
		private Dialog waitDialog;

		@Override
		protected void onPostExecute(final FileList list) {
			MainActivity.this.list = list;
			handler.post(new Runnable() {

				@Override
				public void run() {
					waitDialog.dismiss();
					showDialog(DIALOG_FORM);
				}
			});
			if (list == null) {
				return;
			}
			for (File file : list.getItems()) {
				Log.i(TAG, file.getId() + " | " + file.getTitle() + " | " + file.getMimeType());
			}

			Log.i(TAG, "Items listed");
		}

		@Override
		protected void onPreExecute() {

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Searching forms ...");
			waitDialog = builder.create();
			handler.post(new Runnable() {

				@Override
				public void run() {
					waitDialog.show();

				}
			});
		}

		@Override
		protected FileList doInBackground(Void... params) {

			try {
				com.google.api.services.drive.Drive.Files.List request = driveService.files().list() //
						.setQ("mimeType='application/vnd.google-apps.form'")//
				;
				final FileList list = request.execute();
				return list;

			} catch (IOException e) {

				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			return null;
		}
	};

	private AsyncTask<Void, Void, WorksheetEntry> asynTaskSpreadSheet = new AsyncTask<Void, Void, WorksheetEntry>() {
		private Dialog waitDialog;

		@Override
		protected void onPostExecute(final WorksheetEntry workSheet) {
			if (workSheet != null) {

				try {
					ListFeed listFeed = spreadSheetService.getFeed(workSheet.getListFeedUrl(), ListFeed.class);

					// Iterate through each row, printing its cell values.
					for (ListEntry row : listFeed.getEntries()) {
						// Print the first column's cell value
						Log.i(TAG, row.getTitle().getPlainText() + "\t");
						// Iterate over the remaining columns, and print each cell value
						for (String tag : row.getCustomElements().getTags()) {
							Log.i(TAG, row.getCustomElements().getValue(tag) + "\t");
						}
						Log.i(TAG, "");
					}
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}
			}
			Log.i(TAG, "WorkSheet Manage");
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected WorksheetEntry doInBackground(Void... params) {

			try {
				URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

				// Make a request to the API and get all spreadsheets.
				SpreadsheetFeed feed = spreadSheetService.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
				List<SpreadsheetEntry> spreadsheets = feed.getEntries();

				// com.google.api.services.drive.Drive.Files.List request = spreadSheetService.getfiles().list() //
				// .setQ("mimeType='application/vnd.google-apps.form'")//
				// ;
				WorksheetEntry worksheetEntry = null;
				Log.i(TAG, "Search for : " + formId);
				for (SpreadsheetEntry entry : spreadsheets) {
					Log.i(TAG, "SpreadSheet Id : " + entry.getId() + ", " + entry.getTitle().getPlainText() + ", key : " + entry.getKey());
					if (Objects.equal(entry.getKey(), formId)) {
						for (WorksheetEntry entryWork : entry.getWorksheets()) {

							Log.i(TAG, "WorkSheet Title : " + entryWork.getTitle().getPlainText());
							if (Objects.equal(entryWork.getTitle().getPlainText(), "Codes")) {
								worksheetEntry = entryWork;
							}

						}

					}

				}
				return worksheetEntry;

			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			return null;
		}
	};

	private AsyncTask<Void, Void, Void> asynTaskSpreadSheetStandAlone = new AsyncTask<Void, Void, Void>() {
		private Dialog waitDialog;

		@Override
		protected void onPostExecute(final Void workSheet) {
			handler.post(new Runnable() {

				@Override
				public void run() {
					showDialog(DIALOG_FORM);
				}
			});
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

				// Make a request to the API and get all spreadsheets.
				SpreadsheetFeed feed = spreadSheetService.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
				List<SpreadsheetEntry> spreadsheets = feed.getEntries();
				MainActivity.this.spreadsheets = spreadsheets;

			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			return null;
		}
	};

	private AsyncTask<Void, Void, Void> asynTaskWorkSheet = new AsyncTask<Void, Void, Void>() {
		private Dialog waitDialog;

		@Override
		protected void onPostExecute(final Void workSheet) {
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				WorksheetEntry workSheet = null;
				for (WorksheetEntry workSheetEntry2 : spreadSheetEntry.getWorksheets()) {
					Log.i(TAG, "WorkSheetName : " + workSheetEntry2.getTitle().getPlainText());
					if (Objects.equal(workSheetEntry2.getTitle().getPlainText(), "Codes")) {
						workSheet = workSheetEntry2;
						break;
					}
				}

				if (workSheet != null) {
					ListFeed listFeed = spreadSheetService.getFeed(workSheet.getListFeedUrl(), ListFeed.class);

					// Iterate through each row, printing its cell values.
					for (ListEntry row : listFeed.getEntries()) {
						// Print the first column's cell value
						Log.i(TAG, row.getTitle().getPlainText() + "\t");
						// Iterate over the remaining columns, and print each cell value
						for (String tag : row.getCustomElements().getTags()) {
							Log.i(TAG, row.getCustomElements().getValue(tag) + "\t");
						}
						Log.i(TAG, "");
					}
				}
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}
			return null;
		}
	};

}
