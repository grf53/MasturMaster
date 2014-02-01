package com.example.masturtest;

import android.os.AsyncTask;

public class TimeCheckThread extends AsyncTask<Void, Integer, Void> {

	private int limitTime;
	
	private long pastTime;
	private long currentTime;
	
	private boolean measureDone;
	
	public TimeCheckThread(int limitTime) {
		this.limitTime = limitTime;
		pastTime = 0;
		currentTime = 0;
		measureDone = false;
	}

	public long getTime() {
		return currentTime - pastTime;
	}
	
	public boolean isMeasureDone() {
		return measureDone;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		currentTime = pastTime = System.currentTimeMillis();
		
		while(currentTime - pastTime < limitTime)
			currentTime = System.currentTimeMillis();
		
		return null;
	}
		
	@Override
	protected void onPostExecute(Void result) {
		measureDone = true;
		super.onPostExecute(result);
	}
}