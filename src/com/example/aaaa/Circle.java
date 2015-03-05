
package com.example.aaaa;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Circle extends View{

	private float sweepAngle = 0;
	private float offset,scale;
	private Handler handler ;
	private Runnable runnabel;
	private int  time,max_score,score;
	private float  max_sweepAngle;
	private String num;
	private float  score_float;
	private int count,x,y,wr,r,textsize,width;
	
	
	private final float[] color_weight = {0, (float) 0.33, (float) 0.66, (float) 1.0};//一定不能有0.25、。。把他改成0.245
	private final  int[] rgb  = { 0x04, 0x98, 0xa2,  0x02, 0xb9, 0xb4,    0x05,0x7e,0x9b,   0x05, 0x7e, 0x9b} ;
	private int[]   color;
	private float[] color_weight_fix ;
	private int[]   rgb_fix;

	
	public Circle(Context context, int rr){
		super(context);
		time = 100;
		score = 10;
		num = String.valueOf(score);
		max_score = 75;
		max_sweepAngle = max_score*(float)3.6;
		
		x=rr;//圆心
		y=rr;//圆心
	    wr = rr;//半径 外圈
	    width = (int)((float)wr*2/366*5);//画圆的时候的笔的宽度
	    
	    
	    x = wr+width;//预留了一个width的长度
	    y = wr+width;
	    
		
		r = wr - width/2;
		scale = context.getResources().getDisplayMetrics().density;
		textsize = (int)( (float)wr*2*166/366);
		//textsize = r/2;//文字大小
		fixColor();
		sortWeight();
		//offset =(float)((double) Math.asin((double)width/2/r)/Math.PI*180);
	}
	
	

	private void background(Canvas canvas){
		Paint paint  = new Paint();
		paint.setColor(Color.rgb(0xe5, 0xe9, 0xe9));
		paint.setStyle(Style.STROKE); 
		paint.setAntiAlias(true);
		paint.setStrokeWidth(width);
		canvas.drawCircle(x, y, r, paint);
		
	    paint = new Paint();
		paint.setColor(Color.rgb(0xff, 0xff, 0xff));
		
		paint.setAntiAlias(true);
		canvas.drawCircle(x, y, r-width/2, paint);
		
		 paint = new Paint();
		 int tempr = (int) (wr*((float)312/366));
		 paint.setColor(Color.rgb(0xf7, 0xfd, 0xfc));
		 paint.setAntiAlias(true);
		 canvas.drawCircle(x, y, tempr, paint);
		 
		 
		 paint = new Paint();
		 int tempr2= (int) (wr*((float)266/366));
		 paint.setColor(Color.rgb(0xe6, 0xf8, 0xf5));
		 paint.setAntiAlias(true);
		 canvas.drawCircle(x, y, tempr2, paint);
		 
		
	}
	private void fixColor(){
		int i,tempi;
		boolean a;
		float temp,w;
		a =  false;
		color_weight_fix = new float[color_weight.length+2];
		rgb_fix  = new int[3*(color_weight.length+2)];
		for(i=0; i<color_weight.length+2; i++){
			if(a ==false)
				temp = color_weight[i] - (float)0.25;
			else if(i == color_weight.length+2-1){
				temp = color_weight[i-2]-(float)0.001-(float)0.25;
			}
			else
				temp = color_weight[i-2]-(float)0.25;
			if(temp < 0 ){
				color_weight_fix[i] = temp+1;
				rgb_fix[i*3] = rgb[i*3];
				rgb_fix[i*3+1] = rgb[i*3+1];
				rgb_fix[i*3+2] = rgb[i*3+2];
			}
			else if(temp >= 0 && a ==false){
				w =  color_weight[i] - color_weight[i-1];
				w =  ((float)0.25 - color_weight[i-1])/w;
				for(tempi = i ;tempi <i+2 ;tempi++){	
					rgb_fix[tempi*3] = (int)(rgb[3*(i-1)]*(1-w) +rgb[3*i]*w);
					rgb_fix[tempi*3+1] =(int) (rgb[3*(i-1)+1]*(1-w) +rgb[3*i+1]*w);
					rgb_fix[tempi*3+2] = (int)(rgb[3*(i-1)+2]*(1-w) +rgb[i*3+2]*w);
					}
				color_weight_fix[i]= 0;
				color_weight_fix[i+1] = 1;
				i= i+2;
				
				
				color_weight_fix[i] = temp;
				rgb_fix[i*3] = rgb[(i-2)*3];
				rgb_fix[i*3+1] = rgb[(i-2)*3+1];
				rgb_fix[i*3+2] = rgb[(i-2)*3+2];
				
				a = true;
			}
			else {
				color_weight_fix[i] = temp;
				rgb_fix[i*3] = rgb[(i-2)*3];
				rgb_fix[i*3+1] = rgb[(i-2)*3+1];
				rgb_fix[i*3+2] = rgb[(i-2)*3+2];
			}
		}
		
	}
	private void sortWeight(){
		int i,j;
		float temp;
		int colortemp;
		for(i=0;i<color_weight_fix.length-1;i++){
			for(j=i+1;j<color_weight_fix.length;j++){
				if(color_weight_fix[j] < color_weight_fix[i]){
					temp  = color_weight_fix[i];
					color_weight_fix[i] = color_weight_fix[j];
					color_weight_fix[j] = temp;
					
					colortemp =  rgb_fix[3*i];
					rgb_fix[3*i] = rgb_fix[3*j];
					rgb_fix[3*j] = colortemp;
					
					colortemp =  rgb_fix[3*i+1];
					rgb_fix[3*i+1] = rgb_fix[3*j+1];
					rgb_fix[3*j+1] = colortemp;
					
					colortemp =  rgb_fix[3*i+2];
					rgb_fix[3*i+2] = rgb_fix[3*j+2];
					rgb_fix[3*j+2] = colortemp;
				}
						
			}
		}
		
		color = new int[color_weight.length+2];
		Log.i("fdsafa",String.valueOf(color.length));
		
		for(i=0;i<color_weight.length+2;i++)
			color[i] = (int)Color.rgb(rgb_fix[i*3], rgb_fix[i*3+1], rgb_fix[i*3+2]);
	}
	
	private void setColor(float sweepAngle,Paint paint){
		if(sweepAngle - 90 < 0) 
				sweepAngle = sweepAngle +270;
		else 
			sweepAngle = sweepAngle - 90;
		
		float weight =  sweepAngle / 360;
		
		for(int i= 1; i<color_weight_fix.length ;i++)
		if(weight <= color_weight_fix[i] && weight >=color_weight_fix[i-1]){
			float w = ( weight-color_weight_fix[i-1] )/(color_weight_fix[i] - color_weight_fix[i-1]);
			int  red  =(int) (rgb_fix[i*3] * w +rgb_fix[i*3-3]*(1-w));
			int  green = (int) (rgb_fix[i*3+1] * w +rgb_fix[i*3-2]*(1-w));
			int  blue = (int) (rgb_fix[i*3+2] * w +rgb_fix[i*3-1]*(1-w));
			paint.setColor(Color.rgb(red, green, blue));
			return;
		}
		
	}
	private  void drawEdge(Canvas canvas,float sweepAngle,boolean right){
		int xx,yy,rr;
		Paint paint =  new Paint();
		float temp = 270 ;
		
		
		if(right  == true){
			temp =temp + 1;
			setColor(sweepAngle+1, paint);
			}
		else {
			temp =temp +1;
			setColor(sweepAngle+1, paint);
			}
		xx = (int)(x+ r * Math.cos(  (temp+sweepAngle)/180*Math.PI )) ;
		yy = (int)(y+ r * Math.sin(   (temp+sweepAngle)/180*Math.PI )) ;
		rr =  width;
		
		
		
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		
		canvas.drawCircle(xx,yy , rr, paint);
		/*
		RectF rectf =  new RectF(xx-rr,yy-rr,xx+rr,yy+rr);
		
		if(right == true)
			canvas.drawArc(rectf,sweepAngle-90,360,false,paint);
		else 
			canvas.drawArc(rectf,sweepAngle-90+180,360,false,paint);
			*/
	}
	
	



	private void drawText(Canvas canvas){
		Paint paint = new Paint();
		int  i;
		float[] pos = new float[8*2];
		paint.setTextSize(textsize);
		paint.setAntiAlias(true);
		if(score < 70 )
			paint.setColor(Color.rgb(0xfd, 0x72, 0x72));
		else 
			paint.setColor(Color.rgb(0x05, 0x8d, 0xa6));
		canvas.drawText(num, x-(float)textsize*23/40, y+(float)textsize/4, paint);
		
		
		paint.setColor(Color.rgb(0x66, 0x66, 0x66));
		paint.setTextSize((float)textsize/4);
		canvas.drawText("分", x - (float)textsize*20/40 + textsize, y+(float)textsize/4, paint);//看图调节出来的参数
		
		
		float temp = (float)(textsize);
		paint.setTextSize(temp/8);
		paint.setColor(Color.rgb(0x08, 0x9f, 0x9f));
		String doc  ="查看更多历史分数";
		float yy = y+temp/8+(float)textsize/4+textsize/6;
		for(i=0;i<8;i++){
			pos[i*2] = x- temp/8*4+temp/8*i;
			pos[i*2+1] = yy;
			canvas.drawText(doc,i,i+1, pos[i*2], pos[i*2+1], paint);
		}
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(wr+wr+width*2, wr+wr+width*2);//预留一点长度。
	}
	
	
	
	
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		background(canvas);
		
		
		
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE); 
		paint.setAntiAlias(true);
		paint.setStrokeWidth(width);
		RectF rectf =  new RectF(x-r,y-r,x+r,y+r);
		Shader shader = new SweepGradient(x, y,color,color_weight_fix);
		paint.setShader(shader);
		canvas.drawArc(rectf,270,sweepAngle,false, paint);
		drawEdge(canvas,sweepAngle,true);
		drawText(canvas);
	
	}


	public void start(){
		handler = new Handler();
		sweepAngle = 0;
		score = 0;
		score_float = 0 ;
		runnabel = new Runnable() {
		
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				num = String.valueOf(score);
				invalidate();
				
				if (sweepAngle == max_sweepAngle)
					handler.removeCallbacks(this);
				
				else 
					handler.postDelayed(this, 2);
				    
			 
				 sweepAngle  += max_sweepAngle / (time/2);
			     score_float =  score_float + (float)(max_score)/(time/2);
			     score = (int)score_float;
			     if (sweepAngle > max_sweepAngle){
						sweepAngle = max_sweepAngle;
						score = max_score;
				 }
				
				}
		};
		handler.postDelayed(runnabel, 2);
		
	}
	
}
