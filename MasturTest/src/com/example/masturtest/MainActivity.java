package com.example.masturtest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	// 가속도 센서값을 출력하기 위한 TextView
	TextView tvAX = null;
	TextView tvAY = null;
	TextView tvAZ = null;
	// 방향 센서값을 출력하기 위한 TextView
	TextView tvOX = null;
	TextView tvOY = null;
	TextView tvOZ = null;
	TextView tvTimes = null;
	
	int	Times = 0;
	float[] pre = {0,0,0};
	
	 PowerManager mPm;
     WakeLock mWakeLock;
	
	// 센서 관리자
	SensorManager sm = null;
	// 가속도 센서
	Sensor accSensor = null;
	// 방향 센서
	Sensor oriSensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		tvAX = (TextView) findViewById(R.id.tvAX);
		tvAY = (TextView) findViewById(R.id.tvAY);
		tvAZ = (TextView) findViewById(R.id.tvAZ);
		tvOX = (TextView) findViewById(R.id.tvOX);
		tvOY = (TextView) findViewById(R.id.tvOY);
		tvOZ = (TextView) findViewById(R.id.tvOZ);
		tvTimes = (TextView) findViewById(R.id.tvTimes);
		// SensorManager 인스턴스를 가져옴
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		// 가속도 센서
		accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// 방향 센서
		oriSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// 가속도 센서 리스너 오브젝트를 등록
		sm.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
		// 방향 센서 리스너 오브젝트를 등록
		sm.registerListener(this, oriSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 센서에서 이벤트 리스너 분리
		sm.unregisterListener(this);
		sm.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			tvAX.setText(String.valueOf(event.values[0]));
			Log.d("accX",String.valueOf(event.values[0]));
			tvAY.setText(String.valueOf(event.values[1]));
			Log.d("accY",String.valueOf(event.values[1]));
			tvAZ.setText(String.valueOf(event.values[2]));
			Log.d("accZ",String.valueOf(event.values[2]));
			pre[0] = pre[1];
			pre[1] = pre[2];
			pre[2] = (float) Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2]);

			Log.d("gasokdo", ""+pre[1]);
			if(pre[1]>20.0 && pre[0] < pre[1] && pre[1]>pre[2]){
				Times++;
				tvTimes.setText(Times+" times");
			}
			break;
		case Sensor.TYPE_GYROSCOPE:
			tvOX.setText(String.valueOf(event.values[0]));
			tvOY.setText(String.valueOf(event.values[1]));
			tvOZ.setText(String.valueOf(event.values[2]));
			break;
		}
	}

}