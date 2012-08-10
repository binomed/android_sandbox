package com.binomed.gdg.form.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.base.Objects;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

public class AsyncTaskWorksheet extends AsyncTask<Void, Void, ListFeed> {

	private static final String TAG = "AsyncTaskWorksheet";
	private static final String WORKSHEET_NAME = "Codes";

	private final CallBackWorksheetForm _callBack;
	private final SpreadsheetService _service;
	private SpreadsheetEntry spreadSheetEntry;

	public AsyncTaskWorksheet(CallBackWorksheetForm _callBack, SpreadsheetService _service) {
		super();
		this._callBack = _callBack;
		this._service = _service;
	}

	public void setSpreadSheetEntry(SpreadsheetEntry spreadSheetEntry) {
		this.spreadSheetEntry = spreadSheetEntry;
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
			WorksheetEntry workSheet = null;
			for (WorksheetEntry workSheetEntry : spreadSheetEntry.getWorksheets()) {
				if (Objects.equal(workSheetEntry.getTitle().getPlainText(), WORKSHEET_NAME)) {
					workSheet = workSheetEntry;
					break;
				}
			}

			if (workSheet != null) {
				ListFeed listFeed = _service.getFeed(workSheet.getListFeedUrl(), ListFeed.class);
				return listFeed;
			}
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
