package com.binomed.gdg.form.asynctask;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;

/**
 * @author Jef AsyncTask which call Drive API
 */
public class AsyncTaskDriveForm extends AsyncTask<Void, Void, FileList> {

	private static final String TAG = "AsyncTaskDriveForm";
	private static final String MIME_TYPE = "mimeType='application/vnd.google-apps.form'";

	private final CallBackDriveForm _callBack;
	private final Drive _service;

	public AsyncTaskDriveForm(CallBackDriveForm callBack, Drive service) {
		this._callBack = callBack;
		this._service = service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(final FileList list) {
		_callBack.driveListForm(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		_callBack.driveStartSearchFiles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected FileList doInBackground(Void... params) {
		try {
			com.google.api.services.drive.Drive.Files.List request = _service.files().list() //
					.setQ(MIME_TYPE)//
			;
			final FileList list = request.execute();
			return list;

		} catch (IOException e) {
			_callBack.driveError(e);
			Log.e(TAG, e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * @author Jef CallBack interface for managing response to UI
	 */
	public static interface CallBackDriveForm {

		void driveListForm(FileList listFile);

		void driveStartSearchFiles();

		void driveError(Exception e);

	}

}
