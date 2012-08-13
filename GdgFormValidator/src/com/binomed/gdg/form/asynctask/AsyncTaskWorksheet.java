package com.binomed.gdg.form.asynctask;

import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListFeed;

public class AsyncTaskWorksheet extends AsyncTask<Void, Void, ListFeed> {

	private static final String TAG = "AsyncTaskWorksheet";

	private final CallBackWorksheetForm _callBack;
	private final SpreadsheetService _service;
	private URL urlFeed;

	public AsyncTaskWorksheet(CallBackWorksheetForm _callBack, SpreadsheetService _service) {
		super();
		this._callBack = _callBack;
		this._service = _service;
	}

	public void setUrlFedd(URL urlFeed) {
		this.urlFeed = urlFeed;
	}

	@Override
	protected void onPostExecute(final ListFeed rows) {
		_callBack.worksheetListForm(rows);
	}

	@Override
	protected void onPreExecute() {
		_callBack.worksheetStartSearchWorkSheet();
	}

	@Override
	protected ListFeed doInBackground(Void... params) {

		try {

			ListFeed listFeed = _service.getFeed(urlFeed, ListFeed.class);
			return listFeed;
		} catch (Exception e) {
			_callBack.worksheetError(e);
			Log.e(TAG, e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * @author Jef CallBack interface for managing response to UI
	 */
	public static interface CallBackWorksheetForm {

		void worksheetListForm(ListFeed rows);

		void worksheetStartSearchWorkSheet();

		void worksheetError(Exception e);

	}
}
