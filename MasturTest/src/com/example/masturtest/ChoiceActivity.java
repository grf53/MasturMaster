package com.example.masturtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChoiceActivity extends Activity {

	Button btn1 = null;
	Button btn2 = null;
	Button btn3 = null;
	
	int choice = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
		
		btn1 = (Button) findViewById(R.id.btn0);
		btn2 = (Button) findViewById(R.id.btn1);
		btn3 = (Button) findViewById(R.id.btn2);
		
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ChoiceActivity.this, GameActivity.class).putExtra("eTime", 10000));
				finish();
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ChoiceActivity.this, GameActivity.class).putExtra("eTime", 30000));
				finish();
			}
		});
		
		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ChoiceActivity.this, GameActivity.class).putExtra("eTime", 120000));
				finish();
			}
		});
	}
}