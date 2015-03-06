package com.inhand.milk.temperature;

import java.security.PrivilegedActionException;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.commons.logging.Log;

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


public class milkCircle extends View{

	private float wr ;//pix
	private float paint_width  = 2;//sp
	private float width ;//pix
	private float  r;//pix
	private float x,y;
	private float lineToCircle  = 10;//sp
	private float lineSweepAngleOffset ;//单位度
	

	private int   currentAmount=200;
	private int   adviseAmount =300;
	private float BottomNumSize = 20 ;//sp
	private Paint paint =new Paint();
	
	public milkCircle(Context context,float rr) {
		super(context);	
		float scale = this.getResources().getDisplayMetrics().density;
		paint_width = paint_width *scale;
		//BottomNumSize= BottomNumSize * scale;
		
		
		lineToCircle = lineToCircle *scale;
		
		wr = rr;
		width = rr*2 + lineToCircle *2; 
		r = (wr - paint_width/2);
		x = wr + lineToCircle;
		y = wr + lineToCircle;
		
		BottomNumSize = 2*wr/8;
		
		max_score = (int)((float)currentAmount/adviseAmount*100);
		num = String.valueOf(max_score)+"%";
		paint.setTextSize(BottomNumSize);
		lineSweepAngleOffset =  (float)  (Math.atan( paint.measureText(num)/2/(r + lineToCircle) ) /Math.PI* 180  +2);//加2度有点空隙
		max_sweepAngle = (float)currentAmount/adviseAmount * (360 - 2*lineSweepAngleOffset);
		
		sweepAngle = max_sweepAngle;
		
		//Toast.makeText(context, String.valueOf(Math.atan( paint.measureText(num)/2/(r + lineToCircle) ) * 180),Toast.LENGTH_SHORT).show();
		// TODO Auto-generated constructor stub
	}
	
	
	private void drawText(Canvas canvas){
		
		Paint paint = new Paint();
		float numsize = 2*wr/10;
		paint.setColor(Color.WHITE);
		paint.setTextSize(numsize);
		paint.setAntiAlias(true);
		
		String textup = "建议量: "+String.valueOf(adviseAmount)+"ml";
		String textdown = "当前量: "+String.valueOf(currentAmount)+"ml"; 
		canvas.drawText(textup, x - paint.measureText(textup)/2, y - numsize/3, paint);
		canvas.drawText(textdown, x - paint.measureText(textdown)/2, y + numsize/3 + numsize, paint);
	
		
	}
	
	private void drawBackground(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.rgb(0x20,0x92,0x97));
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(paint_width);
		RectF rectf =  new RectF(x - r,y - r,x + r,y + r);
		canvas.drawArc(rectf,0,360,false, paint);
		
	}
	
	protected void onDraw(Canvas canvas){
		drawBackground(canvas);
		drawText(canvas);
		//drawCircle(canvas);
		drawDoubleLine(canvas);
		drawBottomText(canvas);
		
	}
	
	private void drawDoubleLine(Canvas canvas){
		float rr;
		Paint paint = new Paint();
		paint.setColor(Color.rgb(0x20,0x92,0x97));
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(paint_width);
		
		rr = r+ lineToCircle;
		RectF rectf =  new RectF(x - rr,y - rr,x + rr,y + rr);	
		canvas.drawArc(rectf, 90 - lineSweepAngleOffset, -sweepAngle/2,false, paint);
		canvas.drawArc(rectf, 90 + lineSweepAngleOffset, sweepAngle/2,false, paint);
		
	}
	private void drawBottomText(Canvas canvas){
		float xx,yy;
		if( !num.endsWith("%"))
			num = num +"%";
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(paint_width);
		paint.setTextSize(BottomNumSize);
		xx = x - paint.measureText(num)/2;
		yy = y + (r + lineToCircle) *(float) Math.cos(lineSweepAngleOffset/180 *Math.PI) + BottomNumSize/2;
		canvas.drawText(num, xx, yy, paint);
		
	}
	private void drawCircle(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.rgb(0xee, 0xf9, 0x66));
		paint.setStyle(Style.STROKE); 
		paint.setAntiAlias(true);
		paint.setStrokeWidth(paint_width);
		RectF rectf =  new RectF(x-r,y-r,x+r,y+r);
		canvas.drawArc(rectf,90,sweepAngle,false, paint);	
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension((int)( width ), (int)( (wr+wr+wr*14/30) + lineToCircle));//增加一点。。实际效果
	}
	
	private  float sweepAngle,max_sweepAngle;
	private  int score ,max_score;
	private  float  score_float;
	Handler handler ;
	private int  time;
	String num;
	
	
	public void start(){
		handler = new Handler();
		sweepAngle = 0;
		score = 0;
		score_float = 0;
		time = 100;
		Runnable runnabel = new Runnable() {
		
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 num = String.valueOf(score);
				invalidate();
				if (sweepAngle== max_sweepAngle )
					handler.removeCallbacks(this);
				
				else 
					handler.postDelayed(this, 2);
				    
			 
				 sweepAngle += max_sweepAngle / (time/2);
			     score_float=  score_float + (float)(max_score)/(time/2);	     
			     score = (int)score_float;
			     if (sweepAngle > max_sweepAngle || score > max_score){
						sweepAngle = max_sweepAngle;
						score = max_score;
				 }
				
				}
		};
		handler.postDelayed(runnabel, 2);	
	}
	
}
