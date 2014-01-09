package com.example.masturtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	TextView tvResult = null;
	Button btnGoToMain = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Intent intent = getIntent();
		
		tvResult = (TextView) findViewById(R.id.tvResult);
		btnGoToMain = (Button) findViewById(R.id.btnGoToMainMenu);
		
		//tvResult.setText(String.format("%.3f", ddrpm) + " µþ/min");
		tvResult.setText(intent.getExtras().getString("ddrpm"));
		
		
		btnGoToMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tempIntent = new Intent(ResultActivity.this, ChoiceActivity.class);
				startActivity(tempIntent);
				finish();
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
