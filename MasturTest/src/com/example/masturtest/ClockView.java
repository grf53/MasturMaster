package com.example.masturtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ClockView extends RelativeLayout implements Runnable {

	long time = 10000;
	
	private Thread t = null;
	
	ImageView clock;
	SectorView sector;
	
	public ClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		sector = new SectorView(context);
		clock = new ImageView(context);
		clock.setImageResource(R.drawable.background_timer);
		clock.setAdjustViewBounds(true);
		
		addView(sector);
		addView(clock);
	}
	
	private class SectorView extends View {	
		
		private RectF r = null;
		private Paint p;
		
		float arc = 0;
		
		public SectorView(Context context) {
			super(context);
			
			p = new Paint();
			p.setAntiAlias(true);
			setColor(0x4c, 0x85, 0xb4);
		}
		
		public void setColor(int r, int g, int b) {
			
			p.setColor(Color.rgb(r, g, b));
		}

		public void onDraw(Canvas canvas) {
			
			if(r == null) {

				float width = getWidth();
				float margin = (width + 16)/18;
				
				r = new RectF();
				
				r.left = margin;
				r.right = width - margin;
				r.top = margin;
				r.bottom = width - margin;
			}
				
			canvas.drawArc(r, -90, arc, true, p);
			
			invalidate();
		}
	}

	public void setColor(int r, int g, int b) {
		
		sector.setColor(r, g, b);
	}
	
	public void setTime(long time) {
		
		this.time = time;
	}
	
	public void startClock() {
		
		t = new Thread(this, "clock");
		t.start();
	}
	
	@Override
	public void run() {
		
		long before, after;
		
		before = after = System.currentTimeMillis();
		
		while(after - before < time) {
			
			after = System.currentTimeMillis();
			sector.arc = 360*(after - before)/time;
		}
	}
}