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
	TextView tvTimes = null;
	TextView tvTime = null;
	TextView tvDPM = null;
		
	int	Times = 0;
	int[] pastAccel = {0,0,0,0,0};
	long pastTime = 0, presentTime = 0;
	
	 PowerManager mPm;
     WakeLock mWakeLock;
	
	// 센서 관리자
	SensorManager sm = null;
	// 가속도 센서
	Sensor accSensor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		tvAX = (TextView) findViewById(R.id.tvAX);
		tvAY = (TextView) findViewById(R.id.tvAY);
		tvAZ = (TextView) findViewById(R.id.tvAZ);
		tvTimes = (TextView) findViewById(R.id.tvTimes);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvDPM = (TextView) findViewById(R.id.tvDPM);
		
		// SensorManager 인스턴스를 가져옴
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		// 가속도 센서
		accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		pastTime = System.currentTimeMillis();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// 가속도 센서 리스너 오브젝트를 등록
		sm.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 센서에서 이벤트 리스너 분리
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
			tvAY.setText(String.valueOf(event.values[1]));
			tvAZ.setText(String.valueOf(event.values[2]));
			pastAccel[0] = pastAccel[1];
			pastAccel[1] = pastAccel[2];
			pastAccel[2] = pastAccel[3];
			pastAccel[3] = pastAccel[4];
			pastAccel[4] = (int) Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2]);
			
			Log.d("gasokdo", ""+pastAccel[2]);
			presentTime = System.currentTimeMillis();
			tvTime.setText((presentTime - pastTime)/1000+"."+(presentTime - pastTime)%1000+" s");
			tvDPM.setText(String.format("%.3f", (60000*(Times/2)/((double)(presentTime - pastTime))))+" 딸/min");
			if(pastAccel[0]<pastAccel[1] && pastAccel[1]<pastAccel[2] && pastAccel[2]>pastAccel[3] && pastAccel[3]>pastAccel[4]){
				Log.d("_Times", ""+Times);
				Times++;
				tvTimes.setText((Times/2)+" times");				
			}
			break;
		}
	}
}
