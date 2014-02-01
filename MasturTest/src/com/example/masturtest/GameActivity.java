package com.example.masturtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {

	// static boolean nowMeasuring = false;

	boolean hasPaused = false;

	TextView tvAX = null;
	TextView tvAY = null;
	TextView tvAZ = null;
	TextView tvTimes = null;
	TextView tvTime = null;
	TextView tvDPM = null;
	Button btnMeasure = null;

	int Times = 0;
	int[] pastAccel = { 0, 0, 0, 0};
	long pastTime = 0, presentTime = 0;
	int limitTime = 0;
	double DPM = 0;

	SensorManager sm = null;
	Sensor accSensor = null;
	SensorEventListener sel = null;

	TimeCheckThread tct = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_game);

		tvAX = (TextView) findViewById(R.id.tvAX);
		tvAY = (TextView) findViewById(R.id.tvAY);
		tvAZ = (TextView) findViewById(R.id.tvAZ);
		tvTimes = (TextView) findViewById(R.id.tvTimes);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvDPM = (TextView) findViewById(R.id.tvDPM);
		btnMeasure = (Button) findViewById(R.id.btnMeasure);

		limitTime = getIntent().getExtras().getInt("eTime");

		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sel = new SensorEventListener() {

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				tvAX.setText(String.valueOf(event.values[0]));
				tvAY.setText(String.valueOf(event.values[1]));
				tvAZ.setText(String.valueOf(event.values[2]));
				pastAccel[0] = pastAccel[1];
				pastAccel[1] = pastAccel[2];
				pastAccel[2] = pastAccel[3];
				pastAccel[3] = (int) Math.abs(event.values[0]+event.values[1]+event.values[2]);

				tvTime.setText(tct.getTime() / 1000 + "."
						+ getStringTime(tct.getTime() % 1000) + " s");
				tvDPM.setText(String.format("%.3f",
						(60000 * (Times / 2) / ((double) tct.getTime())))
						+ " 딸/min");

				if (pastAccel[0] < pastAccel[1] &&
						pastAccel[1] < pastAccel[2] &&
						pastAccel[2] > pastAccel[3]	) {

					Times++;
					tvTimes.setText((Times / 2) + " times");
				}

				if (tct.isMeasureDone()) {
					DPM = (60000 * (Times / 2) / ((double) tct.getTime()));
					measureComplete();
				}
			}

			private String getStringTime(long time) {

				if (time > 99)
					return String.valueOf(time);
				else if (time > 9)
					return "0" + String.valueOf(time);
				else if (time > 0)
					return "00" + String.valueOf(time);
				else
					return "000";
			}
		};

		btnMeasure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Times = 0;
				btnMeasure.setEnabled(false);
				tvTimes.setText("0 times");

				sm.registerListener(sel, accSensor,
						SensorManager.SENSOR_DELAY_GAME);

				tct = new TimeCheckThread(limitTime);
				tct.execute();
			}
		});
		
	}

	private void measureComplete() {

		Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		btnMeasure.setEnabled(true);
		btnMeasure.setText("결과 확인하기");
		sm.unregisterListener(sel);

		vib.vibrate(1000);

		btnMeasure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(GameActivity.this, ResultActivity.class).putExtra("ddrpm", DPM));
				finish();
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();

		hasPaused = true;
		sm.unregisterListener(sel);
		tct.cancel(true);
	}

	public void onResume() {
		super.onResume();
		if (hasPaused) {
			new AlertDialog.Builder(GameActivity.this)
				.setMessage("게임이 비정상적으로 종료되어 앞으로 돌아갑니다.")
				.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							startActivity(new Intent(GameActivity.this, ChoiceActivity.class));
							finish();
						}
					})
				.show();
		}
	}
}