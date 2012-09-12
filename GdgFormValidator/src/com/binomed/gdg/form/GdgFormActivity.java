/*
 * Copyright (C) 2012 Binomed (http://blog.binomed.fr)
 *
 * Licensed under the Eclipse Public License - v 1.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE PUBLIC 
 * LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR DISTRIBUTION OF THE PROGRAM 
 * CONSTITUTES RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.
 */
package com.binomed.gdg.form;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.binomed.gdg.form.asynctask.AsyncTaskDriveForm;
import com.binomed.gdg.form.asynctask.AsyncTaskDriveForm.CallBackDriveForm;
import com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm;
import com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm.CallBackSpreadsheetForm;
import com.binomed.gdg.form.asynctask.AsyncTaskWorksheet;
import com.binomed.gdg.form.asynctask.AsyncTaskWorksheet.CallBackWorksheetForm;
import com.binomed.gdg.form.tools.DetectConnection;
import com.binomed.gdg.form.tools.zxing.IntentIntegrator;
import com.binomed.gdg.form.tools.zxing.IntentResult;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

/**
 * @author JefBinomed
 * 
 */
public class GdgFormActivity extends Activity implements //
		CallBackDriveForm //
		, CallBackSpreadsheetForm //
		, CallBackWorksheetForm //

{

	/*
	 * 
	 * Constantes
	 */

	/**
	 * Action to check the form id
	 */
	private static final int ACTION_CHECK_FORM_ID = 1;
	/**
	 * Action to list the spreadsheet of user
	 */
	private static final int ACTION_LIST_SPREADSHEET = 2;
	/**
	 * Action to refresh the datas of worksheet
	 */
	private static final int ACTION_REFRESH_DATAS = 3;
	/**
	 * Constantes for selecting account
	 */
	private static final int DIALOG_ACCOUNTS = 1;
	/**
	 * Constantes for selecting formular
	 */
	private static final int DIALOG_DRIVE_FORM = 2;
	/**
	 * Constantes for selecting formular
	 */
	private static final int DIALOG_SPREADSHEET_FORM = 3;

	/**
	 * Constantes for cleaning datas from application
	 */
	private static final int MENU_CLEAR = 1;
	/**
	 * Constatne for refreshing datas from formular
	 */
	private static final int MENU_REFRESH = 2;
	/**
	 * OAuth2 token needed
	 */
	private static final String AUTH_TOKEN_TYPE = "oauth2:" //
			+ DriveScopes.DRIVE_FILE //
			+ " " + DriveScopes.DRIVE //
			+ " " + DriveScopes.DRIVE_READONLY //
			+ " " + DriveScopes.DRIVE_METADATA_READONLY //
			+ " https://spreadsheets.google.com/feeds";
	/**
	 * OAuth2 application Name
	 */
	private static final String APP_NAME = "GdgFormValidator";

	/**
	 * Settings key for account Name
	 */
	private static final String PREF_KEY_ACCOUNT = "accountName";
	/**
	 * Settings key for form id
	 */
	private static final String PREF_KEY_FILE_ID = "fileId";
	/**
	 * Settings key for form Name
	 */
	private static final String PREF_KEY_FILE = "fileName";
	/**
	 * Settings key for the codes to use for validation
	 */
	private static final String PREF_KEY_CODES = "codes";
	/**
	 * Settings key for the feed url for informations of spreadsheet
	 */
	private static final String PREF_KEY_FEED_URL = "feedURL";
	/**
	 * WorkSheetName
	 */
	private static final String WORKSHEET_NAME = "Codes";
	/**
	 * Tag column Name
	 */
	private static final String TAG_CODE_COL = "code";

	private static final String TAG = "GdgFormValidator";

	/*
	 * 
	 * Fields
	 */

	/**
	 * The drive service files list
	 */
	private FileList list;
	/**
	 * The spreadsheet service file list
	 */
	private List<SpreadsheetEntry> spreadsheets;
	/**
	 * The main activity
	 */
	private GdgFormActivity activity;
	/**
	 * Global hanlder for UI sync
	 */
	private Handler handler;

	/**
	 * The shared preferences
	 */
	private SharedPreferences settings;
	/**
	 * Account Name
	 */
	private String accountName;
	/**
	 * Formular Id
	 */
	private String formId;
	/**
	 * Formular Name
	 */
	private String formName;

	/*
	 * OAuth vars
	 */
	/**
	 * Account manager for selecting account
	 */
	private AccountManager accountManager;
	/**
	 * OAuth2 token
	 */
	private String token;

	/**
	 * Transport connection
	 */
	private final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	/**
	 * Factory
	 */
	private final JsonFactory jsonFactory = new JacksonFactory();
	/**
	 * Credential
	 */
	private HttpRequestInitializer credential;

	/*
	 * Services
	 */

	/**
	 * Drive API Service
	 */
	private Drive driveService;
	/**
	 * SpreadSheet service
	 */
	private SpreadsheetService spreadSheetService;

	/**
	 * Drive API asyncTask for retriving forms
	 */
	private AsyncTaskDriveForm asyncTaskDriveForm;
	/**
	 * SpreadSheet API asyncTask for retriving forms
	 */
	private AsyncTaskSpreadsheetForm asyncTaskSpreadsheetForm;
	/**
	 * SpreadSheet API asyncTask for retriving informations from spreadsheet
	 */
	private AsyncTaskWorksheet asyncTaskWorkSheet;

	/*
	 * 
	 * UI Widgets
	 */

	private EditText accountNameText, fileText;
	private TextView loadText, validText;
	private ProgressBar progressBar;
	private Button buttonCheck;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.accountNameText = (EditText) findViewById(R.id.accountName);
		this.fileText = (EditText) findViewById(R.id.file);
		this.loadText = (TextView) findViewById(R.id.loadText);
		this.validText = (TextView) findViewById(R.id.validText);
		this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
		this.buttonCheck = (Button) findViewById(R.id.button1);
		this.buttonCheck.setEnabled(false);
		this.activity = this;
		this.handler = new Handler();

		settings = getPreferences(MODE_PRIVATE);
		accountName = settings.getString(PREF_KEY_ACCOUNT, null);
		formId = settings.getString(PREF_KEY_FILE_ID, null);
		formName = settings.getString(PREF_KEY_FILE, null);

		accountManager = AccountManager.get(this);
		checkAccount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
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
					gotAccount(accounts[which], true);
				}

			});
			return builder.create();
		}
		case DIALOG_DRIVE_FORM: {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Select a Google Form");
			if (list != null) {
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
			}
			return builder.create();
		}
		case DIALOG_SPREADSHEET_FORM: {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Select a Google Form");
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

	/**
	 * Check the account
	 */
	private void checkAccount() {

		if (accountName == null) {
			Account[] accounts = accountManager.getAccountsByType("com.google");
			if (accounts.length > 1) {
				showDialog(DIALOG_ACCOUNTS);
			} else if (accounts.length == 1) {
				gotAccount(accounts[0], true);
			}
		} else {
			gotAccount(getAccountFromName(accountName), false);
		}
	}

	/**
	 * Verify the internet connectivity
	 * 
	 * @return
	 */
	private boolean checkConnectivity() {
		if (DetectConnection.checkInternetConnection(this)) {
			return true;
		} else {
			Toast.makeText(this, "You Do not have Internet Connection", Toast.LENGTH_LONG).show();
			this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
			return false;
		}

	}

	/**
	 * Check the formular
	 */
	private void checkFormId() {

		if (formId != null) {
			this.fileText.setText(formName);
			datasLoad();
		} else {
			useSpreadsheetAPI();
		}

	}

	/**
	 * @param name
	 * @return
	 */
	private Account getAccountFromName(String name) {
		Account[] accounts = accountManager.getAccountsByType("com.google");
		for (Account account : accounts) {
			if (Objects.equal(account.name, this.accountName)) {
				return account;
			}
		}
		return null;
	}

	/**
	 * Called when an account has been selected
	 * 
	 * @param account
	 */
	private void gotAccount(final Account account, boolean getToken) {
		this.accountName = account.name;
		this.accountNameText.setText(this.accountName);
		Editor editor = settings.edit();
		editor.putString(PREF_KEY_ACCOUNT, accountName);
		editor.commit();
		if (getToken) {
			gotToken(account, ACTION_CHECK_FORM_ID);
		} else {
			checkFormId();
		}
	}

	/**
	 * Get the OAuth2 token of the account
	 * 
	 * @param account
	 */
	private void gotToken(final Account account, final int actionToLaunch) {
		if (!checkConnectivity()) {
			return;
		}
		waitMessage(R.string.get_token);
		accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
			public void run(AccountManagerFuture<Bundle> future) {
				try {
					discardWait();
					// If the user has authorized your application to
					// use the tasks API
					// a token is available.
					String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
					GdgFormActivity.this.token = token;
					GdgFormActivity.this.credential = getCredentialGoogle(token);

					initServices();
					switch (actionToLaunch) {
					case ACTION_CHECK_FORM_ID:
						checkFormId();
						break;
					case ACTION_LIST_SPREADSHEET:
						useSpreadsheetAPI();
						break;
					case ACTION_REFRESH_DATAS:
						refreshDatas();
						break;

					default:
						break;
					}

				} catch (OperationCanceledException e) {
					// TODO: The user has denied you access to the API,
					// you should handle that
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}
			}
		}, null);
	}

	/**
	 * Init the services
	 */
	private void initServices() {
		// Setting up the Drive API Service
		driveService = new Drive.Builder(transport, jsonFactory, credential) //
				.setApplicationName(APP_NAME)//
				.build();

		// Setting up the SpreadSheet API Service
		spreadSheetService = new SpreadsheetService(APP_NAME);
		spreadSheetService.setAuthSubToken(token);

	}

	/**
	 * 
	 */
	private void useDriveAPI() {
		asyncTaskDriveForm = new AsyncTaskDriveForm(this, driveService);
		asyncTaskDriveForm.execute();
	}

	/**
	 * 
	 */
	private void useSpreadsheetAPI() {
		if (this.token == null) {
			gotToken(getAccountFromName(accountName), ACTION_LIST_SPREADSHEET);
		} else {
			asyncTaskSpreadsheetForm = new AsyncTaskSpreadsheetForm(this, spreadSheetService);
			asyncTaskSpreadsheetForm.execute();
		}
	}

	/**
	 * @param id
	 *            Call after selecting a form from drive
	 */
	private void gotForm(String id) {
		// TODO find correspondance between drive id and spreadsheet id

	}

	/**
	 * @param entry
	 *            call after selecting a form from spreadsheet
	 */
	private void gotForm(final SpreadsheetEntry entry) {
		this.formId = entry.getKey();
		this.formName = entry.getTitle().getPlainText();
		this.fileText.setText(this.formName);

		// We control the workSheet
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					WorksheetEntry workSheet = null;
					for (WorksheetEntry workSheetEntry : entry.getWorksheets()) {
						if (Objects.equal(workSheetEntry.getTitle().getPlainText(), WORKSHEET_NAME)) {
							workSheet = workSheetEntry;
							break;
						}
					}

					if (workSheet == null) {
						handler.post(new Runnable() {

							@Override
							public void run() {
								validText.setText(R.string.invalid_form);
							}
						});
						return null;
					}

					Editor editor = settings.edit();
					editor.putString(PREF_KEY_FILE_ID, formId);
					editor.putString(PREF_KEY_FILE, formName);
					editor.putString(PREF_KEY_FEED_URL, workSheet.getListFeedUrl().toString());
					editor.commit();
					asyncTaskWorkSheet = new AsyncTaskWorksheet(GdgFormActivity.this, spreadSheetService);
					asyncTaskWorkSheet.setUrlFedd(workSheet.getListFeedUrl());
					asyncTaskWorkSheet.execute();
				} catch (Exception e) {
					manageError("WorkSheet", e);
				}
				return null;
			}

		}.execute();

	}

	/**
	 * @param accessToken
	 * @return
	 */
	private HttpRequestInitializer getCredentialGoogle(final String accessToken) {
		GoogleCredential credential = new GoogleCredential();
		credential.setAccessToken(accessToken);
		return credential;
	}

	/**
	 * @param v
	 */
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

	// Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.addSubMenu(0, MENU_CLEAR, 0, R.string.clear_datas).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		menu.addSubMenu(0, MENU_REFRESH, 0, R.string.refresh_datas).setIcon(android.R.drawable.ic_popup_sync);

		return true;
	}

	// Méthode qui se déclenchera au clic sur un item
	public boolean onOptionsItemSelected(MenuItem item) {
		// On regarde quel item a été cliqué grâce à son id et on déclenche une action
		switch (item.getItemId()) {
		case MENU_CLEAR: {
			buttonCheck.setEnabled(false);
			Editor editor = settings.edit();
			editor.clear();
			editor.commit();
			accountName = null;
			formId = null;
			formName = null;
			accountNameText.setText("");
			fileText.setText("");
			checkAccount();
			return true;
		}
		case MENU_REFRESH: {
			if (token == null) {

				gotToken(getAccountFromName(accountName), ACTION_REFRESH_DATAS);
			} else {
				buttonCheck.setEnabled(false);
				Editor editor = settings.edit();
				editor.putStringSet(PREF_KEY_CODES, null);
				editor.commit();
				refreshDatas();
			}
			return true;
		}
		}
		return false;
	}

	/**
	 * Refresh the datas
	 */
	private void refreshDatas() {
		String urlFeed = settings.getString(PREF_KEY_FEED_URL, null);
		if (urlFeed != null) {
			try {
				asyncTaskWorkSheet = new AsyncTaskWorksheet(this, spreadSheetService);
				asyncTaskWorkSheet.setUrlFedd(new URL(urlFeed));
				asyncTaskWorkSheet.execute();
			} catch (MalformedURLException e) {
				manageError("UrlFeed", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			try {
				Integer.valueOf(scanResult.getContents());
				Set<String> codeList = settings.getStringSet(PREF_KEY_CODES, ImmutableSet.<String> of());

				validateCode(!codeList.isEmpty() && codeList.contains(scanResult.getContents()) ? //
				R.string.code_found
						: //
						R.string.code_not_found);
			} catch (NumberFormatException e) {
				manageError("Result Code", e);
			}
		}
		// else continue with any other code you need in the method
	}

	/*
	 * ----------------------- Overrides methods -----------------------
	 */

	/*
	 * Drive
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskDriveForm.CallBackDriveForm#driveListForm(com.google.api.services.drive.model.FileList)
	 */
	@Override
	public void driveListForm(FileList listFile) {
		this.list = listFile;
		discardWait();
		handler.post(new Runnable() {

			@Override
			public void run() {
				// waitDialog.dismiss(); TODO
				showDialog(DIALOG_DRIVE_FORM);

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskDriveForm.CallBackDriveForm#driveStartSearchFiles()
	 */
	@Override
	public void driveStartSearchFiles() {
		waitMessage(R.string.search_drive_files);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskDriveForm.CallBackDriveForm#driveError(java.lang.Exception)
	 */
	@Override
	public void driveError(Exception e) {
		manageError("Drive", e);
	}

	/*
	 * Spreadsheet
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm.CallBackSpreadsheetForm#spreadsheetListForm(java.util.List)
	 */
	@Override
	public void spreadsheetListForm(List<SpreadsheetEntry> listSpreadSheets) {
		spreadsheets = listSpreadSheets;
		discardWait();
		handler.post(new Runnable() {

			@Override
			public void run() {
				// waitDialog.dismiss(); TODO
				showDialog(DIALOG_SPREADSHEET_FORM);

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm.CallBackSpreadsheetForm#spreadsheetStartSearchFiles()
	 */
	@Override
	public void spreadsheetStartSearchFiles() {
		waitMessage(R.string.search_spreadsheet_files);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm.CallBackSpreadsheetForm#spreadsheetError(java.lang.Exception)
	 */
	@Override
	public void spreadsheetError(Exception e) {
		manageError("SpreadSheet", e);
	}

	/*
	 * 
	 * WorkSheet
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskWorksheet.CallBackWorksheetForm#worksheetListForm(com.google.gdata.data.spreadsheet.ListFeed)
	 */
	@Override
	public void worksheetListForm(ListFeed rows) {
		discardWait();
		Set<String> codes = new HashSet<String>();
		// Iterate through each row, printing its cell values.
		for (ListEntry row : rows.getEntries()) {
			// Print the first column's cell value
			// Iterate over the remaining columns, and print each cell value
			for (String tag : row.getCustomElements().getTags()) {
				if (Objects.equal(TAG_CODE_COL, tag)) {
					codes.add(row.getCustomElements().getValue(tag));
				}
			}
		}

		Editor editor = settings.edit();
		editor.putStringSet(PREF_KEY_CODES, codes);
		editor.commit();
		datasLoad();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskWorksheet.CallBackWorksheetForm#worksheetStartSearchWorkSheet()
	 */
	@Override
	public void worksheetStartSearchWorkSheet() {
		waitMessage(R.string.search_worksheet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskWorksheet.CallBackWorksheetForm#worksheetError(java.lang.Exception)
	 */
	@Override
	public void worksheetError(Exception e) {
		manageError("WorkSheet", e);
	}

	/*
	 * 
	 * UI methods
	 */

	/**
	 * Manage an error
	 * 
	 * @param origin
	 * @param e
	 */
	private void manageError(final String origin, final Exception e) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				loadText.setVisibility(View.INVISIBLE);
				// progressBar.clearAnimation();
				progressBar.setVisibility(View.INVISIBLE);
				Log.e(TAG, origin, e);
				validText.setText(e.getMessage());

			}
		});
	}

	/**
	 * show a wait message
	 * 
	 * @param id
	 */
	private void waitMessage(final int id) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				progressBar.setVisibility(View.VISIBLE);
				loadText.setVisibility(View.VISIBLE);
				loadText.setText(id);
			}
		});

	}

	/**
	 * Hide the wait message
	 */
	private void discardWait() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				progressBar.setVisibility(View.INVISIBLE);
				loadText.setVisibility(View.INVISIBLE);

			}
		});
	}

	/**
	 * Show a message when datas are load
	 */
	private void datasLoad() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				loadText.setText(R.string.datas_load);
				loadText.setVisibility(View.VISIBLE);

				buttonCheck.setEnabled(true);
			}
		});
	}

	/**
	 * Show a the message of validate code
	 */
	private void validateCode(final int string) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				validText.setText(string);
			}
		});
	}

}
