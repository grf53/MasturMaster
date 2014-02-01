package com.example.masturtest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ResultScoreView extends RelativeLayout {

	ImageView topBarView;
	ImageView leftBarView;
	ImageView coverView;
	ImageView scoreView0;
	ImageView scoreView1;
	ImageView scoreView2;
	
	private int DDR;
	
	public ResultScoreView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}

	public ResultScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		topBarView = new ImageView(context);
		leftBarView = new ImageView(context);
		coverView = new ImageView(context);
		scoreView0 = new ImageView(context);
		scoreView1 = new ImageView(context);
		scoreView2 = new ImageView(context);
		
		//setting topBarView
		topBarView.setImageResource(R.drawable.topbar);
		topBarView.setId(1);
		RelativeLayout.LayoutParams topBarParams = new RelativeLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		topBarParams.addRule(ALIGN_PARENT_TOP);
		
		//setting leftBarView
		leftBarView.setImageResource(R.drawable.leftbar);
		leftBarView.setId(2);
		RelativeLayout.LayoutParams leftBarParams = new RelativeLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftBarParams.addRule(BELOW, 1);
		leftBarParams.addRule(ALIGN_PARENT_LEFT);
		
		addView(topBarView, topBarParams);
		addView(leftBarView, leftBarParams);
	}

	public ResultScoreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void setValue(int value)
	{
		int num0, num1, num2;
		DDR = value;
		
		num0 = DDR/100;
		num1 = (DDR/10)%10;
		num2 = DDR%10;
		coverView.setImageResource(R.drawable.cover);
		
		scoreView0.setImageResource(getResID(num0));
		scoreView0.setId(3);
		RelativeLayout.LayoutParams scoreView0Params = new RelativeLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		scoreView0Params.addRule(BELOW, 1);
		scoreView0Params.addRule(RIGHT_OF, 2);
		
		scoreView1.setImageResource(getResID(num1));
		scoreView1.setId(4);
		RelativeLayout.LayoutParams scoreView1Params = new RelativeLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		scoreView1Params.addRule(BELOW, 1);
		scoreView1Params.addRule(RIGHT_OF, 3);
		
		scoreView2.setImageResource(getResID(num2));
		scoreView2.setId(5);
		RelativeLayout.LayoutParams scoreView2Params = new RelativeLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		scoreView2Params.addRule(BELOW, 1);
		scoreView2Params.addRule(RIGHT_OF, 4);
		
		addView(scoreView0, scoreView0Params);
		addView(scoreView1, scoreView1Params);
		addView(scoreView2, scoreView2Params);
		addView(coverView);
	}
	
	private int getResID(int value)
	{
		switch(value)
		{
		case 0:
			return R.drawable.num0;
		case 1:
			return R.drawable.num1;
		case 2:
			return R.drawable.num2;
		case 3:
			return R.drawable.num3;
		case 4:
			return R.drawable.num4;
		case 5:
			return R.drawable.num5;
		case 6:
			return R.drawable.num6;
		case 7:
			return R.drawable.num7;
		case 8:
			return R.drawable.num8;
		case 9:
			return R.drawable.num9;
		}
		
		return 0;
	}
}


