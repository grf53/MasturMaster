package com.example.masturtest;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {

	TextView tvAX = null;
	TextView tvAY = null;
	TextView tvAZ = null;
	TextView tvTimes = null;
	TextView tvTime = null;
	TextView tvDPM = null;
	Button btnMeasure = null;

	int Times = 0;
	int[] pastAccel = { 0, 0, 0, 0, 0 };
	long pastTime = 0, presentTime = 0;
	int limitTime = 0;

	PowerManager mPm;
	WakeLock mWakeLock;

	SensorManager sm = null;
	Sensor accSensor = null;
	SensorEventListener sel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		
		tvAX = (TextView) findViewById(R.id.tvAX);
		tvAY = (TextView) findViewById(R.id.tvAY);
		tvAZ = (TextView) findViewById(R.id.tvAZ);
		tvTimes = (TextView) findViewById(R.id.tvTimes);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvDPM = (TextView) findViewById(R.id.tvDPM);
		btnMeasure = (Button) findViewById(R.id.btnMeasure);
		
		limitTime = intent.getExtras().getInt("eTime");

		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sel = new SensorEventListener() {
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				tvAX.setText(String.valueOf(event.values[0]));
				tvAY.setText(String.valueOf(event.values[1]));
				tvAZ.setText(String.valueOf(event.values[2]));
				pastAccel[0] = pastAccel[1];
				pastAccel[1] = pastAccel[2];
				pastAccel[2] = pastAccel[3];
				pastAccel[3] = pastAccel[4];
				pastAccel[4] = (int) Math.sqrt(event.values[0]
						* event.values[0] + event.values[1] * event.values[1]
						+ event.values[2] * event.values[2]);

				Log.d("gasokdo", "" + pastAccel[2]);
				presentTime = System.currentTimeMillis();
				tvTime.setText((presentTime - pastTime) / 1000 + "." + (presentTime - pastTime) % 1000 + " s");
				tvDPM.setText(String.format("%.3f", (60000 * (Times / 2) / ((double) (presentTime - pastTime)))) + " 딸/min");

				if (pastAccel[0] < pastAccel[1] && pastAccel[1] < pastAccel[2] && pastAccel[2] > pastAccel[3] && pastAccel[3] > pastAccel[4]) {
					Log.d("_Times", "" + Times);
					Times++;
					tvTimes.setText((Times / 2) + " times");
				}

				if (presentTime - pastTime >= limitTime) {
					measureComplete();
				}
			}
		};

	}

	private void measureComplete() {
		btnMeasure.setEnabled(true);
		sm.unregisterListener(sel);
	}

	@Override
	public void onResume() {
		super.onResume();
		btnMeasure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pastTime = System.currentTimeMillis();
				Times = 0;
				sm.registerListener(sel, accSensor,
						SensorManager.SENSOR_DELAY_GAME);
				btnMeasure.setEnabled(false);
				tvTimes.setText("0 times");
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		sm.unregisterListener(sel);
	}
}
