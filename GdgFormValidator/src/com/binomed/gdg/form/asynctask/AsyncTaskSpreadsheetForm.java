package com.binomed.gdg.form.asynctask;

import java.net.URL;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;

public class AsyncTaskSpreadsheetForm extends AsyncTask<Void, Void, List<SpreadsheetEntry>> {

	private static final String TAG = "AsyncTaskSpreadsheetForm";
	private static final String FEED = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

	private final CallBackSpreadsheetForm _callBack;
	private final SpreadsheetService _service;

	public AsyncTaskSpreadsheetForm(CallBackSpreadsheetForm _callBack, SpreadsheetService _service) {
		super();
		this._callBack = _callBack;
		this._service = _service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(final List<SpreadsheetEntry> listSpreadSheets) {
		_callBack.spreadsheetListForm(listSpreadSheets);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		_callBack.spreadsheetStartSearchFiles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected List<SpreadsheetEntry> doInBackground(Void... params) {

		try {
			URL SPREADSHEET_FEED_URL = new URL(FEED);

			// Make a request to the API and get all spreadsheets.
			SpreadsheetFeed feed = _service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
			List<SpreadsheetEntry> spreadsheets = feed.getEntries();
			return spreadsheets;

		} catch (Exception e) {
			_callBack.spreadsheetError(e);
			Log.e(TAG, e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * @author Jef CallBack interface for managing response to UI
	 */
	public static interface CallBackSpreadsheetForm {

		void spreadsheetListForm(List<SpreadsheetEntry> listSpreadSheets);

		void spreadsheetStartSearchFiles();

		void spreadsheetError(Exception e);

	}
}
