package com.example.masturtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class ResultActivity extends Activity {

	Button btnGoToMain = null;
	ImageView needle = null;
	ResultScoreView scoreView = null;
	Animation animation;
	
	double DDR = 0;
	int width, height;
	float rotate = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Intent intent = getIntent();
		DDR = intent.getExtras().getDouble("ddrpm");
		rotate = (float) (DDR/270*180);
		
		btnGoToMain = (Button) findViewById(R.id.btnGoToMainMenu);
		needle = (ImageView) findViewById(R.id.needle);
		scoreView = (ResultScoreView) findViewById(R.id.measure_nemo);
		
		scoreView.setValue((int)DDR);

		Matrix mat = new Matrix();
		mat.setRotate(rotate);
		
		needle.setImageMatrix(mat);
		
		animation = new RotateAnimation(0, rotate, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1300);
		animation.setFillAfter(true);
		animation.setInterpolator(new BounceInterpolator());
		needle.setAnimation(animation);
		
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
