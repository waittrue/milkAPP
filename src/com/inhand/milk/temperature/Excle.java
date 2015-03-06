package com.inhand.milk.temperature;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.security.auth.PrivateCredentialPermission;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;



import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.renderscript.Sampler.Value;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Excle extends View{
	private int width,height;
	private    int  num ;
	private  float brgLineHeight = 25;//背景条目的高度大小  sp
    private  float  milkTextSize = 10;//饮奶量字体大小,单位sp
    private float   rightTextSize = 15;//今天字体大小,sp
    private float   interval = 4 ;//饮奶量文字 和日平均文字之间的间隔，单位 sp；
    private float  offsetx  = 6;//文字偏移左边的量,单位sp
    private float offsety = 30;//最顶上面那个空隙，单位sp
    private  int maxnum = 50;//最大的数据
    private int  minnum ;//最小的数据
    private float leftoffset= 30;//0时x的左边偏移
    private float lineWidth = 1.3f;//线条的宽度sp
    private float rightoffset = 20;//x轴的终点距离屏幕的距离。。sp
    private float pointR = 2.7f;//原点的大小；
   
    private String  RightTiltle,LeftTiltle;
   
  
   
    private int[] MONTH = new int[30];
    
    private float[] point ;
    private List<float[]> LineData =  new ArrayList<float[]>();
    private List<List<float[]>> orignalLineData = new ArrayList<List<float[]>>();
    
    private String[]  BottomTitle ;
    private String[]   dayBottom = {"0时","12时","0时"};
    private String[]   weekBottom = new String[7];
    private String[]  monthBottom = new String[5];
    
    
	public Excle(Context context) {
		super(context);	
		
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);	
		
	//	canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	   // canvas.drawColor(Color.WHITE);
			init();//这里为了获取高度。。必须放这里；
			drawBackground(canvas);
			drawPoint(canvas);
		
	}
	//如果是今天,那格式<2.5h,10°>
	//如果是月或者周，那格式是<30°>
	 public void addLine(List<float[]> p){
		 orignalLineData.add(p);
		
	 }
	 public void createPoint(){
		
		int i,length,j;
		float lenX,lenY,x,y;
		List<float[]> p;
		LineData.clear();
		lenX = rightoffset - leftoffset;
		lenY = ( height - offsety - (height -offsety)/(num+1));
		
		for(j=0 ;j < orignalLineData.size();j++ ){
			p = orignalLineData.get(j);
			if(p.isEmpty()){
				return;
			}
		
			if(p.get(0).length == 2){
				length = p.size();
				point = new float[length *2];
				for(i=0;i<length;i++){
				
					x =  (float)p.get(i)[0]/24 * lenX + leftoffset;
					y =  offsety + lenY - p.get(i)[1]/maxnum *lenY ;
					point[i*2] = x;
					point[i*2+1] = y;
				
					}
			
			}
			else if (p.get(0).length ==1){
				length = p.size();
				point = new float[length*2 ];
				for(i=0;i<length;i++){
					x = (int)(  (float)i/(length-1) * lenX + leftoffset);
					y = (int)(  offsety + lenY - p.get(i)[0]/maxnum *lenY );
					point[i*2] = x;
					point[i*2+1] = y;
					//Log.i("第"+String.valueOf(i)+"个点的时间", String.valueOf(lenX) +"  "+String.valueOf((float) i/(length-1) * lenX ));
				}
			}
			LineData.add(point);
		}
			
	   
	}
	private float calculate(int indexN,int index){
		float angle,temp1,temp2;
		float[] point = LineData.get(indexN);
		temp1 = ((float)point[index*2+1] - point[index*2-1])/(point[index*2] - point[index*2-2]);
		temp1 = (float)Math.atan((double)temp1);
		temp1 =(float) Math.PI +temp1;
		
		temp2 = ((float)point[index*2+3] - point[index*2+1])/(point[index*2+2] - point[index*2]);
		temp2 = (float)Math.atan((double)temp2);
		//temp2 =(float) Math.PI +temp2;
		
		angle = (temp1 +temp2)/2 - (float)Math.PI/2;
		if(angle > Math.PI) 
			angle = angle - (float)Math.PI;
		else if (angle < 0)
			angle = angle +(float)Math.PI;
		return angle;
	}
	
	private float[] dingwei(int indexN,int index){
		float angle1 ,angle2;
		float y1,y2,x1,x2,tan1,tan2;
		float x,y;
		x1 = LineData.get(indexN)[2*index-2];
		y1 = LineData.get(indexN)[2*index-1];
		x2 = LineData.get(indexN)[2*index];
		y2 = LineData.get(indexN)[2*index+1];
		
		if(index == 1){
			angle1 = 0;
			angle2 = calculate(indexN,index);
		}
		else if(index == point.length/2 -1){
			angle1 = calculate(indexN,index-1);
			angle2 = 0;
		}
		else {
			angle1 = calculate(indexN,index-1);
			angle2 = calculate(indexN,index);
		}
		tan1 = (float)Math.tan((double)angle1);
		tan2 = (float)Math.tan((double)angle2);
		float[] a =new float[4];
		float[] point = LineData.get(indexN);
		x = (point[index*2] /3+ point[index*2-2]*2/3);
	
		y = (x-x1)*tan1 +y1;
		a[0]= x;
		a[1] = y;
		x = (point[index*2] *2/3+ point[index*2-2]/3);
		
		y = (x-x2)*tan2 +y2;
		a[2]= x;
		a[3] = y;
		return a;
		
	}
	
	private void drawPoint(Canvas canvas){
		
		//float[] old ;
		int n;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		createPoint();
		
		
		
		
		for(n=0;n<LineData.size();n++){
			
			Path path = new Path();
			float[] point = LineData.get(n);
			path.moveTo(point[0], point[1]);
			
		
			
			
			for(int i=1;i<point.length/2 ; i++){
				//old = dingwei(n,i);//这个画曲线的时候用
				//path.cubicTo(  old[0], old[1],  old[2], old[3], point[i*2], point[i*2+1]);
				path.lineTo(point[i*2], point[i*2+1]);
			//	Log.i(String.valueOf(n)+"第"+String.valueOf(i)+" "+String.valueOf(point[2*i]), String.valueOf( point.length ));
				//canvas.drawLine( point[i*2-2], point[i*2+1-2],  point[i*2], point[i*2+1], paint);
			}
			
			
			
			
			paint.setStyle(Style.STROKE); 
			paint.setColor(Color.rgb(0x01, 0x96, 0xa0));
			paint.setStrokeWidth(lineWidth);
			canvas.drawPath(path, paint);
			
			//Log.i("宽度", String.valueOf( paint.getStrokeWidth() ));
			for(int i=0;i<point.length/2;i++)
				canvas.drawCircle(point[i*2], point[i*2+1], pointR, paint);
		
		
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.FILL);
			paint.setStrokeWidth(1);
			for(int i=0;i<point.length/2;i++)
				canvas.drawCircle(point[i*2], point[i*2+1], pointR-lineWidth/2, paint);
		}
		
	}
	
	
	//画除了点和线以外的其他图形
	private void drawBackground(Canvas canvas){
		
		drawTopText(canvas);
		drawBottomText(canvas);
	}
	
	
	//画上面两个标题
	private void drawTopText(Canvas canvas){
		Paint paint= new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize(rightTextSize);
		canvas.drawText(RightTiltle,width - offsetx - rightTextSize *2 , offsety-interval , paint);
		canvas.drawText(LeftTiltle, offsetx, offsety-interval,paint);

		
	}
	
	//画除了标题的其他所有字体和背景
	private void drawBottomText(Canvas canvas){
		Paint paint= new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(milkTextSize);
		float space = (float)(height - offsety)/(num+1);
		Rect rect = new Rect();
		for(int i=0;i<num;i++){	
			if(i%2 == 0)
				paint.setColor(Color.rgb(0xed, 0xfa, 0xf9));
			
			else 
				paint.setColor(Color.rgb(0xff,0xff,0xff));
			
			
			rect.set(0, (int)((i*space)+offsety), width,(int)( (i+1)*space+offsety)  );
			canvas.drawRect(rect, paint);
			paint.setColor(Color.BLACK);
		    canvas.drawText(String.valueOf((int)(maxnum - (float)(i+1)*(maxnum - minnum)/(num))), offsetx, (i+1)*space+offsety-interval, paint);
		}
		
		float h = height - space/2+milkTextSize/2;
		float x;
		int i,length = BottomTitle.length;
		for(i=0;i<length;i++){
			x =((float)leftoffset *(length-1-i) + rightoffset *i)/(length-1);
			x = x - paint.measureText(BottomTitle[i])/2;
			canvas.drawText(BottomTitle[i], x, h, paint);
		}
		
		
	}
	
	private void init(){
		int oldheight;
		oldheight = height;
		width = getWidth();
		height = getHeight();	
		
		float scale = this.getResources().getDisplayMetrics().density;
		
		if(oldheight ==0){
			milkTextSize = (milkTextSize * scale);
			offsetx = (offsetx *scale);
			offsety = (offsety *scale);
			rightTextSize = (rightTextSize * scale);
			leftoffset = (leftoffset * scale);
			rightoffset = (width - rightoffset*scale);
			pointR = (pointR *scale);
			lineWidth = (lineWidth *scale);
			brgLineHeight = brgLineHeight *scale;
			num = (int) ((height -offsety)/brgLineHeight);
			if(num %2 ==0)
				num = num + 1;
		}
		// Toast.makeText(this.getContext(), String.valueOf(offsety)+" "+ String.valueOf(brgLineHeight), Toast.LENGTH_SHORT).show();
	}

	public void initDayText(){
		BottomTitle = dayBottom;
		
	}
	
	
	
	public void initWeekText(){
		Calendar calendar = Calendar.getInstance();
		int day,i,month;
		for(i=6;i>0;i--){
			day = calendar.get(Calendar.DATE);
			if(day == 1){
				month = calendar.get(Calendar.MONTH);
				weekBottom[i] = String.valueOf(month)+"月"+String.valueOf(day)+"日";
			} 
			else 
				weekBottom[i] = String.valueOf(day);
			
			calendar.roll(Calendar.DATE, -1);
		}
		
		day = calendar.get(Calendar.DATE);
		month = calendar.get(Calendar.MONTH);
		weekBottom[i] = String.valueOf(month)+"月"+String.valueOf(day)+"日";
		BottomTitle = weekBottom;
		
	}
	
	public void initMonthText(){
		Calendar calendar = Calendar.getInstance();
		int preday =100,i,month,day;
		for(i=4 ;i> 0;i--){
		   day = calendar.get(Calendar.DATE);
		   if(day >preday){
			   monthBottom[i+1] = String.valueOf(calendar.get(Calendar.MONTH) + 1)+"月"+
					   				String.valueOf(preday)+"日";
		   }
		   monthBottom[i] = String.valueOf(day);
		   
		   preday = day;
		   calendar.roll(Calendar.DATE, -7);
		}
		
		day = calendar.get(Calendar.DATE);
		month = calendar.get(Calendar.MONTH);
		
		monthBottom[i] = String.valueOf(month)+"月"+String.valueOf(day)+"日";
		
		BottomTitle = monthBottom;
	}
	

	public void setRightTiltle(String a){
		RightTiltle = a;
	}
	public void setLeftTiltle(String a){
		LeftTiltle = a;
	}
	public void setRange(int max,int min){
		maxnum = max;
		minnum = min;
	}
}
