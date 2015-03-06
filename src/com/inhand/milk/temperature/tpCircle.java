package com.inhand.milk.temperature;

import java.security.PrivilegedActionException;

import javax.security.auth.PrivateCredentialPermission;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.text.format.Time;
import android.view.View;
import android.widget.Toast;

public class tpCircle extends View{

	private float wr ;//pix
	private float paint_width  = 2;//sp
	private float width ;//pix
	private float  r;//pix
	private float x1,x2,y1,y2;
	
	private float TextSize = 12;//sp

	
	public tpCircle(Context context,float rr,float line) {
		super(context);
		
		float scale = this.getResources().getDisplayMetrics().density;
		paint_width = paint_width *scale;
		TextSize = TextSize * scale;
		
		
		wr = rr;
		width = rr*4 +line;
		r = (wr - paint_width/2);
		x1 = wr;
		y1 = wr;
		x2 = width - wr;
		y2 = wr;
		
		max_score1 = 39;
		max_sweepAngle1 = (float)max_score1/60 * 360;
		max_score2 = 33;
		max_sweepAngle2 = (float)max_score2 /60 *360;
		
		sweepAngle1 = max_sweepAngle1;
		sweepAngle2 = max_sweepAngle2;
		num1 = String.valueOf(max_score1);
		num2 = String.valueOf(max_score2);
		// TODO Auto-generated constructor stub
	}
	
	
	private void drawText(Canvas canvas){
		
		Paint paint = new Paint();
		float numsize = (float)2*wr*13/(22+26);
		paint.setColor(Color.WHITE);
		paint.setTextSize(numsize);
		paint.setAntiAlias(true);
		
		String text = "C°";
		String text11 = "饮奶开始的温度";
		String text22 = "饮奶结束的温度";
		canvas.drawText(num1, x1-numsize*23/40, y1+numsize/4, paint);
		canvas.drawText(num2, x2-numsize*23/40, y2+numsize/4, paint);
		paint.setTextSize(numsize/2);
		canvas.drawText(text, x1+numsize*23/40, y1+numsize/4, paint);
		canvas.drawText(text, x2+numsize*23/40, y2+numsize/4, paint);
		
		paint.setTextSize(TextSize);
		float  offsetx = TextSize*text11.length()/2;
		canvas.drawText(text11, x1 - offsetx, y1+wr+wr*12/30, paint);
		canvas.drawText(text22, x2 - offsetx, y2+wr+wr*12/30, paint);
		
		
		
	}
	
	private void drawBackground(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.rgb(0x20,0x92,0x97));
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(paint_width);
		paint.setAlpha(0x5f);
		RectF rectf =  new RectF(x1 - r,y1 - r,x1 + r,y1 + r);
		canvas.drawArc(rectf,0,360,false, paint);
		rectf =  new RectF(x2 - r, y2 - r, x2 + r, y2 +r);
		canvas.drawArc(rectf,0,360,false, paint);
		canvas.drawLine(2*wr, wr, width-2*wr, wr, paint);
	}
	
	protected void onDraw(Canvas canvas){
		drawBackground(canvas);
		drawText(canvas);
		drawCircle(canvas);
		
	}
	
	private void drawCircle(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.rgb(0xee, 0xf9, 0x66));
		paint.setStyle(Style.STROKE); 
		paint.setAntiAlias(true);
		paint.setStrokeWidth(paint_width);
		RectF rectf =  new RectF(x1-r,y1-r,x1+r,y1+r);
		canvas.drawArc(rectf,270,sweepAngle1,false, paint);
		
		
		paint.setColor(Color.rgb(0x00, 0xf5, 0xfe));
	    rectf =  new RectF(x2-r,y2-r,x2+r,y2+r);
		canvas.drawArc(rectf,270,sweepAngle2,false, paint);
		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension((int)width, (int)( wr+wr+wr*14/30 ));//增加一点。。实际效果
	}
	
	private  float sweepAngle1,max_sweepAngle1,sweepAngle2,max_sweepAngle2;
	private  int score1 ,max_score1,score2,max_score2;
	private  float  score_float1,score_float2;
	Handler handler ;
	private int  time;
	String num1,num2;
	
	
	public void start(){
		handler = new Handler();
		sweepAngle1 = 0;
		score1 = 0;
		score_float1 = 0 ;
		time = 100;
		sweepAngle2 = 0;
		score2 = 0;
		score_float2 = 0 ;
	
		Runnable runnabel = new Runnable() {
		
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 num1 = String.valueOf(score1);
				 num2 = String.valueOf(score2);
				invalidate();
			//	Toast.makeText(getContext(), String.valueOf(score1), Toast.LENGTH_SHORT).show();
				if (sweepAngle1 == max_sweepAngle1 && sweepAngle2 == max_sweepAngle2)
					handler.removeCallbacks(this);
				
				else 
					handler.postDelayed(this, 2);
				    
			 
				 sweepAngle1 += max_sweepAngle1 / (time/2);
				 sweepAngle2 += max_sweepAngle1 / (time/2);
			     score_float1 =  score_float1 + (float)(max_score1)/(time/2);
			     score_float2 =  score_float2 + (float)(max_score1)/(time/2);
			     score1 = (int)score_float1;
			     score2 = (int)score_float2;
			     if (sweepAngle1 > max_sweepAngle1 || score1 > max_score1){
						sweepAngle1 = max_sweepAngle1;
						score1 = max_score1;
				 }
				
			     if (sweepAngle2 > max_sweepAngle2 || score2 > max_score2){
						sweepAngle2 = max_sweepAngle2;
						score2 = max_score2;
				 }
				}
		};
		handler.postDelayed(runnabel, 2);
		
	}
	
	
	
	

	
}
