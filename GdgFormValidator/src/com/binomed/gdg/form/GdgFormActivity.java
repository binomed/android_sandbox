package com.binomed.gdg.form;

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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.binomed.gdg.form.asynctask.AsyncTaskDriveForm;
import com.binomed.gdg.form.asynctask.AsyncTaskDriveForm.CallBackDriveForm;
import com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm;
import com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm.CallBackSpreadsheetForm;
import com.binomed.gdg.form.asynctask.AsyncTaskWorksheet;
import com.binomed.gdg.form.asynctask.AsyncTaskWorksheet.CallBackWorksheetForm;
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
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;

/**
 * @author Jef
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
	private static final String PREF_KEY_FILE = "fileName";
	/**
	 * Settings key for the codes to use for validation
	 */
	private static final String PREF_KEY_CODES = "codes";

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
		this.activity = this;
		this.handler = new Handler();

		settings = getPreferences(MODE_PRIVATE);
		accountName = settings.getString(PREF_KEY_ACCOUNT, null);
		formId = settings.getString(PREF_KEY_FILE, null);

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
					gotAccount(accounts[which]);
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

		Account[] accounts = accountManager.getAccountsByType("com.google");
		if (accountName == null) {
			if (accounts.length > 1) {
				showDialog(DIALOG_ACCOUNTS);
			} else if (accounts.length == 1) {
				gotAccount(accounts[0]);
			}
		} else {
			for (Account account : accounts) {
				if (Objects.equal(account.name, this.accountName)) {
					gotAccount(account);
					break;
				}
			}
		}
	}

	/**
	 * Called when an account has been selected
	 * 
	 * @param account
	 */
	private void gotAccount(final Account account) {
		this.accountName = account.name;
		this.accountNameText.setText(this.accountName);
		Editor editor = settings.edit();
		editor.putString(PREF_KEY_ACCOUNT, accountName);
		editor.commit();
		accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
			public void run(AccountManagerFuture<Bundle> future) {
				try {
					// If the user has authorized your application to
					// use the tasks API
					// a token is available.
					String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
					GdgFormActivity.this.token = token;
					GdgFormActivity.this.credential = getCredentialGoogle(token);

					initServices();

					// Now you can use the Tasks API...
					// useDriveAPI();
					useSpreadsheetAPI();
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

		asyncTaskDriveForm = new AsyncTaskDriveForm(this, driveService);
		asyncTaskSpreadsheetForm = new AsyncTaskSpreadsheetForm(this, spreadSheetService);
		asyncTaskWorkSheet = new AsyncTaskWorksheet(this, spreadSheetService);

	}

	/**
	 * 
	 */
	private void useDriveAPI() {
		asyncTaskDriveForm.execute();
	}

	/**
	 * 
	 */
	private void useSpreadsheetAPI() {
		asyncTaskSpreadsheetForm.execute();
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
	private void gotForm(SpreadsheetEntry entry) {
		asyncTaskWorkSheet.setSpreadSheetEntry(entry);
		asyncTaskWorkSheet.execute();

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
			// TODO handle scan result
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
		// TODO load

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskDriveForm.CallBackDriveForm#driveError(java.lang.Exception)
	 */
	@Override
	public void driveError(Exception e) {
		// TODO show error

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
		// TODO load

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskSpreadsheetForm.CallBackSpreadsheetForm#spreadsheetError(java.lang.Exception)
	 */
	@Override
	public void spreadsheetError(Exception e) {
		// TODO show error

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
		// Iterate through each row, printing its cell values.
		for (ListEntry row : rows.getEntries()) {
			// Print the first column's cell value
			Log.i(TAG, row.getTitle().getPlainText() + "\t");
			// Iterate over the remaining columns, and print each cell value
			for (String tag : row.getCustomElements().getTags()) {
				Log.i(TAG, row.getCustomElements().getValue(tag) + "\t");
			}
			Log.i(TAG, "");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskWorksheet.CallBackWorksheetForm#worksheetStartSearchWorkSheet()
	 */
	@Override
	public void worksheetStartSearchWorkSheet() {
		// TODO load

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.binomed.gdg.form.asynctask.AsyncTaskWorksheet.CallBackWorksheetForm#worksheetError(java.lang.Exception)
	 */
	@Override
	public void worksheetError(Exception e) {
		// TODO show error

	}

}
