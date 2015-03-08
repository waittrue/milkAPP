package com.inhand.milk.fragment.temperature_milk;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.aaaa.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.View;
import android.widget.Toast;

public class Excle extends View{
	private int width,height = 0;
	private int  numGrid ;
	private float backgroundGridHeight ;//背景条目的高度大小  sp
    private float leftTitleSize ;//饮奶量字体大小,单位sp
    private float rightTitleSize ;//今天字体大小,sp
    private float textMarginBottom;//饮奶量文字 和日平均文字之间的间隔，单位 sp；
    private float textMarginLeft;//文字偏移左边的量,单位sp
    private float titleMarginTop;//最顶上面那个空隙，单位sp
    private int maxnum ;//最大的数据
    private int minnum ;//最小的数据
    private float xAxisStart;//0时x的左边偏移
    private float lineWidth;//线条的宽度sp
    private float xAxisMarginRight ;//x轴的终点距离屏幕的距离。。sp
    private float pointR ;//原点的大小；
    private String  rightTiltle,leftTiltle;
  
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
	protected void onDraw(Canvas canvas){
		// TODO Auto-generated method stub
		//super.onDraw(canvas);	
		init();//这里为了获取高度。。必须放这里；
		drawBackground(canvas);
		drawPoint(canvas);	
	}
	//如果是今天,那格式<2.5h,10°>
	//如果是月或者周，那格式是<30°>
	public void clearData(){
		orignalLineData.clear();
	}
	 public void addLine(List<float[]> p){
		 orignalLineData.add(p);
		
	 }
	 
	 public void createPoint(){
		int i,length,j;
		float lenX,lenY,x,y;
		List<float[]> p;
		LineData.clear();
		lenX = xAxisMarginRight - xAxisStart;
		lenY = ( height - titleMarginTop - (height -titleMarginTop)/(numGrid+1));
		
		for(j=0 ;j < orignalLineData.size();j++ ){
			p = orignalLineData.get(j);
			if(p.isEmpty()){
				return;
			}
			if(p.get(0).length == 2){
				length = p.size();
				point = new float[length *2];
				for(i=0;i<length;i++){
					x =  (float)p.get(i)[0]/24 * lenX + xAxisStart;
					y =  titleMarginTop + lenY - p.get(i)[1]/maxnum *lenY ;
					point[i*2] = x;
					point[i*2+1] = y;	
				}	
			}
			else if (p.get(0).length ==1){
				length = p.size();
				point = new float[length*2 ];
				for(i=0;i<length;i++){
					x = (int)(  (float)i/(length-1) * lenX + xAxisStart);
					y = (int)(  titleMarginTop + lenY - p.get(i)[0]/maxnum *lenY );
					point[i*2] = x;
					point[i*2+1] = y;
					//Log.i("第"+String.valueOf(i)+"个点的时间", String.valueOf(lenX) +"  "+String.valueOf((float) i/(length-1) * lenX ));
				}
			}
			LineData.add(point);
		} 
	}
	
	
	private void drawPoint(Canvas canvas){
	
		int lineColor = getResources().getColor(R.color.temperature_milk_excle_line_color);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		createPoint();
		
		for(int n=0;n<LineData.size();n++){			
			Path path = new Path();
			float[] point = LineData.get(n);
			path.moveTo(point[0], point[1]);
			
			for(int i=1;i<point.length/2 ; i++){
				path.lineTo(point[i*2], point[i*2+1]);
			}
			
			paint.setStyle(Style.STROKE); 
			paint.setColor(lineColor);
			paint.setStrokeWidth(lineWidth);
			canvas.drawPath(path, paint);
			
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
		int textColor = getResources().getColor(R.color.temperature_milk_excle_title_text_color);
		paint.setAntiAlias(true);
		paint.setColor(textColor);
		paint.setTextSize(rightTitleSize);
		canvas.drawText(rightTiltle,width - textMarginLeft - paint.measureText(rightTiltle) , titleMarginTop-textMarginBottom , paint);
		canvas.drawText(leftTiltle, textMarginLeft, titleMarginTop-textMarginBottom,paint);	
	}
	
	//画除了标题的其他所有字体和背景
	private void drawBottomText(Canvas canvas){
		Paint paint= new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(leftTitleSize);
		float space = (height - titleMarginTop)/(numGrid+1);
		Rect rect = new Rect();
		int color1 = getResources().getColor(R.color.temperature_milk_excle_grid_first_background_color);
		int color2 = getResources().getColor(R.color.temperature_milk_excle_grid_second_background_color);
		int textColor = getResources().getColor(R.color.temperature_milk_excle_title_text_color);
		for(int i=0;i<numGrid;i++){	
			if(i%2 == 0)
				paint.setColor( color1);
			else 
				paint.setColor(color2);
			
			rect.set(0, (int)((i*space)+titleMarginTop), width,(int)( (i+1)*space+titleMarginTop)  );
			canvas.drawRect(rect, paint);
			paint.setColor(textColor);
		    canvas.drawText(String.valueOf((int)(maxnum - (float)(i+1)*(maxnum - minnum)/(numGrid))), textMarginLeft, (i+1)*space+titleMarginTop-textMarginBottom, paint);
		}
		
		float h = height - space/2+leftTitleSize/2;
		float x;
		int i,length = BottomTitle.length;
		for(i=0;i<length;i++){
			x =((float)xAxisStart *(length-1-i) + xAxisMarginRight *i)/(length-1);
			x = x - paint.measureText(BottomTitle[i])/2;
			canvas.drawText(BottomTitle[i], x, h, paint);
		}
	}
	
	private void init(){
		int oldheight;
		oldheight = height;
		width = getWidth();
		height = getHeight();	
		if (oldheight == 0){
			leftTitleSize = getResources().getDimension(R.dimen.temperature_milk_excle_left_title_text_size);
			textMarginLeft = getResources().getDimension(R.dimen.temperature_milk_excle_text_margin_left);
			titleMarginTop = getResources().getDimension(R.dimen.temperature_milk_excle_title_margin_top);
			rightTitleSize = getResources().getDimension(R.dimen.temperature_milk_excle_right_title_text_size);
			xAxisStart = getResources().getDimension(R.dimen.temperature_milk_excle_x_axis_start);
			xAxisMarginRight = getResources().getDimension(R.dimen.temperature_milk_excle_x_axis_margin_right);
			pointR = getResources().getDimension(R.dimen.temperature_milk_excle_circle_r);
			lineWidth = getResources().getDimension(R.dimen.temperature_milk_excle_line_width);
			backgroundGridHeight = getResources().getDimension(R.dimen.temperature_milk_excle_background_grid_height);
			textMarginBottom = getResources().getDimension(R.dimen.temperature_milk_excle_text_margin_bottom);
			xAxisMarginRight = (width - xAxisMarginRight);
		
			numGrid = (int) ((height -titleMarginTop)/backgroundGridHeight);
			if(numGrid %2 ==0)
				numGrid = numGrid + 1;
		}
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
		rightTiltle = a;
	}
	public void setLeftTiltle(String a){
		leftTiltle = a;
	}
	public void setRange(int max,int min){
		maxnum = max;
		minnum = min;
	}
}
