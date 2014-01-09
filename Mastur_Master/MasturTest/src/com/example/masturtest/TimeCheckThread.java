package com.example.masturtest;

import android.os.AsyncTask;

public class TimeCheckThread extends AsyncTask<Void, Integer, Void> {

	int time;
	
	long pastTime;
	long currentTime;
	
	public TimeCheckThread(int time) {
		
		this.time = time;
	}

	public long getTime() {
		// TODO Auto-generated method stub
		return currentTime - pastTime;
	}

	@Override
	protected Void doInBackground(Void... params) {

		currentTime = pastTime = System.currentTimeMillis();
		
		while(currentTime - pastTime < time)
			currentTime = System.currentTimeMillis();
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		
		GameActivity.nowMeasuring = false;
	}
}